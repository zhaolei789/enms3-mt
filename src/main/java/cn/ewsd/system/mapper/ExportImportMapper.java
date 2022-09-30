package cn.ewsd.system.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-06-26 10:50:51
 */

public interface ExportImportMapper  {


    List<Map<String,Object>> getDataByTablesAndField(@Param("tables") String tables, @Param("field") String field);

}
