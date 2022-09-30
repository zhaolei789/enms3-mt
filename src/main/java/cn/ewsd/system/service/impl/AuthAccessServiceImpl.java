package cn.ewsd.system.service.impl;

import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.system.mapper.AuthAccessMapper;
import cn.ewsd.system.model.AuthAccess;
import cn.ewsd.system.model.Menu;
import cn.ewsd.system.service.AuthAccessService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@SuppressWarnings({"unchecked"})
@Service("authAccessServiceImpl")
@CacheConfig(cacheNames = "authAccess")
public class AuthAccessServiceImpl extends SystemBaseServiceImpl<AuthAccess, String> implements AuthAccessService {

    @Autowired
    private AuthAccessMapper authAccessMapper;

    @Autowired
    public UserInfo userInfo;

    public AuthAccess authAccess;


    @Override
    public String getMenuIdsByGroupUuid(String roleId) {
        // String hql = "from AuthAccess where roleId = '" + roleId + "'";
        List<AuthAccess> authAccesss = authAccessMapper.getListByRoleId(roleId);
        String pidStr = "";
        for (AuthAccess authAccess : authAccesss) {
            pidStr += authAccess.getId() + ",";
        }
        return pidStr.substring(0, pidStr.length() - 1);
    }

    @Override
    public AuthAccess getDetailByRoleIdAndUrl(String roleId, String url) {
        List<String> list = new ArrayList<String>();
        String d[] = roleId.split(",");
        for (int i = 0; i < d.length; i++) {
            list.add(d[i]);
        }
        // String hql = "from AuthAccess where roleId in (" + roleId + ") and url = '" + url + "'";
        return authAccessMapper.getDetailByRoleIdAndUrl(list, url);
    }

    @Override
    public void insertOrUpdateAuthAccess(Integer roleId, Menu menu) {
        // String authAccessSearchHql = "from AuthAccess where roleId = '" + roleId + "' and id = '" + menu.getId() + "'";
        Integer count = authAccessMapper.getCountByRoleIdAndMenuId(roleId, menu.getId());

        if (count >= 1) {
            //  String authAccessUpdateHql = "update AuthAccess set state = '" + menu.getState() + "' where roleId = '" + roleId + "' and id = '" + menu.getId() + "'";
            authAccessMapper.executeByStateRoleIdMid(menu.getState(), roleId, menu.getId());
        } else {
            AuthAccess authAccess = new AuthAccess();
            authAccess.setUuid(BaseUtils.UUIDGenerator());
            authAccess.setId(menu.getId());
            authAccess.setPid(menu.getPid());
            authAccess.setState(menu.getState());
            authAccess.setRoleId(roleId);
            authAccess.setCreateTime(new Date());
            authAccessMapper.insertSelective(authAccess);
        }
    }

    @Override
    public String getSelfAndChildUuids(String uuid, String uuids) {
        String newUuid = uuid.replace("'", "");
        AuthAccess authAccess = authAccessMapper.selectByPrimaryKey(newUuid);
        if (authAccess != null) {
            // String.format("FROM AuthAccess WHERE roleId = '%s' AND pid = '%s'", authAccess.getRoleId(), authAccess.getId())
            List<AuthAccess> authAccesses = authAccessMapper.getListByRoleIdAndPid(authAccess.getRoleId(), authAccess.getId());
            if (authAccesses.size() > 0) {
                for (AuthAccess authAccess1 : authAccesses) {
                    uuids += ",'" + authAccess1.getUuid() + "'";
                    getSelfAndChildUuids(authAccess1.getUuid(), uuids);
                }
            }
        }
        return uuids.replace("$", "'" + newUuid + "'");
    }

    @Override
    public String getFilterByRoleIdAndUrl(String roldIds, String url) {
        List<String> list = new ArrayList<String>();
        String d[] = roldIds.split(",");
        for (int i = 0; i < d.length; i++) {
            list.add(d[i]);
        }
        //"FROM AuthAccess WHERE roleId IN (%s) AND url = '%s' ORDER BY sort ASC", roldIds, url), 0, 1
        List<AuthAccess> authAccesses = authAccessMapper.getLimitedListByRoldIdsAndUrl(list, url);
        if (authAccesses.size() > 0) {
            String remark = authAccesses.get(0).getRemark();
            if (remark == null) {
                return "";
            } else {
                return remark;
            }
        } else {
            return "";
        }
    }

    @Override
    public PageSet<AuthAccess> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<AuthAccess> list = authAccessMapper.getPageSet(filterSort);
        PageInfo<AuthAccess> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public List<AuthAccess> getListByRoleIdAndPid1(String roleId, String pid) {
        List<String> list = new ArrayList<String>();
        String d[] = roleId.split(",");
        for (int i = 0; i < d.length; i++) {
            list.add(d[i]);
        }
        return authAccessMapper.getListByRoleIdAndPid1(list, pid);
    }

    @Override
    public Integer getListByRoleIdAndUuid(Integer roleId, String uuid) {
        return authAccessMapper.getListByRoleIdAndUuid(roleId, uuid);
    }

