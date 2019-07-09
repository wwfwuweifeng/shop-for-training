package top.wwf.modules.goods.service;

import com.github.pagehelper.PageHelper;

import com.google.common.collect.Lists;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.wwf.common.base.MySession;
import top.wwf.common.consts.Const;
import top.wwf.common.consts.GoodsConst;
import top.wwf.common.consts.HttpResponseEnum;
import top.wwf.common.exception.MyException;
import top.wwf.common.page.PageBean;
import top.wwf.modules.goods.dao.enhance.GoodsDao;
import top.wwf.modules.goods.entity.SFTGoods;
import top.wwf.modules.goods.entity.SFTGoodsClassify;
import top.wwf.modules.goods.entity.SFTGoodsOperateLog;
import top.wwf.modules.goods.vo.GoodsClassifyVO;
import top.wwf.modules.goods.vo.GoodsDetailForSellerVO;
import java.util.List;

/**
* @Description:    TODO
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-07 00:21
*/
@Service
public class GoodsService {
    @Autowired
    private GoodsDao goodsDao;

    /**
     * 当前的模糊搜索较简单，仅依靠数据库的模糊查询实现
     * @param session
     * @param classifyId
     * @param keyWord
     * @param pageNum
     * @param pageSize
     * @return
     */
    @SuppressWarnings("unchecked")
    public PageBean<SFTGoods> getGoodsListByBuyer(MySession session, Long classifyId, String keyWord, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<SFTGoods> goodsList=goodsDao.getGoodsListForBuyer(classifyId, keyWord, session.getUserId(), GoodsConst.STATE.ON_SALE.getKey());
        //如果为空，生成的page好像不会是0，如果不会，再自己改下吧
        return PageBean.createByPage(goodsList);
    }

    @SuppressWarnings("unchecked")
    public PageBean<SFTGoods> getGoodsListBySeller(MySession session, int state, String keyWord, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<SFTGoods> goodsList=goodsDao.getGoodsListForSeller(state,keyWord,session.getUserId());
        return PageBean.createByPage(goodsList);
    }

    public SFTGoods getGoodsDetailByBuyer(String goodsId) {
        SFTGoods goods=goodsDao.getGoodsByGoodsId(goodsId);
        if (null==goods) {
            throw new MyException(HttpResponseEnum.ERRONEOUS_REQUEST,"无效商品编号");
        }
        return goods;
    }

    public GoodsDetailForSellerVO getGoodsDetailBySeller(MySession session, String goodsId) {
        if (session.getUserRole()!= Const.USER_ROLE.SELLER){
            throw new MyException(HttpResponseEnum.PROHIBIT,"权限不允许");
        }
        SFTGoods goods=goodsDao.getGoodsByGoodsId(goodsId);
        if (null==goods||!goods.getShopOwnerId().equals(session.getUserId())){
            //商品不存在，或不属于当前用户的销售产品
            throw new MyException(HttpResponseEnum.PROHIBIT);
        }
        GoodsConst.STATE       goodsState             = GoodsConst.STATE.getStateByKey(goods.getState());
        GoodsDetailForSellerVO goodsDetailForSellerVO =new GoodsDetailForSellerVO();
        goodsDetailForSellerVO.setGoods(goods);
        goodsDetailForSellerVO.setMsgForMainBt(goodsState.getMsgForMainBt());
        goodsDetailForSellerVO.setAllowClickMainBt(goodsState.getAllowClickMainBt());
        goodsDetailForSellerVO.setAllowLowerShelf(goodsState.getAllowLowerShelf());

        //获取操作日志
        List<SFTGoodsOperateLog> goodsOperateLogList=goodsDao.getGoodsOperateLogListByGoodsId(goodsId);
        SFTGoodsOperateLog goodsOperateLog=new SFTGoodsOperateLog();
        goodsOperateLog.setOperateType(goodsState.getDesc());
        goodsOperateLog.setOperateTime(null);
        goodsOperateLogList.add(0,goodsOperateLog);
        goodsDetailForSellerVO.setOperateLogList(goodsOperateLogList);

        return goodsDetailForSellerVO;
    }


