package cn.ewsd.system.mapper;


import cn.ewsd.base.bean.CommonMapper;
import cn.ewsd.system.model.Archive;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


//@Mapper
public interface ArchiveMapper extends CommonMapper<Archive> {


    List<Archive> getPageSet(@Param("filterSort") String filterSort);

    String getChildIds(Map map);

    List<Archive> getListByCreateTime();

    List<Archive> getListByFilterSort(@Param("filterSort") String filterSort);
}
