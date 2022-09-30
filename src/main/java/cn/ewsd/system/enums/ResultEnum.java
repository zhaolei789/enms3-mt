package cn.ewsd.system.enums;

/**
 * @author ZhaoXiace
 * @className ResultEnum
 * @description TODO
 * @date 2018-03-29 13:56
 */
public enum ResultEnum {
    UNKNOW_ERROR(-1, "未知错误"),
    SUCCESS(0, "成功"),
    PRIMARY_SCHOOL(100, "你可能还在上小学"),
    MIDDLE_SCHOOL(101, "你可能在上初中"),
    SUPER_SCHOOL(102, "你可能在上大学");

    private Integer statusCode;

    private String meesage;

    ResultEnum(Integer statusCode, String meesage) {
        this.statusCode = statusCode;
        this.meesage = meesage;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMeesage() {
        return meesage;
    }

    public void setMeesage(String meesage) {
        this.meesage = meesage;
    }
}
