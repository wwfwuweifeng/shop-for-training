package top.wwf.modules.goods.dao.enhance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.wwf.modules.goods.dao.SFTGoodsClassifyMapper;
import top.wwf.modules.goods.dao.SFTGoodsMapper;
import top.wwf.modules.goods.dao.SFTGoodsParamMapper;
import top.wwf.modules.goods.entity.SFTGoods;

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

    public SFTGoods getGoodsByGoodsId(String goodsId) {
        return null;
    }

    /**
     * order By createTime
     * @param goodsIdList
     * @return
     */
    public List<SFTGoods> getGoodsListByGoodsIdList(List<String> goodsIdList) {
        return null;
    }
}
