package cn.ewsd.base.utils.jwt;

import org.springframework.stereotype.Component;

@Component
public class UserInfo {
    private String uuid;
    private String userNameId;
    private String userName;
    private String roleId;
    private String orgId;
    private String tenantId;

    public UserInfo() {
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserNameId() {
        return this.userNameId;
    }

    public void setUserNameId(String userNameId) {
        this.userNameId = userNameId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleId() {
        return this.roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
