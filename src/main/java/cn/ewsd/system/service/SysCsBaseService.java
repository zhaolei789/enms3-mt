package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.SysCsBase;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-07-24 15:27:51
 */
public interface SysCsBaseService extends SystemBaseService<SysCsBase, String> {

    PageSet<SysCsBase> getPageSet(PageParam pageParam, String filterSort);

	SysCsBase queryObject(String uuid);
	
	List<SysCsBase> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(SysCsBase sysCsBase);
	
	int executeUpdate(SysCsBase sysCsBase);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);
}
