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
@Table(name="m_plan")
public class 	MPlan extends MCoreBase{
	private static final long serialVersionUID = 1L;
	private String planNo;
	private String prjNo;
	private Integer teamNo;
	private String teamName;
	private Integer payTeam;
	private String planMonth;
	private String planType;
	private String itemNo;
	private String itemName;
	private String matNo;
	private String matCode;
	private String matName;
	private String matUnit;
	private String matUnitName;
	private String abcType;
	private BigDecimal matPrice;
	private BigDecimal matAmount;
	private BigDecimal matBala;
	private BigDecimal usableAmount;
	private String useDate;
	private String remark;
	private String planDate;
	private String planTime;
	private String planStep;
	private String planStepName;
	private String checkNo;
	private String orderDir;
	private String endMonth;
	private String matAddr;
	private String factoryNo;
	private String costCenter;
	private String traxNo;
	private String wbsElement;
	private String moveType;
	private String moveTypeName;
	private String listNo;
	private String itemType;
	private String purGroup;
	private String trackingNo;
	private String reserveNo;
	private String purchaseNo;
	private String ifMake;
	private String planSrc;
	private String planSrcName;
	private String matUse;
	private String needComp;
	private String needFactory;
	private String purOrg;
	private String pmNumber;
	private String ifUrgent;
	private String purchaseList;
	private String offerNo;
	private BigDecimal srmPrice;
	private String erpType;
	private String typeName;
	private String realFlag;
	private BigDecimal sumAmount;
	private String direct;
	private String centerName;
	private Integer backCount;
	private String planAbc;
	private String matAbc;
	private String sbType;
	private BigDecimal oldAmount;
	private Integer count;
	private Integer znksTeam;
	private String znksTeamName;
	private String prjName;
	private String offerDeptName;
	private String factoryName;
	private String oldCenter;
	private BigDecimal ztAmount;
	private BigDecimal factAmount;
	private String accountType;
	private String accountTypeName;
	private String store;
	private String storeName;
	private String prjType1;
	private BigDecimal mMatPrice;
	private String matType;
	private String matAddrName;
	private BigDecimal purAmount;
	private BigDecimal lkqAmount;
	private BigDecimal lkhAmount;
	private String planTypeName;
	private String occYear;

	public String getOccYear() {
		return occYear;
	}

	public void setOccYear(String occYear) {
		this.occYear = occYear;
	}

	public String getPlanTypeName() {
		return planTypeName;
	}

	public void setPlanTypeName(String planTypeName) {
		this.planTypeName = planTypeName;
	}

	public BigDecimal getPurAmount() {
		return purAmount;
	}

	public void setPurAmount(BigDecimal purAmount) {
		this.purAmount = purAmount;
	}

	public BigDecimal getLkqAmount() {
		return lkqAmount;
	}

	public void setLkqAmount(BigDecimal lkqAmount) {
		this.lkqAmount = lkqAmount;
	}

	public BigDecimal getLkhAmount() {
		return lkhAmount;
	}

	public void setLkhAmount(BigDecimal lkhAmount) {
		this.lkhAmount = lkhAmount;
	}

	public String getMatAddrName() {
		return matAddrName;
	}

	public void setMatAddrName(String matAddrName) {
		this.matAddrName = matAddrName;
	}

	public String getMatType() {
		return matType;
	}

	public void setMatType(String matType) {
		this.matType = matType;
	}

	public String getPrjType1() {
		return prjType1;
	}

	public void setPrjType1(String prjType1) {
		this.prjType1 = prjType1;
	}

	public BigDecimal getmMatPrice() {
		return mMatPrice;
	}

	public void setmMatPrice(BigDecimal mMatPrice) {
		this.mMatPrice = mMatPrice;
	}

	public BigDecimal getZtAmount() {
		return ztAmount;
	}

	public void setZtAmount(BigDecimal ztAmount) {
		this.ztAmount = ztAmount;
	}

