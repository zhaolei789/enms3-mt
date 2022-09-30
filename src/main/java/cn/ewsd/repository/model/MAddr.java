package cn.ewsd.repository.model;

import cn.ewsd.common.model.MCoreBase;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 
 * 
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
@Table(name="m_addr")
public class MAddr extends MCoreBase{
	private static final long serialVersionUID = 1L;
	private String matAddr;
	private Integer teamNo;
	private String upDown;
	private String ifUsing;

	public String getMatAddr() {
		return matAddr;
	}

	public void setMatAddr(String matAddr) {
		this.matAddr = matAddr;
	}

	public Integer getTeamNo() {
		return teamNo;
	}

	public void setTeamNo(Integer teamNo) {
		this.teamNo = teamNo;
	}

	public String getUpDown() {
		return upDown;
	}

	public void setUpDown(String upDown) {
		this.upDown = upDown;
	}

	public String getIfUsing() {
		return ifUsing;
	}

	public void setIfUsing(String ifUsing) {
		this.ifUsing = ifUsing;
	}
}