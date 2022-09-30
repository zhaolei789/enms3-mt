package cn.ewsd.cost.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import cn.ewsd.common.model.MCoreBase;
import javax.persistence.Table;

/**
 * 生产
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-13 18:44:03
 */
@Table(name="m_work")
public class MWork extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
		// 标识ID
	    private Long workId;
				// 工程编号
	    private String prjNo;
				// 工作项目
	    private String workPrj;
				// 生产月份
	    private String workMonth;
				// 区队编号
	    private String teamNo;
				// 计划原煤
	    private BigDecimal planRawout;
				// 计划商品煤
	    private BigDecimal planSaleout;
				// 计划进尺
	    private BigDecimal planDig;
				// 实际原煤
	    private BigDecimal occRawout;
				// 实际商品煤
	    private BigDecimal occSaleout;
				// 实际进尺
	    private BigDecimal occDig;
				// 上报产量
	    private BigDecimal reportOut;
				// 备注信息
	    private String remark;

	    //查询参数
	private String teamNoName;


	public String getTeamNoName() {
		return teamNoName;
	}

	public void setTeamNoName(String teamNoName) {
		this.teamNoName = teamNoName;
	}

	/**
	 * 设置：标识ID
	 */
	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	/**
	 * 获取：标识ID
	 */
	public Long getWorkId() {
		return workId;
	}
		
	/**
	 * 设置：工程编号
	 */
	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
	}

	/**
	 * 获取：工程编号
	 */
	public String getPrjNo() {
		return prjNo;
	}
		
	/**
	 * 设置：工作项目
	 */
	public void setWorkPrj(String workPrj) {
		this.workPrj = workPrj;
	}

	/**
	 * 获取：工作项目
	 */
	public String getWorkPrj() {
		return workPrj;
	}
		
	/**
	 * 设置：生产月份
	 */
	public void setWorkMonth(String workMonth) {
		this.workMonth = workMonth;
	}

	/**
	 * 获取：生产月份
	 */
	public String getWorkMonth() {
		return workMonth;
	}
		
	/**
	 * 设置：区队编号
	 */
	public void setTeamNo(String teamNo) {
		this.teamNo = teamNo;
	}

	/**
	 * 获取：区队编号
	 */
	public String getTeamNo() {
		return teamNo;
	}
		
	/**
	 * 设置：计划原煤
	 */
	public void setPlanRawout(BigDecimal planRawout) {
		this.planRawout = planRawout;
	}

	/**
	 * 获取：计划原煤
	 */
	public BigDecimal getPlanRawout() {
		return planRawout;
	}
		
	/**
	 * 设置：计划商品煤
	 */
	public void setPlanSaleout(BigDecimal planSaleout) {
		this.planSaleout = planSaleout;
	}

	/**
	 * 获取：计划商品煤
	 */
	public BigDecimal getPlanSaleout() {
		return planSaleout;
	}
		
	/**
	 * 设置：计划进尺
	 */
	public void setPlanDig(BigDecimal planDig) {
		this.planDig = planDig;
	}

	/**
	 * 获取：计划进尺
	 */
	public BigDecimal getPlanDig() {
		return planDig;
	}
		
	/**
	 * 设置：实际原煤
	 */
	public void setOccRawout(BigDecimal occRawout) {
		this.occRawout = occRawout;
	}

	/**
	 * 获取：实际原煤
	 */
	public BigDecimal getOccRawout() {
		return occRawout;
	}
		
	/**
	 * 设置：实际商品煤
	 */
	public void setOccSaleout(BigDecimal occSaleout) {
		this.occSaleout = occSaleout;
	}

	/**
	 * 获取：实际商品煤
	 */
	public BigDecimal getOccSaleout() {
		return occSaleout;
	}
		
	/**
	 * 设置：实际进尺
	 */
	public void setOccDig(BigDecimal occDig) {
		this.occDig = occDig;
	}

	/**
	 * 获取：实际进尺
	 */
	public BigDecimal getOccDig() {
		return occDig;
	}
		
	/**
	 * 设置：上报产量
	 */
	public void setReportOut(BigDecimal reportOut) {
		this.reportOut = reportOut;
	}

	/**
	 * 获取：上报产量
	 */
	public BigDecimal getReportOut() {
		return reportOut;
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