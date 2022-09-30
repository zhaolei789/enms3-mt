package cn.ewsd.base.utils.easyui;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;

/**
 * Mybatis 分页查询工具类，结合 EasyUI 分页请求及返回结果规范
 * <p>
 * Created by admin on 2017-07-19.
 */
public class PageUtils {

    /**
     * 封装 MyBatis.PageHelper 查询结果 PageInfo，符合 EasyUI 分页结果集规范
     *
     * @param pageInfo 原结果集，PageInfo
     * @param <T>
     * @return 符合 EasyUI 规范的分页结果集 MePageSet
     */
    public static <T> PageSet<T> getPageSet(PageInfo<T> pageInfo) {
        PageSet<T> pageSet = new PageSet<>(pageInfo.getTotal(), pageInfo.getList());
        BeanUtils.copyProperties(pageInfo, pageSet);
        return pageSet;
    }

}
