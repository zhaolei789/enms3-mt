package cn.ewsd.logistics.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import cn.ewsd.common.model.MCoreBase;
import javax.persistence.Table;

/**
 * 用户的物品
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-08-02 15:11:19
 */
@Table(name="hq_user_items")
public class HqUserItems extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
										// 
	    private String userUuid;
				// 
	    private String userNameId;
				// 更衣柜
	    private String items1;
				// 就餐柜
	    private String items2;
				// 
	    private String items3;
				// 
	    private String items4;
				// 
	    private String items5;

	    //关联用户表
		private String orgName;
		private String userName;


	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
	 * 设置：更衣柜
	 */
	public void setItems1(String items1) {
		this.items1 = items1;
	}

	/**
	 * 获取：更衣柜
	 */
	public String getItems1() {
		return items1;
	}
		
	/**
	 * 设置：就餐柜
	 */
	public void setItems2(String items2) {
		this.items2 = items2;
	}

	/**
	 * 获取：就餐柜
	 */
	public String getItems2() {
		return items2;
	}
		
	/**
	 * 设置：
	 */
	public void setItems3(String items3) {
		this.items3 = items3;
	}

	/**
	 * 获取：
	 */
	public String getItems3() {
		return items3;
	}
		
	/**
	 * 设置：
	 */
	public void setItems4(String items4) {
		this.items4 = items4;
	}

	/**
	 * 获取：
	 */
	public String getItems4() {
		return items4;
	}
		
	/**
	 * 设置：
	 */
	public void setItems5(String items5) {
		this.items5 = items5;
	}

	/**
	 * 获取：
	 */
	public String getItems5() {
		return items5;
	}
	
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}