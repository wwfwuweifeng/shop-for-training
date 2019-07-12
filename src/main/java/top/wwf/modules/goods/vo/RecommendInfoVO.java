package top.wwf.modules.goods.vo;

import top.wwf.modules.goods.entity.SFTGoods;

import java.util.List;

/**
* @Description:    TODO
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-12 16:50
*/
public class RecommendInfoVO {
    private List<String> recommendImages;
    private List<SFTGoods> newGoodsList;    //最大长度为3
    private List<SFTGoods> hotSellGoodsList;    //最大长度为3

    public List<String> getRecommendImages() {
        return recommendImages;
    }

    public void setRecommendImages(List<String> recommendImages) {
        this.recommendImages = recommendImages;
    }

    public List<SFTGoods> getNewGoodsList() {
        return newGoodsList;
    }

    public void setNewGoodsList(List<SFTGoods> newGoodsList) {
        this.newGoodsList = newGoodsList;
    }

    public List<SFTGoods> getHotSellGoodsList() {
        return hotSellGoodsList;
    }

    public void setHotSellGoodsList(List<SFTGoods> hotSellGoodsList) {
        this.hotSellGoodsList = hotSellGoodsList;
    }
}
