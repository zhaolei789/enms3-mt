package cn.ewsd.cost.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.cost.model.FAwardAssign;
import cn.ewsd.cost.model.FAwardAssignSelect_A;
import cn.ewsd.cost.service.CostBaseService;

import java.util.List;
import java.util.Map;

/**
 * 罚款分配
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-08-26 18:03:29
 */
public interface FAwardAssignService extends CostBaseService<FAwardAssign, String> {

    PageSet<FAwardAssign> getPageSet(PageParam pageParam, String filterSort);

	FAwardAssign queryObject(String uuid);
	
	List<FAwardAssign> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(FAwardAssign fAwardAssign);
	
	int executeUpdate(FAwardAssign fAwardAssign);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuid);

	List<FAwardAssign> queryListByFilterSort(String filterSort);

	List<FAwardAssignSelect_A> queryListByLeadExcel(String filterSort);
}
