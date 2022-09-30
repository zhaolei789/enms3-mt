package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;

import javax.persistence.Table;


@Table(name="sys_workflow_process")
public class WorkflowProcess extends MCoreBase {

	private String moduleNameId;
	private String moduleName;
	private String parentId;
	private String stepNameId;
	private String stepName;
	private String opinion;

	public WorkflowProcess() {
		super();
	}


	public String getModuleName() {
		return moduleName;
	}


	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}


	public String getOpinion() {
		return opinion;
	}


	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getStepName() {
		return stepName;
	}


	public void setStepName(String stepName) {
		this.stepName = stepName;
	}


	public String getStepNameId() {
		return stepNameId;
	}


	public void setStepNameId(String stepNameId) {
		this.stepNameId = stepNameId;
	}


	public String getModuleNameId() {
		return moduleNameId;
	}


	public void setModuleNameId(String moduleNameId) {
		this.moduleNameId = moduleNameId;
	}


	public String getParentId() {
		return parentId;
	}


	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	
	
}
