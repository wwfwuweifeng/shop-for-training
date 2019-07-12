package top.wwf.modules.goods.dao;

import org.apache.ibatis.annotations.Param;
import top.wwf.modules.goods.entity.SFTGoods;

import java.util.List;

public interface SFTGoodsMapper {


    int insertSelective(SFTGoods record);

    SFTGoods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SFTGoods record);


    SFTGoods selectByGoodsId(String goodsId);

    List<SFTGoods> selectByGoodsIdListAndState(@Param("goodsIds")List<String> goodsIdList, @Param("state") int state);

    List<SFTGoods> selectListByStateAndClassifyIdAndShopIdAndKeyword(int state, Long classifyId, String shopId,String keyword);

    List<SFTGoods> selectListByStateAndShopIdAndKeyword(int state, String shopId, String keyword);

    void updateStateByShopId(int state, String shopId);

    List<SFTGoods> selectSimpleInfoListByGoodsIdList(List<String> goodsIdList);

    List<SFTGoods> selectNewGoodsListByListSize(int listSize);

    List<SFTGoods> selectHotSellGoodsListByListSize(int listSize);
}