package cn.ewsd.mdata.service.impl;


import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.mdata.mapper.OrganizationMapper;
import cn.ewsd.mdata.model.Organization;
import cn.ewsd.mdata.model.User;
import cn.ewsd.mdata.service.OrganizationService;
import cn.ewsd.mdata.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unchecked"})
@Service("organizationService")
public class OrganizationServiceImpl extends MdataBaseServiceImpl<Organization, String> implements OrganizationService {

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationService organizationService;

    @Override
    public PageSet<Organization> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<Organization> list = organizationMapper.getPageSet(filterSort);
        PageInfo<Organization> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public Organization queryObject(String uuid) {
        return organizationMapper.queryObject(uuid);
    }

    @Override
    public List<Organization> queryList(Map<String, Object> map) {
        return organizationMapper.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return organizationMapper.queryTotal(map);
    }

    @Override
    public int executeSave(Organization organization) {
        return organizationMapper.executeSave(organization);
    }

    @Override
    public int executeUpdate(Organization organization) {
        return organizationMapper.executeUpdate(organization);
    }

    @Override
    public int executeDelete(String uuid) {
        return organizationMapper.executeDelete(uuid);
    }

    @Override
    public int executeDeleteBatch(String[] uuids) {
        return organizationMapper.executeDeleteBatch(uuids);
    }

    @Override
    public Organization getListByCode(String code) {
        return organizationMapper.getListByCode(code);
    }

    @Override
    public Integer getMaxById() {
        return organizationMapper.getMaxById();
    }

    @Override
    public Boolean getListByCodeAndUuid(String code, String uuid) {
        return organizationMapper.getListByCodeAndUuid(code, uuid) > 0 ? true : false;
    }

    @Override
    public List<Organization> getListByCodeSetIdAndLevelId(String codeSetId, String levelId) {
        return organizationMapper.getListByCodeSetIdAndLevelId(codeSetId,levelId);
    }

    @Override
    public List<Organization> getListByLevelId(String levelId) {
        return organizationMapper.getListByLevelId(levelId);
    }

    @Override
    public List<Organization> getListByPid(String pid) {
        return organizationMapper.getListByPid(pid);
    }

    @Override
    public List<Organization> getListByCodeSetIdAndPid(String codeSetId, String pid) {
        return organizationMapper.getListByCodeSetIdAndPid(codeSetId,pid);
    }

    @Override
    public List<Organization> getListByCodesetidAndLevelId() {
        return organizationMapper.getListByCodesetidAndLevelId();
    }

    @Override
    public List<Organization> getListByIsDelAndPidAndLevelId(String pid) {
        return organizationMapper.getListByIsDelAndPidAndLevelId(pid);
    }

    @Override
    public Integer updateByIsDelAndUuid(String uuid) {
        return organizationMapper.updateByIsDelAndUuid(uuid);
    }



    @Override
    public String getChildIds(Map map) {
        return organizationMapper.getChildIds(map);
    }

    public Organization getOrgById(String id) {
        Organization org = organizationService.getListById(id);
        return org;
    }
    
    public Organization getMyOrg(String myUuid) {
        User user = userService.getUserByCuuid(myUuid);
        return getOrgById(user.getOrgId());
    }


    @Override
    public String getMyLeaderId(String myUuid) {
        String leaderId = "";
        try {
            Organization myOrg = getMyOrg(myUuid);
            Organization parentOrg = getOrgById(myOrg.getPid().toString());
            leaderId = parentOrg.getCreatorId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return leaderId;
    }

    @Override
    public Organization getOrgByCode(String hrd) {
        //return organizationDao.getDetailByHql(String.format("FROM Organization WHERE code = '%s'", code));
        return organizationMapper.getOrgByCode(hrd);
    }

    @Override
    public Organization getListById(String id) {
        return organizationMapper.getListById(id);
    }

    @Override
    public String getFatherIds(Map map) {
        return organizationMapper.getFatherIds(map);
    }




    @Override
    public String getUserInfo(String orgId,String orgType) {
        Map map = new HashMap();
        map.put("p1", "sys_organization");
        map.put("p2", orgId);
        map.put("p3", 1);
        map.put("p4", "idStr");
        organizationService.getFatherIds(map);
        String ids = map.get("p4").toString();
        List<Organization> list=organizationMapper.getListByOrgType(ids,orgType);
        if(list.size()>0){
            Organization organization=list.get(list.size()-1);
            //去sys_user_post 中表找
            String list1=organizationMapper.getUserInfo(String.valueOf(organization.getId()),orgType);
            return list1;
        }else{
            return null;
        }

    }

    @Override
    public Organization getOrganizationById(Integer id) {
        return organizationMapper.getOrganizationById(id);
    }

    @Override
    public List<Organization> getOrgByLevel(Integer level){
        return organizationMapper.getOrgByLevel(level);
    }

    @Override
    public List<Organization> getUserDeptSet(String deptIds, String minus, String levelId){
        return organizationMapper.getUserDeptSet(deptIds, minus, levelId);
    }

    @Override
    public List<Organization> getWorkDeptSet(){
        return organizationMapper.getWorkDeptSet();
    }

    @Override
    public List<Organization> getTeamDownClass(String teamNo){
        return organizationMapper.getTeamDownClass(teamNo);
    }

    @Override
    public List<Organization> getListByIdsLevel3(String[] ids) {
        return organizationMapper.getListByIdsLevel3(ids);
    }

    @Override
    public Integer executeDeleteByTenantBatch(String[] uuids, String tenantId,Integer level,Integer pid) {
        return organizationMapper.executeDeleteByTenantBatch(uuids, tenantId,level,pid);
    }

    @Override
    public List<Organization> getOrgListByTypeAndTenant(String orgType, String tenantId) {
        return organizationMapper.getOrgListByTypeAndTenant(orgType, tenantId);
    }
}
