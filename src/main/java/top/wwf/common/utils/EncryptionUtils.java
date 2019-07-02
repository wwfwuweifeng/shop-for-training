package top.wwf.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
* @Description:    加密算法工具类
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019/1/18 10:10
*/
public class EncryptionUtils {
    //to32位的编码表
    private static final char[] CODE_TABLE_32={'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    //to8位的编码表
    private static final char[] CODE_TABLE_8={'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'B', 'C', 'd', 'e', 'F','g','h',
                                                'i','j','K','L','M','n','o','P','Q','r','S','t','u','v','W','x','Y','Z'};

    //用于异或操作时的密钥
    private static final int SECRET_KEY=0b10100110;

    /**
     * 返回32位的加密结果
     * @param source
     * @return
     */
    public static String md5To32(byte[] source){
        String md5Result = null;
        try {
            MessageDigest md=MessageDigest.getInstance("md5");
            byte[] temp;
            synchronized (EncryptionUtils.class){
                md.update(source);
                temp=md.digest();   //返回一个 128 位的长整数
            }
            char[] md5Encode=new char[32];
            int addr=0;
            for (byte bt:temp){
                md5Encode[addr++]=CODE_TABLE_32[(bt>>>4)&0b1111]; //高四位
                md5Encode[addr++]=CODE_TABLE_32[bt&0b1111];   //低四位
            }
            md5Result=new String(md5Encode);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5Result;
    }

    public static String md5To32(String source){
        return md5To32(source.getBytes());
    }

    
    /**
     * 返回8位的加密结果，但是并不能保证是一对一的关系
     * @param source
     * @return
     */
    public static String md5To8(byte[] source){
        String md5Result = null;
        try {
            MessageDigest md=MessageDigest.getInstance("md5");
            byte[] temp=null;
            synchronized (EncryptionUtils.class){
                md.update(source);
                temp=md.digest();
            }
            char[] md5Encode=new char[8];
            for (int i=0;i<8;i++){
                md5Encode[i]=CODE_TABLE_8[((temp[i]^temp[15-i])&0b11111)];
            }
            md5Result=new String(md5Encode);
        }catch (Exception e){
            e.printStackTrace();
        }
        return md5Result;
    }
    
    public static String md5To8(String source){
        return md5To8(source.getBytes());
    }

    /**
     * 自己设计的一种位加密算法：双向加密、完全基于位运算
     * 没有返回值，加密操作直接作用于原对象上
     * @param source 等待加密的字节数组
     */
    public static void encryptBEP(byte[] source){
        byte temp_low;
        byte temp_high;
        byte temp;
        for (int i = 0; i < source.length; i++) {
            temp_low= (byte)((source[i]&0xff)>>>4);   //将高四位转为低四位，无符号右移
            temp_high= (byte) (source[i]<<4);     //将低四位转为高四位，左移（左移不区分有无符号）
            temp= (byte) (temp_low|temp_high);
            source[i]= (byte) (temp^SECRET_KEY);
        }
    }

    /**
     * 用于对位加密进行解密
     * @param source
     */
    public static void decryptBEP(byte[] source){
        byte temp_low;
        byte temp_high;
        for (int i = 0; i < source.length; i++) {
            source[i]= (byte) (source[i]^SECRET_KEY);
            temp_low= (byte) ((source[i]&0xff)>>>4);
            temp_high= (byte) (source[i]<<4);
            source[i]=(byte) (temp_low|temp_high);
        }
    }

    public static void main(String[] args){

        //加密word文件
        MyFileUtils.encryptFile("/Users/wwf/Desktop/加密演示/租房模板.docx");
        //加密mp3文件
        MyFileUtils.encryptFile("/Users/wwf/Desktop/加密演示/让我留在你身边mp3.mp3");


//        //解密word文件
//        MyFileUtils.decryptFile("/Users/wwf/Desktop/加密演示/租房模板.docx");
//        //解密mp3文件
//        MyFileUtils.decryptFile("/Users/wwf/Desktop/加密演示/让我留在你身边mp3.mp3");
    }
}
