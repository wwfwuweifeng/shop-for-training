package top.wwf.modules.goods.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SFTGoods {
    @JsonIgnore
    private Long id;

    private String goodsId;

    private String name;

    private Long classifyId;

    private String classifyName;

    private String coverImage;

    private Integer remainNum;

    private Integer sellNum;

    private String shopId;
    private String strPrice;

    private String shopName;
    @JsonIgnore
    private String shopOwnerId;

    private String tag;

    //此字段不对应数据库中的字段
    private List<String> tagList;

    private String detail;

    private Long price;

    private Integer state;
    @JsonIgnore
    private Integer isSellByShop;
    @JsonIgnore
    private Integer isSellByManager;
    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date updateTime;

    public SFTGoods(Long id, String goodsId, String name, Long classifyId, String classifyName, String coverImage, Integer remainNum, Integer sellNum, String shopId, String shopName, String shopOwnerId, String tag, String detail, Long price, Integer state, Integer isSellByShop, Integer isSellByManager, Date createTime, Date updateTime) {
        this.id = id;
        this.goodsId = goodsId;
        this.name = name;
        this.classifyId = classifyId;
        this.classifyName = classifyName;
        this.coverImage = coverImage;
        this.remainNum = remainNum;
        this.sellNum = sellNum;
        this.shopId = shopId;
        this.shopName = shopName;
        this.shopOwnerId = shopOwnerId;
        this.tag = tag;
        this.detail = detail;
        this.price = price;
        this.state = state;
        this.isSellByShop = isSellByShop;
        this.isSellByManager = isSellByManager;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public SFTGoods() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName == null ? null : classifyName.trim();
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage == null ? null : coverImage.trim();
    }

    public Integer getRemainNum() {
        return remainNum;
    }

    public void setRemainNum(Integer remainNum) {
        this.remainNum = remainNum;
    }

    public Integer getSellNum() {
        return sellNum;
    }

    public void setSellNum(Integer sellNum) {
        this.sellNum = sellNum;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId == null ? null : shopId.trim();
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    public String getShopOwnerId() {
        return shopOwnerId;
    }

    public void setShopOwnerId(String shopOwnerId) {
        this.shopOwnerId = shopOwnerId == null ? null : shopOwnerId.trim();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getIsSellByShop() {
        return isSellByShop;
    }

    public void setIsSellByShop(Integer isSellByShop) {
        this.isSellByShop = isSellByShop;
    }

    public Integer getIsSellByManager() {
        return isSellByManager;
    }

    public void setIsSellByManager(Integer isSellByManager) {
        this.isSellByManager = isSellByManager;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<String> getTagList() {
        if (StringUtils.isBlank(this.tag)){
            return Lists.newLinkedList();
        }else {
            return Arrays.asList(tag.split("&"));
        }
    }
    public String getStrPrice(){
        return String.format("%10.2f", price/100.0);
    }
}