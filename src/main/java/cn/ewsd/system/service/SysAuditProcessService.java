package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.SysAuditProcess;
import cn.ewsd.system.service.SystemBaseService;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-04-23 11:18:36
 */
public interface SysAuditProcessService extends SystemBaseService<SysAuditProcess, String> {

    PageSet<SysAuditProcess> getPageSet(PageParam pageParam, String filterSort);

	SysAuditProcess queryObject(String uuid);
	
	List<SysAuditProcess> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(SysAuditProcess sysAuditProcess);
	
	int executeUpdate(SysAuditProcess sysAuditProcess);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);

	//=============================================

	int executeResetInitStateBatch(String[] uuids);

	int executeUpdateInitState(String initState,String uuid);

	int executeUpdateStep(String lastStep,String nextStep,String uuid);

	List<SysAuditProcess> queryListByFuuid(String fuuid);

	List<SysAuditProcess> queryListByUuids(String[] uuids);

	SysAuditProcess queryObjectByProcessNo(String processNo);

	String getFirstStepNoByProcessNo(String processNo);

	SysAuditProcess getFirstStepByProcessNo(String processNo);
}
