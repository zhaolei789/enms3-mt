package cn.ewsd.system.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import cn.ewsd.common.model.MCoreBase;
import javax.persistence.Table;

/**
 * 
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-04-23 11:19:10
 */
@Table(name="sys_user_step")
public class SysUserStep extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
										// 用户
	    private String userId;
				// 流程ID
	    private String processUuid;
				// 步骤ID
	    private String stepUuid;
				// 审核部门
	    private Integer auditOrg;

	    //=====================
		private String processName;
		private String stepName;
		private String orgName;


	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * 设置：用户
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 获取：用户
	 */
	public String getUserId() {
		return userId;
	}
		
	/**
	 * 设置：流程ID
	 */
	public void setProcessUuid(String processUuid) {
		this.processUuid = processUuid;
	}

	/**
	 * 获取：流程ID
	 */
	public String getProcessUuid() {
		return processUuid;
	}
		
	/**
	 * 设置：步骤ID
	 */
	public void setStepUuid(String stepUuid) {
		this.stepUuid = stepUuid;
	}

	/**
	 * 获取：步骤ID
	 */
	public String getStepUuid() {
		return stepUuid;
	}
		
	/**
	 * 设置：审核部门
	 */
	public void setAuditOrg(Integer auditOrg) {
		this.auditOrg = auditOrg;
	}

	/**
	 * 获取：审核部门
	 */
	public Integer getAuditOrg() {
		return auditOrg;
	}
	
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}