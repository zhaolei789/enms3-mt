package cn.ewsd.cost.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import cn.ewsd.common.model.MCoreBase;
import javax.persistence.Table;

/**
 * 奖罚考核
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-08-26 18:03:27
 */
@Table(name="f_award")
public class FAward extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
		// 标识ID
	    private Long awardId;
				// 责任部门
	    private String teamNo;
				// 考核月份
	    private String assMonth;
				// 奖惩原因
	    private String reason;
				// 发生日期
	    private String occDate;
				// 通知单号
	    private String noticeNo;
				// 考核类别
	    private String assessType;
				// 项目ID
	    private Long itemId;
				// 罚款金额
	    private BigDecimal awardBala;
				// 考核单位
	    private String assessTeam;
				// 签发人
	    private String signEmp;
				// 考核分类
	    private String assessCate;
				// 考核依据
	    private String standard;
				// 上报日期
	    private String modiDate;
				// 上报用户
	    private String modiUser;
				// 上报用户名
	    private String modiUserName;
				// 应分配日期
	    private String assignDate;
				// 分配用户
	    private String assignUser;
				// 分配用户名
	    private String assignUserName;
				// 状态
	    private String status;
				// 日志编号
	    private String checkNo;
				// 是否删除
	    private int isDel;
				// 是否执行
	    private int isExecute;
				// 责任部门分类
	    private String deptType;
				// 删除步骤
	    private String delStatus;
				// 删除原因
	    private String delReason;
				// 调整步骤
	    private String updStatus;
				// 调整原因
	    private String updReason;
				// 原始金额
	    private BigDecimal oldBala;
				// 上报部门
	    private String modiTeam;
				// 原始日期
	    private String oldDate;
				// 原始部门
	    private String oldTeam;

	    //查询参数
		private String teamNoName;

		private String assessTeamName;

		private String auditStepName;

		private String modiTeamName;

		private BigDecimal assignSum;

		private Integer assignCount;
		//已分配金额
		private BigDecimal yfBala;

	public BigDecimal getYfBala() {
		return yfBala;
	}

	public void setYfBala(BigDecimal yfBala) {
		this.yfBala = yfBala;
	}

	public String getModiTeamName() {
		return modiTeamName;
	}

	public void setModiTeamName(String modiTeamName) {
		this.modiTeamName = modiTeamName;
	}

	public BigDecimal getAssignSum() {
		return assignSum;
	}

	public void setAssignSum(BigDecimal assignSum) {
		this.assignSum = assignSum;
	}

	public Integer getAssignCount() {
		return assignCount;
	}

	public void setAssignCount(Integer assignCount) {
		this.assignCount = assignCount;
	}

	public String getAuditStepName() {
		return auditStepName;
	}

	public void setAuditStepName(String auditStepName) {
		this.auditStepName = auditStepName;
	}

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

	/**
	 * 设置：标识ID
	 */
	public void setAwardId(Long awardId) {
		this.awardId = awardId;
	}

	/**
	 * 获取：标识ID
	 */
	public Long getAwardId() {
		return awardId;
	}
		
	/**
	 * 设置：责任部门
	 */
	public void setTeamNo(String teamNo) {
		this.teamNo = teamNo;
	}

	/**
	 * 获取：责任部门
	 */
	public String getTeamNo() {
		return teamNo;
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
	 * 设置：奖惩原因
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * 获取：奖惩原因
	 */
	public String getReason() {
		return reason;
	}
		
	/**
	 * 设置：发生日期
	 */
	public void setOccDate(String occDate) {
		this.occDate = occDate;
	}

	/**
	 * 获取：发生日期
	 */
	public String getOccDate() {
		return occDate;
	}
		
	/**
	 * 设置：通知单号
	 */
	public void setNoticeNo(String noticeNo) {
		this.noticeNo = noticeNo;
	}

	/**
	 * 获取：通知单号
	 */
	public String getNoticeNo() {
		return noticeNo;
	}
		
	/**
	 * 设置：考核类别
	 */
	public void setAssessType(String assessType) {
		this.assessType = assessType;
	}

	/**
	 * 获取：考核类别
	 */
	public String getAssessType() {
		return assessType;
	}
		
	/**
	 * 设置：项目ID
	 */
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	/**
	 * 获取：项目ID
	 */
	public Long getItemId() {
		return itemId;
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
	 * 设置：考核单位
	 */
	public void setAssessTeam(String assessTeam) {
		this.assessTeam = assessTeam;
	}

	/**
	 * 获取：考核单位
	 */
	public String getAssessTeam() {
		return assessTeam;
	}
		
	/**
	 * 设置：签发人
	 */
	public void setSignEmp(String signEmp) {
		this.signEmp = signEmp;
	}

	/**
	 * 获取：签发人
	 */
	public String getSignEmp() {
		return signEmp;
	}
		
	/**
	 * 设置：考核分类
	 */
	public void setAssessCate(String assessCate) {
		this.assessCate = assessCate;
	}

	/**
	 * 获取：考核分类
	 */
	public String getAssessCate() {
		return assessCate;
	}
		
	/**
	 * 设置：考核依据
	 */
	public void setStandard(String standard) {
		this.standard = standard;
	}

	/**
	 * 获取：考核依据
	 */
	public String getStandard() {
		return standard;
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
	 * 设置：上报用户
	 */
	public void setModiUser(String modiUser) {
		this.modiUser = modiUser;
	}

	/**
	 * 获取：上报用户
	 */
	public String getModiUser() {
		return modiUser;
	}
		
	/**
	 * 设置：上报用户名
	 */
	public void setModiUserName(String modiUserName) {
		this.modiUserName = modiUserName;
	}

	/**
	 * 获取：上报用户名
	 */
	public String getModiUserName() {
		return modiUserName;
	}
		
	/**
	 * 设置：应分配日期
	 */
	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}

	/**
	 * 获取：应分配日期
	 */
	public String getAssignDate() {
		return assignDate;
	}
		
	/**
	 * 设置：分配用户
	 */
	public void setAssignUser(String assignUser) {
		this.assignUser = assignUser;
	}

	/**
	 * 获取：分配用户
	 */
	public String getAssignUser() {
		return assignUser;
	}
		
	/**
	 * 设置：分配用户名
	 */
	public void setAssignUserName(String assignUserName) {
		this.assignUserName = assignUserName;
	}

	/**
	 * 获取：分配用户名
	 */
	public String getAssignUserName() {
		return assignUserName;
	}
		
	/**
	 * 设置：状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 获取：状态
	 */
	public String getStatus() {
		return status;
	}
		
	/**
	 * 设置：日志编号
	 */
	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}

	/**
	 * 获取：日志编号
	 */
	public String getCheckNo() {
		return checkNo;
	}
		
	/**
	 * 设置：是否删除
	 */
	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}

	/**
	 * 获取：是否删除
	 */
	public int getIsDel() {
		return isDel;
	}
		
	/**
	 * 设置：是否执行
	 */
	public void setIsExecute(int isExecute) {
		this.isExecute = isExecute;
	}

	/**
	 * 获取：是否执行
	 */
	public int getIsExecute() {
		return isExecute;
	}
		
	/**
	 * 设置：责任部门分类
	 */
	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

	/**
	 * 获取：责任部门分类
	 */
	public String getDeptType() {
		return deptType;
	}
		
	/**
	 * 设置：删除步骤
	 */
	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}

	/**
	 * 获取：删除步骤
	 */
	public String getDelStatus() {
		return delStatus;
	}
		
	/**
	 * 设置：删除原因
	 */
	public void setDelReason(String delReason) {
		this.delReason = delReason;
	}

	/**
	 * 获取：删除原因
	 */
	public String getDelReason() {
		return delReason;
	}
		
	/**
	 * 设置：调整步骤
	 */
	public void setUpdStatus(String updStatus) {
		this.updStatus = updStatus;
	}

	/**
	 * 获取：调整步骤
	 */
	public String getUpdStatus() {
		return updStatus;
	}
		
	/**
	 * 设置：调整原因
	 */
	public void setUpdReason(String updReason) {
		this.updReason = updReason;
	}

	/**
	 * 获取：调整原因
	 */
	public String getUpdReason() {
		return updReason;
	}
		
	/**
	 * 设置：原始金额
	 */
	public void setOldBala(BigDecimal oldBala) {
		this.oldBala = oldBala;
	}

	/**
	 * 获取：原始金额
	 */
	public BigDecimal getOldBala() {
		return oldBala;
	}
		
	/**
	 * 设置：上报部门
	 */
	public void setModiTeam(String modiTeam) {
		this.modiTeam = modiTeam;
	}

	/**
	 * 获取：上报部门
	 */
	public String getModiTeam() {
		return modiTeam;
	}
		
	/**
	 * 设置：原始日期
	 */
	public void setOldDate(String oldDate) {
		this.oldDate = oldDate;
	}

	/**
	 * 获取：原始日期
	 */
	public String getOldDate() {
		return oldDate;
	}
		
	/**
	 * 设置：原始部门
	 */
	public void setOldTeam(String oldTeam) {
		this.oldTeam = oldTeam;
	}

	/**
	 * 获取：原始部门
	 */
	public String getOldTeam() {
		return oldTeam;
	}
									
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}