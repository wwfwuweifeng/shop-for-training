package top.wwf.modules.order.dao;

import top.wwf.modules.order.entity.SFTOrderItem;

public interface SFTOrderItemsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SFTOrderItem record);

    int insertSelective(SFTOrderItem record);

    SFTOrderItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SFTOrderItem record);

    int updateByPrimaryKey(SFTOrderItem record);
}