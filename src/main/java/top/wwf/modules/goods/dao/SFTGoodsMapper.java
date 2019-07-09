package top.wwf.modules.goods.dao;

import top.wwf.modules.goods.entity.SFTGoods;

public interface SFTGoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SFTGoods record);

    int insertSelective(SFTGoods record);

    SFTGoods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SFTGoods record);

    int updateByPrimaryKey(SFTGoods record);
}