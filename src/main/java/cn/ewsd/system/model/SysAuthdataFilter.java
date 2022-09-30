package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.Table;


/**
 * 数据权限过滤器
 * 
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-12-26 09:58:54
 */
@Table(name="sys_authdata_filter")
public class SysAuthdataFilter extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
								
	//创建人机构ID
	private Integer creatorOrgId;

		
	//类型
	private String type;

		
	//连接类型
	private String joinType;

		
	//左括号
	private String lb;

		
	//字段名
	private String field;

		
	//条件
	private String op;

		
	//字段值
	private String value;

		
	//右括号
	private String rb;


	//排序
	private Integer sort;

	//数据接口UUID
	private String apiUuid;

	public String getApiUuid() {
		return apiUuid;
	}

	public void setApiUuid(String apiUuid) {
		this.apiUuid = apiUuid;
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
	 * 设置：类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取：类型
	 */
	public String getType() {
		return type;
	}
		
	/**
	 * 设置：连接符
	 */
	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}

	/**
	 * 获取：连接符
	 */
	public String getJoinType() {
		return joinType;
	}
		
	/**
	 * 设置：左括号
	 */
	public void setLb(String lb) {
		this.lb = lb;
	}

	/**
	 * 获取：左括号
	 */
	public String getLb() {
		return lb;
	}
		
	/**
	 * 设置：字段名
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * 获取：字段名
	 */
	public String getField() {
		return field;
	}
		
	/**
	 * 设置：条件
	 */
	public void setOp(String op) {
		this.op = op;
	}

	/**
	 * 获取：条件
	 */
	public String getOp() {
		return op;
	}
		
	/**
	 * 设置：字段值
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 获取：字段值
	 */
	public String getValue() {
		return value;
	}
		
	/**
	 * 设置：右括号
	 */
	public void setRb(String rb) {
		this.rb = rb;
	}

	/**
	 * 获取：右括号
	 */
	public String getRb() {
		return rb;
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