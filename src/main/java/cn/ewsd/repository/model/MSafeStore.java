package cn.ewsd.repository.model;

import cn.ewsd.common.model.MCoreBase;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 
 * 
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
@Table(name="m_safe_stock")
public class MSafeStore extends MCoreBase{
	private static final long serialVersionUID = 1L;
	private String storeNo;
	private String storeName;
	private String matNo;
	private String matName;
	private BigDecimal yellowWarn;
	private BigDecimal redWarn;
	private BigDecimal maxAmount;
	private String abcType;
	private String matUnit;
	private String matUnitName;
	private String matCode;
	private String erpType;
	private String typeName;
	private BigDecimal stockAmount;

	public BigDecimal getStockAmount() {
		return stockAmount;
	}

	public void setStockAmount(BigDecimal stockAmount) {
		this.stockAmount = stockAmount;
	}

	public String getMatUnitName() {
		return matUnitName;
	}

	public void setMatUnitName(String matUnitName) {
		this.matUnitName = matUnitName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getAbcType() {
		return abcType;
	}

	public void setAbcType(String abcType) {
		this.abcType = abcType;
	}

	public String getMatUnit() {
		return matUnit;
	}

	public void setMatUnit(String matUnit) {
		this.matUnit = matUnit;
	}

	public String getMatCode() {
		return matCode;
	}

	public void setMatCode(String matCode) {
		this.matCode = matCode;
	}

	public String getErpType() {
		return erpType;
	}

	public void setErpType(String erpType) {
		this.erpType = erpType;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getMatNo() {
		return matNo;
	}

	public void setMatNo(String matNo) {
		this.matNo = matNo;
	}

	public String getMatName() {
		return matName;
	}

	public void setMatName(String matName) {
		this.matName = matName;
	}

	public BigDecimal getYellowWarn() {
		return yellowWarn;
	}

	public void setYellowWarn(BigDecimal yellowWarn) {
		this.yellowWarn = yellowWarn;
	}

	public BigDecimal getRedWarn() {
		return redWarn;
	}

	public void setRedWarn(BigDecimal redWarn) {
		this.redWarn = redWarn;
	}

	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}
}