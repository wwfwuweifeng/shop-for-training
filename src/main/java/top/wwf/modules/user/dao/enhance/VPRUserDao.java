package top.wwf.modules.user.dao.enhance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.wwf.modules.user.dao.SFTUserSysInfoMapper;
import top.wwf.modules.user.dao.SFTUserPersonalInfoMapper;

/**
* @Description:    所有有关用户的dao层操作，均通过此增强dao
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019/2/20 10:27
*/
@Repository
public class VPRUserDao {
    @Autowired
    private SFTUserPersonalInfoMapper userPersonalInfoMapper;

    @Autowired
    private SFTUserSysInfoMapper userSysInfoMapper;
}
