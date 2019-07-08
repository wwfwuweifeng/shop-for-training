package top.wwf.modules.order.entity;

import java.util.Date;

public class SFTOrderPay {
    private Long id;

    private String payId;

    private String orderId;

    private String cartNum;

    private String userId;

    private Integer payType;

    private Integer orderTotalPrice;

    private Integer orderActualPay;

    private Date createTime;

    public SFTOrderPay(Long id, String payId, String orderId, String cartNum, String userId, Integer payType, Integer orderTotalPrice, Integer orderActualPay, Date createTime) {
        this.id = id;
        this.payId = payId;
        this.orderId = orderId;
        this.cartNum = cartNum;
        this.userId = userId;
        this.payType = payType;
        this.orderTotalPrice = orderTotalPrice;
        this.orderActualPay = orderActualPay;
        this.createTime = createTime;
    }

    public SFTOrderPay() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId == null ? null : payId.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getCartNum() {
        return cartNum;
    }

    public void setCartNum(String cartNum) {
        this.cartNum = cartNum == null ? null : cartNum.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(Integer orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public Integer getOrderActualPay() {
        return orderActualPay;
    }

    public void setOrderActualPay(Integer orderActualPay) {
        this.orderActualPay = orderActualPay;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}