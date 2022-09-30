package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;

import javax.persistence.Table;


@Table(name = "sys_auth_access")
public class AuthAccess extends MCoreBase {

    private Integer pid;
    private Integer sort;
    private String levelId;
    private Integer codeSetId;
    private String code;
    private Integer status;
    private String url;
    private Integer id;
    private String text;
    private String state;
    private String iconCls;
    private String checked;
    private String attributes;
    private String children;
    private Integer roleId;
    private String resourceType;
    private Integer authorize;
    private Integer accessAuth;
    private String remark;

    public AuthAccess() {
        super();
    }

    public AuthAccess(
            String uuid,
            Integer id,
            Integer pid,
            String state,
            String iconCls,
            String text,
            String url,
            Integer roleId,
            Integer accessAuth
    ) {
        super.setUuid(uuid);
        this.id = id;
        this.pid = pid;
        this.state = state;
        this.iconCls = iconCls;
        this.text = text;
        this.url = url;
        this.roleId = roleId;
        this.accessAuth = accessAuth;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public Integer getCodeSetId() {
        return codeSetId;
    }

    public void setCodeSetId(Integer codeSetId) {
        this.codeSetId = codeSetId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public Integer getAuthorize() {
        return authorize;
    }

    public void setAuthorize(Integer authorize) {
        this.authorize = authorize;
    }

    public Integer getAccessAuth() {
        return accessAuth;
    }

    public void setAccessAuth(Integer accessAuth) {
        this.accessAuth = accessAuth;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
