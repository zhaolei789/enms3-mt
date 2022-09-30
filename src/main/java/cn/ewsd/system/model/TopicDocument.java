package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.Table;


/**
 * 主题内容
 * 
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-06-08 16:06:00
 */
@Table(name="sys_topic_document")
public class TopicDocument extends MCoreBase {
	private static final long serialVersionUID = 1L;

	//主题UUID
	private String topicUuid;

	//内容
	private String content;

	/**
	 * 设置：主题UUID
	 */
	public void setTopicUuid(String topicUuid) {
		this.topicUuid = topicUuid;
	}

	/**
	 * 获取：主题UUID
	 */
	public String getTopicUuid() {
		return topicUuid;
	}
		
	/**
	 * 设置：内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取：内容
	 */
	public String getContent() {
		return content;
	}
	
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}