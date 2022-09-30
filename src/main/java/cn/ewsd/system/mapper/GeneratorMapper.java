package cn.ewsd.system.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器 
 */
public interface GeneratorMapper {

	List<HashMap<String, Object>> getPageSet(@Param("filterSort") String filterSort);


	List<HashMap<String, Object>> queryList(HashMap<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	Map<String, String> queryTable(@Param("tableName")String tableName);
	
	List<Map<String, String>> queryColumns(String tableName);

	List<HashMap<String,Object>> getPageSetBySqlserver(@Param("filterSort") String filterSort);

	Map<String, String> queryTableBySqlserver(String tableName);

	List<Map<String, String>> queryColumnsBySqlserver(String tableName);

    List<HashMap<String, Object>> getAll(@Param("filterSort") String filterSort);


//	List<HashMap<String, Object>> getPageSetBySqlserver(@Param("filterSort") String filterSort);
}
