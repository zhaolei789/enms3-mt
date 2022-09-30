package cn.ewsd.system.mapper;
import cn.ewsd.common.mapper.BaseMapper;
import cn.ewsd.system.model.SysUserStore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-06-09 17:58:25
 */
public interface SysUserStoreMapper extends tk.mybatis.mapper.common.Mapper<SysUserStore> {

    //获取分页集
    List<SysUserStore> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(SysUserStore var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<SysUserStore> var1);

    int executeUpdate(SysUserStore var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    SysUserStore queryObject(Object var1);

    List<SysUserStore> queryList(Map<String, Object> var1);

    List<SysUserStore> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

}
