package cn.ewsd.system.controller;

import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.common.Constants;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.base.utils.JsonUtils;
import cn.ewsd.base.utils.StringUtils;
import cn.ewsd.common.utils.UrlUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.mdata.service.UserService;
import cn.ewsd.system.model.AuthAccess;
import cn.ewsd.system.model.AuthAccessView;
import cn.ewsd.system.model.AuthGroup;
import cn.ewsd.system.model.Menu;
import cn.ewsd.system.service.AuthAccessService;
import cn.ewsd.system.service.AuthAccessViewService;
import cn.ewsd.system.service.AuthGroupService;
import cn.ewsd.system.service.MenuService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色组权限
 */
@Controller
@RequestMapping("/system/authAccess")
public class AuthAccessController extends SystemBaseController {

    @Resource
    private AuthAccessService authAccessService;

    @Resource
    private UserService userService;

    @Resource
    private MenuService menuService;

    @Resource
    private AuthGroupService authGroupService;

    @Resource
    private AuthAccessViewService authAccessViewService;

    private List<Integer> returnList = new ArrayList<>();

    @RequestMapping("index")
    public String index(PageParam pageParam) throws Exception {
//        String hql = "FROM AuthAccess" + Common.searchInfo(request);
//        PageSet pageSet = authAccessService.getPageSet(request, hql);
//        request.setAttribute("pageSet", pageSet);
//        return display();

        String filterSort = BaseUtils.filterSort(request);
        PageSet<AuthAccess> pageSet = authAccessService.getPageSet(pageParam, filterSort);
        request.setAttribute("pageSet", pageSet);
        return display();
    }

    @ResponseBody
    @RequestMapping(value = "getDetailByUuid")
    public Object getDetailByUuid() throws Exception {
        String uuid = request.getParameter("uuid");
        AuthAccess authAccess = authAccessService.selectByPrimaryKey(uuid);
        return authAccess;
    }

    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Integer update(AuthAccess authAccess) throws Exception {
        authAccess.setAccessAuth(authAccess.getAccessAuth());
        // String hql = "update AuthAccess set accessAuth = '" + authAccess.getAccessAuth() + "' where uuid = '" + authAccess.getUuid() + "'";
        return authAccessService.updateByPrimaryKey(authAccess);
    }

