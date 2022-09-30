package cn.ewsd.material.model;

import java.math.BigDecimal;

public class MPurchasePlan {
    private String purNo;
    private String planNo;
    private BigDecimal planAmount;
    private BigDecimal buyAmount;

    public String getPurNo() {
        return purNo;
    }

    public void setPurNo(String purNo) {
        this.purNo = purNo;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public BigDecimal getPlanAmount() {
        return planAmount;
    }

    public void setPlanAmount(BigDecimal planAmount) {
        this.planAmount = planAmount;
    }

    public BigDecimal getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(BigDecimal buyAmount) {
        this.buyAmount = buyAmount;
    }
}
