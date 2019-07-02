package top.wwf.common.consts;


import top.wwf.common.base.GlobalConfig;
import top.wwf.common.base.MySession;
import top.wwf.common.exception.MyException;
import top.wwf.common.utils.MyFileUtils;

import java.io.File;

/**
* @Description:    常量定义
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019/1/17 17:49
*/
public class Const {

    //图片服务器的host
    public static final String IMAGE_SERVER_HOST           =GlobalConfig.IMAGE_SERVER_HOST;
    //web服务器的host
    public static final String WEB_SERVER_START_URL=GlobalConfig.WEB_SERVER_HOST+"/shop-for-training/view/";

    //应用统一编码
    public static final String ENCODE                      ="utf-8";

    //上传的文件的临时保存路径
    public static final String UPLOAD_FILE_TEMP_PATH=GlobalConfig.FILE_PARENT_PATH+"uploadFiles"+File.separator;

    //最长等待锁时间
    public static final int MAX_WAIT_LOCK_TIME=10000;

    public static final int YES                         =1;
    public static final int NO                          =0;
    //微信appid
    public static final String WEIXIN_APPID_OPENID = GlobalConfig.getConfig("weixin.appid");
    //微信secret
    public static final String WEIXIN_SECRET_OPENID = GlobalConfig.getConfig("weixin.secret");
    //微信grant_type
    public static final String WEIXIN_GRANT_TYPE_OPENID = "authorization_code";
    //获取openid请求的url
    public static final String URL_GET_WX_OPENID           = "https://api.weixin.qq.com/sns/jscode2session?appid={appid}&secret={secret}&js_code={js_code}&grant_type={grant_type}";

    //response 返回头类型名
    public static final String CONTENT_TYPE_NAME = "Content-type";
    //response 返回头类型值
    public static final String CONTENT_TYPE_VALUE = "text/html;charset=UTF-8";
    //response 返回数据编码
    public static final String CHARACTER_ENCODING = "UTF-8";
    //token的redis的key的前缀
    public static final String TOKEN_PREFIX_KEY="token_";
    //用于获取该线程处理请求中的mySession的threadLocal
    public static final ThreadLocal<MySession> SESSION_THREAD_LOCAL=new ThreadLocal<>();
    //时间格式
    public static final String DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
    static {
        MyFileUtils.createDirectory(Const.UPLOAD_FILE_TEMP_PATH);
    }

    /**
     * 用户的身份
     */
    public enum  USER_ROLE{
        PART_A(1,"甲方"),
        PART_B(2,"乙方"),
        PART_C(3,"第三方");
        private int key;
        private String role;

        USER_ROLE(int key, String role) {
            this.key=key;
            this.role=role;
        }

        public int getKey() {
            return key;
        }

        public String getRole() {
            return role;
        }

        public static String getRoleByKey(int key){
            for (USER_ROLE  userRole:USER_ROLE.values()){
                if (userRole.getKey()==key){
                    return userRole.getRole();
                }
            }
            throw new MyException(HttpResponseEnum.PROHIBIT,"参数非法");
        }
    }


    /**
     * 性别：男、女
     */
    public enum SEX{
        MAN(1,"男"),
        WOMAN(0,"女");
        private int key;
        private String desc;

        SEX(int key, String desc) {
            this.key = key;
            this.desc = desc;
        }
        public static String getDescByKey(int key){
            for (SEX sex:SEX.values()){
                if (key==sex.getKey()){
                    return sex.getDesc();
                }
            }
            throw new MyException(HttpResponseEnum.PROHIBIT,"参数非法");
        }

        public static int getKeyByDesc(String desc){
            for (SEX sex:SEX.values()){
                if (sex.getDesc().equals(desc)){
                    return sex.getKey();
                }
            }
            throw new MyException(HttpResponseEnum.PROHIBIT,"参数非法");
        }

        public int getKey() {
            return key;
        }

        public String getDesc() {
            return desc;
        }
    }


}
