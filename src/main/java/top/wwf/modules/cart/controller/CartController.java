package top.wwf.modules.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.wwf.common.base.ServerResponse;
import top.wwf.modules.cart.service.CartService;

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
     * 添加新商品到购物车中
     * @return
     */
    @ResponseBody
    @RequestMapping("/add")
    public ServerResponse addNewGoodsToCart(String goodsId){

        return null;
    }

    /**
     * 删除购物车里的商品，只支持单个删除
     * @param goodsId
     * @return
     */
    @ResponseBody
    @RequestMapping("/del")
    public ServerResponse delGoodsFromCart(String goodsId){
        return null;
    }

    /**
     * 获取购物车列表
     * @return
     */
    @ResponseBody
    @RequestMapping("/goodsListByCart")
    public ServerResponse getGoodsListByCart(){
        return null;
    }


}
