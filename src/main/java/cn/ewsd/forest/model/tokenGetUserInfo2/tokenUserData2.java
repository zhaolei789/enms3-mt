package cn.ewsd.forest.model.tokenGetUserInfo2;


import java.util.List;

public class tokenUserData2 {
    private String id;
    private String mobile;
    private String nickName;
    private String avatar;
    private String imAccount;
    private String email;
    private String remark;
    private String gender;
    private String birthday;
    private int status;
    private int isManager;
    private List<tokenUserDataManageCorp2> manageCorps;
    private List<tokenUserDataCorpUser2> corpUsers;
    private String creator;
    private long createAt;
    private String updator;
    private long updateAt;
    private String imToken;
    private boolean flag;
    private String appKey;
    private String appSecret;
    private String isSubscribe;
    private String colleagueCircleUrl;
    private String isHide;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getImAccount() {
        return imAccount;
    }

    public void setImAccount(String imAccount) {
        this.imAccount = imAccount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsManager() {
        return isManager;
    }

    public void setIsManager(int isManager) {
        this.isManager = isManager;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(long updateAt) {
        this.updateAt = updateAt;
    }

    public String getImToken() {
        return imToken;
    }

    public void setImToken(String imToken) {
        this.imToken = imToken;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getIsSubscribe() {
        return isSubscribe;
    }

    public void setIsSubscribe(String isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public String getColleagueCircleUrl() {
        return colleagueCircleUrl;
    }

    public void setColleagueCircleUrl(String colleagueCircleUrl) {
        this.colleagueCircleUrl = colleagueCircleUrl;
    }

    public String getIsHide() {
        return isHide;
    }

    public void setIsHide(String isHide) {
        this.isHide = isHide;
    }

    public List<tokenUserDataManageCorp2> getManageCorps() {
        return manageCorps;
    }

    public void setManageCorps(List<tokenUserDataManageCorp2> manageCorps) {
        this.manageCorps = manageCorps;
    }

    public List<tokenUserDataCorpUser2> getCorpUsers() {
        return corpUsers;
    }

    public void setCorpUsers(List<tokenUserDataCorpUser2> corpUsers) {
        this.corpUsers = corpUsers;
    }
}
