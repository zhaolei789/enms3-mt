package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.SysUserItem;
import cn.ewsd.system.service.SystemBaseService;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-05-10 09:39:02
 */
public interface SysUserItemService extends SystemBaseService<SysUserItem, String> {

    PageSet<SysUserItem> getPageSet(PageParam pageParam, String filterSort);

	SysUserItem queryObject(String uuid);
	
	List<SysUserItem> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(SysUserItem sysUserItem);
	
	int executeUpdate(SysUserItem sysUserItem);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);
}
