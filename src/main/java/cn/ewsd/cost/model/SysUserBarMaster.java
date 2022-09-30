package cn.ewsd.cost.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import cn.ewsd.common.model.MCoreBase;
import javax.persistence.Table;

/**
 * 用户与矿长
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-17 14:44:35
 */
@Table(name="sys_user_bar_master")
public class SysUserBarMaster extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
										// 
	    private Integer isDel;
				// 用户ID
	    private String userId;
				// 用户账户
	    private String userNameId;
				// 领导ID
	    private String masterId;
				// 备注
	    private String note;

	    private String masterName;


	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	/**
	 * 设置：
	 */
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	/**
	 * 获取：
	 */
	public Integer getIsDel() {
		return isDel;
	}
		
	/**
	 * 设置：用户ID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 获取：用户ID
	 */
	public String getUserId() {
		return userId;
	}
		
	/**
	 * 设置：用户账户
	 */
	public void setUserNameId(String userNameId) {
		this.userNameId = userNameId;
	}

	/**
	 * 获取：用户账户
	 */
	public String getUserNameId() {
		return userNameId;
	}
		
	/**
	 * 设置：领导ID
	 */
	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}

	/**
	 * 获取：领导ID
	 */
	public String getMasterId() {
		return masterId;
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