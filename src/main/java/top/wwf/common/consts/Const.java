package top.wwf.common.consts;



import top.wwf.common.base.GlobalConfig;
import top.wwf.common.base.MySession;
import top.wwf.common.exception.MyException;
import top.wwf.common.utils.MyFileUtils;
import java.io.File;
import java.util.List;

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
    //recommend页的内容
    public static final List<String> RECOMMEND_IMAGES=GlobalConfig.RECOMMEND_IMAGES;

    //用户sys表中最大的id值
    public static final String USER_MAX_ID_KEY="user_sys_max_id";

    //上传的文件的临时保存路径
    public static final String UPLOAD_FILE_TEMP_PATH=GlobalConfig.FILE_PARENT_PATH+"uploadFiles"+File.separator;

    //最长等待锁时间
    public static final int MAX_WAIT_LOCK_TIME=10000;
    public static final int LIST_SIZE=4;
    public static final int YES                         =1;
    public static final int NO                          =0;

    public static final String IMAGE_DIR=GlobalConfig.getConfig("file.imagePath")+File.separator;
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
    public static final String CONTENT_TYPE_VALUE                   = "text/html;charset=UTF-8";
    //response 返回数据编码
    public static final String CHARACTER_ENCODING                   = "UTF-8";
    //token的redis的key的前缀
//    public static final String TOKEN_PREFIX_KEY                     ="token_";
    //用于获取该线程处理请求中的mySession的threadLocal
    public static final ThreadLocal<MySession> SESSION_THREAD_LOCAL =new ThreadLocal<>();
    //时间格式
    public static final String DATE_FORMAT                          ="yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_TO_NUM                          ="yyyyMMddHHmmss";
    public static final String ORDER_MAX_ID                         = "order_max_id";

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

    /**
     * 商品属性操作
     */
    public interface GOODS_PARAM_OPERATE{
        int ADD=1;  //添加
        int UPDATE=2;   //修改
        int DEL=3;  //删除
    }

    public enum PAY_TYPE{
        YL(1,"银联"),
        ALP(2,"支付宝"),
        WX(3,"微信");

        private int key;
        private String desc;

        PAY_TYPE(int key, String desc) {
            this.key = key;
            this.desc = desc;
        }
        public static String getDescByKey(int key){
            for (PAY_TYPE payType: PAY_TYPE.values()){
                if (key==payType.getKey()){
                    return payType.getDesc();
                }
            }
            throw new MyException(HttpResponseEnum.PROHIBIT,"参数非法");
        }

        public static PAY_TYPE getPayTypeByKey(int key){
            for (PAY_TYPE payType: PAY_TYPE.values()){
                if (payType.getKey()==key){
                    return payType;
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
