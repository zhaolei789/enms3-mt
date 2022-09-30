package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;

import java.util.List;
import java.util.Map;

/**
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-06-26 10:50:51
 */
public interface FormDefineService {

    PageSet<Map<String, Object>> getPageSet(PageParam pageParam, String tableName, String filterSort);

    Map<String, Object> queryObject(String tableName, String uuid);

    List<Map<String, Object>> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    int executeSave(Map<String, Object> map);

    int executeUpdate(String tableName, Map<String, Object> map);

    int executeUpdateSql(String sql);

    int executeDeleteSql(String sql);

    int executeInsertSql(String sql);

    int executeDelete(String uuid);

    int executeDeleteBatch(String tableName, String[] uuids);

    List<Map<String, Object>> getFieldsByPuuid(String puuid);

    List<Map<String, Object>> getListByLevelId(String tableName, String levelId);

    List<Map<String, Object>> getListByPid(String tableName, String pid);

    int getIncreaseId(String tableName);

    public String getChildIds(Map map);

    List<Map<String, Object>> getDataByPid(String tableName, int pid);
}
