package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.Table;


/**
 * 
 * 
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-07-02 08:49:48
 */
@Table(name="sys_auth_data")
public class SysAuthData extends MCoreBase {
	private static final long serialVersionUID = 1L;
	
								
	//
	private Integer isDel;

		
	//
	private Integer id;

		
	//
	private Integer pid;

		
	//
	private String text;

		
	//
	private String state;

		
	//
	private String status;

		
	//
	private String orgId;

		
	//
	private Integer sort;

		
	//
	private Integer levelId;

		
	//
	private String remark;


	private String orgName;

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

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
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
		
	/**
	 * 设置：
	 */
	public void setPid(Integer pid) {
		this.pid = pid;
	}

	/**
	 * 获取：
	 */
	public Integer getPid() {
		return pid;
	}
		
	/**
	 * 设置：
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 获取：
	 */
	public String getText() {
		return text;
	}
		
	/**
	 * 设置：
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * 获取：
	 */
	public String getState() {
		return state;
	}
		
	/**
	 * 设置：
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 获取：
	 */
	public String getStatus() {
		return status;
	}
		
	/**
	 * 设置：
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	/**
	 * 获取：
	 */
	public String getOrgId() {
		return orgId;
	}
		
	/**
	 * 设置：
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}

	/**
	 * 获取：
	 */
	public Integer getSort() {
		return sort;
	}
		
	/**
	 * 设置：
	 */
	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}

	/**
	 * 获取：
	 */
	public Integer getLevelId() {
		return levelId;
	}
		
	/**
	 * 设置：
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取：
	 */
	public String getRemark() {
		return remark;
	}
	
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}