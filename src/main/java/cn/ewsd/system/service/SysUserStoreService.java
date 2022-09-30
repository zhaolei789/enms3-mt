package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.SysUserStore;
import cn.ewsd.system.service.SystemBaseService;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-06-09 17:58:25
 */
public interface SysUserStoreService extends SystemBaseService<SysUserStore, String> {

    PageSet<SysUserStore> getPageSet(PageParam pageParam, String filterSort);

	SysUserStore queryObject(String uuid);
	
	List<SysUserStore> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(SysUserStore sysUserStore);
	
	int executeUpdate(SysUserStore sysUserStore);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);
}
