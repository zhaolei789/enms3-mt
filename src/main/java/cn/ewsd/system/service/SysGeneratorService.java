package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.SysGenerator;
import cn.ewsd.system.service.SystemBaseService;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-12-24 16:27:06
 */
public interface SysGeneratorService extends SystemBaseService<SysGenerator, String> {

    PageSet<SysGenerator> getPageSet(PageParam pageParam, String filterSort);

	SysGenerator queryObject(String uuid);
	
	List<SysGenerator> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(SysGenerator sysGenerator);
	
	int executeUpdate(SysGenerator sysGenerator);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);
}
