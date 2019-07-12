package top.wwf.common.base;


import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
* @Description:    用于保存全局配置，注意：当对全局配置中的属性值进行修改时，并不会同步到配置文件
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019/1/17 11:15
*/
public class GlobalConfig {
    private static Map<String,String> configMap= new ConcurrentHashMap<>();
    private static PropertiesLoader propertiesLoader=new PropertiesLoader("project.properties");
    static {

    }

    /**
     *  图片服务器域名
     */
    public static final String IMAGE_SERVER_HOST =getConfig("image.server.host");

    /**
     * web服务器域名
     */
    public static final String WEB_SERVER_HOST=getConfig("web.server.host");

    /**
     * session过期时间
     */
    public static final int    SESSION_TIMEOUT   =Integer.parseInt(getConfig("session.timeout"));
    /**
     * redis前缀
     */
    public static final String REDIS_PREFIX_KEY  =getConfig("redis.prefixKey");
    /**
     * 程序读写文件时的父路径，以 File.separator 结尾
     */
    public static final String FILE_PARENT_PATH  =getFileParentPath();



    public static String getConfig(String key){
        String value=configMap.get(key);
        if (value==null){
            value=propertiesLoader.getConfigValue(key);
            configMap.put(key,value);
        }
        return value;
    }

    private static String getFileParentPath(){
        String fileParentPath=getConfig("file.parentPath");
        if (!fileParentPath.endsWith(File.separator))fileParentPath+=File.separator;
        return fileParentPath;
    }
    public static void setConfig(String key,String value){
        configMap.put(key,value);
    }

}
