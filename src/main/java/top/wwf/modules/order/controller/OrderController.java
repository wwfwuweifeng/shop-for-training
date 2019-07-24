package top.wwf.modules.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.wwf.common.base.MySession;
import top.wwf.common.base.ServerResponse;
import top.wwf.common.consts.Const;
import top.wwf.common.page.PageBean;
import top.wwf.modules.order.dto.SubmitOrderDTO;
import top.wwf.modules.order.service.OrderService;
import top.wwf.modules.order.vo.OrderInfoVO;
import top.wwf.modules.order.vo.SubmitOrderVO;


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
     * 通过购物车提交订单，将订单按商店进行分解，最后返回所有订单总的付款金额
     * @return
     */
    @ResponseBody
    @RequestMapping("/submitOrderByCart")
    public ServerResponse submitOrderByCart( @RequestBody SubmitOrderDTO submitOrderDTO){
        MySession session=MySession.getInstanceByToken(submitOrderDTO.getToken());
        SubmitOrderVO result  =orderService.submitOrderByCart(session,submitOrderDTO.getCartList(),submitOrderDTO.getTotalMoney(),
                                                              submitOrderDTO.getReceiverPeople(),submitOrderDTO.getReceiverAddress());
        return ServerResponse.create(result);
    }

    /**
     * 直接在商品页点击购买，提交订单
     * @return
     */
    @ResponseBody
    @RequestMapping("/submitOrderByBuy")
    public ServerResponse submitOrderByBuy(@RequestBody SubmitOrderDTO submitOrderDTO){   //还是将提交的参数封装成一个购物车项
        MySession session=MySession.getInstanceByToken(submitOrderDTO.getToken());
        SubmitOrderVO result=orderService.submitOrderByBuy(session,submitOrderDTO.getCart(),submitOrderDTO.getTotalMoney(),
                                                           submitOrderDTO.getReceiverPeople(),submitOrderDTO.getReceiverAddress());
        return ServerResponse.create(result);
    }

    /**
     * 有时间使用微信支付进行接入
     * 通过订单付款，即为一个订单进行付款，此部分先预留，看后面是否接入微信支付
     * @return
     */
    @ResponseBody
    @RequestMapping("/payByOrder")
    public ServerResponse payByOrder(String orderId,Long totalMoney,int payType){
        MySession session=MySession.getInstance();
        orderService.payByOrder(session,orderId,totalMoney,payType);
        return ServerResponse.create();
    }

    /**
     * 通过购物车提交订单，并进行多订单付款，此部分先预留，看后面是否接入微信支付
     * @return
     */
    @ResponseBody
    @RequestMapping("/payByCart")
    public ServerResponse payByCart(String cartNum,Long totalMoney,int payType){
        MySession session=MySession.getInstance();
        orderService.payByCart(session,cartNum,totalMoney,payType);
        return ServerResponse.create();
    }

    /**
     * 接受订单
     * @return
     */
    @ResponseBody
    @RequestMapping("/receipt")
    public ServerResponse receiptOrder(String orderId){
        MySession session=MySession.getInstance();
        orderService.receiptOrder(session,orderId);
        //获取最新的订单页信息返回
        OrderInfoVO result=orderService.getOrderDetail(session, orderId, Const.USER_ROLE.SELLER.getKey());
        return ServerResponse.create(result);
    }

    /**
     * 为订单发货
     * @return
     */
    @ResponseBody
    @RequestMapping("/send")
    public ServerResponse sendGoodsForOrder(String orderId,String expressNum){
        MySession session=MySession.getInstance();
        orderService.sendGoodsForOrder(session,orderId,expressNum);
        OrderInfoVO result=orderService.getOrderDetail(session, orderId, Const.USER_ROLE.SELLER.getKey());
        return ServerResponse.create(result);
    }

    /**
     * 签收订单
     * @return
     */
    @ResponseBody
    @RequestMapping("/sign")
    public ServerResponse signOrder(String orderId){
        MySession session=MySession.getInstance();
        orderService.signOrder(session,orderId);
        OrderInfoVO result=orderService.getOrderDetail(session, orderId, Const.USER_ROLE.BUYER.getKey());
        return ServerResponse.create(result);
    }

    /**
     * 取消订单，含买家取消订单，卖家拒绝接单，管理员取消订单 三种情况
     * @param role 表示以何种身份进行操作
     * @return
     */
    @ResponseBody
    @RequestMapping("/cancel")
    public ServerResponse cancelOrder(String orderId,
            @RequestParam(value = "role",defaultValue = "0") int role
    ){
        MySession session=MySession.getInstance();
        orderService.cancelOrder(session,role,orderId);
        OrderInfoVO result=orderService.getOrderDetail(session, orderId,role);
        return ServerResponse.create(result);
    }

    /**
     * 买方获取订单列表，分状态显示，若无搜索条件，则KeyWord设为空字符，分页查询返回
     * 支持订单号、商店名、付款编号、收货人、收获地址、快递编号的模糊查询
     * @return
     */
    @ResponseBody
    @RequestMapping("/orderListByBuyer")
    public ServerResponse getOrderListByBuyer(
            @RequestParam(value = "state", defaultValue = "0") int state,   //订单状态，默认获取全部
            @RequestParam(value = "keyword",defaultValue ="") String keyWord,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize",defaultValue = "7") int pageSize
    ){
        MySession session               =MySession.getInstance();
        PageBean  orderSimpleInfoVOList =orderService.getOrderListByBuyer(session, state, keyWord, pageNum, pageSize);
        return ServerResponse.create(orderSimpleInfoVOList);
    }

    /**
     * 卖家获取已销售的订单列表，分状态显示，若无搜索条件，则KeyWord设为空字符，分页查询返回
     * 支持订单号、付款编号、收货人、收获地址、快递编号的模糊查询
     * @return
     */
    @ResponseBody
    @RequestMapping("/orderListBySeller")
    public ServerResponse getOrderListBySeller(
            @RequestParam(value = "state",defaultValue = "0") int state,   //该字段暂不使用
            @RequestParam(value = "keyword",defaultValue ="") String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize",defaultValue = "7") int pageSize
    ){
        MySession session               =MySession.getInstance();
        PageBean  orderSimpleInfoVOList =orderService.getOrderListBySeller(session, state, keyword, pageNum, pageSize);
        return ServerResponse.create(orderSimpleInfoVOList);
    }

    /**
     * 获取订单详情页
     * @return
     */
    @ResponseBody
    @RequestMapping("/detail")
    public ServerResponse getOrderDetail(String orderId,
            @RequestParam(value = "role",defaultValue = "0") int role){
        MySession session=MySession.getInstance();
        OrderInfoVO result=orderService.getOrderDetail(session,orderId,role);
        return ServerResponse.create(result);
    }
}
