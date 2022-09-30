package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.SysLoggerInfo;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-12-24 16:27:06
 */
public interface SysLoggerInfoService extends SystemBaseService<SysLoggerInfo, String> {

    PageSet<SysLoggerInfo> getPageSet(PageParam pageParam, String filterSort);

	SysLoggerInfo queryObject(String uuid);
	
	List<SysLoggerInfo> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(SysLoggerInfo sysLoggerInfo);
	
	int executeUpdate(SysLoggerInfo sysLoggerInfo);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);
}
