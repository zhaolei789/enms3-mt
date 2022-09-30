package cn.ewsd.forest.model.getUserDetail;

import java.util.List;

public class UserDetailData {
    private String id;
    private long corpId;
    private String outerId;
    private String accountId;
    private String name;
    private String mobile;
    private String tel;
    private String email;
    private int status;
    private String jobNumber;
    private String workPlace;
    private String hiredDate;
    private String leaveDate;
    private int isHideMobile;
    private String remark;
    private List<UserDetailDepts> depts;
    private String imAccount;
    private long creator;
    private long createAt;
    private long updator;
    private long updateAt;
    private String avatar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCorpId() {
        return corpId;
    }

    public void setCorpId(long corpId) {
        this.corpId = corpId;
    }

    public String getOuterId() {
        return outerId;
    }

    public void setOuterId(String outerId) {
        this.outerId = outerId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getHiredDate() {
        return hiredDate;
    }

    public void setHiredDate(String hiredDate) {
        this.hiredDate = hiredDate;
    }

    public String getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(String leaveDate) {
        this.leaveDate = leaveDate;
    }

    public int getIsHideMobile() {
        return isHideMobile;
    }

    public void setIsHideMobile(int isHideMobile) {
        this.isHideMobile = isHideMobile;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<UserDetailDepts> getDepts() {
        return depts;
    }

    public void setDepts(List<UserDetailDepts> depts) {
        this.depts = depts;
    }

    public String getImAccount() {
        return imAccount;
    }

    public void setImAccount(String imAccount) {
        this.imAccount = imAccount;
    }

    public long getCreator() {
        return creator;
    }

    public void setCreator(long creator) {
        this.creator = creator;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public long getUpdator() {
        return updator;
    }

    public void setUpdator(long updator) {
        this.updator = updator;
    }

    public long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(long updateAt) {
        this.updateAt = updateAt;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
