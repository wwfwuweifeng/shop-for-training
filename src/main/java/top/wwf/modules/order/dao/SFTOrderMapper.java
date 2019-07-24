package top.wwf.modules.order.dao;

import top.wwf.modules.order.entity.SFTOrder;
import top.wwf.modules.order.vo.OrderSimpleInfoVO;

import java.util.List;

public interface SFTOrderMapper {

    int insertSelective(SFTOrder record);

    SFTOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SFTOrder record);

    List<SFTOrder> selectNotFinishSellOrderListByShopId(String shopId,int dealState,int cancelState);

    List<SFTOrder> selectNotFinishBuyOrderListByUserId(String userId, int dealState, int cancelState);

    void insertOrders(List<SFTOrder> orderList);

    SFTOrder selectByOrderIdAndBuyerId(String orderId, String buyerId);

    SFTOrder selectByOrderIdAndShopId(String orderId, String shopId);

    SFTOrder selectByOrderId(String orderId);

    List<SFTOrder> selectListByShopIdAndStateAndKeyword(String shopId, int state, String keyword);

    List<SFTOrder> selectListByBuyerIdAndStateAndKeyword(String buyerId, int state, String keyword);

    Long selectMaxId();

    List<SFTOrder> selectListByCartNumAndBuyerId(String cartNum, String userId);
}