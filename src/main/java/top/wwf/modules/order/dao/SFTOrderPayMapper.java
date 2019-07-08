package top.wwf.modules.order.dao;

import top.wwf.modules.order.entity.SFTOrderPay;

public interface SFTOrderPayMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SFTOrderPay record);

    int insertSelective(SFTOrderPay record);

    SFTOrderPay selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SFTOrderPay record);

    int updateByPrimaryKey(SFTOrderPay record);
}