package cn.ewsd.repository.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.repository.model.MTeamBill;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
public interface MTeamBillService extends RepositoryBaseService<MTeamBill, String> {

	PageSet<MTeamBill> getClassMatQryPageSet(PageParam pageParam, String filterSort, String date1Qry, String date2Qry, String teamNoQry, String deptNoQry, String typeQry, String prjNameQry, String matNameQry);

	List<MTeamBill> getClassMatQryList(String date1Qry, String date2Qry, String teamNoQry, String deptNoQry, String typeQry, String prjNameQry, String matNameQry);

}
