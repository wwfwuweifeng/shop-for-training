package top.wwf.modules.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SFTUserSysInfo {
    @JsonIgnore
    private Long id;
    @JsonIgnore
    private String userId;
    @JsonIgnore
    private String openId;

    private String account;

    private String password;
    @JsonIgnore
    private String sessionKey;
    @JsonIgnore
    private String registerCode;

    private Integer userRole;

    private String userName;
    @JsonIgnore
    private String codeUsedTime;

    private String shopId;
    @JsonIgnore
    private Integer isDelete;
    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date updateTime;

    public SFTUserSysInfo(Long id, String userId, String openId, String account, String password, String sessionKey, String registerCode, Integer userRole, String userName, String codeUsedTime, String shopId, Integer isDelete, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.openId = openId;
        this.account = account;
        this.password = password;
        this.sessionKey = sessionKey;
        this.registerCode = registerCode;
        this.userRole = userRole;
        this.userName = userName;
        this.codeUsedTime = codeUsedTime;
        this.shopId = shopId;
        this.isDelete = isDelete;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey == null ? null : sessionKey.trim();
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

    public String getCodeUsedTime() {
        return codeUsedTime;
    }

    public void setCodeUsedTime(String codeUsedTime) {
        this.codeUsedTime = codeUsedTime == null ? null : codeUsedTime.trim();
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId == null ? null : shopId.trim();
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