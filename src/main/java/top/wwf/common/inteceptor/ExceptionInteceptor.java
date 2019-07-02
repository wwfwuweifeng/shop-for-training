package top.wwf.common.inteceptor;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import top.wwf.common.base.ServerResponse;
import top.wwf.common.consts.Const;
import top.wwf.common.consts.HttpResponseEnum;
import top.wwf.common.exception.MyException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
* @Description:    异常拦截器
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-04-09 20:43
*/
public class ExceptionInteceptor implements HandlerInterceptor {
    private static Logger logger= LoggerFactory.getLogger(ExceptionInteceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception ex) throws Exception {
        if (null== ex){
            return;
        }
        //发现异常才进行处理
        ServerResponse serverResponse;
        if(ex instanceof NullPointerException){
            serverResponse=ServerResponse.create(HttpResponseEnum.ERRONEOUS_REQUEST);
        }else if(ex instanceof ResourceAccessException){
            serverResponse=ServerResponse.create(HttpResponseEnum.GATEWAY_TIMEOUT);
        }else if(ex instanceof MyException){
            if (((MyException) ex).getStatusEnum()==HttpResponseEnum.INTERNAL_SERVER_ERROR){
                //服务器发生错误的异常，不将异常信息返回给前端
                serverResponse=ServerResponse.create(HttpResponseEnum.INTERNAL_SERVER_ERROR);
            }else {
                serverResponse=ServerResponse.create(((MyException) ex).getStatusEnum(), ex.getMessage());
            }
        }else if(ex instanceof IllegalArgumentException){
            serverResponse=ServerResponse.create(HttpResponseEnum.ERRONEOUS_REQUEST);
        }else{
            serverResponse=ServerResponse.create(HttpResponseEnum.INTERNAL_SERVER_ERROR);
        }

        logger.error("异常拦截： url={}，异常名={}，异常信息：" ,request.getRequestURI(), ex.getClass().getName(),ex);
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
}
