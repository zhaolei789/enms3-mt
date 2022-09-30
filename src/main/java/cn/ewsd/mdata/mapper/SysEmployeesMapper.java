package cn.ewsd.mdata.mapper;
import cn.ewsd.common.mapper.BaseMapper;
import cn.ewsd.mdata.model.SysEmployees;
import cn.ewsd.mdata.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-03-23 11:33:15
 */
public interface SysEmployeesMapper extends tk.mybatis.mapper.common.Mapper<SysEmployees> {

    //获取分页集
    List<SysEmployees> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(SysEmployees var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<SysEmployees> var1);

    int executeUpdate(SysEmployees var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    SysEmployees queryObject(Object var1);

    List<SysEmployees> queryList(Map<String, Object> var1);

    List<SysEmployees> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

    SysEmployees getByEmpNo(Object var1);

    List<SysEmployees> selectAllList();

    List<SysEmployees> getLimitedListByQ(String q);
}
