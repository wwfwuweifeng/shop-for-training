package top.wwf.modules.order.dto;

import top.wwf.modules.cart.entity.SFTCart;

import java.util.List;

/**
* @Description:    提交订单的DTO
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-10 15:46
*/
public class SubmitOrderDTO {
    private String token;
    private String receiverPeople;
    private String receiverAddress;
    private List<SFTCart> cartList; //用于购物车提交订单的
    private SFTCart cart;   //用于购买单件商品的

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getReceiverPeople() {
        return receiverPeople;
    }

    public void setReceiverPeople(String receiverPeople) {
        this.receiverPeople = receiverPeople;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public List<SFTCart> getCartList() {
        return cartList;
    }

    public void setCartList(List<SFTCart> cartList) {
        this.cartList = cartList;
    }

    public SFTCart getCart() {
        return cart;
    }

    public void setCart(SFTCart cart) {
        this.cart = cart;
    }
}
