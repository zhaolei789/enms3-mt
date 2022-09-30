package cn.ewsd.system.mapper;
import cn.ewsd.common.mapper.BaseMapper;
import cn.ewsd.system.model.SysAuditProcess;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-04-23 11:18:36
 */
public interface SysAuditProcessMapper extends tk.mybatis.mapper.common.Mapper<SysAuditProcess> {

    //获取分页集
    List<SysAuditProcess> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(SysAuditProcess var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<SysAuditProcess> var1);

    int executeUpdate(SysAuditProcess var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    SysAuditProcess queryObject(Object var1);

    List<SysAuditProcess> queryList(Map<String, Object> var1);

    List<SysAuditProcess> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

    //=============================================================

    int executeResetInitStateBatch(Object[] var1);

    int executeUpdateInitState(@Param("initState") String initState,@Param("uuid") String uuid);

    int executeUpdateStep(@Param("lastStep") String lastStep,@Param("nextStep") String nextStep,@Param("uuid") String uuid);

    List<SysAuditProcess> queryListByFuuid(@Param("fuuid") String fuuid);

    List<SysAuditProcess> queryListByUuids(Object[] var1);

    SysAuditProcess queryObjectByProcessNo(Object var1);

    SysAuditProcess queryFirstObjectByfuuid(Object var1);
}
