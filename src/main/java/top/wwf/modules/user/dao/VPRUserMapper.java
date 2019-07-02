package top.wwf.modules.user.dao;

import top.wwf.modules.user.entity.VPRUser;

/**
* @Description:    TODO
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-04-09 18:55
*/
public interface VPRUserMapper {

    int insertSelective(VPRUser record);

    VPRUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VPRUser record);

    VPRUser selectByOpenId(String openId);

    VPRUser selectByToken(String token);
}