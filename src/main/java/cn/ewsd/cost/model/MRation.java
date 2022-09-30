package cn.ewsd.cost.model;

import cn.ewsd.common.model.MCoreBase;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 生产
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-13 18:44:03
 */
@Table(name="m_ration")
public class MRation extends MCoreBase{
	private static final long serialVersionUID = 1L;
	private String rationNo;
	private String rationCode;
	private String rationName;
	private String rationUnit;
	private String isImport;
	private String resSet;
	private String denom;
	private Integer orderNo;
	private String isUsing;

	private String denomName;
	private String occYear;
	private BigDecimal amount;
	private BigDecimal bala;
	private BigDecimal rawAmount;
	private BigDecimal rawBala;

	private String nowAmount;
	private String nowBala;
	private String nowRawAmount;
	private String nowRawBala;
	private String oneAmount;
	private String oneBala;
	private String oneRawAmount;
	private String oneRawBala;
	private String twoAmount;
	private String twoBala;
	private String twoRawAmount;
	private String twoRawBala;

	public String getNowAmount() {
		return nowAmount;
	}

	public void setNowAmount(String nowAmount) {
		this.nowAmount = nowAmount;
	}

	public String getNowBala() {
		return nowBala;
	}

	public void setNowBala(String nowBala) {
		this.nowBala = nowBala;
	}

	public String getNowRawAmount() {
		return nowRawAmount;
	}

	public void setNowRawAmount(String nowRawAmount) {
		this.nowRawAmount = nowRawAmount;
	}

	public String getNowRawBala() {
		return nowRawBala;
	}

	public void setNowRawBala(String nowRawBala) {
		this.nowRawBala = nowRawBala;
	}

	public String getOneAmount() {
		return oneAmount;
	}

	public void setOneAmount(String oneAmount) {
		this.oneAmount = oneAmount;
	}

	public String getOneBala() {
		return oneBala;
	}

	public void setOneBala(String oneBala) {
		this.oneBala = oneBala;
	}

	public String getOneRawAmount() {
		return oneRawAmount;
	}

	public void setOneRawAmount(String oneRawAmount) {
		this.oneRawAmount = oneRawAmount;
	}

	public String getOneRawBala() {
		return oneRawBala;
	}

	public void setOneRawBala(String oneRawBala) {
		this.oneRawBala = oneRawBala;
	}

	public String getTwoAmount() {
		return twoAmount;
	}

	public void setTwoAmount(String twoAmount) {
		this.twoAmount = twoAmount;
	}

	public String getTwoBala() {
		return twoBala;
	}

	public void setTwoBala(String twoBala) {
		this.twoBala = twoBala;
	}

	public String getTwoRawAmount() {
		return twoRawAmount;
	}

	public void setTwoRawAmount(String twoRawAmount) {
		this.twoRawAmount = twoRawAmount;
	}

	public String getTwoRawBala() {
		return twoRawBala;
	}

	public void setTwoRawBala(String twoRawBala) {
		this.twoRawBala = twoRawBala;
	}

	public String getOccYear() {
		return occYear;
	}

	public void setOccYear(String occYear) {
		this.occYear = occYear;
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

	public BigDecimal getRawAmount() {
		return rawAmount;
	}

	public void setRawAmount(BigDecimal rawAmount) {
		this.rawAmount = rawAmount;
	}

	public BigDecimal getRawBala() {
		return rawBala;
	}

	public void setRawBala(BigDecimal rawBala) {
		this.rawBala = rawBala;
	}

	public String getDenomName() {
		return denomName;
	}

	public void setDenomName(String denomName) {
		this.denomName = denomName;
	}

	public String getRationNo() {
		return rationNo;
	}

	public void setRationNo(String rationNo) {
		this.rationNo = rationNo;
	}

	public String getRationCode() {
		return rationCode;
	}

	public void setRationCode(String rationCode) {
		this.rationCode = rationCode;
	}

	public String getRationName() {
		return rationName;
	}

	public void setRationName(String rationName) {
		this.rationName = rationName;
	}

	public String getRationUnit() {
		return rationUnit;
	}

	public void setRationUnit(String rationUnit) {
		this.rationUnit = rationUnit;
	}

	public String getIsImport() {
		return isImport;
	}

	public void setIsImport(String isImport) {
		this.isImport = isImport;
	}

	public String getResSet() {
		return resSet;
	}

	public void setResSet(String resSet) {
		this.resSet = resSet;
	}

	public String getDenom() {
		return denom;
	}

	public void setDenom(String denom) {
		this.denom = denom;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getIsUsing() {
		return isUsing;
	}

	public void setIsUsing(String isUsing) {
		this.isUsing = isUsing;
	}

	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}