package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.SysPost;
import cn.ewsd.system.service.SystemBaseService;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2022-03-07 15:11:32
 * 11
 */
public interface SysPostService extends SystemBaseService<SysPost, String> {

    PageSet<SysPost> getPageSet(PageParam pageParam, String filterSort);

	SysPost queryObject(String uuid);
	
	List<SysPost> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(SysPost sysPost);
	
	int executeUpdate(SysPost sysPost);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);

	int queryTotalByFS(String filterSort);

	List<SysPost> queryListByFS(String filterSort);
}
