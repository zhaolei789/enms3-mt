package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.Table;

/**
 * 
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-06-09 17:58:25
 */
@Table(name="t_dept_query")
public class SysUserQryOrg extends MCoreBase{
	private String userId;
	private Integer orgId;
	private String orgName;
	private String orgNo;

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
}