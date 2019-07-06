package top.wwf.modules.user.entity;

import java.util.Date;

public class SFTUserSysInfo {
    private Long id;

    private String userId;

    private String openId;

    private String sessionKey;

    private String token;

    private String registerCode;

    private Integer userRole;

    private String userName;

    private Integer codeIsUsed;

    private String shopId;

    private String shopName;

    private Date createTime;

    private Date updateTime;

    public SFTUserSysInfo(Long id, String userId, String openId, String sessionKey, String token, String registerCode, Integer userRole, String userName, Integer codeIsUsed, String shopId, String shopName, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.openId = openId;
        this.sessionKey = sessionKey;
        this.token = token;
        this.registerCode = registerCode;
        this.userRole = userRole;
        this.userName = userName;
        this.codeIsUsed = codeIsUsed;
        this.shopId = shopId;
        this.shopName = shopName;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public SFTUserSysInfo() {
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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
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

    public String getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode == null ? null : registerCode.trim();
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getCodeIsUsed() {
        return codeIsUsed;
    }

    public void setCodeIsUsed(Integer codeIsUsed) {
        this.codeIsUsed = codeIsUsed;
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