package cn.ewsd.material.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import cn.ewsd.common.model.MCoreBase;
import javax.persistence.Table;

/**
 * 
 * 
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-21 16:55:52
 */
@Table(name="m_item")
public class MItem extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
		// 
	    private String itemNo;
				// 
	    private String itemName;
				// 
	    private BigDecimal warnLine;
				// 
	    private String payType;
				// 
	    private Integer payTeam;
				// 
	    private String ifOn;
				// 
	    private String itemType;
				// 
	    private String itemUse;
				// 
	    private BigDecimal bmcbjsCoe;
				// 
	    private BigDecimal bmfjkhCoe;
				// 
	    private Integer orderNo;
										
		private String payTeamName;
		private String itemUseName;
		private String payTypeName;

	public String getPayTypeName() {
		return payTypeName;
	}

	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}

	public String getItemUseName() {
		return itemUseName;
	}

	public void setItemUseName(String itemUseName) {
		this.itemUseName = itemUseName;
	}

	public String getPayTeamName() {
		return payTeamName;
	}

	public void setPayTeamName(String payTeamName) {
		this.payTeamName = payTeamName;
	}

	/**
	 * 设置：
	 */
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	/**
	 * 获取：
	 */
	public String getItemNo() {
		return itemNo;
	}
		
	/**
	 * 设置：
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * 获取：
	 */
	public String getItemName() {
		return itemName;
	}
		
	/**
	 * 设置：
	 */
	public void setWarnLine(BigDecimal warnLine) {
		this.warnLine = warnLine;
	}

	/**
	 * 获取：
	 */
	public BigDecimal getWarnLine() {
		return warnLine;
	}
		
	/**
	 * 设置：
	 */
	public void setPayType(String payType) {
		this.payType = payType;
	}

	/**
	 * 获取：
	 */
	public String getPayType() {
		return payType;
	}
		
	/**
	 * 设置：
	 */
	public void setPayTeam(Integer payTeam) {
		this.payTeam = payTeam;
	}

	/**
	 * 获取：
	 */
	public Integer getPayTeam() {
		return payTeam;
	}
		
	/**
	 * 设置：
	 */
	public void setIfOn(String ifOn) {
		this.ifOn = ifOn;
	}

	/**
	 * 获取：
	 */
	public String getIfOn() {
		return ifOn;
	}
		
	/**
	 * 设置：
	 */
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	/**
	 * 获取：
	 */
	public String getItemType() {
		return itemType;
	}
		
	/**
	 * 设置：
	 */
	public void setItemUse(String itemUse) {
		this.itemUse = itemUse;
	}

	/**
	 * 获取：
	 */
	public String getItemUse() {
		return itemUse;
	}
		
	/**
	 * 设置：
	 */
	public void setBmcbjsCoe(BigDecimal bmcbjsCoe) {
		this.bmcbjsCoe = bmcbjsCoe;
	}

	/**
	 * 获取：
	 */
	public BigDecimal getBmcbjsCoe() {
		return bmcbjsCoe;
	}
		
	/**
	 * 设置：
	 */
	public void setBmfjkhCoe(BigDecimal bmfjkhCoe) {
		this.bmfjkhCoe = bmfjkhCoe;
	}

	/**
	 * 获取：
	 */
	public BigDecimal getBmfjkhCoe() {
		return bmfjkhCoe;
	}
		
	/**
	 * 设置：
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * 获取：
	 */
	public Integer getOrderNo() {
		return orderNo;
	}
									
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}