package cn.ewsd.material.model;

import cn.ewsd.common.model.MCoreBase;

import java.math.BigDecimal;

public class PlanCheckIndex extends MCoreBase {
    private String planType;
    private String planMonth;
    private String prjNo;
    private String planStep;
    private String planStepName;
    private Integer cnt;
    private BigDecimal bala;
    private String prjType2;
    private String prjType2Name;
    private String upName;
    private String prjName;
    private Integer teamNo;
    private String teamName;
    private String beginDate;

    public String getPrjType2Name() {
        return prjType2Name;
    }

    public void setPrjType2Name(String prjType2Name) {
        this.prjType2Name = prjType2Name;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getPlanStepName() {
        return planStepName;
    }

    public void setPlanStepName(String planStepName) {
        this.planStepName = planStepName;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getPlanMonth() {
        return planMonth;
    }

    public void setPlanMonth(String planMonth) {
        this.planMonth = planMonth;
    }

    public String getPrjNo() {
        return prjNo;
    }

    public void setPrjNo(String prjNo) {
        this.prjNo = prjNo;
    }

    public String getPlanStep() {
        return planStep;
    }

    public void setPlanStep(String planStep) {
        this.planStep = planStep;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public BigDecimal getBala() {
        return bala;
    }

    public void setBala(BigDecimal bala) {
        this.bala = bala;
    }

    public String getPrjType2() {
        return prjType2;
    }

    public void setPrjType2(String prjType2) {
        this.prjType2 = prjType2;
    }

    public String getUpName() {
        return upName;
    }

    public void setUpName(String upName) {
        this.upName = upName;
    }

    public String getPrjName() {
        return prjName;
    }

    public void setPrjName(String prjName) {
        this.prjName = prjName;
    }

    public Integer getTeamNo() {
        return teamNo;
    }

    public void setTeamNo(Integer teamNo) {
        this.teamNo = teamNo;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }
}
