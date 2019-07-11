package top.wwf.modules.user.dao;

import top.wwf.modules.user.entity.SFTUserSysInfo;

import java.util.List;

public interface SFTUserSysInfoMapper {

    int insertSelective(SFTUserSysInfo record);

    SFTUserSysInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SFTUserSysInfo record);


    SFTUserSysInfo selectByOpenId(String openId);

    SFTUserSysInfo selectByAccountAndPassword(String account, String password);

    SFTUserSysInfo selectByRegisterCode(String registerCode);

    List<SFTUserSysInfo> selectUserListWithoutManager(int managerRole);
}