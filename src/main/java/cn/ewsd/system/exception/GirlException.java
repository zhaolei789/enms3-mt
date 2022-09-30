package cn.ewsd.system.exception;

import cn.ewsd.system.enums.ResultEnum;

/**
 * @author 小策一喋<xvpindex@qq.com>
 * @className GirlException
 * @description
 * @date 2018-03-29 10:29
 */
public class GirlException extends RuntimeException {

    private Integer statusCode;

    public GirlException(ResultEnum resultEnum) {
        super(resultEnum.getMeesage());
        this.statusCode = resultEnum.getStatusCode();
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
