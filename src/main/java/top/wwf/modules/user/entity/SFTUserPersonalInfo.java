package top.wwf.modules.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SFTUserPersonalInfo {

    @JsonIgnore
    private Long id;
    @JsonIgnore
    private String userId;
    @JsonIgnore
    private String wxName;

    private Integer userSex;

    private String userEmail;

    private String userTel;

    private String shopName;

    private String receiverName;

    private String receiverTel;
    private String receiverAddressSimple;
    private String receiverAddressDetail;
    private Integer haveReceiverAddress;
    @JsonIgnore
    private Integer isDelete;
    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date updateTime;

    public SFTUserPersonalInfo(Long id, String userId, String wxName, Integer userSex, String userEmail, String userTel, String shopName, String receiverName, String receiverTel, String receiverAddressSimple, String receiverAddressDetail, Integer haveReceiverAddress, Integer isDelete, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.wxName = wxName;
        this.userSex = userSex;
        this.userEmail = userEmail;
        this.userTel = userTel;
        this.shopName = shopName;
        this.receiverName = receiverName;
        this.receiverTel = receiverTel;
        this.receiverAddressSimple = receiverAddressSimple;
        this.receiverAddressDetail = receiverAddressDetail;
        this.haveReceiverAddress = haveReceiverAddress;
        this.isDelete = isDelete;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public SFTUserPersonalInfo() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName == null ? null : wxName.trim();
    }

    public Integer getUserSex() {
        return userSex;
    }

    public void setUserSex(Integer userSex) {
        this.userSex = userSex;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel == null ? null : userTel.trim();
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName == null ? null : receiverName.trim();
    }

    public String getReceiverTel() {
        return receiverTel;
    }

    public void setReceiverTel(String receiverTel) {
        this.receiverTel = receiverTel == null ? null : receiverTel.trim();
    }

    public String getReceiverAddressSimple() {
        return receiverAddressSimple;
    }

    public void setReceiverAddressSimple(String receiverAddressSimple) {
        this.receiverAddressSimple = receiverAddressSimple == null ? null : receiverAddressSimple.trim();
    }

    public String getReceiverAddressDetail() {
        return receiverAddressDetail;
    }

    public void setReceiverAddressDetail(String receiverAddressDetail) {
        this.receiverAddressDetail = receiverAddressDetail == null ? null : receiverAddressDetail.trim();
    }

    public Integer getHaveReceiverAddress() {
        return haveReceiverAddress;
    }

    public void setHaveReceiverAddress(Integer haveReceiverAddress) {
        this.haveReceiverAddress = haveReceiverAddress;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}