    @Override
    public Integer updateAccessAuth(Integer roleId, String uuid) {
        return authAccessMapper.updateAccessAuth(roleId, uuid);
    }

    @Override
    public Integer getListByRoleIdAndUuids(String roleId, String singleUuid) {
        return authAccessMapper.getListByRoleIdAndUuids(roleId, singleUuid);
    }

    @Override
    public Integer updateAccessAuths(String roleId, String singleUuid) {
        return authAccessMapper.updateAccessAuths(roleId, singleUuid);
    }

    @Override
    public Integer getByRoleIdAndId(Integer id, Integer rootId) {
        return authAccessMapper.getByRoleIdAndId(id, rootId);
    }

    @Override
    public List<Map<String, Object>> getListByResourceType() {
        return authAccessMapper.getListByResourceType();
    }

    @Override
    public void updateByPids(String pids) {
        List<String> list = new ArrayList<String>();
        String d[] = pids.split(",");
        for (int i = 0; i < d.length; i++) {
            list.add(d[i]);
        }
        authAccessMapper.updateByPids(list);
    }

    @Override
    public List<AuthAccess> selectCodeLeveMenu(String roleId, String codeSetId, String levelId, String menuId) {
        List<String> list = new ArrayList<String>();
        String d[] = roleId.split(",");
        for (int i = 0; i < d.length; i++) {
            list.add(d[i]);
        }
        return authAccessMapper.selectCodeLeveMenu(list, codeSetId, levelId, menuId);
    }

    @Override
    public List<AuthAccess> selectUserNameIdAndPid(String roleIdByUserNameId, String pid) {
        return authAccessMapper.selectUserNameIdAndPid(roleIdByUserNameId, pid);
    }

    @Override
//    @Cacheable(key = "targetClass + methodName + #p0 + #p1")
//    @Cacheable(key = "#root.target.userInfo.getRoleId()+'_'+#p1") 丰凯
    @Cacheable(key = "#roleIds+'_'+#p1")
    public List<Map<String, Object>> getListByRoleIdByUserNameIdAndPid(String roleIds, String pid) {
        List<String> list = new ArrayList<String>();
        String d[] = roleIds.split(",");
        for (int i = 0; i < d.length; i++) {
            list.add(d[i]);
        }
        return authAccessMapper.getListByRoleIdByUserNameIdAndPid(list, pid);
    }

    @Override
//    @Cacheable(key = "#root.target.userInfo.getRoleId()+'_'+#p1")
    @Cacheable(cacheNames = "authAccess2", key = "#roleIdByUserNameId+'_'+#p1")
    public List<Map<String, Object>> getListByRoleIdByUserNameIdAndText(String roleIdByUserNameId, String text) {
        List<String> list = new ArrayList<String>();
        String d[] = roleIdByUserNameId.split(",");
        for (int i = 0; i < d.length; i++) {
            list.add(d[i]);
        }
        return authAccessMapper.getListByRoleIdByUserNameIdAndText(list, text);
    }

    @Override
    public AuthAccess getDetailByRoleIdByUserNameIdAndUrl(String roleIdByUserNameId, String refererUri) {
        List<String> list = new ArrayList<String>();
        String d[] = roleIdByUserNameId.split(",");
        for (int i = 0; i < d.length; i++) {
            list.add(d[i]);
        }
        return authAccessMapper.getDetailByRoleIdByUserNameIdAndUrl(list, refererUri);
    }

    @Override
    public Integer getCountByCurrentUserNameIdAUrl(String roleIdByUserNameId, String url) {
        List<String> list = new ArrayList<String>();
        String d[] = roleIdByUserNameId.split(",");
        for (int i = 0; i < d.length; i++) {
            list.add(d[i]);
        }
        return authAccessMapper.getCountByCurrentUserNameIdAUrl(list, url);
    }

    @Override
    public List<Map<String, Object>> getListByJdbc(String filterStr) {
        return authAccessMapper.getListByJdbc(filterStr);
    }

    @Override
    public String getChildIds(Map map) {
        return authAccessMapper.getChildIds(map);
    }

    @Override
    public List<AuthAccess> getListBypId(String filterSort) {
        return authAccessMapper.getListBypId(filterSort);
    }

    @Override
    public List<AuthAccess> getListInUuid(String uuid) {
        List<String> list = new ArrayList<String>();
        String d[] = uuid.split(",");
        for (int i = 0; i < d.length; i++) {
            list.add(d[i]);
        }
        return authAccessMapper.getListInUuid(list);
    }

    @Override
    public int deleteByUuid(String allUuids) {
        return authAccessMapper.deleteByUuid(allUuids);
    }

    @Override
    public int getListByRoleIdInt(Integer id) {
        return authAccessMapper.getListByRoleIdInt(id);
    }

    @Override
    public int deleteByRoleId(List<Integer> roleIdList) {
        return authAccessMapper.deleteByRoleId(roleIdList);
    }

    @Override
    public void insertMultiple(int roleId) {
        Map map = new HashMap();
        map.put("p1",roleId);
        map.put("p2", 1);
        authAccessMapper.insertMultiple(map);

        authAccessMapper.setMenuStateByRecursion(map);
    }


}
