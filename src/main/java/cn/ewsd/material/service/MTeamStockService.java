package cn.ewsd.material.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.*;
import cn.ewsd.repository.model.StockQryDetail;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface MTeamStockService extends MaterialBaseService<MTeamStock, String> {
	int getCountByMatNo(String matNo);

	PageSet<MTeamStock> getTeamStockQryPageSet(PageParam pageParam, String filterSort, String userDeptIds, String teamNoQry, String storeNoQry, String matCodeQry, String matNameQry);

	List<MTeamStock> getTeamStockQryList(String userDeptIds, String teamNoQry, String storeNoQry, String matCodeQry, String matNameQry);

	List<StockQryDetail> getTeamStockQryDetail(String matNo, String storeNo, String bDateQry, String eDateQry, String teamNoQry);

	List<MTeamStock> getTeamStockQryMatList(String matNo, String userDeptIds, String storeNo, String teamNo);
}
