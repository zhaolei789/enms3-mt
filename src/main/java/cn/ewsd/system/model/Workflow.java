package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;

import javax.persistence.Table;


@Table(name="sys_workflow")
public class Workflow extends MCoreBase {

	private String workflowUuid;
	private String workflowNameId;
	private String workflowName;
	private String stepNameId;
	private String stepName;
	private String previousStepId;
	private String nextStepId;
	private String dealUserNameId;
	private String dealUserName;

	public Workflow() {
		super();
	}

	public String getWorkflowUuid() {
		return workflowUuid;
	}

	public void setWorkflowUuid(String workflowUuid) {
		this.workflowUuid = workflowUuid;
	}

	public String getWorkflowNameId() {
		return workflowNameId;
	}

	public void setWorkflowNameId(String workflowNameId) {
		this.workflowNameId = workflowNameId;
	}

	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	public String getProcessNameId() {
		return workflowNameId;
	}

	public void setProcessNameId(String workflowNameId) {
		this.workflowNameId = workflowNameId;
	}

	public String getProcessName() {
		return workflowName;
	}

	public void setProcessName(String workflowName) {
		this.workflowName = workflowName;
	}

	public String getStepNameId() {
		return stepNameId;
	}

	public void setStepNameId(String stepNameId) {
		this.stepNameId = stepNameId;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getDealUserNameId() {
		return dealUserNameId;
	}

	public void setDealUserNameId(String dealUserNameId) {
		this.dealUserNameId = dealUserNameId;
	}

	public String getDealUserName() {
		return dealUserName;
	}

	public void setDealUserName(String dealUserName) {
		this.dealUserName = dealUserName;
	}

	public String getPreviousStepId() {
		return previousStepId;
	}

	public void setPreviousStepId(String previousStepId) {
		this.previousStepId = previousStepId;
	}

	public String getNextStepId() {
		return nextStepId;
	}

	public void setNextStepId(String nextStepId) {
		this.nextStepId = nextStepId;
	}
}
