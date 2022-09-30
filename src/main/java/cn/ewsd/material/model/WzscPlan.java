package cn.ewsd.material.model;

import cn.ewsd.common.model.MCoreBase;

import java.math.BigDecimal;

public class WzscPlan extends MCoreBase {
    private String epId;
    private String matNo;
    private String matCode;
    private String matName;
    private String matUnit;
    private String erpType;
    private String typeName;
    private BigDecimal matAmount;
    private BigDecimal matPrice;
    private String factoryNo;
    private String centerNo;
    private String centerName;
    private String matAddr;
    private String matAddrName;
    private String itemType;
    private String wbs;
    private String planType;
    private String planTypeName;
    private String request;
    private String purchaseNo;
    private String purchaseList;
    private String remark;
    private String createDate;
    private String requestDate;
    private String status;
    private String eccMsg;
    private String supplier;
    private String supplierName;
    private String followNo;
    private BigDecimal amount;
    private BigDecimal bala;
    private BigDecimal matBala;

    private BigDecimal sumBala;
    private BigDecimal applyed;
    private String prucList;
    private BigDecimal arriveAmount;
    private String arriveDate;
    private BigDecimal stockAmount;
    private BigDecimal outAmount;
    private BigDecimal outBala;
    private BigDecimal arriveBala;
    private BigDecimal stockBala;

    public BigDecimal getArriveAmount() {
        return arriveAmount;
    }

    public void setArriveAmount(BigDecimal arriveAmount) {
        this.arriveAmount = arriveAmount;
    }

    public String getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(String arriveDate) {
        this.arriveDate = arriveDate;
    }

    public BigDecimal getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(BigDecimal stockAmount) {
        this.stockAmount = stockAmount;
    }

    public BigDecimal getOutAmount() {
        return outAmount;
    }

    public void setOutAmount(BigDecimal outAmount) {
        this.outAmount = outAmount;
    }

    public BigDecimal getOutBala() {
        return outBala;
    }

    public void setOutBala(BigDecimal outBala) {
        this.outBala = outBala;
    }

    public BigDecimal getArriveBala() {
        return arriveBala;
    }

    public void setArriveBala(BigDecimal arriveBala) {
        this.arriveBala = arriveBala;
    }

    public BigDecimal getStockBala() {
        return stockBala;
    }

    public void setStockBala(BigDecimal stockBala) {
        this.stockBala = stockBala;
    }

    public String getMatNo() {
        return matNo;
    }

    public void setMatNo(String matNo) {
        this.matNo = matNo;
    }

    public String getPrucList() {
        return prucList;
    }

    public void setPrucList(String prucList) {
        this.prucList = prucList;
    }

    public BigDecimal getApplyed() {
        return applyed;
    }

    public void setApplyed(BigDecimal applyed) {
        this.applyed = applyed;
    }

    public String getMatAddrName() {
        return matAddrName;
    }

    public void setMatAddrName(String matAddrName) {
        this.matAddrName = matAddrName;
    }

    public BigDecimal getSumBala() {
        return sumBala;
    }

    public void setSumBala(BigDecimal sumBala) {
        this.sumBala = sumBala;
    }

    public String getPlanTypeName() {
        return planTypeName;
    }

    public void setPlanTypeName(String planTypeName) {
        this.planTypeName = planTypeName;
    }

    public BigDecimal getMatBala() {
        return matBala;
    }

    public void setMatBala(BigDecimal matBala) {
        this.matBala = matBala;
    }

    public BigDecimal getBala() {
        return bala;
    }

    public void setBala(BigDecimal bala) {
        this.bala = bala;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getEpId() {
        return epId;
    }

    public void setEpId(String epId) {
        this.epId = epId;
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

    public BigDecimal getMatAmount() {
        return matAmount;
    }

    public void setMatAmount(BigDecimal matAmount) {
        this.matAmount = matAmount;
    }

    public BigDecimal getMatPrice() {
        return matPrice;
    }

    public void setMatPrice(BigDecimal matPrice) {
        this.matPrice = matPrice;
    }

    public String getFactoryNo() {
        return factoryNo;
    }

    public void setFactoryNo(String factoryNo) {
        this.factoryNo = factoryNo;
    }

    public String getCenterNo() {
        return centerNo;
    }

    public void setCenterNo(String centerNo) {
        this.centerNo = centerNo;
    }

    public String getMatAddr() {
        return matAddr;
    }

    public void setMatAddr(String matAddr) {
        this.matAddr = matAddr;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getWbs() {
        return wbs;
    }

    public void setWbs(String wbs) {
        this.wbs = wbs;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getPurchaseNo() {
        return purchaseNo;
    }

    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo;
    }

    public String getPurchaseList() {
        return purchaseList;
    }

    public void setPurchaseList(String purchaseList) {
        this.purchaseList = purchaseList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEccMsg() {
        return eccMsg;
    }

    public void setEccMsg(String eccMsg) {
        this.eccMsg = eccMsg;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getFollowNo() {
        return followNo;
    }

    public void setFollowNo(String followNo) {
        this.followNo = followNo;
    }
}
