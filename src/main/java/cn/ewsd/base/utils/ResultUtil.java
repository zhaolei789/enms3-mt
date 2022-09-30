package cn.ewsd.base.utils;


import cn.ewsd.mdata.dto.Result;

/**
 * @className ResultUtil
 * @description 
 * @author 小策一喋<xvpindex@qq.com>
 * @date 2018-03-29 10:45
 */
public class ResultUtil {

    public static Result success(Object object) {
        Result result = new Result();
        result.setStatusCode(200);
        result.setMessage("成功");
        result.setData(object);
        return result;
    }

    public static Result success() {
        return success(null);
    }

    public static Result error(Integer statusCode, String message) {
        Result result = new Result();
        result.setStatusCode(statusCode);
        result.setMessage(message);
        return result;
    }

}
