package cn.ewsd.fix.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import cn.ewsd.common.model.MCoreBase;
import javax.persistence.Table;

/**
 * 
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-05-13 15:11:59
 */
@Table(name="m_fix_assess")
public class MFixAssess extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
	    private String assessId;
	    private String assType;
	    private String assMonth;
	    private Integer mngTeam;
	    private Integer teamNo;
	    private String prjNo;
	    private String prjName;
	    private BigDecimal planValue;
	    private BigDecimal workValue;
	    private String remark;
	    private String assStatus;
	    private String applyUser;
	    private String applyDate;
	    private String agreeUser;
	    private String agreeDate;

	public void setAssessId(String assessId) {
		this.assessId = assessId;
	}

	/**
	 * 获取：考核ID
	 */
	public String getAssessId() {
		return assessId;
	}
		
	/**
	 * 设置：考核类型
	 */
	public void setAssType(String assType) {
		this.assType = assType;
	}

	/**
	 * 获取：考核类型
	 */
	public String getAssType() {
		return assType;
	}
		
	/**
	 * 设置：考核月份
	 */
	public void setAssMonth(String assMonth) {
		this.assMonth = assMonth;
	}

	/**
	 * 获取：考核月份
	 */
	public String getAssMonth() {
		return assMonth;
	}
		
	/**
	 * 设置：业务科室
	 */
	public void setMngTeam(Integer mngTeam) {
		this.mngTeam = mngTeam;
	}

	/**
	 * 获取：业务科室
	 */
	public Integer getMngTeam() {
		return mngTeam;
	}
		
	/**
	 * 设置：施工单位
	 */
	public void setTeamNo(Integer teamNo) {
		this.teamNo = teamNo;
	}

	/**
	 * 获取：施工单位
	 */
	public Integer getTeamNo() {
		return teamNo;
	}
		
	/**
	 * 设置：工程编号
	 */
	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
	}

	/**
	 * 获取：工程编号
	 */
	public String getPrjNo() {
		return prjNo;
	}
		
	/**
	 * 设置：工程名称
	 */
	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	/**
	 * 获取：工程名称
	 */
	public String getPrjName() {
		return prjName;
	}
		
	/**
	 * 设置：计划完成
	 */
	public void setPlanValue(BigDecimal planValue) {
		this.planValue = planValue;
	}

	/**
	 * 获取：计划完成
	 */
	public BigDecimal getPlanValue() {
		return planValue;
	}
		
	/**
	 * 设置：生产完成
	 */
	public void setWorkValue(BigDecimal workValue) {
		this.workValue = workValue;
	}

	/**
	 * 获取：生产完成
	 */
	public BigDecimal getWorkValue() {
		return workValue;
	}
		
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
		
	/**
	 * 设置：考核状态
	 */
	public void setAssStatus(String assStatus) {
		this.assStatus = assStatus;
	}

	/**
	 * 获取：考核状态
	 */
	public String getAssStatus() {
		return assStatus;
	}
		
	/**
	 * 设置：申请人
	 */
	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	/**
	 * 获取：申请人
	 */
	public String getApplyUser() {
		return applyUser;
	}
		
	/**
	 * 设置：申请日期
	 */
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	/**
	 * 获取：申请日期
	 */
	public String getApplyDate() {
		return applyDate;
	}
		
	/**
	 * 设置：审核人
	 */
	public void setAgreeUser(String agreeUser) {
		this.agreeUser = agreeUser;
	}

	/**
	 * 获取：审核人
	 */
	public String getAgreeUser() {
		return agreeUser;
	}
		
	/**
	 * 设置：审核日期
	 */
	public void setAgreeDate(String agreeDate) {
		this.agreeDate = agreeDate;
	}

	/**
	 * 获取：审核日期
	 */
	public String getAgreeDate() {
		return agreeDate;
	}
	
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}