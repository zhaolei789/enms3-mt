package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;

import javax.persistence.Table;


@Table(name="sys_auth_group")
public class AuthGroup extends MCoreBase {
	
	private Integer pid;
	private Integer id;
	private Integer menuId;
	private Integer sort;
	private String text;
	private String status;
	private String state;
	private String code;
	private String codeSetId;
	private Integer levelId;
	private String remark;
	private Integer isDel;


	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	public AuthGroup() {
		super();
	}



	public Integer getPid() {
		return pid;
	}



	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSort() {
		return sort;
	}



	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getText() {
		return text;
	}



	public void setText(String text) {
		this.text = text;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}


	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}



	public String getCodeSetId() {
		return codeSetId;
	}



	public void setCodeSetId(String codeSetId) {
		this.codeSetId = codeSetId;
	}



	public Integer getLevelId() {
		return levelId;
	}



	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}



	public Integer getMenuId() {
		return menuId;
	}



	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
}
	