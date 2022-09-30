package cn.ewsd.logistics.model;

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
 * @Date 2021-07-27 09:52:49
 */
@Table(name="sys_sequence")
public class SysSequence extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
						// 是否删除  0-否  1-是
	    private Integer isDel;
								// 年
	    private Integer sqYear;
				// 月
	    private Integer sqMonth;
				// 日
	    private Integer sqDay;
				// 系统
	    private String sqSystem;
				// 模块
	    private String sqModule;
				// 序列
	    private Integer sqSequence;
		
					
	/**
	 * 设置：是否删除  0-否  1-是
	 */
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	/**
	 * 获取：是否删除  0-否  1-是
	 */
	public Integer getIsDel() {
		return isDel;
	}
						
	/**
	 * 设置：年
	 */
	public void setSqYear(Integer sqYear) {
		this.sqYear = sqYear;
	}

	/**
	 * 获取：年
	 */
	public Integer getSqYear() {
		return sqYear;
	}
		
	/**
	 * 设置：月
	 */
	public void setSqMonth(Integer sqMonth) {
		this.sqMonth = sqMonth;
	}

	/**
	 * 获取：月
	 */
	public Integer getSqMonth() {
		return sqMonth;
	}
		
	/**
	 * 设置：日
	 */
	public void setSqDay(Integer sqDay) {
		this.sqDay = sqDay;
	}

	/**
	 * 获取：日
	 */
	public Integer getSqDay() {
		return sqDay;
	}
		
	/**
	 * 设置：系统
	 */
	public void setSqSystem(String sqSystem) {
		this.sqSystem = sqSystem;
	}

	/**
	 * 获取：系统
	 */
	public String getSqSystem() {
		return sqSystem;
	}
		
	/**
	 * 设置：模块
	 */
	public void setSqModule(String sqModule) {
		this.sqModule = sqModule;
	}

	/**
	 * 获取：模块
	 */
	public String getSqModule() {
		return sqModule;
	}
		
	/**
	 * 设置：序列
	 */
	public void setSqSequence(Integer sqSequence) {
		this.sqSequence = sqSequence;
	}

	/**
	 * 获取：序列
	 */
	public Integer getSqSequence() {
		return sqSequence;
	}
	
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}