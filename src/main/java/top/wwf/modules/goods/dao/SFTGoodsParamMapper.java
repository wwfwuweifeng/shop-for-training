package top.wwf.modules.goods.dao;

import top.wwf.modules.goods.entity.SFTGoodsParam;

import java.util.List;

public interface SFTGoodsParamMapper {
    int deleteByPrimaryKey(Long id);


    int insertSelective(SFTGoodsParam record);

    SFTGoodsParam selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SFTGoodsParam record);

    List<SFTGoodsParam> selectListByGoodsId(String goodsId);
}