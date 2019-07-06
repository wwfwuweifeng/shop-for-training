package top.wwf.modules.goods.entity;

import java.util.Date;

public class SFTGoodsClassify {
    private Long id;

    private Long parentClassifyId;

    private String classifyName;

    private String coverImage;

    private Date createTime;

    public SFTGoodsClassify(Long id, Long parentClassifyId, String classifyName, String coverImage, Date createTime) {
        this.id = id;
        this.parentClassifyId = parentClassifyId;
        this.classifyName = classifyName;
        this.coverImage = coverImage;
        this.createTime = createTime;
    }

    public SFTGoodsClassify() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentClassifyId() {
        return parentClassifyId;
    }

    public void setParentClassifyId(Long parentClassifyId) {
        this.parentClassifyId = parentClassifyId;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName == null ? null : classifyName.trim();
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage == null ? null : coverImage.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}