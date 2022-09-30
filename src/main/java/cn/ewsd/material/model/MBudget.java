package cn.ewsd.material.model;

import java.math.BigDecimal;

public class MBudget {
    private String budId;
    private String itemNo;
    private Integer teamNo;
    private String teamName;
    private String occMonth;
    private BigDecimal iniBala;
    private BigDecimal addBala;
    private BigDecimal overAdd;
    private BigDecimal budBala;
    private BigDecimal yearBudget;
    private BigDecimal sumBudget;

    private String itemName;
    private BigDecimal yfjBala;
    private BigDecimal occBala;

    public BigDecimal getYfjBala() {
        return yfjBala;
    }

    public void setYfjBala(BigDecimal yfjBala) {
        this.yfjBala = yfjBala;
    }

    public BigDecimal getOccBala() {
        return occBala;
    }

    public void setOccBala(BigDecimal occBala) {
        this.occBala = occBala;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getYearBudget() {
        return yearBudget;
    }

    public void setYearBudget(BigDecimal yearBudget) {
        this.yearBudget = yearBudget;
    }

    public BigDecimal getSumBudget() {
        return sumBudget;
    }

    public void setSumBudget(BigDecimal sumBudget) {
        this.sumBudget = sumBudget;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getBudId() {
        return budId;
    }

    public void setBudId(String budId) {
        this.budId = budId;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public Integer getTeamNo() {
        return teamNo;
    }

    public void setTeamNo(Integer teamNo) {
        this.teamNo = teamNo;
    }

    public String getOccMonth() {
        return occMonth;
    }

    public void setOccMonth(String occMonth) {
        this.occMonth = occMonth;
    }

    public BigDecimal getIniBala() {
        return iniBala;
    }

    public void setIniBala(BigDecimal iniBala) {
        this.iniBala = iniBala;
    }

    public BigDecimal getAddBala() {
        return addBala;
    }

    public void setAddBala(BigDecimal addBala) {
        this.addBala = addBala;
    }

    public BigDecimal getOverAdd() {
        return overAdd;
    }

    public void setOverAdd(BigDecimal overAdd) {
        this.overAdd = overAdd;
    }

    public BigDecimal getBudBala() {
        return budBala;
    }

    public void setBudBala(BigDecimal budBala) {
        this.budBala = budBala;
    }
}
