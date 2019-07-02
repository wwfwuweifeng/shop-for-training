create database if not exists vpr_electronic_contract ;
use vpr_electronic_contract;
create table if not exists vpr_user(
  id bigint(20) unsigned auto_increment primary key ,
  open_id varchar(255) not null comment '微信返回的用户唯一id',
  wx_name varchar(400) comment '用户微信名，该字段暂时不使用',
  user_sex int comment '微信资料上的性别',
  session_key varchar(50) comment '微信服务器返回的临时session_key',
  token varchar(50) comment '服务器为该用户颁发的临时登录凭证，有效时间以微信的checkSession为准',
  user_name varchar(50) comment '用户真实姓名',
  tel_num varchar(25) comment '电话号码',
  address varchar(255) comment '地址',
  email varchar(255) comment '用户邮箱',
  is_perfect_info int default 0 comment '用户的详细信息',
  create_time timestamp not null default current_timestamp,
  update_time timestamp not null default current_timestamp on update current_timestamp

);

create table if not exists vpr_contract_template(
  id bigint(20) unsigned auto_increment primary key ,
  template_id varchar(40) not null unique comment '模板唯一id',
  template_name varchar(255) not null comment '模板名称',
  owner_user_id varchar(255) not null comment '拥有者的openid',
  create_type int not null comment '创建方式：线上、线下',
  contract_template_title varchar(255) comment '合同标题',
  contract_template_content longtext comment '合同正文',
  create_time timestamp not null default current_timestamp,
  update_time timestamp not null default current_timestamp on update current_timestamp
);

create table if not exists vpr_contract_info(
  id bigint(20) unsigned auto_increment primary key ,
  contract_id varchar(20) not null ,
  contract_name varchar(255) not null comment '合同名称',
  contract_type int not null comment '合同类型：正常、解约',
  contract_state int not null comment '合同状态',
  state_type int not null comment '状态类型：流程中、生效中、已解约',
  initiate_user_id varchar(255) not null comment '发起者的open_id',
  initiate_user_name varchar(255) not null comment '发起者的名字',
  part_a_id varchar(255) comment '甲方的open_id',
  part_a_name varchar(255) not null comment '甲方的名字',
  part_b_id varchar(255) comment '乙方的open_id',
  part_b_name varchar(255) not null comment '乙方的名字',
  is_on_rescind int not null comment '是否处于解约中',
  is_finish int not null comment '是否已经结束流程',
  create_time timestamp not null default current_timestamp,
  update_time timestamp not null default current_timestamp on update current_timestamp
);

create table if not exists vpr_contract_rescind_relation_info(
  id bigint(20) unsigned auto_increment primary key ,
  original_contract_id varchar(20) not null comment '原始合同编号',
  rescind_contract_id varchar(20) not null comment '解约合同编号',
  is_give_up int not null comment '该解约关系是否被放弃了，一个原始合同只能有一条未被放弃的记录',
  create_time timestamp not null default current_timestamp,
  update_time timestamp not null default current_timestamp on update current_timestamp
);
# create table if not exists vpr_contract_content(
#   id bigint(20) unsigned auto_increment primary key,
#   contract_id varchar(20) not null ,
#   initiate_user_id varchar(255) not null comment '发起的open_id',
#   part_a_id varchar(255) not null comment '甲方的open_id',
#   part_a_name varchar(255) not null comment '甲方的名字',
#   part_b_id varchar(255) not null comment '乙方的open_id',
#   part_b_name varchar(255) not null comment '乙方的名字',
#   create_time timestamp not null default current_timestamp,
#   update_time timestamp not null default current_timestamp on update current_timestamp
# );

create table if not exists vpr_contract_ascription_info(
  id bigint(20) unsigned auto_increment primary key ,
  contract_id varchar(20) not null comment '合同编号',
  contract_info_table_id bigint(20) unsigned not null comment '合同信息表的主键Id',
  owner_user_id varchar(255) not null comment '拥有者的openid',
  owner_user_role int not null comment '拥有者身份',
  contract_type int not null comment '合同类型：正常、解约',
  is_finish int not null comment '是否已经结束流程',
  is_hide int not null comment '是否被隐藏',
  create_time timestamp not null default current_timestamp,
  update_time timestamp not null default current_timestamp on update current_timestamp
);

create table if not exists vpr_contract_template_file_info(
  id bigint(20) unsigned auto_increment primary key ,
  template_id varchar(40) not null ,
  owner_user_id varchar(255) not null comment '拥有者的openid',
  contract_template_docx_file_name varchar(50) comment '合同模板的docx文件名',
  contract_template_pdf_file_name varchar(50) comment '合同模板的pdf文件名',
  contract_template_png_dir_name varchar(50) comment '合同模板的png文件名',
  contract_template_down_docx_file_name varchar(50) comment '用户下载合同模板docx文件时，模板的文件名，用于通过上传更新模板时，做验证',
  create_time timestamp not null default current_timestamp,
  update_time timestamp not null default current_timestamp on update current_timestamp
);

create table if not exists vpr_contract_file_info(
  id bigint(20) unsigned auto_increment primary key ,
  contract_id varchar(20) not null ,
  part_a_mp3_file_name varchar(50) comment '甲方的语音文件名',
  part_b_mp3_file_name varchar(50) comment '乙方的语音文件名',
  part_a_seal_file_name varchar(50) comment '甲方的电子印章文件名',
  part_b_seal_file_name varchar(50) comment '乙方的电子印章文件名',
  contract_docx_file_name varchar(50) comment '合同的docx文件名',
  contract_pdf_file_name varchar(50) comment '合同的pdf文件名',
  contract_png_dir_name varchar(50) comment '合同的png文件夹名',
  create_time timestamp not null default current_timestamp,
  update_time timestamp not null default current_timestamp on update current_timestamp
);

create table if not exists vpr_contract_operate_log(
  id bigint(20) unsigned auto_increment primary key ,
  contract_id varchar(20) not null ,
  operate_user_id varchar(255) not null comment '操作用户的open_id',
  operate_user_role varchar(40) not null comment '操作用户的身份：1 甲方、2 乙方、3 第三方、4 双方',
  operate_type varchar(255) not null comment '操作类型',
  operate_time timestamp not null default current_timestamp
);

create table if not exists vpr_upload_file_info(
  id bigint(20) unsigned auto_increment primary key ,
  upload_user_id varchar(255) comment '上传的用户id',
  check_id varchar(50) not null comment '校验码id',
  file_name varchar(255) comment '临时文件名',
  original_file_name varchar(255) comment '远程文件名',
  purpose varchar(20) comment '用途：create/modify',
  is_delete int not null comment '是否已经删除',
  is_use int not null comment '是否已经被使用',
  upload_time timestamp not null default current_timestamp
)
# alter table vpr_contract_info add unique index vpr_contract_id_and_owner_user_id(contract_id,owner_user_id);