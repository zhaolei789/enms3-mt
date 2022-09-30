package cn.ewsd.mdata.mapper;

import cn.ewsd.mdata.model.Organization;
import org.apache.ibatis.annotations.Param;
import org.aspectj.weaver.ast.Or;

import java.util.List;
import java.util.Map;

public interface OrganizationMapper extends tk.mybatis.mapper.common.Mapper<Organization> {

   //获取分页集
    List<Organization> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(Organization var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<DevDeviceBase> var1);

    int executeUpdate(Organization var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

//    Organization queryObject(Object var1);
//
//    List<Organization> queryList(Map<String, Object> var1);
//
//    List<Organization> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

    Organization getListByCode(String code);

    Integer getMaxById();

    Integer getListByCodeAndUuid(@Param("code") String code, @Param("uuid") String uuid);

    List<Organization> getListByCodeSetIdAndLevelId(String codeSetId, String levelId);

    List<Organization> getListByLevelId(String levelId);

    List<Organization> getListByPid(String pid);

    List<Organization> getListByCodeSetIdAndPid(String codeSetId, String pid);

    List<Organization> getListByCodesetidAndLevelId();

    List<Organization> getListByIsDelAndPidAndLevelId(String pid);

    Organization queryObject(String uuid);

    List<Organization> queryList(Map<String, Object> map);

    Integer updateByIsDelAndUuid(String uuid);

    String getChildIds(Map map);

    Organization getListById(String id);

    Organization getOrgByCode(String hrd);

    String getFatherIds(Map map);

    List<Organization> getListByOrgType(@Param("ids") String ids, @Param("groupLeader") String groupLeader);

    String getUserInfo(@Param("orgId") String orgId, @Param("postText") String postText);

    Organization getOrganizationById(@Param("id") Integer id);

    String getMaxCodeByPid(String pid);

    List<Organization> getOrgByLevel(Integer level);

    List<Organization> getUserDeptSet(@Param("deptIds") String deptIds, @Param("minus") String minus, @Param("levelId") String levelId);

    Organization getOrgByCenterNo(String centerNo);

    Organization getOrgEpPlan(String epId);

    List<Organization> getWorkDeptSet();

    List<Organization> getTeamDownClass(String teamNo);

    List<Organization> getListByIdsLevel3(Object[] var1);

    Integer executeDeleteByTenantBatch(@Param("uuids") String[] uuids,@Param("tenantId") String tenantId,@Param("levelId") Integer level,@Param("pid") Integer pid);

    List<Organization> getOrgListByTypeAndTenant(@Param("orgType") String orgType, @Param("tenantId") String tenantId);
}
