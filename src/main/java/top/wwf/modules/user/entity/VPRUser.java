package top.wwf.modules.user.entity;

import java.util.Date;

public class VPRUser {
    private Long id;

    private String openId;

    private String wxName;

    private Integer userSex;

    private String sessionKey;

    private String token;

    private String userName;

    private String telNum;

    private String address;

    private Date createTime;

    private Date updateTime;

    private String email;

    private Integer isPerfectInfo;

    public VPRUser(Long id, String openId, String wxName, Integer userSex, String sessionKey, String token, String userName, String telNum, String address, Date createTime, Date updateTime, String email, Integer isPerfectInfo) {
        this.id = id;
        this.openId = openId;
        this.wxName = wxName;
        this.userSex = userSex;
        this.sessionKey = sessionKey;
        this.token = token;
        this.userName = userName;
        this.telNum = telNum;
        this.address = address;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.email = email;
        this.isPerfectInfo = isPerfectInfo;
    }

    public VPRUser() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
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

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey == null ? null : sessionKey.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getTelNum() {
        return telNum;
    }

    public void setTelNum(String telNum) {
        this.telNum = telNum == null ? null : telNum.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getIsPerfectInfo() {
        return isPerfectInfo;
    }

    public void setIsPerfectInfo(Integer isPerfectInfo) {
        this.isPerfectInfo = isPerfectInfo;
    }
}