package cn.ewsd.cost.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import cn.ewsd.common.model.MCoreBase;
import javax.persistence.Table;

/**
 * 分管费用
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-07 09:22:24
 */
@Table(name="f_charge_fee")
public class FChargeFee extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
		// 费用ID
	    private Long feeId;
				// 领导ID
	    private String leader;
				// 部门编号
	    private String teamNo;
				// 是否科室;是科室，则自动汇总成部门汇总表
	    private Integer ifOffice;
				// 科目ID
	    private String itemId;
				// 科目名称
	    private String itemName;
				// 测算依据;1周期  2商品煤  3原煤  4进尺
	    private String calcWay;
				// 年度指标
	    private BigDecimal yearBud;
				// 单价
	    private BigDecimal normPrice;
				// 财务月份;年预算、月发生
	    private String fMonth;
				// 上报部门
	    private String reportTeam;
				// 月度定额
	    private BigDecimal monthBud;
				// 上期结余
	    private BigDecimal lastBala;
				// 月度费用
	    private BigDecimal monthBala;
				// 财务统计额
	    private BigDecimal financeBala;
				// 月度考核;根据科目的测算方式，非时间的均按月考核；季度，半年，年度根据周期来；
	    private BigDecimal monthAssess;
				// 节超金额
	    private BigDecimal diffBala;
				// 节超比例
	    private BigDecimal diffScale;
				// 累计金额
	    private BigDecimal sumBala;
				// 剩余金额
	    private BigDecimal surplusBala;
				// 备注
	    private String remark;

	    private BigDecimal sumFinc;

	    private String itemType;

	    //查询参数

	    private String assPeriod;

	    private String teamNoName;

	    private String reportTeamName;

	    private BigDecimal assessBala;
	    private BigDecimal bala;
	    private String leaderName;
	    private String yearBudText;
	    private String monthBalaText;
	    private String assessBalaText;
	    private String diffBalaText;
	    private String diffScaleText;
	    private String sumOccText;
	    private String leftBalaText;
	    private String leftScaleText;
	    private String teamName;
	    private String calcWayName;
	    private String sumFinance;
	    private String financeBalaText;
	    private String monthBudText;
	    private String monthAssessText;
	    private String surplusBalaText;
		private String sumBalaText;


	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getSumBalaText() {
		return sumBalaText;
	}

	public void setSumBalaText(String sumBalaText) {
		this.sumBalaText = sumBalaText;
	}

	public String getSurplusBalaText() {
		return surplusBalaText;
	}

	public void setSurplusBalaText(String surplusBalaText) {this.surplusBalaText = surplusBalaText;
	}

	public String getMonthAssessText() {
		return monthAssessText;
	}

	public void setMonthAssessText(String monthAssessText) {
		this.monthAssessText = monthAssessText;
	}

	public String getMonthBudText() {
		return monthBudText;
	}

	public void setMonthBudText(String monthBudText) {
		this.monthBudText = monthBudText;
	}

	public BigDecimal getSumFinc() {
		return sumFinc;
	}

	public void setSumFinc(BigDecimal sumFinc) {
		this.sumFinc = sumFinc;
	}

	public String getFinanceBalaText() {
		return financeBalaText;
	}

	public void setFinanceBalaText(String financeBalaText) {
		this.financeBalaText = financeBalaText;
	}

	public String getSumFinance() {
		return sumFinance;
	}

	public void setSumFinance(String sumFinance) {
		this.sumFinance = sumFinance;
	}

	public String getCalcWayName() {
		return calcWayName;
	}

	public void setCalcWayName(String calcWayName) {
		this.calcWayName = calcWayName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getDiffScaleText() {
		return diffScaleText;
	}

	public void setDiffScaleText(String diffScaleText) {
		this.diffScaleText = diffScaleText;
	}

	public String getLeftScaleText() {
		return leftScaleText;
	}

	public void setLeftScaleText(String leftScaleText) {
		this.leftScaleText = leftScaleText;
	}

	public String getMonthBalaText() {
		return monthBalaText;
	}

	public void setMonthBalaText(String monthBalaText) {
		this.monthBalaText = monthBalaText;
	}

	public String getAssessBalaText() {
		return assessBalaText;
	}

	public void setAssessBalaText(String assessBalaText) {
		this.assessBalaText = assessBalaText;
	}

	public String getDiffBalaText() {
		return diffBalaText;
	}

	public void setDiffBalaText(String diffBalaText) {
		this.diffBalaText = diffBalaText;
	}

	public String getSumOccText() {
		return sumOccText;
	}

	public void setSumOccText(String sumOccText) {
		this.sumOccText = sumOccText;
	}

	public String getLeftBalaText() {
		return leftBalaText;
	}

	public void setLeftBalaText(String leftBalaText) {
		this.leftBalaText = leftBalaText;
	}

	public String getYearBudText() {
		return yearBudText;
	}

	public void setYearBudText(String yearBudText) {
		this.yearBudText = yearBudText;
	}

	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	public BigDecimal getAssessBala() {
		return assessBala;
	}

	public void setAssessBala(BigDecimal assessBala) {
		this.assessBala = assessBala;
	}

	public BigDecimal getBala() {
		return bala;
	}

	public void setBala(BigDecimal bala) {
		this.bala = bala;
	}

	public String getTeamNoName() {
		return teamNoName;
	}

	public void setTeamNoName(String teamNoName) {
		this.teamNoName = teamNoName;
	}

	public String getReportTeamName() {
		return reportTeamName;
	}

	public void setReportTeamName(String reportTeamName) {
		this.reportTeamName = reportTeamName;
	}

	public String getfMonth() {
		return fMonth;
	}

	public void setfMonth(String fMonth) {
		this.fMonth = fMonth;
	}

	public String getAssPeriod() {
		return assPeriod;
	}

	public void setAssPeriod(String assPeriod) {
		this.assPeriod = assPeriod;
	}

	/**
	 * 设置：费用ID
	 */
	public void setFeeId(Long feeId) {
		this.feeId = feeId;
	}

	/**
	 * 获取：费用ID
	 */
	public Long getFeeId() {
		return feeId;
	}
		
	/**
	 * 设置：领导ID
	 */
	public void setLeader(String leader) {
		this.leader = leader;
	}

	/**
	 * 获取：领导ID
	 */
	public String getLeader() {
		return leader;
	}
		
	/**
	 * 设置：部门编号
	 */
	public void setTeamNo(String teamNo) {
		this.teamNo = teamNo;
	}

	/**
	 * 获取：部门编号
	 */
	public String getTeamNo() {
		return teamNo;
	}
		
	/**
	 * 设置：是否科室;是科室，则自动汇总成部门汇总表
	 */
	public void setIfOffice(Integer ifOffice) {
		this.ifOffice = ifOffice;
	}

	/**
	 * 获取：是否科室;是科室，则自动汇总成部门汇总表
	 */
	public Integer getIfOffice() {
		return ifOffice;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/**
	 * 设置：科目名称
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * 获取：科目名称
	 */
	public String getItemName() {
		return itemName;
	}
		
	/**
	 * 设置：测算依据;1周期  2商品煤  3原煤  4进尺
	 */
	public void setCalcWay(String calcWay) {
		this.calcWay = calcWay;
	}

	/**
	 * 获取：测算依据;1周期  2商品煤  3原煤  4进尺
	 */
	public String getCalcWay() {
		return calcWay;
	}
		
	/**
	 * 设置：年度指标
	 */
	public void setYearBud(BigDecimal yearBud) {
		this.yearBud = yearBud;
	}

	/**
	 * 获取：年度指标
	 */
	public BigDecimal getYearBud() {
		return yearBud;
	}
		
	/**
	 * 设置：单价
	 */
	public void setNormPrice(BigDecimal normPrice) {
		this.normPrice = normPrice;
	}

	/**
	 * 获取：单价
	 */
	public BigDecimal getNormPrice() {
		return normPrice;
	}

	/**
	 * 设置：上报部门
	 */
	public void setReportTeam(String reportTeam) {
		this.reportTeam = reportTeam;
	}

	/**
	 * 获取：上报部门
	 */
	public String getReportTeam() {
		return reportTeam;
	}
		
	/**
	 * 设置：月度定额
	 */
	public void setMonthBud(BigDecimal monthBud) {
		this.monthBud = monthBud;
	}

	/**
	 * 获取：月度定额
	 */
	public BigDecimal getMonthBud() {
		return monthBud;
	}
		
	/**
	 * 设置：上期结余
	 */
	public void setLastBala(BigDecimal lastBala) {
		this.lastBala = lastBala;
	}

	/**
	 * 获取：上期结余
	 */
	public BigDecimal getLastBala() {
		return lastBala;
	}
		
	/**
	 * 设置：月度费用
	 */
	public void setMonthBala(BigDecimal monthBala) {
		this.monthBala = monthBala;
	}

	/**
	 * 获取：月度费用
	 */
	public BigDecimal getMonthBala() {
		return monthBala;
	}
		
	/**
	 * 设置：财务统计额
	 */
	public void setFinanceBala(BigDecimal financeBala) {
		this.financeBala = financeBala;
	}

	/**
	 * 获取：财务统计额
	 */
	public BigDecimal getFinanceBala() {
		return financeBala;
	}
		
	/**
	 * 设置：月度考核;根据科目的测算方式，非时间的均按月考核；季度，半年，年度根据周期来；
	 */
	public void setMonthAssess(BigDecimal monthAssess) {
		this.monthAssess = monthAssess;
	}

	/**
	 * 获取：月度考核;根据科目的测算方式，非时间的均按月考核；季度，半年，年度根据周期来；
	 */
	public BigDecimal getMonthAssess() {
		return monthAssess;
	}
		
	/**
	 * 设置：节超金额
	 */
	public void setDiffBala(BigDecimal diffBala) {
		this.diffBala = diffBala;
	}

	/**
	 * 获取：节超金额
	 */
	public BigDecimal getDiffBala() {
		return diffBala;
	}
		
	/**
	 * 设置：节超比例
	 */
	public void setDiffScale(BigDecimal diffScale) {
		this.diffScale = diffScale;
	}

	/**
	 * 获取：节超比例
	 */
	public BigDecimal getDiffScale() {
		return diffScale;
	}
		
	/**
	 * 设置：累计金额
	 */
	public void setSumBala(BigDecimal sumBala) {
		this.sumBala = sumBala;
	}

	/**
	 * 获取：累计金额
	 */
	public BigDecimal getSumBala() {
		return sumBala;
	}
		
	/**
	 * 设置：剩余金额
	 */
	public void setSurplusBala(BigDecimal surplusBala) {
		this.surplusBala = surplusBala;
	}

	/**
	 * 获取：剩余金额
	 */
	public BigDecimal getSurplusBala() {
		return surplusBala;
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
									
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}