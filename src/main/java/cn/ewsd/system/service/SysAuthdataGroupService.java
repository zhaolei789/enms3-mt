package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.SysAuthdataGroup;

import java.util.List;
import java.util.Map;

/**
 * 数据权限分组
 * 
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-12-26 09:57:35
 */
public interface SysAuthdataGroupService extends SystemBaseService<SysAuthdataGroup, String> {

    PageSet<SysAuthdataGroup> getPageSet(PageParam pageParam, String filterSort);

	SysAuthdataGroup queryObject(String uuid);
	
	List<SysAuthdataGroup> queryList(Map<String, Object> map);


	int queryTotal(Map<String, Object> map);

    int executeSave(SysAuthdataGroup sysAuthdataGroup);
	
	int executeUpdate(SysAuthdataGroup sysAuthdataGroup);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);

    String getGroupUuidsByIds(String ids);

    Integer getMaxById();
}
