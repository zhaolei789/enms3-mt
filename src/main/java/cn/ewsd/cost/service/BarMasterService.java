package cn.ewsd.cost.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.cost.model.BarMaster;
import cn.ewsd.cost.service.CostBaseService;

import java.util.List;
import java.util.Map;

/**
 * 矿长
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-08-31 17:13:54
 */
public interface BarMasterService extends CostBaseService<BarMaster, String> {

    PageSet<BarMaster> getPageSet(PageParam pageParam, String filterSort);

	PageSet<BarMaster> getPageSetYearFee(PageParam pageParam, String filterSort,String fMonth);

	PageSet<BarMaster> getPageSetSbCw(PageParam pageParam, String filterSort,String queryYear,String queryMonth);

	PageSet<BarMaster> getPageSetSbFs(PageParam pageParam, String filterSort,String queryYear,String queryMonth);

	PageSet<BarMaster> getPageSetSettle(PageParam pageParam, String filterSort,String queryYear,String queryMonth);

	BarMaster queryObject(String uuid);
	
	List<BarMaster> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(BarMaster barMaster);
	
	int executeUpdate(BarMaster barMaster);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);
}
