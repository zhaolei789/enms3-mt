package cn.ewsd.cost.model;

import cn.ewsd.common.model.MCoreBase;
import io.swagger.models.auth.In;
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
@Table(name="m_norm_fee")
public class MNormFee extends MCoreBase{
	private static final long serialVersionUID = 1L;
	private String assessId;
	private String occMonth;
	private String assType;
	private Integer teamNo;
	private String itemNo;
	private String prjNo;
	private String prjName;
	private BigDecimal assPrice;
	private BigDecimal normAmount;
	private BigDecimal assRate;
	private BigDecimal normBala;
	private String upItem;
	private String resultId;

	private BigDecimal occBala;
	private BigDecimal diffScale;
	private String outType;
	private BigDecimal diffBala;
	private String itemName;
	private String ifEnter;
	private String partitionBy;
	private Integer frequ;
	private String assPriceText;
	private String normAmountText;
	private String normBalaText;
	private String normBalaAssText;
	private String lastStockText;
	private String inBalaText;
	private String stockBalaText;
	private String addBalaText;
	private String occBalaText;
	private String diffBalaText;
	private String remark;
	private Integer cnt;
	private String teamName;
	private String occDigText;
	private Integer rowSpan;
	private Integer orderNo;
	private String itemUnit;

	public BigDecimal getDiffBala() {
		return diffBala;
	}

	public void setDiffBala(BigDecimal diffBala) {
		this.diffBala = diffBala;
	}

	public BigDecimal getOccBala() {
		return occBala;
	}

	public void setOccBala(BigDecimal occBala) {
		this.occBala = occBala;
	}

	public BigDecimal getDiffScale() {
		return diffScale;
	}

	public void setDiffScale(BigDecimal diffScale) {
		this.diffScale = diffScale;
	}

	public String getOutType() {
		return outType;
	}

	public void setOutType(String outType) {
		this.outType = outType;
	}

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getRowSpan() {
		return rowSpan;
	}

	public void setRowSpan(Integer rowSpan) {
		this.rowSpan = rowSpan;
	}

	public String getOccDigText() {
		return occDigText;
	}

	public void setOccDigText(String occDigText) {
		this.occDigText = occDigText;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Integer getCnt() {
		return cnt;
	}

	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}

	public String getLastStockText() {
		return lastStockText;
	}

	public void setLastStockText(String lastStockText) {
		this.lastStockText = lastStockText;
	}

	public String getInBalaText() {
		return inBalaText;
	}

	public void setInBalaText(String inBalaText) {
		this.inBalaText = inBalaText;
	}

	public String getStockBalaText() {
		return stockBalaText;
	}

	public void setStockBalaText(String stockBalaText) {
		this.stockBalaText = stockBalaText;
	}

	public String getAddBalaText() {
		return addBalaText;
	}

	public void setAddBalaText(String addBalaText) {
		this.addBalaText = addBalaText;
	}

	public String getOccBalaText() {
		return occBalaText;
	}

	public void setOccBalaText(String occBalaText) {
		this.occBalaText = occBalaText;
	}

	public String getDiffBalaText() {
		return diffBalaText;
	}

	public void setDiffBalaText(String diffBalaText) {
		this.diffBalaText = diffBalaText;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPartitionBy() {
		return partitionBy;
	}

	public void setPartitionBy(String partitionBy) {
		this.partitionBy = partitionBy;
	}

	public Integer getFrequ() {
		return frequ;
	}

	public void setFrequ(Integer frequ) {
		this.frequ = frequ;
	}

	public String getAssPriceText() {
		return assPriceText;
	}

	public void setAssPriceText(String assPriceText) {
		this.assPriceText = assPriceText;
	}

	public String getNormAmountText() {
		return normAmountText;
	}

	public void setNormAmountText(String normAmountText) {
		this.normAmountText = normAmountText;
	}

	public String getNormBalaText() {
		return normBalaText;
	}

	public void setNormBalaText(String normBalaText) {
		this.normBalaText = normBalaText;
	}

	public String getNormBalaAssText() {
		return normBalaAssText;
	}

	public void setNormBalaAssText(String normBalaAssText) {
		this.normBalaAssText = normBalaAssText;
	}

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

	public BigDecimal getAssPrice() {
		return assPrice;
	}

	public void setAssPrice(BigDecimal assPrice) {
		this.assPrice = assPrice;
	}

	public BigDecimal getNormAmount() {
		return normAmount;
	}

	public void setNormAmount(BigDecimal normAmount) {
		this.normAmount = normAmount;
	}

	public BigDecimal getAssRate() {
		return assRate;
	}

	public void setAssRate(BigDecimal assRate) {
		this.assRate = assRate;
	}

	public BigDecimal getNormBala() {
		return normBala;
	}

	public void setNormBala(BigDecimal normBala) {
		this.normBala = normBala;
	}

	public String getUpItem() {
		return upItem;
	}

	public void setUpItem(String upItem) {
		this.upItem = upItem;
	}

	public String getResultId() {
		return resultId;
	}

	public void setResultId(String resultId) {
		this.resultId = resultId;
	}

	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}