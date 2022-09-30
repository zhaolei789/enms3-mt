package cn.ewsd.material.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import cn.ewsd.common.model.MCoreBase;
import javax.persistence.Table;

/**
 * 货位
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2022-07-06 17:28:02
 */
@Table(name="m_site")
public class MSite extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
										// 仓库名称
	    private String storeNo;
				// 货位编号
	    private String siteCode;
				// 货位名称
	    private String siteName;
				// 货位说明
	    private String remark;
				// 名称简拼
	    private String wordLevel;
				// 备注
	    private String note;
				// 类型
	    private Integer type;
				// 状态
	    private Integer status;
				// 0
	    private Integer isDel;
		
									
	/**
	 * 设置：仓库名称
	 */
	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	/**
	 * 获取：仓库名称
	 */
	public String getStoreNo() {
		return storeNo;
	}
		
	/**
	 * 设置：货位编号
	 */
	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	/**
	 * 获取：货位编号
	 */
	public String getSiteCode() {
		return siteCode;
	}
		
	/**
	 * 设置：货位名称
	 */
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	/**
	 * 获取：货位名称
	 */
	public String getSiteName() {
		return siteName;
	}
		
	/**
	 * 设置：货位说明
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取：货位说明
	 */
	public String getRemark() {
		return remark;
	}
		
	/**
	 * 设置：名称简拼
	 */
	public void setWordLevel(String wordLevel) {
		this.wordLevel = wordLevel;
	}

	/**
	 * 获取：名称简拼
	 */
	public String getWordLevel() {
		return wordLevel;
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
	 * 设置：状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取：状态
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