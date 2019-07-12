package top.wwf.modules.user.service;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.wwf.common.base.DateYMDHMSJsonSerializer;
import top.wwf.common.base.MySession;
import top.wwf.common.base.OpenIdResult;
import top.wwf.common.consts.Const;
import top.wwf.common.consts.GoodsConst;
import top.wwf.common.consts.HttpResponseEnum;
import top.wwf.common.consts.OrderConst;
import top.wwf.common.exception.MyException;
import top.wwf.common.page.PageBean;
import top.wwf.common.utils.IdGenUtils;
import top.wwf.common.utils.JedisUtils;
import top.wwf.common.utils.WeChartUtils;
import top.wwf.modules.goods.dao.enhance.GoodsDao;
import top.wwf.modules.order.dao.enhance.OrderDao;
import top.wwf.modules.order.entity.SFTOrder;
import top.wwf.modules.user.dao.enhance.UserDao;

import top.wwf.modules.user.dto.UserInfoDTO;
import top.wwf.modules.user.entity.SFTUserPersonalInfo;
import top.wwf.modules.user.entity.SFTUserSysInfo;
import top.wwf.modules.user.vo.RegisterUserVO;
import top.wwf.modules.user.vo.UserInfoVO;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

/**
* @Description:    TODO
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-08 15:17
*/
@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private GoodsDao goodsDao;


    /**
     * 当该bean初始化时，自动执行
     * 从数据库当中读取userSys表中Id的最大值，存入redis当中
     */
    @PostConstruct
    private void initUserMaxIdToRedis(){
        Long nowMaxId=userDao.getUserSysInfoNowMaxId();
        nowMaxId = null==nowMaxId?0:nowMaxId;
        JedisUtils.set(Const.USER_MAX_ID_KEY, nowMaxId.toString());
    }

    /**
     * 当前的登录逻辑，允许一个账号，多处同时登录（一个userId可能同时对应多个token）
     * @param code
     * @param account
     * @param password
     * @return
     */
    public UserInfoVO loginServer(String code, String account, String password) {
        UserInfoVO userInfoVO =new UserInfoVO();
        String token=IdGenUtils.uuid().replace("-","");
        if (StringUtils.isNotBlank(code)){  //微信小程序端登录
            OpenIdResult openIdResult=WeChartUtils.getOpenId(code);
            SFTUserSysInfo userSysInfo=userDao.getUserSysInfoByOpenId(openIdResult.getOpenid());
            if (null==userSysInfo){ //未注册
                userInfoVO.setToken(token);
                userInfoVO.setIsRegister(Const.NO);
                //对于未注册的用户，存储的是openId
                JedisUtils.setex(token,Const.SESSION_TIMEOUT,openIdResult.getOpenid());
            }else { //已注册
                //生成token,并保存至redis中
                JedisUtils.setex(token,Const.SESSION_TIMEOUT,userSysInfo.getUserId());
                saveUserSysInfoToCache(userSysInfo);    //保存部分信息至缓存
                userInfoVO.setIsRegister(Const.YES);
                userInfoVO.setUserName(userSysInfo.getUserName());
                userInfoVO.setToken(token);
                userInfoVO.setUserSysInfo(userSysInfo);
                SFTUserPersonalInfo userPersonalInfo=userDao.getUserPersonalInfoByUserId(userSysInfo.getUserId());
                //此处就不进行userPersonalInfo是否为null的判断了
                userInfoVO.setUserPersonalInfo(userPersonalInfo);
            }
        }else if (StringUtils.isNotBlank(account)&&StringUtils.isNotBlank(password)){//网页端登录，目前只限管理员登录，且管理员只有一个账号
            SFTUserSysInfo userSysInfo=userDao.getUserSysInfoByAccountAndPassword(account,password);
            if (null==userSysInfo || userSysInfo.getUserRole()!=Const.USER_ROLE.MANAGER.getKey()){
                throw new MyException(HttpResponseEnum.UNAUTHORIZED);   //只有管理员能使用此方式登录
            }
            JedisUtils.setex(token,Const.SESSION_TIMEOUT,userSysInfo.getUserId());
            saveUserSysInfoToCache(userSysInfo);
            userInfoVO.setUserName(userSysInfo.getUserName());
            userInfoVO.setToken(token);
        }else {throw new MyException(HttpResponseEnum.ERRONEOUS_REQUEST);}

        return userInfoVO;
    }

    /**
     * 将用户的部分常用与系统相关的信息保存至缓存中，以userId作为map结构的外key，有效时长与session时长一致
     * @param userSysInfo
     */
    private void saveUserSysInfoToCache(SFTUserSysInfo userSysInfo){
//        JedisUtils.hset(userSysInfo.getUserId(),"userId",userSysInfo.getUserId());
        JedisUtils.hset(userSysInfo.getUserId(),"role",userSysInfo.getUserRole());
        if (userSysInfo.getUserRole()== Const.USER_ROLE.SELLER.getKey()) {
            JedisUtils.hset(userSysInfo.getUserId(), "shopId", userSysInfo.getShopId());
        }
//        JedisUtils.hset(userSysInfo.getUserId(),"shopName",userSysInfo.getShopName());
        JedisUtils.expire(userSysInfo.getUserId(),Const.SESSION_TIMEOUT);
    }

    /**
     * @param session
     * @param userInfoDTO
     * @return
     */
    @Transactional
    public SFTUserPersonalInfo editUserInfo(MySession session, UserInfoDTO userInfoDTO) {

        SFTUserPersonalInfo userPersonalInfo=userDao.getUserPersonalInfoByUserId(session.getUserId());

        if (userInfoDTO.getIsEditReceiverAddress()==Const.YES){//修改收货地址

            if (StringUtils.isBlank(userInfoDTO.getReceiverName())||StringUtils.isBlank(userInfoDTO.getReceiverTel())||
            StringUtils.isBlank(userInfoDTO.getReceiverAddressSimple())||StringUtils.isBlank(userInfoDTO.getReceiverAddressDetail())){
                throw new MyException(HttpResponseEnum.ERRONEOUS_REQUEST);
            }else {
                userPersonalInfo.setUserId(session.getUserId());
                userPersonalInfo.setReceiverAddressDetail(userInfoDTO.getReceiverAddressDetail());
                userPersonalInfo.setReceiverAddressSimple(userInfoDTO.getReceiverAddressSimple());
                userPersonalInfo.setReceiverName(userInfoDTO.getReceiverName());
                userPersonalInfo.setReceiverTel(userInfoDTO.getReceiverTel());
                userPersonalInfo.setHaveReceiverAddress(Const.YES);
            }
        }else{//修改个人信息
            userPersonalInfo.setUserSex(Const.SEX.getKeyByDesc(userInfoDTO.getUserSex()));
            userPersonalInfo.setUserTel(userInfoDTO.getTelNum());
            userPersonalInfo.setUserEmail(userInfoDTO.getEmail());
            //上传的店铺名字段不为空
            if (StringUtils.isNotBlank(userInfoDTO.getShopName())){
                Const.USER_ROLE userRole=session.getUserRole();
                if (!userInfoDTO.getShopName().equals(userPersonalInfo.getShopName())){//店铺名不一致

                    if (!userRole.hasPower(Const.POWER.SELL)){//不具备售卖权限
                        throw new MyException(HttpResponseEnum.PROHIBIT,"权限不允许");
                    }else if (StringUtils.isNotBlank(userPersonalInfo.getShopName())){//店铺名已设置过
                        throw new MyException(HttpResponseEnum.PROHIBIT,"商铺名已设置过，无法修改");
                    }
                    if (null!=userDao.getUserPersonalInfoByShopName(userInfoDTO.getShopName())){//商店名已经被使用
                        throw new MyException(HttpResponseEnum.PROHIBIT,"商铺名已经被使用");
                    }
                    userPersonalInfo.setShopName(userInfoDTO.getShopName());
                }
            }
        }
        userDao.updateUserPersonalInfoByUserId(userPersonalInfo);
        return userPersonalInfo;
    }

    @Transactional
    public RegisterUserVO addUserByManager(MySession session, int role, String userName) {
        Const.USER_ROLE userRole=session.getUserRole();
        if (userRole!= Const.USER_ROLE.MANAGER){
            throw new MyException(HttpResponseEnum.PROHIBIT,"权限不允许");
        }
        if (StringUtils.isBlank(userName)||role==Const.USER_ROLE.MANAGER.getKey()){
            throw new MyException(HttpResponseEnum.ERRONEOUS_REQUEST);
        }
        SFTUserSysInfo userSysInfo=new SFTUserSysInfo();
        String registerCode=IdGenUtils.generateRegisterCode();
        userSysInfo.setRegisterCode(registerCode);
        userSysInfo.setUserName(userName);
        userSysInfo.setUserRole(role);
        if (Const.USER_ROLE.getRoleByKey(role)==Const.USER_ROLE.SELLER){
            userSysInfo.setShopId(IdGenUtils.uuid().replace("-",""));
        }

        RegisterUserVO registerUserVO =new RegisterUserVO();
        registerUserVO.setCodeUsedTime("尚未使用");
        registerUserVO.setRegisterCode(registerCode);
        registerUserVO.setUserName(userName);
        registerUserVO.setUserRole(Const.USER_ROLE.getRoleByKey(role).getDesc());//顺带检验数据合法性

        userDao.addUserSysInfo(userSysInfo);
        return registerUserVO;
    }

    @Transactional
    public UserInfoVO registerByCode(MySession session, String registerCode) {
        SFTUserSysInfo userSysInfo=userDao.getUserSysInfoByRegisterCode(registerCode);
        if (null==userSysInfo){
            throw new MyException(HttpResponseEnum.PROHIBIT,"无效注册码");
        }else if (StringUtils.isNotBlank(userSysInfo.getOpenId())){
            throw new MyException(HttpResponseEnum.PROHIBIT,"注册码已被使用");
        }
        String openId=session.getUserId();  //此处较特殊，实际获取到的是用户的openId
        userSysInfo.setUserId(IdGenUtils.uuid().replace("-",""));
        userSysInfo.setOpenId(openId);
        userSysInfo.setCodeUsedTime(DateYMDHMSJsonSerializer.dateFormat.format(new Date()));
        userDao.updateUserSysInfoByPrimaryKey(userSysInfo);

        SFTUserPersonalInfo userPersonalInfo=new SFTUserPersonalInfo();
        userPersonalInfo.setUserId(userSysInfo.getUserId());
        userPersonalInfo.setHaveReceiverAddress(Const.NO);
        userDao.addUserPersonalInfo(userPersonalInfo);

        UserInfoVO userInfoVO=new UserInfoVO();
        userInfoVO.setToken(session.getToken());
        userInfoVO.setIsRegister(Const.YES);
        userInfoVO.setUserName(userSysInfo.getUserName());
        userInfoVO.setUserPersonalInfo(userPersonalInfo);
        userInfoVO.setUserSysInfo(userSysInfo);

        return userInfoVO;
    }

    /**
     * 修改后的权限，需要用户重新登录后，才会生效！！！
     * @param session
     * @param registerCode
     * @param userName
     * @param role
     * @return
     */
    public RegisterUserVO editUser(MySession session,String registerCode, String userName, int role) {
        if (StringUtils.isBlank(registerCode)){
            throw new MyException(HttpResponseEnum.ERRONEOUS_REQUEST);
        } else if (session.getUserRole()!=Const.USER_ROLE.MANAGER){
            throw new MyException(HttpResponseEnum.PROHIBIT,"权限不允许");
        }else if (StringUtils.isBlank(userName)){
            throw new MyException(HttpResponseEnum.ERRONEOUS_REQUEST);
        }else if (Const.USER_ROLE.getRoleByKey(role)==Const.USER_ROLE.MANAGER){
            throw new MyException(HttpResponseEnum.PROHIBIT,"参数非法");
        }
        SFTUserSysInfo userSysInfo=userDao.getUserSysInfoByRegisterCode(registerCode);
        if (null==userSysInfo){
            throw new MyException(HttpResponseEnum.PROHIBIT,"参数有误");
        }
        //当前注册码用户的角色
        Const.USER_ROLE userRole=Const.USER_ROLE.getRoleByKey(role);
        userSysInfo.setUserName(userName);
        userSysInfo.setUserRole(role);
        if (StringUtils.isBlank(userSysInfo.getShopId())&&userRole==Const.USER_ROLE.SELLER){
            //是卖家，且还未分配shopId
            userSysInfo.setShopId(IdGenUtils.uuid().replace("-",""));
        }else if (Const.USER_ROLE.getRoleByKey(userSysInfo.getUserRole())== Const.USER_ROLE.SELLER && userRole==Const.USER_ROLE.BUYER){
            //由卖家切换为买家，则需要判断是否有未结束的销售订单
            List<SFTOrder> orderList=orderDao.getNotFinishSellOrderListByShopId(userSysInfo.getShopId(),OrderConst.STATE_FOR_SELLER.ALREADY_DEAL.getKey(),OrderConst.STATE_FOR_SELLER.ALREADY_CANCEL.getKey());
            if (null!=orderList&&orderList.size()>0){
                //还有未结束的销售订单
                throw new MyException(HttpResponseEnum.PROHIBIT,"该用户仍有未结束的销售订单");
            }
        }
        userDao.updateUserSysInfoByPrimaryKey(userSysInfo);
        RegisterUserVO registerUserVO=new RegisterUserVO();
        registerUserVO.setRegisterCode(registerCode);
        registerUserVO.setUserName(userName);
        registerUserVO.setUserRole(userRole.getDesc());
        registerUserVO.setCodeUsedTime(userSysInfo.getCodeUsedTime());

        return registerUserVO;
    }

    /**
     * 删除用户，只进行逻辑删除，逻辑删除前，需检验用户是否有未结束的订单
     * 如果删除的是商家，则所以在售商品自动下架
     * @param registerCode
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Transactional
    public PageBean<RegisterUserVO> delUser(MySession session,String registerCode, int pageNum, int pageSize) {

        if (StringUtils.isBlank(registerCode)){
            throw new MyException(HttpResponseEnum.ERRONEOUS_REQUEST);
        }else if (session.getUserRole()!=Const.USER_ROLE.MANAGER){
            throw new MyException(HttpResponseEnum.PROHIBIT,"权限不允许");
        }

        SFTUserSysInfo userSysInfo=userDao.getUserSysInfoByRegisterCode(registerCode);
        if (null==userSysInfo){
            throw new MyException(HttpResponseEnum.PROHIBIT);
        }
        if (session.getUserId().equals(userSysInfo.getUserId())){
            //当前删除的用户是管理员自己，不允许
            throw new MyException(HttpResponseEnum.PROHIBIT,"操作不允许");

        } else if (StringUtils.isBlank(userSysInfo.getUserId())){
            //还未绑定用户，直接逻辑删除用户的系统信息即可
            userSysInfo.setIsDelete(Const.YES);
            userDao.updateUserSysInfoByPrimaryKey(userSysInfo);
        }else{
            //检验是否有作为买家，仍未结束的订单
            List<SFTOrder> orderList=orderDao.getNotFinishBuyOrderListByUserId(userSysInfo.getUserId(),OrderConst.STATE_FOR_SELLER.ALREADY_DEAL.getKey(),OrderConst.STATE_FOR_SELLER.ALREADY_CANCEL.getKey());
            if (null!=orderList&&orderList.size()>0){
                throw new MyException(HttpResponseEnum.PROHIBIT,"该用户仍有未结束的订单，无法删除该用户");
            }
            if (Const.USER_ROLE.getRoleByKey(userSysInfo.getUserRole())== Const.USER_ROLE.SELLER){
                //当前用户拥有销售权利，检验是否有作为卖家，仍未结束的订单
                orderList=orderDao.getNotFinishSellOrderListByShopId(userSysInfo.getShopId(), OrderConst.STATE_FOR_SELLER.ALREADY_DEAL.getKey(),OrderConst.STATE_FOR_SELLER.ALREADY_CANCEL.getKey());
                if (null!=orderList&&orderList.size()>0){
                    //还有未结束的销售订单
                    throw new MyException(HttpResponseEnum.PROHIBIT,"该用户仍有未结束的销售订单，无法删除该用户");
                }
            }

            //通过检验，该用户可以进行删除，逻辑删除该用户的sysInfo和personalInfo
            userSysInfo.setIsDelete(Const.YES);
            userDao.updateUserSysInfoByPrimaryKey(userSysInfo);
            SFTUserPersonalInfo userPersonalInfo=new SFTUserPersonalInfo();
            userPersonalInfo.setUserId(userSysInfo.getUserId());
            userPersonalInfo.setIsDelete(Const.YES);
            userDao.updateUserPersonalInfoByUserId(userPersonalInfo);
            //下架该用户销售的所有商品
            goodsDao.lowerShelfGoodsByShopId(GoodsConst.STATE.LOWER_SHELF.getKey(), userSysInfo.getShopId());

        }

        return getUserList(session,pageNum,pageSize);
    }

    /**
     * 获取用户列表，管理员自己不会出现在列表中
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @SuppressWarnings("unchecked")
    public PageBean<RegisterUserVO> getUserList(MySession session, int pageNum, int pageSize) {
        if (session.getUserRole()!=Const.USER_ROLE.MANAGER){
            throw new MyException(HttpResponseEnum.PROHIBIT,"权限不允许");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<RegisterUserVO> registerUserVOList= Lists.newLinkedList();
        List<SFTUserSysInfo> userSysInfoList= userDao.getUserListWithoutManager(Const.USER_ROLE.MANAGER.getKey());
        RegisterUserVO registerUserVO;
        for (SFTUserSysInfo userSysInfo:userSysInfoList){
            registerUserVO=new RegisterUserVO();
            registerUserVO.setUserRole(Const.USER_ROLE.getRoleByKey(userSysInfo.getUserRole()).getDesc());
            registerUserVO.setCodeUsedTime(userSysInfo.getCodeUsedTime());
            registerUserVO.setUserName(userSysInfo.getUserName());
            registerUserVO.setRegisterCode(registerUserVO.getRegisterCode());
            registerUserVOList.add(registerUserVO);
        }
        return PageBean.createByPage(registerUserVOList);

    }
}
