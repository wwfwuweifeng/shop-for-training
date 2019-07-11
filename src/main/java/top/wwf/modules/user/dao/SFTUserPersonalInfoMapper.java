package top.wwf.modules.user.dao;

import top.wwf.modules.user.entity.SFTUserPersonalInfo;
import top.wwf.modules.user.entity.SFTUserSysInfo;

public interface SFTUserPersonalInfoMapper {

    int insertSelective(SFTUserPersonalInfo record);

    SFTUserPersonalInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SFTUserPersonalInfo record);


    SFTUserPersonalInfo selectByUserId(String userId);

    void updateByUserId(SFTUserPersonalInfo userPersonalInfo);

    SFTUserPersonalInfo selectByShopName(String shopName);
}