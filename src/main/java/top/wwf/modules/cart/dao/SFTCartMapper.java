package top.wwf.modules.cart.dao;

import org.apache.ibatis.annotations.Param;
import top.wwf.modules.cart.entity.SFTCart;

import java.util.List;

public interface SFTCartMapper {
    int deleteByPrimaryKey(Long id);



    int insertSelective(SFTCart record);

    SFTCart selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SFTCart record);



    SFTCart selectByUserIdAndGoodsId(String userId, String goodsId);

    List<String> selectGoodsIdListByUserId(String userId);

    void deleteByUserIdAndGoodsIdList(@Param("userId") String userId, @Param("goodsIdList") List<String> goodsIdList);
}