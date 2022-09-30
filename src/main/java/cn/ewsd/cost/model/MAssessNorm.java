package cn.ewsd.cost.model;

import cn.ewsd.common.model.MCoreBase;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 工程结算
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-03 08:39:36
 */
@Table(name="m_assess_norm")
public class MAssessNorm extends MCoreBase{
	private static final long serialVersionUID = 1L;
	private String normId;
	private String beginMonth;
	private String itemNo;
	private String teamNo;
	private String prjNo;
	private String prjName;
	private BigDecimal normAmount;
	private BigDecimal normPrice;
	private String assTeam;
	private String modiDate;
	private String modiEmp;
	private String endDate;

	private String teamName;
	private String assTeamName;
	private String itemName;

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getAssTeamName() {
		return assTeamName;
	}

	public void setAssTeamName(String assTeamName) {
		this.assTeamName = assTeamName;
	}

	public String getNormId() {
		return normId;
	}

	public void setNormId(String normId) {
		this.normId = normId;
	}

	public String getBeginMonth() {
		return beginMonth;
	}

	public void setBeginMonth(String beginMonth) {
		this.beginMonth = beginMonth;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getTeamNo() {
		return teamNo;
	}

	public void setTeamNo(String teamNo) {
		this.teamNo = teamNo;
	}

	public String getPrjNo() {
		return prjNo;
	}

	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
	}

	public String getPrjName() {
		return prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	public BigDecimal getNormAmount() {
		return normAmount;
	}

	public void setNormAmount(BigDecimal normAmount) {
		this.normAmount = normAmount;
	}

	public BigDecimal getNormPrice() {
		return normPrice;
	}

	public void setNormPrice(BigDecimal normPrice) {
		this.normPrice = normPrice;
	}

	public String getAssTeam() {
		return assTeam;
	}

	public void setAssTeam(String assTeam) {
		this.assTeam = assTeam;
	}

	public String getModiDate() {
		return modiDate;
	}

	public void setModiDate(String modiDate) {
		this.modiDate = modiDate;
	}

	public String getModiEmp() {
		return modiEmp;
	}

	public void setModiEmp(String modiEmp) {
		this.modiEmp = modiEmp;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}