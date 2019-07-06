package top.wwf.modules.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import top.wwf.modules.cart.service.CartService;

/**
* @Description:    TODO
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-07 00:01
*/

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;


}
