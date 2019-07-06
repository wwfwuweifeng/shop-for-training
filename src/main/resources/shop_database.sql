create database if not exists shop_for_trainning;
use shop_for_trainning;
create table if not exists sft_user_sys_info(
  id bigint(20) unsigned auto_increment primary key ,
  user_id varchar(50) comment '用户在本平台的唯一标识',
  open_id varchar(255) comment '微信官方给的用户唯一身份凭证',
  session_key varchar(50) comment '微信服务器返回的临时session_key',
  token varchar(50) comment '服务器为该用户颁发的临时登录凭证，有效时间以微信的checkSession为准',
  register_code varchar(12) not null comment '10位注册码，一个注册码只能绑定一个用户和用户身份',
  user_role int not null comment '用户身份：买方、卖方、管理员',
  user_name varchar(50) not null comment '用户姓名，管理员分配注册码是设定，用户无法修改',
  code_is_used int not null comment '注册码是否已经被使用',
  shop_id varchar(50) comment '商店id',
  shop_name varchar(50) comment '商店名，不超过20字，全局唯一',
  create_time timestamp not null default current_timestamp,
  update_time timestamp not null default current_timestamp on update current_timestamp
);

create table if not exists sft_user_personal_info(
  id bigint(20) unsigned auto_increment primary key ,
  user_id varchar(50) comment '用户在本平台的唯一标识',
  wx_name varchar(255),
  user_sex int comment '用户性别：1 男；2女',
  user_email varchar(255) comment '用户邮箱',
  user_tel varchar(20) comment '用户电话',
  receiver_name varchar(50) comment '收货人姓名',
  receiver_tel varchar(20) comment '收货人电话',
  receiver_address_simple varchar(255) comment '收货地址区域',
  receiver_address_detail varchar(255) comment '收货地址详细',
  have_receiver_address int comment '是否已经设置了收货人地址',
  create_time timestamp not null default current_timestamp,
  update_time timestamp not null default current_timestamp on update current_timestamp
);

create table if not exists stf_goods_classify(
  id bigint(20) unsigned auto_increment primary key ,
  parent_classify_id bigint(20) default 0 comment '若无父节点，则为0，一级节点由人工手动添加到数据库中，目前只提供二级分类',
  classify_name varchar(12) not null comment '分类名,一级节点不超过3字，2级节点不超过5字',
  cover_image varchar(255) comment '该分类的封面图片的文件名，一级节点无此属性',
  create_time timestamp not null default current_timestamp
);

create table if not exists stf_goods(
  id bigint(20) unsigned auto_increment primary key ,
  goods_id varchar(50) not null comment '唯一标识',
  name varchar(60) not null comment '商品名称，不超过25字',
  classify_id bigint(20) not null comment '商品所属类别id',
  classify_name varchar(12) not null comment '分类名',
  cover_image varchar(255) comment '该商品的封面图片的文件名',
  remain_num int default 0 comment '剩余库存',
  sell_num int default 0 comment '已售数量',
  shop_id varchar(50) not null ,
  shop_name varchar(50) not null ,
  shop_owner_id varchar(50) not null comment '商家的用户id',
  tag varchar(50) comment '商品标签，最多支持三个标签，一个标签不超过5个字符，多个标签直接使用&分隔',
  detail longtext comment '商品详细内容',
  price int default 0 comment '价格，单位分，为了避免浮点数运算',
  state int default 3 comment '商品状态：1、在售；2、已售完；3、下架；4、待批准上架；5、不允许上架',
  is_sell_by_shop int default 0 comment '店家是否进行售卖，商品售完后，自动下架',
  is_sell_by_manager int default 0 comment '管理员是否批准上架，只有首次上架需要审批',
  create_time timestamp not null default current_timestamp,
  update_time timestamp not null default current_timestamp on update current_timestamp
);

create table if not exists stf_goods_param(
  id bigint(20) unsigned auto_increment primary key ,
  goods_id varchar(50) not null,
  param_name varchar(25) not null comment '参数名，不超过10字',
  param_value varchar(35) not null comment '值，不操过15字',
  create_time timestamp not null default current_timestamp
);

create table if not exists stf_order(
  id bigint(20) unsigned auto_increment primary key ,
  order_id varchar(20) not null comment '订单编号,长度为15位',
  user_id varchar(50) not null ,
  shop_id varchar(50) not null ,
  shop_name varchar(50) not null ,
  order_total_money int default 0 not null comment '订单总额，单位分',
  state int not null comment '订单状态',
  create_time timestamp not null default current_timestamp,
  pay_time varchar(50) default '暂无数据' not null comment '付款时间',
  send_time varchar(50) default '暂无数据' not null comment '发货时间',
  deal_time varchar(50) default '暂无数据' not null comment '成交时间',
  express_num varchar(50) default '暂无数据' not null comment '快递单号',
  update_time timestamp not null default current_timestamp on update current_timestamp
);

create table if not exists stf_order_operate_log(
  id bigint(20) unsigned auto_increment primary key ,
  order_id varchar(20) not null ,
  goods_id varchar(50) not null ,
  goods_name varchar(60) not null comment '购买时的商品名称',
  goods_cover_image varchar(255) not null comment '购买时的商品封面图',
  tag varchar(50) comment '购买时的商品标签',
  buy_price int comment '购买时的商品单价',
  buy_num int comment '购买数量',
  total_money int comment '总计，单位分',
  create_time timestamp not null default current_timestamp
);

create table if not exists stf_order_operate_log(
  id bigint(20) unsigned auto_increment primary key ,
  order_id varchar(20)not null ,
  operate_time timestamp not null default current_timestamp,
  operate_type varchar(255) not null comment '操作类型'
);