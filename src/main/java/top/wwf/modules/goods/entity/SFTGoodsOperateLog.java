package top.wwf.modules.goods.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import top.wwf.common.base.DateYMDHMSJsonSerializer;

import java.util.Date;

public class SFTGoodsOperateLog {
    private Long id;

    private String goodsId;

    private Date operateTime;

    private String operateType;

    public SFTGoodsOperateLog(Long id, String goodsId, Date operateTime, String operateType) {
        this.id = id;
        this.goodsId = goodsId;
        this.operateTime = operateTime;
        this.operateType = operateType;
    }

    public SFTGoodsOperateLog() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
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