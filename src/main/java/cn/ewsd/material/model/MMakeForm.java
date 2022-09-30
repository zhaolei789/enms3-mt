package cn.ewsd.material.model;

import cn.ewsd.common.model.MCoreBase;
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
@Table(name="m_make_form")
public class MMakeForm extends MCoreBase{
	private static final long serialVersionUID = 1L;
	private String askNo;
	private String prodNo;
	private String matNo;
	private BigDecimal matPrice;
	private BigDecimal matAmount;
	private BigDecimal otherFee;

	private String matCode;
	private String matName;
	private String matUnit;

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

	public String getAskNo() {
		return askNo;
	}

	public void setAskNo(String askNo) {
		this.askNo = askNo;
	}

	public String getProdNo() {
		return prodNo;
	}

	public void setProdNo(String prodNo) {
		this.prodNo = prodNo;
	}

	public String getMatNo() {
		return matNo;
	}

	public void setMatNo(String matNo) {
		this.matNo = matNo;
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

	public BigDecimal getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}
}