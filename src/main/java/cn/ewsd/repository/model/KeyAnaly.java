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
public class KeyAnaly extends MCoreBase{
	private static final long serialVersionUID = 1L;
	private String balaMatCode;
	private String balaMatName;
	private String balaMatUnit;
	private String balaTypeName;
	private String balaBala;
	private String priceMatCode;
	private String priceMatName;
	private String priceMatUnit;
	private String priceTypeName;
	private String priceBala;
	private String amountMatCode;
	private String amountMatName;
	private String amountMatUnit;
	private String amountTypeName;
	private String amountBala;

	public String getBalaMatCode() {
		return balaMatCode;
	}

	public void setBalaMatCode(String balaMatCode) {
		this.balaMatCode = balaMatCode;
	}

	public String getBalaMatName() {
		return balaMatName;
	}

	public void setBalaMatName(String balaMatName) {
		this.balaMatName = balaMatName;
	}

	public String getBalaMatUnit() {
		return balaMatUnit;
	}

	public void setBalaMatUnit(String balaMatUnit) {
		this.balaMatUnit = balaMatUnit;
	}

	public String getBalaTypeName() {
		return balaTypeName;
	}

	public void setBalaTypeName(String balaTypeName) {
		this.balaTypeName = balaTypeName;
	}

	public String getBalaBala() {
		return balaBala;
	}

	public void setBalaBala(String balaBala) {
		this.balaBala = balaBala;
	}

	public String getPriceMatCode() {
		return priceMatCode;
	}

	public void setPriceMatCode(String priceMatCode) {
		this.priceMatCode = priceMatCode;
	}

	public String getPriceMatName() {
		return priceMatName;
	}

	public void setPriceMatName(String priceMatName) {
		this.priceMatName = priceMatName;
	}

	public String getPriceMatUnit() {
		return priceMatUnit;
	}

	public void setPriceMatUnit(String priceMatUnit) {
		this.priceMatUnit = priceMatUnit;
	}

	public String getPriceTypeName() {
		return priceTypeName;
	}

	public void setPriceTypeName(String priceTypeName) {
		this.priceTypeName = priceTypeName;
	}

	public String getPriceBala() {
		return priceBala;
	}

	public void setPriceBala(String priceBala) {
		this.priceBala = priceBala;
	}

	public String getAmountMatCode() {
		return amountMatCode;
	}

	public void setAmountMatCode(String amountMatCode) {
		this.amountMatCode = amountMatCode;
	}

	public String getAmountMatName() {
		return amountMatName;
	}

	public void setAmountMatName(String amountMatName) {
		this.amountMatName = amountMatName;
	}

	public String getAmountMatUnit() {
		return amountMatUnit;
	}

	public void setAmountMatUnit(String amountMatUnit) {
		this.amountMatUnit = amountMatUnit;
	}

	public String getAmountTypeName() {
		return amountTypeName;
	}

	public void setAmountTypeName(String amountTypeName) {
		this.amountTypeName = amountTypeName;
	}

	public String getAmountBala() {
		return amountBala;
	}

	public void setAmountBala(String amountBala) {
		this.amountBala = amountBala;
	}
}