package cn.ewsd.mdata.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.mdata.model.SysEmployees;
import cn.ewsd.mdata.model.User;
import cn.ewsd.mdata.service.MdataBaseService;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-03-23 11:33:15
 */
public interface SysEmployeesService extends MdataBaseService<SysEmployees, String> {

    PageSet<SysEmployees> getPageSet(PageParam pageParam, String filterSort);

	SysEmployees queryObject(String uuid);
	
	List<SysEmployees> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(SysEmployees sysEmployees);
	
	int executeUpdate(SysEmployees sysEmployees);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);

	SysEmployees getByEmpNo(String empNo);

	List<SysEmployees> selectAllList();

	List<SysEmployees> getLimitedListByQ(String q);
}
