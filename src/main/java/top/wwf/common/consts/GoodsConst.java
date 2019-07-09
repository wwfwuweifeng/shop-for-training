package top.wwf.common.consts;

import top.wwf.common.exception.MyException;

/**
* @Description:    TODO
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-09 14:24
*/
public class GoodsConst {
    /**
     * 卖家和管理员针对商品的操作
     */
    public enum  OPERATE {
        APPLY_UPPER_SHELF(1,"申请商品上架"),
        LOWER_SHELF(2,"下架商品"),  //管理员和销售商均可进行此操作
        APPROVE_FAIL(3,"申请不通过"),
        APPROVE_SUCCESS(4,"申请通过");

        private int key;
        private String desc;

        OPERATE(int key, String desc) {
            this.key = key;
            this.desc = desc;
        }

        public static String getDescByKey(int key){
            for (OPERATE operate:OPERATE.values()){
                if (key==operate.getKey()){
                    return operate.getDesc();
                }
            }
            throw new MyException(HttpResponseEnum.PROHIBIT, "参数非法");
        }

        public int getKey() {
            return key;
        }

        public String getDesc() {
            return desc;
        }
    }

    /**
     * 商品的状态
     */
    public enum STATE {
        ON_SALE(1,"在售中",Const.YES,Const.NO,"在售中"),
        SELL_OUT(2,"已售完",Const.NO,Const.NO,"已售完"),  //该状态暂时不使用
        LOWER_SHELF(3,"已下架",Const.NO,Const.YES,"申请上架"),
        WAIT_APPROVE(4,"待审批",Const.YES,Const.NO,"审批中"),
        APPROVE_FAIL(5,"审批失败",Const.NO,Const.YES,"申请上架");

        private int key;
        private String desc;
        private int allowLowerShelf;
        private int allowClickMainBt;
        private String msgForMainBt;

        STATE(int key, String desc, int allowLowerShelf, int allowClickMainBt, String msgForMainBt) {
            this.key = key;
            this.desc = desc;
            this.allowLowerShelf = allowLowerShelf;
            this.allowClickMainBt = allowClickMainBt;
            this.msgForMainBt = msgForMainBt;
        }

        public int getKey() {
            return key;
        }

        public String getDesc() {
            return desc;
        }

        public static String getDescByKey(int key){
            for (STATE goodsState: STATE.values()){
                if (key==goodsState.getKey()){
                    return goodsState.getDesc();
                }
            }
            throw new MyException(HttpResponseEnum.PROHIBIT, "参数非法");
        }

        public static STATE getStateByKey(int key){
            for (STATE state: STATE.values()){
                if (state.getKey()==key){
                    return state;
                }
            }
            throw new MyException(HttpResponseEnum.PROHIBIT,"参数非法");
        }

        public int getAllowLowerShelf() {
            return allowLowerShelf;
        }

        public int getAllowClickMainBt() {
            return allowClickMainBt;
        }

        public String getMsgForMainBt() {
            return msgForMainBt;
        }
    }

}
