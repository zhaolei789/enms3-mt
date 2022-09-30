package cn.ewsd.cost.model;

import cn.ewsd.common.model.MCoreBase;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 工程结算
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-03 08:39:36
 */
@Table(name="m_assess")
public class MAssess extends MCoreBase{
	private static final long serialVersionUID = 1L;
	private String assessId;
	private String occMonth;
	private String assType;
	private Integer teamNo;
	private String itemNo;
	private String prjNo;
	private String prjName;
	private BigDecimal normBala;
	private BigDecimal lastStock;
	private BigDecimal inBala;
	private BigDecimal stockBala;
	private BigDecimal occBala;
	private BigDecimal addBala;
	private BigDecimal awardRatio;
	private BigDecimal punishRatio;
	private BigDecimal diffBala;
	private BigDecimal diffScale;
	private BigDecimal awardBala;
	private Integer assTeam;
	private String modiDate;
	private String modiEmp;
	private String remark;
	private String upItem;
	private Integer cnt;
	private String ifEnter;
	private String itemName;

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getIfEnter() {
		return ifEnter;
	}

	public void setIfEnter(String ifEnter) {
		this.ifEnter = ifEnter;
	}

	public Integer getCnt() {
		return cnt;
	}

	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}

	public String getAssessId() {
		return assessId;
	}

	public void setAssessId(String assessId) {
		this.assessId = assessId;
	}

	public String getOccMonth() {
		return occMonth;
	}

	public void setOccMonth(String occMonth) {
		this.occMonth = occMonth;
	}

	public String getAssType() {
		return assType;
	}

	public void setAssType(String assType) {
		this.assType = assType;
	}

	public Integer getTeamNo() {
		return teamNo;
	}

	public void setTeamNo(Integer teamNo) {
		this.teamNo = teamNo;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getPrjNo() {
		return prjNo;
	}

	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
	}

	public String getPrjName() {
		return prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	public BigDecimal getNormBala() {
		return normBala;
	}

	public void setNormBala(BigDecimal normBala) {
		this.normBala = normBala;
	}

	public BigDecimal getLastStock() {
		return lastStock;
	}

	public void setLastStock(BigDecimal lastStock) {
		this.lastStock = lastStock;
	}

	public BigDecimal getInBala() {
		return inBala;
	}

	public void setInBala(BigDecimal inBala) {
		this.inBala = inBala;
	}

	public BigDecimal getStockBala() {
		return stockBala;
	}

	public void setStockBala(BigDecimal stockBala) {
		this.stockBala = stockBala;
	}

	public BigDecimal getOccBala() {
		return occBala;
	}

	public void setOccBala(BigDecimal occBala) {
		this.occBala = occBala;
	}

	public BigDecimal getAddBala() {
		return addBala;
	}

	public void setAddBala(BigDecimal addBala) {
		this.addBala = addBala;
	}

	public BigDecimal getAwardRatio() {
		return awardRatio;
	}

	public void setAwardRatio(BigDecimal awardRatio) {
		this.awardRatio = awardRatio;
	}

	public BigDecimal getPunishRatio() {
		return punishRatio;
	}

	public void setPunishRatio(BigDecimal punishRatio) {
		this.punishRatio = punishRatio;
	}

	public BigDecimal getDiffBala() {
		return diffBala;
	}

	public void setDiffBala(BigDecimal diffBala) {
		this.diffBala = diffBala;
	}

	public BigDecimal getDiffScale() {
		return diffScale;
	}

	public void setDiffScale(BigDecimal diffScale) {
		this.diffScale = diffScale;
	}

	public BigDecimal getAwardBala() {
		return awardBala;
	}

	public void setAwardBala(BigDecimal awardBala) {
		this.awardBala = awardBala;
	}

	public Integer getAssTeam() {
		return assTeam;
	}

	public void setAssTeam(Integer assTeam) {
		this.assTeam = assTeam;
	}

	public String getModiDate() {
		return modiDate;
	}

	public void setModiDate(String modiDate) {
		this.modiDate = modiDate;
	}

	public String getModiEmp() {
		return modiEmp;
	}

	public void setModiEmp(String modiEmp) {
		this.modiEmp = modiEmp;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUpItem() {
		return upItem;
	}

	public void setUpItem(String upItem) {
		this.upItem = upItem;
	}

	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}