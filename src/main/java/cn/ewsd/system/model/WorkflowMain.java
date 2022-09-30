package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;

import javax.persistence.Table;


@Table(name="sys_workflow_main")
public class WorkflowMain extends MCoreBase {

	private String workflowUuid;
	private String title;
	private String content;
	private String currentStep;
	private String currentStepId;
	private String currentUserName;
	private String currentUserNameId;

	public WorkflowMain() {
		super();
	}

	public void setWorkflowUuid(String workflowUuid) {
		this.workflowUuid = workflowUuid;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWorkflowUuid() {
		return workflowUuid;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public String getCurrentStep() {
		return currentStep;
	}

	public void setCurrentStep(String currentStep) {
		this.currentStep = currentStep;
	}

	public String getCurrentStepId() {
		return currentStepId;
	}

	public void setCurrentStepId(String currentStepId) {
		this.currentStepId = currentStepId;
	}

	public String getCurrentUserName() {
		return currentUserName;
	}

	public void setCurrentUserName(String currentUserName) {
		this.currentUserName = currentUserName;
	}

	public String getCurrentUserNameId() {
		return currentUserNameId;
	}

	public void setCurrentUserNameId(String currentUserNameId) {
		this.currentUserNameId = currentUserNameId;
	}
}
