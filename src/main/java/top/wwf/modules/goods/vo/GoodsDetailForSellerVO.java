package top.wwf.modules.goods.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Lists;
import top.wwf.common.base.DateYMDHMSJsonSerializer;
import top.wwf.modules.goods.entity.SFTGoods;
import top.wwf.modules.goods.entity.SFTGoodsOperateLog;

import java.util.List;

/**
* @Description:    TODO
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-09 11:08
*/
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class GoodsDetailForSellerVO {
    private SFTGoods goods;
    private String msgForMainBt;    //主按钮显示的字段
    private int allowClickMainBt;   //是否允许点击主要按钮
    private int allowLowerShelf;    //是否允许点击下架，只有处于下架状态的商品无法点击
    private String stateDesc;
    private List<OperateLog> operateLogList;

    private class OperateLog{
        private String text;
        private String desc;

        public OperateLog(String text, String desc) {
            this.text = text;
            this.desc = desc;
        }

        public String getText() {
            return text;
        }

        public String getDesc() {
            return desc;
        }

    }

    public SFTGoods getGoods() {
        return goods;
    }

    public void setGoods(SFTGoods goods) {
        this.goods = goods;
    }

    public String getMsgForMainBt() {
        return msgForMainBt;
    }

    public void setMsgForMainBt(String msgForMainBt) {
        this.msgForMainBt = msgForMainBt;
    }

    public int getAllowClickMainBt() {
        return allowClickMainBt;
    }

    public void setAllowClickMainBt(int allowClickMainBt) {
        this.allowClickMainBt = allowClickMainBt;
    }

    public int getAllowLowerShelf() {
        return allowLowerShelf;
    }

    public void setAllowLowerShelf(int allowLowerShelf) {
        this.allowLowerShelf = allowLowerShelf;
    }

    public List<OperateLog> getOperateLogList() {
        return operateLogList;
    }

    public void setOperateLogList(List<SFTGoodsOperateLog> goodsOperateLogList) {
        List<OperateLog> operateLogList= Lists.newLinkedList();
        OperateLog operateLog;
        for (SFTGoodsOperateLog goodsOperateLog:goodsOperateLogList){
            operateLog=new OperateLog(
                    goodsOperateLog.getOperateType(),
                    goodsOperateLog.getOperateTime()==null?"":DateYMDHMSJsonSerializer.dateFormat.format(goodsOperateLog.getOperateTime())
            );
            operateLogList.add(operateLog);
        }
        this.operateLogList=operateLogList;
    }

    public String getStateDesc() {
        return stateDesc;
    }

    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }
}
