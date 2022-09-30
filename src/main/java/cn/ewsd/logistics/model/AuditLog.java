package cn.ewsd.logistics.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import cn.ewsd.common.model.MCoreBase;
import javax.persistence.Table;

/**
 * 审批日志
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-03-25 14:10:22
 */
@Table(name="audit_log")
public class AuditLog extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
										// 业务类型
	    private Integer businessType;
				// 业务ID
	    private String businessId;
				// 审批方向
	    private Integer direction;
				// 审批意见
	    private String opinion;
				// 附件
	    private String attachment;
				// 关联字典键
	    private String auditProcess;
				// 关联字典值
	    private String auditProcessStep;
				//变更前
	    private String changeBefore;
				//变更后
		private String changeAfter;

		//===================
		private String auditProcessStepName;


	public String getAuditProcessStepName() {
		return auditProcessStepName;
	}

	public void setAuditProcessStepName(String auditProcessStepName) {
		this.auditProcessStepName = auditProcessStepName;
	}

	public String getChangeBefore() {
		return changeBefore;
	}

	public void setChangeBefore(String changeBefore) {
		this.changeBefore = changeBefore;
	}

	public String getChangeAfter() {
		return changeAfter;
	}

	public void setChangeAfter(String changeAfter) {
		this.changeAfter = changeAfter;
	}

	/**
	 * 设置：业务类型
	 */
	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	/**
	 * 获取：业务类型
	 */
	public Integer getBusinessType() {
		return businessType;
	}
		
	/**
	 * 设置：业务ID
	 */
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	/**
	 * 获取：业务ID
	 */
	public String getBusinessId() {
		return businessId;
	}
		
	/**
	 * 设置：审批方向
	 */
	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	/**
	 * 获取：审批方向
	 */
	public Integer getDirection() {
		return direction;
	}
		
	/**
	 * 设置：审批意见
	 */
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	/**
	 * 获取：审批意见
	 */
	public String getOpinion() {
		return opinion;
	}
		
	/**
	 * 设置：附件
	 */
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	/**
	 * 获取：附件
	 */
	public String getAttachment() {
		return attachment;
	}

	public String getAuditProcess() {
		return auditProcess;
	}

	public void setAuditProcess(String auditProcess) {
		this.auditProcess = auditProcess;
	}

	public String getAuditProcessStep() {
		return auditProcessStep;
	}

	public void setAuditProcessStep(String auditProcessStep) {
		this.auditProcessStep = auditProcessStep;
	}

	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}