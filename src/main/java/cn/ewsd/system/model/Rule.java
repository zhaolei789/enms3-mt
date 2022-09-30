package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;

import javax.persistence.Table;


@Table(name="sys_auth_rule")
public class Rule extends MCoreBase {
	
	private String pid;
	private String sort;
	private String name;
	private String title;
	private Integer status;
	private String condition2;
	private String level_rule;
	private String type;
	
	public Rule() {
		super();
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCondition2() {
		return condition2;
	}

	public void setCondition2(String condition2) {
		this.condition2 = condition2;
	}

	public String getLevel_rule() {
		return level_rule;
	}

	public void setLevel_rule(String level_rule) {
		this.level_rule = level_rule;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}
	