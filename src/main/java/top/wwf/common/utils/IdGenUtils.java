package top.wwf.common.utils;

import org.apache.commons.lang3.StringUtils;
import top.wwf.common.consts.Const;

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
    public static String uuid(){
        return UUID.randomUUID().toString();
    }

    /**
     * 获取8位的邀请码，具有局部的唯一性（即：保证不会同时存在两个一样的邀请码）
     * @return
     */
    public static String generateInvitationId(){
        String tempId=null;
        String resultId=null;
        do {
            tempId=uuid();
            resultId=EncryptionUtils.md5To8(tempId);
        }while (!JedisUtils.setnx(resultId, StringUtils.EMPTY));    //直到设置成功
        return resultId;
    }

    /**
     * 获取8位的校验码，具有局部的唯一性（即：保证不会同时存在两个一样的校验码）
     * @return
     */
    public static String generateCheckId(){
        String tempId=null;
        String resultId=null;
        Random random=new Random();
        do {
            tempId=uuid();
            resultId=random.nextInt(9)+EncryptionUtils.md5To8(tempId)+random.nextInt(9);
        }while (!JedisUtils.setnx(resultId, StringUtils.EMPTY));    //直到设置成功
        return resultId;
    }

    /**
     * 生成唯一性的16位合同编号
     * @return
     */
    public static String generateContractId(){
//        Long seedId=JedisUtils.incr(Const.CONTRACT_MAX_ID_KEY);
//        return generateId(seedId, Const.CONTRACT_ID_PREFIX);
        return null;
    }


    /**
     * 用于生成唯一性的16位编号
     * @param seedId 用于生成编号的种子Id
     * @param idPrefix
     * @return
     */
    private static String generateId(long seedId,String idPrefix){
        StringBuilder resultID=new StringBuilder(idPrefix);
        String strId=seedId+"9";
        Random random=new Random();
        for (int i=0;i<strId.length()/4;i++){
            resultID.append(" ");
            resultID.append(strId.substring(i*4,(i+1)*4));
        }
        resultID.append(" ");
        resultID.append(strId.substring((strId.length()/4)*4));
        while (resultID.length()<19){
            if ((resultID.length()+1)%5==0){
                resultID.append(" ");
            }else{
                resultID.append(random.nextInt(8));
            }
        }
        return resultID.toString();
    }
    

    public static void main(String[] args){
//        System.out.println(contractId(3));
//        System.out.println(contractId(3241));
//        System.out.println(contractId(34321));
//        System.out.println(contractId(12345678));
//        System.out.println(contractId(1322234566));
//        System.out.println(contractId(231231311323131231L));
    }

    public static String generateRegisterCode() {

        return null;
    }

    /**
     * 订单编号
     * @return
     */
    public static String generateOrderId() {
        return null;
    }
}