    /**
     * 角色管理界面导航菜单初始化调用
     *
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "getListByRoleId")
    public Object getListByRoleId(String roleId) throws Exception {
        String filterStr;
        if (!StringUtils.isNullOrEmpty(roleId)) {
            filterStr = "AND role_id = '" + request.getParameter("roleId") + "'";
        } else {
            filterStr = "AND role_id = 'role_id'";
        }

//        String sql = "SELECT a.uuid,m.id as id,m.pid as pid,m.state as state,m.iconCls,m.text,m.url,a.roleId," +
//                "a.accessAuth FROM sys_menu m LEFT JOIN sys_auth_access a ON m.id=a.id WHERE m.codeSetId = 'menu' AND m.levelId = '1'"
//                + filterStr;
        List<Map<String, Object>> listObj = authAccessService.getListByJdbc(filterStr);
        //JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(listObj));
        //List<AuthAccess> list = JSON.parseArray(JSON.toJSONString(listObj), AuthAccess.class);
        return listObj;
    }

    /**
     * 角色管理界面，点击展开下一级菜单
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getListByRoleIdAndPid1")
    public Object getListByRoleIdAndPid1() {
        String roleId = request.getParameter("roleId");
        String pid = request.getParameter("pid");
//        String hql = "select new AuthAccess(a.uuid, m.id, m.pid, m.state, m.iconCls, m.text, m.url, "
//                + "a.roleId,a.accessAuth,a.detailAuth,a.addAuth,a.editAuth,a.deleteAuth,a.searchAuth,a.importAuth,a.exportAuth) "
//                + "FROM AuthAccess a, Menu m "
//                + "WHERE a.roleId IN (" + roleId + ") AND m.pid = '" + pid + "' AND a.id = m.id ORDER BY m.sort ASC";
        List<AuthAccess> list = authAccessService.getListByRoleIdAndPid1(roleId, pid);
        return JSON.toJSON(list);
    }


    /**
     * 根据角色Id查看所有菜单(一次性查出来)
     *
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getListByRoleIdAllMenu")
    public Object getListByRoleIdAllMenu(String roleId) {
        List<AuthAccess> list = authAccessViewService.getListByRoleIdAllMenu(roleId);
        return list;
    }

    @ResponseBody
    @RequestMapping(value = "resetListByRoleId")
    public Object resetListByRoleId (String uuid) {
        // 1.通过uuid找到所有的角色信息
        if(uuid.equals("")){
            return failure("请选择角色!");
        }
        // 2.获取角色id

        List<AuthGroup> authGroup = authGroupService.getListByUuid(uuid);
        List<Integer> roleIdList = new ArrayList<Integer>() ;
        for(int i = 0; i < authGroup.size(); i++){
            roleIdList.add(authGroup.get(i).getId());
        }
        // 3.根据角色Id删除菜单列表
        int result = authAccessService.deleteByRoleId(roleIdList);
//        if ( result == 0) return failure("角色菜单同步失败！");

        // 4.调用存储过程给以上菜单重新赋值
        for(int i = 0; i < roleIdList.size() ; i++){
            authAccessService.insertMultiple(roleIdList.get(i));
        }
        return success("菜单重置成功!");

    }

    @ResponseBody
    @RequestMapping(value = "getListByRoleIdAndPid")
    public Object getListByRoleIdAndPid(String roleId, String id) {
        return authAccessViewService.getListByRoleIdAndPid(roleId, id);
    }

    /**
     * 授权
     *
     * @param authAccess 授权菜单
     * @param roleId 角色id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "grant")
    @ApiOperation(value = "授权菜单")
    @CacheEvict(cacheNames={"authAccessView","authAccess"}, allEntries=true)
    public Object grant(AuthAccess authAccess, String roleId) {
        try {
            Integer ct;
            Map<String, Object> nodeMap = new HashMap<>();
            List<Map<String, Object>> nodes = new ArrayList<>();
            for (String uuid : authAccess.getUuid().toString().split(",")) {
                ct = authAccessService.getListByRoleIdAndUuid(Integer.parseInt(roleId), uuid);
                if (ct == 1) {
                    int result = authAccessService.updateAccessAuth(Integer.parseInt(roleId), uuid);
                    if (result >= 1) {
                        nodeMap.put("accessAuth", "1");
                        nodes.add(nodeMap);
                    }
                } else {
                    authAccess.setAccessAuth(1);
                    authAccess.setRoleId(Integer.parseInt(roleId));
                    authAccess.setUuid(uuid);
                    authAccessService.insertSelective(authAccess);
                }
            }
            updateStateToOpen();
            Map map = JsonUtils.messageJson(200, Constants.OPERATE_TIPS, Constants.OPERATE_SUCCESS_MSG);
            map.put("data", nodes);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return JsonUtils.messageJson(300, Constants.OPERATE_TIPS, "授权失败！");
        }
    }

    /**
     *
     * @param roleId 角色id
     * @param uuid authAccess表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "ungrant")
    @ApiOperation(value = "取消授权菜单")
    @CacheEvict(cacheNames={"authAccessView","authAccess"}, allEntries=true)
    public Object ungrant(String roleId, String[] uuid) {
        Integer ct;
        Map<String, Object> nodeMap = new HashMap<>();
        List<Map<String, Object>> nodes = new ArrayList<>();
        for (String singleUuid : uuid) {
            ct = authAccessService.getListByRoleIdAndUuids(roleId, singleUuid);
            if (ct == 1) {
                Integer result = authAccessService.updateAccessAuths(roleId, singleUuid);
                if (result >= 1) {
                    nodeMap.put("accessAuth", "0");
                    nodes.add(nodeMap);
                }
            }
        }
        updateStateToOpen();
        Map map = JsonUtils.messageJson(200, Constants.OPERATE_TIPS, Constants.OPERATE_SUCCESS_MSG);
        map.put("data", nodes);
        return map;
    }

    /**
     * 同步菜单
     */
    @ResponseBody
    @RequestMapping(value = "syncMenu")
    public Object syncMenu() {
        String uuid = request.getParameter("uuid");
        // String groupHql = "from AuthGroup WHERE uuid in ('" + uuid + "')";
        List<AuthGroup> authGroups = authGroupService.getListByUuid(uuid);
        for (int i = 0; i < authGroups.size(); i++) {
            int result = authAccessService.getListByRoleIdInt(authGroups.get(i).getId());
            if (result > 0) {
                return success("菜单已同步！");
            }
        }

        for (AuthGroup authGroup : authGroups) {
            if (authGroup.getId() > 0) {
                //写入顶级菜单“导航菜单”
                Integer rootId = 2;
                // String rootNodeHql = "from AuthAccess where roleId = '" + authGroup.getId() + "' and id = '" + rootId + "'";
                Integer ct = authAccessService.getByRoleIdAndId(authGroup.getId(), rootId);
                if (ct == 0) {
                    AuthAccess rootNode = new AuthAccess();
                    //rootNode.setUuid(BaseUtils.UUIDGenerator());
                    rootNode.setId(rootId);
                    rootNode.setPid(0);
                    rootNode.setLevelId("1");
                    rootNode.setText("导航菜单");
                    rootNode.setRoleId(authGroup.getId());
                    //  rootNode.setCreateTime(new Date());
                    authAccessService.insertSelective(getSaveData(rootNode));
                }
                // 查询出所有菜单
                // String allMenuHql = "from Menu";
                List<Menu> allMenus = menuService.selectAll();
                // 要更新的用户组对应最高级菜单Id
                String menuIds = menuService.getChildMenus(allMenus, authGroup.getMenuId());

                //  String menuHql = "from Menu WHERE id in (" + menuIds + ")";
                List<Menu> menus = menuService.getListById(menuIds);
                for (Menu menu : menus) {
                    authAccessService.insertOrUpdateAuthAccess(authGroup.getId(), menu);
                }
            }
        }
        updateStateToOpen();
        return JsonUtils.messageJson(200, Constants.OPERATE_TIPS, Constants.OPERATE_SUCCESS_MSG);
    }

