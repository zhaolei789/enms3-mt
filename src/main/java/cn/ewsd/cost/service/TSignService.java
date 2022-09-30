package cn.ewsd.cost.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.cost.model.TSign;
import cn.ewsd.cost.service.CostBaseService;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-17 10:27:06
 */
public interface TSignService extends CostBaseService<TSign, String> {

    PageSet<TSign> getPageSet(PageParam pageParam, String filterSort);

	TSign queryObject(String uuid);
	
	List<TSign> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(TSign tSign);
	
	int executeUpdate(TSign tSign);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);
}
