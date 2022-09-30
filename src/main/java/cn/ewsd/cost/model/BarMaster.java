package cn.ewsd.cost.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import cn.ewsd.common.model.MCoreBase;
import javax.persistence.Table;

/**
 * 矿长
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-08-31 17:13:54
 */
@Table(name="bar_master")
public class BarMaster extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
										// 
	    private Integer isDel;
				// 职务名称
	    private String positionName;
				// 姓名
	    private String userName;
				// 编号
	    private String userNo;
				// 备注
	    private String note;

	    //查询参数
		private BigDecimal yearBudSum;

		private BigDecimal financeBalaSum;

		private BigDecimal monthBalaSum;

	private BigDecimal monthBudSum;

	private BigDecimal monthAssessSum;

	private BigDecimal diffBalaSum;

	private BigDecimal surplusBalaSum;

	private BigDecimal monthBalaSumAll;

	private BigDecimal financebalaSumAll;


	public BigDecimal getMonthBudSum() {
		return monthBudSum;
	}

	public void setMonthBudSum(BigDecimal monthBudSum) {
		this.monthBudSum = monthBudSum;
	}

	public BigDecimal getMonthAssessSum() {
		return monthAssessSum;
	}

	public void setMonthAssessSum(BigDecimal monthAssessSum) {
		this.monthAssessSum = monthAssessSum;
	}

	public BigDecimal getDiffBalaSum() {
		return diffBalaSum;
	}

	public void setDiffBalaSum(BigDecimal diffBalaSum) {
		this.diffBalaSum = diffBalaSum;
	}

	public BigDecimal getSurplusBalaSum() {
		return surplusBalaSum;
	}

	public void setSurplusBalaSum(BigDecimal surplusBalaSum) {
		this.surplusBalaSum = surplusBalaSum;
	}

	public BigDecimal getMonthBalaSumAll() {
		return monthBalaSumAll;
	}

	public void setMonthBalaSumAll(BigDecimal monthBalaSumAll) {
		this.monthBalaSumAll = monthBalaSumAll;
	}

	public BigDecimal getFinancebalaSumAll() {
		return financebalaSumAll;
	}

	public void setFinancebalaSumAll(BigDecimal financebalaSumAll) {
		this.financebalaSumAll = financebalaSumAll;
	}

	public BigDecimal getMonthBalaSum() {
		return monthBalaSum;
	}

	public void setMonthBalaSum(BigDecimal monthBalaSum) {
		this.monthBalaSum = monthBalaSum;
	}

	public BigDecimal getFinanceBalaSum() {
		return financeBalaSum;
	}

	public void setFinanceBalaSum(BigDecimal financeBalaSum) {
		this.financeBalaSum = financeBalaSum;
	}

	public BigDecimal getYearBudSum() {
		return yearBudSum;
	}

	public void setYearBudSum(BigDecimal yearBudSum) {
		this.yearBudSum = yearBudSum;
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
	 * 设置：职务名称
	 */
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	/**
	 * 获取：职务名称
	 */
	public String getPositionName() {
		return positionName;
	}
		
	/**
	 * 设置：姓名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 获取：姓名
	 */
	public String getUserName() {
		return userName;
	}
		
	/**
	 * 设置：编号
	 */
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	/**
	 * 获取：编号
	 */
	public String getUserNo() {
		return userNo;
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
	
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}