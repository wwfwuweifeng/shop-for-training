package top.wwf.modules.web.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import top.wwf.common.base.CheckCode;
import top.wwf.common.consts.Const;
import top.wwf.common.consts.HttpResponseEnum;
import top.wwf.common.exception.MyException;
import top.wwf.common.utils.IdGenUtils;
import top.wwf.common.utils.MyFileUtils;
import top.wwf.modules.web.dao.enhance.VPRWebDao;
import top.wwf.modules.web.entity.VPRUploadFileInfo;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* @Description:    web相关接口的方法实现
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-03-16 22:28
*/
@Service
public class WebService {

    @Autowired
    private VPRWebDao webDao;


    /**
     * 只开放用于上传修改模板的docx文件
     * @param uploadCheckCode
     * @param uploadFile
     */
    @Transactional
    public void uploadFile(String uploadCheckCode, MultipartFile uploadFile) {
        if (StringUtils.isBlank(uploadCheckCode)){
            throw new MyException(HttpResponseEnum.ERRONEOUS_REQUEST);
        }
        CheckCode checkCode=CheckCode.getInstanceByCheckId(uploadCheckCode);


        //先检查是否有未删除和消费的临时上传文件
        List<VPRUploadFileInfo> uploadFileInfoList=webDao.getUploadFileInfoByUserIdAndIsUseAndIsDelete(checkCode.getUserOpenId(),Const.NO,Const.NO);
        if (CollectionUtils.isNotEmpty(uploadFileInfoList)){
            for (VPRUploadFileInfo fileInfo:uploadFileInfoList){
                MyFileUtils.delFileOrDir(Const.UPLOAD_FILE_TEMP_PATH+fileInfo.getFileName());
            }
            //更新数据库
            webDao.deleteUploadFileInfoByUserIdAndIsUseAndIsDelete(checkCode.getUserOpenId(),Const.NO,Const.NO);
        }
        String uploadFileName= IdGenUtils.uuid()+".docx";
        //保存文件
        if (MyFileUtils.isDocxFile(uploadFile)){
            MyFileUtils.saveFile(Const.UPLOAD_FILE_TEMP_PATH+uploadFileName,uploadFile);
        }else {
            throw new MyException(HttpResponseEnum.PROHIBIT,"文件类型非法");
        }
        //添加数据库记录
        VPRUploadFileInfo uploadFileInfo=new VPRUploadFileInfo();
        uploadFileInfo.setCheckId(checkCode.getCheckId());
        uploadFileInfo.setFileName(uploadFileName);
        uploadFileInfo.setIsDelete(Const.NO);
        uploadFileInfo.setIsUse(Const.NO);
        uploadFileInfo.setOriginalFileName(uploadFile.getOriginalFilename());
        uploadFileInfo.setUploadUserId(checkCode.getUserOpenId());
        uploadFileInfo.setPurpose(
                checkCode.getObjectId().length()==32?"create":"modify"
        );
        webDao.addUploadFileInfo(uploadFileInfo);
        //修改checkCode的文件上传字段
        checkCode.setFileIsUpload(Const.YES);
    }
}
