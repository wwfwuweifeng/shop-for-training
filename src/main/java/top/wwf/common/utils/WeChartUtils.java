package top.wwf.common.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import top.wwf.common.base.OpenIdResult;
import top.wwf.common.consts.Const;
import top.wwf.common.consts.HttpResponseEnum;
import top.wwf.common.exception.MyException;

import java.util.HashMap;

/**
* @Description:    微信相关工具类
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-05-11 11:04
*/
public class WeChartUtils {

    /**
     * Http请求
     */
    private static RestTemplate restTemplate = new RestTemplate();

    /**
     * 获取OpendID
     * @param code 请求码
     * @return 用户唯一id 和 会话密钥
     */
    public static OpenIdResult getOpenId(String code) throws ResourceAccessException {
        HashMap<String, String> param = new HashMap<>();
        param.put("appid", Const.WEIXIN_APPID_OPENID);
        param.put("secret",Const.WEIXIN_SECRET_OPENID);
        param.put("js_code",code);
        param.put("grant_type",Const.WEIXIN_GRANT_TYPE_OPENID);
        ResponseEntity<String> responseEntity=restTemplate.getForEntity(Const.URL_GET_WX_OPENID, String.class, param);


        if(!responseEntity.getBody().contains("openid")){
            throw new MyException(HttpResponseEnum.INTERNAL_SERVER_ERROR,"登录失败，请重试");
        }
        return JSON.parseObject(responseEntity.getBody(),OpenIdResult.class);

    }

    public static void main(String[] args){
        OpenIdResult responseEntity = getOpenId("033BUzc42F5ugO0NCqc42gSoc42BUzck");
        System.out.println(responseEntity);
    }
}