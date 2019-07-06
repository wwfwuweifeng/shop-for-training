package top.wwf.modules.cart.dao;

import top.wwf.modules.cart.entity.SFTCart;

public interface SFTCartMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SFTCart record);

    int insertSelective(SFTCart record);

    SFTCart selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SFTCart record);

    int updateByPrimaryKey(SFTCart record);
}