package top.wwf.common.base;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.wwf.common.consts.Const;
import top.wwf.common.consts.HttpResponseEnum;
import top.wwf.common.exception.MyException;
import top.wwf.common.utils.JedisUtils;


/**
* @Description:    邀请码对象
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-03-13 16:15
*/
public class InvitationCode {
    private static Logger logger= LoggerFactory.getLogger(InvitationCode.class);
    private String code;
    private int userRole;
    private String contractId;

    private InvitationCode(String code, int userRole, String contractId) {
        this.code = code;
        this.userRole = userRole;
        this.contractId = contractId;
    }

    public String getCode() {
        return code;
    }

    public int getUserRole() {
        return userRole;
    }

    public String getContractId() {
        return contractId;
    }

    /**
     * 消费字符串邀请码
     * @param invitationCode 传进来的字符串邀请码
     * @return 若消费成功，则返回生成的邀请码对象，若失败，则抛出异常
     */
//    public static InvitationCode costInvitationCode(String invitationCode){
//        String contractId= JedisUtils.get(invitationCode);
//        String code=invitationCode;
//        int userRole=0;
//        if (StringUtils.isBlank(contractId)){
//            throw new MyException(HttpResponseEnum.PROHIBIT,"邀请码已失效");
//        }
//        if (!JedisUtils.del(invitationCode)){
//            logger.error("删除邀请码：{} 失败",invitationCode);
//            throw new MyException(HttpResponseEnum.INTERNAL_SERVER_ERROR);
//        }
//
//        if (invitationCode.equals(JedisUtils.hget(contractId, Const.SIDE.PART_A.getValue()))){
//            //是甲方的邀请码
//            userRole=Const.SIDE.PART_A.getKey();
//            //不删除，最多30分钟后自动删除
//            JedisUtils.hset(contractId,Const.SIDE.PART_A.getValue(),StringUtils.EMPTY);
//        }else if (invitationCode.equals(JedisUtils.hget(contractId, Const.SIDE.PART_B.getValue()))){
//            //是乙方的邀请码
//            userRole=Const.SIDE.PART_B.getKey();
//            //不删除，最多30分钟后自动删除
//            JedisUtils.hset(contractId,Const.SIDE.PART_B.getValue(),StringUtils.EMPTY);
//        }else {
//            logger.error("邀请码：{}，合同编号：{} 找不到对应用户身份");
//            throw new MyException(HttpResponseEnum.INTERNAL_SERVER_ERROR);
//        }
//        return new InvitationCode(code,userRole,contractId);
//    }
}
