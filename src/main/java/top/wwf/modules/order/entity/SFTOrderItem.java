package top.wwf.modules.order.entity;

import java.util.Date;

public class SFTOrderItem {
    private Long id;

    private String orderId;

    private String goodsId;

    private String goodsName;

    private String goodsCoverImage;

    private String tag;

    private Long buyPrice;

    private Integer buyNum;

    private Long totalMoney;

    private Date createTime;

    public SFTOrderItem(Long id, String orderId, String goodsId, String goodsName, String goodsCoverImage, String tag, Long buyPrice, Integer buyNum, Long totalMoney, Date createTime) {
        this.id = id;
        this.orderId = orderId;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsCoverImage = goodsCoverImage;
        this.tag = tag;
        this.buyPrice = buyPrice;
        this.buyNum = buyNum;
        this.totalMoney = totalMoney;
        this.createTime = createTime;
    }

    public SFTOrderItem() {
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

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getGoodsCoverImage() {
        return goodsCoverImage;
    }

    public void setGoodsCoverImage(String goodsCoverImage) {
        this.goodsCoverImage = goodsCoverImage == null ? null : goodsCoverImage.trim();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public Long getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Long buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public Long getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Long totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getShopId() {
        return null;
    }

    public void setCartNum(String cartNum) {

    }
}