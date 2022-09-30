package cn.ewsd.base.utils;

/**
 * 数据源工具类
 *
 * @author 小策一喋
 * @email xvpindex@qq.com
 * @date 2018年12月28日 上午11:40:24
 */
public class DatasourceUtils {

    public static String getDatasourceTypeByDriverClassName(String driverClassName) {
        if (driverClassName.contains("mysql"))
            return "mysql";
        if (driverClassName.contains("sqlserver"))
            return "sqlserver";
        if (driverClassName.contains("oracle"))
            return "oracle";
        return "";
    }

}
