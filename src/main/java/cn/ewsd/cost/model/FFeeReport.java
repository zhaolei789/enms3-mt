package cn.ewsd.cost.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import cn.ewsd.common.model.MCoreBase;
import javax.persistence.Table;

/**
 * 费用上报
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-09 10:31:35
 */
@Table(name="f_fee_report")
public class FFeeReport extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
		// 标识ID
	    private String reportId;
				// 部门
	    private String teamNo;
				// 费用月份
	    private String fMonth;
				// 项目ID
	    private String itemId;
				// 项目名称
	    private String itemName;
				// 领导ID
	    private String leader;
				// 费用ID
	    private String feeId;
				// 数据项目
	    private String dataItem;
				// 上报值
	    private BigDecimal occBala;
				// 上报日期
	    private String modiDate;
				// 上报人员
	    private String modiEmp;
				// 上报部门
	    private String modiTeam;
										
	
	/**
	 * 设置：标识ID
	 */
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	/**
	 * 获取：标识ID
	 */
	public String getReportId() {
		return reportId;
	}
		
	/**
	 * 设置：部门
	 */
	public void setTeamNo(String teamNo) {
		this.teamNo = teamNo;
	}

	/**
	 * 获取：部门
	 */
	public String getTeamNo() {
		return teamNo;
	}
		
	/**
	 * 设置：费用月份
	 */
	public void setFMonth(String fMonth) {
		this.fMonth = fMonth;
	}

	/**
	 * 获取：费用月份
	 */
	public String getFMonth() {
		return fMonth;
	}
		
	/**
	 * 设置：项目ID
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/**
	 * 获取：项目ID
	 */
	public String getItemId() {
		return itemId;
	}
		
	/**
	 * 设置：项目名称
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * 获取：项目名称
	 */
	public String getItemName() {
		return itemName;
	}
		
	/**
	 * 设置：领导ID
	 */
	public void setLeader(String leader) {
		this.leader = leader;
	}

	/**
	 * 获取：领导ID
	 */
	public String getLeader() {
		return leader;
	}
		
	/**
	 * 设置：费用ID
	 */
	public void setFeeId(String feeId) {
		this.feeId = feeId;
	}

	/**
	 * 获取：费用ID
	 */
	public String getFeeId() {
		return feeId;
	}
		
	/**
	 * 设置：数据项目
	 */
	public void setDataItem(String dataItem) {
		this.dataItem = dataItem;
	}

	/**
	 * 获取：数据项目
	 */
	public String getDataItem() {
		return dataItem;
	}
		
	/**
	 * 设置：上报值
	 */
	public void setOccBala(BigDecimal occBala) {
		this.occBala = occBala;
	}

	/**
	 * 获取：上报值
	 */
	public BigDecimal getOccBala() {
		return occBala;
	}
		
	/**
	 * 设置：上报日期
	 */
	public void setModiDate(String modiDate) {
		this.modiDate = modiDate;
	}

	/**
	 * 获取：上报日期
	 */
	public String getModiDate() {
		return modiDate;
	}
		
	/**
	 * 设置：上报人员
	 */
	public void setModiEmp(String modiEmp) {
		this.modiEmp = modiEmp;
	}

	/**
	 * 获取：上报人员
	 */
	public String getModiEmp() {
		return modiEmp;
	}
		
	/**
	 * 设置：上报部门
	 */
	public void setModiTeam(String modiTeam) {
		this.modiTeam = modiTeam;
	}

	/**
	 * 获取：上报部门
	 */
	public String getModiTeam() {
		return modiTeam;
	}
									
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}