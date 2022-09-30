package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;

import javax.persistence.Table;


@Table(name="sys_remind")
public class Remind extends MCoreBase {

	private Integer remindId;
	private String remindModuleName;
	private Integer remindPid;
	private String remindTitle;
	private String remindUrl;
	private String remindSubTime;
	private String remindReminder;
	private Integer remindIsRemind;
	private String remindTime;
	
	public Remind() {
		super();
	}

	public Integer getRemindId() {
		return remindId;
	}

	public void setRemindId(Integer remindId) {
		this.remindId = remindId;
	}

	public String getRemindModuleName() {
		return remindModuleName;
	}

	public void setRemindModuleName(String remindModuleName) {
		this.remindModuleName = remindModuleName;
	}

	public Integer getRemindPid() {
		return remindPid;
	}

	public void setRemindPid(Integer remindPid) {
		this.remindPid = remindPid;
	}

	public String getRemindTitle() {
		return remindTitle;
	}

	public void setRemindTitle(String remindTitle) {
		this.remindTitle = remindTitle;
	}

	public String getRemindUrl() {
		return remindUrl;
	}

	public void setRemindUrl(String remindUrl) {
		this.remindUrl = remindUrl;
	}

	public String getRemindSubTime() {
		return remindSubTime;
	}

	public void setRemindSubTime(String remindSubTime) {
		this.remindSubTime = remindSubTime;
	}

	public String getRemindReminder() {
		return remindReminder;
	}

	public void setRemindReminder(String remindReminder) {
		this.remindReminder = remindReminder;
	}

	public Integer getRemindIsRemind() {
		return remindIsRemind;
	}

	public void setRemindIsRemind(Integer remindIsRemind) {
		this.remindIsRemind = remindIsRemind;
	}

	public String getRemindTime() {
		return remindTime;
	}

	public void setRemindTime(String remindTime) {
		this.remindTime = remindTime;
	}
	
}
