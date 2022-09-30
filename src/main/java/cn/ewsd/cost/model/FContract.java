package cn.ewsd.cost.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import cn.ewsd.common.model.MCoreBase;
import javax.persistence.Table;

/**
 * 合同
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-03 08:39:34
 */
@Table(name="f_contract")
public class FContract extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
		// 合同ID
	    private String contractId;
				// 合同号
	    private String contractNo;
				// 合同名称
	    private String contractName;
				// 竞标类别
	    private String bidType;
				// 采办方式
	    private String buyType;
				// 合同金额
	    private BigDecimal contractBala;
				// 币种
	    private String currency;
				// 合同开始
	    private String beginDate;
				// 合同结束
	    private String endDate;
				// 对应合同
	    private String linkId;
				// 变更内容
	    private String modiInfo;
				// 录入部门
	    private String teamNo;
				// 录入日期
	    private String modiDate;

	    private String contractNameJp;

	    //查询参数
		private String teamNoName;


	public String getTeamNoName() {
		return teamNoName;
	}

	public void setTeamNoName(String teamNoName) {
		this.teamNoName = teamNoName;
	}

	public String getContractNameJp() {
		return contractNameJp;
	}

	public void setContractNameJp(String contractNameJp) {
		this.contractNameJp = contractNameJp;
	}

	/**
	 * 设置：合同ID
	 */
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	/**
	 * 获取：合同ID
	 */
	public String getContractId() {
		return contractId;
	}
		
	/**
	 * 设置：合同号
	 */
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	/**
	 * 获取：合同号
	 */
	public String getContractNo() {
		return contractNo;
	}
		
	/**
	 * 设置：合同名称
	 */
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	/**
	 * 获取：合同名称
	 */
	public String getContractName() {
		return contractName;
	}
		
	/**
	 * 设置：竞标类别
	 */
	public void setBidType(String bidType) {
		this.bidType = bidType;
	}

	/**
	 * 获取：竞标类别
	 */
	public String getBidType() {
		return bidType;
	}
		
	/**
	 * 设置：采办方式
	 */
	public void setBuyType(String buyType) {
		this.buyType = buyType;
	}

	/**
	 * 获取：采办方式
	 */
	public String getBuyType() {
		return buyType;
	}
		
	/**
	 * 设置：合同金额
	 */
	public void setContractBala(BigDecimal contractBala) {
		this.contractBala = contractBala;
	}

	/**
	 * 获取：合同金额
	 */
	public BigDecimal getContractBala() {
		return contractBala;
	}
		
	/**
	 * 设置：币种
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * 获取：币种
	 */
	public String getCurrency() {
		return currency;
	}
		
	/**
	 * 设置：合同开始
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * 获取：合同开始
	 */
	public String getBeginDate() {
		return beginDate;
	}
		
	/**
	 * 设置：合同结束
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * 获取：合同结束
	 */
	public String getEndDate() {
		return endDate;
	}
		
	/**
	 * 设置：对应合同
	 */
	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}

	/**
	 * 获取：对应合同
	 */
	public String getLinkId() {
		return linkId;
	}
		
	/**
	 * 设置：变更内容
	 */
	public void setModiInfo(String modiInfo) {
		this.modiInfo = modiInfo;
	}

	/**
	 * 获取：变更内容
	 */
	public String getModiInfo() {
		return modiInfo;
	}
		
	/**
	 * 设置：录入部门
	 */
	public void setTeamNo(String teamNo) {
		this.teamNo = teamNo;
	}

	/**
	 * 获取：录入部门
	 */
	public String getTeamNo() {
		return teamNo;
	}
		
	/**
	 * 设置：录入日期
	 */
	public void setModiDate(String modiDate) {
		this.modiDate = modiDate;
	}

	/**
	 * 获取：录入日期
	 */
	public String getModiDate() {
		return modiDate;
	}
									
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}