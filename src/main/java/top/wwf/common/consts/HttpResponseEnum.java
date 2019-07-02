package top.wwf.common.consts;

/**
* @Description:    TODO
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019/1/17 16:33
*/
public enum  HttpResponseEnum {

    SUCCESS(200, "处理完成"),
    ERRONEOUS_REQUEST(400, "参数为空或请求数据格式错误"),
    UNAUTHORIZED(401, "身份验证失败"),
    PROHIBIT(403, "服务器拒绝请求"),
    INTERNAL_SERVER_ERROR(500, "服务器遇到错误，无法完成请求"),
    SERVICE_NOT_AVAILABLE(503, "服务器目前无法使用"),
    GATEWAY_TIMEOUT(504, "服务器作为网关或代理，但是没有及时从上游服务器收到请求");

    /**
     * 键
     */
    private int key;
    /**
     * 值
     */
    private String value;

    HttpResponseEnum(int key, String value) {
        this.key=key;
        this.value=value;
    }

    public String getValueByKey(int key){
        for (HttpResponseEnum httpResponseEnum: values()){
            if (httpResponseEnum.getKey()==key){
                return httpResponseEnum.getValue();
            }
        }
        return "500";
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
