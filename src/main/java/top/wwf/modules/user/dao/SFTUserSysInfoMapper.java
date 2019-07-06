package top.wwf.modules.user.dao;

import top.wwf.modules.user.entity.SFTUserSysInfo;

public interface SFTUserSysInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SFTUserSysInfo record);

    int insertSelective(SFTUserSysInfo record);

    SFTUserSysInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SFTUserSysInfo record);

    int updateByPrimaryKey(SFTUserSysInfo record);
}