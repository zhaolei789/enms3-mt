package cn.ewsd.material.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MSite;
import cn.ewsd.material.service.MaterialBaseService;

import java.util.List;
import java.util.Map;

/**
 * 货位
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2022-07-06 17:28:02
 */
public interface MSiteService extends MaterialBaseService<MSite, String> {

    PageSet<MSite> getPageSet(PageParam pageParam, String filterSort);

	MSite queryObject(String uuid);
	
	List<MSite> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(MSite mSite);
	
	int executeUpdate(MSite mSite);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);

	List<MSite> queryListByFS(String filterSort);

	List<MSite> queryListByIds(String[] uuids);

	List<MSite> getAllList();

	List<MSite> getListByStoreNo(String storeNo);
}
