package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;


@Table(name = "sys_document")
public class Document extends MCoreBase {

    private Integer cid;
    private String module;
    private String title;
    private String description;
    private String thumbnail;
    private String keywords;
    private String recommend = "0";
    private String status = "0";
    private Integer viewNum;
    private String ipAddress;

    public Document() {
        super();
    }

    public Document(String uuid, String title, String description, String thumbnail, Integer viewNum, String creator, Date createTime) {
        super();
        this.setUuid(uuid);
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.viewNum = viewNum;
        this.setCreator(creator);
        this.setCreateTime(createTime);
    }

    public Document(String uuid, Integer cid, String title, String creator, Date createTime) {
        super();
        this.setUuid(uuid);
        this.cid = cid;
        this.title = title;
        this.setCreator(creator);
        this.setCreateTime(createTime);
    }

    @Column(length = 11, nullable = true, columnDefinition = "INT default 0")
    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    @Column(updatable = true, nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

}