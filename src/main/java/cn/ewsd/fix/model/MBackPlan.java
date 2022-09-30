package cn.ewsd.fix.model;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import cn.ewsd.common.model.MCoreBase;
import javax.persistence.Table;

/**
 * 
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-05-12 14:32:59
 */
@Table(name="m_back_plan")
public class MBackPlan extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
	    private String planNo;
	    private String planMonth;
	    private Integer teamNo;
	    private String matNo;
	    private BigDecimal planAmount;
	    private BigDecimal matPrice;
	    private String planStep;
	    private String checkNo;
	    private BigDecimal backAmount;
	    private BigDecimal backNorm;
	    private BigDecimal backRatio;
	    private BigDecimal awardRatio;
	    private BigDecimal awardBala;
	    private String prjNo;
	    private String prjName;
	    private String billNo;
	    private String assMonth;
	    private Integer planTeam;
	    private String fixAddr;
	    private String endDate;
	    private String fixType;
	    private String planDate;
	    private String planParam;
	    private Integer acceptTeam;
	    private String acceptAddr;
	    private String remark;

	    private BigDecimal hsAmount;
	    private BigDecimal zzAmount;
	    private String planStepName;
	    private String matCode;
	    private String matName;
	    private String matUnit;
		private String teamName;
		private String planTeamName;
		private String fixTypeName;
		private BigDecimal amount;
		private BigDecimal matBala;
		private BigDecimal fixPrice;
		private BigDecimal applyAmount;

	public BigDecimal getFixPrice() {
		return fixPrice;
	}

	public void setFixPrice(BigDecimal fixPrice) {
		this.fixPrice = fixPrice;
	}

	public BigDecimal getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	public BigDecimal getMatBala() {
		return matBala;
	}

	public void setMatBala(BigDecimal matBala) {
		this.matBala = matBala;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getFixTypeName() {
		return fixTypeName;
	}

	public void setFixTypeName(String fixTypeName) {
		this.fixTypeName = fixTypeName;
	}

	public String getPlanTeamName() {
		return planTeamName;
	}

	public void setPlanTeamName(String planTeamName) {
		this.planTeamName = planTeamName;
	}

	public BigDecimal getHsAmount() {
		return hsAmount;
	}

	public void setHsAmount(BigDecimal hsAmount) {
		this.hsAmount = hsAmount;
	}

	public BigDecimal getZzAmount() {
		return zzAmount;
	}

	public void setZzAmount(BigDecimal zzAmount) {
		this.zzAmount = zzAmount;
	}

	public String getPlanStepName() {
		return planStepName;
	}

	public void setPlanStepName(String planStepName) {
		this.planStepName = planStepName;
	}

	public String getMatCode() {
		return matCode;
	}

	public void setMatCode(String matCode) {
		this.matCode = matCode;
	}

	public String getMatName() {
		return matName;
	}

	public void setMatName(String matName) {
		this.matName = matName;
	}

	public String getMatUnit() {
		return matUnit;
	}

	public void setMatUnit(String matUnit) {
		this.matUnit = matUnit;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}

	/**
	 * 获取：计划编号
	 */
	public String getPlanNo() {
		return planNo;
	}
		
	/**
	 * 设置：计划月份
	 */
	public void setPlanMonth(String planMonth) {
		this.planMonth = planMonth;
	}

	/**
	 * 获取：计划月份
	 */
	public String getPlanMonth() {
		return planMonth;
	}
		
	/**
	 * 设置：回收部门
	 */
	public void setTeamNo(Integer teamNo) {
		this.teamNo = teamNo;
	}

	/**
	 * 获取：回收部门
	 */
	public Integer getTeamNo() {
		return teamNo;
	}
		
	/**
	 * 设置：材料内码
	 */
	public void setMatNo(String matNo) {
		this.matNo = matNo;
	}

	/**
	 * 获取：材料内码
	 */
	public String getMatNo() {
		return matNo;
	}
		
	/**
	 * 设置：计划数量
	 */
	public void setPlanAmount(BigDecimal planAmount) {
		this.planAmount = planAmount;
	}

	/**
	 * 获取：计划数量
	 */
	public BigDecimal getPlanAmount() {
		return planAmount;
	}
		
	/**
	 * 设置：材料价格
	 */
	public void setMatPrice(BigDecimal matPrice) {
		this.matPrice = matPrice;
	}

	/**
	 * 获取：材料价格
	 */
	public BigDecimal getMatPrice() {
		return matPrice;
	}
		
	/**
	 * 设置：计划步骤
	 */
	public void setPlanStep(String planStep) {
		this.planStep = planStep;
	}

	/**
	 * 获取：计划步骤
	 */
	public String getPlanStep() {
		return planStep;
	}
		
	/**
	 * 设置：计划日志
	 */
	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}

	/**
	 * 获取：计划日志
	 */
	public String getCheckNo() {
		return checkNo;
	}
		
	/**
	 * 设置：回收数量
	 */
	public void setBackAmount(BigDecimal backAmount) {
		this.backAmount = backAmount;
	}

	/**
	 * 获取：回收数量
	 */
	public BigDecimal getBackAmount() {
		return backAmount;
	}
		
	/**
	 * 设置：回收标准
	 */
	public void setBackNorm(BigDecimal backNorm) {
		this.backNorm = backNorm;
	}

	/**
	 * 获取：回收标准
	 */
	public BigDecimal getBackNorm() {
		return backNorm;
	}
		
	/**
	 * 设置：回收率
	 */
	public void setBackRatio(BigDecimal backRatio) {
		this.backRatio = backRatio;
	}

	/**
	 * 获取：回收率
	 */
	public BigDecimal getBackRatio() {
		return backRatio;
	}
		
	/**
	 * 设置：奖罚系数
	 */
	public void setAwardRatio(BigDecimal awardRatio) {
		this.awardRatio = awardRatio;
	}

	/**
	 * 获取：奖罚系数
	 */
	public BigDecimal getAwardRatio() {
		return awardRatio;
	}
		
	/**
	 * 设置：奖罚金额
	 */
	public void setAwardBala(BigDecimal awardBala) {
		this.awardBala = awardBala;
	}

	/**
	 * 获取：奖罚金额
	 */
	public BigDecimal getAwardBala() {
		return awardBala;
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
	 * 设置：回撤工程
	 */
	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	/**
	 * 获取：回撤工程
	 */
	public String getPrjName() {
		return prjName;
	}
		
	/**
	 * 设置：发放记录
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	/**
	 * 获取：发放记录
	 */
	public String getBillNo() {
		return billNo;
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
	 * 设置：编制单位
	 */
	public void setPlanTeam(Integer planTeam) {
		this.planTeam = planTeam;
	}

	/**
	 * 获取：编制单位
	 */
	public Integer getPlanTeam() {
		return planTeam;
	}
		
	/**
	 * 设置：修复地点
	 */
	public void setFixAddr(String fixAddr) {
		this.fixAddr = fixAddr;
	}

	/**
	 * 获取：修复地点
	 */
	public String getFixAddr() {
		return fixAddr;
	}
		
	/**
	 * 设置：完成日期
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * 获取：完成日期
	 */
	public String getEndDate() {
		return endDate;
	}
		
	/**
	 * 设置：修复类型
	 */
	public void setFixType(String fixType) {
		this.fixType = fixType;
	}

	/**
	 * 获取：修复类型
	 */
	public String getFixType() {
		return fixType;
	}
		
	/**
	 * 设置：编制日期
	 */
	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}

	/**
	 * 获取：编制日期
	 */
	public String getPlanDate() {
		return planDate;
	}
		
	/**
	 * 设置：计划参数
	 */
	public void setPlanParam(String planParam) {
		this.planParam = planParam;
	}

	/**
	 * 获取：计划参数
	 */
	public String getPlanParam() {
		return planParam;
	}
		
	/**
	 * 设置：接收单位
	 */
	public void setAcceptTeam(Integer acceptTeam) {
		this.acceptTeam = acceptTeam;
	}

	/**
	 * 获取：接收单位
	 */
	public Integer getAcceptTeam() {
		return acceptTeam;
	}
		
	/**
	 * 设置：接收地点
	 */
	public void setAcceptAddr(String acceptAddr) {
		this.acceptAddr = acceptAddr;
	}

	/**
	 * 获取：接收地点
	 */
	public String getAcceptAddr() {
		return acceptAddr;
	}
		
	/**
	 * 设置：备注信息
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取：备注信息
	 */
	public String getRemark() {
		return remark;
	}
	
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}