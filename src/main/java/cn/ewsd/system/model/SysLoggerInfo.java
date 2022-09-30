package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;
import lombok.Data;

import javax.persistence.Table;
import java.util.Date;


@Data
@Table(name = "sys_logger_info")
public class SysLoggerInfo extends MCoreBase {

    private String aliClientIp;
    private String aliUri;
    private String aliType;
    private String aliMethod;
    private String aliParamData;
    private String aliSessionId;
    private Date aliTime;
    private String aliReturnTime;
    private Integer aliHttpStatusCode;
    private Integer aliTimeConsuming;

    public String getAliClientIp() {
        return aliClientIp;
    }

    public void setAliClientIp(String aliClientIp) {
        this.aliClientIp = aliClientIp;
    }

    public String getAliUri() {
        return aliUri;
    }

    public void setAliUri(String aliUri) {
        this.aliUri = aliUri;
    }

    public String getAliType() {
        return aliType;
    }

    public void setAliType(String aliType) {
        this.aliType = aliType;
    }

    public String getAliMethod() {
        return aliMethod;
    }

    public void setAliMethod(String aliMethod) {
        this.aliMethod = aliMethod;
    }

    public String getAliParamData() {
        return aliParamData;
    }

    public void setAliParamData(String aliParamData) {
        this.aliParamData = aliParamData;
    }

    public String getAliSessionId() {
        return aliSessionId;
    }

    public void setAliSessionId(String aliSessionId) {
        this.aliSessionId = aliSessionId;
    }

    public Date getAliTime() {
        return aliTime;
    }

    public void setAliTime(Date aliTime) {
        this.aliTime = aliTime;
    }

    public String getAliReturnTime() {
        return aliReturnTime;
    }

    public void setAliReturnTime(String aliReturnTime) {
        this.aliReturnTime = aliReturnTime;
    }

    public Integer getAliHttpStatusCode() {
        return aliHttpStatusCode;
    }

    public void setAliHttpStatusCode(Integer aliHttpStatusCode) {
        this.aliHttpStatusCode = aliHttpStatusCode;
    }

    public Integer getAliTimeConsuming() {
        return aliTimeConsuming;
    }

    public void setAliTimeConsuming(Integer aliTimeConsuming) {
        this.aliTimeConsuming = aliTimeConsuming;
    }
}