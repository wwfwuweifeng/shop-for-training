package top.wwf.common.inteceptor;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import top.wwf.common.base.GlobalConfig;
import top.wwf.common.base.MySession;
import top.wwf.common.base.ServerResponse;
import top.wwf.common.consts.Const;
import top.wwf.common.consts.HttpResponseEnum;
import top.wwf.common.utils.JedisUtils;
import top.wwf.modules.user.dao.enhance.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
* @Description:    登录拦截器
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-04-10 12:53
*/
public class LoginInteceptor implements HandlerInterceptor {

    @Autowired
    private UserDao userDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String token=request.getParameter("token");
        if (StringUtils.isBlank(token)){
            responseJson(response,ServerResponse.create(HttpResponseEnum.ERRONEOUS_REQUEST,"token值不能为空"));
            return false;
        }
        MySession session=getSession(token);
        if (null==session){
            responseJson(response,ServerResponse.create(HttpResponseEnum.PROHIBIT,"登录信息已过期，请先关闭小程序，然后重新登录"));
            return false;
        }
        Const.SESSION_THREAD_LOCAL.set(session);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    public void responseJson(HttpServletResponse response, ServerResponse serverResponse){
        PrintWriter out = null;
        response.setHeader(Const.CONTENT_TYPE_NAME, Const.CONTENT_TYPE_VALUE);
        response.setCharacterEncoding(Const.CHARACTER_ENCODING);
        try {
            out = response.getWriter();
            out.append(JSON.toJSONString(serverResponse));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public MySession getSession(String token){
        String userOpenId=JedisUtils.get(Const.TOKEN_PREFIX_KEY+token);
        MySession session=new MySession();
        session.setToken(token);
        if (StringUtils.isBlank(userOpenId)){
            VPRUser user =userDao.getUserByToken(token);
            if (null==user){
                return null;
            }else {
                userOpenId = user.getOpenId();
            }
        }
        session.setUserId(userOpenId);
//        JedisUtils.expire(Const.TOKEN_PREFIX_KEY+token,GlobalConfig.SESSION_TIMEOUT*60);
        JedisUtils.setex(Const.TOKEN_PREFIX_KEY+token, GlobalConfig.SESSION_TIMEOUT*60,userOpenId);
        return session;
    }
}
