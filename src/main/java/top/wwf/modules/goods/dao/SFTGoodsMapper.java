package top.wwf.modules.goods.dao;

import top.wwf.modules.goods.entity.SFToods;

public interface SFTGoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SFToods record);

    int insertSelective(SFToods record);

    SFToods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SFToods record);

    int updateByPrimaryKey(SFToods record);
}