package cn.ewsd.mdata.model;

import cn.ewsd.common.model.MCoreBase;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.Table;

/**
 * 
 * 
 * @Author zhaoxiace
 * @Email zhaoxiace@ewsd.cn
 * @Date 2019-05-06 14:04:50
 */
@Table(name="sys_user_post")
public class SysUserPost extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
										// 
	    private Integer isDel;
				// 
	    private String remark;
				// 
	    private String puuid;
				// 
	    private String unit;
				// 
	    private String company;
				// 
	    private String department;
				// 
	    private String office;
				// 
	    private String group;
				// 
	    private String post;
				// 
	    private String userNameId;

	    private String orgId;


	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
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
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取：
	 */
	public String getRemark() {
		return remark;
	}
		
	/**
	 * 设置：
	 */
	public void setPuuid(String puuid) {
		this.puuid = puuid;
	}

	/**
	 * 获取：
	 */
	public String getPuuid() {
		return puuid;
	}
		
	/**
	 * 设置：
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * 获取：
	 */
	public String getUnit() {
		return unit;
	}
		
	/**
	 * 设置：
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * 获取：
	 */
	public String getCompany() {
		return company;
	}
		
	/**
	 * 设置：
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * 获取：
	 */
	public String getDepartment() {
		return department;
	}
		
	/**
	 * 设置：
	 */
	public void setOffice(String office) {
		this.office = office;
	}

	/**
	 * 获取：
	 */
	public String getOffice() {
		return office;
	}
		
	/**
	 * 设置：
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * 获取：
	 */
	public String getGroup() {
		return group;
	}
		
	/**
	 * 设置：
	 */
	public void setPost(String post) {
		this.post = post;
	}

	/**
	 * 获取：
	 */
	public String getPost() {
		return post;
	}
		
	/**
	 * 设置：
	 */
	public void setUserNameId(String userNameId) {
		this.userNameId = userNameId;
	}

	/**
	 * 获取：
	 */
	public String getUserNameId() {
		return userNameId;
	}
	
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}