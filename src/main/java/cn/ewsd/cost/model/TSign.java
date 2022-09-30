package cn.ewsd.cost.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import cn.ewsd.common.model.MCoreBase;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Table;

/**
 * 
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-17 10:27:06
 */
@Table(name="t_sign")
public class TSign extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
									// 
	    @DateTimeFormat(
        pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
        pattern = "yyyy-MM-dd HH:mm:ss",
        timezone = "GMT+8"
    )
    private Date modifierTime;
				// 业务ID
	    private String billId;
				// 审批用户ID
	    private String userId;
				// 用户名称
	    private String userName;
				// 审核ID
	    private String checkId;
				// 
	    private String checkDate;
				// 
	    private String checkTime;
				// 0待审 1同意 2驳回
	    private String result;
				// 
	    private String remark;
				// 
	    private String stepKey;
				// 
	    private String stepCode;


	    //连表参数
		private String auditStepName;
		private String delStatus;
	private String delReason;
	private String occDate;
	private String modiDate;
	private String assignDate;
	private String noticeNo;
	private String teamNo;
	private String teamNoName;
	private String assessType;
	private BigDecimal awardBala;
	private String assessTeam;
	private String assessTeamName;
	private String modiTeam;
	private String modiTeamName;
	private String signEmp;
	private String assessCate;
	private String reason;


	public String getTeamNoName() {
		return teamNoName;
	}

	public void setTeamNoName(String teamNoName) {
		this.teamNoName = teamNoName;
	}

	public String getAssessTeamName() {
		return assessTeamName;
	}

	public void setAssessTeamName(String assessTeamName) {
		this.assessTeamName = assessTeamName;
	}

	public String getModiTeamName() {
		return modiTeamName;
	}

	public void setModiTeamName(String modiTeamName) {
		this.modiTeamName = modiTeamName;
	}

	public String getAuditStepName() {
		return auditStepName;
	}

	public void setAuditStepName(String auditStepName) {
		this.auditStepName = auditStepName;
	}

	public String getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}

	public String getDelReason() {
		return delReason;
	}

	public void setDelReason(String delReason) {
		this.delReason = delReason;
	}

	public String getOccDate() {
		return occDate;
	}

	public void setOccDate(String occDate) {
		this.occDate = occDate;
	}

	public String getModiDate() {
		return modiDate;
	}

	public void setModiDate(String modiDate) {
		this.modiDate = modiDate;
	}

	public String getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}

	public String getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(String noticeNo) {
		this.noticeNo = noticeNo;
	}

	public String getTeamNo() {
		return teamNo;
	}

	public void setTeamNo(String teamNo) {
		this.teamNo = teamNo;
	}

	public String getAssessType() {
		return assessType;
	}

	public void setAssessType(String assessType) {
		this.assessType = assessType;
	}

	public BigDecimal getAwardBala() {
		return awardBala;
	}

	public void setAwardBala(BigDecimal awardBala) {
		this.awardBala = awardBala;
	}

	public String getAssessTeam() {
		return assessTeam;
	}

	public void setAssessTeam(String assessTeam) {
		this.assessTeam = assessTeam;
	}

	public String getModiTeam() {
		return modiTeam;
	}

	public void setModiTeam(String modiTeam) {
		this.modiTeam = modiTeam;
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

	/**
	 * 设置：
	 */
	public void setModifierTime(Date modifierTime) {
		this.modifierTime = modifierTime;
	}

	/**
	 * 获取：
	 */
	public Date getModifierTime() {
		return modifierTime;
	}
		
	/**
	 * 设置：业务ID
	 */
	public void setBillId(String billId) {
		this.billId = billId;
	}

	/**
	 * 获取：业务ID
	 */
	public String getBillId() {
		return billId;
	}
		
	/**
	 * 设置：审批用户ID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 获取：审批用户ID
	 */
	public String getUserId() {
		return userId;
	}
		
	/**
	 * 设置：用户名称
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 获取：用户名称
	 */
	public String getUserName() {
		return userName;
	}
		
	/**
	 * 设置：审核ID
	 */
	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	/**
	 * 获取：审核ID
	 */
	public String getCheckId() {
		return checkId;
	}
		
	/**
	 * 设置：
	 */
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	/**
	 * 获取：
	 */
	public String getCheckDate() {
		return checkDate;
	}
		
	/**
	 * 设置：
	 */
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}

	/**
	 * 获取：
	 */
	public String getCheckTime() {
		return checkTime;
	}
		
	/**
	 * 设置：0待审 1同意 2驳回
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * 获取：0待审 1同意 2驳回
	 */
	public String getResult() {
		return result;
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
	public void setStepKey(String stepKey) {
		this.stepKey = stepKey;
	}

	/**
	 * 获取：
	 */
	public String getStepKey() {
		return stepKey;
	}
		
	/**
	 * 设置：
	 */
	public void setStepCode(String stepCode) {
		this.stepCode = stepCode;
	}

	/**
	 * 获取：
	 */
	public String getStepCode() {
		return stepCode;
	}
	
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}