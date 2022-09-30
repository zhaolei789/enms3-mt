package cn.ewsd.mdata.mapper;
import cn.ewsd.common.mapper.BaseMapper;
import cn.ewsd.mdata.model.SysTenant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 租户
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2022-06-02 18:20:18
 */
public interface SysTenantMapper extends tk.mybatis.mapper.common.Mapper<SysTenant> {

    //获取分页集
    List<SysTenant> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(SysTenant var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<SysTenant> var1);

    int executeUpdate(SysTenant var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    SysTenant queryObject(Object var1);

    List<SysTenant> queryList(Map<String, Object> var1);

    List<SysTenant> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

    List<SysTenant> queryListByIds(Object[] var1);

    SysTenant getOneOld();

}
