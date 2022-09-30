package cn.ewsd.forest.model.getUserDetail;

import java.util.List;

public class UserDetailDepts {
    private String id;
    private String corpId;
    private String outerId;
    private String name;
    private String pid;
    private String pids;
    private int isCreateImgroup;
    private int sort;
    private int isBiz;
    private List<UserDetailDeptsPath> path;
    private String managers;
    private int userCnt;
    private String creator;
    private long createAt;
    private String updator;
    private long updateAt;
    private String position;
    private String flag;
    private String group;
    private String owner;
    private String groupMembers;
    private UserDetailSetDeptVisibility setDeptVisibility;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getOuterId() {
        return outerId;
    }

    public void setOuterId(String outerId) {
        this.outerId = outerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPids() {
        return pids;
    }

    public void setPids(String pids) {
        this.pids = pids;
    }

    public int getIsCreateImgroup() {
        return isCreateImgroup;
    }

    public void setIsCreateImgroup(int isCreateImgroup) {
        this.isCreateImgroup = isCreateImgroup;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getIsBiz() {
        return isBiz;
    }

    public void setIsBiz(int isBiz) {
        this.isBiz = isBiz;
    }

    public List<UserDetailDeptsPath> getPath() {
        return path;
    }

    public void setPath(List<UserDetailDeptsPath> path) {
        this.path = path;
    }

    public String getManagers() {
        return managers;
    }

    public void setManagers(String managers) {
        this.managers = managers;
    }

    public int getUserCnt() {
        return userCnt;
    }

    public void setUserCnt(int userCnt) {
        this.userCnt = userCnt;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(String groupMembers) {
        this.groupMembers = groupMembers;
    }

    public UserDetailSetDeptVisibility getSetDeptVisibility() {
        return setDeptVisibility;
    }

    public void setSetDeptVisibility(UserDetailSetDeptVisibility setDeptVisibility) {
        this.setDeptVisibility = setDeptVisibility;
    }
}
