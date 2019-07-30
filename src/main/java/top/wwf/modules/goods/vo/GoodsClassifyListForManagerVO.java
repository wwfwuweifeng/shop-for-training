package top.wwf.modules.goods.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import top.wwf.modules.goods.entity.SFTGoodsClassify;

import java.util.List;
import java.util.Map;

public class GoodsClassifyListForManagerVO {
    private List<SFTGoodsClassify> firstGoodsClassifyList;
    private List<SecondClassify> secondGoodsClassifyList;

    @JsonIgnore
    private Map<Long,String> firstGoodsClassifyMap;

    private class SecondClassify{
        private Long classifyId;
        private String parentClassifyName;
        private String classifyName;
        private String coverImage;

        public SecondClassify(Long classifyId,String parentClassifyName, String classifyName, String coverImage) {
            this.classifyId=classifyId;
            this.parentClassifyName = parentClassifyName;
            this.classifyName = classifyName;
            this.coverImage = coverImage;
        }

        public Long getClassifyId() {
            return classifyId;
        }

        public String getParentClassifyName() {
            return parentClassifyName;
        }

        public String getClassifyName() {
            return classifyName;
        }

        public String getCoverImage() {
            return coverImage;
        }
    }

    public List<SFTGoodsClassify> getFirstGoodsClassifyList() {
        return firstGoodsClassifyList;
    }

    public void setFirstGoodsClassifyList(List<SFTGoodsClassify> firstGoodsClassifyList) {
        firstGoodsClassifyMap= Maps.newHashMap();
        this.firstGoodsClassifyList = firstGoodsClassifyList;
        for (SFTGoodsClassify goodsClassify:firstGoodsClassifyList){
            firstGoodsClassifyMap.put(goodsClassify.getId(),goodsClassify.getClassifyName());
        }
    }

    public List<SecondClassify> getSecondGoodsClassifyList() {
        return secondGoodsClassifyList;
    }

    public void setSecondGoodsClassifyList(List<SFTGoodsClassify> goodsClassifyList) {
        List<SecondClassify> secondGoodsClassifyList= Lists.newLinkedList();
        SecondClassify secondClassify;
        for (SFTGoodsClassify goodsClassify:goodsClassifyList) {
            secondClassify = new SecondClassify(goodsClassify.getId(),firstGoodsClassifyMap.get(goodsClassify.getId()),
                                                goodsClassify.getClassifyName(), goodsClassify.getCoverImage());
            secondGoodsClassifyList.add(secondClassify);
        }
        this.secondGoodsClassifyList=secondGoodsClassifyList;
    }
}
