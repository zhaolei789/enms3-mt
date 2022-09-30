package cn.ewsd.system.mapper;


import cn.ewsd.base.bean.CommonMapper;
import cn.ewsd.system.model.SysCsBase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-07-24 15:27:51
 */

public interface SysCsBaseMapper extends CommonMapper<SysCsBase> {

    //获取分页集
    List<SysCsBase> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(SysCsBase var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<SysCsBase> var1);

    int executeUpdate(SysCsBase var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    SysCsBase queryObject(Object var1);

    List<SysCsBase> queryList(Map<String, Object> var1);

    List<SysCsBase> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

}
