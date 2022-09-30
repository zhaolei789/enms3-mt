package cn.ewsd.cost.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.cost.model.FAccount;
import cn.ewsd.cost.service.CostBaseService;

import java.util.List;
import java.util.Map;

/**
 * 工程结算
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-03 08:39:36
 */
public interface FAccountService extends CostBaseService<FAccount, String> {

    PageSet<FAccount> getPageSet(PageParam pageParam, String filterSort);

	FAccount queryObject(String uuid);
	
	List<FAccount> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(FAccount fAccount);
	
	int executeUpdate(FAccount fAccount);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);
}
