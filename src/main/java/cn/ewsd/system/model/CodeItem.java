package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;

import javax.persistence.Table;


@Table(name="sys_code_item")
public class CodeItem extends MCoreBase {
	private Integer	sort;
	private Integer levelId;
	private String codeSetId;
	private String code;
	private String status;
	private String url;
	
	private String id;
	private String pid;
	private String text;
	private String state;
	private String iconCls;
	private String checked;
	private String attributes;
	private String children;

	private String type;
	private String remark;


	public CodeItem() {
		super();
	}
	
	public CodeItem(String id, String pid, String text, String code, String state) {
		this.id = id;
		this.pid = pid;
		this.text = text;
		this.code = code;
		this.state = state;
	}

	public CodeItem(String uuid, String codeSetId, String id, String pid, String code, String text, String state, String iconCls) {
		super.setUuid(uuid);
		this.codeSetId = codeSetId;
		this.id = id;
		this.pid = pid;
		this.code = code;
		this.text = text;
		this.state = state;
		this.iconCls = iconCls;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
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


	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}


	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}


	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getChildren() {
		return children;
	}

	public void setChildren(String children) {
		this.children = children;
	}

	public String getCodeSetId() {
		return codeSetId;
	}

	public void setCodeSetId(String codeSetId) {
		this.codeSetId = codeSetId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


}