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
@Table(name="m_stock")
public class MStock extends MCoreBase{
	private static final long serialVersionUID = 1L;
	private String storeNo;
	private String matNo;
	private BigDecimal inAmount;
	private BigDecimal outAmount;
	private BigDecimal stockAmount;
	private BigDecimal packAmount;
	private BigDecimal lockAmount;
	private BigDecimal bulkAmount;
	private String siteCode;

	private String storeName;
	private Integer teamNo;
	private BigDecimal matBala;
	private String matCode;
	private String matName;
	private String matUnit;
	private String abcType;
	private BigDecimal matPrice;
	private String erpType;
	private String typeName;
	private BigDecimal redWarn;
	private BigDecimal diffAmount;
	private BigDecimal oldAmount;
	private String oldCode;
	private BigDecimal warnAmount;
	private BigDecimal qqAmount;
	private BigDecimal qqBala;
	private BigDecimal bqrAmount;
	private BigDecimal bqrBala;
	private BigDecimal bqcAmount;
	private BigDecimal bqcBala;
	private BigDecimal jyAmount;
	private BigDecimal jyBala;
	private String qqAmountText;
	private String qqBalaText;
	private String bqrAmountText;
	private String bqrBalaText;
	private String bqcAmountText;
	private String bqcBalaText;
	private String jyAmountText;
	private String jyBalaText;
	private String storeLevel;
	private String storeLevelName;
	private BigDecimal amount;
	private String billDate;
	private String centerName;
	private BigDecimal setPrice;
	private BigDecimal bala;
	private String supplier;

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public BigDecimal getSetPrice() {
		return setPrice;
	}

	public void setSetPrice(BigDecimal setPrice) {
		this.setPrice = setPrice;
	}

	public BigDecimal getBala() {
		return bala;
	}

	public void setBala(BigDecimal bala) {
		this.bala = bala;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getStoreLevel() {
		return storeLevel;
	}

	public void setStoreLevel(String storeLevel) {
		this.storeLevel = storeLevel;
	}

	public String getStoreLevelName() {
		return storeLevelName;
	}

	public void setStoreLevelName(String storeLevelName) {
		this.storeLevelName = storeLevelName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getQqAmountText() {
		return qqAmountText;
	}

	public void setQqAmountText(String qqAmountText) {
		this.qqAmountText = qqAmountText;
	}

	public String getQqBalaText() {
		return qqBalaText;
	}

	public void setQqBalaText(String qqBalaText) {
		this.qqBalaText = qqBalaText;
	}

	public String getBqrAmountText() {
		return bqrAmountText;
	}

	public void setBqrAmountText(String bqrAmountText) {
		this.bqrAmountText = bqrAmountText;
	}

	public String getBqrBalaText() {
		return bqrBalaText;
	}

	public void setBqrBalaText(String bqrBalaText) {
		this.bqrBalaText = bqrBalaText;
	}

	public String getBqcAmountText() {
		return bqcAmountText;
	}

	public void setBqcAmountText(String bqcAmountText) {
		this.bqcAmountText = bqcAmountText;
	}

	public String getBqcBalaText() {
		return bqcBalaText;
	}

	public void setBqcBalaText(String bqcBalaText) {
		this.bqcBalaText = bqcBalaText;
	}

	public String getJyAmountText() {
		return jyAmountText;
	}

	public void setJyAmountText(String jyAmountText) {
		this.jyAmountText = jyAmountText;
	}

	public String getJyBalaText() {
		return jyBalaText;
	}

	public void setJyBalaText(String jyBalaText) {
		this.jyBalaText = jyBalaText;
	}

	public BigDecimal getQqAmount() {
		return qqAmount;
	}

	public void setQqAmount(BigDecimal qqAmount) {
		this.qqAmount = qqAmount;
	}

	public BigDecimal getQqBala() {
		return qqBala;
	}

	public void setQqBala(BigDecimal qqBala) {
		this.qqBala = qqBala;
	}

	public BigDecimal getBqrAmount() {
		return bqrAmount;
	}

	public void setBqrAmount(BigDecimal bqrAmount) {
		this.bqrAmount = bqrAmount;
	}

	public BigDecimal getBqrBala() {
		return bqrBala;
	}

	public void setBqrBala(BigDecimal bqrBala) {
		this.bqrBala = bqrBala;
	}

	public BigDecimal getBqcAmount() {
		return bqcAmount;
	}

	public void setBqcAmount(BigDecimal bqcAmount) {
		this.bqcAmount = bqcAmount;
	}

	public BigDecimal getBqcBala() {
		return bqcBala;
	}

	public void setBqcBala(BigDecimal bqcBala) {
		this.bqcBala = bqcBala;
	}

	public BigDecimal getJyAmount() {
		return jyAmount;
	}

	public void setJyAmount(BigDecimal jyAmount) {
		this.jyAmount = jyAmount;
	}

	public BigDecimal getJyBala() {
		return jyBala;
	}

	public void setJyBala(BigDecimal jyBala) {
		this.jyBala = jyBala;
	}

	public BigDecimal getWarnAmount() {
		return warnAmount;
	}

	public void setWarnAmount(BigDecimal warnAmount) {
		this.warnAmount = warnAmount;
	}

	public String getOldCode() {
		return oldCode;
	}

	public void setOldCode(String oldCode) {
		this.oldCode = oldCode;
	}

	public BigDecimal getOldAmount() {
		return oldAmount;
	}

	public void setOldAmount(BigDecimal oldAmount) {
		this.oldAmount = oldAmount;
	}

	public BigDecimal getRedWarn() {
		return redWarn;
	}

	public void setRedWarn(BigDecimal redWarn) {
		this.redWarn = redWarn;
	}

	public BigDecimal getDiffAmount() {
		return diffAmount;
	}

	public void setDiffAmount(BigDecimal diffAmount) {
		this.diffAmount = diffAmount;
	}

	public BigDecimal getMatBala() {
		return matBala;
	}

	public void setMatBala(BigDecimal matBala) {
		this.matBala = matBala;
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

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
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

	public String getMatNo() {
		return matNo;
	}

	public void setMatNo(String matNo) {
		this.matNo = matNo;
	}

	public BigDecimal getInAmount() {
		return inAmount;
	}

	public void setInAmount(BigDecimal inAmount) {
		this.inAmount = inAmount;
	}

	public BigDecimal getOutAmount() {
		return outAmount;
	}

	public void setOutAmount(BigDecimal outAmount) {
		this.outAmount = outAmount;
	}

	public BigDecimal getStockAmount() {
		return stockAmount;
	}

	public void setStockAmount(BigDecimal stockAmount) {
		this.stockAmount = stockAmount;
	}

	public BigDecimal getPackAmount() {
		return packAmount;
	}

	public void setPackAmount(BigDecimal packAmount) {
		this.packAmount = packAmount;
	}

	public BigDecimal getLockAmount() {
		return lockAmount;
	}

	public void setLockAmount(BigDecimal lockAmount) {
		this.lockAmount = lockAmount;
	}

	public BigDecimal getBulkAmount() {
		return bulkAmount;
	}

	public void setBulkAmount(BigDecimal bulkAmount) {
		this.bulkAmount = bulkAmount;
	}

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}
}