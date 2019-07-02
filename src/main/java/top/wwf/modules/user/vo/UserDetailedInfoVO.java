package top.wwf.modules.user.vo;

/**
* @Description:    用户详细信息
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-04-30 10:36
*/
public class UserDetailedInfoVO {
    private String wxName;
    private String userName;
    private int    isAllowModify;
    private String userSex;
    private String telNum;
    private String email;
    private String address;
    private int isPerfectInfo;

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

    public int getIsAllowModify() {
        return isAllowModify;
    }

    public void setIsAllowModify(int isAllowModify) {
        this.isAllowModify = isAllowModify;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getTelNum() {
        return telNum;
    }

    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }
}
