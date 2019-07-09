package top.wwf.modules.order.dao;

import top.wwf.modules.order.entity.SFTOrderItems;

public interface SFTOrderItemsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SFTOrderItems record);

    int insertSelective(SFTOrderItems record);

    SFTOrderItems selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SFTOrderItems record);

    int updateByPrimaryKey(SFTOrderItems record);
}