package cn.ewsd.cost.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import cn.ewsd.common.model.MCoreBase;
import javax.persistence.Table;

/**
 * 费用项目
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-07 09:22:21
 */
@Table(name="f_item")
public class FItem extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
		// 科目ID
	    private Long itemId;
				// 科目名称
	    private String itemName;
				// 预警基线
	    private BigDecimal warnLine;
				// 提取方式;1部门手动录入  2根据关联物料  3根据仓库领料  4根据部门领料  5部门剩余领料
	    private String getType;
				// 提取顺序;单位内材料费提取顺序，先按物料，再按仓库，剩余费用
	    private Integer getSort;
				// 是否启用;0-否  1-是
	    private Integer ifOn;
				// 关联仓库
	    private String linkStore;
				// 使用分母;1本单位  2全公司  3时间
	    private String denomType;
				// 排序编号
	    private Integer orderNo;
				// 考核周期;1年度  2半年  4季度  12月度
	    private String assPeriod;

	    private String itemType;


	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	/**
	 * 设置：科目ID
	 */
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	/**
	 * 获取：科目ID
	 */
	public Long getItemId() {
		return itemId;
	}
		
	/**
	 * 设置：科目名称
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * 获取：科目名称
	 */
	public String getItemName() {
		return itemName;
	}
		
	/**
	 * 设置：预警基线
	 */
	public void setWarnLine(BigDecimal warnLine) {
		this.warnLine = warnLine;
	}

	/**
	 * 获取：预警基线
	 */
	public BigDecimal getWarnLine() {
		return warnLine;
	}
		
	/**
	 * 设置：提取方式;1部门手动录入  2根据关联物料  3根据仓库领料  4根据部门领料  5部门剩余领料
	 */
	public void setGetType(String getType) {
		this.getType = getType;
	}

	/**
	 * 获取：提取方式;1部门手动录入  2根据关联物料  3根据仓库领料  4根据部门领料  5部门剩余领料
	 */
	public String getGetType() {
		return getType;
	}
		
	/**
	 * 设置：提取顺序;单位内材料费提取顺序，先按物料，再按仓库，剩余费用
	 */
	public void setGetSort(Integer getSort) {
		this.getSort = getSort;
	}

	/**
	 * 获取：提取顺序;单位内材料费提取顺序，先按物料，再按仓库，剩余费用
	 */
	public Integer getGetSort() {
		return getSort;
	}
		
	/**
	 * 设置：是否启用;0-否  1-是
	 */
	public void setIfOn(Integer ifOn) {
		this.ifOn = ifOn;
	}

	/**
	 * 获取：是否启用;0-否  1-是
	 */
	public Integer getIfOn() {
		return ifOn;
	}
		
	/**
	 * 设置：关联仓库
	 */
	public void setLinkStore(String linkStore) {
		this.linkStore = linkStore;
	}

	/**
	 * 获取：关联仓库
	 */
	public String getLinkStore() {
		return linkStore;
	}
		
	/**
	 * 设置：使用分母;1本单位  2全公司  3时间
	 */
	public void setDenomType(String denomType) {
		this.denomType = denomType;
	}

	/**
	 * 获取：使用分母;1本单位  2全公司  3时间
	 */
	public String getDenomType() {
		return denomType;
	}
		
	/**
	 * 设置：排序编号
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * 获取：排序编号
	 */
	public Integer getOrderNo() {
		return orderNo;
	}
		
	/**
	 * 设置：考核周期;1年度  2半年  4季度  12月度
	 */
	public void setAssPeriod(String assPeriod) {
		this.assPeriod = assPeriod;
	}

	/**
	 * 获取：考核周期;1年度  2半年  4季度  12月度
	 */
	public String getAssPeriod() {
		return assPeriod;
	}
									
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}