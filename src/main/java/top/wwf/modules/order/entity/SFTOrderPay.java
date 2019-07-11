package top.wwf.modules.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import top.wwf.common.base.DateYMDHMSJsonSerializer;

import java.util.Date;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SFTOrderPay {
    @JsonIgnore
    private Long id;

    private String payId;

    private String orderId;

    private String cartNum;
    @JsonIgnore
    private String userId;

    private Integer payType;

    private Long orderTotalPrice;

    private Long orderActualPay;
    @JsonSerialize(using = DateYMDHMSJsonSerializer.class)
    private Date createTime;

    public SFTOrderPay(Long id, String payId, String orderId, String cartNum, String userId, Integer payType, Long orderTotalPrice, Long orderActualPay, Date createTime) {
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

    public Long getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(Long orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public Long getOrderActualPay() {
        return orderActualPay;
    }

    public void setOrderActualPay(Long orderActualPay) {
        this.orderActualPay = orderActualPay;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}