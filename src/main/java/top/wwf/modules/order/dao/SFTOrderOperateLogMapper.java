package top.wwf.modules.order.dao;

import top.wwf.modules.order.entity.SFTOrderOperateLog;

public interface SFTOrderOperateLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SFTOrderOperateLog record);

    int insertSelective(SFTOrderOperateLog record);

    SFTOrderOperateLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SFTOrderOperateLog record);

    int updateByPrimaryKey(SFTOrderOperateLog record);
}