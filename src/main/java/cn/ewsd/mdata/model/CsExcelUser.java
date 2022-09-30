package cn.ewsd.mdata.model;

import cn.ewsd.common.model.MCoreBase;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.Table;

/**
 * 用户导入导出测试
 * 
 * @Author fengkai
 * @Email fengkai@ewsd.cn
 * @Date 2019-08-29 14:01:55
 */
@Table(name="cs_excel_user")
public class CsExcelUser extends MCoreBase{
	private static final long serialVersionUID = 1L;
	

		// 备注
	    private String remark;
				// 生日
	    private String birthday;
				// 学历
	    private String education;
				// 手机
	    private String cellphone;
				// 电子邮箱
	    private String email;
				// 用户名称
	    private String userName;
				// 用户ID
	    private String userNameId;
				// 密码
	    private String password;
				// 年龄
	    private Integer age;
				// 国家
	    private String country;
		

						
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
		
	/**
	 * 设置：生日
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	/**
	 * 获取：生日
	 */
	public String getBirthday() {
		return birthday;
	}
		
	/**
	 * 设置：学历
	 */
	public void setEducation(String education) {
		this.education = education;
	}

	/**
	 * 获取：学历
	 */
	public String getEducation() {
		return education;
	}
		
	/**
	 * 设置：手机
	 */
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	/**
	 * 获取：手机
	 */
	public String getCellphone() {
		return cellphone;
	}
		
	/**
	 * 设置：电子邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取：电子邮箱
	 */
	public String getEmail() {
		return email;
	}
		
	/**
	 * 设置：用户名称
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 获取：用户名称
	 */
	public String getUserName() {
		return userName;
	}
		
	/**
	 * 设置：用户ID
	 */
	public void setUserNameId(String userNameId) {
		this.userNameId = userNameId;
	}

	/**
	 * 获取：用户ID
	 */
	public String getUserNameId() {
		return userNameId;
	}
		
	/**
	 * 设置：密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取：密码
	 */
	public String getPassword() {
		return password;
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
	 * 设置：国家
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * 获取：国家
	 */
	public String getCountry() {
		return country;
	}
	
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}