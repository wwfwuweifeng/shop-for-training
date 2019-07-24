package top.wwf.modules.goods.dto;

import org.springframework.web.multipart.MultipartFile;
import top.wwf.modules.goods.entity.SFTGoods;
import top.wwf.modules.goods.entity.SFTGoodsParam;

import java.util.List;
import java.util.Map;

/**
* @Description:    TODO
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-24 00:15
*/
public class EditGoodsDTO {
    private String token;
    private SFTGoods goods;
    private MultipartFile imageFile;
    private List<SFTGoodsParam> goodsParamList;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public SFTGoods getGoods() {
        return goods;
    }

    public void setGoods(SFTGoods goods) {
        this.goods = goods;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public List<SFTGoodsParam> getGoodsParamList() {
        return goodsParamList;
    }

    public void setGoodsParamList(List<SFTGoodsParam> goodsParamList) {
        this.goodsParamList = goodsParamList;
    }
}
