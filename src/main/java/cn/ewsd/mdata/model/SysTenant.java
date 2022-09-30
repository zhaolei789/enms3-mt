package cn.ewsd.mdata.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import cn.ewsd.common.model.MCoreBase;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Table;

/**
 * 租户
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2022-06-02 18:20:18
 */
@Table(name="sys_tenant")
public class SysTenant extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
										// 企业名称
	    private String tenantName;
				// LOGO
	    private String logo;
				// 建立时间
	    private String establishDate;
				// 邮箱
	    private String businessEmail;
				// 电话
	    private String businessPhone;
				// 业务名称
	    private String businessName;
				// 法人
	    private String legalPerson;
				// 地址
	    private String corpAddress;
				// 业务范围
	    private String businessScope;
				// 管理员名称
	    private String adminName;
				// 管理员电话
	    private String adminPhone;
				// 启用时间
	    @DateTimeFormat(
        pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
        pattern = "yyyy-MM-dd HH:mm:ss",
        timezone = "GMT+8"
    )
    private Date enableTime;
				// 过期时间
	    @DateTimeFormat(
        pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
        pattern = "yyyy-MM-dd HH:mm:ss",
        timezone = "GMT+8"
    )
    private Date overdueTime;
				// 备注
	    private String note;
				// 类型
	    private Integer type;
				// 状态1启用2禁用
	    private Integer status;
				// 0
	    private Integer isDel;
		
									
	/**
	 * 设置：企业名称
	 */
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	/**
	 * 获取：企业名称
	 */
	public String getTenantName() {
		return tenantName;
	}
		
	/**
	 * 设置：LOGO
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

	/**
	 * 获取：LOGO
	 */
	public String getLogo() {
		return logo;
	}
		
	/**
	 * 设置：建立时间
	 */
	public void setEstablishDate(String establishDate) {
		this.establishDate = establishDate;
	}

	/**
	 * 获取：建立时间
	 */
	public String getEstablishDate() {
		return establishDate;
	}
		
	/**
	 * 设置：邮箱
	 */
	public void setBusinessEmail(String businessEmail) {
		this.businessEmail = businessEmail;
	}

	/**
	 * 获取：邮箱
	 */
	public String getBusinessEmail() {
		return businessEmail;
	}
		
	/**
	 * 设置：电话
	 */
	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}

	/**
	 * 获取：电话
	 */
	public String getBusinessPhone() {
		return businessPhone;
	}
		
	/**
	 * 设置：业务名称
	 */
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	/**
	 * 获取：业务名称
	 */
	public String getBusinessName() {
		return businessName;
	}
		
	/**
	 * 设置：法人
	 */
	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	/**
	 * 获取：法人
	 */
	public String getLegalPerson() {
		return legalPerson;
	}
		
	/**
	 * 设置：地址
	 */
	public void setCorpAddress(String corpAddress) {
		this.corpAddress = corpAddress;
	}

	/**
	 * 获取：地址
	 */
	public String getCorpAddress() {
		return corpAddress;
	}
		
	/**
	 * 设置：业务范围
	 */
	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	/**
	 * 获取：业务范围
	 */
	public String getBusinessScope() {
		return businessScope;
	}
		
	/**
	 * 设置：管理员名称
	 */
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	/**
	 * 获取：管理员名称
	 */
	public String getAdminName() {
		return adminName;
	}
		
	/**
	 * 设置：管理员电话
	 */
	public void setAdminPhone(String adminPhone) {
		this.adminPhone = adminPhone;
	}

	/**
	 * 获取：管理员电话
	 */
	public String getAdminPhone() {
		return adminPhone;
	}
		
	/**
	 * 设置：启用时间
	 */
	public void setEnableTime(Date enableTime) {
		this.enableTime = enableTime;
	}

	/**
	 * 获取：启用时间
	 */
	public Date getEnableTime() {
		return enableTime;
	}
		
	/**
	 * 设置：过期时间
	 */
	public void setOverdueTime(Date overdueTime) {
		this.overdueTime = overdueTime;
	}

	/**
	 * 获取：过期时间
	 */
	public Date getOverdueTime() {
		return overdueTime;
	}
		
	/**
	 * 设置：备注
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * 获取：备注
	 */
	public String getNote() {
		return note;
	}
		
	/**
	 * 设置：类型
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * 获取：类型
	 */
	public Integer getType() {
		return type;
	}
		
	/**
	 * 设置：状态1启用2禁用
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取：状态1启用2禁用
	 */
	public Integer getStatus() {
		return status;
	}
		
	/**
	 * 设置：0
	 */
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	/**
	 * 获取：0
	 */
	public Integer getIsDel() {
		return isDel;
	}
	
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}