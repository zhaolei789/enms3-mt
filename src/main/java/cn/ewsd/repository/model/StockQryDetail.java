package cn.ewsd.repository.model;

import cn.ewsd.common.model.MCoreBase;

import java.math.BigDecimal;

public class StockQryDetail extends MCoreBase {
    private String storeName;
    private String billDate;
    private String inEmpName;
    private BigDecimal billAmount;
    private BigDecimal setPrice;
    private BigDecimal bala;
    private String teamName;
    private String storeName1;
    private String outDate;
    private String empName;
    private String drawEmpName;
    private BigDecimal outAmount;
    private BigDecimal matPrice;
    private BigDecimal outBala;
    private String inType;
    private String inDate;
    private BigDecimal amount;
    private BigDecimal price;
    private BigDecimal inBala;
    private String prjName;
    private String occDate;
    private BigDecimal occAmount;

    public String getInType() {
        return inType;
    }

    public void setInType(String inType) {
        this.inType = inType;
    }

    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getInBala() {
        return inBala;
    }

    public void setInBala(BigDecimal inBala) {
        this.inBala = inBala;
    }

    public String getPrjName() {
        return prjName;
    }

    public void setPrjName(String prjName) {
        this.prjName = prjName;
    }

    public String getOccDate() {
        return occDate;
    }

    public void setOccDate(String occDate) {
        this.occDate = occDate;
    }

    public BigDecimal getOccAmount() {
        return occAmount;
    }

    public void setOccAmount(BigDecimal occAmount) {
        this.occAmount = occAmount;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getInEmpName() {
        return inEmpName;
    }

    public void setInEmpName(String inEmpName) {
        this.inEmpName = inEmpName;
    }

    public BigDecimal getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(BigDecimal billAmount) {
        this.billAmount = billAmount;
    }

    public BigDecimal getSetPrice() {
        return setPrice;
    }

    public void setSetPrice(BigDecimal setPrice) {
        this.setPrice = setPrice;
    }

    public BigDecimal getBala() {
        return bala;
    }

    public void setBala(BigDecimal bala) {
        this.bala = bala;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getStoreName1() {
        return storeName1;
    }

    public void setStoreName1(String storeName1) {
        this.storeName1 = storeName1;
    }

    public String getOutDate() {
        return outDate;
    }

    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getDrawEmpName() {
        return drawEmpName;
    }

    public void setDrawEmpName(String drawEmpName) {
        this.drawEmpName = drawEmpName;
    }

    public BigDecimal getOutAmount() {
        return outAmount;
    }

    public void setOutAmount(BigDecimal outAmount) {
        this.outAmount = outAmount;
    }

    public BigDecimal getMatPrice() {
        return matPrice;
    }

    public void setMatPrice(BigDecimal matPrice) {
        this.matPrice = matPrice;
    }

    public BigDecimal getOutBala() {
        return outBala;
    }

    public void setOutBala(BigDecimal outBala) {
        this.outBala = outBala;
    }
}
