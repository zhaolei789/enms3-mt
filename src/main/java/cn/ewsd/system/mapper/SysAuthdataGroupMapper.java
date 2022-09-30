package cn.ewsd.system.mapper;


import cn.ewsd.base.bean.CommonMapper;
import cn.ewsd.system.model.SysAuthdataGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 数据权限分组
 *
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-12-26 09:57:35
 */

public interface SysAuthdataGroupMapper extends CommonMapper<SysAuthdataGroup> {

    //获取分页集
    List<SysAuthdataGroup> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(SysAuthdataGroup var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<SysAuthdataGroup> var1);

    int executeUpdate(SysAuthdataGroup var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    SysAuthdataGroup queryObject(Object var1);

    List<SysAuthdataGroup> queryList(Map<String, Object> var1);

    List<SysAuthdataGroup> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

    List<SysAuthdataGroup> getGroupUuidsByIds(@Param("ids") String ids);

    Integer getMaxById();
}
