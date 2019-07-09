package top.wwf.modules.order.entity;

import java.util.Date;

public class SFTOrderOperateLog {
    private Long id;

    private String orderId;

    private Date operateTime;

    private String operateType;

    public SFTOrderOperateLog(Long id, String orderId, Date operateTime, String operateType) {
        this.id = id;
        this.orderId = orderId;
        this.operateTime = operateTime;
        this.operateType = operateType;
    }

    public SFTOrderOperateLog() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType == null ? null : operateType.trim();
    }
}