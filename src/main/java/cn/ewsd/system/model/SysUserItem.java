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
 * @Date 2021-05-10 09:39:02
 */
@Table(name="sys_user_item")
public class SysUserItem extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
										// 用户
	    private String userId;
				// 科目编号
	    private String itemNo;

	    private String itemName;

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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
	 * 设置：科目编号
	 */
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	/**
	 * 获取：科目编号
	 */
	public String getItemNo() {
		return itemNo;
	}
	
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}