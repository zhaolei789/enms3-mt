package cn.ewsd.mdata.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import cn.ewsd.common.model.MCoreBase;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Table;

/**
 * 
 * 
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-20 11:40:20
 */
@Table(name="t_dept_type")
public class TdeptType extends MCoreBase{
	private static final long serialVersionUID = 1L;

	    private Integer deptId;
				// 
	    private String dictKey;
				// 
	    private String subKey;
				// 
	    private Integer orderNo;

	    private String dictName;
	    private String subName;
											//
//	    @DateTimeFormat(
//        pattern = "yyyy-MM-dd HH:mm:ss"
//    )
//    @JsonFormat(
//        pattern = "yyyy-MM-dd HH:mm:ss",
//        timezone = "GMT+8"
//    )
    private String modifierTime;


	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	/**
	 * 设置：
	 */
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	/**
	 * 获取：
	 */
	public Integer getDeptId() {
		return deptId;
	}
		
	/**
	 * 设置：
	 */
	public void setDictKey(String dictKey) {
		this.dictKey = dictKey;
	}

	/**
	 * 获取：
	 */
	public String getDictKey() {
		return dictKey;
	}
		
	/**
	 * 设置：
	 */
	public void setSubKey(String subKey) {
		this.subKey = subKey;
	}

	/**
	 * 获取：
	 */
	public String getSubKey() {
		return subKey;
	}
		
	/**
	 * 设置：
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * 获取：
	 */
	public Integer getOrderNo() {
		return orderNo;
	}
									
	/**
	 * 设置：
	 */
	public void setModifierTime(String modifierTime) {
		this.modifierTime = modifierTime;
	}

	/**
	 * 获取：
	 */
	public String getModifierTime() {
		return modifierTime;
	}
	
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}