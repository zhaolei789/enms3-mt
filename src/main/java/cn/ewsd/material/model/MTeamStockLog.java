package cn.ewsd.material.model;

import cn.ewsd.common.model.MCoreBase;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 
 * 
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
@Table(name="m_team_stock_log")
public class MTeamStockLog extends MCoreBase{
	private static final long serialVersionUID = 1L;
	private String logId;
	private String storeNo;
	private Integer teamNo;
	private String matNo;
	private String occDate;
	private BigDecimal occAmount;
	private BigDecimal occBala;
	private String inoutFlag;
	private String dataSrc;
	private String billNo;
	private String matStatus;

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public Integer getTeamNo() {
		return teamNo;
	}

	public void setTeamNo(Integer teamNo) {
		this.teamNo = teamNo;
	}

	public String getMatNo() {
		return matNo;
	}

	public void setMatNo(String matNo) {
		this.matNo = matNo;
	}

	public String getOccDate() {
		return occDate;
	}

	public void setOccDate(String occDate) {
		this.occDate = occDate;
	}

	public BigDecimal getOccAmount() {
		return occAmount;
	}

	public void setOccAmount(BigDecimal occAmount) {
		this.occAmount = occAmount;
	}

	public BigDecimal getOccBala() {
		return occBala;
	}

	public void setOccBala(BigDecimal occBala) {
		this.occBala = occBala;
	}

	public String getInoutFlag() {
		return inoutFlag;
	}

	public void setInoutFlag(String inoutFlag) {
		this.inoutFlag = inoutFlag;
	}

	public String getDataSrc() {
		return dataSrc;
	}

	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getMatStatus() {
		return matStatus;
	}

	public void setMatStatus(String matStatus) {
		this.matStatus = matStatus;
	}
}