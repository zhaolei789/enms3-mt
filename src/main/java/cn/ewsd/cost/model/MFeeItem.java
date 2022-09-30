package cn.ewsd.cost.model;

import cn.ewsd.common.model.MCoreBase;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 合同
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-03 08:39:34
 */
@Table(name="m_fee_item")
public class MFeeItem extends MCoreBase{
	private static final long serialVersionUID = 1L;
	private String itemNo;
	private String itemName;
	private String itemType;
	private String itemUnit;
	private BigDecimal assPrice;
	private BigDecimal assRatio;
	private BigDecimal awardRatio;
	private BigDecimal punishRatio;
	private String priceType;
	private Integer mngDept;
	private Integer orderNo;
	private String ifUse;
	private String outType;
	private Integer teamNo;
	private String ifEnter;
	private String assCycle;
	private String ifMain;
	private String upItem;
	private String itemModel;

	private String prjNo;
	private String prjName;
	private BigDecimal normAmount;
	private BigDecimal normPrice;
	private String teamName;
	private String outTypeName;
	private String assCycleName;
	private String upItemName;

	public String getOutTypeName() {
		return outTypeName;
	}

	public void setOutTypeName(String outTypeName) {
		this.outTypeName = outTypeName;
	}

	public String getAssCycleName() {
		return assCycleName;
	}

	public void setAssCycleName(String assCycleName) {
		this.assCycleName = assCycleName;
	}

	public String getUpItemName() {
		return upItemName;
	}

	public void setUpItemName(String upItemName) {
		this.upItemName = upItemName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
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

	public BigDecimal getNormAmount() {
		return normAmount;
	}

	public void setNormAmount(BigDecimal normAmount) {
		this.normAmount = normAmount;
	}

	public BigDecimal getNormPrice() {
		return normPrice;
	}

	public void setNormPrice(BigDecimal normPrice) {
		this.normPrice = normPrice;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	public BigDecimal getAssPrice() {
		return assPrice;
	}

	public void setAssPrice(BigDecimal assPrice) {
		this.assPrice = assPrice;
	}

	public BigDecimal getAssRatio() {
		return assRatio;
	}

	public void setAssRatio(BigDecimal assRatio) {
		this.assRatio = assRatio;
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

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public Integer getMngDept() {
		return mngDept;
	}

	public void setMngDept(Integer mngDept) {
		this.mngDept = mngDept;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getIfUse() {
		return ifUse;
	}

	public void setIfUse(String ifUse) {
		this.ifUse = ifUse;
	}

	public String getOutType() {
		return outType;
	}

	public void setOutType(String outType) {
		this.outType = outType;
	}

	public Integer getTeamNo() {
		return teamNo;
	}

	public void setTeamNo(Integer teamNo) {
		this.teamNo = teamNo;
	}

	public String getIfEnter() {
		return ifEnter;
	}

	public void setIfEnter(String ifEnter) {
		this.ifEnter = ifEnter;
	}

	public String getAssCycle() {
		return assCycle;
	}

	public void setAssCycle(String assCycle) {
		this.assCycle = assCycle;
	}

	public String getIfMain() {
		return ifMain;
	}

	public void setIfMain(String ifMain) {
		this.ifMain = ifMain;
	}

	public String getUpItem() {
		return upItem;
	}

	public void setUpItem(String upItem) {
		this.upItem = upItem;
	}

	public String getItemModel() {
		return itemModel;
	}

	public void setItemModel(String itemModel) {
		this.itemModel = itemModel;
	}

	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}