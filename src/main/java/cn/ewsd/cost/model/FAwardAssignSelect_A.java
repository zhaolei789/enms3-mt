package cn.ewsd.cost.model;

import cn.ewsd.common.model.MCoreBase;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 罚款分配-查询
 * Excel根据领导标记导出
 * @Author zxrmine
 * @Email zxrmine@163.cn
 */
public class FAwardAssignSelect_A extends MCoreBase{
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

	    private String leadFlag;

	    //连表主单据=============
		//发生日期
		private String occDate;
		//责任单位
		private String teamNo;
		private String teamNoName;
		//考核单位
		private String assessTeam;
		private String assessTeamName;
		// 签发人
		private String signEmp;
		// 考核分类
		private String assessCate;
		private String assessCateName;
		// 奖惩原因
		private String reason;


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

	public String getOccDate() {
		return occDate;
	}

	public void setOccDate(String occDate) {
		this.occDate = occDate;
	}

	public String getTeamNo() {
		return teamNo;
	}

	public void setTeamNo(String teamNo) {
		this.teamNo = teamNo;
	}

	public String getTeamNoName() {
		return teamNoName;
	}

	public void setTeamNoName(String teamNoName) {
		this.teamNoName = teamNoName;
	}

	public String getAssessTeam() {
		return assessTeam;
	}

	public void setAssessTeam(String assessTeam) {
		this.assessTeam = assessTeam;
	}

	public String getAssessTeamName() {
		return assessTeamName;
	}

	public void setAssessTeamName(String assessTeamName) {
		this.assessTeamName = assessTeamName;
	}

	public String getSignEmp() {
		return signEmp;
	}

	public void setSignEmp(String signEmp) {
		this.signEmp = signEmp;
	}

	public String getAssessCate() {
		return assessCate;
	}

	public void setAssessCate(String assessCate) {
		this.assessCate = assessCate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getAssessCateName() {
		return assessCateName;
	}

	public void setAssessCateName(String assessCateName) {
		this.assessCateName = assessCateName;
	}

	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}