package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;

import javax.persistence.Table;


@Table(name="sys_operationlog")
public class Operationlog extends MCoreBase {

	private String content;
	private Integer status;
	
	public Operationlog() {
		super();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
