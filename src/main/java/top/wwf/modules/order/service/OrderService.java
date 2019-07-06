package top.wwf.modules.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.wwf.modules.order.dao.enhance.OrderDao;

/**
* @Description:    TODO
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-07 00:22
*/
@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;
}
