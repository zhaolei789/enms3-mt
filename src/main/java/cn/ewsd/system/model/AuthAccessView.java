package cn.ewsd.system.model;

import org.springframework.data.annotation.Transient;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "v_sys_auth_access")
public class AuthAccessView implements Serializable {

    private String codeSetId;
    private String resourceType;
    private Integer levelId;
    private Integer id;
    private Integer pid;
    private String state;
    private String iconCls;
    private String text;
    private String url;
    private Integer isDel;
    private String uuid;
    private Integer roleId;
    private Integer accessAuth;
    private Integer sort;
    @Transient
    private Integer ct;

    public AuthAccessView() {
        super();
    }

    public String getCodeSetId() {
        return codeSetId;
    }

    public void setCodeSetId(String codeSetId) {
        this.codeSetId = codeSetId;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    @Id
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getAccessAuth() {
        return accessAuth;
    }

    public void setAccessAuth(Integer accessAuth) {
        this.accessAuth = accessAuth;
    }

    public Integer getCt() {
        return ct;
    }

    public void setCt(Integer ct) {
        this.ct = ct;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
