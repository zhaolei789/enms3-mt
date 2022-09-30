package cn.ewsd.system.service;

import cn.ewsd.system.model.CodeTable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器 
 */
public interface GeneratorService {

	List<HashMap<String, Object>> getPageSet(String filterSort);


	List<HashMap<String, Object>> queryList(HashMap<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	Map<String, String> queryTable(String tableName);

	Map<String, String> queryTableBySqlserver(String tableName);

	List<Map<String, String>> queryColumns(String tableName);
	List<Map<String, String>> queryColumnsBySqlserver(String tableName);

	/**
	 * 生成代码
	 */
	byte[] generatorCode(String[] tableNames);

	List<HashMap<String,Object>> getPageSetBySqlserver(String filterSort);
	/**
	 * 生成代码 SQL SERVER
	 */
	byte[] generatorCodeBySqlserver(String[] tables);

	byte[] codeMultTable(CodeTable codeTable);

    List<HashMap<String, Object>> getAll(String filterSort);
}
