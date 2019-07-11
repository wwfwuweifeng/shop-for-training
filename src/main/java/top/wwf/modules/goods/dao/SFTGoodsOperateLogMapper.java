package top.wwf.modules.goods.dao;

import top.wwf.modules.goods.entity.SFTGoodsClassify;
import top.wwf.modules.goods.entity.SFTGoodsOperateLog;

import java.util.List;

public interface SFTGoodsOperateLogMapper {




    int insertSelective(SFTGoodsOperateLog record);

    SFTGoodsOperateLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SFTGoodsOperateLog record);

    List<SFTGoodsOperateLog> selectListByGoodsId(String goodsId);


}