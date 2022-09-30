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
 * @Date 2022-03-07 15:11:32
 */
@Table(name="sys_post")
public class SysPost extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
										// 
	    private String postCode;
				// 
	    private String postName;
				// 
	    private String postType;
				// 
	    private String postTypeName;
				// 
	    private Integer isDel;
		
									
	/**
	 * 设置：
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	/**
	 * 获取：
	 */
	public String getPostCode() {
		return postCode;
	}
		
	/**
	 * 设置：
	 */
	public void setPostName(String postName) {
		this.postName = postName;
	}

	/**
	 * 获取：
	 */
	public String getPostName() {
		return postName;
	}
		
	/**
	 * 设置：
	 */
	public void setPostType(String postType) {
		this.postType = postType;
	}

	/**
	 * 获取：
	 */
	public String getPostType() {
		return postType;
	}
		
	/**
	 * 设置：
	 */
	public void setPostTypeName(String postTypeName) {
		this.postTypeName = postTypeName;
	}

	/**
	 * 获取：
	 */
	public String getPostTypeName() {
		return postTypeName;
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
	
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}