    @Transactional
    public Object operateGoodsStateByManager(MySession session, String goodsId, int operate) {
        if (session.getUserRole()!= Const.USER_ROLE.MANAGER){
            throw new MyException(HttpResponseEnum.PROHIBIT,"权限不允许");
        }
        SFTGoods goods=goodsDao.getGoodsByGoodsId(goodsId);
        if (null==goods){//商品不存在
            throw new MyException(HttpResponseEnum.PROHIBIT);
        }
        GoodsConst.STATE state= GoodsConst.STATE.getStateByKey(goods.getState());
        SFTGoodsOperateLog goodsOperateLog=new SFTGoodsOperateLog();
        if (operate== GoodsConst.OPERATE.APPROVE_FAIL.getKey()&& state== GoodsConst.STATE.WAIT_APPROVE){//审批不通过
            //修改商品状态，并保存
            goods.setState(GoodsConst.STATE.APPROVE_FAIL.getKey());
            goods.setIsSellByManager(Const.NO);
            goodsDao.updateGoodsByPrimaryKey(goods);
            //添加操作日志
            goodsOperateLog.setGoodsId(goodsId);
            goodsOperateLog.setOperateType(session.getUserRole().getDesc()+"："+GoodsConst.OPERATE.APPROVE_FAIL.getDesc());
            goodsDao.addGoodsOperateLog(goodsOperateLog);

        }else if (operate== GoodsConst.OPERATE.APPROVE_SUCCESS.getKey()&&state== GoodsConst.STATE.WAIT_APPROVE){
            //审批通过
            goods.setIsSellByManager(Const.YES);
            goods.setState(
                    goods.getIsSellByShop()==Const.YES? GoodsConst.STATE.ON_SALE.getKey(): GoodsConst.STATE.LOWER_SHELF.getKey()
            );
            goodsDao.updateGoodsByPrimaryKey(goods);
            //添加操作日志
            goodsOperateLog.setGoodsId(goodsId);
            goodsOperateLog.setOperateType(session.getUserRole().getDesc()+"："+GoodsConst.OPERATE.APPROVE_SUCCESS.getDesc());
            goodsDao.addGoodsOperateLog(goodsOperateLog);

        }else if (operate== GoodsConst.OPERATE.LOWER_SHELF.getKey()&&
                (state!= GoodsConst.STATE.LOWER_SHELF&&state!= GoodsConst.STATE.SELL_OUT)){
            //下架商品，由管理员下架的商品，再重新上架时，需要再次审批
            goods.setState(GoodsConst.STATE.LOWER_SHELF.getKey());
            goods.setIsSellByManager(Const.NO);
            goodsDao.updateGoodsByPrimaryKey(goods);
            //添加操作日志
            goodsOperateLog.setGoodsId(goodsId);
            goodsOperateLog.setOperateType(session.getUserRole().getDesc()+"："+GoodsConst.OPERATE.LOWER_SHELF.getDesc());
            goodsDao.addGoodsOperateLog(goodsOperateLog);

        }else {
            throw new MyException(HttpResponseEnum.PROHIBIT);
        }
        //返回数据待定！！！！！！！！！！！！
        return null;
    }

    @Transactional
    public void operateGoodsStateBySeller(MySession session, String goodsId, int operate) {
        if (session.getUserRole()!= Const.USER_ROLE.SELLER){
            throw new MyException(HttpResponseEnum.PROHIBIT,"权限不允许");
        }
        SFTGoods goods=goodsDao.getGoodsByGoodsId(goodsId);
        if (null==goods||!goods.getShopOwnerId().equals(session.getUserId())){
            //商品不存在，或不属于当前用户的销售产品
            throw new MyException(HttpResponseEnum.PROHIBIT);
        }
        GoodsConst.STATE state= GoodsConst.STATE.getStateByKey(goods.getState());
        SFTGoodsOperateLog goodsOperateLog=new SFTGoodsOperateLog();
        if (operate == GoodsConst.OPERATE.APPLY_UPPER_SHELF.getKey()&&
                (state== GoodsConst.STATE.LOWER_SHELF ||state== GoodsConst.STATE.APPROVE_FAIL)){//申请上架
            goods.setIsSellByShop(Const.YES);
            goods.setState(
                    goods.getIsSellByManager()==Const.YES? GoodsConst.STATE.ON_SALE.getKey(): GoodsConst.STATE.WAIT_APPROVE.getKey()
            );
            goodsDao.updateGoodsByPrimaryKey(goods);
            //添加操作日志
            goodsOperateLog.setGoodsId(goodsId);
            goodsOperateLog.setOperateType(session.getUserRole().getDesc()+"："+GoodsConst.OPERATE.APPLY_UPPER_SHELF.getDesc());
            goodsDao.addGoodsOperateLog(goodsOperateLog);

        }else if (operate== GoodsConst.OPERATE.LOWER_SHELF.getKey()&&
                (state!= GoodsConst.STATE.LOWER_SHELF&&state!= GoodsConst.STATE.SELL_OUT)){
            //下架商品
            goods.setIsSellByShop(Const.NO);
            goods.setState(GoodsConst.STATE.LOWER_SHELF.getKey());
            goodsDao.updateGoodsByPrimaryKey(goods);
            //添加操作日志
            goodsOperateLog.setGoodsId(goodsId);
            goodsOperateLog.setOperateType(session.getUserRole().getDesc()+"："+GoodsConst.OPERATE.LOWER_SHELF.getDesc());
            goodsDao.addGoodsOperateLog(goodsOperateLog);
        }else {
            //状态不允许或参数错误
            throw new MyException(HttpResponseEnum.PROHIBIT);
        }
    }

    public List<GoodsClassifyVO> getGoodsClassifyList() {
        List<SFTGoodsClassify> firstGoodsClassifyList=goodsDao.getFirstGoodsClassifyList();
        List<GoodsClassifyVO> goodsClassifyVOList= Lists.newLinkedList();
        GoodsClassifyVO goodsClassifyVO;
        List<SFTGoodsClassify> secondGoodsClassifyList;
        for (SFTGoodsClassify goodsClassify:firstGoodsClassifyList){
            goodsClassifyVO=new GoodsClassifyVO();
            goodsClassifyVO.setFirstClassifyName(goodsClassify.getClassifyName());
            goodsClassifyVO.setFirstId(goodsClassify.getId());
            secondGoodsClassifyList=goodsDao.getSecondClassifyListByParentClassifyId(goodsClassify.getId());
            goodsClassifyVO.setSecondGoodsClassifyList(
                    null==secondGoodsClassifyList?Lists.newLinkedList():secondGoodsClassifyList
            );
            goodsClassifyVOList.add(goodsClassifyVO);
        }
        return goodsClassifyVOList;
    }
}
