package top.wwf.modules.cart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.wwf.modules.cart.dao.enhance.CartDao;

/**
* @Description:    TODO
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-07 00:20
*/
@Service
public class CartService {
    @Autowired
    private CartDao cartDao;
}
