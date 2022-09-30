package cn.ewsd.cost.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.cost.model.FItem;
import cn.ewsd.cost.service.CostBaseService;

import java.util.List;
import java.util.Map;

/**
 * 费用项目
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-07 09:22:21
 */
public interface FItemService extends CostBaseService<FItem, String> {

    PageSet<FItem> getPageSet(PageParam pageParam, String filterSort);

	FItem queryObject(String uuid);
	
	List<FItem> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(FItem fItem);
	
	int executeUpdate(FItem fItem);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);
}
