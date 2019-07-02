package top.wwf.modules.user.vo;

/**
* @Description:    登录服务器，返回的结果
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-04-30 10:23
*/
public class LoginResultVO {
    private String token;
    private int isPerfectInfo;
    private String userName;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getIsPerfectInfo() {
        return isPerfectInfo;
    }

    public void setIsPerfectInfo(int isPerfectInfo) {
        this.isPerfectInfo = isPerfectInfo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
