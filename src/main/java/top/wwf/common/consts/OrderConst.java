package top.wwf.common.consts;

import top.wwf.common.exception.MyException;

/**
* @Description:    订单状态常量类
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-05 11:00
*/
public class OrderConst {

    /**
     * 针对订单的操作
     */
    public interface OPERATE {
        String CREATE_ORDER    = "订单已创建";
        String PAY             = "买家已付款";
        String RECEIPT         = "商家已接单";
        String SEND            = "商家已发货";
        String SIGN            = "买家已签收";
        String REFUSE_RECEIPT  = "商家拒绝接单";
        String CANCEL_BY_BUYER = "买家取消订单";
        String CANCEL_BY_MANAGER="管理员取消订单"; //管理员只有在商家未发货前，才有权限进行取消
    }

    /**
     * 针对购买者的订单状态
     */
    public enum STATE_FOR_BUYER {
        CREATE(1, "订单已创建", "订单已创建", "",Const.YES, Const.NO),
        WAIT_PAY(2, "待付款", "立即支付","pay" ,Const.YES, Const.YES),
        WAIT_RECEIPT(3, "等待商家接单", "等待商家接单", "",Const.YES, Const.NO),
        WAIT_SEND(4, "等待商家发货", "等待商家发货", "",Const.NO, Const.NO),
        WAIT_SIGN(5, "待签收", "签收","sign", Const.NO, Const.YES),
        ALREADY_DEAL(6, "已成交", "已成交","", Const.NO, Const.NO),
        ALREADY_CANCEL(7, "已取消", "已取消", "",Const.NO, Const.NO);

        private int    key;
        private String desc;             //状态显示内容
        private String msgForBt;        //主按钮显示的内容
        private String btOperate;       //主按钮点击的操作
        private int    allowCancel;     //是否允许取消订单
        private int    allowClickMain; //是否允许点击主按钮

        STATE_FOR_BUYER(int key, String desc, String msgForBt,String btOperate, int allowCancel, int allowClickMain) {
            this.key = key;
            this.desc = desc;
            this.msgForBt = msgForBt;
            this.btOperate=btOperate;
            this.allowCancel = allowCancel;
            this.allowClickMain = allowClickMain;
        }

        public int getKey() {
            return key;
        }

        public String getDesc() {
            return desc;
        }

        public String getMsgForBt() {
            return msgForBt;
        }

        public int getAllowCancel() {
            return allowCancel;
        }

        public String getBtOperate() {
            return btOperate;
        }

        public int getAllowClickMain() {
            return allowClickMain;
        }

        //获取订单状态
        public static STATE_FOR_BUYER getStateByKey(int key){
            for (STATE_FOR_BUYER state: STATE_FOR_BUYER.values()){
                if (state.getKey()==key){
                    return state;
                }
            }
            throw new MyException(HttpResponseEnum.PROHIBIT, "参数非法");
        }
    }

    /**
     * 针对商家的订单状态
     */
    public enum STATE_FOR_SELLER {
        CREATE(1, "订单已创建", "订单已创建", "",Const.NO, Const.NO),
        WAIT_PAY(2, "等待买家付款", "等待买家付款", "",Const.NO, Const.NO),
        WAIT_RECEIPT(3, "待接单", "接单","receipt", Const.YES, Const.YES),
        WAIT_SEND(4, "待发货", "发货", "send",Const.NO, Const.YES),
        WAIT_SIGN(5, "等待买家签收", "等待买家签收", "",Const.NO, Const.NO),
        ALREADY_DEAL(6, "已成交", "已成交", "",Const.NO, Const.NO),
        ALREADY_CANCEL(7, "已取消", "已取消","", Const.NO, Const.NO);

        private int    key;
        private String desc;            //状态显示内容
        private String msgForBt;        //主按钮显示的内容
        private String btOperate;       //主按钮点击的操作
        private int    allowCancel;     //是否允许取消订单
        private int    allowClickMain; //是否允许点击主按钮

        STATE_FOR_SELLER(int key, String desc, String msgForBt, String btOperate,int allowCancel, int allowClickMain) {
            this.key = key;
            this.desc = desc;
            this.msgForBt = msgForBt;
            this.btOperate=btOperate;
            this.allowCancel = allowCancel;
            this.allowClickMain = allowClickMain;
        }

        public int getKey() {
            return key;
        }

        public String getDesc() {
            return desc;
        }

        public String getMsgForBt() {
            return msgForBt;
        }

        public int getAllowCancel() {
            return allowCancel;
        }

        public int getAllowClickMain() {
            return allowClickMain;
        }

        public String getBtOperate() {
            return btOperate;
        }

        //获取订单状态
        public static STATE_FOR_SELLER getStateByKey(int key){
            for (STATE_FOR_SELLER state: STATE_FOR_SELLER.values()){
                if (state.getKey()==key){
                    return state;
                }
            }
            throw new MyException(HttpResponseEnum.PROHIBIT, "参数非法");
        }
    }

    /**
     * 针对管理员的订单状态
     */
    public enum STATE_FOR_MANAGER {
        CREATE(1, "订单已创建",Const.YES),
        WAIT_PAY(2, "等待买家付款",Const.YES),
        WAIT_RECEIPT(3, "等待商家接单",Const.YES),
        WAIT_SEND(4, "等待商家发货",Const.YES),       //管理员只有在卖家发货前，才能进行订单取消
        WAIT_SIGN(5, "等待买家签收",Const.NO),
        ALREADY_DEAL(6, "已成交",Const.NO),
        ALREADY_CANCEL(7, "已取消",Const.NO);

        private int    key;
        private String desc;             //状态显示内容
        private int    allowCancel;     //是否允许取消订单

        STATE_FOR_MANAGER(int key, String desc,int allowCancel) {
            this.key = key;
            this.desc = desc;
            this.allowCancel = allowCancel;
        }

        public int getKey() {
            return key;
        }

        public String getDesc() {
            return desc;
        }


        public int getAllowCancel() {
            return allowCancel;
        }


        //获取订单状态
        public static STATE_FOR_MANAGER getStateByKey(int key){
            for (STATE_FOR_MANAGER state: STATE_FOR_MANAGER.values()){
                if (state.getKey()==key){
                    return state;
                }
            }
            throw new MyException(HttpResponseEnum.PROHIBIT, "参数非法");
        }
    }
}
