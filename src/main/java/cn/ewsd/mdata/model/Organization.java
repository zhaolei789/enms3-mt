package cn.ewsd.mdata.model;

import cn.ewsd.common.model.MCoreBase;

import javax.persistence.Table;
import java.util.Date;

@Table(name="sys_organization")
public class Organization extends MCoreBase {

	private String typeValue;
	private String typeText;
	private Integer id;
	private Integer pid;
	private String text;
	private String checked;
	private String state;
	private String attributes;
	private Integer levelId;
	private Integer sort;
	private String code;
	private String status;
	private Integer isDel;
	private String leaderId;
	private String iconCls;
	private String orgType;
	private String deptCode;
	private String workTeam;
	private String deptType2;
	private String remark;
	private String leader;
	private String tempLeader;
	private String tempLeaderId;
	private Date authStartDate;
	private Date authEndDate;
	private String tenantId;
	private Integer entryWay;

	public Integer getEntryWay() {
		return entryWay;
	}

	public void setEntryWay(Integer entryWay) {
		this.entryWay = entryWay;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public String getTempLeader() {
		return tempLeader;
	}

	public void setTempLeader(String tempLeader) {
		this.tempLeader = tempLeader;
	}

	public String getTempLeaderId() {
		return tempLeaderId;
	}

	public void setTempLeaderId(String tempLeaderId) {
		this.tempLeaderId = tempLeaderId;
	}

	public Date getAuthStartDate() {
		return authStartDate;
	}

	public void setAuthStartDate(Date authStartDate) {
		this.authStartDate = authStartDate;
	}

	public Date getAuthEndDate() {
		return authEndDate;
	}

	public void setAuthEndDate(Date authEndDate) {
		this.authEndDate = authEndDate;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getWorkTeam() {
		return workTeam;
	}

	public void setWorkTeam(String workTeam) {
		this.workTeam = workTeam;
	}

	public String getDeptType2() {
		return deptType2;
	}

	public void setDeptType2(String deptType2) {
		this.deptType2 = deptType2;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	public String getTypeText() {
		return typeText;
	}

	public void setTypeText(String typeText) {
		this.typeText = typeText;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public String getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
}
