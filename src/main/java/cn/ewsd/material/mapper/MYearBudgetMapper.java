package cn.ewsd.material.mapper;
import cn.ewsd.material.model.MBudget;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface MYearBudgetMapper extends tk.mybatis.mapper.common.Mapper<MBudget> {
    List<MBudget> getYearBudgetList(@Param("itemNo") String itemNo, @Param("useYear") String useYear, @Param("sysNo") String sysNo);

    int deleteData(@Param("teamNo") String teamNo, @Param("itemNo") String itemNo, @Param("useYear") String useYear);

    int updateData(@Param("teamNo") String teamNo, @Param("itemNo") String itemNo, @Param("useYear") String useYear, @Param("iniBala") Double iniBala,
                   @Param("addBala") Double addBala, @Param("overBala") Double overBala, @Param("budBala") Double budBala);

    int saveData(@Param("budId") String budId, @Param("teamNo") String teamNo, @Param("itemNo") String itemNo, @Param("useYear") String useYear, @Param("iniBala") Double iniBala,
                 @Param("addBala") Double addBala, @Param("overBala") Double overBala, @Param("budBala") Double budBala);

    List<MBudget> getMonthBudgetList(@Param("itemNo") String itemNo, @Param("useYear") String useYear, @Param("useMonth") String useMonth,
                                     @Param("sysNo") String sysNo, @Param("monthQry") String monthQry);

    double getSumBudBala(@Param("payTeam") String payTeam, @Param("planMonth") String planMonth, @Param("itemNo") String itemNo);

    double getUrgBudBala(@Param("occMonth") String occMonth, @Param("teamNo") String teamNo);

    double getUrgPlanBala(@Param("teamNo") String teamNo, @Param("planMonth") String planMonth);

    List<MBudget> getBudOccAnalItem(@Param("userDeptIds") String userDeptIds, @Param("monthQry") String monthQry, @Param("itemNoQry") String itemNoQry, @Param("teamNoQry") String teamNoQry);

    List<MBudget> getBudOccAnalTeam(@Param("userDeptIds") String userDeptIds, @Param("monthQry") String monthQry, @Param("itemNoQry") String itemNoQry, @Param("teamNoQry") String teamNoQry);

    Double getDeptYearBudget(@Param("year") String year, @Param("teamNo") String teamNo);

    Double getDeptYearOccBudget(@Param("year") String year, @Param("month") String month, @Param("teamNo") String teamNo);

    Double getDeptYearOutBudget(@Param("year") String year, @Param("month") String month, @Param("teamNo") String teamNo);

    Double getDeptMonthBudget(@Param("year") String year, @Param("month1") String month1, @Param("month2") String month2, @Param("teamNo") String teamNo);

    Double getDeptMonthOccBudget(@Param("month1") String month1, @Param("month2") String month2, @Param("teamNo") String teamNo);

    Double getDeptPlanSum(@Param("month1") String month1, @Param("month2") String month2, @Param("teamNo") String teamNo);

    Double getDeptAddPlan(@Param("month1") String month1, @Param("month2") String month2, @Param("teamNo") String teamNo);

    Double getDeptNoOk(@Param("month1") String month1, @Param("month2") String month2, @Param("teamNo") String teamNo);

    List<MBudget> getClassBudgetList(@Param("useYear") String useYear, @Param("useMonth") String useMonth, @Param("teamNo") String teamNo);

    List<MBudget> getClassForm(@Param("teamNo") String teamNo, @Param("occMonth") String occMonth, @Param("itemNo") String itemNo);

    List<MBudget> getClassCheck(@Param("teamNo") String teamNo, @Param("occMonth") String occMonth, @Param("itemNo") String itemNo);
}
