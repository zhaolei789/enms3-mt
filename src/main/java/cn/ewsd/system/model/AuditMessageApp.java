package cn.ewsd.system.model;

/**
 * 审核消息
 */
public class AuditMessageApp implements Comparable<AuditMessageApp> {
    private String messageId;//32位随机ID
    private String messageName;//消息描述
    private Integer messageType;//消息类型 1通知2待办
    private Integer messageNum;//消息数量
    private Integer messageSort;//消息排序
    private String jumpPageUrl;//跳转页面路径
    private String jumpPageName;//跳转页面名称
    private String businessId;//业务ID
    private String param1;
    private String param2;

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageName() {
        return messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public Integer getMessageNum() {
        return messageNum;
    }

    public void setMessageNum(Integer messageNum) {
        this.messageNum = messageNum;
    }

    public Integer getMessageSort() {
        return messageSort;
    }

    public void setMessageSort(Integer messageSort) {
        this.messageSort = messageSort;
    }

    public String getJumpPageUrl() {
        return jumpPageUrl;
    }

    public void setJumpPageUrl(String jumpPageUrl) {
        this.jumpPageUrl = jumpPageUrl;
    }

    public String getJumpPageName() {
        return jumpPageName;
    }

    public void setJumpPageName(String jumpPageName) {
        this.jumpPageName = jumpPageName;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    @Override
    public int compareTo(AuditMessageApp o) {
        return this.messageSort - o.messageSort;
    }
}
