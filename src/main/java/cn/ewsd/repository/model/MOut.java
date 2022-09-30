package cn.ewsd.repository.model;

import java.math.BigDecimal;
import cn.ewsd.common.model.MCoreBase;
import javax.persistence.Table;

/**
 * 
 * 
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
@Table(name="m_out")
public class MOut extends MCoreBase{
	private static final long serialVersionUID = 1L;
	private String drawNo;
	private String outType;
	private String dataSrc;
	private String planNo;
	private String planTeam;
	private String matNo;
	private BigDecimal applyAmount;
	private String applyDate;
	private String applyTime;
	private String applyEmp;
	private String urgentLevel;
	private String storeAddr;
	private String ifSend;
	private String useAddr;
	private String applyInfo;
	private BigDecimal chkAmount;
	private String agreeDate;
	private String agreeTime;
	private String agreeEmp;
	private String abcType;
	private String drawStep;
	private String checkNo;
	private BigDecimal outAmount;
	private String pickDate;
	private String pickTime;
	private String drawEmp;
	private Integer teamNo;
	private String storeNo;
	private String outDate;
	private String outTime;
	private String payTeam;
	private String fMonth;
	private String itemNo;
	private BigDecimal planPrice;
	private BigDecimal matPrice;
	private BigDecimal price1;
	private BigDecimal price2;
	private String prjNo;
	private String reserveNo;
	private String linkNo;
	private Integer offerTeam;
	private String reserve1;
	private String reserve2;
	private String reserve3;

	private String matCode;
	private String matName;
	private String erpType;
	private String typeName;
	private BigDecimal bala;
	private String storeName;
	private String prjName;
	private String drawStepName;
	private String planTeamName;
	private String offerTeamName;
	private String applyMonth;
	private String upName;
	private Integer cnt;
	private BigDecimal teamAmount;
	private BigDecimal matBala;
	private String applyDt;
	private BigDecimal packScale;
	private String matUnit;
	private BigDecimal avgAmount;
	private String siteCode;
	private String prjType1;
	private String occDate;
	private BigDecimal amount;
	private String itemName;
	private Integer count;
	private String teamName;
	private String empName;
	private String planMonth;
	private String backDraw;
	private String drawEmpName;
	private BigDecimal fixBala;
	private BigDecimal oldRate;
	private BigDecimal chkBala;
	private BigDecimal outBala;
	private BigDecimal planBala;
	private String wbsElement;
	private String centerName;
	private String centerNo;
	private BigDecimal fixPrice;
	private BigDecimal fixContent;
	private String znksName;
	private BigDecimal stockAmount;

	public BigDecimal getStockAmount() {
		return stockAmount;
	}

	public void setStockAmount(BigDecimal stockAmount) {
		this.stockAmount = stockAmount;
	}

	public String getZnksName() {
		return znksName;
	}

	public void setZnksName(String znksName) {
		this.znksName = znksName;
	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public String getCenterNo() {
		return centerNo;
	}

	public void setCenterNo(String centerNo) {
		this.centerNo = centerNo;
	}

	public BigDecimal getFixPrice() {
		return fixPrice;
	}

	public void setFixPrice(BigDecimal fixPrice) {
		this.fixPrice = fixPrice;
	}

	public BigDecimal getFixContent() {
		return fixContent;
	}

	public void setFixContent(BigDecimal fixContent) {
		this.fixContent = fixContent;
	}

	public String getWbsElement() {
		return wbsElement;
	}

	public void setWbsElement(String wbsElement) {
		this.wbsElement = wbsElement;
	}

	public BigDecimal getPlanBala() {
		return planBala;
	}

	public void setPlanBala(BigDecimal planBala) {
		this.planBala = planBala;
	}

	public BigDecimal getChkBala() {
		return chkBala;
	}

	public void setChkBala(BigDecimal chkBala) {
		this.chkBala = chkBala;
	}

	public BigDecimal getOutBala() {
		return outBala;
	}

	public void setOutBala(BigDecimal outBala) {
		this.outBala = outBala;
	}

	public BigDecimal getOldRate() {
		return oldRate;
	}

	public void setOldRate(BigDecimal oldRate) {
		this.oldRate = oldRate;
	}

	public BigDecimal getFixBala() {
		return fixBala;
	}

	public void setFixBala(BigDecimal fixBala) {
		this.fixBala = fixBala;
	}

	public String getDrawEmpName() {
		return drawEmpName;
	}

	public void setDrawEmpName(String drawEmpName) {
		this.drawEmpName = drawEmpName;
	}

	public String getBackDraw() {
		return backDraw;
	}

	public void setBackDraw(String backDraw) {
		this.backDraw = backDraw;
	}

	public String getPlanMonth() {
		return planMonth;
	}

	public void setPlanMonth(String planMonth) {
		this.planMonth = planMonth;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getOccDate() {
		return occDate;
	}

	public void setOccDate(String occDate) {
		this.occDate = occDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getPrjType1() {
		return prjType1;
	}

	public void setPrjType1(String prjType1) {
		this.prjType1 = prjType1;
	}

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public BigDecimal getAvgAmount() {
		return avgAmount;
	}

	public void setAvgAmount(BigDecimal avgAmount) {
		this.avgAmount = avgAmount;
	}

	public BigDecimal getTeamAmount() {
		return teamAmount;
	}

	public void setTeamAmount(BigDecimal teamAmount) {
		this.teamAmount = teamAmount;
	}

	public BigDecimal getMatBala() {
		return matBala;
	}

	public void setMatBala(BigDecimal matBala) {
		this.matBala = matBala;
	}

	public String getApplyDt() {
		return applyDt;
	}

	public void setApplyDt(String applyDt) {
		this.applyDt = applyDt;
	}

	public BigDecimal getPackScale() {
		return packScale;
	}

	public void setPackScale(BigDecimal packScale) {
		this.packScale = packScale;
	}

	public String getMatUnit() {
		return matUnit;
	}

	public void setMatUnit(String matUnit) {
		this.matUnit = matUnit;
	}

	public String getApplyMonth() {
		return applyMonth;
	}

	public void setApplyMonth(String applyMonth) {
		this.applyMonth = applyMonth;
	}

	public String getUpName() {
		return upName;
	}

	public void setUpName(String upName) {
		this.upName = upName;
	}

	public Integer getCnt() {
		return cnt;
	}

	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}

	public String getOfferTeamName() {
		return offerTeamName;
	}

	public void setOfferTeamName(String offerTeamName) {
		this.offerTeamName = offerTeamName;
	}

	public String getPlanTeamName() {
		return planTeamName;
	}

	public void setPlanTeamName(String planTeamName) {
		this.planTeamName = planTeamName;
	}

	public String getDrawStepName() {
		return drawStepName;
	}

	public void setDrawStepName(String drawStepName) {
		this.drawStepName = drawStepName;
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

	public BigDecimal getBala() {
		return bala;
	}

	public void setBala(BigDecimal bala) {
		this.bala = bala;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getPrjName() {
		return prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	public String getDrawNo() {
		return drawNo;
	}

	public void setDrawNo(String drawNo) {
		this.drawNo = drawNo;
	}

	public String getOutType() {
		return outType;
	}

	public void setOutType(String outType) {
		this.outType = outType;
	}

	public String getDataSrc() {
		return dataSrc;
	}

	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}

	public String getPlanNo() {
		return planNo;
	}

	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}

	public String getPlanTeam() {
		return planTeam;
	}

	public void setPlanTeam(String planTeam) {
		this.planTeam = planTeam;
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

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public String getApplyEmp() {
		return applyEmp;
	}

	public void setApplyEmp(String applyEmp) {
		this.applyEmp = applyEmp;
	}

	public String getUrgentLevel() {
		return urgentLevel;
	}

	public void setUrgentLevel(String urgentLevel) {
		this.urgentLevel = urgentLevel;
	}

	public String getStoreAddr() {
		return storeAddr;
	}

	public void setStoreAddr(String storeAddr) {
		this.storeAddr = storeAddr;
	}

	public String getIfSend() {
		return ifSend;
	}

	public void setIfSend(String ifSend) {
		this.ifSend = ifSend;
	}

	public String getUseAddr() {
		return useAddr;
	}

	public void setUseAddr(String useAddr) {
		this.useAddr = useAddr;
	}

	public String getApplyInfo() {
		return applyInfo;
	}

	public void setApplyInfo(String applyInfo) {
		this.applyInfo = applyInfo;
	}

	public BigDecimal getChkAmount() {
		return chkAmount;
	}

	public void setChkAmount(BigDecimal chkAmount) {
		this.chkAmount = chkAmount;
	}

	public String getAgreeDate() {
		return agreeDate;
	}

	public void setAgreeDate(String agreeDate) {
		this.agreeDate = agreeDate;
	}

	public String getAgreeTime() {
		return agreeTime;
	}

	public void setAgreeTime(String agreeTime) {
		this.agreeTime = agreeTime;
	}

	public String getAgreeEmp() {
		return agreeEmp;
	}

	public void setAgreeEmp(String agreeEmp) {
		this.agreeEmp = agreeEmp;
	}

	public String getAbcType() {
		return abcType;
	}

	public void setAbcType(String abcType) {
		this.abcType = abcType;
	}

	public String getDrawStep() {
		return drawStep;
	}

	public void setDrawStep(String drawStep) {
		this.drawStep = drawStep;
	}

	public String getCheckNo() {
		return checkNo;
	}

	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}

	public BigDecimal getOutAmount() {
		return outAmount;
	}

	public void setOutAmount(BigDecimal outAmount) {
		this.outAmount = outAmount;
	}

	public String getPickDate() {
		return pickDate;
	}

	public void setPickDate(String pickDate) {
		this.pickDate = pickDate;
	}

	public String getPickTime() {
		return pickTime;
	}

	public void setPickTime(String pickTime) {
		this.pickTime = pickTime;
	}

	public String getDrawEmp() {
		return drawEmp;
	}

	public void setDrawEmp(String drawEmp) {
		this.drawEmp = drawEmp;
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

	public String getOutDate() {
		return outDate;
	}

	public void setOutDate(String outDate) {
		this.outDate = outDate;
	}

	public String getOutTime() {
		return outTime;
	}

	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}

	public String getPayTeam() {
		return payTeam;
	}

	public void setPayTeam(String payTeam) {
		this.payTeam = payTeam;
	}

	public String getfMonth() {
		return fMonth;
	}

	public void setfMonth(String fMonth) {
		this.fMonth = fMonth;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public BigDecimal getPlanPrice() {
		return planPrice;
	}

	public void setPlanPrice(BigDecimal planPrice) {
		this.planPrice = planPrice;
	}

	public BigDecimal getMatPrice() {
		return matPrice;
	}

	public void setMatPrice(BigDecimal matPrice) {
		this.matPrice = matPrice;
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

	public String getPrjNo() {
		return prjNo;
	}

	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
	}

	public String getReserveNo() {
		return reserveNo;
	}

	public void setReserveNo(String reserveNo) {
		this.reserveNo = reserveNo;
	}

	public String getLinkNo() {
		return linkNo;
	}

	public void setLinkNo(String linkNo) {
		this.linkNo = linkNo;
	}

	public Integer getOfferTeam() {
		return offerTeam;
	}

	public void setOfferTeam(Integer offerTeam) {
		this.offerTeam = offerTeam;
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
}