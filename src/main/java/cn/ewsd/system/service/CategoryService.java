package cn.ewsd.system.service;

import cn.ewsd.common.service.IMybatisBaseService;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.Category;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-06-09 09:20:29
 */
public interface CategoryService extends IMybatisBaseService<Category, String> {

    PageSet<Category> getPageSet(PageParam pageParam, String filterSort);

	Category queryObject(String uuid);
	
	List<Category> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(Category Category);
	
	int executeUpdate(Category Category);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);

	List<Category> getCategorysByTypeAndLevelId(String levelId, String type);

	List<Category> selectListByPid(String pid, String type);

	int getIncreasementId();

	String getChildIds(Map map);

    String getFatherId(Map map);

	List<Category> getCategorysByPortalDisplayAndTypeAndLevelId(String levelId, String type);

}
