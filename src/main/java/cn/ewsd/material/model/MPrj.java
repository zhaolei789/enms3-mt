package cn.ewsd.material.model;

import cn.ewsd.common.model.MCoreBase;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 
 * 
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
@Table(name="m_prj")
public class MPrj extends MCoreBase{
	private static final long serialVersionUID = 1L;
	private String prjNo;
	private String prjName;
	private String prjStatus;
	private String prjStatusName;
	private String upName;
	private String prjType1;
	private String prjType2;
	private String prjType2Name;
	private String prodType;
	private String planPeriod;
	private String planPeriodName;
	private Integer teamNo;
	private String teamName;
	private String prjAddr;
	private String beginDate;
	private String endDate;
	private String prjInfo;
	private String accessory;
	private int checkTeam;
	private String budFinish;
	private BigDecimal budBala;
	private String teamMng;
	private String optEmp;
	private String inDate;
	private BigDecimal matBala;

	public String getPrjStatusName() {
		return prjStatusName;
	}

	public void setPrjStatusName(String prjStatusName) {
		this.prjStatusName = prjStatusName;
	}

	public String getPlanPeriodName() {
		return planPeriodName;
	}

	public void setPlanPeriodName(String planPeriodName) {
		this.planPeriodName = planPeriodName;
	}

	public String getPrjType2Name() {
		return prjType2Name;
	}

	public void setPrjType2Name(String prjType2Name) {
		this.prjType2Name = prjType2Name;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public BigDecimal getMatBala() {
		return matBala;
	}

	public void setMatBala(BigDecimal matBala) {
		this.matBala = matBala;
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

	public String getPrjStatus() {
		return prjStatus;
	}

	public void setPrjStatus(String prjStatus) {
		this.prjStatus = prjStatus;
	}

	public String getUpName() {
		return upName;
	}

	public void setUpName(String upName) {
		this.upName = upName;
	}

	public String getPrjType1() {
		return prjType1;
	}

	public void setPrjType1(String prjType1) {
		this.prjType1 = prjType1;
	}

	public String getPrjType2() {
		return prjType2;
	}

	public void setPrjType2(String prjType2) {
		this.prjType2 = prjType2;
	}

	public String getProdType() {
		return prodType;
	}

	public void setProdType(String prodType) {
		this.prodType = prodType;
	}

	public String getPlanPeriod() {
		return planPeriod;
	}

	public void setPlanPeriod(String planPeriod) {
		this.planPeriod = planPeriod;
	}

	public Integer getTeamNo() {
		return teamNo;
	}

	public void setTeamNo(Integer teamNo) {
		this.teamNo = teamNo;
	}

	public String getPrjAddr() {
		return prjAddr;
	}

	public void setPrjAddr(String prjAddr) {
		this.prjAddr = prjAddr;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPrjInfo() {
		return prjInfo;
	}

	public void setPrjInfo(String prjInfo) {
		this.prjInfo = prjInfo;
	}

	public String getAccessory() {
		return accessory;
	}

	public void setAccessory(String accessory) {
		this.accessory = accessory;
	}

	public int getCheckTeam() {
		return checkTeam;
	}

	public void setCheckTeam(int checkTeam) {
		this.checkTeam = checkTeam;
	}

	public String getBudFinish() {
		return budFinish;
	}

	public void setBudFinish(String budFinish) {
		this.budFinish = budFinish;
	}

	public BigDecimal getBudBala() {
		return budBala;
	}

	public void setBudBala(BigDecimal budBala) {
		this.budBala = budBala;
	}

	public String getTeamMng() {
		return teamMng;
	}

	public void setTeamMng(String teamMng) {
		this.teamMng = teamMng;
	}

	public String getOptEmp() {
		return optEmp;
	}

	public void setOptEmp(String optEmp) {
		this.optEmp = optEmp;
	}

	public String getInDate() {
		return inDate;
	}

	public void setInDate(String inDate) {
		this.inDate = inDate;
	}
}