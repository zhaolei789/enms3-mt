package cn.ewsd.cost.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.cost.model.FContract;
import cn.ewsd.cost.service.CostBaseService;

import java.util.List;
import java.util.Map;

/**
 * 合同
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-03 08:39:34
 */
public interface FContractService extends CostBaseService<FContract, String> {

    PageSet<FContract> getPageSet(PageParam pageParam, String filterSort);

	FContract queryObject(String uuid);
	
	List<FContract> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(FContract fContract);
	
	int executeUpdate(FContract fContract);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);
}
