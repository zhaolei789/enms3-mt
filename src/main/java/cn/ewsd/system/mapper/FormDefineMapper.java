package cn.ewsd.system.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-06-26 10:50:51
 */

public interface FormDefineMapper {

    //获取分页集
    List<Map<String, Object>> getPageSet(@Param("tableName") String tableName, @Param("filterSort") String filterSort);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<SysFdField> var1);

    int executeUpdate(@Param("tableName") String tableName, @Param("paramsMap") Map<String, Object> paramsMap);

    int executeUpdateSql(@Param("sql") String sql);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(@Param("tableName") String tableName, @Param("uuids") Object[] uuids);

    Map<String, Object> queryObject(@Param("tableName") String tableName, @Param("uuid") Object uuid);

    List<Map<String, Object>> queryList(Map<String, Object> var1);

    List<Map<String, Object>> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();



    List<Map<String, Object>> getFieldsByPuuid(String puuid);

    int executeInsertSql(@Param("sql") String sql);

    int executeDeleteSql(@Param("sql") String sql);

    List<Map<String, Object>> getListByLevelId(@Param("tableName") String tableName, @Param("levelId") String levelId);

    List<Map<String, Object>> getListByPid(@Param("tableName") String tableName, @Param("pid") String pid);

    int getIncreaseId(@Param("tableName") String tableName);

    public String getChildIds(Map map);

    List<Map<String, Object>> getDataByPid(@Param("tableName") String tableName, @Param("pid") int pid);
}
