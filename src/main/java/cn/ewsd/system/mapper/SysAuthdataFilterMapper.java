package cn.ewsd.system.mapper;


import cn.ewsd.base.bean.CommonMapper;
import cn.ewsd.system.model.SysAuthdataFilter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 数据权限过滤器
 * 
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-12-26 09:58:54
 */

public interface SysAuthdataFilterMapper extends CommonMapper<SysAuthdataFilter> {

    //获取分页集
    List<SysAuthdataFilter> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(SysAuthdataFilter var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<SysAuthdataFilter> var1);

    int executeUpdate(SysAuthdataFilter var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    SysAuthdataFilter queryObject(Object var1);

    List<SysAuthdataFilter> queryList(Map<String, Object> var1);

    List<SysAuthdataFilter> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

    List<SysAuthdataFilter> getListByApiUuid(String apiUuid);
}
