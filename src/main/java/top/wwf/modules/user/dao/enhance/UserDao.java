package top.wwf.modules.user.dao.enhance;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.wwf.modules.user.dao.SFTUserSysInfoMapper;
import top.wwf.modules.user.dao.SFTUserPersonalInfoMapper;
import top.wwf.modules.user.entity.SFTUserPersonalInfo;
import top.wwf.modules.user.entity.SFTUserSysInfo;

import java.util.List;

/**
* @Description:    所有有关用户的dao层操作，均通过此增强dao
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019/2/20 10:27
*/
@Repository
public class UserDao {
    @Autowired
    private SFTUserPersonalInfoMapper userPersonalInfoMapper;

    @Autowired
    private SFTUserSysInfoMapper userSysInfoMapper;

    public SFTUserSysInfo getUserSysInfoByOpenId(String openId) {
        return userSysInfoMapper.selectByOpenId(openId);
    }

    public SFTUserPersonalInfo getUserPersonalInfoByUserId(String userId) {
        return userPersonalInfoMapper.selectByUserId(userId);
    }

    public SFTUserSysInfo getUserSysInfoByAccountAndPassword(String account, String password) {
        return userSysInfoMapper.selectByAccountAndPassword(account,password);
    }


    public void updateUserPersonalInfoByUserId(SFTUserPersonalInfo userPersonalInfo) {
        userPersonalInfoMapper.updateByUserId(userPersonalInfo);
    }

    public SFTUserPersonalInfo getUserPersonalInfoByShopName(String shopName) {
        return userPersonalInfoMapper.selectByShopName(shopName);
    }

    public void addUserSysInfo(SFTUserSysInfo userSysInfo) {
        userSysInfoMapper.insertSelective(userSysInfo);
    }

    public SFTUserSysInfo getUserSysInfoByRegisterCode(String registerCode) {
        return userSysInfoMapper.selectByRegisterCode(registerCode);
    }

    public void updateUserSysInfoByPrimaryKey(SFTUserSysInfo userSysInfo) {
        userSysInfoMapper.updateByPrimaryKeySelective(userSysInfo);
    }

    public void addUserPersonalInfo(SFTUserPersonalInfo userPersonalInfo) {
        userPersonalInfoMapper.insertSelective(userPersonalInfo);
    }

    public Long getUserSysInfoNowMaxId() {
        return userSysInfoMapper.selectMaxId();
    }

    //记得isDelete的字段设为0
    public List<SFTUserSysInfo> getUserListByKeywordWithoutManager(int managerRole, String keyword) {
        if (StringUtils.isBlank(keyword)){
            keyword=null;
        }
        return userSysInfoMapper.selectUserListByKeywordWithoutManager(managerRole,keyword);
    }
}