	public BigDecimal getFactAmount() {
		return factAmount;
	}

	public void setFactAmount(BigDecimal factAmount) {
		this.factAmount = factAmount;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountTypeName() {
		return accountTypeName;
	}

	public void setAccountTypeName(String accountTypeName) {
		this.accountTypeName = accountTypeName;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getOldCenter() {
		return oldCenter;
	}

	public void setOldCenter(String oldCenter) {
		this.oldCenter = oldCenter;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public String getOfferDeptName() {
		return offerDeptName;
	}

	public void setOfferDeptName(String offerDeptName) {
		this.offerDeptName = offerDeptName;
	}

	public String getPrjName() {
		return prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	public String getZnksTeamName() {
		return znksTeamName;
	}

	public void setZnksTeamName(String znksTeamName) {
		this.znksTeamName = znksTeamName;
	}

	public Integer getZnksTeam() {
		return znksTeam;
	}

	public void setZnksTeam(Integer znksTeam) {
		this.znksTeam = znksTeam;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getMoveTypeName() {
		return moveTypeName;
	}

	public void setMoveTypeName(String moveTypeName) {
		this.moveTypeName = moveTypeName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getPlanSrcName() {
		return planSrcName;
	}

	public void setPlanSrcName(String planSrcName) {
		this.planSrcName = planSrcName;
	}

	public String getMatUnitName() {
		return matUnitName;
	}

	public void setMatUnitName(String matUnitName) {
		this.matUnitName = matUnitName;
	}

	public String getPlanStepName() {
		return planStepName;
	}

	public void setPlanStepName(String planStepName) {
		this.planStepName = planStepName;
	}

	public BigDecimal getOldAmount() {
		return oldAmount;
	}

	public void setOldAmount(BigDecimal oldAmount) {
		this.oldAmount = oldAmount;
	}

	public Integer getBackCount() {
		return backCount;
	}

	public void setBackCount(Integer backCount) {
		this.backCount = backCount;
	}

	public String getPlanAbc() {
		return planAbc;
	}

	public void setPlanAbc(String planAbc) {
		this.planAbc = planAbc;
	}

	public String getMatAbc() {
		return matAbc;
	}

	public void setMatAbc(String matAbc) {
		this.matAbc = matAbc;
	}

	public String getSbType() {
		return sbType;
	}

	public void setSbType(String sbType) {
		this.sbType = sbType;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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

	public String getRealFlag() {
		return realFlag;
	}

	public void setRealFlag(String realFlag) {
		this.realFlag = realFlag;
	}

	public BigDecimal getSumAmount() {
		return sumAmount;
	}

	public void setSumAmount(BigDecimal sumAmount) {
		this.sumAmount = sumAmount;
	}

	public String getDirect() {
		return direct;
	}

	public void setDirect(String direct) {
		this.direct = direct;
	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public BigDecimal getMatBala() {
		return matBala;
	}

	public void setMatBala(BigDecimal matBala) {
		this.matBala = matBala;
	}

	public String getMatNo() {
		return matNo;
	}

	public void setMatNo(String matNo) {
		this.matNo = matNo;
	}

	public String getPlanNo() {
		return planNo;
	}

	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}

	public String getPrjNo() {
		return prjNo;
	}

	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
	}

	public Integer getTeamNo() {
		return teamNo;
	}

	public void setTeamNo(Integer teamNo) {
		this.teamNo = teamNo;
	}

	public Integer getPayTeam() {
		return payTeam;
	}

	public void setPayTeam(Integer payTeam) {
		this.payTeam = payTeam;
	}

	public String getPlanMonth() {
		return planMonth;
	}

	public void setPlanMonth(String planMonth) {
		this.planMonth = planMonth;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getAbcType() {
		return abcType;
	}

	public void setAbcType(String abcType) {
		this.abcType = abcType;
	}

	public BigDecimal getMatPrice() {
		return matPrice;
	}

	public void setMatPrice(BigDecimal matPrice) {
		this.matPrice = matPrice;
	}

	public BigDecimal getMatAmount() {
		return matAmount;
	}

	public void setMatAmount(BigDecimal matAmount) {
		this.matAmount = matAmount;
	}

	public BigDecimal getUsableAmount() {
		return usableAmount;
	}

	public void setUsableAmount(BigDecimal usableAmount) {
		this.usableAmount = usableAmount;
	}

	public String getUseDate() {
		return useDate;
	}

	public void setUseDate(String useDate) {
		this.useDate = useDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPlanDate() {
		return planDate;
	}

	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}

	public String getPlanTime() {
		return planTime;
	}

	public void setPlanTime(String planTime) {
		this.planTime = planTime;
	}

	public String getPlanStep() {
		return planStep;
	}

	public void setPlanStep(String planStep) {
		this.planStep = planStep;
	}

	public String getCheckNo() {
		return checkNo;
	}

	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}

	public String getOrderDir() {
		return orderDir;
	}

	public void setOrderDir(String orderDir) {
		this.orderDir = orderDir;
	}

	public String getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

	public String getMatAddr() {
		return matAddr;
	}

	public void setMatAddr(String matAddr) {
		this.matAddr = matAddr;
	}

	public String getFactoryNo() {
		return factoryNo;
	}

	public void setFactoryNo(String factoryNo) {
		this.factoryNo = factoryNo;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public String getTraxNo() {
		return traxNo;
	}

	public void setTraxNo(String traxNo) {
		this.traxNo = traxNo;
	}

	public String getWbsElement() {
		return wbsElement;
	}

	public void setWbsElement(String wbsElement) {
		this.wbsElement = wbsElement;
	}

	public String getMoveType() {
		return moveType;
	}

	public void setMoveType(String moveType) {
		this.moveType = moveType;
	}

	public String getListNo() {
		return listNo;
	}

	public void setListNo(String listNo) {
		this.listNo = listNo;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getPurGroup() {
		return purGroup;
	}

	public void setPurGroup(String purGroup) {
		this.purGroup = purGroup;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	public String getReserveNo() {
		return reserveNo;
	}

	public void setReserveNo(String reserveNo) {
		this.reserveNo = reserveNo;
	}

	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public String getIfMake() {
		return ifMake;
	}

	public void setIfMake(String ifMake) {
		this.ifMake = ifMake;
	}

	public String getPlanSrc() {
		return planSrc;
	}

	public void setPlanSrc(String planSrc) {
		this.planSrc = planSrc;
	}

	public String getMatUse() {
		return matUse;
	}

	public void setMatUse(String matUse) {
		this.matUse = matUse;
	}

	public String getNeedComp() {
		return needComp;
	}

	public void setNeedComp(String needComp) {
		this.needComp = needComp;
	}

	public String getNeedFactory() {
		return needFactory;
	}

	public void setNeedFactory(String needFactory) {
		this.needFactory = needFactory;
	}

	public String getPurOrg() {
		return purOrg;
	}

	public void setPurOrg(String purOrg) {
		this.purOrg = purOrg;
	}

	public String getPmNumber() {
		return pmNumber;
	}

	public void setPmNumber(String pmNumber) {
		this.pmNumber = pmNumber;
	}

	public String getIfUrgent() {
		return ifUrgent;
	}

	public void setIfUrgent(String ifUrgent) {
		this.ifUrgent = ifUrgent;
	}

	public String getPurchaseList() {
		return purchaseList;
	}

	public void setPurchaseList(String purchaseList) {
		this.purchaseList = purchaseList;
	}

	public String getOfferNo() {
		return offerNo;
	}

	public void setOfferNo(String offerNo) {
		this.offerNo = offerNo;
	}

	public BigDecimal getSrmPrice() {
		return srmPrice;
	}

	public void setSrmPrice(BigDecimal srmPrice) {
		this.srmPrice = srmPrice;
	}
}