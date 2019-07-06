package top.wwf.modules.order.entity;

import java.util.Date;

public class SFTOrder {
    private Long id;

    private String orderId;

    private String userId;

    private String shopId;

    private String shopName;

    private Integer orderTotalMoney;

    private Integer state;

    private Date createTime;

    private String payTime;

    private String sendTime;

    private String dealTime;

    private String expressNum;

    private Date updateTime;

    public SFTOrder(Long id, String orderId, String userId, String shopId, String shopName, Integer orderTotalMoney, Integer state, Date createTime, String payTime, String sendTime, String dealTime, String expressNum, Date updateTime) {
        this.id = id;
        this.orderId = orderId;
        this.userId = userId;
        this.shopId = shopId;
        this.shopName = shopName;
        this.orderTotalMoney = orderTotalMoney;
        this.state = state;
        this.createTime = createTime;
        this.payTime = payTime;
        this.sendTime = sendTime;
        this.dealTime = dealTime;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
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

    public Integer getOrderTotalMoney() {
        return orderTotalMoney;
    }

    public void setOrderTotalMoney(Integer orderTotalMoney) {
        this.orderTotalMoney = orderTotalMoney;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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
}