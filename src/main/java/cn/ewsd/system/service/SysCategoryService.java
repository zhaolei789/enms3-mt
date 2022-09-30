package cn.ewsd.system.service;

import cn.ewsd.common.service.IMybatisBaseService;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.SysCategory;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-06-09 09:20:29
 */
public interface SysCategoryService extends IMybatisBaseService<SysCategory, String> {

    PageSet<SysCategory> getPageSet(PageParam pageParam, String filterSort);

	SysCategory queryObject(String uuid);
	
	List<SysCategory> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(SysCategory SysCategory);
	
	int executeUpdate(SysCategory SysCategory);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);

	List<SysCategory> getCategorysByTypeAndLevelId(String levelId, String type);

	List<SysCategory> selectListByPid(String pid, String type);

	int getIncreasementId();

	String getChildIds(Map map);

    String getFatherId(Map map);

	List<SysCategory> getCategorysByPortalDisplayAndTypeAndLevelId(String levelId, String type);

}
