package top.wwf.modules.goods.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import top.wwf.modules.goods.entity.SFTGoods;
import top.wwf.modules.goods.entity.SFTGoodsParam;

import java.util.List;

/**
* @Description:    TODO
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-11 10:12
*/
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class GoodsDetailForBuyerVO {
    private SFTGoods goods;
    private int allowBuy;
    private String msgForBuyBt;
    private List<SFTGoodsParam> paramList;

    public SFTGoods getGoods() {
        return goods;
    }

    public void setGoods(SFTGoods goods) {
        this.goods = goods;
    }

    public int getAllowBuy() {
        return allowBuy;
    }

    public void setAllowBuy(int allowBuy) {
        this.allowBuy = allowBuy;
    }

    public String getMsgForBuyBt() {
        return msgForBuyBt;
    }

    public void setMsgForBuyBt(String msgForBuyBt) {
        this.msgForBuyBt = msgForBuyBt;
    }

    public List<SFTGoodsParam> getParamList() {
        return paramList;
    }

    public void setParamList(List<SFTGoodsParam> paramList) {
        this.paramList = paramList;
    }
}
