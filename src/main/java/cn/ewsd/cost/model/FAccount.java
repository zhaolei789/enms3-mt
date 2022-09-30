package cn.ewsd.cost.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import cn.ewsd.common.model.MCoreBase;
import javax.persistence.Table;

/**
 * 工程结算
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-03 08:39:36
 */
@Table(name="f_account")
public class FAccount extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
		// 标识ID
	    private String accountId;
				// 结算月份
	    private String accountMonth;
				// 管理科室
	    private String mngTeam;
				// 挂账单位
	    private String creditTeam;
				// 施工单位
	    private String workTeam;
				// 施工地点
	    private String workAddr;
				// 工作类型
	    private String workType;
				// 工作量
	    private BigDecimal workload;
				// 结算起始日
	    private String accountDate1;
				// 结算截至日
	    private String accountDate2;
				// 挂账日期
	    private String creditDate;
				// 核算单位
	    private String workUnit;
				// 财务报账单号
	    private String accountNo;
				// 资金性质
	    private String fundType;
				// 结算性质
	    private String accountType;
				// 结算金额
	    private BigDecimal accountBala;
				// 合同约定付款
	    private BigDecimal payScale;
				// 预留款
	    private BigDecimal keepBala;
				// 质保金
	    private BigDecimal retenBala;
				// 扣款金额
	    private BigDecimal cutBala;
				// 合同ID
	    private String contractId;
				// 累计支付金额
	    private BigDecimal sumPay;
				// 合同节超情况
	    private String contractDiff;
				// 备注
	    private String remark;
				// 审核日期
	    private String checkDate;
				// 审核人员
	    private String checkUser;
				// 上报日期
	    private String modiDate;
				// 上报人员
	    private String modiUser;
				// 审核步骤
	    private String status;
				// 日志编号
	    private String checkNo;

	    //查询参数
		private String creditTeamName;
		private String workTeamName;

	public String getCreditTeamName() {
		return creditTeamName;
	}

	public void setCreditTeamName(String creditTeamName) {
		this.creditTeamName = creditTeamName;
	}

	public String getWorkTeamName() {
		return workTeamName;
	}

	public void setWorkTeamName(String workTeamName) {
		this.workTeamName = workTeamName;
	}

	/**
	 * 设置：标识ID
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	/**
	 * 获取：标识ID
	 */
	public String getAccountId() {
		return accountId;
	}
		
	/**
	 * 设置：结算月份
	 */
	public void setAccountMonth(String accountMonth) {
		this.accountMonth = accountMonth;
	}

	/**
	 * 获取：结算月份
	 */
	public String getAccountMonth() {
		return accountMonth;
	}
		
	/**
	 * 设置：管理科室
	 */
	public void setMngTeam(String mngTeam) {
		this.mngTeam = mngTeam;
	}

	/**
	 * 获取：管理科室
	 */
	public String getMngTeam() {
		return mngTeam;
	}
		
	/**
	 * 设置：挂账单位
	 */
	public void setCreditTeam(String creditTeam) {
		this.creditTeam = creditTeam;
	}

	/**
	 * 获取：挂账单位
	 */
	public String getCreditTeam() {
		return creditTeam;
	}
		
	/**
	 * 设置：施工单位
	 */
	public void setWorkTeam(String workTeam) {
		this.workTeam = workTeam;
	}

	/**
	 * 获取：施工单位
	 */
	public String getWorkTeam() {
		return workTeam;
	}
		
	/**
	 * 设置：施工地点
	 */
	public void setWorkAddr(String workAddr) {
		this.workAddr = workAddr;
	}

	/**
	 * 获取：施工地点
	 */
	public String getWorkAddr() {
		return workAddr;
	}
		
	/**
	 * 设置：工作类型
	 */
	public void setWorkType(String workType) {
		this.workType = workType;
	}

	/**
	 * 获取：工作类型
	 */
	public String getWorkType() {
		return workType;
	}
		
	/**
	 * 设置：工作量
	 */
	public void setWorkload(BigDecimal workload) {
		this.workload = workload;
	}

	/**
	 * 获取：工作量
	 */
	public BigDecimal getWorkload() {
		return workload;
	}
		
	/**
	 * 设置：结算起始日
	 */
	public void setAccountDate1(String accountDate1) {
		this.accountDate1 = accountDate1;
	}

	/**
	 * 获取：结算起始日
	 */
	public String getAccountDate1() {
		return accountDate1;
	}
		
	/**
	 * 设置：结算截至日
	 */
	public void setAccountDate2(String accountDate2) {
		this.accountDate2 = accountDate2;
	}

	/**
	 * 获取：结算截至日
	 */
	public String getAccountDate2() {
		return accountDate2;
	}
		
	/**
	 * 设置：挂账日期
	 */
	public void setCreditDate(String creditDate) {
		this.creditDate = creditDate;
	}

	/**
	 * 获取：挂账日期
	 */
	public String getCreditDate() {
		return creditDate;
	}
		
	/**
	 * 设置：核算单位
	 */
	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	/**
	 * 获取：核算单位
	 */
	public String getWorkUnit() {
		return workUnit;
	}
		
	/**
	 * 设置：财务报账单号
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * 获取：财务报账单号
	 */
	public String getAccountNo() {
		return accountNo;
	}
		
	/**
	 * 设置：资金性质
	 */
	public void setFundType(String fundType) {
		this.fundType = fundType;
	}

	/**
	 * 获取：资金性质
	 */
	public String getFundType() {
		return fundType;
	}
		
	/**
	 * 设置：结算性质
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * 获取：结算性质
	 */
	public String getAccountType() {
		return accountType;
	}
		
	/**
	 * 设置：结算金额
	 */
	public void setAccountBala(BigDecimal accountBala) {
		this.accountBala = accountBala;
	}

	/**
	 * 获取：结算金额
	 */
	public BigDecimal getAccountBala() {
		return accountBala;
	}
		
	/**
	 * 设置：合同约定付款
	 */
	public void setPayScale(BigDecimal payScale) {
		this.payScale = payScale;
	}

	/**
	 * 获取：合同约定付款
	 */
	public BigDecimal getPayScale() {
		return payScale;
	}
		
	/**
	 * 设置：预留款
	 */
	public void setKeepBala(BigDecimal keepBala) {
		this.keepBala = keepBala;
	}

	/**
	 * 获取：预留款
	 */
	public BigDecimal getKeepBala() {
		return keepBala;
	}
		
	/**
	 * 设置：质保金
	 */
	public void setRetenBala(BigDecimal retenBala) {
		this.retenBala = retenBala;
	}

	/**
	 * 获取：质保金
	 */
	public BigDecimal getRetenBala() {
		return retenBala;
	}
		
	/**
	 * 设置：扣款金额
	 */
	public void setCutBala(BigDecimal cutBala) {
		this.cutBala = cutBala;
	}

	/**
	 * 获取：扣款金额
	 */
	public BigDecimal getCutBala() {
		return cutBala;
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
	 * 设置：累计支付金额
	 */
	public void setSumPay(BigDecimal sumPay) {
		this.sumPay = sumPay;
	}

	/**
	 * 获取：累计支付金额
	 */
	public BigDecimal getSumPay() {
		return sumPay;
	}
		
	/**
	 * 设置：合同节超情况
	 */
	public void setContractDiff(String contractDiff) {
		this.contractDiff = contractDiff;
	}

	/**
	 * 获取：合同节超情况
	 */
	public String getContractDiff() {
		return contractDiff;
	}
		
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
		
	/**
	 * 设置：审核日期
	 */
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	/**
	 * 获取：审核日期
	 */
	public String getCheckDate() {
		return checkDate;
	}
		
	/**
	 * 设置：审核人员
	 */
	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}

	/**
	 * 获取：审核人员
	 */
	public String getCheckUser() {
		return checkUser;
	}
		
	/**
	 * 设置：上报日期
	 */
	public void setModiDate(String modiDate) {
		this.modiDate = modiDate;
	}

	/**
	 * 获取：上报日期
	 */
	public String getModiDate() {
		return modiDate;
	}
		
	/**
	 * 设置：上报人员
	 */
	public void setModiUser(String modiUser) {
		this.modiUser = modiUser;
	}

	/**
	 * 获取：上报人员
	 */
	public String getModiUser() {
		return modiUser;
	}
		
	/**
	 * 设置：审核步骤
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 获取：审核步骤
	 */
	public String getStatus() {
		return status;
	}
		
	/**
	 * 设置：日志编号
	 */
	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}

	/**
	 * 获取：日志编号
	 */
	public String getCheckNo() {
		return checkNo;
	}
									
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}