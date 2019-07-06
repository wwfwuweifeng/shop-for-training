package top.wwf.modules.cart.dao.enhance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.wwf.modules.cart.dao.SFTCartMapper;

/**
* @Description:    TODO
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-07 00:07
*/
@Repository
public class CartDao {
    @Autowired
    private SFTCartMapper cartMapper;

}
