package top.wwf.modules.web.entity;

import java.util.Date;

public class VPRUploadFileInfo {
    private Long id;

    private String uploadUserId;

    private String fileName;

    private String purpose;

    private Integer isDelete;

    private Integer isUse;

    private Date uploadTime;

    private String checkId;

    private String originalFileName;

    public VPRUploadFileInfo(Long id, String uploadUserId, String fileName, String purpose, Integer isDelete, Integer isUse, Date uploadTime, String checkId, String originalFileName) {
        this.id = id;
        this.uploadUserId = uploadUserId;
        this.fileName = fileName;
        this.purpose = purpose;
        this.isDelete = isDelete;
        this.isUse = isUse;
        this.uploadTime = uploadTime;
        this.checkId = checkId;
        this.originalFileName = originalFileName;
    }

    public VPRUploadFileInfo() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUploadUserId() {
        return uploadUserId;
    }

    public void setUploadUserId(String uploadUserId) {
        this.uploadUserId = uploadUserId == null ? null : uploadUserId.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose == null ? null : purpose.trim();
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getIsUse() {
        return isUse;
    }

    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId == null ? null : checkId.trim();
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName == null ? null : originalFileName.trim();
    }
}