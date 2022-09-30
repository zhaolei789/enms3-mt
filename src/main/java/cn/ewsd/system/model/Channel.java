package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;


import javax.persistence.*;


@Table(name="sys_channel")
public class Channel extends MCoreBase {

	private Integer id;
	private Integer pid;
	private String text;
	private String checked;
	private String attributes;
	private Integer levelId;
	private String state;
	private Integer sort;

	private String module;
	private String name;
	private String code;
	private String model;
	private String url;
	private String thumbnail;
	private String keywords;
	private String description;
	private String summary;
	private String content;
	private String position;
	private Integer status;
	private String target;
	private Integer viewNums;

	public Channel() {
		super();
	}

	public Channel(String uuid, Integer id, Integer pid, String text, String description, String code, String url, String thumbnail) {
		super();
		this.setUuid(uuid);
		this.setId(id);
		this.setPid(pid);
		this.setText(text);
		this.setDescription(description);
		this.setCode(code);
		this.setUrl(url);
		this.setThumbnail(thumbnail);
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
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

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
	//@Type(type = "text")
	@Column(nullable = false)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Column(length = 11, nullable = false, columnDefinition = "INT default 0")
	public Integer getViewNums() {
		return viewNums;
	}

	public void setViewNums(Integer viewNums) {
		this.viewNums = viewNums;
	}
}
	