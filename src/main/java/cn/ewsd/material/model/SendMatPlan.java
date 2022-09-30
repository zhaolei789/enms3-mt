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
public class SendMatPlan extends MCoreBase{
	private static final long serialVersionUID = 1L;
	private String planNo;
	private String prjNo;
	private Integer teamNo;
	private String teamName;
	private Integer payTeam;
	private String payTeamName;
	private String planMonth;
	private String planType;
	private String planTypeName;
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

	private BigDecimal matBala;
	private String erpType;
	private String typeName;

	public String getPlanTypeName() {
		return planTypeName;
	}

	public void setPlanTypeName(String planTypeName) {
		this.planTypeName = planTypeName;
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

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Integer getPayTeam() {
		return payTeam;
	}

	public void setPayTeam(Integer payTeam) {
		this.payTeam = payTeam;
	}

	public String getPayTeamName() {
		return payTeamName;
	}

	public void setPayTeamName(String payTeamName) {
		this.payTeamName = payTeamName;
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

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getMatNo() {
		return matNo;
	}

	public void setMatNo(String matNo) {
		this.matNo = matNo;
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

	public String getMatUnitName() {
		return matUnitName;
	}

	public void setMatUnitName(String matUnitName) {
		this.matUnitName = matUnitName;
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

	public String getPlanStepName() {
		return planStepName;
	}

	public void setPlanStepName(String planStepName) {
		this.planStepName = planStepName;
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

	public String getMoveTypeName() {
		return moveTypeName;
	}

	public void setMoveTypeName(String moveTypeName) {
		this.moveTypeName = moveTypeName;
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

	public String getPlanSrcName() {
		return planSrcName;
	}

	public void setPlanSrcName(String planSrcName) {
		this.planSrcName = planSrcName;
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

	public BigDecimal getMatBala() {
		return matBala;
	}

	public void setMatBala(BigDecimal matBala) {
		this.matBala = matBala;
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
}