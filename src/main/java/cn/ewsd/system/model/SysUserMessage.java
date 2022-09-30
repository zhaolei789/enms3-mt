package cn.ewsd.system.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import cn.ewsd.common.model.MCoreBase;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Table;

/**
 * 消息
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-10-21 13:58:38
 */
@Table(name="sys_user_message")
public class SysUserMessage extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
									// 
	    @DateTimeFormat(
        pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
        pattern = "yyyy-MM-dd HH:mm:ss",
        timezone = "GMT+8"
    )
    private Date modifierTime;
				// 1删除
	    private Integer isDel;
				// 0未读 1已读
	    private Integer isRead;
				// 
	    private String userUuid;
				// 
	    private String userNameId;
				// 1通知消息 2待办消息
	    private Integer msgType;
				// 消息描述
	    private String msgText;
				// 跳转路径
	    private String msgUrl;
				// 关联业务ID
	    private String businessId;
				// 备注
	    private String note;

	    private String msgUrlName;

	public String getMsgUrlName() {
		return msgUrlName;
	}

	public void setMsgUrlName(String msgUrlName) {
		this.msgUrlName = msgUrlName;
	}

	/**
	 * 设置：
	 */
	public void setModifierTime(Date modifierTime) {
		this.modifierTime = modifierTime;
	}

	/**
	 * 获取：
	 */
	public Date getModifierTime() {
		return modifierTime;
	}
		
	/**
	 * 设置：1删除
	 */
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	/**
	 * 获取：1删除
	 */
	public Integer getIsDel() {
		return isDel;
	}
		
	/**
	 * 设置：0未读 1已读
	 */
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	/**
	 * 获取：0未读 1已读
	 */
	public Integer getIsRead() {
		return isRead;
	}
		
	/**
	 * 设置：
	 */
	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	/**
	 * 获取：
	 */
	public String getUserUuid() {
		return userUuid;
	}
		
	/**
	 * 设置：
	 */
	public void setUserNameId(String userNameId) {
		this.userNameId = userNameId;
	}

	/**
	 * 获取：
	 */
	public String getUserNameId() {
		return userNameId;
	}
		
	/**
	 * 设置：1通知消息 2待办消息
	 */
	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	/**
	 * 获取：1通知消息 2待办消息
	 */
	public Integer getMsgType() {
		return msgType;
	}
		
	/**
	 * 设置：消息描述
	 */
	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}

	/**
	 * 获取：消息描述
	 */
	public String getMsgText() {
		return msgText;
	}
		
	/**
	 * 设置：跳转路径
	 */
	public void setMsgUrl(String msgUrl) {
		this.msgUrl = msgUrl;
	}

	/**
	 * 获取：跳转路径
	 */
	public String getMsgUrl() {
		return msgUrl;
	}
		
	/**
	 * 设置：关联业务ID
	 */
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	/**
	 * 获取：关联业务ID
	 */
	public String getBusinessId() {
		return businessId;
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