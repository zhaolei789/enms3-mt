package cn.ewsd.system.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import cn.ewsd.common.model.MCoreBase;
import javax.persistence.Table;

/**
 * 
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-04-23 11:18:36
 */
@Table(name="sys_audit_process")
public class SysAuditProcess extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
										// 流程编号
	    private String processNo;
				// 流程名称
	    private String processName;
				// 步骤序列
	    private Integer processSequence;
				// 层级
	    private Integer level;
				// 父级ID
	    private String fuuid;
				// 上一步
	    private String lastStep;
				// 下一步
	    private String nextStep;
				// 初始化状态
	    private Integer initState;
				// 部署状态
	    private Integer deployState;
				// 备注
	    private String note;
		
									
	/**
	 * 设置：流程编号
	 */
	public void setProcessNo(String processNo) {
		this.processNo = processNo;
	}

	/**
	 * 获取：流程编号
	 */
	public String getProcessNo() {
		return processNo;
	}
		
	/**
	 * 设置：流程名称
	 */
	public void setProcessName(String processName) {
		this.processName = processName;
	}

	/**
	 * 获取：流程名称
	 */
	public String getProcessName() {
		return processName;
	}
		
	/**
	 * 设置：步骤序列
	 */
	public void setProcessSequence(Integer processSequence) {
		this.processSequence = processSequence;
	}

	/**
	 * 获取：步骤序列
	 */
	public Integer getProcessSequence() {
		return processSequence;
	}
		
	/**
	 * 设置：层级
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}

	/**
	 * 获取：层级
	 */
	public Integer getLevel() {
		return level;
	}
		
	/**
	 * 设置：父级ID
	 */
	public void setFuuid(String fuuid) {
		this.fuuid = fuuid;
	}

	/**
	 * 获取：父级ID
	 */
	public String getFuuid() {
		return fuuid;
	}
		
	/**
	 * 设置：上一步
	 */
	public void setLastStep(String lastStep) {
		this.lastStep = lastStep;
	}

	/**
	 * 获取：上一步
	 */
	public String getLastStep() {
		return lastStep;
	}
		
	/**
	 * 设置：下一步
	 */
	public void setNextStep(String nextStep) {
		this.nextStep = nextStep;
	}

	/**
	 * 获取：下一步
	 */
	public String getNextStep() {
		return nextStep;
	}
		
	/**
	 * 设置：初始化状态
	 */
	public void setInitState(Integer initState) {
		this.initState = initState;
	}

	/**
	 * 获取：初始化状态
	 */
	public Integer getInitState() {
		return initState;
	}
		
	/**
	 * 设置：部署状态
	 */
	public void setDeployState(Integer deployState) {
		this.deployState = deployState;
	}

	/**
	 * 获取：部署状态
	 */
	public Integer getDeployState() {
		return deployState;
	}
		
	/**
	 * 设置：备注
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * 获取：备注
	 */
	public String getNote() {
		return note;
	}
	
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}