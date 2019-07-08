package top.wwf.modules.order.dao.enhance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.wwf.modules.order.dao.SFTOrderMapper;
import top.wwf.modules.order.dao.SFTOrderOperateLogMapper;
import top.wwf.modules.order.dao.SFTOrderPayMapper;
import top.wwf.modules.order.entity.SFTOrder;

import java.util.List;

/**
* @Description:    TODO
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-07 00:08
*/
@Repository
public class OrderDao {
    @Autowired
    private SFTOrderMapper           orderMapper;
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
}
