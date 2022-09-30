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
@Table(name="s_settle_item")
public class MSettleItem extends MCoreBase{
	private static final long serialVersionUID = 1L;
	private String itemNo;
	private String itemName;
	private String itemUnit;
	private String indexCode;
	private String indexName;
	private String dataType;
	private String dictKey;
	private String itemHint;
	private String itemType;
	private String ifUsing;
	private String getWay;
	private String crType;
	private String tableCode;
	private Integer sort;
	private String marketType;
	private Integer linkTeam;
	private String sumItem;
	private String ifEnd;
	private Integer colCount;
	private String reserve1;
	private String reserve2;
	private String reserve3;
	private String reserve4;
	private String reserve5;

	private String dataId;
	private String valueDataType;
	private String occMonth;
	private BigDecimal occValue;
	private Integer teamNo;
	private String occDay;

	public String getOccDay() {
		return occDay;
	}

	public void setOccDay(String occDay) {
		this.occDay = occDay;
	}

	public Integer getTeamNo() {
		return teamNo;
	}

	public void setTeamNo(Integer teamNo) {
		this.teamNo = teamNo;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getValueDataType() {
		return valueDataType;
	}

	public void setValueDataType(String valueDataType) {
		this.valueDataType = valueDataType;
	}

	public String getOccMonth() {
		return occMonth;
	}

	public void setOccMonth(String occMonth) {
		this.occMonth = occMonth;
	}

	public BigDecimal getOccValue() {
		return occValue;
	}

	public void setOccValue(BigDecimal occValue) {
		this.occValue = occValue;
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

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	public String getIndexCode() {
		return indexCode;
	}

	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDictKey() {
		return dictKey;
	}

	public void setDictKey(String dictKey) {
		this.dictKey = dictKey;
	}

	public String getItemHint() {
		return itemHint;
	}

	public void setItemHint(String itemHint) {
		this.itemHint = itemHint;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getIfUsing() {
		return ifUsing;
	}

	public void setIfUsing(String ifUsing) {
		this.ifUsing = ifUsing;
	}

	public String getGetWay() {
		return getWay;
	}

	public void setGetWay(String getWay) {
		this.getWay = getWay;
	}

	public String getCrType() {
		return crType;
	}

	public void setCrType(String crType) {
		this.crType = crType;
	}

	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getMarketType() {
		return marketType;
	}

	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}

	public Integer getLinkTeam() {
		return linkTeam;
	}

	public void setLinkTeam(Integer linkTeam) {
		this.linkTeam = linkTeam;
	}

	public String getSumItem() {
		return sumItem;
	}

	public void setSumItem(String sumItem) {
		this.sumItem = sumItem;
	}

	public String getIfEnd() {
		return ifEnd;
	}

	public void setIfEnd(String ifEnd) {
		this.ifEnd = ifEnd;
	}

	public Integer getColCount() {
		return colCount;
	}

	public void setColCount(Integer colCount) {
		this.colCount = colCount;
	}

	public String getReserve1() {
		return reserve1;
	}

	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}

	public String getReserve2() {
		return reserve2;
	}

	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}

	public String getReserve3() {
		return reserve3;
	}

	public void setReserve3(String reserve3) {
		this.reserve3 = reserve3;
	}

	public String getReserve4() {
		return reserve4;
	}

	public void setReserve4(String reserve4) {
		this.reserve4 = reserve4;
	}

	public String getReserve5() {
		return reserve5;
	}

	public void setReserve5(String reserve5) {
		this.reserve5 = reserve5;
	}
}