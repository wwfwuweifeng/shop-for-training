package top.wwf.modules.goods.dao;

import top.wwf.modules.goods.entity.SFTGoodsParam;

public interface SFTGoodsParamMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SFTGoodsParam record);

    int insertSelective(SFTGoodsParam record);

    SFTGoodsParam selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SFTGoodsParam record);

    int updateByPrimaryKey(SFTGoodsParam record);
}