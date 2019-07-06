package top.wwf.modules.cart.entity;

import java.util.Date;

public class SFTCart {
    private Long id;

    private String userId;

    private String goodsId;

    private Integer checked;

    private Integer num;

    private Date createTime;

    public SFTCart(Long id, String userId, String goodsId, Integer checked, Integer num, Date createTime) {
        this.id = id;
        this.userId = userId;
        this.goodsId = goodsId;
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