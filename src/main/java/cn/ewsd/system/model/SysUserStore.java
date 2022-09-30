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
 * @Date 2021-06-09 17:58:25
 */
@Table(name="sys_user_store")
public class SysUserStore extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
										// 用户
	    private String userId;
				// 仓库
	    private String storeNo;

	    private String storeName;


	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
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
	 * 设置：仓库
	 */
	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	/**
	 * 获取：仓库
	 */
	public String getStoreNo() {
		return storeNo;
	}
	
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}