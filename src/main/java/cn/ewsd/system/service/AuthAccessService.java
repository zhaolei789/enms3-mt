package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.AuthAccess;
import cn.ewsd.system.model.Menu;

import java.util.List;
import java.util.Map;

public interface AuthAccessService extends SystemBaseService<AuthAccess, String> {
    

    public String getMenuIdsByGroupUuid(String groupUuid);

    public AuthAccess getDetailByRoleIdAndUrl(String roleId, String url);

    public void insertOrUpdateAuthAccess(Integer roleId, Menu menu);

    public String getSelfAndChildUuids(String uuid, String uuids);

    public String getFilterByRoleIdAndUrl(String roldIds, String url);

    PageSet<AuthAccess> getPageSet(PageParam pageParam, String filterSort);

    List<AuthAccess> getListByRoleIdAndPid1(String roleId, String pid);

    Integer getListByRoleIdAndUuid(Integer roleId, String uuid);

    Integer updateAccessAuth(Integer roleId, String uuid);

    Integer getListByRoleIdAndUuids(String roleId, String singleUuid);

    Integer updateAccessAuths(String roleId, String singleUuid);

    Integer getByRoleIdAndId(Integer id, Integer rootId);

    List<Map<String,Object>> getListByResourceType();

    void updateByPids(String pids);

    List<AuthAccess> selectCodeLeveMenu(String roleId, String codeSetId, String levelId, String menuId);

    List<AuthAccess> selectUserNameIdAndPid(String roleIdByUserNameId, String pid);

    List<Map<String,Object>> getListByRoleIdByUserNameIdAndPid(String roleIdByUserNameId, String pid);

    List<Map<String,Object>> getListByRoleIdByUserNameIdAndText(String roleIdByUserNameId, String text);

    AuthAccess getDetailByRoleIdByUserNameIdAndUrl(String roleIdByUserNameId, String refererUri);

    Integer getCountByCurrentUserNameIdAUrl(String roleIdByUserNameId, String url);

    List<Map<String,Object>> getListByJdbc(String filterStr);

    String getChildIds(Map map);

    List<AuthAccess> getListBypId(String filterSort);

    List<AuthAccess> getListInUuid(String uuid);

    int deleteByUuid(String allUuids);

    int getListByRoleIdInt(Integer id);

    int deleteByRoleId(List<Integer> roleIdList);

    void insertMultiple(int roleId);
}