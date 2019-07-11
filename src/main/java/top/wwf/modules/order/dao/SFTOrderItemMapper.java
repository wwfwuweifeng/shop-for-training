package top.wwf.modules.order.dao;

import top.wwf.modules.order.entity.SFTOrderItem;

import java.util.List;

public interface SFTOrderItemMapper {




    int insertSelective(SFTOrderItem record);

    SFTOrderItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SFTOrderItem record);


    void insertOrderItems(List<SFTOrderItem> orderItemList);

    List<SFTOrderItem> selectOrderItemListByOrderId(String orderId);
}