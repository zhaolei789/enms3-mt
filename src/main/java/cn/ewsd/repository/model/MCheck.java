package cn.ewsd.repository.model;

import cn.ewsd.common.model.MCoreBase;

import javax.persistence.Table;

/**
 * 
 * 
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
@Table(name="m_check")
public class MCheck extends MCoreBase {
	private static final long serialVersionUID = 1L;
	private String checkNo;
	private String checkStep;
	private String checkDate;
	private String checkEmp;
	private String storeNo;

	private String checkStepName;
	private String storeName;

	public String getCheckNo() {
		return checkNo;
	}

	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}

	public String getCheckStep() {
		return checkStep;
	}

	public void setCheckStep(String checkStep) {
		this.checkStep = checkStep;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public String getCheckEmp() {
		return checkEmp;
	}

	public void setCheckEmp(String checkEmp) {
		this.checkEmp = checkEmp;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public String getCheckStepName() {
		return checkStepName;
	}

	public void setCheckStepName(String checkStepName) {
		this.checkStepName = checkStepName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
}