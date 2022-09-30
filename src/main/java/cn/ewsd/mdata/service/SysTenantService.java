package cn.ewsd.mdata.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.mdata.model.SysTenant;
import cn.ewsd.mdata.service.MdataBaseService;

import java.util.List;
import java.util.Map;

/**
 * 租户
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2022-06-02 18:20:18
 */
public interface SysTenantService extends MdataBaseService<SysTenant, String> {

    PageSet<SysTenant> getPageSet(PageParam pageParam, String filterSort);

	SysTenant queryObject(String uuid);
	
	List<SysTenant> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(SysTenant sysTenant);
	
	int executeUpdate(SysTenant sysTenant);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);

	List<SysTenant> queryListByFS(String filterSort);

	List<SysTenant> queryListByIds(String[] uuids);

	SysTenant getOneOld();
}
