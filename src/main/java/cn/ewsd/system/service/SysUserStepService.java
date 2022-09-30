package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.SysUserStep;
import cn.ewsd.system.service.SystemBaseService;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-04-23 11:19:10
 */
public interface SysUserStepService extends SystemBaseService<SysUserStep, String> {

    PageSet<SysUserStep> getPageSet(PageParam pageParam, String filterSort);

	SysUserStep queryObject(String uuid);
	
	List<SysUserStep> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(SysUserStep sysUserStep);
	
	int executeUpdate(SysUserStep sysUserStep);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);

	/**
	 * 查看用户是否拥有权限
	 * @param stepId 步骤ID
	 * @param userId 用户uuid
	 * @param orgId 部门id
	 * @return
	 */
	boolean checkUserHavePms(String stepId,String userId,String orgId);

	/**
	 * 查看用户在该步骤下的权限部门
	 * @param stepId 步骤ID
	 * @param userId 用户uuid
	 * @return
	 */
	String selectUserPmsOrg(String stepId,String userId);

	/**
	 * 查看用户在流程下的步骤
	 * @param processNo 流程ID
	 * @param userId 用户uuid
	 * @return
	 */
	String selectUserStepSqlByProcessNo(String processNo,String userId);

	List<String> selectUserStepListByProcessNo(String processNo, String userId);

	List<SysUserStep> queryListByFS(String filterSort);

	List<SysUserStep> queryListByStepGroupUser(String stepId);

	List<SysUserStep> queryListByStepAndOrgGroupUser(String stepUuid,Integer auditOrg);
}
