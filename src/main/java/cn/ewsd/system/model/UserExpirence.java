package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;

import javax.persistence.Table;


@Table(name="sys_user_expirence")
public class UserExpirence extends MCoreBase {
	
	private String puuid; 
	private String content;
	
	public UserExpirence() {
		super();
	}

	public String getPuuid() {
		return puuid;
	}

	public void setPuuid(String puuid) {
		this.puuid = puuid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
                         
}
