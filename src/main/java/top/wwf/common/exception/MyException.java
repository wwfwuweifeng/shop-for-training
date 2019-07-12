package top.wwf.common.exception;

import top.wwf.common.consts.HttpResponseEnum;

public class MyException extends RuntimeException {
    private HttpResponseEnum statusEnum;

    public MyException(HttpResponseEnum statusEnum,String errorMsg){
        super(errorMsg);
        this.statusEnum=statusEnum;
    }

    public MyException(HttpResponseEnum statusEnum){
        super(statusEnum.getValue());
        this.statusEnum=statusEnum;
    }

    public HttpResponseEnum getStatusEnum() {
        return statusEnum;
    }

    public int getStatusKey(){
        return this.statusEnum.getKey();
    }

    public String getStatusMsg(){
        return this.statusEnum.getValue();
    }
}
