package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;

import javax.persistence.Column;
import javax.persistence.Table;


@Table(name="sys_config")
public class Config extends MCoreBase {

	private String type;
	private String name;
	private String code;
	private String value;
	private String description;
	private Integer modifyFlag;
	private Integer sort;

	public Config() {
		super();
	}

	public Config(String name, String code, String value) {
		this.name = name;
		this.code = code;
		this.value = value;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(nullable = false, columnDefinition = "INT default 1")
	public Integer getModifyFlag() {
		return modifyFlag;
	}

	public void setModifyFlag(Integer modifyFlag) {
		this.modifyFlag = modifyFlag;
	}
}
