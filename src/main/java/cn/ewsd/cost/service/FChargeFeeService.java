package cn.ewsd.cost.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.cost.model.FChargeFee;
import cn.ewsd.cost.service.CostBaseService;

import java.util.List;
import java.util.Map;

/**
 * 分管费用
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-07 09:22:24
 */
public interface FChargeFeeService extends CostBaseService<FChargeFee, String> {

    PageSet<FChargeFee> getPageSet(PageParam pageParam, String filterSort);

	PageSet<FChargeFee> getPageSetSbCw(PageParam pageParam, String filterSort,String queryMonth);

	PageSet<FChargeFee> getPageSetSbFs(PageParam pageParam, String filterSort,String queryMonth);

	PageSet<FChargeFee> getPageSetSettle(PageParam pageParam, String filterSort,String fMonth);

	List<FChargeFee> getSettleList(String filterSort, String fMonth);

	List<FChargeFee> getSettleSum(String year);

	FChargeFee queryObject(String uuid);
	
	List<FChargeFee> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(FChargeFee fChargeFee);
	
	int executeUpdate(FChargeFee fChargeFee);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);

	List<FChargeFee> getLeaderSumList(String userId, String yearQry, String month1, String month2);

	List<FChargeFee> getLeaderSumDetailList(String yearQry, String month1, String month2, String leader);

	List<FChargeFee> getLeaderSumDeptList(String yearQry, String month1, String month2, String leader, String teamNo);

	List<FChargeFee> getDeptSumList(String userDeptIds, String yearQry, String month1, String month2);

	List<FChargeFee> getDeptSumDetailList(String yearQry, String month1, String month2, String teamNo);

	List<FChargeFee> getCostAnalysisList(String userDeptIds, String yearQry, String month1, String month2);

	List<FChargeFee> getCostAnalysisDetailList(String yearQry, String month1, String month2, String teamNo);
}
