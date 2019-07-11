package top.wwf.modules.order.dao.enhance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
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
    private SFTOrderOperateLogMapper orderOperateLogMapper;
    @Autowired
    private SFTOrderPayMapper orderPayMapper;

    public List<SFTOrder> getNotFinishSellOrderListByShopId(String shopId) {
        return null;
    }

    public List<SFTOrder> getNotFinishBuyOrderListByUserId(String userId) {
        return null;
    }

    /**
     * 添加订单，可一次性添加多条
     * @param orderList
     */
    public void addOrders(List<SFTOrder> orderList) {

    }

    /**
     * 添加订单项，一次添加多条
     * @param orderItemList
     */
    public void addOrderItems(List<SFTOrderItem> orderItemList) {
    }

    public void addOrderOperateLog(SFTOrderOperateLog orderOperateLog) {

    }

    public void addOrder(SFTOrder order) {

    }

    public void addOrderItem(SFTOrderItem orderItem) {

    }

    public SFTOrder getOrderByOrderIdAndBuyerId(String orderId, String userId) {
        return null;
    }

    public SFTOrder getOrderByOrderIdAndShopId(String orderId, String shopId) {
        return null;
    }

    /**
     * order by createTime
     * @param orderId
     * @return
     */
    public List<SFTOrderItem> getOrderItemListByOrderId(String orderId) {

        return null;
    }

    /**
     * order by operateTime desc 降序
     * @param orderId
     * @return
     */
    public List<SFTOrderOperateLog> getOrderOperateLogListByOrderId(String orderId) {
        return null;
    }

    public void updateOrderByPrimaryKey(SFTOrder order) {

    }

    public SFTOrder getOrderByOrderId(String orderId) {
        return null;
    }

    public List<OrderSimpleInfoVO> getOrderListByBuyerWithCondition(String userId, int state, String keyWord) {
        return null;
    }

    public List<OrderSimpleInfoVO> getOrderListBySellerWithCondition(String userId, int state, String keyWord) {

        return null;
    }
}
