package top.wwf.modules.cart.dao.enhance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.wwf.modules.cart.dao.SFTCartMapper;
import top.wwf.modules.cart.entity.SFTCart;

import java.util.List;

/**
* @Description:    TODO
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-07 00:07
*/
@Repository
public class CartDao {
    @Autowired
    private SFTCartMapper cartMapper;

    public SFTCart getCartByUserIdAndGoodsId(String userId, String goodsId) {
        return cartMapper.selectByUserIdAndGoodsId(userId,goodsId);
    }

    public void addGoodsToCart(SFTCart cart) {
        cartMapper.insertSelective(cart);
    }

    /**
     * order by createTime desc
     * @param userId
     * @return
     */
    public List<String> getGoodsIdListInCartByUserId(String userId) {
        return cartMapper.selectGoodsIdListByUserId(userId);
    }

    public void delGoodsFromCartByPrimaryKey(SFTCart cart) {
        cartMapper.deleteByPrimaryKey(cart.getId());
    }

    public void delGoodsFromCartByUserIdAndGoodsIdList(String userId, List<String>  goodsIdList) {
        cartMapper.deleteByUserIdAndGoodsIdList(userId,goodsIdList);
    }
}
