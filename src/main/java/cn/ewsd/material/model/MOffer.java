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
@Table(name="m_offer")
public class MOffer extends MCoreBase{
	private static final long serialVersionUID = 1L;
	private String offerNo;
	private String offerName;
	private String shortName;
	private String linkMan;
	private String contactWay;
	private String offerAddr;
	private String certInfo;

	public String getOfferNo() {
		return offerNo;
	}

	public void setOfferNo(String offerNo) {
		this.offerNo = offerNo;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getContactWay() {
		return contactWay;
	}

	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}

	public String getOfferAddr() {
		return offerAddr;
	}

	public void setOfferAddr(String offerAddr) {
		this.offerAddr = offerAddr;
	}

	public String getCertInfo() {
		return certInfo;
	}

	public void setCertInfo(String certInfo) {
		this.certInfo = certInfo;
	}
}