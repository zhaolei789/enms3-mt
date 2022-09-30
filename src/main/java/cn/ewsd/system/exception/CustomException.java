package cn.ewsd.system.exception;

//系统自定义异常处理类,针对预期的异常，需要在程序中抛出此类的异常
public class CustomException extends RuntimeException {

    //异常信息
    private Integer statusCode;
    private String message;

    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    public CustomException(Integer statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
    }

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
}
