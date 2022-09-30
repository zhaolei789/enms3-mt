package cn.ewsd.repository.service;

import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MTeamStock;
import cn.ewsd.repository.model.KeyAnaly;
import cn.ewsd.repository.model.MOut;
import cn.ewsd.repository.model.MTeamBill;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
public interface MOutService extends RepositoryBaseService<MOut, String> {
	int getCountByMatNo(String matNo);

	List<MOut> getApplySet(String planNos);

	MOut getZaiTuByPlanNo(String planNo);

	PageSet<MOut> getOutApplyPageSet(PageParam pageParam, String filterSort, String date1Qry, String date2Qry, String teamNo, String storeNoQry, String addrQry, String appTypeQry, String matCodeQry, String matNameQry, String planStepQry);

	void insertDrawApply(String prjNo, String item, String deptNo, String ifSend, String sendAddr, String sendTime, String getEmp, UserInfo userInfo, HttpServletRequest request) throws Exception;

	List<MOut> getDrawCheckIndex(String userId, String userTeam);

	PageSet<MOut> getDrawCheckList(PageParam pageParam, String filterSort, String teamNo, String drawStep, String applyDate, String date1Qry, String date2Qry, String drawNoQry, String userNo, String storeNoQry, String matQry, String typeQry, String userTeam);

	double getRoadAmount(String matNo, String storeNo);

	void submitOutCheck(UserInfo userInfo, HttpServletRequest request) throws Exception;

	void backOutCheck(UserInfo userInfo, HttpServletRequest request) throws Exception;

	List<HashMap<String, String>> getHzLog(String drawNo) throws Exception;

	List<MOut> getDrawList(String drawNo) throws Exception;

	List<MTeamBill> getConsumeList(String drawNo);

	List<MTeamStock> getKcList(String drawNo);

	List<HashMap<String, String>> getApplyStepList(String date1Qry, String date2Qry, String deptIds);

	PageSet<MOut> getApplyStepDetail(PageParam pageParam, String filterSort, String teamNo, String date1Qry, String date2Qry, String drawStep, String itemNo, String planTypeQry, String matQry, String userTeam, boolean ifRole);

	PageSet<MOut> getOutDrawPageSet(PageParam pageParam, String filterSort, String date1Qry, String date2Qry, String teamNoQry, String stepQry, String itemQry, String urgentQry, String drawNoQry, String storeNoQry, String erpTypeQry, String matCodeQry, String matNameQry, String deptIds, String userId, String newOldQry);

	List<MOut> getOutDrawList(String date1Qry, String date2Qry, String teamNoQry, String stepQry, String itemQry, String urgentQry, String drawNoQry, String storeNoQry, String erpTypeQry, String matCodeQry, String matNameQry, String deptIds, String userId, String newOldQry);

	void doCancel(String drawNo, UserInfo userInfo) throws Exception;

	List<KeyAnaly> getKeyAnalyList(String date1Qry, String date2Qry);

	void submitOldApply(HttpServletRequest request, UserInfo userInfo) throws Exception;

	PageSet<MOut> getOldApplyDetail(PageParam pageParam, String filterSort, String date1Qry, String date2Qry, String userTeam, String matQry);

	PageSet<MOut> getOldOutChkList(PageParam pageParam, String filterSort, String userId, String userTeam, String teamNoQry);

	void submitOldOutChk(HttpServletRequest request, UserInfo userInfo) throws Exception;

	void backOldOutChk(HttpServletRequest request, UserInfo userInfo) throws Exception;

	List<MOut> getDrawApplyPrint(String drawNos);

	List<MOut> getOutStatQryItem(String beginDateQry, String endDateQry, String matCodeQry, String matNameQry);

	List<HashMap<String, Object>> getOutStatQryList(String beginDateQry, String endDateQry, String matCodeQry, String matNameQry);

	List<MOut> getOutStatQryDetail(String teamNo, String date1Qry, String date2Qry, String matCodeQry, String matNameQry);

	PageSet<MOut> getOldOutQryPageSet(PageParam pageParam, String filterSort, String userDeptIds, String userTeam, String teamNoQry, String typeQry, String stepQry, String outTeamQry, String date1Qry, String date2Qry, String storeQry, String matQry);

	List<MOut> getOldOutQryList(String userDeptIds, String userTeam, String teamNoQry, String typeQry, String stepQry, String outTeamQry, String date1Qry, String date2Qry, String storeQry, String matQry);
}
