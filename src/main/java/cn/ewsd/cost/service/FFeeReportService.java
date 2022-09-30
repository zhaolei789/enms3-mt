package cn.ewsd.cost.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.cost.model.FFeeReport;
import cn.ewsd.cost.service.CostBaseService;

import java.util.List;
import java.util.Map;

/**
 * 费用上报
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-09 10:31:35
 */
public interface FFeeReportService extends CostBaseService<FFeeReport, String> {

    PageSet<FFeeReport> getPageSet(PageParam pageParam, String filterSort);

	FFeeReport queryObject(String uuid);
	
	List<FFeeReport> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(FFeeReport fFeeReport);
	
	int executeUpdate(FFeeReport fFeeReport);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);
}
