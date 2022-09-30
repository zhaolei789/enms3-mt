package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;

import javax.persistence.Table;

/**
 * 被选中的用户
 * @Author 佐佑科技<service@ewsd.cn>
 * @Date 2016/5/27 12:47
 */

@Table(name="sys_user_selected")
public class UserSelected extends MCoreBase {

    private String moduleNameId;
    private String moduleName;
    private String puuid;
    private String userNameId;
    private String userName;
    private String unitName;
    private String firstDept;
    private String secondDept;
    private String thirdDept;
    private String partyCommittee;
    private String partyBranch;
    private String partyGroup;

    public String getModuleNameId() {
        return moduleNameId;
    }

    public void setModuleNameId(String moduleNameId) {
        this.moduleNameId = moduleNameId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
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

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getFirstDept() {
        return firstDept;
    }

    public void setFirstDept(String firstDept) {
        this.firstDept = firstDept;
    }

    public String getSecondDept() {
        return secondDept;
    }

    public void setSecondDept(String secondDept) {
        this.secondDept = secondDept;
    }

    public String getThirdDept() {
        return thirdDept;
    }

    public void setThirdDept(String thirdDept) {
        this.thirdDept = thirdDept;
    }

    public String getPartyCommittee() {
        return partyCommittee;
    }

    public void setPartyCommittee(String partyCommittee) {
        this.partyCommittee = partyCommittee;
    }

    public String getPartyBranch() {
        return partyBranch;
    }

    public void setPartyBranch(String partyBranch) {
        this.partyBranch = partyBranch;
    }

    public String getPartyGroup() {
        return partyGroup;
    }

    public void setPartyGroup(String partyGroup) {
        this.partyGroup = partyGroup;
    }
}
