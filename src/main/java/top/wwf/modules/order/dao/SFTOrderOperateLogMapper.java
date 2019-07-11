package top.wwf.modules.order.dao;

import top.wwf.modules.order.entity.SFTOrderOperateLog;

import java.util.List;

public interface SFTOrderOperateLogMapper {

    int insertSelective(SFTOrderOperateLog record);

    SFTOrderOperateLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SFTOrderOperateLog record);

    List<SFTOrderOperateLog> selectOperateLogListByOrderId(String orderId);
}