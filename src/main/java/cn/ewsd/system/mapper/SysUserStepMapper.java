package cn.ewsd.system.mapper;
import cn.ewsd.common.mapper.BaseMapper;
import cn.ewsd.system.model.SysUserStep;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-04-23 11:19:10
 */
public interface SysUserStepMapper extends tk.mybatis.mapper.common.Mapper<SysUserStep> {

    //获取分页集
    List<SysUserStep> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(SysUserStep var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<SysUserStep> var1);

    int executeUpdate(SysUserStep var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    SysUserStep queryObject(Object var1);

    List<SysUserStep> queryList(Map<String, Object> var1);

    List<SysUserStep> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

    int checkUserHavePms(@Param("stepUuid") String stepId,@Param("userId") String userId,@Param("auditOrg") String orgId);

    List<SysUserStep> queryListByUserAndStep(@Param("stepUuid") String stepId,@Param("userId") String userId);

    List<SysUserStep> queryListByUserAndProcessUuid(@Param("processUuid") String processUuid,@Param("userId") String userId);

    List<SysUserStep> queryListByStepGroupUser(Object var1);

    List<SysUserStep> queryListByStepAndOrgGroupUser(@Param("stepUuid") String stepUuid,@Param("auditOrg") Integer auditOrg);

}
