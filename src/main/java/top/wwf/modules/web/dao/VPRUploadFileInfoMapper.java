package top.wwf.modules.web.dao;

import top.wwf.modules.web.entity.VPRUploadFileInfo;

import java.util.List;

public interface VPRUploadFileInfoMapper {

    int insertSelective(VPRUploadFileInfo record);

    VPRUploadFileInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VPRUploadFileInfo record);

    List<VPRUploadFileInfo> selectByUserIdAndIsUseAndIsDelete(String userOpenId, int isUse, int isDelete);

    void deleteByUserIdAndIsUseAndIsDelete(String userOpenId, int isUse, int isDelete);

    VPRUploadFileInfo selectByUserIdAndCheckIdAndIsUseAndIsDelete(String userOpenId, String checkId, int isUse, int isDelete);
}