package cn.ewsd.cost.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.cost.model.SysUserLikeMenu;
import cn.ewsd.cost.service.CostBaseService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户喜欢的菜单
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-30 09:10:09
 */
public interface SysUserLikeMenuService extends CostBaseService<SysUserLikeMenu, String> {

    PageSet<SysUserLikeMenu> getPageSet(PageParam pageParam, String filterSort);

	SysUserLikeMenu queryObject(String uuid);
	
	List<SysUserLikeMenu> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(SysUserLikeMenu sysUserLikeMenu);
	
	int executeUpdate(SysUserLikeMenu sysUserLikeMenu);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);

	SysUserLikeMenu queryByUserIdAndUri(String user_id, String menu_uri);

	int executeAddClicksNumber(String user_id, String menu_uri);

	int queryTotalByUser(String user_id);

	List<SysUserLikeMenu> queryListByUser(String user_id);
}
