package cn.ewsd.cost.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import cn.ewsd.common.model.MCoreBase;
import javax.persistence.Table;

/**
 * 罚款分配
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-08-26 18:03:29
 */
@Table(name="f_award_assign")
public class FAwardAssign extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
		// 分配ID
	    private Long assignId;
				// 罚款ID
	    private String awardId;
				// 员工ID
	    private String empId;
				// 员工姓名
	    private String empName;
				// 罚款金额
	    private BigDecimal awardBala;
				// 分配说明
	    private String remark;
				// 分配类型
	    private String assignType;
				// 上报日期
	    private String modiDate;
				// 上报人员
	    private String modiUser;
				// 用户描述
	    private String userName;
				// 删除标记
	    private int isDel;

	    private String leadFlag;

	public String getLeadFlag() {
		return leadFlag;
	}

	public void setLeadFlag(String leadFlag) {
		this.leadFlag = leadFlag;
	}

	/**
	 * 设置：分配ID
	 */
	public void setAssignId(Long assignId) {
		this.assignId = assignId;
	}

	/**
	 * 获取：分配ID
	 */
	public Long getAssignId() {
		return assignId;
	}
		
	/**
	 * 设置：罚款ID
	 */
	public void setAwardId(String awardId) {
		this.awardId = awardId;
	}

	/**
	 * 获取：罚款ID
	 */
	public String getAwardId() {
		return awardId;
	}
		
	/**
	 * 设置：员工ID
	 */
	public void setEmpId(String empId) {
		this.empId = empId;
	}

	/**
	 * 获取：员工ID
	 */
	public String getEmpId() {
		return empId;
	}
		
	/**
	 * 设置：员工姓名
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}

	/**
	 * 获取：员工姓名
	 */
	public String getEmpName() {
		return empName;
	}
		
	/**
	 * 设置：罚款金额
	 */
	public void setAwardBala(BigDecimal awardBala) {
		this.awardBala = awardBala;
	}

	/**
	 * 获取：罚款金额
	 */
	public BigDecimal getAwardBala() {
		return awardBala;
	}
		
	/**
	 * 设置：分配说明
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取：分配说明
	 */
	public String getRemark() {
		return remark;
	}
		
	/**
	 * 设置：分配类型
	 */
	public void setAssignType(String assignType) {
		this.assignType = assignType;
	}

	/**
	 * 获取：分配类型
	 */
	public String getAssignType() {
		return assignType;
	}
		
	/**
	 * 设置：上报日期
	 */
	public void setModiDate(String modiDate) {
		this.modiDate = modiDate;
	}

	/**
	 * 获取：上报日期
	 */
	public String getModiDate() {
		return modiDate;
	}
		
	/**
	 * 设置：上报人员
	 */
	public void setModiUser(String modiUser) {
		this.modiUser = modiUser;
	}

	/**
	 * 获取：上报人员
	 */
	public String getModiUser() {
		return modiUser;
	}
		
	/**
	 * 设置：用户描述
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 获取：用户描述
	 */
	public String getUserName() {
		return userName;
	}
		
	/**
	 * 设置：删除标记
	 */
	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}

	/**
	 * 获取：删除标记
	 */
	public int getIsDel() {
		return isDel;
	}
									
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}