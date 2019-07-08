package top.wwf.modules.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.wwf.common.base.ServerResponse;
import top.wwf.modules.order.service.OrderService;

/**
* @Description:    TODO
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-07 00:03
*/
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 通过购物车提交订单
     * @return
     */
    @ResponseBody
    @RequestMapping("/submitOrderByCart")
    public ServerResponse submitOrderByCart(){
        return null;
    }

    /**
     * 直接在商品页点击购买，提交订单
     * @return
     */
    @ResponseBody
    @RequestMapping("/submitOrderByBuy")
    public ServerResponse submitOrderByBuy(){
        return null;
    }

    /**
     * 通过订单付款，即为一个订单进行付款
     * @return
     */
    @ResponseBody
    @RequestMapping("/payByOrder")
    public ServerResponse payByOrder(){
     return null;
    }

    /**
     * 通过购物车提交订单，并进行多订单付款
     * @return
     */
    @ResponseBody
    @RequestMapping("/payByCart")
    public ServerResponse payByCart(){
        return null;
    }

    /**
     * 接受订单
     * @return
     */
    @ResponseBody
    @RequestMapping("/receipt")
    public ServerResponse receiptOrder(){
        return null;
    }

    /**
     * 为订单发货
     * @return
     */
    @ResponseBody
    @RequestMapping("/send")
    public ServerResponse sendGoodsForOrder(){
        return null;
    }

    /**
     * 签收订单
     * @return
     */
    @ResponseBody
    @RequestMapping("/sign")
    public ServerResponse signOrder(){
        return null;
    }

    /**
     * 取消订单
     * @param role 表示以何种身份进行操作
     * @return
     */
    @ResponseBody
    @RequestMapping("/cancel")
    public ServerResponse cancelOrder(
            @RequestParam(value = "role",defaultValue = "0") int role
    ){
        return null;
    }

    /**
     * 买方获取订单列表，分状态显示，若无搜索条件，则KeyWord设为空字符，分页查询返回
     * @return
     */
    @ResponseBody
    @RequestMapping("/orderListByBuyer")
    public ServerResponse getOrderListByBuyer(
            @RequestParam(value = "state", defaultValue = "0") int state,   //订单状态，默认获取全部
            @RequestParam(value = "keyWord",defaultValue ="") String keyWord,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize",defaultValue = "7") int pageSize
    ){

        return null;
    }

    /**
     * 卖家获取已销售的订单列表，分状态显示，若无搜索条件，则KeyWord设为空字符，分页查询返回
     * @return
     */
    @ResponseBody
    @RequestMapping("/orderListBySeller")
    public ServerResponse getOrderListBySeller(
            @RequestParam(value = "state",defaultValue = "0") int state,   //该字段暂不使用
            @RequestParam(value = "keyWord",defaultValue ="") String keyWord,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize",defaultValue = "7") int pageSize
    ){
        return null;
    }

    /**
     * 获取订单详情页
     * @return
     */
    @ResponseBody
    @RequestMapping("/detail")
    public ServerResponse getOrderDetail(){
        return null;
    }
}
