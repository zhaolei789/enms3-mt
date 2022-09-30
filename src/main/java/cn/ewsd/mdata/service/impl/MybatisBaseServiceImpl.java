package cn.ewsd.mdata.service.impl;


import cn.ewsd.base.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.mdata.service.MybatisBaseService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.List;

/**
 * @author 小策一喋<xvpindex@qq.com>
 * @className MybatisBaseServiceImpl
 * @description
 * @date 2018-03-15 9:59
 */
@Service
public abstract class MybatisBaseServiceImpl<T, PK extends Serializable> implements MybatisBaseService<T, PK> {

    @Autowired
    private Mapper<T> mapper;

    @Override
    public T selectByPrimaryKey(PK pk) {
        return mapper.selectByPrimaryKey(pk);
    }

    @Override
    public T selectOne(T param) {
        return this.mapper.selectOne(param);
    }

    @Override
    public List<T> selectAll() {
        return this.mapper.select(null);
    }

    @Override
    public List<T> selectByExample(Example t) {
        return this.mapper.selectByExample(t);
    }

    @Override
    public List<T> select(T param) {
        return this.mapper.select(param);
    }

    @Override
    public Integer selectCount(T param) {
        return this.mapper.selectCount(param);
    }

    @Override
    public Integer selectCountByExample(Example example) {
        return this.mapper.selectCountByExample(example);
    }

    @Override
    public PageSet<T> selectPageListByWhere(T param, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<T> list = this.select(param);

        PageInfo<T> pageInfo = new PageInfo<T>(list);
        // 转换为TopJUI表格分页数据
        PageSet<T> topJUIPageInfo = new PageSet(pageInfo.getTotal(), pageInfo.getList());
        return topJUIPageInfo;
    }

    @Override
    public PageSet<T> selectPageSetByExample(Class<T> clazz, PageParam pageParam, String filterSort) {
        Example example = new Example(clazz);
        example.orderBy("createTime").desc();
        example.or().andGreaterThan("username", 1);

        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<T> list = this.mapper.selectByExample(example);
        //List<T> list = sampleDatagridMapper.getPageSet(filterSort);

        PageInfo<T> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public PageSet<T> selectPageSetByExample(Example example, PageParam pageParam) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<T> list = this.mapper.selectByExample(example);

        PageInfo<T> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public Integer insert(T param) {
        return this.mapper.insert(param);
    }

    @Override
    public Integer insertSelective(T param) {
        return this.mapper.insertSelective(param);
    }

    @Override
    public Integer updateByPrimaryKey(T param) {
        return this.mapper.updateByPrimaryKey(param);
    }

    @Override
    public Integer updateByPrimaryKeySelective(T param) {
        return this.mapper.updateByPrimaryKeySelective(param);
    }

    @Override
    public Integer updateByExample(T t, Example example) {
        return this.mapper.updateByExample(t, example);
    }

    @Override
    public Integer updateByExampleSelective(T t, Example example) {
        return this.mapper.updateByExampleSelective(t, example);
    }

    @Override
    public Integer deleteByPrimaryKey(PK pk) {
        return this.mapper.deleteByPrimaryKey(pk);
    }

    @Override
    public Integer delete(T t) {
        return this.mapper.delete(t);
    }

    @Override
    public Integer deleteByExample(Example example) {
        return this.mapper.deleteByExample(example);
    }

    @Override
    public Integer deleteByExampleInField(Class<T> clazz, String field, List<Object> values) {
        Example example = new Example(clazz);
        example.createCriteria().andIn(field, values);
        return this.mapper.deleteByExample(example);
    }

}