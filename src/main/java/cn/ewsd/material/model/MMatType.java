package cn.ewsd.material.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import cn.ewsd.common.model.MCoreBase;
import javax.persistence.Table;

/**
 * 
 * 
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-21 11:04:46
 */
@Table(name="m_mat_type")
public class MMatType extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
			// 
	    private String typeCode;
				// 
	    private String typeName;
				// 
	    private String remark;
				// 
	    private String useFlag;
		
		
	/**
	 * 设置：
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	/**
	 * 获取：
	 */
	public String getTypeCode() {
		return typeCode;
	}
		
	/**
	 * 设置：
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * 获取：
	 */
	public String getTypeName() {
		return typeName;
	}
		
	/**
	 * 设置：
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取：
	 */
	public String getRemark() {
		return remark;
	}
		
	/**
	 * 设置：
	 */
	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}

	/**
	 * 获取：
	 */
	public String getUseFlag() {
		return useFlag;
	}
	
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}