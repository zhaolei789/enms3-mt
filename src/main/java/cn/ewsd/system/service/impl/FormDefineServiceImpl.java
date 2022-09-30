package cn.ewsd.system.service.impl;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.system.mapper.FormDefineMapper;
import cn.ewsd.system.service.FormDefineService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FormDefineServiceImpl implements FormDefineService {

    @Autowired
    private FormDefineMapper formDefineMapper;

    @Override
    public PageSet<Map<String, Object>> getPageSet(PageParam pageParam, String tableName, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<Map<String, Object>> list = formDefineMapper.getPageSet(tableName, filterSort);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public Map<String, Object> queryObject(String tableName, String uuid) {
        return formDefineMapper.queryObject(tableName, uuid);
    }

    @Override
    public List<Map<String, Object>> queryList(Map<String, Object> map) {
        return formDefineMapper.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return formDefineMapper.queryTotal(map);
    }

    @Override
    public int executeSave(Map<String, Object> map) {
        return formDefineMapper.executeSave(map);
    }

    @Override
    public int executeUpdate(String tableName, Map<String, Object> map) {
        return formDefineMapper.executeUpdate(tableName, map);
    }

    @Override
    public int executeUpdateSql(String sql) {
        return formDefineMapper.executeUpdateSql(sql);
    }

    @Override
    public int executeDeleteSql(String sql) {
        return formDefineMapper.executeDeleteSql(sql);
    }

    @Override
    public int executeInsertSql(String sql) {
        return formDefineMapper.executeInsertSql(sql);
    }

    @Override
    public int executeDelete(String uuid) {
        return formDefineMapper.executeDelete(uuid);
    }

    @Override
    public int executeDeleteBatch(String tableName, String[] uuids) {
        return formDefineMapper.executeDeleteBatch(tableName, uuids);
    }

    @Override
    public List<Map<String, Object>> getFieldsByPuuid(String puuid) {
        return formDefineMapper.getFieldsByPuuid(puuid);
    }

    @Override
    public List<Map<String, Object>> getListByLevelId(String tableName, String levelId) {
        return formDefineMapper.getListByLevelId(tableName,levelId);
    }

    @Override
    public List<Map<String, Object>> getListByPid(String tableName, String pid) {
        return formDefineMapper.getListByPid(tableName,pid);
    }

    @Override
    public int getIncreaseId(String tableName) {
        return formDefineMapper.getIncreaseId(tableName);
    }

    @Override
    public String getChildIds(Map map) {
        return formDefineMapper.getChildIds(map);
    }

    @Override
    public List<Map<String, Object>> getDataByPid(String tableName, int pid) {
        return formDefineMapper.getDataByPid(tableName,pid);
    }
}
