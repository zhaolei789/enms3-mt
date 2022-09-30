package cn.ewsd.system.service;

import cn.ewsd.system.model.AuthAccess;
import cn.ewsd.system.model.AuthAccessView;

import java.util.List;
import java.util.Map;

public interface AuthAccessViewService extends SystemBaseService<AuthAccessView, String> {

    /**
     * 获得系统授权菜单
     *
     * @param roleId
     * @param menuId
     * @param codeSetId
     * @param levelId
     * @return
     */
    public List<Map<String, Object>> getAuthorizedMenu(String roleId, String menuId, String codeSetId, String levelId);

    public List<Map<String, Object>> getAuthorizedMenu(String roleId, String menuId, String codeSetId);

    /**
     * 获得系统授权菜单、按钮、链接、窗口等
     *
     * @param roleId
     * @return
     */
    public List<AuthAccessView> getAuthorizedMenu(String roleId);

    /**
     * 展开授权菜单
     *
     * @param roleId
     * @param id
     * @return
     */
    public List<Map<String, Object>> getListByRoleIdAndPid(String roleId, String id);

    /**
     * 获得授权的系统，横向导航菜单
     *
     * @param codeSetId
     * @param levelId
     * @param roleId
     * @return
     */
    public List<AuthAccessView> getSystemList(String codeSetId, String levelId, String roleId) throws Exception;

    /**
     * 根据角色Id查看所有菜单(一次性查出来)
     * @param roleId
     * @return
     */
    List<AuthAccess> getListByRoleIdAllMenu(String roleId);

}