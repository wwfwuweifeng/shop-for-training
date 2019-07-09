package top.wwf.modules.goods.dao;

import top.wwf.modules.goods.entity.SFTGoodsOperateLog;

public interface SFTGoodsOperateLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SFTGoodsOperateLog record);

    int insertSelective(SFTGoodsOperateLog record);

    SFTGoodsOperateLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SFTGoodsOperateLog record);

    int updateByPrimaryKey(SFTGoodsOperateLog record);
}