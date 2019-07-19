package top.wwf.modules.cart.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SFTCart {
    @JsonIgnore
    private Long id;
    @JsonIgnore
    private String userId;

    private String goodsId;

    private String shopId;

    private Integer checked;
    private Integer num;
    @JsonIgnore
    private Date createTime;

    public SFTCart(Long id, String userId, String goodsId, String shopId, Integer checked, Integer num, Date createTime) {
        this.id = id;
        this.userId = userId;
        this.goodsId = goodsId;
        this.shopId = shopId;
        this.checked = checked;
        this.num = num;
        this.createTime = createTime;
    }

    public SFTCart() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId == null ? null : shopId.trim();
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}