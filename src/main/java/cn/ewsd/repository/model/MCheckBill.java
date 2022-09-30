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
@Table(name="m_check")
public class MCheckBill extends MCoreBase {
	private static final long serialVersionUID = 1L;
	private String billNo;
	private String checkNo;
	private String storeNo;
	private String matNo;
	private String billType;
	private BigDecimal matAmount;
	private BigDecimal setPrice;
	private String occDate;
	private String occTime;
	private String checkEmp;

	private String billTypeName;
	private String checkEmpName;

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getCheckNo() {
		return checkNo;
	}

	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
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

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public BigDecimal getMatAmount() {
		return matAmount;
	}

	public void setMatAmount(BigDecimal matAmount) {
		this.matAmount = matAmount;
	}

	public BigDecimal getSetPrice() {
		return setPrice;
	}

	public void setSetPrice(BigDecimal setPrice) {
		this.setPrice = setPrice;
	}

	public String getOccDate() {
		return occDate;
	}

	public void setOccDate(String occDate) {
		this.occDate = occDate;
	}

	public String getOccTime() {
		return occTime;
	}

	public void setOccTime(String occTime) {
		this.occTime = occTime;
	}

	public String getCheckEmp() {
		return checkEmp;
	}

	public void setCheckEmp(String checkEmp) {
		this.checkEmp = checkEmp;
	}

	public String getBillTypeName() {
		return billTypeName;
	}

	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
	}

	public String getCheckEmpName() {
		return checkEmpName;
	}

	public void setCheckEmpName(String checkEmpName) {
		this.checkEmpName = checkEmpName;
	}
}