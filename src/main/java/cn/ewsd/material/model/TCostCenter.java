package cn.ewsd.material.model;

import cn.ewsd.common.model.MCoreBase;

import javax.persistence.Table;

@Table(name="t_cost_center")
public class TCostCenter extends MCoreBase {
    private String centerNo;
    private String centerName;
    private String moveType;
    private String moveTypeName;
    private String deptNo;
    private String deptName;

    public String getMoveTypeName() {
        return moveTypeName;
    }

    public void setMoveTypeName(String moveTypeName) {
        this.moveTypeName = moveTypeName;
    }

    public String getCenterNo() {
        return centerNo;
    }

    public void setCenterNo(String centerNo) {
        this.centerNo = centerNo;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getMoveType() {
        return moveType;
    }

    public void setMoveType(String moveType) {
        this.moveType = moveType;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
