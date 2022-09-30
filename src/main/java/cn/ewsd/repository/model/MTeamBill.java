package cn.ewsd.repository.model;

import cn.ewsd.common.model.MCoreBase;
import io.swagger.models.auth.In;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 
 * 
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
@Table(name="m_team_bill")
public class MTeamBill extends MCoreBase{
	private static final long serialVersionUID = 1L;
	private String billNo;
	private String storeNo;
	private Integer teamNo;
	private Integer deptNo;
	private String matNo;
	private BigDecimal occAmount;
	private BigDecimal matPrice;
	private String occDate;
	private String prjNo;
	private String drawEmp;
	private String useAddr;
	private String normNo;
	private String ifAssess;
	private String ifStock;
	private String remark;
	private String inStore;
	private String stepCode;
	private String modiEmp;
	private String modiDate;
	private String modiTime;
	private String dataSrc;
	private String accountType;
	private String matStatus;
	private BigDecimal bala;
	private String newOld;
	private String deptName;
	private String prjName;
	private String storeName;
	private String teamName;
	private String accountTypeName;
	private String matCode;
	private String matName;
	private String matUnit;
	private String erpType;
	private String typeName;
	private String itemNo;

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
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

	public String getErpType() {
		return erpType;
	}

	public void setErpType(String erpType) {
		this.erpType = erpType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getAccountTypeName() {
		return accountTypeName;
	}

	public void setAccountTypeName(String accountTypeName) {
		this.accountTypeName = accountTypeName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public BigDecimal getBala() {
		return bala;
	}

	public void setBala(BigDecimal bala) {
		this.bala = bala;
	}

	public String getNewOld() {
		return newOld;
	}

	public void setNewOld(String newOld) {
		this.newOld = newOld;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getPrjName() {
		return prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public Integer getTeamNo() {
		return teamNo;
	}

	public void setTeamNo(Integer teamNo) {
		this.teamNo = teamNo;
	}

	public Integer getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(Integer deptNo) {
		this.deptNo = deptNo;
	}

	public String getMatNo() {
		return matNo;
	}

	public void setMatNo(String matNo) {
		this.matNo = matNo;
	}

	public BigDecimal getOccAmount() {
		return occAmount;
	}

	public void setOccAmount(BigDecimal occAmount) {
		this.occAmount = occAmount;
	}

	public BigDecimal getMatPrice() {
		return matPrice;
	}

	public void setMatPrice(BigDecimal matPrice) {
		this.matPrice = matPrice;
	}

	public String getOccDate() {
		return occDate;
	}

	public void setOccDate(String occDate) {
		this.occDate = occDate;
	}

	public String getPrjNo() {
		return prjNo;
	}

	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
	}

	public String getDrawEmp() {
		return drawEmp;
	}

	public void setDrawEmp(String drawEmp) {
		this.drawEmp = drawEmp;
	}

	public String getUseAddr() {
		return useAddr;
	}

	public void setUseAddr(String useAddr) {
		this.useAddr = useAddr;
	}

	public String getNormNo() {
		return normNo;
	}

	public void setNormNo(String normNo) {
		this.normNo = normNo;
	}

	public String getIfAssess() {
		return ifAssess;
	}

	public void setIfAssess(String ifAssess) {
		this.ifAssess = ifAssess;
	}

	public String getIfStock() {
		return ifStock;
	}

	public void setIfStock(String ifStock) {
		this.ifStock = ifStock;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getInStore() {
		return inStore;
	}

	public void setInStore(String inStore) {
		this.inStore = inStore;
	}

	public String getStepCode() {
		return stepCode;
	}

	public void setStepCode(String stepCode) {
		this.stepCode = stepCode;
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

	public String getModiTime() {
		return modiTime;
	}

	public void setModiTime(String modiTime) {
		this.modiTime = modiTime;
	}

	public String getDataSrc() {
		return dataSrc;
	}

	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getMatStatus() {
		return matStatus;
	}

	public void setMatStatus(String matStatus) {
		this.matStatus = matStatus;
	}
}