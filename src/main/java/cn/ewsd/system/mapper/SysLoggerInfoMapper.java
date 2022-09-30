package cn.ewsd.system.mapper;


import cn.ewsd.base.bean.CommonMapper;
import cn.ewsd.system.model.SysLoggerInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-12-24 16:27:06
 */

public interface SysLoggerInfoMapper extends CommonMapper<SysLoggerInfo> {

    //获取分页集
    List<SysLoggerInfo> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(SysLoggerInfo var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<SysLoggerInfo> var1);

    int executeUpdate(SysLoggerInfo var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    SysLoggerInfo queryObject(Object var1);

    List<SysLoggerInfo> queryList(Map<String, Object> var1);

    List<SysLoggerInfo> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

}
