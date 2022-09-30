package cn.ewsd.system.mapper;


import cn.ewsd.base.bean.CommonMapper;
import cn.ewsd.system.model.Templet;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TempletMapper extends CommonMapper<Templet> {

    List<Templet> getPageSet(@Param("filterSort") String filterStr);

    /**
     * 根据报表名称检查报表是否存在
     *
     * @param name 报表名称
     * @return
     */
    public int checkExistByName(String name);

    /**
     * 根据报表名称查询报表
     *
     * @param name 报表名称
     * @return
     */
    public Templet queryUreportFileByName(String name);

    /**
     * 查询全部报表
     *
     * @return
     */
    public List<Templet> queryReportFileList();

    /**
     * 根据报表名称删除报表
     *
     * @param name
     * @return
     */
    public int deleteReportFileByName(String name);


    /**
     * 保存报表
     */
    public int insertReportFile(Templet entity);

    /**
     * 更新报表
     *
     * @param entity
     * @return
     */
    public int updateReportFile(Templet entity);
}
