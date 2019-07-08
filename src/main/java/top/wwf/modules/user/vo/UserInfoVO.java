package top.wwf.modules.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import top.wwf.modules.user.entity.SFTUserPersonalInfo;
import top.wwf.modules.user.entity.SFTUserSysInfo;

/**
* @Description:    TODO
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-08 15:17
*/
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class UserInfoVO {
    private String token;
    private int isRegister;
    private String userName;
    private SFTUserPersonalInfo userPersonalInfo;
    private SFTUserSysInfo userSysInfo;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getIsRegister() {
        return isRegister;
    }

    public void setIsRegister(int isRegister) {
        this.isRegister = isRegister;
    }

    public SFTUserPersonalInfo getUserPersonalInfo() {
        return userPersonalInfo;
    }

    public void setUserPersonalInfo(SFTUserPersonalInfo userPersonalInfo) {
        this.userPersonalInfo = userPersonalInfo;
    }

    public SFTUserSysInfo getUserSysInfo() {
        return userSysInfo;
    }

    public void setUserSysInfo(SFTUserSysInfo userSysInfo) {
        this.userSysInfo = userSysInfo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
