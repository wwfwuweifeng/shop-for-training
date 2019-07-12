package top.wwf.common.utils;

import org.apache.commons.lang3.StringUtils;
import top.wwf.common.base.DateYMDHMSJsonSerializer;
import top.wwf.common.consts.Const;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
* @Description:    用于生成唯一性ID
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019/1/18 13:52
*/
public class IdGenUtils {

    /**
     * 32位的唯一字符串，中间用 - 分割，用于生成唯一的文件名
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 生成唯一邀请码,11位
     * 规则：maxId+0+年份+随机数补至11位
     * @return
     */
    public static String generateRegisterCode() {
        Long maxId=JedisUtils.incr(Const.USER_MAX_ID_KEY);
        StringBuilder resultID=new StringBuilder(maxId.toString()).append(0);
        Random random=new Random();
        resultID.append(DateYMDHMSJsonSerializer.dateFormatToNum.format(new Date()).substring(0,4));
        while (resultID.length()<11){
            resultID.append(random.nextInt(9));
        }
        return resultID.toString();
    }

    /**
     * 生成唯一订单编号20位
     * 规则 年月日+33+maxId+9+随机数0-8补满20
     * @return
     */
    public static String generateOrderId() {
        Long maxId=JedisUtils.incr(Const.ORDER_MAX_ID);
        StringBuilder resultID=new StringBuilder(DateYMDHMSJsonSerializer.dateFormatToNum.format(new Date()).substring(0,8));
        resultID.append(33).append(maxId).append(9);
        Random random=new Random();
        while (resultID.length()<20){
            resultID.append(random.nextInt(8));
        }
        return resultID.toString();
    }

    public static void main(String[] args) {

    }
}


