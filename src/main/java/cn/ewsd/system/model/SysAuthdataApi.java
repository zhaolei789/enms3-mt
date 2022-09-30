package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.Table;


/**
 * 数据权限接口
 * 
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-12-26 09:58:25
 */
@Table(name="sys_authdata_api")
public class SysAuthdataApi extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
								
	//创建人机构ID
	private Integer creatorOrgId;

		
	//接口名称
	private String name;

		
	//接口地址
	private String url;


	//接口地址
	private String sqlStr;

	//排序
	private Integer sort;
		
	//备注
	private String remark;

	//权限组UUID
	private String  groupUuid;

	//优先级
	private Integer priority;

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}



	public String getGroupUuid() {
		return groupUuid;
	}

	public void setGroupUuid(String groupUuid) {
		this.groupUuid = groupUuid;
	}

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
	 * 设置：接口名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取：接口名称
	 */
	public String getName() {
		return name;
	}
		
	/**
	 * 设置：接口地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获取：接口地址
	 */
	public String getUrl() {
		return url;
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

	public String getSqlStr() {
		return sqlStr;
	}

	public void setSqlStr(String sqlStr) {
		this.sqlStr = sqlStr;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}