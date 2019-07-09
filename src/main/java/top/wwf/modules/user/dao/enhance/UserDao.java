package top.wwf.modules.user.dao.enhance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.wwf.modules.user.dao.SFTUserSysInfoMapper;
import top.wwf.modules.user.dao.SFTUserPersonalInfoMapper;
import top.wwf.modules.user.entity.SFTUserPersonalInfo;
import top.wwf.modules.user.entity.SFTUserSysInfo;
import top.wwf.modules.user.vo.RegisterUserVO;

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

    public SFTUserSysInfo getUserSysInfoByOpenId(String openid) {
        return null;
    }

    public SFTUserPersonalInfo getUserPersonalInfoByUserId(String userId) {
        return null;
    }

    public SFTUserSysInfo getUserSysInfoByAccountAndPassword(String account, String password) {
        return null;
    }



    public void updateUserPersonalInfoByUserId(SFTUserPersonalInfo userPersonalInfo) {

    }

    public SFTUserPersonalInfo getUserPersonalInfoByShopName(String shopName) {
        return null;
    }

    public void addUserSysInfo(SFTUserSysInfo userSysInfo) {

    }

    public SFTUserSysInfo getUserSysInfoByRegisterCode(String registerCode) {
        return null;
    }

    public void updateUserSysInfoByPrimaryKey(SFTUserSysInfo userSysInfo) {

    }

    public void addUserPersonalInfo(SFTUserPersonalInfo userPersonalInfo) {

    }

    //记得isDelete的字段设为0
    public List<RegisterUserVO> getUserListWithoutManager() {
        return null;
    }
}
