package cn.ewsd.material.model;

import cn.ewsd.common.model.MCoreBase;

import java.math.BigDecimal;

public class Riku extends MCoreBase {
    private String matNo;
    private String matCode;
    private String matName;
    private String matUnit;
    private String matUnitName;
    private BigDecimal matPrice;
    private String erpType;
    private String typeName;
    private String planNo;
    private Integer teamNo;
    private String teamName;
    private BigDecimal matAmount;
    private String abcType;
    private String abcTypeName;
    private BigDecimal bala;
    private String costCenter;
    private BigDecimal planAmount;
    private BigDecimal tgAmount;
    private BigDecimal sfAmount;
    private BigDecimal calcAmount;

    public BigDecimal getPlanAmount() {
        return planAmount;
    }

    public void setPlanAmount(BigDecimal planAmount) {
        this.planAmount = planAmount;
    }

    public BigDecimal getCalcAmount() {
        return calcAmount;
    }

    public void setCalcAmount(BigDecimal calcAmount) {
        this.calcAmount = calcAmount;
    }

    public String getAbcTypeName() {
        return abcTypeName;
    }

    public void setAbcTypeName(String abcTypeName) {
        this.abcTypeName = abcTypeName;
    }

    public BigDecimal getTgAmount() {
        return tgAmount;
    }

    public void setTgAmount(BigDecimal tgAmount) {
        this.tgAmount = tgAmount;
    }

    public BigDecimal getSfAmount() {
        return sfAmount;
    }

    public void setSfAmount(BigDecimal sfAmount) {
        this.sfAmount = sfAmount;
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

    public String getMatName() {
        return matName;
    }

    public void setMatName(String matName) {
        this.matName = matName;
    }

    public String getMatUnit() {
        return matUnit;
    }

    public void setMatUnit(String matUnit) {
        this.matUnit = matUnit;
    }

    public String getMatUnitName() {
        return matUnitName;
    }

    public void setMatUnitName(String matUnitName) {
        this.matUnitName = matUnitName;
    }

    public BigDecimal getMatPrice() {
        return matPrice;
    }

    public void setMatPrice(BigDecimal matPrice) {
        this.matPrice = matPrice;
    }

    public String getErpType() {
        return erpType;
    }

    public void setErpType(String erpType) {
        this.erpType = erpType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public Integer getTeamNo() {
        return teamNo;
    }

    public void setTeamNo(Integer teamNo) {
        this.teamNo = teamNo;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public BigDecimal getMatAmount() {
        return matAmount;
    }

    public void setMatAmount(BigDecimal matAmount) {
        this.matAmount = matAmount;
    }

    public String getAbcType() {
        return abcType;
    }

    public void setAbcType(String abcType) {
        this.abcType = abcType;
    }

    public BigDecimal getBala() {
        return bala;
    }

    public void setBala(BigDecimal bala) {
        this.bala = bala;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }
}
