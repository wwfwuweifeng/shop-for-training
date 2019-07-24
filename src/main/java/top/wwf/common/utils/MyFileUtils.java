package top.wwf.common.utils;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import top.wwf.common.base.GlobalConfig;
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