    /**
     * 更新最后一级菜单的state值为open
     */
    public void updateStateToOpen() {
        String updateLastMenuSql = "UPDATE AuthAccess SET state = 'open' WHERE ID IN (SELECT t.pid FROM (SELECT DISTINCT(pid) FROM AuthAccess WHERE resourceType != 'menu') t)";

        // String hql1 = "SELECT DISTINCT(pid) FROM sys_menu WHERE resourceType != 'menu'";
        List<Map<String, Object>> lists = authAccessService.getListByResourceType();

        String pids = "";
        for (Map<String, Object> list : lists) {
//            pids += "'" + list.get("pid") + "',";
            pids += list.get("pid") + ",";
        }
        if(!"".equals(pids.trim())){
            pids = pids.substring(0, pids.length() - 1);

            // String hql2 = "UPDATE AuthAccess SET state = 'open' WHERE ID IN (" + pids + ")";
            authAccessService.updateByPids(pids);
        }
    }

    /**
     * 获得用户的用户组ID信息
     *
     * @return
     */
    public String getRoleIdByUserNameId(String userNameId) {
        return userService.getRoleIdByUserNameId(userNameId);
    }

    /**
     * 后台左侧导航菜单初始化调用
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getMenuListByRoleIdAndCodeSetIdAndLevelId1", method = RequestMethod.GET)
    public Object getMenuListByRoleIdAndCodeSetIdAndLevelId1() {
        String codeSetId = request.getParameter("codeSetId");
        String levelId = request.getParameter("levelId");
        String menuId = request.getParameter("menuId");
//        String hql = "select new AuthAccess(a.uuid, m.id, m.pid, a.state, m.iconCls, m.text, m.url, a.roleId, a.accessAuth) " +
//                "FROM AuthAccess a, Menu m " +
//                "WHERE a.roleId IN (" + getRoleIdByUserNameId(userInfo.getUserNameId()) + ") AND a.accessAuth = '1' " +
//                "AND m.resourceType = 'menu' AND m.codeSetId = '" + codeSetId + "' AND m.levelId = '" + levelId + "' " +
//                "AND m.status = 1 AND m.pid = '" + menuId + "' AND a.id = m.id ORDER BY m.sort ASC";
        List<AuthAccess> list = authAccessService.selectCodeLeveMenu(getRoleIdByUserNameId(LoginInfo.getUserNameId()), codeSetId, levelId, menuId);
        return list;
    }

    @ResponseBody
    @RequestMapping(value = "getAuthorizedMenu", method = RequestMethod.GET)
    public List<AuthAccessView> getAuthorizedMenu(String roleId) {
        return authAccessViewService.getAuthorizedMenu(roleId);
    }

    @ResponseBody
    @RequestMapping(value = "getMenuListByRoleIdAndCodeSetIdAndLevelId", method = RequestMethod.GET)
    public Object getMenuListByRoleIdAndCodeSetIdAndLevelId(String menuId, String codeSetId, String levelId) {
        String curruentUserNameId = LoginInfo.getUserNameId();
        String roleId = getRoleIdByUserNameId(curruentUserNameId);
        return authAccessViewService.getAuthorizedMenu(roleId, menuId, codeSetId, levelId);
    }

    @ResponseBody
    @RequestMapping(value = "getMenuListByRoleIdAndCodeSetId", method = RequestMethod.GET)
    public Object getMenuListByRoleIdAndCodeSetId(String menuId, String codeSetId) {
        String curruentUserNameId = LoginInfo.getUserNameId();
        String roleId = getRoleIdByUserNameId(curruentUserNameId);
        return authAccessViewService.getAuthorizedMenu(roleId, menuId, codeSetId);
    }

    /**
         * 点击展开后台左侧导航菜单
         *
         * @return
         */
        @ResponseBody
        @RequestMapping(value = "getMenuListByPid1")
        public Object getMenuListByPid1() {
            String pid = request.getParameter("pid");
//        String hql = "select new AuthAccess(a.uuid, m.id, m.pid, a.state, m.iconCls, m.text, m.url, a.roleId, a.accessAuth) "
//                + "FROM AuthAccess a, Menu m "
//                + "WHERE a.roleId IN (" + getRoleIdByUserNameId(userInfo.getUserNameId()) + ") AND a.accessAuth = '1' AND m.resourceType = 'menu' AND a.id = m.id AND m.pid = '" + pid + "' ORDER BY m.sort ASC";
            List<AuthAccess> list = authAccessService.selectUserNameIdAndPid(getRoleIdByUserNameId(LoginInfo.getUserNameId()), pid);
            return list;
    }

