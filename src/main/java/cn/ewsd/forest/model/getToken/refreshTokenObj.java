package cn.ewsd.forest.model.getToken;

public class refreshTokenObj {
    private String code;
    private String msg;
    private Long ctime;
    private String requestID;
    private refreshTokenData data;

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

    public refreshTokenData getData() {
        return data;
    }

    public void setData(refreshTokenData data) {
        this.data = data;
    }
}
