package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;

import java.math.BigDecimal;

public class TCheck  extends MCoreBase {
    private String checkNo;
    private String stepKey;
    private String stepCode;
    private String stepCodeName;
    private String checkType;
    private String direct;
    private String directName;
    private String idea;
    private String userId;
    private String userName;
    private Long empId;
    private Integer teamNo;
    private String occDate;
    private String occTime;
    private String logInfo;
    private BigDecimal befAmount;
    private BigDecimal befPrice;
    private BigDecimal aftAmount;
    private BigDecimal aftPrice;
    private String tcUserName;

    public String getTcUserName() {
        return tcUserName;
    }

    public void setTcUserName(String tcUserName) {
        this.tcUserName = tcUserName;
    }

    public String getDirectName() {
        return directName;
    }

    public void setDirectName(String directName) {
        this.directName = directName;
    }

    public String getStepCodeName() {
        return stepCodeName;
    }

    public void setStepCodeName(String stepCodeName) {
        this.stepCodeName = stepCodeName;
    }

    public String getCheckNo() {
        return checkNo;
    }

    public void setCheckNo(String checkNo) {
        this.checkNo = checkNo;
    }

    public String getStepKey() {
        return stepKey;
    }

    public void setStepKey(String stepKey) {
        this.stepKey = stepKey;
    }

    public String getStepCode() {
        return stepCode;
    }

    public void setStepCode(String stepCode) {
        this.stepCode = stepCode;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

    public String getIdea() {
        return idea;
    }

    public void setIdea(String idea) {
        this.idea = idea;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Integer getTeamNo() {
        return teamNo;
    }

    public void setTeamNo(Integer teamNo) {
        this.teamNo = teamNo;
    }

    public String getOccDate() {
        return occDate;
    }

    public void setOccDate(String occDate) {
        this.occDate = occDate;
    }

    public String getOccTime() {
        return occTime;
    }

    public void setOccTime(String occTime) {
        this.occTime = occTime;
    }

    public String getLogInfo() {
        return logInfo;
    }

    public void setLogInfo(String logInfo) {
        this.logInfo = logInfo;
    }

    public BigDecimal getBefAmount() {
        return befAmount;
    }

    public void setBefAmount(BigDecimal befAmount) {
        this.befAmount = befAmount;
    }

    public BigDecimal getBefPrice() {
        return befPrice;
    }

    public void setBefPrice(BigDecimal befPrice) {
        this.befPrice = befPrice;
    }

    public BigDecimal getAftAmount() {
        return aftAmount;
    }

    public void setAftAmount(BigDecimal aftAmount) {
        this.aftAmount = aftAmount;
    }

    public BigDecimal getAftPrice() {
        return aftPrice;
    }

    public void setAftPrice(BigDecimal aftPrice) {
        this.aftPrice = aftPrice;
    }
}
