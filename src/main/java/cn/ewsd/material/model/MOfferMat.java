package cn.ewsd.material.model;

import cn.ewsd.common.model.MCoreBase;

import javax.persistence.Table;

/**
 * 
 * 
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
@Table(name="m_offer_mat")
public class MOfferMat extends MCoreBase{
	private static final long serialVersionUID = 1L;
	private String offerNo;
	private Long matNo;
	private String occDate;

	public String getOfferNo() {
		return offerNo;
	}

	public void setOfferNo(String offerNo) {
		this.offerNo = offerNo;
	}

	public Long getMatNo() {
		return matNo;
	}

	public void setMatNo(Long matNo) {
		this.matNo = matNo;
	}

	public String getOccDate() {
		return occDate;
	}

	public void setOccDate(String occDate) {
		this.occDate = occDate;
	}
}