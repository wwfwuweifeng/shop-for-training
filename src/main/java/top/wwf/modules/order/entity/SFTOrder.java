package top.wwf.modules.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import top.wwf.common.base.DateYMDHMSJsonSerializer;

import java.util.Date;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SFTOrder {
    @JsonIgnore
    private Long id;

    private String orderId;

    private String cartNum;
    @JsonIgnore
    private String buyerId;

    private String shopId;

    private String shopName;

    private Long orderTotalMoney;
    private String strOrderTotalMoney;
    private Integer state;

    private String payId;
    @JsonSerialize(using = DateYMDHMSJsonSerializer.class)
    private Date createTime;

    private String payTime;

    private String sendTime;

    private String dealTime;

    private String receiverPeople;

    private String receiverAddress;

    private String expressNum;
    @JsonIgnore
    private Date updateTime;

    public SFTOrder(Long id, String orderId, String cartNum, String buyerId, String shopId, String shopName, Long orderTotalMoney, Integer state, String payId, Date createTime, String payTime, String sendTime, String dealTime, String receiverPeople, String receiverAddress, String expressNum, Date updateTime) {
        this.id = id;
        this.orderId = orderId;
        this.cartNum = cartNum;
        this.buyerId = buyerId;
        this.shopId = shopId;
        this.shopName = shopName;
        this.orderTotalMoney = orderTotalMoney;
        this.state = state;
        this.payId = payId;
        this.createTime = createTime;
        this.payTime = payTime;
        this.sendTime = sendTime;
        this.dealTime = dealTime;
        this.receiverPeople = receiverPeople;
        this.receiverAddress = receiverAddress;
        this.expressNum = expressNum;
        this.updateTime = updateTime;
    }

    public SFTOrder() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId == null ? null : buyerId.trim();
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId == null ? null : shopId.trim();
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    public Long getOrderTotalMoney() {
        return orderTotalMoney;
    }

    public void setOrderTotalMoney(Long orderTotalMoney) {
        this.orderTotalMoney = orderTotalMoney;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId ;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime == null ? null : payTime.trim();
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime == null ? null : sendTime.trim();
    }

    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime == null ? null : dealTime.trim();
    }

    public String getReceiverPeople() {
        return receiverPeople;
    }

    public void setReceiverPeople(String receiverPeople) {
        this.receiverPeople = receiverPeople == null ? null : receiverPeople.trim();
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress == null ? null : receiverAddress.trim();
    }

    public String getExpressNum() {
        return expressNum;
    }

    public void setExpressNum(String expressNum) {
        this.expressNum = expressNum == null ? null : expressNum.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public String getStrOrderTotalMoney(){
        return String.format("%10.2f", orderTotalMoney/100.0);
    }
}