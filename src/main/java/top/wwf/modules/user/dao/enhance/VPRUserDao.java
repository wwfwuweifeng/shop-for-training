package top.wwf.modules.user.dao.enhance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.wwf.modules.user.dao.VPRUserMapper;
import top.wwf.modules.user.entity.VPRUser;

/**
* @Description:    所有有关用户的dao层操作，均通过此增强dao
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019/2/20 10:27
*/
@Repository
public class VPRUserDao {
    @Autowired
    private VPRUserMapper userMapper;

    public VPRUser getUserByOpenId(String openId) {
        return userMapper.selectByOpenId(openId);
    }

    public void addOneUser(VPRUser user) {
        userMapper.insertSelective(user);
    }

    public void updateUserByPrimaryKeySelective(VPRUser user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    public VPRUser getUserByToken(String token) {
        return userMapper.selectByToken(token);
    }
}
