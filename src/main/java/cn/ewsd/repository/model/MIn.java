package cn.ewsd.repository.model;

import cn.ewsd.common.model.MCoreBase;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 
 * 
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
@Table(name="m_in")
public class MIn extends MCoreBase{
	private static final long serialVersionUID = 1L;
	private String billNo;
	private String inType;
	private String planSrc;
	private String planNo;
	private Integer teamNo;
	private Integer offerTeam;
	private String storeNo;
	private String reserveNo;
	private String matNo;
	private BigDecimal applyAmount;
	private String applyDate;
	private String applyEmp;
	private BigDecimal inAmount;
	private String inDate;
	private String inEmp;
	private BigDecimal billAmount;
	private BigDecimal setPrice;
	private String billDate;
	private String billEmp;
	private String offerNo;
	private String inStep;
	private String checkNo;
	private String remark;
	private String dataSrc;
	private BigDecimal price1;
	private BigDecimal price2;
	private BigDecimal price3;
	private String linkNo;
	private String normNo;
	private BigDecimal outAmount;
	private Integer outTeam;
	private String erpBill;
	private String ifAccount;
	private String checkAddr;
	private String checkType;
	private String checkInfo;
	private String checkRemark;
	private String reserve1;
	private String reserve2;
	private String reserve3;
	private String reserve4;
	private String reserve5;

	private String centerNo;
	private String centerName;
	private String storeName;
	private String applyEmpName;
	private BigDecimal bala;
	private String offerName;
	private String inStepName;
	private String inTypeName;
	private String erpType;
	private String typeName;
	private String matUnit;
	private String matCode;
	private String matName;
	private Integer count;
	private Integer qaPeriod;
	private String backBill;
	private String inName;
	private String supplier;
	private String checkEname;
	private String wbs;
	private BigDecimal matBala;
	private BigDecimal fixBala;
	private String offerTeamName;
	private String teamName;
	private String prjName;
	private String planSrcName;
	private String inEmpName;
	private String needDate;
	private String useAddr;
	private String reserve22;
	private BigDecimal amount;
	private BigDecimal price;
	private String status;
	private BigDecimal matPrice;

	public BigDecimal getMatPrice() {
		return matPrice;
	}

