package cn.ewsd.mdata.model;

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
 * @Date 2021-03-23 11:33:15
 */
@Table(name="sys_employees")
public class SysEmployees extends MCoreBase{
	private static final long serialVersionUID = 1L;
	
										// 员工编号
	    private String empNo;
				// 矿帽号
	    private String empHatNo;
				// 姓名
	    private String empName;
				// 姓名简拼
	    private String empNameJp;
				// 性别
	    private Integer empSex;
				// 员工状态
	    private Integer empStatus;
				// 组织机构ID
	    private String empOrgId;
				// 组织机构名称
	    private String empOrgName;
				//职务
	    private String empPost;
				// 劳保工种
	    private String empPostLb;
				// 工具工种
	    private String empPostGj;
				// 电话
	    private String empPhoneNum;
				// 备注
	    private String empRemark;


	public String getEmpPost() {
		return empPost;
	}

	public void setEmpPost(String empPost) {
		this.empPost = empPost;
	}

	/**
	 * 设置：员工编号
	 */
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	/**
	 * 获取：员工编号
	 */
	public String getEmpNo() {
		return empNo;
	}
		
	/**
	 * 设置：矿帽号
	 */
	public void setEmpHatNo(String empHatNo) {
		this.empHatNo = empHatNo;
	}

	/**
	 * 获取：矿帽号
	 */
	public String getEmpHatNo() {
		return empHatNo;
	}
		
	/**
	 * 设置：姓名
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}

	/**
	 * 获取：姓名
	 */
	public String getEmpName() {
		return empName;
	}
		
	/**
	 * 设置：姓名简拼
	 */
	public void setEmpNameJp(String empNameJp) {
		this.empNameJp = empNameJp;
	}

	/**
	 * 获取：姓名简拼
	 */
	public String getEmpNameJp() {
		return empNameJp;
	}
		
	/**
	 * 设置：性别
	 */
	public void setEmpSex(Integer empSex) {
		this.empSex = empSex;
	}

	/**
	 * 获取：性别
	 */
	public Integer getEmpSex() {
		return empSex;
	}
		
	/**
	 * 设置：员工状态
	 */
	public void setEmpStatus(Integer empStatus) {
		this.empStatus = empStatus;
	}

	/**
	 * 获取：员工状态
	 */
	public Integer getEmpStatus() {
		return empStatus;
	}
		
	/**
	 * 设置：组织机构ID
	 */
	public void setEmpOrgId(String empOrgId) {
		this.empOrgId = empOrgId;
	}

	/**
	 * 获取：组织机构ID
	 */
	public String getEmpOrgId() {
		return empOrgId;
	}
		
	/**
	 * 设置：组织机构名称
	 */
	public void setEmpOrgName(String empOrgName) {
		this.empOrgName = empOrgName;
	}

	/**
	 * 获取：组织机构名称
	 */
	public String getEmpOrgName() {
		return empOrgName;
	}
		
	/**
	 * 设置：劳保工种
	 */
	public void setEmpPostLb(String empPostLb) {
		this.empPostLb = empPostLb;
	}

	/**
	 * 获取：劳保工种
	 */
	public String getEmpPostLb() {
		return empPostLb;
	}
		
	/**
	 * 设置：工具工种
	 */
	public void setEmpPostGj(String empPostGj) {
		this.empPostGj = empPostGj;
	}

	/**
	 * 获取：工具工种
	 */
	public String getEmpPostGj() {
		return empPostGj;
	}
		
	/**
	 * 设置：电话
	 */
	public void setEmpPhoneNum(String empPhoneNum) {
		this.empPhoneNum = empPhoneNum;
	}

	/**
	 * 获取：电话
	 */
	public String getEmpPhoneNum() {
		return empPhoneNum;
	}
		
	/**
	 * 设置：备注
	 */
	public void setEmpRemark(String empRemark) {
		this.empRemark = empRemark;
	}

	/**
	 * 获取：备注
	 */
	public String getEmpRemark() {
		return empRemark;
	}
	
	@Override
	public String toString() {
		return  ReflectionToStringBuilder.toString(this);
	}
}