    @ResponseBody
    @RequestMapping(value = "getMenuListByPid3")
    public Object getMenuListByPid3() {
        String pid = request.getParameter("pid");
//        String hql = "SELECT *, COUNT(DISTINCT id) FROM v_sys_auth_access "
//                + "WHERE roleId IN (" + getRoleIdByUserNameId(userInfo.getUserNameId()) + ") AND accessAuth = '1' " +
//                "AND status = 1 AND resourceType = 'menu' AND pid = '" + pid + "' GROUP BY id ORDER BY sort ASC";
        //String currentUserNameId = "ewsd0001";
//        String roleId = getRoleIdByUserNameId(userInfo.getUserNameId());
        String roleId =LoginInfo.getRoleId();
        List<Map<String, Object>> list = authAccessService.getListByRoleIdByUserNameIdAndPid(roleId, pid);
        return list;
    }

    @ResponseBody
    @RequestMapping(value = "getMenuListByPid")
    public Object getMenuListByPid(String pid) {
        String roleId =LoginInfo.getRoleId();
        List<Map<String, Object>> list = authAccessService.getListByRoleIdByUserNameIdAndPid(roleId, pid);
        return list;
    }

    @ResponseBody
    @RequestMapping(value = "getMenuListByText")
    public Object getMenuListByText() {
        String text = request.getParameter("text");
        String roleId =LoginInfo.getRoleId();
        List<Map<String, Object>> list = authAccessService.getListByRoleIdByUserNameIdAndText(roleId, text);
        return list;
    }

