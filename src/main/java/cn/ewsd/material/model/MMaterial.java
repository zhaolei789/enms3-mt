package cn.ewsd.material.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import cn.ewsd.common.model.MCoreBase;
import javax.persistence.Table;

/**
 * 
 * 
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
@Table(name="m_material")
public class MMaterial extends MCoreBase{
	private static final long serialVersionUID = 1L;
	private String matNo;
	private String matCode;
	private String matName;
	private String newOld;
	private String matType;
	private String matUnit;
	private BigDecimal matPrice;
	private BigDecimal oldRebate;
	private String realFlag;
	private String reFlag;
	private String offenFlag;
	private String useInfo;
	private String insDate;
	private String erpCode;
	private String abcType;
	private String ifSend;
	private BigDecimal packScale;
	private String goodsType;
	private BigDecimal payFee;
	private BigDecimal otherFee;
	private String erpType;
	private String typeName;
	private BigDecimal planPrice;
	private BigDecimal fixPrice;
	private String fixContent;
	private BigDecimal jfPrice;
	private Integer arrivePeriod;
	private Integer qaPeriod;
	private String factoryNo;
	private String matAddr;
	private String matAddrName;
	private Integer makeTeam;
	private BigDecimal matAmount;
	private BigDecimal oldAmount;
	private String sbType;
	private String matUnitName;
	private BigDecimal planAmount;
	private String planNo;
	private String prjNo;
	private String prjName;
	private String teamName;
	private String storeNo;
	private BigDecimal stockAmount;
	private BigDecimal bala;
	private String storeName;
	private String reserveNo;
	private String centerNo;
	private String centerName;

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getReserveNo() {
		return reserveNo;
	}

	public void setReserveNo(String reserveNo) {
		this.reserveNo = reserveNo;
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

	public BigDecimal getBala() {
		return bala;
	}

	public void setBala(BigDecimal bala) {
		this.bala = bala;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public BigDecimal getStockAmount() {
		return stockAmount;
	}

	public void setStockAmount(BigDecimal stockAmount) {
		this.stockAmount = stockAmount;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
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

	public String getPlanNo() {
		return planNo;
	}

	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}

	public BigDecimal getPlanAmount() {
		return planAmount;
	}

	public void setPlanAmount(BigDecimal planAmount) {
		this.planAmount = planAmount;
	}

	public String getMatUnitName() {
		return matUnitName;
	}

	public void setMatUnitName(String matUnitName) {
		this.matUnitName = matUnitName;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getMatAddrName() {
		return matAddrName;
	}

	public void setMatAddrName(String matAddrName) {
		this.matAddrName = matAddrName;
	}

	public BigDecimal getMatAmount() {
		return matAmount;
	}

	public void setMatAmount(BigDecimal matAmount) {
		this.matAmount = matAmount;
	}

	public BigDecimal getOldAmount() {
		return oldAmount;
	}

	public void setOldAmount(BigDecimal oldAmount) {
		this.oldAmount = oldAmount;
	}

	public String getSbType() {
		return sbType;
	}

	public void setSbType(String sbType) {
		this.sbType = sbType;
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

	public String getNewOld() {
		return newOld;
	}

	public void setNewOld(String newOld) {
		this.newOld = newOld;
	}

	public String getMatType() {
		return matType;
	}

	public void setMatType(String matType) {
		this.matType = matType;
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

	public BigDecimal getOldRebate() {
		return oldRebate;
	}

	public void setOldRebate(BigDecimal oldRebate) {
		this.oldRebate = oldRebate;
	}

	public String getRealFlag() {
		return realFlag;
	}

	public void setRealFlag(String realFlag) {
		this.realFlag = realFlag;
	}

	public String getReFlag() {
		return reFlag;
	}

	public void setReFlag(String reFlag) {
		this.reFlag = reFlag;
	}

	public String getOffenFlag() {
		return offenFlag;
	}

	public void setOffenFlag(String offenFlag) {
		this.offenFlag = offenFlag;
	}

	public String getUseInfo() {
		return useInfo;
	}

	public void setUseInfo(String useInfo) {
		this.useInfo = useInfo;
	}

	public String getInsDate() {
		return insDate;
	}

	public void setInsDate(String insDate) {
		this.insDate = insDate;
	}

	public String getErpCode() {
		return erpCode;
	}

	public void setErpCode(String erpCode) {
		this.erpCode = erpCode;
	}

	public String getAbcType() {
		return abcType;
	}

	public void setAbcType(String abcType) {
		this.abcType = abcType;
	}

	public String getIfSend() {
		return ifSend;
	}

	public void setIfSend(String ifSend) {
		this.ifSend = ifSend;
	}

	public BigDecimal getPackScale() {
		return packScale;
	}

	public void setPackScale(BigDecimal packScale) {
		this.packScale = packScale;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public BigDecimal getPayFee() {
		return payFee;
	}

	public void setPayFee(BigDecimal payFee) {
		this.payFee = payFee;
	}

	public BigDecimal getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
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

	public BigDecimal getPlanPrice() {
		return planPrice;
	}

	public void setPlanPrice(BigDecimal planPrice) {
		this.planPrice = planPrice;
	}

	public BigDecimal getFixPrice() {
		return fixPrice;
	}

	public void setFixPrice(BigDecimal fixPrice) {
		this.fixPrice = fixPrice;
	}

	public String getFixContent() {
		return fixContent;
	}

	public void setFixContent(String fixContent) {
		this.fixContent = fixContent;
	}

	public BigDecimal getJfPrice() {
		return jfPrice;
	}

	public void setJfPrice(BigDecimal jfPrice) {
		this.jfPrice = jfPrice;
	}

	public Integer getArrivePeriod() {
		return arrivePeriod;
	}

	public void setArrivePeriod(Integer arrivePeriod) {
		this.arrivePeriod = arrivePeriod;
	}

	public Integer getQaPeriod() {
		return qaPeriod;
	}

	public void setQaPeriod(Integer qaPeriod) {
		this.qaPeriod = qaPeriod;
	}

	public String getFactoryNo() {
		return factoryNo;
	}

	public void setFactoryNo(String factoryNo) {
		this.factoryNo = factoryNo;
	}

	public String getMatAddr() {
		return matAddr;
	}

	public void setMatAddr(String matAddr) {
		this.matAddr = matAddr;
	}

	public Integer getMakeTeam() {
		return makeTeam;
	}

	public void setMakeTeam(Integer makeTeam) {
		this.makeTeam = makeTeam;
	}
}