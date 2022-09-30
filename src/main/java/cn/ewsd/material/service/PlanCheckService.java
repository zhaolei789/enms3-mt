package cn.ewsd.material.service;

import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MPlan;
import cn.ewsd.material.model.PlanCheckIndex;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface PlanCheckService extends MaterialBaseService<PlanCheckIndex, String> {
    PageSet<PlanCheckIndex> getIndexData(PageParam pageParam, String filterSort, String teamNo, String userId, int userItemCnt);

    PageSet<MPlan> getPlanList(PageParam pageParam, String filterSort, String prjNo, String planMonth, String planType, String planStep, String teamNo, String matCodeQry, String matNameQry, String itemQry);

    void backPlan(HttpServletRequest request, UserInfo userInfo) throws Exception;

    Integer getUserItemCnt(String userId);
}
