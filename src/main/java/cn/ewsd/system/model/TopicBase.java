package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.Table;


/**
 * 主题表
 * 
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-06-08 16:05:31
 */
@Table(name="sys_topic_base")
public class TopicBase extends MCoreBase {
	private static final long serialVersionUID = 1L;
	
								
	//模块标识
	private String moduleUuid;

		
	//标题
	private String title;

		
	//类型
	private String categoryId;

		
	//最后回复人标识
	private String lastReply;

		
	//回复数量
	private String replyCount;

		
	//IP地址
	private String ipAddress;

	private String status;

								
	/**
	 * 设置：模块标识
	 */
	public void setModuleUuid(String moduleUuid) {
		this.moduleUuid = moduleUuid;
	}

	/**
	 * 获取：模块标识
	 */
	public String getModuleUuid() {
		return moduleUuid;
	}
		
	/**
	 * 设置：标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获取：标题
	 */
	public String getTitle() {
		return title;
	}
		
	/**
	 * 设置：类型
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * 获取：类型
	 */
	public String getCategoryId() {
		return categoryId;
	}
		
	/**
	 * 设置：最后回复人标识
	 */
	public void setLastReply(String lastReply) {
		this.lastReply = lastReply;
	}

	/**
	 * 获取：最后回复人标识
	 */
	public String getLastReply() {
		return lastReply;
	}
		
	/**
	 * 设置：回复数量
	 */
	public void setReplyCount(String replyCount) {
		this.replyCount = replyCount;
	}

	/**
	 * 获取：回复数量
	 */
	public String getReplyCount() {
		return replyCount;
	}
		
	/**
	 * 设置：IP地址
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * 获取：IP地址
	 */
	public String getIpAddress() {
		return ipAddress;
	}
	
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}