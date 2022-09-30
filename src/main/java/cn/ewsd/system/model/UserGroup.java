package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;

import javax.persistence.Table;


@Table(name="sys_user_group")
public class UserGroup extends MCoreBase {
	
	private Integer id;
	private String sort;
	private String name;
	private String description;
	
	public UserGroup() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
