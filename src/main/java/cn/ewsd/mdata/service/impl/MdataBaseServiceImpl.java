package cn.ewsd.mdata.service.impl;

import cn.ewsd.mdata.service.MdataBaseService;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @author 小策一喋<xvpindex@qq.com>
 * @className MybatisBaseServiceImpl
 * @description
 * @date 2018-03-15 9:59
 */
@Service
public abstract class MdataBaseServiceImpl<T, PK extends Serializable> extends MybatisBaseServiceImpl<T, Serializable> implements MdataBaseService<T, PK> {

    /*@Autowired
    private Mapper<T> mapper;

    @Override
    public T selectByPrimaryKey(Long id) {
        return this.mapper.selectByPrimaryKey(id);
    }

    @Override
    public T selectByPrimaryKey(String uuid) {
        return this.mapper.selectByPrimaryKey(uuid);
    }

    @Override
    public List<T> selectAll() {
        return this.mapper.select(null);
    }

    @Override
    public List<T> selectByWhere(T param) {
        return this.mapper.select(param);
    }

    @Override
    public Integer selectCount(T param) {
        return this.mapper.selectCount(param);
    }

    @Override
    public PageSet<T> selectPageListByWhere(T param, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<T> list = this.selectByWhere(param);
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
    public T selectOne(T param) {
        return this.mapper.selectOne(param);
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
    public Integer deleteByPrimaryKey(Long id) {
        return this.mapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer deleteByPrimaryKey(String uuid) {
        return this.mapper.deleteByPrimaryKey(uuid);
    }

    @Override
    public Integer deleteByExample(Class<T> clazz, String field, List<Object> values) {
        Example example = new Example(clazz);
        example.createCriteria().andIn(field, values);
        return this.mapper.deleteByExample(example);
    }

    @Override
    public Integer deleteByExampleForId(Class<T> clazz, List<Object> values) {
        return this.deleteByExample(clazz, "id", values);
    }

    @Override
    public Integer deleteByExampleForUuid(Class<T> clazz, List<Object> values) {
        return this.deleteByExample(clazz, "uuid", values);
    }*/

}