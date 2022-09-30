package cn.ewsd.mdata.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.ewsd.common.model.MCoreBase;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Table;
import java.util.Date;

@Table(name = "sys_user")
public class User extends MCoreBase {

    @Excel(name = "用户名", orderNum = "0", width = 20)
    private String userNameId;
    private String password;
    @Excel(name = "姓名", orderNum = "1", width = 20)
    private String userName;
    private String nickName;
    private String avatar;
    private String sex;
    private Integer age;
    private String idCard;
    private String birthday;
    @Excel(name = "办公电话", orderNum = "2", width = 20)
    private String telephone;
    @Excel(name = "手机", orderNum = "3", width = 25)
    private String cellphone;
    @Excel(name = "邮箱", orderNum = "4", width = 30)
    private String email;
    private String school;
    private String education;//学历
    private String degree;//学位
    private String nation;//民族
    private String nativePlace;//籍贯
    private String zone;
    private String country;
    private String province;
    private String city;
    private String district;
    private String orgId;
    private String orgName;
    private String post;
    private String userGroup;
    private String dataAuth;
    private Integer status;
    private Integer isDel;
    private String postText;
    private String onlineStatus;// 网络状态
    private String  sign;// 个性签名
    private String openid;
    private String unionid;
    private String linkEmployees;
    private String linkEmployeesName;
    private String userNameJp;
    private String lbPost;
    private String lbPostText;
    private String gjPost;
    private String gjPostText;
    private String leadFlag;
    private String leadFlagText;
    private Integer isDriver;
    private String driverId;
    private Integer isTenantAdmin;
    private String tenantId;
    private String assessToken;
    private String refreshToken;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date tokenRefreshTime;
    private Integer entryWay;

    public Integer getEntryWay() {
        return entryWay;
    }

    public void setEntryWay(Integer entryWay) {
        this.entryWay = entryWay;
    }

    public Integer getIsTenantAdmin() {
        return isTenantAdmin;
    }

    public void setIsTenantAdmin(Integer isTenantAdmin) {
        this.isTenantAdmin = isTenantAdmin;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getAssessToken() {
        return assessToken;
    }

    public void setAssessToken(String assessToken) {
        this.assessToken = assessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getTokenRefreshTime() {
        return tokenRefreshTime;
    }

    public void setTokenRefreshTime(Date tokenRefreshTime) {
        this.tokenRefreshTime = tokenRefreshTime;
    }

    public Integer getIsDriver() {
        return isDriver;
    }

    public void setIsDriver(Integer isDriver) {
        this.isDriver = isDriver;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getLeadFlagText() {
        return leadFlagText;
    }

    public void setLeadFlagText(String leadFlagText) {
        this.leadFlagText = leadFlagText;
    }

    public String getLeadFlag() {
        return leadFlag;
    }

    public void setLeadFlag(String leadFlag) {
        this.leadFlag = leadFlag;
    }

    public String getLbPost() {
        return lbPost;
    }

    public void setLbPost(String lbPost) {
        this.lbPost = lbPost;
    }

    public String getLbPostText() {
        return lbPostText;
    }

    public void setLbPostText(String lbPostText) {
        this.lbPostText = lbPostText;
    }

    public String getGjPost() {
        return gjPost;
    }

    public void setGjPost(String gjPost) {
        this.gjPost = gjPost;
    }

    public String getGjPostText() {
        return gjPostText;
    }

    public void setGjPostText(String gjPostText) {
        this.gjPostText = gjPostText;
    }

    public String getUserNameJp() {
        return userNameJp;
    }

    public void setUserNameJp(String userNameJp) {
        this.userNameJp = userNameJp;
    }

    public String getLinkEmployees() {
        return linkEmployees;
    }

    public void setLinkEmployees(String linkEmployees) {
        this.linkEmployees = linkEmployees;
    }

    public String getLinkEmployeesName() {
        return linkEmployeesName;
    }

    public void setLinkEmployeesName(String linkEmployeesName) {
        this.linkEmployeesName = linkEmployeesName;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
/*public User() {
        super();
    }

    public User(String uuid, String userNameId, String userName) {
        super();
        super.setUuid(uuid);
        this.userNameId = userNameId;
        this.userName = userName;
    }*/

    public String getUserNameId() {
        return userNameId;
    }

    public void setUserNameId(String userNameId) {
        this.userNameId = userNameId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getDataAuth() {
        return dataAuth;
    }

    public void setDataAuth(String dataAuth) {
        this.dataAuth = dataAuth;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
