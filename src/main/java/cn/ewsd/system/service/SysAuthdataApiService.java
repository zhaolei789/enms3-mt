package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.SysAuthdataApi;

import java.util.List;
import java.util.Map;

/**
 * 数据权限接口
 * 
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-12-26 09:58:25
 */
public interface SysAuthdataApiService extends SystemBaseService<SysAuthdataApi, String> {

    PageSet<SysAuthdataApi> getPageSet(PageParam pageParam, String filterSort);

	SysAuthdataApi queryObject(String uuid);
	
	List<SysAuthdataApi> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(SysAuthdataApi sysAuthdataApi);
	
	int executeUpdate(SysAuthdataApi sysAuthdataApi);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);

	List<SysAuthdataApi> getListByGroupUuidsAndUrl(String[] groupUuids, String url);
}
