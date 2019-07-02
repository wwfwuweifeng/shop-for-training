package top.wwf.common.utils;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import top.wwf.common.base.GlobalConfig;
import top.wwf.common.consts.Const;
import top.wwf.common.consts.HttpResponseEnum;
import top.wwf.common.exception.MyException;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

/**
* @Description:    文件操作工具类
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019/1/18 10:09
*/
public class MyFileUtils {
    private static Logger logger= LoggerFactory.getLogger(MyFileUtils.class);

    /**
     * 将字节数组写入到文件
     * @param fileName
     * @param data
     */
    public static void writeBytesToFile(String fileName,byte[] data){
        try {
            FileUtils.writeByteArrayToFile(new File(fileName),data,false);
        } catch (IOException e) {
            logger.error("write file {} fail, reason is :{}",fileName,e.getMessage());
            throw new MyException(HttpResponseEnum.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * 从文件中读取字节数组
     * @param fileName
     * @return
     */
    public static byte[] readBytesFromFile(String fileName){
        byte[] fileByte=null;
        try {
            fileByte=FileUtils.readFileToByteArray(new File(fileName));
        } catch (IOException e) {
            logger.error("read file {} fail, reason is :{}",fileName,e.getMessage());
            throw new MyException(HttpResponseEnum.INTERNAL_SERVER_ERROR);
        }
        return fileByte;
    }

    /**
     * 加密的将字节数组写入到文件中。注意：加密操作会作用于原byte数组
     * @param fileName
     * @param data
     */
    public static void writeBytesToFileWithEncrypt(String fileName,byte[] data){
        if (GlobalConfig.OPEN_ECP_AND_DCP==1){  //开启
            EncryptionUtils.encryptBEP(data);
        }
        writeBytesToFile(fileName,data);
    }

    /**
     * 解密的将字节数组从文件中读出
     * @param fileName
     * @return
     */
    public static byte[] readBytesFromFileWithDecrypt(String fileName){
        byte[] result=readBytesFromFile(fileName);
        if (GlobalConfig.OPEN_ECP_AND_DCP==1){  //开启
            EncryptionUtils.decryptBEP(result);
        }
        return result;
    }


    /**
     * 对文件进行解密，并保存至原文件
     * @param fileName 待解密的文件名
     */
    public static void decryptFile(String fileName){
        if (GlobalConfig.OPEN_ECP_AND_DCP!=1){  //未开启
            return;
        }
        byte[] fileBytes=readBytesFromFileWithDecrypt(fileName);
        writeBytesToFile(fileName,fileBytes);
    }

    /**
     * 对文件进行加密，并保存至原文件
     * @param fileName
     */
    public static void encryptFile(String fileName){
        if (GlobalConfig.OPEN_ECP_AND_DCP!=1){  //未开启
            return;
        }
        byte[] fileBytes=readBytesFromFile(fileName);
        writeBytesToFileWithEncrypt(fileName,fileBytes);

    }


    /**
     * @param src 要读取文件头信息的文件的byte数组
     * @return 文件头信息
     * 方法描述：将要读取文件头信息的文件的byte数组转换成string类型表示
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        String hv;
        for (byte aSrc : src) {
            // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
            hv = Integer.toHexString(aSrc & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }

    /**
     * 判断文件是否是docx类型
     * @param src 文件的字节数组
     * @return
     */
    public static boolean isDocxFile(byte[] src){
        byte[] fileHeaderByte=null;
        if (src.length==4){
            fileHeaderByte=src;
        }else {
            fileHeaderByte=new byte[4];
            System.arraycopy(src,0,fileHeaderByte,0,4);
        }
        String fileHeaderValue=bytesToHexString(fileHeaderByte);
        return Const.DOCX_FILE_HEADER.equals(fileHeaderValue);
    }

    /**
     * 判断文件是否是docx类型
     * @param file 服务器接收到的文件
     * @return
     */
    public static boolean isDocxFile(MultipartFile file){
        InputStream inputStream=null;
        byte[] b = new byte[4];
        try {
            inputStream=file.getInputStream();
            inputStream.read(b, 0, b.length);
        }catch (Exception e){
            logger.error("判断上传文件：{} 的类型时发生异常，异常原因：",file.getOriginalFilename(),e);
            throw new MyException(HttpResponseEnum.INTERNAL_SERVER_ERROR);
        }finally {
            IOUtils.closeQuietly(inputStream);
        }
        return isDocxFile(b);
    }

    /**
     * 判断文件是否是docx类型
     * @param filePath  文件的路径
     * @return
     */
    public static boolean isDocxFile(String filePath){

        File file=new File(filePath);
        if (!file.isFile()){
            logger.info("文件:{} 不是docx文件");
            return false;
        }

        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
            byte[] b = new byte[4];
            is.read(b, 0, b.length);
            return isDocxFile(b);
        }catch(Exception e) {
            logger.error("判断文件：{}的类型时，发生错误，错误原因：{}", filePath, e);
            throw new MyException(HttpResponseEnum.INTERNAL_SERVER_ERROR);
        }finally {
            IOUtils.closeQuietly(is);   //如果close出现异常，会被忽略
        }
    }


    public static void createDirectory(String dirPath){
        File file=new File(dirPath);
        if (!file.exists()){
            file.mkdirs();
        }
        Validate.validState(file.isDirectory(), "传入的路径:%s 无法创建文件夹，请更换路径",dirPath);
    }

    /**
     * 获取某个文件夹下的文件的文件名（不对文件夹进行递归且文件夹名不返回）
     * @param dirPath
     * @return
     */
    public static List<String> getFileNameListByDir(String dirPath){
        File dir=new File(dirPath);
        File[] files=dir.listFiles();
        List<String> filesNameList=Lists.newArrayList();
        if (null==files){
            return filesNameList;
        }
        for (File file:files){
            if (file.isFile()){
                filesNameList.add(file.getName());
            }
        }
        return filesNameList;
    }

    /**
     * 获取某个文件夹下的文件个数（不对文件夹进行递归且文件夹不计数）
     * @param dirPath
     * @return
     */
    public static int getFileNumsByDir(String dirPath){
        File dir=new File(dirPath);
        File[] files=dir.listFiles();
        int nums=0;
        if (null==files){
            return nums;
        }
        for (File file:files){
            if (file.isFile())nums++;
        }
        return nums;
    }

    /**
     * 强制重命名文件/文件夹，若新文件名已被其他文件使用，则删除其他文件
     * @param oldNameWithPath   旧文件名带路径
     * @param newNameWithPath   新文件名带路径
     */
    public static void forcedRename(String oldNameWithPath,String newNameWithPath){
        File newFile=new File(newNameWithPath);
        if (newFile.exists()){
            newFile.delete();
        }
        File oldFile=new File(oldNameWithPath);
        oldFile.renameTo(newFile);
    }

    /**
     * 删除文件或文件夹
     * @param filePath
     */
    public static void delFileOrDir(String filePath){
        File file=new File(filePath);
        if (!file.exists())return;
        if (!FileUtils.deleteQuietly(file)){
            logger.error("删除文件：{} 失败",filePath);
            throw new MyException(HttpResponseEnum.INTERNAL_SERVER_ERROR);
        }
    }

    public static void playMp3File(HttpServletResponse response,String filePath,boolean isDecrypt){
        response.reset();
        //设置为音频输出
        response.addHeader("Content-Type", "audio/mpeg;charset=UTF-8");

        responseFile(response,filePath,isDecrypt);
    }

    private static void responseFile(HttpServletResponse response,String filePath,boolean isDecrypt){
        byte[] data=isDecrypt?readBytesFromFileWithDecrypt(filePath):readBytesFromFile(filePath);
        OutputStream outputStream=null;
        try {
            response.addHeader("Content-Length", "" + data.length);
//            response.setContentType("application/octet-stream;charset=UTF-8");
            //缓冲流
            outputStream = new BufferedOutputStream(response.getOutputStream());
            outputStream.write(data);
            //刷新缓存
            outputStream.flush();
            response.flushBuffer();
        }catch (Exception e){
            logger.error("下载文件：{} 时发生异常，异常原因：",filePath,e);
            throw new MyException(HttpResponseEnum.INTERNAL_SERVER_ERROR);
        }finally {
            //保持好习惯，用完就关掉
            IOUtils.closeQuietly(outputStream);
        }
    }
    /**
     * 客户端请求下载文件
     * @param response
     * @param filePath
     * @param fileNameForClient 客户端下载完成时，显示的文件名
     * @param isDecrypt 是否解密的读取字节数组
     */
    public static void downloadFile(HttpServletResponse response,String filePath,String fileNameForClient,boolean isDecrypt){
        try {
            fileNameForClient = URLEncoder.encode(fileNameForClient, "UTF-8");
        }catch (Exception e){
            logger.error("编码文件：{} 的文件名时发生异常，异常原因：",filePath,e);
            throw new MyException(HttpResponseEnum.INTERNAL_SERVER_ERROR);
        }
        response.reset();
        //保证文件都是下载的，不是解析
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileNameForClient + "\"");
        response.setContentType("application/octet-stream;charset=UTF-8");
        responseFile(response,filePath,isDecrypt);
    }

    /**
     * 将网络文件保存到指定位置
     * @param filePath
     * @param waitSaveFile
     */
    public static void saveFile(String filePath,MultipartFile waitSaveFile){
        try {
            waitSaveFile.transferTo(new File(filePath));
        }catch (Exception e){
            logger.error("保存文件：{}时发生异常，异常原因：",filePath,e);
            throw new MyException(HttpResponseEnum.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 移动文件至指定路径
     * @param srcFilePath
     * @param distFilePath
     */
    public static void moveFile(String srcFilePath,String distFilePath){
        try {
            delFileOrDir(distFilePath);
            FileUtils.moveFile(new File(srcFilePath),new File(distFilePath));
        }catch (Exception e){
            logger.error("移动文件{}至{}时发生异常，异常原因：",srcFilePath,distFilePath,e);
            throw new MyException(HttpResponseEnum.INTERNAL_SERVER_ERROR);
        }
    }
    public static void main(String[] args) throws IOException {
//        byte[] t1=readBytesFromFile("C:\\CloudMusic\\多幸运.mp3");
//        writeBytesToFileWithEncrypt("C:\\CloudMusic\\多幸运2.mp3",t1);
//        byte[] t2=readBytesFromFileWithDecrypt("C:\\CloudMusic\\多幸运2.mp3");
//        writeBytesToFile("C:\\CloudMusic\\多幸运3.mp3",t2);
//        System.out.println(isDocxFile("C:\\Users\\admin\\Desktop\\校训石.jpg"));
        File file=new File("/Users/wwf/Desktop/test/");
        System.out.println(FileUtils.deleteQuietly(file));

    }
}
