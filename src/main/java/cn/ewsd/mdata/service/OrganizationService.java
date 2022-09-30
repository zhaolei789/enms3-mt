package cn.ewsd.mdata.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.mdata.model.Organization;
import org.aspectj.weaver.ast.Or;

import java.util.List;
import java.util.Map;

public interface OrganizationService extends MdataBaseService<Organization, String> {

    PageSet<Organization> getPageSet(PageParam pageParam, String filterSort);

    Organization queryObject(String uuid);

    List<Organization> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    int executeSave(Organization organization);

    int executeUpdate(Organization organization);

    int executeDelete(String uuid);

    int executeDeleteBatch(String[] uuids);

    Organization getListByCode(String code);

    Integer getMaxById();

    Boolean getListByCodeAndUuid(String code, String uuid);

    List<Organization> getListByCodeSetIdAndLevelId(String codeSetId, String levelId);

    List<Organization> getListByLevelId(String levelId);

    List<Organization> getListByPid(String pid);

    List<Organization> getListByCodeSetIdAndPid(String codeSetId, String pid);

    List<Organization> getListByCodesetidAndLevelId();

    List<Organization> getListByIsDelAndPidAndLevelId(String pid);

    Integer updateByIsDelAndUuid(String uuid);

    String getChildIds(Map map);

    String getMyLeaderId(String currentUserUuid);

    Organization getOrgByCode(String hrd);

    Organization getListById(String id);

    String getFatherIds(Map map);

    String getUserInfo(String orgId, String orgType);

    Organization getOrganizationById(Integer pid);

    List<Organization> getOrgByLevel(Integer level);

    List<Organization> getUserDeptSet(String deptIds, String minus, String levelId);

    List<Organization> getWorkDeptSet();

    List<Organization> getTeamDownClass(String teamNo);

    List<Organization> getListByIdsLevel3(String[] ids);

    Integer executeDeleteByTenantBatch(String[] uuids,String tenantId,Integer level,Integer pid);

    List<Organization> getOrgListByTypeAndTenant(String orgType,String tenantId);
}