package top.wwf.modules.goods.dao;

import top.wwf.modules.goods.entity.SFTGoodsClassify;

import java.util.List;

public interface SFTGoodsClassifyMapper {
    int deleteByPrimaryKey(Long id);



    int insertSelective(SFTGoodsClassify record);

    SFTGoodsClassify selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SFTGoodsClassify record);


    List<SFTGoodsClassify> selectFirstGoodsClassifyList();

    List<SFTGoodsClassify> selectSecondClassifyListByParentClassifyId(Long parentClassifyId);

    SFTGoodsClassify selectByClassifyName(String classifyName);

    List<SFTGoodsClassify> selectSecondClassifyList();

    int deleteSecondGoodsById(Long goodsClassifyId);
}