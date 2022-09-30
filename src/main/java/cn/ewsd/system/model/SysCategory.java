package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.Table;


/**
 * 
 * 
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-06-09 09:20:29
 */
@Table(name="sys_category")
public class SysCategory extends MCoreBase {
	private static final long serialVersionUID = 1L;
	
		
	//
	private Integer id;

		
	//
	private Integer pid;

		
	//
	private String text;

		
	//
	private String state;

		
	//
	private String type;

		
	//
	private String code;

		
	//
	private Integer levelId;

	private Integer sort;

	private String iconCls;

	private String portalDisplay;

	public String getPortalDisplay() {
		return portalDisplay;
	}

	public void setPortalDisplay(String portalDisplay) {
		this.portalDisplay = portalDisplay;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
		
	/**
	 * 设置：
	 */
	public void setPid(Integer pid) {
		this.pid = pid;
	}

	/**
	 * 获取：
	 */
	public Integer getPid() {
		return pid;
	}
		
	/**
	 * 设置：
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 获取：
	 */
	public String getText() {
		return text;
	}
		
	/**
	 * 设置：
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * 获取：
	 */
	public String getState() {
		return state;
	}
		
	/**
	 * 设置：
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取：
	 */
	public String getType() {
		return type;
	}
		
	/**
	 * 设置：
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获取：
	 */
	public String getCode() {
		return code;
	}
		
	/**
	 * 设置：
	 */
	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}

	/**
	 * 获取：
	 */
	public Integer getLevelId() {
		return levelId;
	}
							
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}