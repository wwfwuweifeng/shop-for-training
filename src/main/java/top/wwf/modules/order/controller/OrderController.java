package top.wwf.modules.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
