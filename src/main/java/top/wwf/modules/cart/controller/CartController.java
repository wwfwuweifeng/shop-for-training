package top.wwf.modules.cart.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.wwf.common.base.MySession;
import top.wwf.common.base.ServerResponse;
import top.wwf.common.consts.HttpResponseEnum;
import top.wwf.modules.cart.service.CartService;
import top.wwf.modules.goods.entity.SFTGoods;
import top.wwf.modules.goods.vo.GoodsListGroupByShopVO;

import java.util.List;

/**
* @Description:    TODO
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-07 00:01
*/

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    /**
     * 添加新商品到购物车中，卖家无法添加自己出售的商品至购物车，任何状态的商品均可添加，区别只在于是否可以勾选
     * @return
     */
    @ResponseBody
    @RequestMapping("/add")
    public ServerResponse addNewGoodsToCart(
            @RequestParam(value = "goodsId",defaultValue = "") String goodsId){
        MySession session=MySession.getInstance();
        cartService.addNewGoodsToCart(session,goodsId);
        return ServerResponse.create(HttpResponseEnum.SUCCESS,"添加成功");
    }

    /**
     * 删除购物车里的商品，只支持单个删除
     * @param goodsId
     * @return
     */
    @ResponseBody
    @RequestMapping("/del")
    public ServerResponse delGoodsFromCart(
            @RequestParam(value = "goodsId",defaultValue = "") String goodsId){
        MySession session= MySession.getInstance();
        cartService.delGoodsFromCart(session,goodsId);
//        List<GoodsListGroupByShopVO> result  =cartService.getGoodsListByCart(session);
        return ServerResponse.create();
    }

    /**
     * 获取购物车列表，购物车列表不进行分页显示
     * @return
     */
    @ResponseBody
    @RequestMapping("/goodsListByCart")
    public ServerResponse getGoodsListByCart(){
        MySession                    session =MySession.getInstance();
        List<GoodsListGroupByShopVO> result  =cartService.getGoodsListByCart(session);
        return ServerResponse.create(result);
    }


}
