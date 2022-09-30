package cn.ewsd.system.mapper;


import cn.ewsd.base.bean.CommonMapper;
import cn.ewsd.system.model.SysAuthdataApi;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 数据权限接口
 * 
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-12-26 09:58:25
 */

public interface SysAuthdataApiMapper extends CommonMapper<SysAuthdataApi> {

    //获取分页集
    List<SysAuthdataApi> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(SysAuthdataApi var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<SysAuthdataApi> var1);

    int executeUpdate(SysAuthdataApi var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    SysAuthdataApi queryObject(Object var1);

    List<SysAuthdataApi> queryList(Map<String, Object> var1);

    List<SysAuthdataApi> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

    List<SysAuthdataApi> getListByGroupUuidsAndUrl(@Param("groupUuids") String[] groupUuids, @Param("url") String url);
}
