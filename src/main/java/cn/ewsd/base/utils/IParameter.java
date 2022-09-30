package cn.ewsd.base.utils;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求参数工具类
 * 
 * @author 冯俊伟
 * @version 1.0.0
 * @since 1.0.0
 */
public class IParameter {
	private Map<String, String[]> parameterMap;
	private HttpServletRequest request;
	
	/**
	 * 构造函数，初始化参数数据
	 * 
	 * @param request http请求
	 * @since 1.0.0
	 */
	public IParameter(HttpServletRequest request){
		parameterMap = (Map<String, String[]>)request.getParameterMap();
		this.request = request;
	}
	
	/**
	 * 通过参数名获取参数值
	 * 
	 * @param parameterName 参数名
	 * @return 参数值，没有对应值时返回“”
	 * @since 1.0.0
	 */
	public String getParameter(String parameterName){
		String[] rets = parameterMap.get(parameterName);
		String ret = "";
		if(rets != null) ret = rets[0];
		return ret;
	}
	
	/**
	 * 通过参数名获取参数值，并设置到request中
	 * 
	 * @param parameterName 参数名
	 * @param defaultValue 默认值
	 * @return 参数值，没有对应值时返回默认值
	 * @since 1.0.0
	 */
	public String getParameter(String parameterName, String defaultValue){
		String ret = getParameter(parameterName);
		if("".equals(ret)) ret = defaultValue;
		request.setAttribute(parameterName, ret);
		return ret;
	}
	
	/**
	 * 通过参数名获取一组参数值，例如一组checkbox的值
	 * 
	 * @param parameterName 参数名
	 * @return 参数值数组，没有对应值时返回长度为0的字符串数组
	 * @since 1.0.0
	 */
	public String[] getParameters(String parameterName){
		if(parameterMap.get(parameterName) != null){
			return parameterMap.get(parameterName);
		}
		return new String[0];
	}
	
	/**
	 * 通过参数名获取参数值（int），并设置到request中
	 * 
	 * @param parameterName 参数名
	 * @return 参数值，没有对应值或转换int失败时返回0
	 * @since 1.0.0
	 */
	public Integer getIntParameter(String parameterName){
		String ret = getParameter(parameterName);
		try {
			return Integer.parseInt(ret);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 通过参数名获取参数值（int），并设置到request中
	 * 
	 * @param parameterName 参数名
	 * @param defaultValue 默认值
	 * @return 参数值，没有对应值或转换int失败时返回0
	 * @since 1.0.0
	 */
	public Integer getIntParameter(String parameterName, int defaultValue){
		String ret = getParameter(parameterName, defaultValue+"");
		try {
			return Integer.parseInt(ret);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 通过参数名获取参数值（double），并设置到request中
	 * 
	 * @param parameterName 参数名
	 * @return 参数值，没有对应值或转换double失败时返回0
	 * @since 1.0.0
	 */
	public Double getDoubleParameter(String parameterName){
		String ret = getParameter(parameterName);
		try {
			return Double.parseDouble(ret);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 通过参数名获取参数值（double）， 并设置到request中
	 * 
	 * @param parameterName 参数名
	 * @param defaultValue 默认值
	 * @return 参数值，没有对应值或转换double失败时返回0
	 * @since 1.0.0
	 */
	public Double getDoubleParameter(String parameterName, double defaultValue){
		String ret = getParameter(parameterName, defaultValue+"");
		try {
			return Double.parseDouble(ret);
		} catch (Exception e) {
			return null;
		}
	}
	
	public Boolean getBooleanParameter(String parameterName){
		String ret = getParameter(parameterName);
		try {
			return Boolean.parseBoolean(ret);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 获取所有的参数
	 * 
	 * @return 参数名，参数值map列表
	 * @since 1.0.0
	 */
	public Map<String, String[]> getAllParameters(){
		return parameterMap;
	}
}
