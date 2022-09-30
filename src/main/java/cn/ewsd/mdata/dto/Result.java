package cn.ewsd.mdata.dto;

/**
 * @className Result
 * @description http请求返回的最外层对象
 * @author 小策一喋<xvpindex@qq.com>
 * @date 2018-03-29 10:15
 */
public class Result<T> {

    //状态码
    private Integer statusCode;
    //提示信息
    private String message;
    //具体内容
    private T data;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
