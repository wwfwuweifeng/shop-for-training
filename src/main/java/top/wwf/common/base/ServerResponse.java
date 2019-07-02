package top.wwf.common.base;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import top.wwf.common.consts.HttpResponseEnum;

import java.io.Serializable;


/**
* @Description:    用作api请求的返回类
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019/1/17 16:00
*/
//保证序列化json的时候,如果是null的对象,key也会消失
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> implements Serializable {
    private static final long serialVersionUID=1L;

    //HTTP响应码 默认响应状态码200
    private int code;

    //响应请求标识
    private String message;

    //响应数据实体
    private T data;

    /**
     * 不带数据的返回体
     * @param responseEnum
     */
    private ServerResponse(HttpResponseEnum responseEnum){
        this.code=responseEnum.getKey();
        this.message=responseEnum.getValue();
    }

    /**
     * 不带数据，带返回信息的返回体
     * @param responseEnum
     * @param message
     */
    private ServerResponse(HttpResponseEnum responseEnum,String message){
        this.code=responseEnum.getKey();
        this.message=message;
    }

    /**
     * 带数据的返回体
     * @param responseEnum
     * @param data
     */
    private ServerResponse(HttpResponseEnum responseEnum,T data){
        this(responseEnum);
        this.data=data;
    }

    /**
     * 响应成功，带数据的返回体
     * @param data
     */
    private ServerResponse(T data){
        this(HttpResponseEnum.SUCCESS);
        this.data=data;
    }


    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 响应成功，不带数据
     * @return
     */
    public static ServerResponse create(){
        return new ServerResponse(HttpResponseEnum.SUCCESS);
    }

    public static ServerResponse create(HttpResponseEnum httpResponseEnum){
        return new ServerResponse(httpResponseEnum);
    }

    /**
     * 带数据的成功响应返回体
     * @param data
     * @param <T>
     * @return
     */
    public static<T> ServerResponse create(T data){
        return new ServerResponse<T>(data);
    }

    public static ServerResponse create(HttpResponseEnum httpResponseEnum,String message){
        return new ServerResponse(httpResponseEnum,message);
    }

    public static<T> ServerResponse create(HttpResponseEnum httpResponseEnum,T data){
        return new ServerResponse<T>(httpResponseEnum,data);
    }
}
