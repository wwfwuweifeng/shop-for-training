package top.wwf.modules.goods.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import top.wwf.common.base.MySession;
import top.wwf.common.base.ServerResponse;
import top.wwf.common.page.PageBean;
import top.wwf.modules.goods.entity.SFTGoods;
import top.wwf.modules.goods.entity.SFTGoodsParam;
import top.wwf.modules.goods.service.GoodsService;
import top.wwf.modules.goods.vo.*;

import java.util.List;

/**
* @Description:    TODO
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-07 00:02
*/
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    /**
     * 买方获取商品列表，若无搜索条件，则KeyWord设为空字符，分页查询返回
     * 只有处于上架状态的商品会返回，自己出售的商品也会出现在此列表中
     * @return
     */
    @ResponseBody
    @RequestMapping("/goodsListByBuyer")
    public ServerResponse getGoodsListByBuyer(
            @RequestParam(value = "classifyId",defaultValue = "0") Long classifyId,
            @RequestParam(value = "shopId",defaultValue = "")String shopId,
            @RequestParam(value = "keyword",defaultValue ="") String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize",defaultValue = "7") int pageSize
            ){
        MySession session=MySession.getInstance();
        PageBean<SFTGoods> result=goodsService.getGoodsListByBuyer(session, classifyId, shopId, keyword, pageNum, pageSize);
        return ServerResponse.create(result);
    }

    /**
     * 卖家获取自己的商品列表，有时间再考虑分状态获取和显示
     * @return
     */
    @ResponseBody
    @RequestMapping("/goodsListBySeller")
    public ServerResponse getGoodsListBySeller(
            @RequestParam(value = "state",defaultValue = "0") int state,   //该字段暂不使用
            @RequestParam(value = "keyword",defaultValue ="") String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize",defaultValue = "7") int pageSize
    ){
        MySession session=MySession.getInstance();
        PageBean<SFTGoods> result=goodsService.getGoodsListBySeller(session,state,keyword,pageNum,pageSize);
        return ServerResponse.create(result);
    }

    /**
     * 获取商品的详细信息，卖家也可以查看，但是到达提交订单环节，会被拦截
     * @return
     */
    @ResponseBody
    @RequestMapping("/detailByBuyer")
    public ServerResponse getGoodsDetailByBuyer(
            @RequestParam(value = "goodsId",defaultValue = "") String goodsId

    ){
        GoodsDetailForBuyerVO result =goodsService.getGoodsDetailByBuyer(goodsId);
        return ServerResponse.create(result);
    }

    /**
     * 卖家获取商品的销售信息，当前状态信息等
     * @return
     */
    @ResponseBody
    @RequestMapping("/detailBySeller")
    public ServerResponse getGoodsDetailBySeller(
            @RequestParam(value = "goodsId",defaultValue = "") String goodsId){
        MySession session=MySession.getInstance();
        GoodsDetailForSellerVO result=goodsService.getGoodsDetailBySeller(session,goodsId);
        return ServerResponse.create(result);
    }


    /**
     * 卖家添加商品，预留，看是使用小程序端，还是web端来实现
     * 已废弃
     * @return
     */
    @ResponseBody
    @RequestMapping("/addGoodsBySeller")
    public ServerResponse addGoodsBySeller(){

        return null;
    }

    /**
     * 卖家修改商品的详细信息：含修改数量，修改描述等，不含修改状态；
     * 目前为覆盖修改，有时间再考虑新增记录，然后使用版本记录！！！
     * @return
     */
    @ResponseBody
    @RequestMapping("/editGoodsDetailBySellerWithImage")
    public ServerResponse editGoodsBySellerWithImage(MultipartFile imageFile,String name,String strPrice,String detail,
    String goodsId,String tag,int remainNum,String classifyName,String goodsParamList){
        MySession session=MySession.getInstance();
        SFTGoods goods=new SFTGoods();
        goods.setGoodsId(goodsId);
        goods.setName(name);
        goods.setRemainNum(remainNum);
        goods.setPrice((new Double(Double.parseDouble(strPrice)*100)).longValue());
        goods.setClassifyName(classifyName);
        goods.setTag(tag);
        goods.setDetail(detail);
        List<SFTGoodsParam> paramList = JSONObject.parseArray(goodsParamList, SFTGoodsParam.class);
        SFTGoods result=goodsService.editGoodsBySellerWithImage(session,goods,imageFile,paramList);
        return ServerResponse.create(result);
    }

    @ResponseBody
    @RequestMapping("/editGoodsDetailBySellerWithOutImage")
    public ServerResponse editGoodsBySellerWithOutImage(String name,String strPrice,String detail,
            String goodsId,String tag,int remainNum,String classifyName,String goodsParamList){
        MySession session=MySession.getInstance();
        SFTGoods goods=new SFTGoods();
        goods.setGoodsId(goodsId);
        goods.setName(name);
        goods.setRemainNum(remainNum);
        goods.setPrice((new Double(Double.parseDouble(strPrice)*100)).longValue());
        goods.setClassifyName(classifyName);
        goods.setTag(tag);
        goods.setDetail(detail);
        List<SFTGoodsParam> paramList = JSONObject.parseArray(goodsParamList, SFTGoodsParam.class);
        SFTGoods result=goodsService.editGoodsBySellerWithOutImage(session,goods,paramList);
        return ServerResponse.create(result);
    }
    /**
     * 管理员编辑商品状态
     * @param operate 操作行为，将对应至一个枚举类
     * @return
     */
    @ResponseBody
    @RequestMapping("/operateGoodsStateByManager")
    public ServerResponse operateGoodsStateByManager(
            @RequestParam(value = "goodsId",defaultValue = "") String goodsId,
            @RequestParam(value = "operate",defaultValue = "0") int operate
    ){
        MySession session=MySession.getInstance();
        SFTGoods result=goodsService.operateGoodsStateByManager(session,goodsId,operate);
        return ServerResponse.create(result);
    }

    /**
     * 销售商编辑商品状态
     * @param goodsId
     * @param operate
     * @return
     */
    @ResponseBody
    @RequestMapping("/operateGoodsStateBySeller")
    public ServerResponse operateGoodsStateBySeller(
            @RequestParam(value = "goodsId",defaultValue = "") String goodsId,
            @RequestParam(value = "operate",defaultValue = "0") int operate
    ){
        MySession session=MySession.getInstance();
        goodsService.operateGoodsStateBySeller(session,goodsId,operate);
        GoodsDetailForSellerVO result=goodsService.getGoodsDetailBySeller(session,goodsId);
        return ServerResponse.create(result);
    }

    /**
     * 获取商品的分类列表，用于客户端
     * @return
     */
    @ResponseBody
    @RequestMapping("/classifyList")
    public ServerResponse getGoodsClassifyList(){
        List<GoodsClassifyVO> result=goodsService.getGoodsClassifyList();
        return ServerResponse.create(result);
    }

    /**
     * 添加商品分类，目前只支持添加二级分类
     * 返回结果待定
     * @return
     */
    @ResponseBody
    @RequestMapping("/addClassify")
    public ServerResponse addGoodsClassify(Long parentId,String classifyName,MultipartFile image){
        MySession session=MySession.getInstance();
        goodsService.addGoodsClassify(session,parentId,classifyName,image);
        return ServerResponse.create(goodsService.getGoodsClassifyListForManager(session));
    }


    /**
     * 只限管理员使用，此部分数据不会过多，因此不使用分页
     * @return
     */
    @ResponseBody
    @RequestMapping("/goodsClassifyListForManager")
    public ServerResponse getGoodsClassifyListForManager(){
        MySession                     session =MySession.getInstance();
        GoodsClassifyListForManagerVO result  =goodsService.getGoodsClassifyListForManager(session);
        return ServerResponse.create(result);
    }




    /**
     * 获取推荐首页的内容
     * @return
     */
    @ResponseBody
    @RequestMapping("/recommend")
    public ServerResponse getRecommendInfo(){
        RecommendInfoVO result =goodsService.getRecommendInfo();
        return ServerResponse.create(result);
    }

    @ResponseBody
    @RequestMapping("/delGoodsClassify")
    public ServerResponse delGoodsClassify(Long goodsClassifyId){
        MySession session=MySession.getInstance();
        goodsService.delGoodsClassify(session,goodsClassifyId);
        return ServerResponse.create(goodsService.getGoodsClassifyListForManager(session));
    }

    /**
     * 买方获取商品列表，若无搜索条件，则KeyWord设为空字符，分页查询返回
     * 只有处于上架状态的商品会返回，自己出售的商品也会出现在此列表中
     * @return
     */
    @ResponseBody
    @RequestMapping("/goodsListByManager")
    public ServerResponse getGoodsListByManager(
            @RequestParam(value = "classifyId",defaultValue = "0") Long classifyId,
            @RequestParam(value = "shopId",defaultValue = "")String shopId,
            @RequestParam(value = "keyword",defaultValue ="") String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize",defaultValue = "7") int pageSize
    ){
        MySession session=MySession.getInstance();
        PageBean<SFTGoods> result=goodsService.getGoodsListByManager(session, classifyId, shopId, keyword, pageNum, pageSize);
        return ServerResponse.create(result);
    }
    /**
     * 该接口只进行登录状态检验，不进行权限检验
     * 暂时废弃该接口
     * @param goodsIdList
     * @return
     */
//    @ResponseBody
//    @RequestMapping("/groupByShop")
//    public ServerResponse getGoodsListGroupByShop(List<String> goodsIdList){
//        MySession                    session =MySession.getInstance();
//        List<GoodsListGroupByShopVO> result  =goodsService.getGoodsListBygoodsIdList(goodsIdList);
//        return ServerResponse.create(result);
//    }
}
