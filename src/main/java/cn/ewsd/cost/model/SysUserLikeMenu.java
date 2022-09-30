package cn.ewsd.cost.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import cn.ewsd.common.model.MCoreBase;
import javax.persistence.Table;

/**
 * 用户喜欢的菜单
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-30 09:10:09
 */
@Table(name="sys_user_like_menu")
public class SysUserLikeMenu extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
										// 用户
	    private String userId;
				// 
	    private String userNameId;
				// 菜单链接
	    private String menuUri;
				// 点击数
	    private Integer clicksNumber;

	    //查询参数
		private String menuName;


	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	/**
	 * 设置：用户
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 获取：用户
	 */
	public String getUserId() {
		return userId;
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
	 * 设置：菜单链接
	 */
	public void setMenuUri(String menuUri) {
		this.menuUri = menuUri;
	}

	/**
	 * 获取：菜单链接
	 */
	public String getMenuUri() {
		return menuUri;
	}
		
	/**
	 * 设置：点击数
	 */
	public void setClicksNumber(Integer clicksNumber) {
		this.clicksNumber = clicksNumber;
	}

	/**
	 * 获取：点击数
	 */
	public Integer getClicksNumber() {
		return clicksNumber;
	}
	
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}