package top.wwf.common.base;

import top.wwf.common.consts.Const;
import top.wwf.common.consts.HttpResponseEnum;
import top.wwf.common.exception.MyException;

/**
 * @Description: 自定义session类
 * @Author: wwf（hitwh_wwf@163.com）
 * @CreateDate: 2019-04-09 20:28
 */
public class MySession {
    private String token;
    private String userOpenId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserOpenId() {
        return userOpenId;
    }

    public void setUserOpenId(String userOpenId) {
        this.userOpenId = userOpenId;
    }

    public static MySession getInstance() {
        MySession session = Const.SESSION_THREAD_LOCAL.get();
        if (null == session) {
            throw new MyException(HttpResponseEnum.UNAUTHORIZED);
        }
        Const.SESSION_THREAD_LOCAL.remove();    //获取到值后就立刻移除掉
        return session;

    }

}
