package cn.ewsd.mdata.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import cn.ewsd.common.model.MCoreBase;
import javax.persistence.Table;

/**
 * 部门分管领导
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2022-04-14 11:12:56
 */
@Table(name="sys_organization_master")
public class SysOrganizationMaster extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
										// 
	    private Integer isDel;
				// 
	    private String orgUuid;
				// 
	    private Integer orgId;
				// 
	    private Integer orgLevel;
				// 领导ID
	    private String masterUuid;
				// 领导名称
	    private String masterName;
				// 领导职务
	    private String masterPositionName;
		
									
	/**
	 * 设置：
	 */
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	/**
	 * 获取：
	 */
	public Integer getIsDel() {
		return isDel;
	}
		
	/**
	 * 设置：
	 */
	public void setOrgUuid(String orgUuid) {
		this.orgUuid = orgUuid;
	}

	/**
	 * 获取：
	 */
	public String getOrgUuid() {
		return orgUuid;
	}
		
	/**
	 * 设置：
	 */
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	/**
	 * 获取：
	 */
	public Integer getOrgId() {
		return orgId;
	}
		
	/**
	 * 设置：
	 */
	public void setOrgLevel(Integer orgLevel) {
		this.orgLevel = orgLevel;
	}

	/**
	 * 获取：
	 */
	public Integer getOrgLevel() {
		return orgLevel;
	}
		
	/**
	 * 设置：领导ID
	 */
	public void setMasterUuid(String masterUuid) {
		this.masterUuid = masterUuid;
	}

	/**
	 * 获取：领导ID
	 */
	public String getMasterUuid() {
		return masterUuid;
	}
		
	/**
	 * 设置：领导名称
	 */
	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	/**
	 * 获取：领导名称
	 */
	public String getMasterName() {
		return masterName;
	}
		
	/**
	 * 设置：领导职务
	 */
	public void setMasterPositionName(String masterPositionName) {
		this.masterPositionName = masterPositionName;
	}

	/**
	 * 获取：领导职务
	 */
	public String getMasterPositionName() {
		return masterPositionName;
	}
	
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}