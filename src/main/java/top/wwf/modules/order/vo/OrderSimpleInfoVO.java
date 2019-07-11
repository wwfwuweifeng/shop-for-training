package top.wwf.modules.order.vo;

import top.wwf.modules.order.entity.SFTOrderItem;

import java.util.List;

/**
* @Description:    用于订单列表显示
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-11 09:33
*/
public class OrderSimpleInfoVO {
    private String orderId;
    private String shopName;
    private int orderState;
    private String orderStateDesc;
    private String orderTotalMoney;
    private List<SFTOrderItem> orderItemList;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public String getOrderStateDesc() {
        return orderStateDesc;
    }

    public void setOrderStateDesc(String orderStateDesc) {
        this.orderStateDesc = orderStateDesc;
    }

    public String getOrderTotalMoney() {
        return orderTotalMoney;
    }

    public void setOrderTotalMoney(String orderTotalMoney) {
        this.orderTotalMoney = orderTotalMoney;
    }

    public List<SFTOrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<SFTOrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }
}
