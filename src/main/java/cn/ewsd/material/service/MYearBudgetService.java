package cn.ewsd.material.service;

import cn.ewsd.material.model.MBudget;

import java.util.HashMap;
import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface MYearBudgetService extends MaterialBaseService<MBudget, String> {
    List<MBudget> getYearBudgetList(String itemNo, String useYear, String sysNo);

    int saveData(String itemNo, String useYear, HashMap<String, Double[]> map);

    List<MBudget> getBudOccAnalItem(String userDeptIds, String monthQry, String itemNoQry, String teamNoQry);

    List<HashMap<String, Object>> getBudOccAnalList(String userDeptIds, String monthQry, String itemNoQry, String teamNoQry);

    Double getDeptYearBudget(String year, String teamNo);

    Double getDeptYearOccBudget(String year, String month, String teamNo);

    Double getDeptYearOutBudget(String year, String month, String teamNo);

    Double getDeptMonthBudget(String year, String month1, String month2, String teamNo);

    Double getDeptMonthOccBudget(String month1, String month2, String teamNo);

    Double getDeptPlanSum(String month1, String month2, String teamNo);

    Double getDeptAddPlan(String month1, String month2, String teamNo);

    Double getDeptNoOk(String month1, String month2, String teamNo);
}
