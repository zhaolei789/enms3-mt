package cn.ewsd.system.mapper;
import cn.ewsd.common.mapper.BaseMapper;
import cn.ewsd.system.model.SysUserItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-05-10 09:39:02
 */
public interface SysUserItemMapper extends tk.mybatis.mapper.common.Mapper<SysUserItem> {

    //获取分页集
    List<SysUserItem> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(SysUserItem var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<SysUserItem> var1);

    int executeUpdate(SysUserItem var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    SysUserItem queryObject(Object var1);

    List<SysUserItem> queryList(Map<String, Object> var1);

    List<SysUserItem> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

}
