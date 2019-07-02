package top.wwf.common.base;

/**
* @Description:    请求获取openid时，返回的结果。为了对应微信服务器的返回结果，所以变量的命名方式不是驼峰式
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-04-09 10:46
*/
public class OpenIdResult {
    /**
     * 用户唯一标识
     */
    private String openid;
    /**
     * 回话秘钥
     */
    private String session_key;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }
}