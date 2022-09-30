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
 * @Date 2021-05-13 15:11:55
 */
@Table(name="m_assess_detail")
public class MAssessDetail extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
	    private String detailId;
	    private String assessId;
	    private String matNo;
	    private BigDecimal planAmount;
	    private BigDecimal matPrice;
	    private BigDecimal backNorm;
	    private BigDecimal backAmount;
	    private BigDecimal backRatio;
	    private BigDecimal matAmount;
	    private BigDecimal overScale;
	    private BigDecimal accountScale;
	    private BigDecimal accountBala;
	    private String result;
	    private String remark;

	    private BigDecimal workValue;
	    private String assRemark;
	    private String matCode;
	    private String matName;
	    private String matUnit;
	    private String prjName;
	    private String prjRow;
	    private String assStatus;

	public String getAssStatus() {
		return assStatus;
	}

	public void setAssStatus(String assStatus) {
		this.assStatus = assStatus;
	}

	public String getPrjRow() {
		return prjRow;
	}

	public void setPrjRow(String prjRow) {
		this.prjRow = prjRow;
	}

	public String getPrjName() {
		return prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	public BigDecimal getWorkValue() {
		return workValue;
	}

	public void setWorkValue(BigDecimal workValue) {
		this.workValue = workValue;
	}

	public String getAssRemark() {
		return assRemark;
	}

	public void setAssRemark(String assRemark) {
		this.assRemark = assRemark;
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

	public BigDecimal getPlanAmount() {
		return planAmount;
	}

	public void setPlanAmount(BigDecimal planAmount) {
		this.planAmount = planAmount;
	}

	public BigDecimal getMatPrice() {
		return matPrice;
	}

	public void setMatPrice(BigDecimal matPrice) {
		this.matPrice = matPrice;
	}

	public BigDecimal getBackNorm() {
		return backNorm;
	}

	public void setBackNorm(BigDecimal backNorm) {
		this.backNorm = backNorm;
	}

	public BigDecimal getBackAmount() {
		return backAmount;
	}

	public void setBackAmount(BigDecimal backAmount) {
		this.backAmount = backAmount;
	}

	public BigDecimal getBackRatio() {
		return backRatio;
	}

	public void setBackRatio(BigDecimal backRatio) {
		this.backRatio = backRatio;
	}

	public BigDecimal getMatAmount() {
		return matAmount;
	}

	public void setMatAmount(BigDecimal matAmount) {
		this.matAmount = matAmount;
	}

	public BigDecimal getOverScale() {
		return overScale;
	}

	public void setOverScale(BigDecimal overScale) {
		this.overScale = overScale;
	}

	public BigDecimal getAccountScale() {
		return accountScale;
	}

	public void setAccountScale(BigDecimal accountScale) {
		this.accountScale = accountScale;
	}

	public BigDecimal getAccountBala() {
		return accountBala;
	}

	public void setAccountBala(BigDecimal accountBala) {
		this.accountBala = accountBala;
	}

	/**
	 * 设置：明细ID
	 */
	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	/**
	 * 获取：明细ID
	 */
	public String getDetailId() {
		return detailId;
	}
		
	/**
	 * 设置：考核ID
	 */
	public void setAssessId(String assessId) {
		this.assessId = assessId;
	}

	/**
	 * 获取：考核ID
	 */
	public String getAssessId() {
		return assessId;
	}
		
	/**
	 * 设置：材料内码
	 */
	public void setMatNo(String matNo) {
		this.matNo = matNo;
	}

	/**
	 * 获取：材料内码
	 */
	public String getMatNo() {
		return matNo;
	}
		

		
	/**
	 * 设置：考核结果
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * 获取：考核结果
	 */
	public String getResult() {
		return result;
	}
		
	/**
	 * 设置：备注信息
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取：备注信息
	 */
	public String getRemark() {
		return remark;
	}
	
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}