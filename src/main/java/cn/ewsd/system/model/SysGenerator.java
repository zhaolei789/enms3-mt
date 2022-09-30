package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.Table;


/**
 * 
 * 
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-12-24 16:27:06
 */
@Table(name="sys_generator")
public class SysGenerator extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
								
	//创建人机构ID
	private Integer creatorOrgId;

		
	//标题
	private String systemName;

		
	//内容
	private String basePackage;

		
	//备注
	private String Package;

		
	//
	private String author;

		
	//
	private String email;

		
	//
	private String tableprefix;

	
								
	/**
	 * 设置：创建人机构ID
	 */
	public void setCreatorOrgId(Integer creatorOrgId) {
		this.creatorOrgId = creatorOrgId;
	}

	/**
	 * 获取：创建人机构ID
	 */
	public Integer getCreatorOrgId() {
		return creatorOrgId;
	}
		
	/**
	 * 设置：标题
	 */
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	/**
	 * 获取：标题
	 */
	public String getSystemName() {
		return systemName;
	}
		
	/**
	 * 设置：内容
	 */
	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	/**
	 * 获取：内容
	 */
	public String getBasePackage() {
		return basePackage;
	}
		
	/**
	 * 设置：备注
	 */
	public void setPackage(String Package) {
		this.Package = Package;
	}

	/**
	 * 获取：备注
	 */
	public String getPackage() {
		return Package;
	}
		
	/**
	 * 设置：
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * 获取：
	 */
	public String getAuthor() {
		return author;
	}
		
	/**
	 * 设置：
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取：
	 */
	public String getEmail() {
		return email;
	}
		
	/**
	 * 设置：
	 */
	public void setTableprefix(String tableprefix) {
		this.tableprefix = tableprefix;
	}

	/**
	 * 获取：
	 */
	public String getTableprefix() {
		return tableprefix;
	}
	
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}