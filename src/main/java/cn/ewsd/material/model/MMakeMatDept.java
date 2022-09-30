package cn.ewsd.material.model;

import cn.ewsd.common.model.MCoreBase;

import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name="m_make_mat_dept")
public class MMakeMatDept extends MCoreBase {
    private String matNo;
    private Integer makeDept;
    private BigDecimal makePrice;
    private String makeDeptName;

    public String getMakeDeptName() {
        return makeDeptName;
    }

    public void setMakeDeptName(String makeDeptName) {
        this.makeDeptName = makeDeptName;
    }

    public String getMatNo() {
        return matNo;
    }

    public void setMatNo(String matNo) {
        this.matNo = matNo;
    }

    public Integer getMakeDept() {
        return makeDept;
    }

    public void setMakeDept(Integer makeDept) {
        this.makeDept = makeDept;
    }

    public BigDecimal getMakePrice() {
        return makePrice;
    }

    public void setMakePrice(BigDecimal makePrice) {
        this.makePrice = makePrice;
    }
}
