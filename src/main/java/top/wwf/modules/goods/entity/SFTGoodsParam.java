package top.wwf.modules.goods.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SFTGoodsParam {
    @JsonIgnore
    private Long id;

    private String goodsId;

    private String paramName;

    private String paramValue;
    @JsonIgnore
    private Date createTime;

    public SFTGoodsParam(Long id, String goodsId, String paramName, String paramValue, Date createTime) {
        this.id = id;
        this.goodsId = goodsId;
        this.paramName = paramName;
        this.paramValue = paramValue;
        this.createTime = createTime;
    }

    public SFTGoodsParam() {
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

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName == null ? null : paramName.trim();
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue == null ? null : paramValue.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}