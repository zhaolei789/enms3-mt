package cn.ewsd.cost.mapper;
import cn.ewsd.common.mapper.BaseMapper;
import cn.ewsd.cost.model.SysUserBarMaster;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户与矿长
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-17 14:44:35
 */
public interface SysUserBarMasterMapper extends tk.mybatis.mapper.common.Mapper<SysUserBarMaster> {

    //获取分页集
    List<SysUserBarMaster> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(SysUserBarMaster var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<SysUserBarMaster> var1);

    int executeUpdate(SysUserBarMaster var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    SysUserBarMaster queryObject(Object var1);

    List<SysUserBarMaster> queryList(Map<String, Object> var1);

    List<SysUserBarMaster> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

    int queryTotalByFS(@Param("filterSort") String filterSort);

}
