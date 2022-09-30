package cn.ewsd.base.bean;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @className SystemMapper
 * @description https://blog.csdn.net/qq_19260029/article/details/78010369?locationNum=5&fps=1
 */
//本地通用Mapper接口（继承通用Mapper接口）
public interface CommonMapper<T> extends Mapper<T>, MySqlMapper<T> {

}