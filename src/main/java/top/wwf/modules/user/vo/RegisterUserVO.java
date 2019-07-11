package top.wwf.modules.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
* @Description:    TODO
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-08 16:20
*/
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RegisterUserVO {
    private String userName;
    private String userRole;
    private String registerCode;
    private String codeUsedTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode;
    }

    public String getCodeUsedTime() {
        return codeUsedTime;
    }

    public void setCodeUsedTime(String codeUsedTime) {
        this.codeUsedTime = codeUsedTime;
    }
}
