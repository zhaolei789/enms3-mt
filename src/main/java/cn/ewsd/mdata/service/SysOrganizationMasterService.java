package cn.ewsd.mdata.service;

import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.mdata.model.SysOrganizationMaster;
import cn.ewsd.mdata.service.MdataBaseService;

import java.util.List;
import java.util.Map;

/**
 * 部门分管领导
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2022-04-14 11:12:56
 */
public interface SysOrganizationMasterService extends MdataBaseService<SysOrganizationMaster, String> {

    PageSet<SysOrganizationMaster> getPageSet(PageParam pageParam, String filterSort);

	SysOrganizationMaster queryObject(String uuid);
	
	List<SysOrganizationMaster> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(SysOrganizationMaster sysOrganizationMaster);
	
	int executeUpdate(SysOrganizationMaster sysOrganizationMaster);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);

	List<SysOrganizationMaster> queryListByIds(String[] uuids);

	int batchInsert(List<SysOrganizationMaster> list);

	int executeDeleteByOrgBatch(String[] orgIds,String masterUuid);

	/**
	 * 获取当前用户对应分管领导审核的部门
	 * @return
	 */
	String getFgldAuditOrgs();

	String getFgldAuditOrgs(UserInfo userInfo);

	List<SysOrganizationMaster> queryListByMasterIds(String[] masterIds);

	List<SysOrganizationMaster> queryListByFS(String filterSort);
}
