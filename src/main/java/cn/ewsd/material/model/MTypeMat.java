package cn.ewsd.material.model;

import cn.ewsd.common.model.MCoreBase;
import io.swagger.models.auth.In;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 
 * 
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-21 16:55:52
 */
@Table(name="m_type_mat")
public class MTypeMat extends MCoreBase{
	private static final long serialVersionUID = 1L;
	private String typeCode;
	private String typeName;
	private String typeUnit;
	private String matNo;
	private String matCode;
	private BigDecimal assessPrice;
	private BigDecimal oldRate;
	private Integer oldLimit;
	private String openDate;

	private String matName;
	private String matUnit;
	private BigDecimal matPrice;
	private String erpType;

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

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeUnit() {
		return typeUnit;
	}

	public void setTypeUnit(String typeUnit) {
		this.typeUnit = typeUnit;
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

	public BigDecimal getAssessPrice() {
		return assessPrice;
	}

	public void setAssessPrice(BigDecimal assessPrice) {
		this.assessPrice = assessPrice;
	}

	public BigDecimal getOldRate() {
		return oldRate;
	}

	public void setOldRate(BigDecimal oldRate) {
		this.oldRate = oldRate;
	}

	public Integer getOldLimit() {
		return oldLimit;
	}

	public void setOldLimit(Integer oldLimit) {
		this.oldLimit = oldLimit;
	}

	public String getOpenDate() {
		return openDate;
	}

	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
}