package top.wwf.modules.user.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.wwf.common.base.GlobalConfig;
import top.wwf.common.base.MySession;
import top.wwf.common.base.OpenIdResult;
import top.wwf.common.consts.Const;
import top.wwf.common.consts.HttpResponseEnum;
import top.wwf.common.exception.MyException;
import top.wwf.common.utils.IdGenUtils;
import top.wwf.common.utils.JedisUtils;
import top.wwf.common.utils.WeChartUtils;
import top.wwf.modules.contract.dao.enhance.VPRContractDao;
import top.wwf.modules.contract.entity.VPRContractAscriptionInfo;
import top.wwf.modules.contract.entity.VPRContractInfo;
import top.wwf.modules.user.dao.enhance.VPRUserDao;
import top.wwf.modules.user.entity.VPRUser;
import top.wwf.modules.user.vo.LoginResultVO;
import top.wwf.modules.user.vo.UserDetailedInfoVO;

/**
* @Description:    与用户相关的服务
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019/2/20 13:42
*/
@Service
public class UserService {

    @Autowired
    private VPRUserDao userDao;
    @Autowired
    private VPRContractDao contractDao;


    public LoginResultVO loginServer(String code) {
        OpenIdResult  openIdResult  = WeChartUtils.getOpenId(code);
        VPRUser       user          =userDao.getUserByOpenId(openIdResult.getOpenid());
        MySession     session       =createSession(openIdResult.getOpenid());
        LoginResultVO loginResultVO =new LoginResultVO();
        if (null==user){    //为新用户，则自动注册
            user=new VPRUser();
            user.setOpenId(openIdResult.getOpenid());
            user.setSessionKey(openIdResult.getSession_key());
            user.setToken(session.getToken());
            user.setIsPerfectInfo(Const.NO);
//            user.setWxName("用户未授权");
            userDao.addOneUser(user);
            loginResultVO.setUserName(StringUtils.EMPTY);
            loginResultVO.setIsPerfectInfo(Const.NO);   //是否已完善信息
        }else {
            if (StringUtils.isNotBlank(user.getToken())){//确保过期的token已经被移除
                JedisUtils.del(Const.TOKEN_PREFIX_KEY+user.getToken());
            }

            //更新登录信息
            user.setSessionKey(openIdResult.getSession_key());
            user.setToken(session.getToken());
            userDao.updateUserByPrimaryKeySelective(user);

            loginResultVO.setUserName(
                    null==user.getUserName()?StringUtils.EMPTY:user.getUserName()
            );
            loginResultVO.setIsPerfectInfo(user.getIsPerfectInfo());
        }

        loginResultVO.setToken(session.getToken());

        return loginResultVO;
    }

    private MySession createSession(String userOpenId){
        MySession session=new MySession();
        session.setToken(IdGenUtils.uuid().replace("-",""));
        session.setUserOpenId(userOpenId);
        JedisUtils.setex(Const.TOKEN_PREFIX_KEY+session.getToken(),GlobalConfig.SESSION_TIMEOUT*60,userOpenId);
        return session;
    }

    /**
     * 获取用户的详细信息
     * @param userOpenId
     * @param wxName
     * @return
     */
    public UserDetailedInfoVO getUserDetailedInfo(String userOpenId, String wxName) {
        VPRUser user=userDao.getUserByOpenId(userOpenId);
        if (null==user){
            throw new MyException(HttpResponseEnum.UNAUTHORIZED,"用户不存在");
        }
        if (StringUtils.isNotEmpty(wxName)&&!wxName.equals("用户未授权")&&!wxName.equals(user.getWxName())) {
            //用户更新了微信名
            user.setWxName(wxName);
            userDao.updateUserByPrimaryKeySelective(user);
        }else if (StringUtils.isEmpty(user.getWxName())){
            user.setWxName("用户未授权");
        }

        UserDetailedInfoVO userDetailedInfoVO=new UserDetailedInfoVO();
        if (user.getIsPerfectInfo()==Const.NO){ //信息未补充过
            userDetailedInfoVO.setWxName(user.getWxName());
            userDetailedInfoVO.setUserName(StringUtils.EMPTY);
            userDetailedInfoVO.setAddress(StringUtils.EMPTY);
            userDetailedInfoVO.setEmail(StringUtils.EMPTY);
            userDetailedInfoVO.setIsAllowModify(Const.YES);
            userDetailedInfoVO.setTelNum(StringUtils.EMPTY);
            userDetailedInfoVO.setUserSex(StringUtils.EMPTY);
            return userDetailedInfoVO;
        }
        //已补充过信息
        userDetailedInfoVO.setWxName(user.getWxName());
        userDetailedInfoVO.setUserSex(Const.SEX.getDescByKey(user.getUserSex()));
        userDetailedInfoVO.setUserName(user.getUserName());
        userDetailedInfoVO.setIsAllowModify(Const.NO);
        userDetailedInfoVO.setAddress(
                null==user.getAddress()?StringUtils.EMPTY:user.getAddress()
        );
        userDetailedInfoVO.setTelNum(
                null==user.getTelNum()?StringUtils.EMPTY:user.getTelNum()
        );
        userDetailedInfoVO.setEmail(
                null==user.getEmail()?StringUtils.EMPTY:user.getEmail()
        );
        userDetailedInfoVO.setIsPerfectInfo(user.getIsPerfectInfo());
        return userDetailedInfoVO;
    }

