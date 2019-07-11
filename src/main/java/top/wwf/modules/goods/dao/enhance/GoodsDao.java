package top.wwf.modules.goods.dao.enhance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.wwf.common.consts.Const;
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

    public SFTGoods getGoodsByGoodsId(String goodsId) {
        return null;
    }

    /**
     * order By createTime
     * @param goodsIdList
     * @return
     */
    public List<SFTGoods> getOnSellGoodsByGoodsIdList(List<String> goodsIdList,int state) {
        return null;
    }

    /**
     * order by sellNum
     * @param classifyId
     * @param keyWord
     * @param userId   商品出售商家的id不能与此相同，预留参数，暂时不使用
     * @param state
     * @return
     */
    public List<SFTGoods> getGoodsListForBuyer(Long classifyId, String keyWord, String userId, int state) {
        return null;
    }

    /**
     * order by updateTime
     * @param state 商品状态，sql进行实现，前端有时间再补上
     * @param keyWord
     * @param userId
     * @return
     */
    public List<SFTGoods> getGoodsListForSeller(int state, String keyWord, String userId) {
        return null;
    }

    /**
     * 下架某个用户的所有销售商品
     * @param state 下架商品需要设置的状态
     * @param userId
     */
    public void lowerShelfGoodsByUserId(int state, String userId) {

    }

    public void updateGoodsByPrimaryKey(SFTGoods goods) {

    }

    public void addGoodsOperateLog(SFTGoodsOperateLog goodsOperateLog) {

    }

    /**
     * order by operateTime desc !!!
     * @param goodsId
     * @return
     */
    public List<SFTGoodsOperateLog> getGoodsOperateLogListByGoodsId(String goodsId) {
        return null;
    }

    /**
     * parent_classify_id  为0
     * order by createTime
     * @return
     */
    public List<SFTGoodsClassify> getFirstGoodsClassifyList() {
        return null;
    }

    /**
     * order by createTime
     * @param parentClassifyId
     * @return
     */
    public List<SFTGoodsClassify> getSecondClassifyListByParentClassifyId(Long parentClassifyId) {
        return null;
    }

    /**
     * 获取购物车中显示的商品信息，只获取需要的字段，其中主键id,数量和状态必须要有
     * @param goodsIdList
     * @return
     */
    public List<SFTGoods> getSimpleGoodsInfoListByGoodsIdList(List<String> goodsIdList) {
        return null;
    }

    /**
     * order by createTime
     * @param goodsId
     * @return
     */
    public List<SFTGoodsParam> getGoodsParamListByGoodsId(String goodsId) {
        return null;
    }
}
