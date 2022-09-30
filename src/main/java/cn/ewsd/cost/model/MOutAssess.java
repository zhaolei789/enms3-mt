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
@Table(name="m_out_assess")
public class MOutAssess extends MCoreBase{
	private static final long serialVersionUID = 1L;
	private String drawNo;
	private String splitNo;
	private String occDate;
	private String assDate;
	private String teamNo;
	private String storeNo;
	private String centerNo;
	private String prjNo;
	private String prjName;
	private String planItem;
	private String matNo;
	private String matCode;
	private BigDecimal matPrice;
	private BigDecimal matAmount;
	private BigDecimal matBala;
	private String reserveNo;
	private String drawSrc;
	private String ifAccess;
	private String itemNo;
	private String status;
	private String remark;
	private String useAddr;

	private String matName;
	private String erpType;
	private String typeName;
	private String matUnit;
	private String itemName;
	private String storeName;
	private String teamName;
	private String drawSrcName;

	private String upItem;
	private BigDecimal amount;
	private BigDecimal bala;
	private String wbs;

	public String getWbs() {
		return wbs;
	}

	public void setWbs(String wbs) {
		this.wbs = wbs;
	}

	public String getUpItem() {
		return upItem;
	}

	public void setUpItem(String upItem) {
		this.upItem = upItem;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getBala() {
		return bala;
	}

	public void setBala(BigDecimal bala) {
		this.bala = bala;
	}

	public String getDrawSrcName() {
		return drawSrcName;
	}

	public void setDrawSrcName(String drawSrcName) {
		this.drawSrcName = drawSrcName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
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

	public String getMatUnit() {
		return matUnit;
	}

	public void setMatUnit(String matUnit) {
		this.matUnit = matUnit;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getDrawNo() {
		return drawNo;
	}

	public void setDrawNo(String drawNo) {
		this.drawNo = drawNo;
	}

	public String getSplitNo() {
		return splitNo;
	}

	public void setSplitNo(String splitNo) {
		this.splitNo = splitNo;
	}

	public String getOccDate() {
		return occDate;
	}

	public void setOccDate(String occDate) {
		this.occDate = occDate;
	}

	public String getAssDate() {
		return assDate;
	}

	public void setAssDate(String assDate) {
		this.assDate = assDate;
	}

	public String getTeamNo() {
		return teamNo;
	}

	public void setTeamNo(String teamNo) {
		this.teamNo = teamNo;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public String getCenterNo() {
		return centerNo;
	}

	public void setCenterNo(String centerNo) {
		this.centerNo = centerNo;
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

	public String getPlanItem() {
		return planItem;
	}

	public void setPlanItem(String planItem) {
		this.planItem = planItem;
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

	public BigDecimal getMatBala() {
		return matBala;
	}

	public void setMatBala(BigDecimal matBala) {
		this.matBala = matBala;
	}

	public String getReserveNo() {
		return reserveNo;
	}

	public void setReserveNo(String reserveNo) {
		this.reserveNo = reserveNo;
	}

	public String getDrawSrc() {
		return drawSrc;
	}

	public void setDrawSrc(String drawSrc) {
		this.drawSrc = drawSrc;
	}

	public String getIfAccess() {
		return ifAccess;
	}

	public void setIfAccess(String ifAccess) {
		this.ifAccess = ifAccess;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUseAddr() {
		return useAddr;
	}

	public void setUseAddr(String useAddr) {
		this.useAddr = useAddr;
	}

	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}