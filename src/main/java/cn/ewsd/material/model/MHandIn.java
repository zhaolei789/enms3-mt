package cn.ewsd.material.model;

import cn.ewsd.common.model.MCoreBase;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 
 * 
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-21 16:55:52
 */
@Table(name="m_hand_in")
public class MHandIn extends MCoreBase{
	private static final long serialVersionUID = 1L;
	private String billNo;
	private String matNo;
	private Integer teamNo;
	private String storeNo;
	private BigDecimal matAmount;
	private BigDecimal oldRate;
	private String occDate;
	private String planGive;
	private String linkNo;
	private String remark;
	private String modiEmp;
	private String modiDate;
	private String checkStep;
	private String checkNo;
	private String pauseMonth;
	private String dataSrc;

	private String matCode;
	private String matName;
	private String matUnit;
	private BigDecimal matPrice;
	private String erpType;
	private String typeName;
	private String teamName;
	private BigDecimal backAmount;
	private String modiEmpName;
	private String storeName;
	private String lyDate;
	private BigDecimal lyAmount;
	private String jjDate;
	private BigDecimal jjAmount;
	private String deptName;
	private String empName;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getLyDate() {
		return lyDate;
	}

	public void setLyDate(String lyDate) {
		this.lyDate = lyDate;
	}

	public BigDecimal getLyAmount() {
		return lyAmount;
	}

	public void setLyAmount(BigDecimal lyAmount) {
		this.lyAmount = lyAmount;
	}

	public String getJjDate() {
		return jjDate;
	}

	public void setJjDate(String jjDate) {
		this.jjDate = jjDate;
	}

	public BigDecimal getJjAmount() {
		return jjAmount;
	}

	public void setJjAmount(BigDecimal jjAmount) {
		this.jjAmount = jjAmount;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getModiEmpName() {
		return modiEmpName;
	}

	public void setModiEmpName(String modiEmpName) {
		this.modiEmpName = modiEmpName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
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

	public BigDecimal getMatPrice() {
		return matPrice;
	}

	public void setMatPrice(BigDecimal matPrice) {
		this.matPrice = matPrice;
	}

	public String getErpType() {
		return erpType;
	}

	public void setErpType(String erpType) {
		this.erpType = erpType;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public BigDecimal getBackAmount() {
		return backAmount;
	}

	public void setBackAmount(BigDecimal backAmount) {
		this.backAmount = backAmount;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getMatNo() {
		return matNo;
	}

	public void setMatNo(String matNo) {
		this.matNo = matNo;
	}

	public Integer getTeamNo() {
		return teamNo;
	}

	public void setTeamNo(Integer teamNo) {
		this.teamNo = teamNo;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public BigDecimal getMatAmount() {
		return matAmount;
	}

	public void setMatAmount(BigDecimal matAmount) {
		this.matAmount = matAmount;
	}

	public BigDecimal getOldRate() {
		return oldRate;
	}

	public void setOldRate(BigDecimal oldRate) {
		this.oldRate = oldRate;
	}

	public String getOccDate() {
		return occDate;
	}

	public void setOccDate(String occDate) {
		this.occDate = occDate;
	}

	public String getPlanGive() {
		return planGive;
	}

	public void setPlanGive(String planGive) {
		this.planGive = planGive;
	}

	public String getLinkNo() {
		return linkNo;
	}

	public void setLinkNo(String linkNo) {
		this.linkNo = linkNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getModiEmp() {
		return modiEmp;
	}

	public void setModiEmp(String modiEmp) {
		this.modiEmp = modiEmp;
	}

	public String getModiDate() {
		return modiDate;
	}

	public void setModiDate(String modiDate) {
		this.modiDate = modiDate;
	}

	public String getCheckStep() {
		return checkStep;
	}

	public void setCheckStep(String checkStep) {
		this.checkStep = checkStep;
	}

	public String getCheckNo() {
		return checkNo;
	}

	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}

	public String getPauseMonth() {
		return pauseMonth;
	}

	public void setPauseMonth(String pauseMonth) {
		this.pauseMonth = pauseMonth;
	}

	public String getDataSrc() {
		return dataSrc;
	}

	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}
}