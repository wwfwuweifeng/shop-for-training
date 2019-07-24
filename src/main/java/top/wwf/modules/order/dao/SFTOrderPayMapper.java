package top.wwf.modules.order.dao;

import top.wwf.modules.order.entity.SFTOrderPay;

public interface SFTOrderPayMapper {

    int insertSelective(SFTOrderPay record);

    SFTOrderPay selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SFTOrderPay record);

}