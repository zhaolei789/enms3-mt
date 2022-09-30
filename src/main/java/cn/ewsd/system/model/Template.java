package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;

import javax.persistence.Table;
import java.math.BigDecimal;


@Table(name="sys_process_template")
public class Template extends MCoreBase {

	private Integer pid;//父UUID
	private Integer stepNumber;//步骤号
	private BigDecimal stepNo;//步骤号
	private String nextstep;//下一步骤
	private String stepName;//步骤名称
	private String stepState;//步骤状态
	private String param;//参数
	private String company;//公司
	private String department;//部门
	private String office;//
	private String group2;
	private String duty;//责任
	private String zrren;//责任人
	private String zhihui;
	
	public Template() {
		super();
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public BigDecimal getStepNo() {
		return stepNo;
	}

	public void setStepNo(BigDecimal stepNo) {
		this.stepNo = stepNo;
	}

	public String getNextstep() {
		return nextstep;
	}

	public void setNextstep(String nextstep) {
		this.nextstep = nextstep;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public String getGroup2() {
		return group2;
	}

	public void setGroup2(String group2) {
		this.group2 = group2;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getZrren() {
		return zrren;
	}

	public void setZrren(String zrren) {
		this.zrren = zrren;
	}

	public String getZhihui() {
		return zhihui;
	}

	public void setZhihui(String zhihui) {
		this.zhihui = zhihui;
	}

	public String getStepState() {
		return stepState;
	}

	public void setStepState(String stepState) {
		this.stepState = stepState;
	}

	public Integer getStepNumber() {
		return stepNumber;
	}

	public void setStepNumber(Integer stepNumber) {
		this.stepNumber = stepNumber;
	}
	
	
}
