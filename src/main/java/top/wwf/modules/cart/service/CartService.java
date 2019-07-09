package top.wwf.modules.cart.service;

import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.wwf.common.base.MySession;
import top.wwf.common.consts.HttpResponseEnum;
import top.wwf.common.exception.MyException;
import top.wwf.modules.cart.dao.enhance.CartDao;
import top.wwf.modules.cart.entity.SFTCart;
import top.wwf.modules.goods.dao.enhance.GoodsDao;
import top.wwf.modules.goods.entity.SFTGoods;

import java.util.List;

/**
* @Description:    TODO
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-07 00:20
*/
@Service
public class CartService {
    @Autowired
    private CartDao cartDao;
    @Autowired
    private GoodsDao goodsDao;

    public void addNewGoodsToCart(MySession session, String goodsId) {
        SFTGoods goods=goodsDao.getGoodsByGoodsId(goodsId);
        if (null==goods){
            throw new MyException(HttpResponseEnum.PROHIBIT,"无效goodId");
        }else if (goods.getShopOwnerId().equals(session.getUserId())){
            throw new MyException(HttpResponseEnum.PROHIBIT,"无法添加自己销售的商品");
        }
        SFTCart cart=cartDao.getCartByUserIdAndGoodsId(session.getUserId(),goodsId);
        if (null!=cart){
            throw new MyException(HttpResponseEnum.PROHIBIT,"商品已存在，请勿重复添加");
        }
        cart=new SFTCart();
        cart.setGoodsId(goodsId);
        cart.setUserId(session.getUserId());
        cartDao.addGoodsToCart(cart);
    }

    /**
     * 当前购物车的列表顺序是按照商品的创建时间由近到远，并不是添加的顺序
     *
     * 有时间再考虑是否分商店进行显示！！！
     * @param session
     * @return
     */
    public List<SFTGoods> getGoodsListByCart(MySession session) {
        List<String> goodsIdList=cartDao.getGoodsIdListInCartByUserId(session.getUserId());
        if (null==goodsIdList||goodsIdList.size()==0) return Lists.newArrayList();
        else return goodsDao.getGoodsListByGoodsIdList(goodsIdList);
    }


    public List<SFTGoods> delGoodsFromCart(MySession session, String goodsId) {
        SFTCart cart=cartDao.getCartByUserIdAndGoodsId(session.getUserId(),goodsId);
        if (null==cart){
            throw new MyException(HttpResponseEnum.PROHIBIT,"请勿重复删除");
        }
        cartDao.delGoodsFromCartByPrimaryKey(cart);
        return getGoodsListByCart(session);
    }
}
