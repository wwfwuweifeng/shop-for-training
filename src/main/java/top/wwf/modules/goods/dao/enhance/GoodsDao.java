package top.wwf.modules.goods.dao.enhance;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.wwf.modules.goods.dao.SFTGoodsClassifyMapper;
import top.wwf.modules.goods.dao.SFTGoodsMapper;
import top.wwf.modules.goods.dao.SFTGoodsOperateLogMapper;
import top.wwf.modules.goods.dao.SFTGoodsParamMapper;
import top.wwf.modules.goods.entity.SFTGoods;
import top.wwf.modules.goods.entity.SFTGoodsClassify;
import top.wwf.modules.goods.entity.SFTGoodsOperateLog;
import top.wwf.modules.goods.entity.SFTGoodsParam;

import java.util.List;

/**
* @Description:    TODO
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-07 00:08
*/
@Repository
public class GoodsDao {
    @Autowired
    private SFTGoodsMapper         goodsMapper;
    @Autowired
    private SFTGoodsClassifyMapper goodsClassifyMapper;
    @Autowired
    private SFTGoodsParamMapper    goodsParamMapper;
    @Autowired
    private SFTGoodsOperateLogMapper goodsOperateLogMapper;

    public List<SFTGoods> getNewGoodsList(int listSize) {
        return goodsMapper.selectNewGoodsListByListSize(listSize);
    }

    public SFTGoods getGoodsByGoodsId(String goodsId) {
        return goodsMapper.selectByGoodsId(goodsId);
    }

    /**
     * order By createTime
     * @param goodsIdList
     * @return
     */
    public List<SFTGoods> getOnSellGoodsByGoodsIdList(List<String> goodsIdList,int state) {
        return goodsMapper.selectByGoodsIdListAndState(goodsIdList,state);
    }

    /**
     * order by sellNum
     * @param classifyId
     * @param keyword
     * @param userId   商品出售商家的id不能与此相同，预留参数，当前暂不使用
     * @param state
     * @return
     */
    public List<SFTGoods> getGoodsListForBuyer(Long classifyId,String shopId ,String keyword, String userId, int state) {
        if (StringUtils.isBlank(keyword)){keyword=null;}
        if (StringUtils.isBlank(shopId)){shopId=null;}
        return goodsMapper.selectListByStateAndClassifyIdAndShopIdAndKeyword(state, classifyId, shopId,keyword);
    }

    /**
     * order by updateTime
     * @param state 商品状态，sql进行实现，前端有时间再补上
     * @param keyword
     * @param shopId
     * @return
     */
    public List<SFTGoods> getGoodsListForSeller(int state, String shopId,String keyword) {
        if (StringUtils.isBlank(keyword)){keyword=null;}
        return goodsMapper.selectListByStateAndShopIdAndKeyword(state,shopId,keyword);
    }


    public void updateGoodsByPrimaryKey(SFTGoods goods) {
        goodsMapper.updateByPrimaryKeySelective(goods);
    }

    public void addGoodsOperateLog(SFTGoodsOperateLog goodsOperateLog) {
        goodsOperateLogMapper.insertSelective(goodsOperateLog);
    }

    /**
     * order by operateTime desc !!!
     * @param goodsId
     * @return
     */
    public List<SFTGoodsOperateLog> getGoodsOperateLogListByGoodsId(String goodsId) {
        return goodsOperateLogMapper.selectListByGoodsId(goodsId);
    }

    /**
     * parent_classify_id  为0
     * order by createTime
     * @return
     */
    public List<SFTGoodsClassify> getFirstGoodsClassifyList() {
        return goodsClassifyMapper.selectFirstGoodsClassifyList();
    }

    /**
     * order by createTime
     * @param parentClassifyId
     * @return
     */
    public List<SFTGoodsClassify> getSecondClassifyListByParentClassifyId(Long parentClassifyId) {
        return goodsClassifyMapper.selectSecondClassifyListByParentClassifyId(parentClassifyId);
    }

    /**
     * 如果为空会不会报错？？？？忘了
     * 获取购物车中显示的商品信息，只获取需要的字段，其中主键id,数量和状态必须要有
     * @param goodsIdList
     * @return
     */
    public List<SFTGoods> getSimpleGoodsInfoListByGoodsIdList(List<String> goodsIdList) {
        return goodsMapper.selectSimpleInfoListByGoodsIdList(goodsIdList);
    }

    /**
     * order by createTime
     * @param goodsId
     * @return
     */
    public List<SFTGoodsParam> getGoodsParamListByGoodsId(String goodsId) {
        return goodsParamMapper.selectListByGoodsId(goodsId);
    }

    /**
     * 下架某个商店的所有销售商品
     * @param state 下架商品需要设置的状态
     * @param shopId
     */
    public void lowerShelfGoodsByShopId(int state, String shopId) {
        goodsMapper.updateStateByShopId(state,shopId);
    }

    public List<SFTGoods> getHotSellGoodsList(int listSize) {
        return goodsMapper.selectHotSellGoodsListByListSize(listSize);
    }
}
