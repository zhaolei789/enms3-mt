package cn.ewsd.repository.service;

import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.WzscPlan;
import cn.ewsd.repository.model.MIn;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
public interface MInService extends RepositoryBaseService<MIn, String> {
	int getCountByMatNo(Long matNo);

	int getCountByStoreNo(String storeNo);

	PageSet<WzscPlan> getEpPlanPageSet(PageParam pageParam, String filterSort, String beginDate, String occDate, String monthQry, String codeQry, String typeQry, String purcQry, String nameQry, String statusQry);

	void submitApply(String storeQry, String ifStockQry, double amount, String offer, String remark, String epId, UserInfo userInfo) throws Exception;

	PageSet<MIn> getDetailPageSet(PageParam pageParam, String filterSort);

	PageSet<MIn> getDirectInPageSet(PageParam pageParam, String filterSort, String userId, String startDateQry, String endDateQry, String matQry, String storeNoQry);

	//int saveDirectIn(UserInfo userInfo, String offer, String matNo, String storeNo, double amount, String remark, String ifAccount);
	int saveDirectIn(UserInfo userInfo, String site, String matNo, String storeNo, double amount, String remark, String ifAccount);

	int saveDirectIn2(UserInfo userInfo, String site, String matNo, String storeNo, double amount, String remark, String ifAccount, BigDecimal matPrice);

	PageSet<MIn> getCheckIndexPageSet(PageParam pageParam, String filterSort, String userId);

	PageSet<MIn> getInCheckPageSet(PageParam pageParam, String filterSort, String applyDate, String inType, String inStep, String storeNo, String matQry);

	MIn getMInByBillNo(String billNo);

	void submitMIn(HttpServletRequest request, UserInfo userInfo) throws Exception;

	PageSet<MIn> getBcDrawQryPageSet(PageParam pageParam, String filterSort, String date1Qry, String date2Qry, String userId, boolean ifAllQry, String matQry, String storeNoQry, String inTypeQry, String wbsQry, String inStepQry, String accountQry, String centerQry, String periodQry, String periodDay, String userDeptIds);

	List<MIn> getBcDrawQryList(String date1Qry, String date2Qry, String userId, boolean ifAllQry, String matQry, String storeNoQry, String inTypeQry, String wbsQry, String inStepQry, String accountQry, String centerQry, String periodQry, String periodDay, String userDeptIds);

	int backDrawIn(String billNo, UserInfo userInfo) throws Exception;

	double getBillAmount(String planNo);

	void insertTurnReg(HttpServletRequest request, UserInfo userInfo) throws Exception;

	void deletePlanIn(String billNo) throws Exception;

	PageSet<MIn> getTurnRegPageSet(PageParam pageParam, String filterSort, String date1Qry, String date2Qry, String matQry, String stepQry, String userTeam);

	void deleteTurnReg(String billNo) throws Exception;

	PageSet<MIn> getTurnChkPageSet(PageParam pageParam, String filterSort, String date1Qry, String date2Qry, String userId, String userTeam, String matQry);

	void submitTurnChk(HttpServletRequest request, UserInfo userInfo) throws Exception;

	void backTurnChk(HttpServletRequest request, UserInfo userInfo) throws Exception;

	PageSet<MIn> getTurnConfPageSet(PageParam pageParam, String filterSort, String date1Qry, String date2Qry, String matQry, String userTeam);

	void submitTurnConf(HttpServletRequest request, UserInfo userInfo) throws Exception;

	void backTurnConf(HttpServletRequest request, UserInfo userInfo) throws Exception;

	PageSet<MIn> getReturnMatList(PageParam pageParam, String filterSort, String userTeam, String date1Qry, String date2Qry, String matQry);

	void insertReturnMat(HttpServletRequest request, UserInfo userInfo) throws Exception;

	void deleteReturnMat(HttpServletRequest request, UserInfo userInfo) throws Exception;

	PageSet<MIn> getReturnChkList(PageParam pageParam, String filterSort, String userNo, String userTeam, String matQry, String storeQry);

	void submitReturnChk(HttpServletRequest request, UserInfo userInfo) throws Exception;

	void backReturnChk(HttpServletRequest request, UserInfo userInfo) throws Exception;

	void backReturn(HttpServletRequest request, UserInfo userInfo) throws Exception;

	PageSet<MIn> getReturnQryList(PageParam pageParam, String filterSort, String date1Qry, String date2Qry, String userDeptIds, String typeQry, String matQry);

	List<MIn> getRetQryList(String date1Qry, String date2Qry, String userDeptIds, String typeQry, String matQry);

	void submitRepInStk(HttpServletRequest request, UserInfo userInfo) throws Exception;

	PageSet<MIn> getInStkChkPageSet(PageParam pageParam, String filterSort, String date1Qry, String date2Qry, String userId);

	void submitInStkChk(HttpServletRequest request, UserInfo userInfo) throws Exception;

	void backInStkChk(HttpServletRequest request, UserInfo userInfo) throws Exception;

	void backMIn(HttpServletRequest request, UserInfo userInfo) throws Exception;

	PageSet<MIn> getInstockQryPageSet(PageParam pageParam, String filterSort, String userDeptIds, String teamNoQry, String statusQry, String date1Qry, String date2Qry, String matQry);

	List<MIn> getInstockQryList(String userDeptIds, String teamNoQry, String statusQry, String date1Qry, String date2Qry, String matQry);
}
