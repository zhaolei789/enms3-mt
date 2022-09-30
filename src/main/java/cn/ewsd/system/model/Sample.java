package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;

import javax.persistence.Table;


@Table(name="sys_sample")
public class Sample extends MCoreBase {

	private String title;
	private String content;
	
	public Sample() {
		super();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
