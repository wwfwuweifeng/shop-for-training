package top.wwf.modules.goods.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.wwf.modules.goods.dao.enhance.GoodsDao;

/**
* @Description:    TODO
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-07 00:21
*/
@Service
public class GoodsService {
    @Autowired
    private GoodsDao goodsDao;
}
