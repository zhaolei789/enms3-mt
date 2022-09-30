package cn.ewsd.forest.model.getToken;

public class getTokenObj {
    private String code;
    private String msg;
    private Long ctime;
    private String requestID;
    private getTokenData data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getCtime() {
        return ctime;
    }

    public void setCtime(Long ctime) {
        this.ctime = ctime;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public getTokenData getData() {
        return data;
    }

    public void setData(getTokenData data) {
        this.data = data;
    }
}
