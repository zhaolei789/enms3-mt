package cn.ewsd.material.service;

import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MPlan;
import cn.ewsd.system.model.AuditMessage;
import cn.ewsd.system.model.DicItem;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface MPlanService extends MaterialBaseService<MPlan, String> {
	int getCountByMatNo(String matNo);

	int getCountByPrjNo(String prjNo);

	int checkPrjItemHasPlaned(String prjNo, String[] itemNos);

	PageSet<MPlan> getPageSet(PageParam pageParam, String filterSort, String prjNo, String planMonth, String teamNo, String itemNo, String planStep);

	double getSumBala(String prjNo, String planMonth, String teamNo, String itemNo, String planStep);

	double getBudgetBala(String prjNo, String payTeam, String planMonth, String itemNo, String prjType);

	double getPlanBala(String prjNo, String payTeam, String planMonth, String itemNo, String prjType);

	void addPlan(String prjNo, String[] matNos, String clModel, String planType, String planMonth, String itemNo, String teamNo, int period, String factoryNo, String costCenter, String wbsElement, String itemType, String needComp, String needFactory, String purOrg, String purGroup, String creatorId, String creator, String[] planAmounts, String[] matAddrs) throws Exception;

	List<DicItem> getFactory();

	int update(ArrayList<MPlan> list);

	int deleteBatch(String[] planNos);

	MPlan getPlanByPlanNo(String planNo);

	int submitPlan(HttpServletRequest request, HashMap<String, String> paramMap, String[] planNos, String planStep, String userId, String userName) throws Exception;

	String getNextStep(String prevStepCode, String itemNo, double matPrice, double matAmount, String teamNo, String planSrc, String reserveNo);

	int updatePlan(MPlan mPlan);

	List<MPlan> getPlanStepData(String userDept, String beginMonth, String endMonth, String itemNoQry, boolean ifRole, String userTeam);

	List<MPlan> getPlanStepTeamList(String userDept, String beginMonth, String endMonth, String itemNoQry, boolean ifRole, String userTeam);

	PageSet<MPlan> getDetailPageSet(PageParam pageParam, String filterSort, String beginMonth, String endMonth, String teamNo, String planStep, String itemNo, String planSrc, String balaFlag, String matBala, String matQry, boolean ifRole, String userTeam);

	PageSet<MPlan> getQueryPlanList(PageParam pageParam, String filterSort, String userDept, String teamNo, String prjStatus, String matBala, String balaFlag, String prjNo, String itemNo, String reserveNo, String mon1, String mon2, String year, String planStep, String matName, String matCode, String reseFlag);

	List<MPlan> getQueryPlanDownLoad(String userDept, String teamNo, String prjStatus, String matBala, String balaFlag, String prjNo, String itemNo, String reserveNo, String mon1, String mon2, String year, String planStep, String matName, String matCode, String reseFlag);

	PageSet<MPlan> getMatHis(PageParam pageParam, String filterSort, String userDept, String year, String matNo);

	void adjustPlan(String isDelete, String planNo, String purchaseNo, String reserveNo, String planStep, String remark, double minusAmount, String checkNo, UserInfo userInfo) throws Exception;

	PageSet<MPlan> getDrawApply(PageParam pageParam, String filterSort, String teamNo, String applyType, String inDate, String storeNo, String matCodeQry, String matNameQry);

	void submitUrgentPlan(HttpServletRequest request, UserInfo userInfo) throws Exception;

	double getUrgBudBala(String teamNo, String planMonth);

	double getUrgPlanBala(String teamNo, String planMonth);

	PageSet<MPlan> getRikuQry(PageParam pageParam, String filterSort, String monthQry, String teamQry, String stepQry, String matQry);

	List<MPlan> getRikuQryList(String monthQry, String teamQry, String stepQry, String matQry);

	PageSet<HashMap<String, Object>> getMatAnalyPageSet(PageParam pageParam, String filterSort, String matQry);

	void importPlan(List<Map<String, String>> varList, UserInfo userInfo, String teamNo, String itemNo, String planMonth, String planType, String prjNo) throws Exception;

	List<AuditMessage> getPendingRecords(UserInfo userInfo) throws Exception;
}
