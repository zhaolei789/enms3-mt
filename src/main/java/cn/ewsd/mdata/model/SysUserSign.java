package cn.ewsd.mdata.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import cn.ewsd.common.model.MCoreBase;
import javax.persistence.Table;

/**
 * 用户签名
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2022-05-06 15:43:29
 */
@Table(name="sys_user_sign")
public class SysUserSign extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
										// 
	    private String userId;
				// 
	    private String userNameId;
				// 
	    private String userName;
				// 
	    private String signBase64;
				// 
	    private String signPath;
				// 
	    private String signUrl;
				// 备注
	    private String note;
				// 类型
	    private Integer type;
				// 状态0未使用1在用
	    private Integer status;
				// 0
	    private Integer isDel;
		
									
	/**
	 * 设置：
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 获取：
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
	 * 设置：
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 获取：
	 */
	public String getUserName() {
		return userName;
	}
		
	/**
	 * 设置：
	 */
	public void setSignBase64(String signBase64) {
		this.signBase64 = signBase64;
	}

	/**
	 * 获取：
	 */
	public String getSignBase64() {
		return signBase64;
	}
		
	/**
	 * 设置：
	 */
	public void setSignPath(String signPath) {
		this.signPath = signPath;
	}

	/**
	 * 获取：
	 */
	public String getSignPath() {
		return signPath;
	}
		
	/**
	 * 设置：
	 */
	public void setSignUrl(String signUrl) {
		this.signUrl = signUrl;
	}

	/**
	 * 获取：
	 */
	public String getSignUrl() {
		return signUrl;
	}
		
	/**
	 * 设置：备注
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * 获取：备注
	 */
	public String getNote() {
		return note;
	}
		
	/**
	 * 设置：类型
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * 获取：类型
	 */
	public Integer getType() {
		return type;
	}
		
	/**
	 * 设置：状态0未使用1在用
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取：状态0未使用1在用
	 */
	public Integer getStatus() {
		return status;
	}
		
	/**
	 * 设置：0
	 */
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	/**
	 * 获取：0
	 */
	public Integer getIsDel() {
		return isDel;
	}
	
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}