	public void setMatPrice(BigDecimal matPrice) {
		this.matPrice = matPrice;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReserve22() {
		return reserve22;
	}

	public void setReserve22(String reserve22) {
		this.reserve22 = reserve22;
	}

	public String getNeedDate() {
		return needDate;
	}

	public void setNeedDate(String needDate) {
		this.needDate = needDate;
	}

	public String getUseAddr() {
		return useAddr;
	}

	public void setUseAddr(String useAddr) {
		this.useAddr = useAddr;
	}

	public String getInEmpName() {
		return inEmpName;
	}

	public void setInEmpName(String inEmpName) {
		this.inEmpName = inEmpName;
	}

	public String getPlanSrcName() {
		return planSrcName;
	}

	public void setPlanSrcName(String planSrcName) {
		this.planSrcName = planSrcName;
	}

	public String getPrjName() {
		return prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getOfferTeamName() {
		return offerTeamName;
	}

	public void setOfferTeamName(String offerTeamName) {
		this.offerTeamName = offerTeamName;
	}

	public BigDecimal getMatBala() {
		return matBala;
	}

	public void setMatBala(BigDecimal matBala) {
		this.matBala = matBala;
	}

	public BigDecimal getFixBala() {
		return fixBala;
	}

	public void setFixBala(BigDecimal fixBala) {
		this.fixBala = fixBala;
	}

	public String getBackBill() {
		return backBill;
	}

	public void setBackBill(String backBill) {
		this.backBill = backBill;
	}

	public String getInName() {
		return inName;
	}

	public void setInName(String inName) {
		this.inName = inName;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getCheckEname() {
		return checkEname;
	}

	public void setCheckEname(String checkEname) {
		this.checkEname = checkEname;
	}

	public String getWbs() {
		return wbs;
	}

	public void setWbs(String wbs) {
		this.wbs = wbs;
	}

	public Integer getQaPeriod() {
		return qaPeriod;
	}

	public void setQaPeriod(Integer qaPeriod) {
		this.qaPeriod = qaPeriod;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
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

	public String getInTypeName() {
		return inTypeName;
	}

	public void setInTypeName(String inTypeName) {
		this.inTypeName = inTypeName;
	}

	public String getInStepName() {
		return inStepName;
	}

	public void setInStepName(String inStepName) {
		this.inStepName = inStepName;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public BigDecimal getBala() {
		return bala;
	}

	public void setBala(BigDecimal bala) {
		this.bala = bala;
	}

	public String getApplyEmpName() {
		return applyEmpName;
	}

	public void setApplyEmpName(String applyEmpName) {
		this.applyEmpName = applyEmpName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getCenterNo() {
		return centerNo;
	}

	public void setCenterNo(String centerNo) {
		this.centerNo = centerNo;
	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getInType() {
		return inType;
	}

	public void setInType(String inType) {
		this.inType = inType;
	}

	public String getPlanSrc() {
		return planSrc;
	}

	public void setPlanSrc(String planSrc) {
		this.planSrc = planSrc;
	}

	public String getPlanNo() {
		return planNo;
	}

	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}

	public Integer getTeamNo() {
		return teamNo;
	}

	public void setTeamNo(Integer teamNo) {
		this.teamNo = teamNo;
	}

	public Integer getOfferTeam() {
		return offerTeam;
	}

	public void setOfferTeam(Integer offerTeam) {
		this.offerTeam = offerTeam;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public String getReserveNo() {
		return reserveNo;
	}

	public void setReserveNo(String reserveNo) {
		this.reserveNo = reserveNo;
	}

	public String getMatNo() {
		return matNo;
	}

	public void setMatNo(String matNo) {
		this.matNo = matNo;
	}

	public BigDecimal getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getApplyEmp() {
		return applyEmp;
	}

	public void setApplyEmp(String applyEmp) {
		this.applyEmp = applyEmp;
	}

	public BigDecimal getInAmount() {
		return inAmount;
	}

	public void setInAmount(BigDecimal inAmount) {
		this.inAmount = inAmount;
	}

	public String getInDate() {
		return inDate;
	}

	public void setInDate(String inDate) {
		this.inDate = inDate;
	}

	public String getInEmp() {
		return inEmp;
	}

	public void setInEmp(String inEmp) {
		this.inEmp = inEmp;
	}

	public BigDecimal getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(BigDecimal billAmount) {
		this.billAmount = billAmount;
	}

	public BigDecimal getSetPrice() {
		return setPrice;
	}

	public void setSetPrice(BigDecimal setPrice) {
		this.setPrice = setPrice;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public String getBillEmp() {
		return billEmp;
	}

	public void setBillEmp(String billEmp) {
		this.billEmp = billEmp;
	}

	public String getOfferNo() {
		return offerNo;
	}

	public void setOfferNo(String offerNo) {
		this.offerNo = offerNo;
	}

	public String getInStep() {
		return inStep;
	}

	public void setInStep(String inStep) {
		this.inStep = inStep;
	}

	public String getCheckNo() {
		return checkNo;
	}

	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDataSrc() {
		return dataSrc;
	}

	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}

	public BigDecimal getPrice1() {
		return price1;
	}

	public void setPrice1(BigDecimal price1) {
		this.price1 = price1;
	}

	public BigDecimal getPrice2() {
		return price2;
	}

	public void setPrice2(BigDecimal price2) {
		this.price2 = price2;
	}

	public BigDecimal getPrice3() {
		return price3;
	}

	public void setPrice3(BigDecimal price3) {
		this.price3 = price3;
	}

	public String getLinkNo() {
		return linkNo;
	}

	public void setLinkNo(String linkNo) {
		this.linkNo = linkNo;
	}

	public String getNormNo() {
		return normNo;
	}

	public void setNormNo(String normNo) {
		this.normNo = normNo;
	}

	public BigDecimal getOutAmount() {
		return outAmount;
	}

	public void setOutAmount(BigDecimal outAmount) {
		this.outAmount = outAmount;
	}

	public Integer getOutTeam() {
		return outTeam;
	}

	public void setOutTeam(Integer outTeam) {
		this.outTeam = outTeam;
	}

	public String getErpBill() {
		return erpBill;
	}

	public void setErpBill(String erpBill) {
		this.erpBill = erpBill;
	}

	public String getIfAccount() {
		return ifAccount;
	}

	public void setIfAccount(String ifAccount) {
		this.ifAccount = ifAccount;
	}

	public String getCheckAddr() {
		return checkAddr;
	}

	public void setCheckAddr(String checkAddr) {
		this.checkAddr = checkAddr;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getCheckInfo() {
		return checkInfo;
	}

	public void setCheckInfo(String checkInfo) {
		this.checkInfo = checkInfo;
	}

	public String getCheckRemark() {
		return checkRemark;
	}

	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}

	public String getReserve1() {
		return reserve1;
	}

	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}

	public String getReserve2() {
		return reserve2;
	}

	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}

	public String getReserve3() {
		return reserve3;
	}

	public void setReserve3(String reserve3) {
		this.reserve3 = reserve3;
	}

	public String getReserve4() {
		return reserve4;
	}

	public void setReserve4(String reserve4) {
		this.reserve4 = reserve4;
	}

	public String getReserve5() {
		return reserve5;
	}

	public void setReserve5(String reserve5) {
		this.reserve5 = reserve5;
	}
}