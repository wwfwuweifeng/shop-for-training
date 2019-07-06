package top.wwf.modules.order.dao;

import top.wwf.modules.order.entity.SFTOrder;

public interface SFTOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SFTOrder record);

    int insertSelective(SFTOrder record);

    SFTOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SFTOrder record);

    int updateByPrimaryKey(SFTOrder record);
}