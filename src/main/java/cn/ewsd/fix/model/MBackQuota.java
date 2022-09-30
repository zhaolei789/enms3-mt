package cn.ewsd.fix.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import cn.ewsd.common.model.MCoreBase;
import javax.persistence.Table;

/**
 * 
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-05-12 14:04:07
 */
@Table(name="m_back_quota")
public class MBackQuota extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
	private String quotaId;
	private String beginMonth;
	private String prjNo;
	private String prjName;
	private String matNo;
	private Double measure;
	private Double amount;
	private Double matPrice;
	private Integer mngTeam;
	private String modiDate;
	private String modiUser;

	private String matCode;
	private String matName;
	private String matUnit;
	private String mngTeamName;
	private BigDecimal planAmount;
	private BigDecimal planValue;
	private String remark;
	private String planNo;
	private String planStep;

	public String getPlanNo() {
		return planNo;
	}

	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}

	public String getPlanStep() {
		return planStep;
	}

	public void setPlanStep(String planStep) {
		this.planStep = planStep;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getPlanAmount() {
		return planAmount;
	}

	public void setPlanAmount(BigDecimal planAmount) {
		this.planAmount = planAmount;
	}

	public BigDecimal getPlanValue() {
		return planValue;
	}

	public void setPlanValue(BigDecimal planValue) {
		this.planValue = planValue;
	}

	public String getMngTeamName() {
		return mngTeamName;
	}

	public void setMngTeamName(String mngTeamName) {
		this.mngTeamName = mngTeamName;
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

	public void setQuotaId(String quotaId) {
		this.quotaId = quotaId;
	}

	public String getQuotaId() {
		return quotaId;
	}
		
	public void setBeginMonth(String beginMonth) {
		this.beginMonth = beginMonth;
	}

	public String getBeginMonth() {
		return beginMonth;
	}

	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
	}

	public String getPrjNo() {
		return prjNo;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	public String getPrjName() {
		return prjName;
	}

	public void setMatNo(String matNo) {
		this.matNo = matNo;
	}

	public String getMatNo() {
		return matNo;
	}

	public void setMeasure(Double measure) {
		this.measure = measure;
	}

	public Double getMeasure() {
		return measure;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getAmount() {
		return amount;
	}

	public void setMatPrice(Double matPrice) {
		this.matPrice = matPrice;
	}

	public Double getMatPrice() {
		return matPrice;
	}

	public void setMngTeam(Integer mngTeam) {
		this.mngTeam = mngTeam;
	}

	public Integer getMngTeam() {
		return mngTeam;
	}

	public void setModiDate(String modiDate) {
		this.modiDate = modiDate;
	}

	public String getModiDate() {
		return modiDate;
	}

	public void setModiUser(String modiUser) {
		this.modiUser = modiUser;
	}

	public String getModiUser() {
		return modiUser;
	}
	
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}