package top.wwf.modules.goods.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.wwf.common.base.ServerResponse;
import top.wwf.modules.goods.service.GoodsService;

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
     * @return
     */
    @ResponseBody
    @RequestMapping("/goodsListByBuyer")
    public ServerResponse getGoodsListByBuyer(
            @RequestParam(value = "classifyId",defaultValue = "0") Long classifyId,
            @RequestParam(value = "keyWord",defaultValue ="") String keyWord,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize",defaultValue = "7") int pageSize
            ){

        return null;
    }

    /**
     * 卖家获取自己的商品列表，有时间再考虑分状态获取和显示
     * @return
     */
    @ResponseBody
    @RequestMapping("/goodsListBySeller")
    public ServerResponse getGoodsListBySeller(
            @RequestParam(value = "classifyId",defaultValue = "0") int state,   //该字段暂不使用
            @RequestParam(value = "keyWord",defaultValue ="") String keyWord,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize",defaultValue = "7") int pageSize
    ){
        return null;
    }

    /**
     * 获取商品的详细信息
     * @return
     */
    @ResponseBody
    @RequestMapping("/detail")
    public ServerResponse getGoodsDetail(){
        return null;
    }

    /**
     * 卖家添加商品
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
    @RequestMapping("/editGoodsDetailBySeller")
    public ServerResponse editGoodsBySeller(){
        return null;
    }

    /**
     * 编辑商品状态，管理员和卖家均可操作
     * @param operate 操作行为，将对应至一个枚举类
     * @return
     */
    @ResponseBody
    @RequestMapping("/editGoodsState")
    public ServerResponse editGoodsState(
            @RequestParam(value = "operate",defaultValue = "0") int operate
    ){
        return null;
    }


}
