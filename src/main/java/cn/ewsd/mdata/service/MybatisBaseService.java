package cn.ewsd.mdata.service;


import cn.ewsd.base.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.List;

/**
 * @className IMybatisBaseService
 * @description 
 * @author 小策一喋<xvpindex@qq.com>
 * @date 2017-03-28 14:39
 */
public interface MybatisBaseService<T, PK extends Serializable> {
    
    /**
     * @functionName selectByPrimaryKey
     * @description 根据主键查询一条数据
     * @param pk 主键
     * @return T
     * @author 小策一喋<xvpindex@qq.com>
     * @date 2017-03-28 14:32
     */
    T selectByPrimaryKey(PK pk);
    
    /**
     * @functionName selectOne
     * @description 查询一条记录
     * @param param 
     * @return T
     * @author 小策一喋<xvpindex@qq.com>
     * @date 2017-03-28 14:32
     */
    T selectOne(T param);
    
    /**
     * @functionName selectAll
     * @description 查询所有
     * @param  
     * @return java.util.List<T>
     * @author 小策一喋<xvpindex@qq.com>
     * @date 2017-03-28 14:33
     */
    List<T> selectAll();
    
    /**
     * @functionName selectByExample
     * @description 查询数据
     * @param example 
     * @return java.util.List<T>
     * @author 小策一喋<xvpindex@qq.com>
     * @date 2017-03-28 14:33
     */
    List<T> selectByExample(Example example);
    
    /**
     * @functionName select
     * @description 条件查询
     * @param param 
     * @return java.util.List<T>
     * @author 小策一喋<xvpindex@qq.com>
     * @date 2017-03-28 14:33
     */
    List<T> select(T param);
    
    /**
     * @functionName selectCount
     * @description 查询记录数
     * @param param 
     * @return java.lang.Integer
     * @author 小策一喋<xvpindex@qq.com>
     * @date 2017-03-28 14:33
     */
    Integer selectCount(T param);
    
    /**
     * @functionName selectCountByExample
     * @description 查询数量
     * @param example 
     * @return java.lang.Integer
     * @author 小策一喋<xvpindex@qq.com>
     * @date 2017-03-28 14:33
     */
    Integer selectCountByExample(Example example);
    
    /**
     * @functionName selectPageListByWhere
     * @description 根据实体条件分页查询
     * @param param 
     * @param page 
     * @param rows 
     * @return cn.ewsd.common.utils.easyui.PageSet<T>
     * @author 小策一喋<xvpindex@qq.com>
     * @date 2017-03-28 14:33
     */
    PageSet<T> selectPageListByWhere(T param, Integer page, Integer rows);
    
    /**
     * @functionName selectPageSetByExample
     * @description 根据example分页查询，带过滤条件
     * @param clazz 
     * @param pageParam 
     * @param filterSort 
     * @return cn.ewsd.common.utils.easyui.PageSet<T>
     * @author 小策一喋<xvpindex@qq.com>
     * @date 2017-03-28 14:34
     */
    PageSet<T> selectPageSetByExample(Class<T> clazz, PageParam pageParam, String filterSort);
    
    /**
     * @functionName selectPageSetByExample
     * @description 根据example分页查询
     * @param example 
     * @param pageParam 
     * @return cn.ewsd.common.utils.easyui.PageSet<T>
     * @author 小策一喋<xvpindex@qq.com>
     * @date 2017-03-28 14:34
     */
    PageSet<T> selectPageSetByExample(Example example, PageParam pageParam);
    
    /**
     * @functionName insert
     * @description 插入实体所有字段
     * @param param 
     * @return java.lang.Integer
     * @author 小策一喋<xvpindex@qq.com>
     * @date 2017-03-28 14:34
     */
    Integer insert(T param);
    
    /**
     * @functionName insertSelective
     * @description 插入实体非空字段
     * @param param 
     * @return java.lang.Integer
     * @author 小策一喋<xvpindex@qq.com>
     * @date 2017-03-28 14:35
     */
    Integer insertSelective(T param);
    
    /**
     * @functionName updateByPrimaryKey
     * @description 根据主键更新
     * @param param 
     * @return java.lang.Integer
     * @author 小策一喋<xvpindex@qq.com>
     * @date 2017-03-28 14:35
     */
    Integer updateByPrimaryKey(T param);
    
    /**
     * @functionName updateByPrimaryKeySelective
     * @description 根据主键更新非空字段
     * @param param 
     * @return java.lang.Integer
     * @author 小策一喋<xvpindex@qq.com>
     * @date 2017-03-28 14:35
     */
    Integer updateByPrimaryKeySelective(T param);
    
    /**
     * @functionName updateByExample
     * @description 更新数据
     * @param t 
     * @param example 
     * @return java.lang.Integer
     * @author 小策一喋<xvpindex@qq.com>
     * @date 2017-03-28 14:35
     */
    Integer updateByExample(T t, Example example);
    
    /**
     * @functionName updateByExampleSelective
     * @description 更新不为空的数据
     * @param t 
     * @param example 
     * @return java.lang.Integer
     * @author 小策一喋<xvpindex@qq.com>
     * @date 2017-03-28 14:35
     */
    Integer updateByExampleSelective(T t, Example example);

    /**
     * @functionName deleteByPrimaryKey
     * @description 根据主键删除单条数据
     * @param pk 
     * @return java.lang.Integer
     * @author 小策一喋<xvpindex@qq.com>
     * @date 2017-03-28 14:35
     */
    Integer deleteByPrimaryKey(PK pk);

    /**
     * @functionName delete
     * @description 通过实体删除数据
     * @param t 
     * @return java.lang.Integer
     * @author 小策一喋<xvpindex@qq.com>
     * @date 2017-03-28 14:36
     */
    Integer delete(T t);

    /**
     * @functionName deleteByExample
     * @description 通过example删除数据
     * @param example
     * @return java.lang.Integer
     * @author 小策一喋<xvpindex@qq.com>
     * @date 2017-03-28 14:36
     */
    Integer deleteByExample(Example example);

    /**
     * @functionName deleteByExampleInField
     * @description 根据指定字段批量删除数据
     * @param clazz
     * @param field
     * @param values
     * @return java.lang.Integer
     * @author 小策一喋<xvpindex@qq.com>
     * @date 2017-03-28 14:37
     */
    Integer deleteByExampleInField(Class<T> clazz, String field, List<Object> values);
}
