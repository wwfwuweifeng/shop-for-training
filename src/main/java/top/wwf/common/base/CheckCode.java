package top.wwf.common.base;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.wwf.common.consts.Const;
import top.wwf.common.consts.HttpResponseEnum;
import top.wwf.common.exception.MyException;
import top.wwf.common.utils.EncryptionUtils;
import top.wwf.common.utils.IdGenUtils;
import top.wwf.common.utils.JedisUtils;


/**
* @Description:    校验码对象
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-03-18 17:20
*/
public class CheckCode {
    private static       Logger logger              = LoggerFactory.getLogger(CheckCode.class);
    private static final String UPLOAD_PREFIX_KEY   ="checkId_for_upload_";
    private static final String DOWNLOAD_PREFIX_KEY ="checkId_for_download_";

    private String userOpenId;
    private String objectType;
    private String objectId;
    //用途：下载、上传
    private String purpose;
    private String checkId;
    private int fileIsUpload;

    private CheckCode(String userOpenId, String objectType, String objectId, String purpose,String checkId,int fileIsUpload) {
        this.userOpenId = userOpenId;
        this.objectType = objectType;
        this.objectId = objectId;
        this.purpose = purpose;
        this.checkId = checkId;
        this.fileIsUpload=fileIsUpload;
    }

    public String getUserOpenId() {
        return userOpenId;
    }

    public String getObjectType() {
        return objectType;
    }

    public String getObjectId() {
        return objectId;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getCheckId() {
        return checkId;
    }

    public int getFileIsUpload() {
        return fileIsUpload;
    }

    public void setFileIsUpload(int fileIsUpload) {
        this.fileIsUpload = fileIsUpload;
        JedisUtils.hset(checkId,"fileIsUpload",Const.YES+"");

    }

    public Long ttl(){
        return JedisUtils.ttl(checkId);
    }

    /**
     * 获取用于修改模板的上传校验码
     * @param userOpenId
     * @param objectType
     * @param objectId
     * @return
     */
    public static CheckCode getModifyUploadInstance(String userOpenId,String objectType,String objectId){
        return getUploadInstance(userOpenId,objectType,objectId);
    }

    /**
     * 用于获取创建模板的上传校验码
     * @param userOpenId
     * @param objectType
     * @return
     */
    public static CheckCode getCreateUploadInstance(String userOpenId,String objectType){
        //若是用于上传模板，则objectId等于userOpenId进行MD5加密后的值
        return getUploadInstance(userOpenId, objectType, EncryptionUtils.md5To32(userOpenId));
    }


    /**
     * 获取上传的校验码对象
     * @param userOpenId
     * @param objectType
     * @param objectId
     * @return
     */
    private static CheckCode getUploadInstance(String userOpenId,String objectType,String objectId){
        String key=UPLOAD_PREFIX_KEY+objectId;
        String checkId=JedisUtils.get(key);
        if (StringUtils.isBlank(checkId)){  //未获取过或者已经过期
            checkId= IdGenUtils.uuid().replace("-","");
            JedisUtils.hset(checkId, "userOpenId", userOpenId);
            JedisUtils.hset(checkId,"objectType",objectType);
            JedisUtils.hset(checkId,"objectId",objectId);
            JedisUtils.hset(checkId,"purpose",Const.PURPOSE_TYPE.UPLOAD);
            JedisUtils.hset(checkId,"fileIsUpload",Const.NO+"");
            JedisUtils.setex(key,Const.INVITE_CODE_VALID_TIME*60,checkId);  //设置过期时间15分钟
            JedisUtils.expire(checkId,Const.INVITE_CODE_VALID_TIME*60); //设置过期时间15分钟
            return new CheckCode(userOpenId, objectType, objectId, Const.PURPOSE_TYPE.UPLOAD,checkId,Const.NO);
        }else {
            //已经获取过，且未失效
            return getInstanceByCheckId(checkId);
        }

    }

    /**
     * 获取下载的校验码对象
     * @param userOpenId
     * @param objectType
     * @param objectId
     * @return
     */
    public static CheckCode getDownloadInstance(String userOpenId,String objectType,String objectId){
        String key=DOWNLOAD_PREFIX_KEY+objectId;
        String checkId=JedisUtils.get(key);
        if (StringUtils.isBlank(checkId)){
            checkId= IdGenUtils.uuid().replace("-","");
            JedisUtils.hset(checkId, "userOpenId", userOpenId);
            JedisUtils.hset(checkId,"objectType",objectType);
            JedisUtils.hset(checkId,"objectId",objectId);
            JedisUtils.hset(checkId,"purpose",Const.PURPOSE_TYPE.DOWNLOAD);
            JedisUtils.hset(checkId,"fileIsUpload",Const.NO+"");
            JedisUtils.setex(key,Const.INVITE_CODE_VALID_TIME*60,checkId);  //设置过期时间15分钟
            JedisUtils.expire(checkId,Const.INVITE_CODE_VALID_TIME*60); //设置过期时间15分钟
            return new CheckCode(userOpenId, objectType, objectId, Const.PURPOSE_TYPE.DOWNLOAD,checkId,Const.NO);
        }else {
            return getInstanceByCheckId(checkId);
        }
    }

    /**
     * 消费校验码，返回一个校验码对象,一个校验码只能被消费一次
     * @param checkId
     * @return
     */
    public static CheckCode costCheckCode(String checkId){
        String userOpenId=JedisUtils.hget(checkId,"userOpenId");
        if (StringUtils.isBlank(userOpenId)){
            throw new MyException(HttpResponseEnum.PROHIBIT,"校验码无效");
        }
        String objectType=JedisUtils.hget(checkId,"objectType");
        String objectId=JedisUtils.hget(checkId,"objectId");
        String purpose=JedisUtils.hget(checkId,"purpose");
        String fileIsUpload=JedisUtils.hget(checkId,"fileIsUpload");
        int isUpload=(Const.YES+"").equals(fileIsUpload)?Const.YES:Const.NO;
        String key=Const.PURPOSE_TYPE.DOWNLOAD.equals(purpose)?DOWNLOAD_PREFIX_KEY+objectId:UPLOAD_PREFIX_KEY+objectId;
        if (!JedisUtils.del(key)){
            logger.error("del key : {} error",key);
            throw new MyException(HttpResponseEnum.INTERNAL_SERVER_ERROR);
        }
        if (!JedisUtils.del(checkId)){
            logger.error("del key : {} error",checkId);
            throw new MyException(HttpResponseEnum.INTERNAL_SERVER_ERROR);
        }
        return new CheckCode(userOpenId,objectType,objectId,purpose,checkId,isUpload);
    }

    /**
     * 获取校验码实例
     * @param checkId
     * @return
     */
    public static CheckCode getInstanceByCheckId(String checkId){
        String userOpenId=JedisUtils.hget(checkId,"userOpenId");
        if (StringUtils.isBlank(userOpenId)){
            throw new MyException(HttpResponseEnum.PROHIBIT,"校验码无效");
        }
        String objectType=JedisUtils.hget(checkId,"objectType");
        String objectId=JedisUtils.hget(checkId,"objectId");
        String purpose=JedisUtils.hget(checkId,"purpose");
        String fileIsUpload=JedisUtils.hget(checkId,"fileIsUpload");
        int isUpload=(Const.YES+"").equals(fileIsUpload)?Const.YES:Const.NO;
        return new CheckCode(userOpenId,objectType,objectId,purpose,checkId,isUpload);
    }
}
