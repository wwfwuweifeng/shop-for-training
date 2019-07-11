package top.wwf.modules.order.dao.enhance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.wwf.modules.order.dao.SFTOrderItemMapper;
import top.wwf.modules.order.dao.SFTOrderMapper;
import top.wwf.modules.order.dao.SFTOrderOperateLogMapper;
import top.wwf.modules.order.dao.SFTOrderPayMapper;
import top.wwf.modules.order.entity.SFTOrder;
import top.wwf.modules.order.entity.SFTOrderItem;
import top.wwf.modules.order.entity.SFTOrderOperateLog;
import top.wwf.modules.order.vo.OrderSimpleInfoVO;

import java.util.List;

/**
* @Description:    TODO
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-07 00:08
*/
@Repository
public class OrderDao {
    @Autowired
    private SFTOrderMapper orderMapper;
    @Autowired
    private SFTOrderItemMapper orderItemMapper;
    @Autowired
    private SFTOrderOperateLogMapper orderOperateLogMapper;
    @Autowired
    private SFTOrderPayMapper orderPayMapper;

    public List<SFTOrder> getNotFinishSellOrderListByShopId(String shopId,int dealState,int cancelState) {
        return orderMapper.selectNotFinishSellOrderListByShopId(shopId, dealState, cancelState);
    }

    public List<SFTOrder> getNotFinishBuyOrderListByUserId(String userId,int dealState,int cancelState) {
        return orderMapper.selectNotFinishBuyOrderListByUserId(userId,dealState,cancelState);
    }

    /**
     * 添加订单，可一次性添加多条
     * @param orderList
     */
    public void addOrders(List<SFTOrder> orderList) {
        orderMapper.insertOrders(orderList);
    }

    /**
     * 添加订单项，一次添加多条
     * @param orderItemList
     */
    public void addOrderItems(List<SFTOrderItem> orderItemList) {
        orderItemMapper.insertOrderItems(orderItemList);
    }

    public void addOrderOperateLog(SFTOrderOperateLog orderOperateLog) {
        orderOperateLogMapper.insertSelective(orderOperateLog);
    }

    public void addOrder(SFTOrder order) {
        orderMapper.insertSelective(order);
    }

    public void addOrderItem(SFTOrderItem orderItem) {
        orderItemMapper.insertSelective(orderItem);
    }

    public SFTOrder getOrderByOrderIdAndBuyerId(String orderId, String buyerId) {
        return orderMapper.selectByOrderIdAndBuyerId(orderId,buyerId);
    }

    public SFTOrder getOrderByOrderIdAndShopId(String orderId, String shopId) {
        return orderMapper.selectByOrderIdAndShopId(orderId,shopId);
    }

    /**
     * order by id
     * @param orderId
     * @return
     */
    public List<SFTOrderItem> getOrderItemListByOrderId(String orderId) {
        return orderItemMapper.selectOrderItemListByOrderId(orderId);
    }

    /**
     * order by id desc 降序
     * @param orderId
     * @return
     */
    public List<SFTOrderOperateLog> getOrderOperateLogListByOrderId(String orderId) {
        return orderOperateLogMapper.selectOperateLogListByOrderId(orderId);
    }

    public void updateOrderByPrimaryKey(SFTOrder order) {
        orderMapper.updateByPrimaryKeySelective(order);
    }

    public SFTOrder getOrderByOrderId(String orderId) {
        return orderMapper.selectByOrderId(orderId);
    }


    public List<SFTOrder> getOrderListByShopIdAndStateAndKeyword(String shopId, int state, String keyword) {
        return orderMapper.selectListByShopIdAndStateAndKeyword(shopId,state,keyword);
    }

    public List<SFTOrder> getOrderListByBuyerIdAndStateAndKeyword(String buyerId, int state, String keyword) {
        return orderMapper.selectListByBuyerIdAndStateAndKeyword(buyerId,state,keyword);
    }
}
