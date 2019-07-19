package top.wwf.modules.goods.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import top.wwf.modules.goods.entity.SFTGoods;

import java.util.List;

/**
* @Description:    商品按照商店进行划分
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-15 23:53
*/
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class GoodsListGroupByShopVO {
    private String shopId;
    private String shopName;
    private List<SFTGoods> list;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<SFTGoods> getList() {
        return list;
    }

    public void setList(List<SFTGoods> list) {
        this.list = list;
    }
}
