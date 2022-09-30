package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.Menu;

import javax.swing.tree.TreeNode;
import java.util.List;
import java.util.Map;

public interface MenuService extends SystemBaseService<Menu, String> {

	public Menu selectByPrimaryKey(String pk);

	public String getChildMenus(List<Menu> list, Integer typeId);

	public void recursionFn(List<Menu> list, Menu menu);

	public boolean hasChild(List<Menu> list, Menu menu);

	public List<Menu> getChildList(List<Menu> list, Menu menu);

	public String getFatherIds(Integer id);

	/**
	 * 获得菜单列表
	 * @param codeSetId 代码集编号
	 * @param levelId 层级编号
	 * @return
	 */
	public List<Menu> getListByCodeSetIdAndLevelId(String codeSetId, String levelId);

	public List<Menu> getListByLevelId(String level_id);

	/**
	 * 获得菜单列表
	 * @param pid 关联编号
	 * @return
	 */
	public List<Menu> getListByPid(String pid);

	public Menu getMenuById(Integer id);

	public List<Menu> getList(Integer parentid);

	public List<TreeNode> getTreeNodes(Integer id);

	List<Menu> getListById(String menuIds);

	Integer getMaxById();

	int updateByUuid(String codeSetId, Integer levelId, Integer pid, String resourceType, String text, String url, String state, Integer status, String iconCls, Integer sort, String remark, String uuid);

	void updatePid(Integer pid);

	PageSet<Menu> getPageSet(PageParam pageParam, String filterSort);

	String getChildIds(Map map);

	String getFatherId(Map map);

    Integer cascadeDeleteByUuid(String s);

    int getLevelIdById(int pId);
}