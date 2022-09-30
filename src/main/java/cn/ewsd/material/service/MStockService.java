package cn.ewsd.material.service;

import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.material.model.MMaterial;
import cn.ewsd.material.model.MStock;
import cn.ewsd.repository.model.StockQryDetail;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface MStockService extends MaterialBaseService<MStock, String> {
	int getCountByMatNo(String matNo);

	List<MStock> getTgAmount();

	void checkStockAmount(String matNo, String storeNo) throws Exception;

	MStock getStock(String matNo, String storeNo);

	PageSet<MMaterial> getStockPackMat(PageParam pageParam, String filterSort, String userId, String storeQry, String flagQry, String matQry);

	List<MStock> getStockPackStore(String matNo);

	void saveStore(HttpServletRequest request) throws Exception;

	PageSet<MStock> getDxStockPageSet(PageParam pageParam, String filterSort, String storeNoQry, String flagQry, String matCodeQry, String matNameQry);

	List<MStock> getDxStockList(String storeNoQry, String flagQry, String matCodeQry, String matNameQry);

	PageSet<MStock> getStockQryPageSet(PageParam pageParam, String filterSort, String storeNoQry, String abcTypeQry, String flagQry, boolean ifPower, String matCodeQry, String erpTypeQry, String matNameQry, String warnFlag);

	List<MStock> getStockQryList(String storeNoQry, String abcTypeQry, String flagQry, boolean ifPower, String matCodeQry, String erpTypeQry, String matNameQry, String warnFlag);

	List<StockQryDetail> getStockQryDetail(String matNo, String date1Qry, String date2Qry, String storeNoQry, boolean ifPower);

	PageSet<MStock> getOldApplyList(PageParam pageParam, String filterSort, String storeQry, String matQry);

	PageSet<MMaterial> getUrgentPlanStock(PageParam pageParam, String filterSort, String teamNo, String planMonth, String storeQry, String reserveQry, String matQry);

	PageSet<MStock> getWarnStockPageSet(PageParam pageParam, String filterSort, String storeQry, String flagQry, String codeQry, String nameQry);

	List<MStock> getWarnStockList(String storeQry, String flagQry, String codeQry, String nameQry);

	PageSet<MStock> getInOutStockPageSet(PageParam pageParam, String filterSort, String userId, String storeQry, boolean ifPower, String beginDateQry, String endDateQry, String matCodeQry, String matNameQry);

	List<MStock> getInOutStockList(String userId, String storeQry, boolean ifPower, String beginDateQry, String endDateQry, String matCodeQry, String matNameQry);

	PageSet<MStock> getAllStockQryPageSet(PageParam pageParam, String filterSort, String storeNoQry, String stLevelQry, boolean ifPower, String matCodeQry, String erpTypeQry, String matNameQry);

	List<MStock> getAllStockQryList(String storeNoQry, String stLevelQry, boolean ifPower, String matCodeQry, String erpTypeQry, String matNameQry);

	PageSet<HashMap<String, Object>> getStockSumPageSet(PageParam pageParam, String filterSort, String matCodeQry, String matNameQry, String xjFlagQry, boolean ifPower);

	List<HashMap<String, Object>> getStockSumList(String matCodeQry, String matNameQry, String xjFlagQry, boolean ifPower);

	PageSet<MStock> getStockSumDetailPageSet(PageParam pageParam, String filterSort, String matCodeQry, String matNameQry, String xjFlagQry, String storeNoQry);

	PageSet<MStock> getExpirationAlertPageSet(PageParam pageParam, String filterSort, String matQry);
}
