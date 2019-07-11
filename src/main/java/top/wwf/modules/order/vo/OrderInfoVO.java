package top.wwf.modules.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Lists;
import top.wwf.common.base.DateYMDHMSJsonSerializer;
import top.wwf.modules.order.entity.SFTOrder;
import top.wwf.modules.order.entity.SFTOrderItem;
import top.wwf.modules.order.entity.SFTOrderOperateLog;

import java.util.List;

/**
* @Description:    TODO
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-10 15:37
*/
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class OrderInfoVO {
    private String receiverPeople;
    private String receiverAddress;
    private String stateDesc;        //状态显示内容
    private String msgForBt;        //主按钮显示的内容
    private int    allowCancel;     //是否允许取消订单
    private int    allowClickMain; //是否允许点击主按钮
    private List<SFTOrderItem> orderItemList;
    private List<OperateLog> operateLogList;
    private SFTOrder order;

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

    public String getReceiverPeople() {
        return receiverPeople;
    }

    public void setReceiverPeople(String receiverPeople) {
        this.receiverPeople = receiverPeople;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public List<SFTOrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<SFTOrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public List<OperateLog> getOperateLogList() {
        return operateLogList;
    }

    public void setOperateLogList(List<SFTOrderOperateLog> orderOperateLogList) {
        List<OperateLog> operateLogList= Lists.newLinkedList();
        OperateLog operateLog;
        for (SFTOrderOperateLog orderOperateLog: orderOperateLogList){
            operateLog=new OperateLog(
                    orderOperateLog.getOperateType(),
                    orderOperateLog.getOperateTime()==null?"": DateYMDHMSJsonSerializer.dateFormat.format(orderOperateLog.getOperateTime())
            );
            operateLogList.add(operateLog);
        }
        this.operateLogList=operateLogList;
    }

    public SFTOrder getOrder() {
        return order;
    }

    public void setOrder(SFTOrder order) {
        this.order = order;
    }

    public String getMsgForBt() {
        return msgForBt;
    }

    public void setMsgForBt(String msgForBt) {
        this.msgForBt = msgForBt;
    }

    public int getAllowCancel() {
        return allowCancel;
    }

    public void setAllowCancel(int allowCancel) {
        this.allowCancel = allowCancel;
    }

    public int getAllowClickMain() {
        return allowClickMain;
    }

    public void setAllowClickMain(int allowClickMain) {
        this.allowClickMain = allowClickMain;
    }

    public String getStateDesc() {
        return stateDesc;
    }

    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }
}
