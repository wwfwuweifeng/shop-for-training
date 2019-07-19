package top.wwf.common.base;

import org.apache.commons.lang3.StringUtils;
import top.wwf.common.consts.Const;
import top.wwf.common.consts.HttpResponseEnum;
import top.wwf.common.exception.MyException;
import top.wwf.common.utils.JedisUtils;

/**
 * @Description: 自定义session类
 * @Author: wwf（hitwh_wwf@163.com）
 * @CreateDate: 2019-04-09 20:28
 */
public class MySession {
    private String token;
    private String userId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static MySession getInstance() {
        MySession session = Const.SESSION_THREAD_LOCAL.get();
        if (null == session) {
            throw new MyException(HttpResponseEnum.UNAUTHORIZED);
        }
        Const.SESSION_THREAD_LOCAL.remove();    //获取到值后就立刻移除掉
        return session;
    }

    public static MySession getInstanceByToken(String token){
        String userId=JedisUtils.get(token);
        MySession session=new MySession();
        session.setToken(token);
        if (StringUtils.isBlank(userId)){
            return null;
        }
        session.setUserId(userId);
        //        JedisUtils.expire(Const.TOKEN_PREFIX_KEY+token,GlobalConfig.SESSION_TIMEOUT*60);
        JedisUtils.expire(token, Const.SESSION_TIMEOUT);
        JedisUtils.expire(userId,Const.SESSION_TIMEOUT);
        return session;
    }

    public String getShopId(){
        return JedisUtils.hget(userId,"shopId");
    }

    public void setShopId(String shopId){
        JedisUtils.hset(userId,"shopId",shopId);
        JedisUtils.expire(userId,Const.SESSION_TIMEOUT);
    }

    public Const.USER_ROLE getUserRole(){
        return Const.USER_ROLE.getRoleByKey(Integer.parseInt(JedisUtils.hget(userId,"role")));
    }

    public void setUserRole(int role){
        JedisUtils.hset(userId,"role",role);
        JedisUtils.expire(userId,Const.SESSION_TIMEOUT);
    }

}
