package cn.ewsd.mdata.model;

import cn.ewsd.common.model.MCoreBase;

import javax.persistence.Table;

@Table(name = "sys_organization_post")
public class OrganizationPost extends MCoreBase {

    public String orgUuid;

    public String postValue;

    public String postText;

    public String userNameId;

    public String userName;

    public String getOrgUuid() {
        return orgUuid;
    }

    public void setOrgUuid(String orgUuid) {
        this.orgUuid = orgUuid;
    }

    public String getPostValue() {
        return postValue;
    }

    public void setPostValue(String postValue) {
        this.postValue = postValue;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getUserNameId() {
        return userNameId;
    }

    public void setUserNameId(String userNameId) {
        this.userNameId = userNameId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}