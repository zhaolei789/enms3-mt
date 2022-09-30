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
@Table(name="m_in_back")
public class MInBack extends MCoreBase{
	private static final long serialVersionUID = 1L;
	private String backId;
	private String backStep;
	private String matNo;
	private String storeNo;
	private BigDecimal backAmount;
	private String backDate;
	private String backEmp;
	private String backInfo;
	private String checkNo;
	private String offerNo;
	private String redBill;
	private String initBill;

	public String getBackId() {
		return backId;
	}

	public void setBackId(String backId) {
		this.backId = backId;
	}

	public String getBackStep() {
		return backStep;
	}

	public void setBackStep(String backStep) {
		this.backStep = backStep;
	}

	public String getMatNo() {
		return matNo;
	}

	public void setMatNo(String matNo) {
		this.matNo = matNo;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public BigDecimal getBackAmount() {
		return backAmount;
	}

	public void setBackAmount(BigDecimal backAmount) {
		this.backAmount = backAmount;
	}

	public String getBackDate() {
		return backDate;
	}

	public void setBackDate(String backDate) {
		this.backDate = backDate;
	}

	public String getBackEmp() {
		return backEmp;
	}

	public void setBackEmp(String backEmp) {
		this.backEmp = backEmp;
	}

	public String getBackInfo() {
		return backInfo;
	}

	public void setBackInfo(String backInfo) {
		this.backInfo = backInfo;
	}

	public String getCheckNo() {
		return checkNo;
	}

	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}

	public String getOfferNo() {
		return offerNo;
	}

	public void setOfferNo(String offerNo) {
		this.offerNo = offerNo;
	}

	public String getRedBill() {
		return redBill;
	}

	public void setRedBill(String redBill) {
		this.redBill = redBill;
	}

	public String getInitBill() {
		return initBill;
	}

	public void setInitBill(String initBill) {
		this.initBill = initBill;
	}
}