    /**
     * 获得权限控制详细信息，用来控制按钮显示
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getDetailByRoleIdAndUrl")
    public Object getDetailByRoleIdAndUrl() {
//        String hql = "select new AuthAccess(a.uuid, m.id, m.pid, m.state, m.iconCls, m.text, m.url, a.roleId, a.accessAuth) "
//                + "FROM AuthAccess a, Menu m "
//                + "WHERE m.id = a.id and a.roleId IN (" + getRoleIdByUserNameId(userInfo.getUserNameId()) + ")  AND m.url = '" + UrlUtils.getRefererUri(request) + "' ORDER BY m.sort ASC";
        AuthAccess authAccess = authAccessService.getDetailByRoleIdByUserNameIdAndUrl(getRoleIdByUserNameId(LoginInfo.getUserNameId()), UrlUtils.getRefererUri(request));
        //System.out.println(JSON.toJSON(authAccess));
        return JSON.toJSON(authAccess);
    }

    /**
     * 根据角色和要启动的资源获得是否有访问权限
     *
     * @return boolean
     */
    @ResponseBody
    @RequestMapping(value = "getAuthByRoleIdAndUrl")
    public Integer getAuthByRoleIdAndUrl() {
        String url = request.getParameter("url");
        //String hql = "FROM AuthAccess WHERE roleId IN (" + getRoleId() + ")  AND url = '" + url + "' AND accessAuth = '1'";
        // String sql = "SELECT COUNT(*) FROM sys_menu m, sys_auth_access a WHERE a.roleId IN (" + getRoleIdByUserNameId(userInfo.getUserNameId()) + ") AND m.url = '" + url + "' AND a.accessAuth = '1' AND a.id = m.id";
        Integer ct = authAccessService.getCountByCurrentUserNameIdAUrl(getRoleIdByUserNameId(LoginInfo.getUserNameId()), url);
        return ct >= 1 ? 1 : 0;
    }

    @RequestMapping("add")
    public String add() {
        return display();
    }

    @ResponseBody
    @RequestMapping(value = "doAdd", method = RequestMethod.POST)
    public Object doAdd(AuthAccess groupAccess) {
        return authAccessService.insertSelective(groupAccess);
    }

    @RequestMapping("edit")
    public String edit() throws Exception {
        return display();
    }

    @ResponseBody
    @RequestMapping(value = "doEdit", method = RequestMethod.POST)
    public Object doEdit(AuthAccess groupAccess) {
        return authAccessService.insertSelective(groupAccess);
    }

    @ResponseBody
    @RequestMapping("delete")
    public Object delete(String uuid) throws Exception {
        try {
            String[] uuidArr = uuid.split(",");
            String allUuids;
            int ruselt = 0;
            for (int i = 0; i < uuidArr.length; i++) {
                allUuids = authAccessService.getSelfAndChildUuids(uuidArr[i], "$");
                ruselt = authAccessService.deleteByPrimaryKey(allUuids);
                //  ruselt=authAccessService.deleteByUuid(allUuids);
                //  ruselt++;
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @RequestMapping("sysAuthAdd")
    public String sysAuthAdd() {
        return display();
    }

    @ResponseBody
    @RequestMapping(value = "sysAuthSave", method = RequestMethod.POST)
    public Object sysAuthSave(AuthAccess groupAccess) {
        return authAccessService.insertSelective(groupAccess);
    }

    @RequestMapping("moduleAuthAdd")
    public String moduleAuthAdd() {
        return display();
    }

    @ResponseBody
    @RequestMapping(value = "moduleAuthSave", method = RequestMethod.POST)
    public Object moduleAuthSave(AuthAccess groupAccess) {
        return authAccessService.insertSelective(groupAccess);
    }

}
