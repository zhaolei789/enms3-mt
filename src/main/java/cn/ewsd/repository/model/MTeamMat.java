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
@Table(name="m_team_mat")
public class MTeamMat extends MCoreBase {
	private static final long serialVersionUID = 1L;
	private Integer teamNo;
	private String matNo;
	private String matCode;

	public Integer getTeamNo() {
		return teamNo;
	}

	public void setTeamNo(Integer teamNo) {
		this.teamNo = teamNo;
	}

	public String getMatNo() {
		return matNo;
	}

	public void setMatNo(String matNo) {
		this.matNo = matNo;
	}

	public String getMatCode() {
		return matCode;
	}

	public void setMatCode(String matCode) {
		this.matCode = matCode;
	}
}