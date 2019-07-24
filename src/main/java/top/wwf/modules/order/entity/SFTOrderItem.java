package top.wwf.modules.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SFTOrderItem {
    @JsonIgnore
    private Long id;

    private String orderId;

    private String goodsId;

    private String goodsName;

    private String goodsCoverImage;

    private String cartNum;

    private String shopId;

    private String tag;

    private Long buyPrice;
    private String strBuyPrice;

    //此字段不对应数据库中的字段
    private List<String> tagList;

    private Integer buyNum;

    private Long totalMoney;
    @JsonIgnore
    private Date createTime;

    public SFTOrderItem(Long id, String orderId, String goodsId, String goodsName, String goodsCoverImage, String cartNum, String shopId, String tag, Long buyPrice, Integer buyNum, Long totalMoney, Date createTime) {
        this.id = id;
        this.orderId = orderId;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsCoverImage = goodsCoverImage;
        this.cartNum = cartNum;
        this.shopId = shopId;
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

    public String getCartNum() {
        return cartNum;
    }

    public void setCartNum(String cartNum) {
        this.cartNum = cartNum == null ? null : cartNum.trim();
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId == null ? null : shopId.trim();
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

    public List<String> getTagList() {
        if (StringUtils.isBlank(this.tag)){
            return Lists.newLinkedList();
        }else {
            return Arrays.asList(tag.split("&"));
        }
    }
    public String getStrBuyPrice(){
        return String.format("%10.2f", buyPrice/100.0);
    }
}