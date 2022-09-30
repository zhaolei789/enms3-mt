package cn.ewsd.cost.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.cost.model.SysUserBarMaster;
import cn.ewsd.cost.service.CostBaseService;

import java.util.List;
import java.util.Map;

/**
 * 用户与矿长
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-17 14:44:35
 */
public interface SysUserBarMasterService extends CostBaseService<SysUserBarMaster, String> {

    PageSet<SysUserBarMaster> getPageSet(PageParam pageParam, String filterSort);

	SysUserBarMaster queryObject(String uuid);
	
	List<SysUserBarMaster> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(SysUserBarMaster sysUserBarMaster);
	
	int executeUpdate(SysUserBarMaster sysUserBarMaster);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);

	int queryTotalByFS(String filterSort);

	List<SysUserBarMaster> queryListByFS(String filterSort);
}
