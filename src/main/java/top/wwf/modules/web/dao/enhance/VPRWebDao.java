package top.wwf.modules.web.dao.enhance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.wwf.modules.web.dao.VPRUploadFileInfoMapper;
import top.wwf.modules.web.entity.VPRUploadFileInfo;

import java.util.List;

/**
* @Description:    所有有关web模板的dao层操作，均通过此增强dao
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-05-04 19:24
*/
@Repository
public class VPRWebDao {
    @Autowired
    private VPRUploadFileInfoMapper uploadFileInfoMapper;


    public List<VPRUploadFileInfo> getUploadFileInfoByUserIdAndIsUseAndIsDelete(String userOpenId, int isUse, int isDelete) {
        return uploadFileInfoMapper.selectByUserIdAndIsUseAndIsDelete(userOpenId,isUse,isDelete);
    }

    public void deleteUploadFileInfoByUserIdAndIsUseAndIsDelete(String userOpenId, int isUse, int isDelete) {
        uploadFileInfoMapper.deleteByUserIdAndIsUseAndIsDelete(userOpenId,isUse,isDelete);   //逻辑删除
    }

    public void addUploadFileInfo(VPRUploadFileInfo uploadFileInfo) {
        uploadFileInfoMapper.insertSelective(uploadFileInfo);
    }



    public VPRUploadFileInfo getUploadFileInfoByUserIdAndCheckIdAndIsUseAndIsDelete(String userOpenId, String checkId, int isUse, int isDelete) {
        return uploadFileInfoMapper.selectByUserIdAndCheckIdAndIsUseAndIsDelete(userOpenId,checkId,isUse,isDelete);
    }

    public void updateUploadFileInfoByPrimaryKeySelective(VPRUploadFileInfo uploadFileInfo) {
        uploadFileInfoMapper.updateByPrimaryKeySelective(uploadFileInfo);
    }
}
