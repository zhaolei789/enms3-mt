package cn.ewsd.material.model;

import cn.ewsd.common.model.MCoreBase;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 
 * 
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-21 16:55:52
 */
@Table(name="m_erp_material")
public class MErpMaterial extends MCoreBase{
	private static final long serialVersionUID = 1L;

	private String factoryNo;
	private String matAddr;
	private String matNo;
	private String matUnit;

	public String getFactoryNo() {
		return factoryNo;
	}

	public void setFactoryNo(String factoryNo) {
		this.factoryNo = factoryNo;
	}

	public String getMatAddr() {
		return matAddr;
	}

	public void setMatAddr(String matAddr) {
		this.matAddr = matAddr;
	}

	public String getMatNo() {
		return matNo;
	}

	public void setMatNo(String matNo) {
		this.matNo = matNo;
	}

	public String getMatUnit() {
		return matUnit;
	}

	public void setMatUnit(String matUnit) {
		this.matUnit = matUnit;
	}

	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}