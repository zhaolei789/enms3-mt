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
@Table(name="m_prj_item")
public class MPrjItem extends MCoreBase{
	private static final long serialVersionUID = 1L;
	private String prjNo;
	private String itemNo;
	private String itemName;
	private String payType;
	private String payTypeName;
	private BigDecimal matBala;
	private String isChk;

	public String getIsChk() {
		return isChk;
	}

	public void setIsChk(String isChk) {
		this.isChk = isChk;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayTypeName() {
		return payTypeName;
	}

	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}

	public String getPrjNo() {
		return prjNo;
	}

	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public BigDecimal getMatBala() {
		return matBala;
	}

	public void setMatBala(BigDecimal matBala) {
		this.matBala = matBala;
	}
}