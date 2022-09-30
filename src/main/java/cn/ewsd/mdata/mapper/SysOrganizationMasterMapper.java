package cn.ewsd.mdata.mapper;
import cn.ewsd.common.mapper.BaseMapper;
import cn.ewsd.mdata.model.SysOrganizationMaster;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 部门分管领导
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2022-04-14 11:12:56
 */
public interface SysOrganizationMasterMapper extends tk.mybatis.mapper.common.Mapper<SysOrganizationMaster> {

    //获取分页集
    List<SysOrganizationMaster> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(SysOrganizationMaster var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<SysOrganizationMaster> var1);

    int executeUpdate(SysOrganizationMaster var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    SysOrganizationMaster queryObject(Object var1);

    List<SysOrganizationMaster> queryList(Map<String, Object> var1);

    List<SysOrganizationMaster> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

    List<SysOrganizationMaster> queryListByIds(Object[] var1);

    int batchInsert(@Param("list") List<SysOrganizationMaster> list);

    int executeDeleteByOrgBatch(@Param("orgId") Object[] orgId,@Param("masterUuid") String masterUuid);

    List<SysOrganizationMaster> queryListByMasterIds(Object[] var1);
}
