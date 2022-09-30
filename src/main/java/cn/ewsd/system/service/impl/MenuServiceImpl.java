package cn.ewsd.system.service.impl;

import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.system.mapper.MenuMapper;
import cn.ewsd.system.model.Menu;
import cn.ewsd.system.service.MenuService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service("menuServiceImpl")
@CacheConfig(cacheNames = "menu")
public class MenuServiceImpl extends SystemBaseServiceImpl<Menu, String> implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Autowired
    public UserInfo userInfo;

//    @Resource
//    private AuthAccessService authAccessService;

//    @Resource
//    public void setSystemBaseDao(ISystemBaseDao<Menu, String> menuDao) {
//        super.setBaseDao(menuDao);
//    }

    private List<Integer> returnList = new ArrayList<Integer>();

    @Override
//    @Cacheable(key = "#p0")
    public Menu selectByPrimaryKey(String pk) {
        return menuMapper.selectByPrimaryKey(pk);
    }

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list   分类表
     * @param typeId 传入的父节点ID
     * @return String
     */
    @Override
    public String getChildMenus(List<Menu> list, Integer typeId) {
        returnList.clear();
        if (list == null && typeId == null) return "";
        for (Iterator<Menu> iterator = list.iterator(); iterator.hasNext(); ) {
            Menu menu = (Menu) iterator.next();
            System.out.println(menu.getId());
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (typeId.equals(menu.getId())) {
                recursionFn(list, menu);
            }
            // 二、遍历所有的父节点下的所有子节点
            /*if (menu.getParentId()==0) {
                recursionFn(list, menu);
            }*/
        }
        return returnList.toString().substring(1, returnList.toString().length() - 1);
    }

    @Override
    public void recursionFn(List<Menu> list, Menu menu) {
        List<Menu> childList = getChildList(list, menu);// 得到子节点列表
        if (hasChild(list, menu)) {// 判断是否有子节点
            returnList.add(menu.getId());
            Iterator<Menu> it = childList.iterator();
            while (it.hasNext()) {
                Menu n = (Menu) it.next();
                recursionFn(list, n);
            }
        } else {
            returnList.add(menu.getId());
        }
    }

    // 得到子节点列表
    @Override
    public List<Menu> getChildList(List<Menu> list, Menu menu) {
        List<Menu> menuList = new ArrayList<Menu>();
        Iterator<Menu> it = list.iterator();
        while (it.hasNext()) {
            Menu n = (Menu) it.next();
            if (n.getPid().equals(menu.getId())) {
                menuList.add(n);
            }
        }
        return menuList;
    }

    // 判断是否有子节点
    @Override
    public boolean hasChild(List<Menu> list, Menu menu) {
        return getChildList(list, menu).size() > 0 ? true : false;
    }

    List<Integer> idArray = new ArrayList<Integer>();

    @Override
    public String getFatherIds(Integer id) {
        // String hql = "from Menu where id = '" + id + "'";
        Integer count = menuMapper.getCountById(id);
        if (count >= 1) {
            Menu menu = menuMapper.getDetailById(id);
            idArray.add(menu.getPid());
            getFatherIds(menu.getPid());
        }
        return idArray.toString().substring(1, idArray.toString().length() - 1);
    }

    @Override
    public List<Menu> getListByCodeSetIdAndLevelId(String codeSetId, String levelId) {
        //   String hql = "FROM Menu WHERE codeSetId = '" + codeSetId + "' AND levelId = '" + levelId + "' ORDER BY sort ASC";
        return menuMapper.getListByCodeSetIdAndLevelId(codeSetId, levelId);
    }

    @Override
//    @Cacheable
    public List<Menu> getListByLevelId(String level_id) {
        // String hql = "FROM Menu WHERE levelId = '" + levelId + "' ORDER BY sort ASC";
        return menuMapper.getListByLevelId(level_id);
    }

    @Override
//    @Cacheable(key = "#p0")
    public List<Menu> getListByPid(String pid) {

        // String hql = "FROM Menu WHERE pid = '" + pid + "' AND levelId <> '0' ORDER BY sort ASC";
        return menuMapper.getListByPidAndLevelId(pid, 0);
    }

    @Override
    public Menu getMenuById(Integer id) {
        return menuMapper.getDetailById(id);
    }

    @Override
    public List<Menu> getList(Integer parentid) {
        // "FROM Menu WHERE resourceType = 'menu' AND pid = '" + parentid + "'"
        return menuMapper.getListByParentid(parentid);
    }

    @Override
    public List<javax.swing.tree.TreeNode> getTreeNodes(Integer id) {
        return null;
    }

    //递归调用的方法
    //获取子节点的集合
    //https://www.cnblogs.com/diaozhaojian/p/6204387.html
//    @Override
//    public List<TreeNode> getTreeNodes(Integer id) {
//        List<TreeNode> treeNodes = null;
//        List<Menu> menus = getList(id);
//        if (menus != null && menus.size() > 0) {
//            treeNodes = new ArrayList<>();
//            for (Menu menu : menus) {
//                TreeNode treeNode = new TreeNode(menu.getId(), menu.getText());
//                System.out.println("name=" + menu.getText());
//                //得到节点的子节点
//                //递归的调用
//                List<TreeNode> children = getTreeNodes(menu.getId());
//                treeNode.setChildren(children);
//                treeNodes.add(treeNode);
//            }
//        }
//        return treeNodes;
//    }

    @Override
    public List<Menu> getListById(String menuIds) {
        List<String> list = new ArrayList<String>();
        String d[] = menuIds.split(",");
        for (int i = 0; i < d.length; i++) {
            list.add(d[i]);
        }
        return menuMapper.getListById(list);
    }

    @Override
    public Integer getMaxById() {
        return menuMapper.getMaxById();
    }

    @Override
//    @CachePut(key = "#p11")
    @CacheEvict(cacheNames={"authAccessView","authAccess"}, allEntries=true)
    public int updateByUuid(String codeSetId, Integer levelId, Integer pid, String resourceType, String text, String url, String state, Integer status, String iconCls, Integer sort, String remark, String uuid) {
        return menuMapper.updateByUuid(codeSetId, levelId, pid, resourceType, text, url, state, status, iconCls, sort, remark, uuid);
    }

    @Override
    public void updatePid(Integer pid) {
        menuMapper.updatePid(pid);
    }

    @Override
    public PageSet<Menu> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<Menu> list = menuMapper.getPageSet(filterSort);
        PageInfo<Menu> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public String getChildIds(Map map) {
        return menuMapper.getChildIds(map);
    }

    @Override
    public String getFatherId(Map map) {
        return menuMapper.getFatherId(map);
    }

    @Override
    public Integer cascadeDeleteByUuid(String uuid) {
        return menuMapper.cascadeDeleteByUuid(uuid);
    }

    @Override
    public int getLevelIdById(int id) {
        return menuMapper.getLevelIdById(id);
    }
}
