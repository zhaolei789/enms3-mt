package cn.ewsd.mdata.service;

import java.io.Serializable;

/**
 * @author 小策一喋<xvpindex@qq.com>
 * @className IMybatisBaseService
 * @description
 * @date 2018-03-15 9:12
 */
public interface MdataBaseService<T, PK extends Serializable> extends MybatisBaseService<T, Serializable> {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     *//*
    T selectByPrimaryKey(Long id);

    *//**
     * 根据uuid查询
     *
     * @param uuid
     * @return
     *//*
    T selectByPrimaryKey(String uuid);

    *//**
     * 查询所有
     *
     * @return
     *//*
    List<T> selectAll();

    *//**
     * 条件查询
     *
     * @param param
     * @return
     *//*
    List<T> selectByWhere(T param);

    *//**
     * 查询记录数
     *
     * @param param
     * @return
     *//*
    Integer selectCount(T param);

    *//**
     * 根据实体条件分页查询
     *
     * @param param
     * @param page
     * @param rows
     * @return
     *//*
    PageSet<T> selectPageListByWhere(T param, Integer page, Integer rows);

    *//**
     * 根据example分页查询，带过滤条件
     *
     * @param clazz
     * @param pageParam
     * @param filterSort
     * @return
     *//*
    PageSet<T> selectPageSetByExample(Class<T> clazz, PageParam pageParam, String filterSort);

    *//**
     * 根据example分页查询
     *
     * @param example
     * @param pageParam
     * @return
     *//*
    PageSet<T> selectPageSetByExample(Example example, PageParam pageParam);

    *//**
     * 查询一条记录
     *
     * @param param
     * @return
     *//*
    T selectOne(T param);

    *//**
     * 插入实体所有字段
     *
     * @param param
     * @return
     *//*
    Integer insert(T param);

    *//**
     * 插入实体非空字段
     *
     * @param param
     * @return
     *//*
    Integer insertSelective(T param);

    *//**
     * 根据主键更新
     *
     * @param param
     * @return
     *//*
    Integer updateByPrimaryKey(T param);

    *//**
     * 根据主键更新非空字段
     *
     * @param param
     * @return
     *//*
    Integer updateByPrimaryKeySelective(T param);

    *//**
     * 根据主键删除单条数据
     *
     * @param id
     * @return
     *//*
    Integer deleteByPrimaryKey(Long id);

    *//**
     * 根据主键删除单条数据
     *
     * @param uuid
     * @return
     *//*
    Integer deleteByPrimaryKey(String uuid);

    *//**
     * 根据指定字段批量删除数据
     *
     * @param clazz
     * @param values
     * @return
     *//*
    Integer deleteByExample(Class<T> clazz, String field, List<Object> values);

    *//**
     * 根据id批量删除数据
     *
     * @param clazz
     * @param values
     * @return
     *//*
    Integer deleteByExampleForId(Class<T> clazz, List<Object> values);

    *//**
     * 根据uuid批量删除数据
     *
     * @param clazz
     * @param values
     * @return
     *//*
    Integer deleteByExampleForUuid(Class<T> clazz, List<Object> values);*/

}
