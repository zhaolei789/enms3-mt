package cn.ewsd.fix.service;

import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.fix.model.MBackPlan;
import cn.ewsd.material.model.MPrj;
import cn.ewsd.mdata.model.Organization;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-05-12 14:32:59
 */
public interface MBackPlanService extends FixBaseService<MBackPlan, String> {
	void insertBackPlan(HttpServletRequest request, UserInfo userInfo) throws Exception;

	void saveBackPlan(HttpServletRequest request, UserInfo userInfo) throws Exception;

	void deleteBackPlan(String planNo) throws Exception;

	void submitBackPlan(HttpServletRequest request) throws Exception;

	PageSet<MBackPlan> getPageSet(PageParam pageParam, String filterSort, String monthQry, String teamNoQry, String matQry, String ksTeamQry, boolean ifZnks, String userTeam, String userDeptIds);

	List<Organization> getBackPlanTeam();

	PageSet<MBackPlan> getTurnRegPageSet(PageParam pageParam, String filterSort, String monthQry, String userTeam, String addrQry, String teamQry, String matQry);

	MBackPlan getBackPlan(String planNo);

	void updateBackPlan(HttpServletRequest request, UserInfo userInfo) throws Exception;

	List<MBackPlan> getBackPlanList(String monthQry, String teamNoQry, String matQry, String ksTeamQry, boolean ifZnks, String userTeam, String userDeptIds);

	PageSet<MBackPlan> getFixTaskList(PageParam pageParam, String filterSort, String userTeam, String matQry, String monthQry);

	void submitFixTask(HttpServletRequest request, UserInfo userInfo, String mngTeam) throws Exception;

	int deleteFixTask(String planNo);

	PageSet<MBackPlan> getFixPlanPageSet(PageParam pageParam, String filterSort, String monthQry, String teamNoQry, String mngTeamQry, boolean ifZnks, String userTeam, String userDeptIds, String stepQry, String fixTypeQry, String matQry);

	List<MBackPlan> getFixPlanList(String monthQry, String teamNoQry, String mngTeamQry, boolean ifZnks, String userTeam, String userDeptIds, String stepQry, String fixTypeQry, String matQry);

	List<Organization> getBackPlanMngTeam(String monthQry);

	List<MBackPlan> getRepInStk(String monthQry, String userTeam);

	List<MPrj> getBackAssPrj(String planMonth, String teamNo);
}
