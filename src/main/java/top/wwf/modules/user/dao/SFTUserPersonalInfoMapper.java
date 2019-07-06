package top.wwf.modules.user.dao;

import top.wwf.modules.user.entity.SFTUserPersonalInfo;

public interface SFTUserPersonalInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SFTUserPersonalInfo record);

    int insertSelective(SFTUserPersonalInfo record);

    SFTUserPersonalInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SFTUserPersonalInfo record);

    int updateByPrimaryKey(SFTUserPersonalInfo record);
}