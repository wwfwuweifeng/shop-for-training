package top.wwf.modules.goods.vo;

import top.wwf.modules.goods.entity.SFTGoodsClassify;

import java.util.List;

/**
* @Description:    代表一个一级分类，及其下面的二级分类
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-09 21:38
*/
public class GoodsClassifyVO {
    private Long firstId;
    private String firstClassifyName;
    private List<SFTGoodsClassify> secondGoodsClassifyList;

    public Long getFirstId() {
        return firstId;
    }

    public void setFirstId(Long firstId) {
        this.firstId = firstId;
    }

    public String getFirstClassifyName() {
        return firstClassifyName;
    }

    public void setFirstClassifyName(String firstClassifyName) {
        this.firstClassifyName = firstClassifyName;
    }

    public List<SFTGoodsClassify> getSecondGoodsClassifyList() {
        return secondGoodsClassifyList;
    }

    public void setSecondGoodsClassifyList(List<SFTGoodsClassify> secondGoodsClassifyList) {
        this.secondGoodsClassifyList = secondGoodsClassifyList;
    }
}
