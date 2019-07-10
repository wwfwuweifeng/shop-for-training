package top.wwf.modules.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
* @Description:    TODO
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-10 10:29
*/
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SubmitOrderVO {
    private String cartNum;    //只有通过购物车提交订单的返回结果才有值
    private String orderId;     //购买单件商品提交订单的返回结果
    private Long   totalMoney;


    public Long getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Long totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getCartNum() {
        return cartNum;
    }

    public void setCartNum(String cartNum) {
        this.cartNum = cartNum;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
