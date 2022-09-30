//package cn.ewsd.system.model;
//
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import java.util.Date;
//
//@Entity
//@Table(name = "sys_user")
//public class User {
//    @Id
//    private String uuid;
//    private Date createtime;
//    private String creator;
//    private String creatorid;
//    private String modifier;
//    private String modifierid;
//    private Date modifytime;
//
//    private String userNameId;
//    private String password;
//    private String userName;
//    private String nickName;
//    private String avatar;
//    private String sex;
//    private Integer age;
//    private String idCard;
//    private String birthday;
//    private String telephone;
//    private String cellphone;
//    private String email;
//    private String school;
//    private String education;//学历
//    private String degree;//学位
//    private String nation;//民族
//    private String nativePlace;//籍贯
//    private String zone;
//    private String country;
//    private String province;
//    private String city;
//    private String district;
//    private String orgId;
//    private String orgName;
//    private String post;
//    private String userGroup;
//    private String dataAuth;
//    private Integer status;
//    private String postText;
//
//    public String getUuid() {
//        return uuid;
//    }
//
//    public void setUuid(String uuid) {
//        this.uuid = uuid;
//    }
//
//    public Date getCreatetime() {
//        return createtime;
//    }
//
//    public void setCreatetime(Date createtime) {
//        this.createtime = createtime;
//    }
//
//    public String getCreator() {
//        return creator;
//    }
//
//    public void setCreator(String creator) {
//        this.creator = creator;
//    }
//
//    public String getCreatorid() {
//        return creatorid;
//    }
//
//    public void setCreatorid(String creatorid) {
//        this.creatorid = creatorid;
//    }
//
//    public String getModifier() {
//        return modifier;
//    }
//
//    public void setModifier(String modifier) {
//        this.modifier = modifier;
//    }
//
//    public String getModifierid() {
//        return modifierid;
//    }
//
//    public void setModifierid(String modifierid) {
//        this.modifierid = modifierid;
//    }
//
//    public Date getModifytime() {
//        return modifytime;
//    }
//
//    public void setModifytime(Date modifytime) {
//        this.modifytime = modifytime;
//    }
//
//    public String getUserNameId() {
//        return userNameId;
//    }
//
//    public void setUserNameId(String userNameId) {
//        this.userNameId = userNameId;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public String getNickName() {
//        return nickName;
//    }
//
//    public void setNickName(String nickName) {
//        this.nickName = nickName;
//    }
//
//    public String getSex() {
//        return sex;
//    }
//
//    public void setSex(String sex) {
//        this.sex = sex;
//    }
//
//    public Integer getAge() {
//        return age;
//    }
//
//    public void setAge(Integer age) {
//        this.age = age;
//    }
//
//    public String getIdCard() {
//        return idCard;
//    }
//
//    public void setIdCard(String idCard) {
//        this.idCard = idCard;
//    }
//
//    public String getBirthday() {
//        return birthday;
//    }
//
//    public void setBirthday(String birthday) {
//        this.birthday = birthday;
//    }
//
//    public String getTelephone() {
//        return telephone;
//    }
//
//    public void setTelephone(String telephone) {
//        this.telephone = telephone;
//    }
//
//    public String getCellphone() {
//        return cellphone;
//    }
//
//    public void setCellphone(String cellphone) {
//        this.cellphone = cellphone;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getSchool() {
//        return school;
//    }
//
//    public void setSchool(String school) {
//        this.school = school;
//    }
//
//    public String getEducation() {
//        return education;
//    }
//
//    public void setEducation(String education) {
//        this.education = education;
//    }
//
//    public String getDegree() {
//        return degree;
//    }
//
//    public void setDegree(String degree) {
//        this.degree = degree;
//    }
//
//    public String getNation() {
//        return nation;
//    }
//
//    public void setNation(String nation) {
//        this.nation = nation;
//    }
//
//    public String getNativePlace() {
//        return nativePlace;
//    }
//
//    public void setNativePlace(String nativePlace) {
//        this.nativePlace = nativePlace;
//    }
//
//    public String getZone() {
//        return zone;
//    }
//
//    public void setZone(String zone) {
//        this.zone = zone;
//    }
//
//    public String getCountry() {
//        return country;
//    }
//
//    public void setCountry(String country) {
//        this.country = country;
//    }
//
//    public String getProvince() {
//        return province;
//    }
//
//    public void setProvince(String province) {
//        this.province = province;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getDistrict() {
//        return district;
//    }
//
//    public void setDistrict(String district) {
//        this.district = district;
//    }
//
//    public String getOrgId() {
//        return orgId;
//    }
//
//    public void setOrgId(String orgId) {
//        this.orgId = orgId;
//    }
//
//    public String getOrgName() {
//        return orgName;
//    }
//
//    public void setOrgName(String orgName) {
//        this.orgName = orgName;
//    }
//
//    public String getPost() {
//        return post;
//    }
//
//    public void setPost(String post) {
//        this.post = post;
//    }
//
//    public String getUserGroup() {
//        return userGroup;
//    }
//
//    public void setUserGroup(String userGroup) {
//        this.userGroup = userGroup;
//    }
//
//    public Integer getStatus() {
//        return status;
//    }
//
//    public void setStatus(Integer status) {
//        this.status = status;
//    }
//
//    public String getPostText() {
//        return postText;
//    }
//
//    public void setPostText(String postText) {
//        this.postText = postText;
//    }
//
//    public String getDataAuth() {
//        return dataAuth;
//    }
//
//    public void setDataAuth(String dataAuth) {
//        this.dataAuth = dataAuth;
//    }
//
//    public String getAvatar() {
//        return avatar;
//    }
//
//    public void setAvatar(String avatar) {
//        this.avatar = avatar;
//    }
//}