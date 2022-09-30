package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.Table;


/**
 * 
 * 
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-07-24 15:27:51
 */
@Table(name="sys_cs_base")
public class SysCsBase extends MCoreBase {
	private static final long serialVersionUID = 1L;
	
								
	//orgId
	private Integer creatorOrgId;

		
	//姓名
	private String userName;

		
	//性别
	private String sex;

		
	//年龄
	private Integer age;

		
	//地址
	private String address;

		
	//手机号
	private String phone;

	
								
	/**
	 * 设置：orgId
	 */
	public void setCreatorOrgId(Integer creatorOrgId) {
		this.creatorOrgId = creatorOrgId;
	}

	/**
	 * 获取：orgId
	 */
	public Integer getCreatorOrgId() {
		return creatorOrgId;
	}
		
	/**
	 * 设置：姓名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 获取：姓名
	 */
	public String getUserName() {
		return userName;
	}
		
	/**
	 * 设置：性别
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * 获取：性别
	 */
	public String getSex() {
		return sex;
	}
		
	/**
	 * 设置：年龄
	 */
	public void setAge(Integer age) {
		this.age = age;
	}

	/**
	 * 获取：年龄
	 */
	public Integer getAge() {
		return age;
	}
		
	/**
	 * 设置：地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 获取：地址
	 */
	public String getAddress() {
		return address;
	}
		
	/**
	 * 设置：手机号
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 获取：手机号
	 */
	public String getPhone() {
		return phone;
	}
	
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}