    public UserDetailedInfoVO editUserInfo(String userOpenId, UserDetailedInfoVO userDetailedInfoVO) {
        VPRUser user=userDao.getUserByOpenId(userOpenId);
        if (null==user){
            throw new MyException(HttpResponseEnum.UNAUTHORIZED,"用户不存在");
        }
        if (StringUtils.isBlank(userDetailedInfoVO.getUserName())||StringUtils.isBlank(userDetailedInfoVO.getUserSex())){
            throw new MyException(HttpResponseEnum.ERRONEOUS_REQUEST,"用户名和性别不能为空");
        }
        if (user.getIsPerfectInfo()==Const.NO){ //初次进行设置
            user.setUserName(userDetailedInfoVO.getUserName());
            user.setUserSex(Const.SEX.getKeyByDesc(userDetailedInfoVO.getUserSex()));
            user.setIsPerfectInfo(Const.YES);
        }
        user.setTelNum(userDetailedInfoVO.getTelNum());
        user.setEmail(userDetailedInfoVO.getEmail());
        user.setAddress(userDetailedInfoVO.getAddress());

        userDao.updateUserByPrimaryKeySelective(user);

        userDetailedInfoVO.setUserName(user.getUserName());
        userDetailedInfoVO.setUserSex(Const.SEX.getDescByKey(user.getUserSex()));
        userDetailedInfoVO.setIsAllowModify(Const.NO);
        userDetailedInfoVO.setIsPerfectInfo(user.getIsPerfectInfo());
        return userDetailedInfoVO;
    }

    /**
     * 获取合同参与者的信息
     * @param userOpenId
     * @param contractId
     * @param role
     * @return
     */
    public UserDetailedInfoVO getDetailedInfoByContractIdAndRole(String userOpenId, String contractId, int role) {
        VPRContractAscriptionInfo ascriptionInfo=contractDao.getContractAscriptionInfoByOwnerUserIdAndContractId(userOpenId,contractId);
        if (null==ascriptionInfo){
            throw new MyException(HttpResponseEnum.PROHIBIT,"合同不存在");
        }
        VPRContractInfo contractInfo=contractDao.getContractInfoByPrimaryKey(ascriptionInfo.getContractInfoTableId());
        String roleUserOpenId=null;
        if (Const.USER_ROLE.PART_A.getKey()==role){//甲方
            roleUserOpenId=contractInfo.getPartAId();
        }else if (Const.USER_ROLE.PART_B.getKey()==role){//乙方
            roleUserOpenId=contractInfo.getPartBId();
        }else if (Const.USER_ROLE.PART_C.getKey()==role){//发起者，前端发请求时，将发起者的role值设为第三方
            roleUserOpenId=contractInfo.getInitiateUserId();
        }else {
            throw new MyException(HttpResponseEnum.PROHIBIT,"参数非法");
        }
        if (StringUtils.isBlank(roleUserOpenId)){
            throw new MyException(HttpResponseEnum.PROHIBIT,"未查询相关用户");
        }
        return getUserDetailedInfo(roleUserOpenId,null);
    }
}
