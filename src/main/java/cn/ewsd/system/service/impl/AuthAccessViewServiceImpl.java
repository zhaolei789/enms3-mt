package cn.ewsd.system.service.impl;

import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.system.mapper.AuthAccessViewMapper;
import cn.ewsd.system.model.AuthAccess;
import cn.ewsd.system.model.AuthAccessView;
import cn.ewsd.system.service.AuthAccessViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unchecked"})
@Service("authAccessViewServiceImpl")
@CacheConfig(cacheNames = "authAccessView")
public class AuthAccessViewServiceImpl extends SystemBaseServiceImpl<AuthAccessView, String> implements AuthAccessViewService {

    @Autowired
    private AuthAccessViewMapper authAccessViewMapper;

    @Autowired
    public UserInfo userInfo;

    @Override
    public List<Map<String, Object>> getAuthorizedMenu(String roleId, String menuId, String codeSetId, String levelId) {
        List<String> list = new ArrayList<String>();
        String d[] = roleId.split(",");
        for (int i = 0; i < d.length; i++) {
            list.add(d[i]);
        }
        List<Map<String, Object>> authAccessViews = authAccessViewMapper.getListByJdbc(list, codeSetId, levelId, menuId);
        return authAccessViews;
    }

    @Override
//    @Cacheable(key = "targetClass + methodName + #p0 + #p1 + #p2")
//    @Cacheable(key = "#root.target.userInfo.getRoleId()+'_'+#p1+ '_'+ #p2") 丰凯
    @Cacheable(key = "#roleId+'_'+#p1+ '_'+ #p2")
    public List<Map<String, Object>> getAuthorizedMenu(String roleId, String menuId, String codeSetId) {
        List<String> list = new ArrayList<String>();

        String d[] = roleId.split(",");
        for (int i = 0; i < d.length; i++) {
            list.add(d[i]);
        }
        List<Map<String, Object>> authAccessViews = authAccessViewMapper.getListByRoleIdAndCodeSetIdAndMenuId2(list, codeSetId, menuId);
        return authAccessViews;
    }

    @Override
//    @Cacheable(key = "#p0")
    public List<AuthAccessView> getAuthorizedMenu(String roleId) {
        List<String> list = new ArrayList<String>();
        String d[] = roleId.split(",");
        for (int i = 0; i < d.length; i++) {
            list.add(d[i]);
        }
        List<AuthAccessView> authAccessViews = authAccessViewMapper.getListByRoleId(list);
        return authAccessViews;
    }

    @Override
    public List<Map<String, Object>> getListByRoleIdAndPid(String roleId, String pid) {
        List<String> list = new ArrayList<String>();
        String d[] = roleId.split(",");
        for (int i = 0; i < d.length; i++) {
            list.add(d[i]);
        }
        List<Map<String, Object>> authAccessViews = authAccessViewMapper.getListByRoleIdAndPid(list, pid);
        return authAccessViews;
    }

    @Override
    public List<AuthAccessView> getSystemList(String codeSetId, String levelId, String roleId) throws Exception {
        List<String> list = new ArrayList<String>();
        String d[] = roleId.split(",");
        for (int i = 0; i < d.length; i++) {
            list.add(d[i]);
        }
        List<AuthAccessView> authAccessViews = authAccessViewMapper.getListByRoleIdAndCodeSetIdAndLevelId3(list, codeSetId, levelId);
        return authAccessViews;
    }

    @Override
    public List<AuthAccess> getListByRoleIdAllMenu(String roleId) {
        return authAccessViewMapper.getListByRoleIdAllMenu(roleId);
    }
}
