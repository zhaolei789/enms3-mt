package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.Table;


/**
 * 数据权限分组
 * 
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-12-26 09:57:35
 */
@Table(name="sys_authdata_group")
public class SysAuthdataGroup extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
								
	//创建人机构ID
	private Integer creatorOrgId;

		
	//分组ID
	private Integer id;

		
	//分组名
	private String name;


	//排序
	private Integer sort;

		
	//备注
	private String remark;

	
								
	/**
	 * 设置：创建人机构ID
	 */
	public void setCreatorOrgId(Integer creatorOrgId) {
		this.creatorOrgId = creatorOrgId;
	}

	/**
	 * 获取：创建人机构ID
	 */
	public Integer getCreatorOrgId() {
		return creatorOrgId;
	}
		
	/**
	 * 设置：分组ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取：分组ID
	 */
	public Integer getId() {
		return id;
	}
		
	/**
	 * 设置：分组名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取：分组名
	 */
	public String getName() {
		return name;
	}
		
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
	
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}