package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;

import javax.persistence.Table;


@Table(name="sys_branch")
public class Branch extends MCoreBase {
	
	private String cid;
	private String fid;
	private String level_branch;
	private String oid;
	private String type;
	private String name;
	private String code;
	private String manager;
	private String tel1;
	private String tel2;
	private String fax;
	private String address;
	private String postCode;
	private String status;
	private String fcdFromDate;
	private String fcdToDate;
	
	public Branch() {
		super();
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getLevel_branch() {
		return level_branch;
	}

	public void setLevel_branch(String level_branch) {
		this.level_branch = level_branch;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getTel1() {
		return tel1;
	}

	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}

	public String getTel2() {
		return tel2;
	}

	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFcdFromDate() {
		return fcdFromDate;
	}

	public void setFcdFromDate(String fcdFromDate) {
		this.fcdFromDate = fcdFromDate;
	}

	public String getFcdToDate() {
		return fcdToDate;
	}

	public void setFcdToDate(String fcdToDate) {
		this.fcdToDate = fcdToDate;
	}


	
}
	