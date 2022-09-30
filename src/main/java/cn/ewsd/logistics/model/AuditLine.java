package cn.ewsd.logistics.model;

public class AuditLine {
    private String LINE_NAME;
    private Integer LINE_SORT;//路线排序
    private Integer LINE_STATUS;//路线状态 1待审2已审
    private String AUDIT_USER_NAME;//审批人员姓名
    private String AUDIT_USER_NAME_ID;//审批用户编号
    private String AUDIT_OPINION;//审批意见
    private String AUDIT_DATE;//审批时间
    private Integer IS_RUNNING;//1进行中


    public Integer getIS_RUNNING() {
        return IS_RUNNING;
    }

    public void setIS_RUNNING(Integer IS_RUNNING) {
        this.IS_RUNNING = IS_RUNNING;
    }

    public String getLINE_NAME() {
        return LINE_NAME;
    }

    public void setLINE_NAME(String LINE_NAME) {
        this.LINE_NAME = LINE_NAME;
    }

    public Integer getLINE_SORT() {
        return LINE_SORT;
    }

    public void setLINE_SORT(Integer LINE_SORT) {
        this.LINE_SORT = LINE_SORT;
    }

    public Integer getLINE_STATUS() {
        return LINE_STATUS;
    }

    public void setLINE_STATUS(Integer LINE_STATUS) {
        this.LINE_STATUS = LINE_STATUS;
    }

    public String getAUDIT_USER_NAME() {
        return AUDIT_USER_NAME;
    }

    public void setAUDIT_USER_NAME(String AUDIT_USER_NAME) {
        this.AUDIT_USER_NAME = AUDIT_USER_NAME;
    }

    public String getAUDIT_USER_NAME_ID() {
        return AUDIT_USER_NAME_ID;
    }

    public void setAUDIT_USER_NAME_ID(String AUDIT_USER_NAME_ID) {
        this.AUDIT_USER_NAME_ID = AUDIT_USER_NAME_ID;
    }

    public String getAUDIT_OPINION() {
        return AUDIT_OPINION;
    }

    public void setAUDIT_OPINION(String AUDIT_OPINION) {
        this.AUDIT_OPINION = AUDIT_OPINION;
    }

    public String getAUDIT_DATE() {
        return AUDIT_DATE;
    }

    public void setAUDIT_DATE(String AUDIT_DATE) {
        this.AUDIT_DATE = AUDIT_DATE;
    }
}
