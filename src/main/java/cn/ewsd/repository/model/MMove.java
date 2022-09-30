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
@Table(name="m_move")
public class MMove extends MCoreBase {
	private static final long serialVersionUID = 1L;
	private String transNo;
	private String moveStep;
	private String outStore;
	private String inStore;
	private String takeEmp;
	private String takeDate;
	private String takeTime;
	private String occDate;
	private String occTime;
	private String handDate;
	private String handTime;
	private String signDate;
	private String signTime;
	private String checkNo;
	private String operationEmp;

	private String listNo;
	private String matNo;
	private Double matAmount;
	private Double pickAmount;
	private String pickEmp;
	private Double getAmount;
	private String getEmp;
	private String getDate;
	private Double matPrice;

	private String stepName;
	private String inName;
	private String outName;
	private String matCode;
	private String matName;
	private String matUnit;
	private String erpType;
	private String typeName;
	private Double stockAmount;
	private String siteCode;

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

	public Double getStockAmount() {
		return stockAmount;
	}

	public void setStockAmount(Double stockAmount) {
		this.stockAmount = stockAmount;
	}

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public String getListNo() {
		return listNo;
	}

	public void setListNo(String listNo) {
		this.listNo = listNo;
	}

	public String getMatNo() {
		return matNo;
	}

	public void setMatNo(String matNo) {
		this.matNo = matNo;
	}

	public Double getMatAmount() {
		return matAmount;
	}

	public void setMatAmount(Double matAmount) {
		this.matAmount = matAmount;
	}

	public Double getPickAmount() {
		return pickAmount;
	}

	public void setPickAmount(Double pickAmount) {
		this.pickAmount = pickAmount;
	}

	public String getPickEmp() {
		return pickEmp;
	}

	public void setPickEmp(String pickEmp) {
		this.pickEmp = pickEmp;
	}

	public Double getGetAmount() {
		return getAmount;
	}

	public void setGetAmount(Double getAmount) {
		this.getAmount = getAmount;
	}

	public String getGetEmp() {
		return getEmp;
	}

	public void setGetEmp(String getEmp) {
		this.getEmp = getEmp;
	}

	public String getGetDate() {
		return getDate;
	}

	public void setGetDate(String getDate) {
		this.getDate = getDate;
	}

	public Double getMatPrice() {
		return matPrice;
	}

	public void setMatPrice(Double matPrice) {
		this.matPrice = matPrice;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getInName() {
		return inName;
	}

	public void setInName(String inName) {
		this.inName = inName;
	}

	public String getOutName() {
		return outName;
	}

	public void setOutName(String outName) {
		this.outName = outName;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public String getMoveStep() {
		return moveStep;
	}

	public void setMoveStep(String moveStep) {
		this.moveStep = moveStep;
	}

	public String getOutStore() {
		return outStore;
	}

	public void setOutStore(String outStore) {
		this.outStore = outStore;
	}

	public String getInStore() {
		return inStore;
	}

	public void setInStore(String inStore) {
		this.inStore = inStore;
	}

	public String getTakeEmp() {
		return takeEmp;
	}

	public void setTakeEmp(String takeEmp) {
		this.takeEmp = takeEmp;
	}

	public String getTakeDate() {
		return takeDate;
	}

	public void setTakeDate(String takeDate) {
		this.takeDate = takeDate;
	}

	public String getTakeTime() {
		return takeTime;
	}

	public void setTakeTime(String takeTime) {
		this.takeTime = takeTime;
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

	public String getHandDate() {
		return handDate;
	}

	public void setHandDate(String handDate) {
		this.handDate = handDate;
	}

	public String getHandTime() {
		return handTime;
	}

	public void setHandTime(String handTime) {
		this.handTime = handTime;
	}

	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	public String getSignTime() {
		return signTime;
	}

	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}

	public String getCheckNo() {
		return checkNo;
	}

	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}

	public String getOperationEmp() {
		return operationEmp;
	}

	public void setOperationEmp(String operationEmp) {
		this.operationEmp = operationEmp;
	}
}