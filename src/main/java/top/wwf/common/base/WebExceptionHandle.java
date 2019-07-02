package top.wwf.common.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.wwf.common.consts.HttpResponseEnum;
import top.wwf.common.exception.MyException;

/**
* @Description:    对全局的异常进行处理
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-03-19 16:59
*/
//@ControllerAdvice
//@Component
public class WebExceptionHandle {
    private static Logger logger= LoggerFactory.getLogger(WebExceptionHandle.class);

//    @ResponseBody
//    @ExceptionHandler(MyException.class)
    public ServerResponse myExceptionHandler(MyException e){
        return ServerResponse.create(e.getStatusEnum(),e.getMessage());
    }

//    @ResponseBody
//    @ExceptionHandler(Exception.class)
    public ServerResponse exceptionHandler(Exception e){
        logger.error("请求URL={},异常内容：",e);
        e.printStackTrace();
        return ServerResponse.create(HttpResponseEnum.INTERNAL_SERVER_ERROR);
    }
}
