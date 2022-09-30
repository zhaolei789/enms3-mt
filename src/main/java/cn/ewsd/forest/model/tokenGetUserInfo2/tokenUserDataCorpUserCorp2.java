package cn.ewsd.forest.model.tokenGetUserInfo2;

import java.util.List;

public class tokenUserDataCorpUserCorp2 {
    private String id;
    private String name;
    private String shortName;
    private String code;
    private String remark;
    private String logo;
    private int userLimit;
    private int status;
    private List<tokenUserDataCorpUserCorpDept2> depts;
    private String rootDeptId;
    private int userCnt;
    private long creator;
    private long createAt;
    private long updator;
    private long updateAt;
    private int corpTypeEnums;
    private String corpAndCorpRelTypeEnums;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getUserLimit() {
        return userLimit;
    }

    public void setUserLimit(int userLimit) {
        this.userLimit = userLimit;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<tokenUserDataCorpUserCorpDept2> getDepts() {
        return depts;
    }

    public void setDepts(List<tokenUserDataCorpUserCorpDept2> depts) {
        this.depts = depts;
    }

    public String getRootDeptId() {
        return rootDeptId;
    }

    public void setRootDeptId(String rootDeptId) {
        this.rootDeptId = rootDeptId;
    }

    public int getUserCnt() {
        return userCnt;
    }

    public void setUserCnt(int userCnt) {
        this.userCnt = userCnt;
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

    public int getCorpTypeEnums() {
        return corpTypeEnums;
    }

    public void setCorpTypeEnums(int corpTypeEnums) {
        this.corpTypeEnums = corpTypeEnums;
    }

    public String getCorpAndCorpRelTypeEnums() {
        return corpAndCorpRelTypeEnums;
    }

    public void setCorpAndCorpRelTypeEnums(String corpAndCorpRelTypeEnums) {
        this.corpAndCorpRelTypeEnums = corpAndCorpRelTypeEnums;
    }
}
