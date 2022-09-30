package cn.ewsd.material.model;

import cn.ewsd.common.model.MCoreBase;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 
 * 
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-21 16:55:52
 */
@Table(name="m_account")
public class MAccount extends MCoreBase{
	private static final long serialVersionUID = 1L;
	private String occMonth;
	private String teamNo;
	private BigDecimal awardBala;
	private BigDecimal keepBala;
	private BigDecimal delayBala;
	private BigDecimal monthBala;
	private String remark;

	public String getOccMonth() {
		return occMonth;
	}

	public void setOccMonth(String occMonth) {
		this.occMonth = occMonth;
	}

	public String getTeamNo() {
		return teamNo;
	}

	public void setTeamNo(String teamNo) {
		this.teamNo = teamNo;
	}

	public BigDecimal getAwardBala() {
		return awardBala;
	}

	public void setAwardBala(BigDecimal awardBala) {
		this.awardBala = awardBala;
	}

	public BigDecimal getKeepBala() {
		return keepBala;
	}

	public void setKeepBala(BigDecimal keepBala) {
		this.keepBala = keepBala;
	}

	public BigDecimal getDelayBala() {
		return delayBala;
	}

	public void setDelayBala(BigDecimal delayBala) {
		this.delayBala = delayBala;
	}

	public BigDecimal getMonthBala() {
		return monthBala;
	}

	public void setMonthBala(BigDecimal monthBala) {
		this.monthBala = monthBala;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}