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
@Table(name="m_team_stock")
public class MTeamStock extends MCoreBase{
	private static final long serialVersionUID = 1L;
	private String storeNo;
	private String matNo;
	private String matStatus;
	private Integer teamNo;
	private BigDecimal matAmount;
	private BigDecimal useAmount;
	private String siteCode;

	private String teamName;
	private String matUnitName;
	private String storeName;
	private String matCode;
	private String matName;
	private String matUnit;
	private String erpType;
	private String typeName;
	private BigDecimal matPrice;
	private String reFlag;
	private BigDecimal matBala;

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

	public BigDecimal getMatPrice() {
		return matPrice;
	}

	public void setMatPrice(BigDecimal matPrice) {
		this.matPrice = matPrice;
	}

	public String getReFlag() {
		return reFlag;
	}

	public void setReFlag(String reFlag) {
		this.reFlag = reFlag;
	}

	public BigDecimal getMatBala() {
		return matBala;
	}

	public void setMatBala(BigDecimal matBala) {
		this.matBala = matBala;
	}

	public String getMatUnitName() {
		return matUnitName;
	}

	public void setMatUnitName(String matUnitName) {
		this.matUnitName = matUnitName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
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

	public String getMatStatus() {
		return matStatus;
	}

	public void setMatStatus(String matStatus) {
		this.matStatus = matStatus;
	}

	public Integer getTeamNo() {
		return teamNo;
	}

	public void setTeamNo(Integer teamNo) {
		this.teamNo = teamNo;
	}

	public BigDecimal getMatAmount() {
		return matAmount;
	}

	public void setMatAmount(BigDecimal matAmount) {
		this.matAmount = matAmount;
	}

	public BigDecimal getUseAmount() {
		return useAmount;
	}

	public void setUseAmount(BigDecimal useAmount) {
		this.useAmount = useAmount;
	}

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}
}