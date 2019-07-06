package top.wwf.modules.goods.dao;

import top.wwf.modules.goods.entity.SFTGoodsClassify;

public interface SFTGoodsClassifyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SFTGoodsClassify record);

    int insertSelective(SFTGoodsClassify record);

    SFTGoodsClassify selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SFTGoodsClassify record);

    int updateByPrimaryKey(SFTGoodsClassify record);
}