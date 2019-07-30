package top.wwf.modules.order.service;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import top.wwf.common.base.DateYMDHMSJsonSerializer;
import top.wwf.common.base.MySession;
import top.wwf.common.consts.Const;
import top.wwf.common.consts.GoodsConst;
import top.wwf.common.consts.HttpResponseEnum;
import top.wwf.common.consts.OrderConst;
import top.wwf.common.exception.MyException;
import top.wwf.common.page.PageBean;
import top.wwf.common.utils.IdGenUtils;
import top.wwf.common.utils.JedisUtils;
import top.wwf.modules.cart.dao.enhance.CartDao;
import top.wwf.modules.cart.entity.SFTCart;
import top.wwf.modules.goods.dao.enhance.GoodsDao;
import top.wwf.modules.goods.entity.SFTGoods;
import top.wwf.modules.order.dao.enhance.OrderDao;
import top.wwf.modules.order.entity.SFTOrder;
import top.wwf.modules.order.entity.SFTOrderItem;
import top.wwf.modules.order.entity.SFTOrderOperateLog;
import top.wwf.modules.order.entity.SFTOrderPay;
import top.wwf.modules.order.vo.OrderInfoVO;
import top.wwf.modules.order.vo.OrderSimpleInfoVO;
import top.wwf.modules.order.vo.SubmitOrderVO;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @Description:    TODO
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-07 00:22
*/
@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private CartDao cartDao;


    /**
     * 当该bean初始化时，自动执行
     * 从数据库当中读取order表中Id的最大值，存入redis当中
     */
    @PostConstruct
    private void initOrderMaxIdToRedis(){
        Long nowMaxId=orderDao.getOrderNowMaxId();
        nowMaxId = null==nowMaxId?0:nowMaxId;
        JedisUtils.set(Const.ORDER_MAX_ID, nowMaxId.toString());
    }

    /**
     * 权限检查（含是否是自卖自买的行为检查）--->查询最新商品数据，并锁住相应的商品行--->货物剩余库存检查---
     * --->根据商店分解，生成多个订单--->计算总额--->结束
     * 使用了读已提交隔离级别！！！！
     * @param session
     * @param carts  用户提交的，要购买的商品列表
     * @return
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public SubmitOrderVO submitOrderByCart(MySession session, List<SFTCart> carts,Long totalMoney,String receiverPeople,String receiverAddress) {
        Const.USER_ROLE userRole=session.getUserRole();

        //权限检查（含是否是自卖自买的行为检查）
        if (userRole== Const.USER_ROLE.MANAGER){
            throw new MyException(HttpResponseEnum.PROHIBIT);    //管理员无购买权限
        }
        List<String>              orderGoodsIdList = Lists.newLinkedList();
        Map<String, SFTOrder>     orderMap         = Maps.newHashMap();   //key为shopId
        Map<String, SFTOrderItem> orderItemMap     =Maps.newHashMap();    //key为goodsId

        SubmitOrderVO submitOrderVO =new SubmitOrderVO();
        submitOrderVO.setCartNum(IdGenUtils.uuid().replace("-", ""));
        submitOrderVO.setTotalMoney(0L);
        SFTOrderItem orderItem;
        SFTOrder order;

        for ( SFTCart cart: carts){
            if (cart.getShopId().equals(session.getShopId())){
                //自卖自买行为
                throw new MyException(HttpResponseEnum.PROHIBIT,"部分商品无法购买，请重新选择");
            }else if (cart.getNum()<1){
                throw new MyException(HttpResponseEnum.ERRONEOUS_REQUEST);  //购买数量需大于等于1
            }

            orderItem=new SFTOrderItem();
            orderGoodsIdList.add(cart.getGoodsId());

            if (!orderMap.containsKey(cart.getShopId())){
                order=new SFTOrder();
                order.setCartNum(submitOrderVO.getCartNum());
                order.setOrderId(IdGenUtils.generateOrderId());
                order.setOrderTotalMoney(0L);
                order.setShopId(cart.getShopId());
                order.setBuyerId(session.getUserId());
                order.setReceiverAddress(receiverAddress);
                order.setReceiverPeople(receiverPeople);
                order.setState(OrderConst.STATE_FOR_BUYER.WAIT_PAY.getKey());    //此处统一用买家的State去设置，其实都一样
                orderMap.put(cart.getShopId(),order);
                addOrderOperateLog(order.getOrderId(),OrderConst.OPERATE.CREATE_ORDER);
            }else{
                order=orderMap.get(cart.getShopId());
            }
            orderItem.setBuyNum(cart.getNum());
            orderItem.setGoodsId(cart.getGoodsId());
            orderItem.setCartNum(order.getCartNum());
            orderItem.setShopId(order.getShopId());
            orderItem.setOrderId(order.getOrderId());
            orderItemMap.put(cart.getGoodsId(),orderItem);
        }

        //查询最新商品数据，并锁住相应的商品行
        List<SFTGoods> onSellGoodsList= goodsDao.getOnSellGoodsByGoodsIdList(orderGoodsIdList, GoodsConst.STATE.ON_SALE.getKey());
        if (null==onSellGoodsList||onSellGoodsList.size()!=orderGoodsIdList.size()){
            //部分商品已下架
            throw new MyException(HttpResponseEnum.PROHIBIT,"部分商品无法购买，请重新选择");
        }
        //货物剩余库存检查
        for (SFTGoods goods:onSellGoodsList){
            orderItem=orderItemMap.get(goods.getGoodsId());
            if (goods.getRemainNum()>=orderItem.getBuyNum()){
                goods.setRemainNum(goods.getRemainNum()-orderItem.getBuyNum());
                goods.setSellNum(goods.getSellNum()+orderItem.getBuyNum());
                if (goods.getRemainNum()==0){
                    goods.setState(GoodsConst.STATE.SELL_OUT.getKey()); //设置为已售完
                }
                goodsDao.updateGoodsByPrimaryKey(goods);    //更新库存信息，如果后面报错的话，会自动回滚
            }else {
                throw new MyException(HttpResponseEnum.PROHIBIT,"部分商品库存不足，请重新选择");
            }
            orderItem.setBuyPrice(goods.getPrice());
            orderItem.setTotalMoney(goods.getPrice()*orderItem.getBuyNum());    //单位分
            orderItem.setGoodsCoverImage(goods.getCoverImage());
            orderItem.setGoodsName(goods.getName());
            orderItem.setTag(goods.getTag());
            //orderItem 后面批量插入

            order=orderMap.get(goods.getShopId());
            order.setOrderTotalMoney(order.getOrderTotalMoney()+orderItem.getTotalMoney());
            if (StringUtils.isBlank(order.getShopName())){
                order.setShopName(goods.getShopName()); //只需设置一次
            }
            submitOrderVO.setTotalMoney(submitOrderVO.getTotalMoney()+orderItem.getTotalMoney());
        }

        //校验后台计算的总金额是否与前台显示的一致
        if (!totalMoney.equals(submitOrderVO.getTotalMoney())){
            throw new MyException(HttpResponseEnum.PROHIBIT,"商品价格发生变动，请返回重新操作");
        }

        //保存订单和订单项
        List<SFTOrder> orderList=Lists.newLinkedList();
        for (SFTOrder itemOrder:orderMap.values()){
            orderList.add(itemOrder);
        }
        orderDao.addOrders(orderList);
        List<SFTOrderItem> orderItemList=Lists.newLinkedList();
        for (SFTOrderItem item:orderItemMap.values()){
            orderItemList.add(item);
        }
        orderDao.addOrderItems(orderItemList);

        //删除购物车中的对应项
        cartDao.delGoodsFromCartByUserIdAndGoodsIdList(session.getUserId(),orderGoodsIdList);

        //返回结果
        return submitOrderVO;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public SubmitOrderVO submitOrderByBuy(MySession session, SFTCart cart,Long totalMoney,String receiverPeople,String receiverAddress) {
        if (cart.getNum()<1){
            throw new MyException(HttpResponseEnum.ERRONEOUS_REQUEST);
        }else if (session.getUserRole()== Const.USER_ROLE.MANAGER){
            throw new MyException(HttpResponseEnum.PROHIBIT);
        }
        SFTGoods goods=goodsDao.getGoodsByGoodsId(cart.getGoodsId());
        if (goods.getShopOwnerId().equals(session.getUserId())){
            throw new MyException(HttpResponseEnum.PROHIBIT,"无法购买自己销售的商品");
        }else if (goods.getState()!= GoodsConst.STATE.ON_SALE.getKey()){
            throw new MyException(HttpResponseEnum.PROHIBIT,"当前商品暂不销售");
        }else if (goods.getRemainNum()<cart.getNum()){
            throw new MyException(HttpResponseEnum.PROHIBIT,"商品剩余库存不足");
        }
        goods.setRemainNum(goods.getRemainNum()-cart.getNum());
        goods.setSellNum(goods.getSellNum()+cart.getNum());
        if (goods.getRemainNum()==0){
            goods.setState(GoodsConst.STATE.SELL_OUT.getKey()); //设置为已售完
        }
        goodsDao.updateGoodsByPrimaryKey(goods);
        SFTOrder order=new SFTOrder();
        order.setOrderId(IdGenUtils.generateOrderId());
        order.setBuyerId(session.getUserId());
        order.setState(OrderConst.STATE_FOR_BUYER.WAIT_PAY.getKey());
        order.setOrderTotalMoney(goods.getPrice()*cart.getNum());
        order.setShopId(cart.getShopId());
        order.setShopName(goods.getShopName());
        order.setReceiverPeople(receiverPeople);
        order.setReceiverAddress(receiverAddress);

        //校验后台计算的总金额是否与前台显示的一致
        if (!totalMoney.equals(order.getOrderTotalMoney())){
            throw new MyException(HttpResponseEnum.PROHIBIT,"商品价格发生变动，请返回重新操作");
        }

        orderDao.addOrder(order);
        addOrderOperateLog(order.getOrderId(),OrderConst.OPERATE.CREATE_ORDER);

        SFTOrderItem orderItem=new SFTOrderItem();
        orderItem.setShopId(goods.getShopId());
        orderItem.setTag(goods.getTag());
        orderItem.setGoodsName(goods.getName());
        orderItem.setGoodsCoverImage(goods.getCoverImage());
        orderItem.setBuyPrice(goods.getPrice());
        orderItem.setOrderId(order.getOrderId());
        orderItem.setBuyNum(cart.getNum());
        orderItem.setGoodsId(goods.getGoodsId());
        orderItem.setTotalMoney(goods.getPrice()*cart.getNum());
        orderDao.addOrderItem(orderItem);

        SubmitOrderVO submitOrderVO=new SubmitOrderVO();
        submitOrderVO.setOrderId(order.getOrderId());
        submitOrderVO.setTotalMoney(order.getOrderTotalMoney());
        return submitOrderVO;
    }


    private void addOrderOperateLog(String orderId,String operateType){
        SFTOrderOperateLog orderOperateLog=new SFTOrderOperateLog();
        orderOperateLog.setOperateType(operateType);
        orderOperateLog.setOrderId(orderId);
        orderDao.addOrderOperateLog(orderOperateLog);
    }

    public OrderInfoVO getOrderDetail(MySession session, String orderId, int role) {
        SFTOrder order=null;
        if (role== Const.USER_ROLE.BUYER.getKey()){
            order=orderDao.getOrderByOrderIdAndBuyerId(orderId,session.getUserId());
        }else if (role== Const.USER_ROLE.SELLER.getKey()){
            order=orderDao.getOrderByOrderIdAndShopId(orderId,session.getShopId());
        }
        if (null==order){
            throw new MyException(HttpResponseEnum.PROHIBIT);
        }

        OrderInfoVO orderInfoVO=new OrderInfoVO();
        orderInfoVO.setReceiverAddress(order.getReceiverAddress());
        orderInfoVO.setReceiverPeople(order.getReceiverPeople());
        orderInfoVO.setOrder(order);
        if (role== Const.USER_ROLE.BUYER.getKey()){ //买家
            OrderConst.STATE_FOR_BUYER orderState= OrderConst.STATE_FOR_BUYER.getStateByKey(order.getState());
            orderInfoVO.setAllowCancel(orderState.getAllowCancel());
            orderInfoVO.setAllowClickMain(orderState.getAllowClickMain());
            orderInfoVO.setMsgForBt(orderState.getMsgForBt());
            orderInfoVO.setStateDesc(orderState.getDesc());
            orderInfoVO.setBtOperate(orderState.getBtOperate());
        }else {//卖家
            OrderConst.STATE_FOR_SELLER orderState= OrderConst.STATE_FOR_SELLER.getStateByKey(order.getState());
            orderInfoVO.setAllowCancel(orderState.getAllowCancel());
            orderInfoVO.setAllowClickMain(orderState.getAllowClickMain());
            orderInfoVO.setMsgForBt(orderState.getMsgForBt());
            orderInfoVO.setStateDesc(orderState.getDesc());
            orderInfoVO.setBtOperate(orderState.getBtOperate());
        }

        //获取orderItems
        List<SFTOrderItem> orderItemList=orderDao.getOrderItemListByOrderId(orderId);
        orderInfoVO.setOrderItemList(orderItemList);
        //获取order operate log
        List<SFTOrderOperateLog> orderOperateLogList=orderDao.getOrderOperateLogListByOrderId(orderId);
        SFTOrderOperateLog orderOperateLog=new SFTOrderOperateLog();
        orderOperateLog.setOrderId(orderId);
        orderOperateLog.setOperateType(orderInfoVO.getStateDesc());
        orderOperateLogList.add(0,orderOperateLog);
        orderInfoVO.setOperateLogList(orderOperateLogList);
        return orderInfoVO;
    }

    @Transactional
    public void receiptOrder(MySession session, String orderId) {
        if (session.getUserRole()!= Const.USER_ROLE.SELLER) {
            throw new MyException(HttpResponseEnum.UNAUTHORIZED);
        }
        SFTOrder order=orderDao.getOrderByOrderIdAndShopId(orderId,session.getShopId());
        if (null==order){
            throw new MyException(HttpResponseEnum.PROHIBIT,"查询不到相关订单");
        }
        OrderConst.STATE_FOR_SELLER orderState=OrderConst.STATE_FOR_SELLER.getStateByKey(order.getState());
        if (orderState!= OrderConst.STATE_FOR_SELLER.WAIT_RECEIPT){
            throw new MyException(HttpResponseEnum.PROHIBIT,"操作不允许");
        }
        //修改订单状态
        order.setState(OrderConst.STATE_FOR_SELLER.WAIT_SEND.getKey()); //接单后，进入待发货状态
        orderDao.updateOrderByPrimaryKey(order);

        //添加操作日志
        SFTOrderOperateLog orderOperateLog=new SFTOrderOperateLog();
        orderOperateLog.setOrderId(orderId);
        orderOperateLog.setOperateType(OrderConst.OPERATE.RECEIPT);
        orderDao.addOrderOperateLog(orderOperateLog);
    }

    /**
     * 目前暂时没有对商家录入的快递单号进行校验，后期看看加不加
     * @param session
     * @param orderId
     * @param expressNum
     */
    public void sendGoodsForOrder(MySession session, String orderId, String expressNum) {
        if (session.getUserRole()!= Const.USER_ROLE.SELLER) {//不是卖家
            throw new MyException(HttpResponseEnum.UNAUTHORIZED);
        }else if (StringUtils.isBlank(expressNum)){
            throw new MyException(HttpResponseEnum.ERRONEOUS_REQUEST);
        }
        SFTOrder order=orderDao.getOrderByOrderIdAndShopId(orderId,session.getShopId());
        if (null==order){
            throw new MyException(HttpResponseEnum.PROHIBIT,"查询不到相关订单");
        }
        OrderConst.STATE_FOR_SELLER orderState=OrderConst.STATE_FOR_SELLER.getStateByKey(order.getState());
        if (orderState!= OrderConst.STATE_FOR_SELLER.WAIT_SEND){
            throw new MyException(HttpResponseEnum.PROHIBIT,"操作不允许");
        }
        //修改订单相关信息
        order.setExpressNum(expressNum);    //填写快递单号
        order.setSendTime(DateYMDHMSJsonSerializer.dateFormat.format(new Date()));  //设置发货时间
        order.setState(OrderConst.STATE_FOR_SELLER.WAIT_SIGN.getKey()); //发货后，进入待签收状态
        orderDao.updateOrderByPrimaryKey(order);

        //添加操作日志
        SFTOrderOperateLog orderOperateLog=new SFTOrderOperateLog();
        orderOperateLog.setOrderId(orderId);
        orderOperateLog.setOperateType(OrderConst.OPERATE.SEND);
        orderDao.addOrderOperateLog(orderOperateLog);
    }

    public void signOrder(MySession session, String orderId) {
//        if (session.getUserRole()!= Const.USER_ROLE.BUYER) {//不是买家
//            throw new MyException(HttpResponseEnum.UNAUTHORIZED);
//        }
        SFTOrder order=orderDao.getOrderByOrderIdAndBuyerId(orderId,session.getUserId());
        if (null==order){
            throw new MyException(HttpResponseEnum.PROHIBIT,"查询不到相关订单");
        }
        OrderConst.STATE_FOR_BUYER orderState=OrderConst.STATE_FOR_BUYER.getStateByKey(order.getState());
        if (orderState!= OrderConst.STATE_FOR_BUYER.WAIT_SIGN){
            throw new MyException(HttpResponseEnum.PROHIBIT,"操作不允许");
        }
        //修改订单相关信息
        order.setDealTime(DateYMDHMSJsonSerializer.dateFormat.format(new Date()));//设置签收时间
        order.setState(OrderConst.STATE_FOR_BUYER.ALREADY_DEAL.getKey()); //确认签收后，订单进入已成交状态
        orderDao.updateOrderByPrimaryKey(order);

        //添加操作日志
        SFTOrderOperateLog orderOperateLog=new SFTOrderOperateLog();
        orderOperateLog.setOrderId(orderId);
        orderOperateLog.setOperateType(OrderConst.OPERATE.SIGN);
        orderDao.addOrderOperateLog(orderOperateLog);
    }

    /**
     *
     * @param session
     * @param orderId
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void cancelOrder(MySession session,int role, String orderId) {
        Const.USER_ROLE operateRole= Const.USER_ROLE.getRoleByKey(role);
        SFTOrder order;
        SFTOrderOperateLog orderOperateLog=new SFTOrderOperateLog();
        orderOperateLog.setOrderId(orderId);
        if (operateRole== Const.USER_ROLE.BUYER){//买家
            order=orderDao.getOrderByOrderIdAndBuyerId(orderId,session.getUserId());
            OrderConst.STATE_FOR_BUYER orderState=OrderConst.STATE_FOR_BUYER.getStateByKey(order.getState());
            if (orderState.getAllowCancel()!=Const.YES){
                throw new MyException(HttpResponseEnum.PROHIBIT);
            }
            orderOperateLog.setOperateType(OrderConst.OPERATE.CANCEL_BY_BUYER);
        }else if (operateRole== Const.USER_ROLE.SELLER){//卖家
            order=orderDao.getOrderByOrderIdAndShopId(orderId,session.getShopId());
            OrderConst.STATE_FOR_SELLER orderState=OrderConst.STATE_FOR_SELLER.getStateByKey(order.getState());
            if (orderState.getAllowCancel()!=Const.YES){
                throw new MyException(HttpResponseEnum.PROHIBIT);
            }
            orderOperateLog.setOperateType(OrderConst.OPERATE.REFUSE_RECEIPT);
        }else {//管理员
            order=orderDao.getOrderByOrderId(orderId);
            OrderConst.STATE_FOR_MANAGER orderState=OrderConst.STATE_FOR_MANAGER.getStateByKey(order.getState());
            if (orderState.getAllowCancel()!=Const.YES){
                throw new MyException(HttpResponseEnum.PROHIBIT);
            }
            orderOperateLog.setOperateType(OrderConst.OPERATE.CANCEL_BY_MANAGER);
        }

        //开始根据订单项更新商品库存
        List<SFTOrderItem> orderItemList=orderDao.getOrderItemListByOrderId(orderId);
        Map<String,SFTOrderItem> orderItemMap=Maps.newHashMap();//key为goodsId
        List<String> goodsIdList=Lists.newLinkedList();
        for (SFTOrderItem orderItem:orderItemList){
            orderItemMap.put(orderItem.getGoodsId(),orderItem);
            goodsIdList.add(orderItem.getGoodsId());
        }
        List<SFTGoods> goodsList=goodsDao.getSimpleGoodsInfoListByGoodsIdList(goodsIdList);
        for (SFTGoods goods:goodsList){
            goods.setRemainNum(goods.getRemainNum()+orderItemMap.get(goods.getGoodsId()).getBuyNum());
            if (goods.getRemainNum()>0&&goods.getState()==GoodsConst.STATE.SELL_OUT.getKey()){
                //如果更新后的库存数量大于0，且商品处于已售完状态，则自动将商品更新为上架中
                goods.setState(GoodsConst.STATE.ON_SALE.getKey());
            }
            //更新商品信息
            goodsDao.updateGoodsByPrimaryKey(goods);
        }

        //修改订单状态
        order.setState(OrderConst.STATE_FOR_BUYER.ALREADY_CANCEL.getKey());
        orderDao.updateOrderByPrimaryKey(order);
        //添加操作记录
        orderDao.addOrderOperateLog(orderOperateLog);
    }

    /**
     * 支持订单号、商店名、付款编号、收货人、收获地址、快递编号的模糊查询
     * @param session
     * @param state
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageBean getOrderListByBuyer(MySession session, int state, String keyword, int pageNum, int pageSize) {
        if (state!=0){
            OrderConst.STATE_FOR_BUYER.getStateByKey(state);    //检查state参数值是否合法
        }
        PageHelper.startPage(pageNum,pageSize);
        List<OrderSimpleInfoVO> orderSimpleInfoVOList=Lists.newLinkedList();
        List<SFTOrder> orderList=orderDao.getOrderListByBuyerIdAndStateAndKeyword(session.getUserId(), state, keyword);
        OrderSimpleInfoVO orderSimpleInfoVO;
        StringBuilder orderTotalMoney;  //用于将金额转化为string类型
        for (SFTOrder order:orderList){
            orderSimpleInfoVO=new OrderSimpleInfoVO();
            orderSimpleInfoVO.setOrderId(order.getOrderId());
            orderSimpleInfoVO.setOrderState(order.getState());
            orderSimpleInfoVO.setOrderStateDesc(OrderConst.STATE_FOR_BUYER.getStateByKey(order.getState()).getDesc());
            orderTotalMoney=new StringBuilder();
            orderTotalMoney.append(order.getOrderTotalMoney()/100).append(".").append(order.getOrderTotalMoney()%100);
            orderSimpleInfoVO.setOrderTotalMoney(orderTotalMoney.toString());
            orderSimpleInfoVO.setShopName(order.getShopName());
            orderSimpleInfoVO.setOrderItemList(orderDao.getOrderItemListByOrderId(order.getOrderId()));
            orderSimpleInfoVOList.add(orderSimpleInfoVO);
        }

        PageBean pageBean=PageBean.createByPage(orderList);
        pageBean.setList(orderSimpleInfoVOList);
        return pageBean;
    }

    /**
     * 支持订单号、付款编号、收货人、收获地址、快递编号的模糊查询
     */
    public PageBean getOrderListBySeller(MySession session, int state, String keyword, int pageNum, int pageSize) {
        if (state!=0){
            OrderConst.STATE_FOR_SELLER.getStateByKey(state);    //检查state参数值是否合法
        }
        PageHelper.startPage(pageNum,pageSize);
        List<OrderSimpleInfoVO> orderSimpleInfoVOList=Lists.newLinkedList();
        List<SFTOrder> orderList=orderDao.getOrderListByShopIdAndStateAndKeyword(session.getShopId(), state, keyword);
        OrderSimpleInfoVO orderSimpleInfoVO;
        StringBuilder orderTotalMoney;
        for (SFTOrder order:orderList){
            orderSimpleInfoVO=new OrderSimpleInfoVO();
            orderSimpleInfoVO.setOrderId(order.getOrderId());
            orderSimpleInfoVO.setOrderState(order.getState());
            orderSimpleInfoVO.setOrderStateDesc(OrderConst.STATE_FOR_SELLER.getStateByKey(order.getState()).getDesc());
            orderTotalMoney=new StringBuilder();
            orderTotalMoney.append(order.getOrderTotalMoney()/100).append(".").append(order.getOrderTotalMoney()%100);
            orderSimpleInfoVO.setOrderTotalMoney(orderTotalMoney.toString());
            orderSimpleInfoVO.setShopName(order.getShopName());
            orderSimpleInfoVO.setOrderItemList(orderDao.getOrderItemListByOrderId(order.getOrderId()));
            orderSimpleInfoVOList.add(orderSimpleInfoVO);
        }

        PageBean pageBean=PageBean.createByPage(orderList);
        pageBean.setList(orderSimpleInfoVOList);
        return pageBean;
    }

    @Transactional
    public void payByOrder(MySession session, String orderId, Long totalMoney,int payType) {
        SFTOrder order=orderDao.getOrderByOrderIdAndBuyerId(orderId,session.getUserId());
        if (null==order){
            throw new MyException(HttpResponseEnum.PROHIBIT);
        }
        if (!order.getOrderTotalMoney().equals(totalMoney)){
            throw new MyException(HttpResponseEnum.PROHIBIT,"支付金额有误");
        }
        if (order.getState()==OrderConst.STATE_FOR_BUYER.WAIT_PAY.getKey()){
            order.setState(OrderConst.STATE_FOR_BUYER.WAIT_RECEIPT.getKey());   //修改订单为等待接单
            order.setPayId(IdGenUtils.uuid().replace("-",""));
            order.setPayTime(DateYMDHMSJsonSerializer.dateFormat.format(new Date()));
            orderDao.updateOrderByPrimaryKey(order);
        }else {
            throw new MyException(HttpResponseEnum.PROHIBIT,"状态不允许");
        }

        //开始保存支付信息
        SFTOrderPay orderPay=new SFTOrderPay();
        orderPay.setOrderId(order.getOrderId());
        orderPay.setUserId(session.getUserId());
        orderPay.setOrderActualPay(order.getOrderTotalMoney());
        orderPay.setOrderTotalPrice(order.getOrderTotalMoney());
        orderPay.setPayId(order.getPayId());
        orderPay.setPayType(Const.PAY_TYPE.getPayTypeByKey(payType).getKey());
        orderDao.addOrderPay(orderPay);
        //保存日志
        SFTOrderOperateLog orderOperateLog=new SFTOrderOperateLog();
        orderOperateLog.setOrderId(order.getOrderId());
        orderOperateLog.setOperateType(OrderConst.OPERATE.PAY);
        orderDao.addOrderOperateLog(orderOperateLog);
    }

    @Transactional
    public void payByCart(MySession session, String cartNum, Long totalMoney, int payType) {
        List<SFTOrder> orderList=orderDao.getOrderListByCartNumAndBuyerId(cartNum,session.getUserId());
        if(null==orderList||orderList.size()==0){
            throw new MyException(HttpResponseEnum.PROHIBIT);
        }
        Long countTotalMoney=0L;
        for (SFTOrder order:orderList){
            if (order.getState()==OrderConst.STATE_FOR_BUYER.WAIT_PAY.getKey()){
                order.setState(OrderConst.STATE_FOR_BUYER.WAIT_RECEIPT.getKey());   //修改订单为等待接单
                order.setPayId(IdGenUtils.uuid().replace("-",""));
                order.setPayTime(DateYMDHMSJsonSerializer.dateFormat.format(new Date()));
                orderDao.updateOrderByPrimaryKey(order);
            }else {
                throw new MyException(HttpResponseEnum.PROHIBIT,"状态不允许");
            }

            //开始保存支付信息
            SFTOrderPay orderPay=new SFTOrderPay();
            orderPay.setOrderId(order.getOrderId());
            orderPay.setCartNum(cartNum);
            orderPay.setUserId(session.getUserId());
            orderPay.setOrderActualPay(order.getOrderTotalMoney());
            orderPay.setOrderTotalPrice(order.getOrderTotalMoney());
            orderPay.setPayId(order.getPayId());
            orderPay.setPayType(Const.PAY_TYPE.getPayTypeByKey(payType).getKey());
            orderDao.addOrderPay(orderPay);
            //保存日志
            SFTOrderOperateLog orderOperateLog=new SFTOrderOperateLog();
            orderOperateLog.setOrderId(order.getOrderId());
            orderOperateLog.setOperateType(OrderConst.OPERATE.PAY);
            orderDao.addOrderOperateLog(orderOperateLog);

            //计算总金额
            countTotalMoney+=order.getOrderTotalMoney();
        }
        if (!countTotalMoney.equals(totalMoney)){
            throw new MyException(HttpResponseEnum.PROHIBIT,"支付金额有误");
        }
    }

    public PageBean getOrderListByManager(MySession session, String keyword, int pageNum, int pageSize) {
        if (session.getUserRole()!= Const.USER_ROLE.MANAGER){
            throw new MyException(HttpResponseEnum.PROHIBIT);
        }
        PageHelper.startPage(pageNum,pageSize);
//        List<OrderSimpleInfoVO> orderSimpleInfoVOList=Lists.newLinkedList();
        List<SFTOrder> orderList=orderDao.getOrderListForManager(keyword);
        PageBean pageBean=PageBean.createByPage(orderList);
//        pageBean.setList(orderSimpleInfoVOList);
        return pageBean;
    }
}
