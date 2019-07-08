package top.wwf.common.consts;



import top.wwf.common.base.GlobalConfig;
import top.wwf.common.base.MySession;
import top.wwf.common.exception.MyException;
import top.wwf.common.utils.MyFileUtils;
import java.io.File;

/**
* @Description:    TODO
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-05 10:41
*/
public class Const {

    //图片服务器的host
    public static final String IMAGE_SERVER_HOST           =GlobalConfig.IMAGE_SERVER_HOST;
    //web服务器的host
    public static final String WEB_SERVER_START_URL=GlobalConfig.WEB_SERVER_HOST+"/shop-for-training/view/";
    //session的有效时间,单位秒
    public static final int SESSION_TIMEOUT=GlobalConfig.SESSION_TIMEOUT;

    //应用统一编码
//    public static final String ENCODE                      ="utf-8";

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
     * 用户权限
     * 因为涉及权限较简单，此处直接将权限硬编码至代码中
     */
    public enum POWER{
        BUY(1,"购买"),
        SELL(3,"出售"),
        MANAGE(3,"管理");
        private int key;
        private String desc;

        POWER(int key, String desc) {
            this.key = key;
            this.desc = desc;
        }
    }

    /**
     * 用户的身份
     */
    public enum  USER_ROLE{
        BUYER(1,"买家",POWER.BUY),
        SELLER(2,"卖家",POWER.BUY,POWER.SELL),
        MANAGER(3,"管理员",POWER.MANAGE);  //因为区分网页和小程序两个端口，所以当前管理员只具备管理权限
        private int    key;
        private String desc;
        private POWER[] powers;

        USER_ROLE(int key, String desc,POWER... powers) {
            this.key=key;
            this.desc = desc;
            this.powers=powers;
        }

        public int getKey() {
            return key;
        }

        public String getDesc() {
            return desc;
        }

        //是否拥有某权限
        public boolean hasPower(POWER power){
            for (POWER myPower:this.powers){
                if (myPower==power)return true;
            }
            return false;
        }

        //获取用户身份
        public static USER_ROLE getRoleByKey(int key){
            for (USER_ROLE  userRole:USER_ROLE.values()){
                if (userRole.getKey()==key){
                    return userRole;
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

    public enum GOODS_STATE{
        ON_SALE(1,"在售"),
        SELL_OUT(2,"已售完"),
        LOWER_SHELF(3,"下架"),
        WAIT_APPROVE(4,"待审批"),
        APPROVE_FAIL(5,"审批失败");

        private int key;
        private String desc;

        GOODS_STATE(int key, String desc) {
            this.key = key;
            this.desc = desc;
        }
        public int getKey() {
            return key;
        }

        public String getDesc() {
            return desc;
        }

        public static String getDescByKey(int key){
            for (GOODS_STATE goodsState:GOODS_STATE.values()){
                if (key==goodsState.getKey()){
                    return goodsState.getDesc();
                }
            }
            throw new MyException(HttpResponseEnum.PROHIBIT,"参数非法");
        }
    }

}
