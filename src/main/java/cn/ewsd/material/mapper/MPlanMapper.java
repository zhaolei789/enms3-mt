package cn.ewsd.material.mapper;
import cn.ewsd.material.model.MMaterial;
import cn.ewsd.material.model.MPlan;
import cn.ewsd.system.model.DicItem;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface MPlanMapper extends tk.mybatis.mapper.common.Mapper<MPlan> {
    int getCountByMatNo(String matNo);

    int getCountByPrjNo(String prjNo);

    int checkPrjItemHasPlaned(@Param("prjNo") String prjNo, @Param("itemNos") String[] itemNos);

    List<MPlan> getPageSet(@Param("filterSort") String filterSort, @Param("prjNo") String prjNo, @Param("planMonth") String planMonth, @Param("teamNo") String teamNo,
                           @Param("itemNo") String itemNo, @Param("planStep") String planStep);

    double getSumBala(@Param("prjNo") String prjNo, @Param("planMonth") String planMonth, @Param("teamNo") String teamNo, @Param("itemNo") String itemNo, @Param("planStep") String planStep);

    double getGCSumBala(String prjNo);

    double getSCSumBala(@Param("payTeam") String payTeam, @Param("planMonth") String planMonth, @Param("itemNo") String itemNo);

    List<DicItem> getFactory();

    String getPlanSrc(@Param("prjNo") String prjNo, @Param("matNo") String matNo, @Param("clModel") String clModel);

    int savePlan(MPlan mPlan);

    int updatePlan(MPlan mPlan);

    int deletePlan(String planNo);

    MPlan getPlanByPlanNo(String planNo);

    int updPlan(@Param("reserveNo") String reserveNo, @Param("matAmount") BigDecimal matAmount, @Param("usableAmount") BigDecimal usableAmount);

    MPlan getPlan(@Param("matNo") String matNo, @Param("planMonth") String planMonth, @Param("planStep") String planStep);

    List<MPlan> getPlanList(MPlan mPlan);

    List<MPlan> getPlanByReserveNo(String reserveNo);

    int updatePlanByReserveNo(MPlan mPlan);

    int updPlanSc(String planStep);

    List<MPlan> getPlanStepData(@Param("userDept") String userDept, @Param("beginMonth") String beginMonth, @Param("endMonth") String endMonth, @Param("itemNoQry") String itemNoQry,
                                @Param("ifRole") boolean ifRole, @Param("userTeam") String userTeam);

    List<MPlan> getPlanStepTeamList(@Param("userDept") String userDept, @Param("beginMonth") String beginMonth, @Param("endMonth") String endMonth, @Param("itemNoQry") String itemNoQry,
                                    @Param("ifRole") boolean ifRole, @Param("userTeam") String userTeam);

    List<MPlan> getDetailPageSet(@Param("beginMonth") String beginMonth, @Param("endMonth") String endMonth, @Param("teamNo") String teamNo, @Param("planStep") String planStep,
                                 @Param("itemNo") String itemNo, @Param("planSrc") String planSrc, @Param("balaFlag") String balaFlag, @Param("matBala") String matBala,
                                 @Param("matQry") String matQry, @Param("ifRole") boolean ifRole, @Param("userTeam") String userTeam);

    List<MPlan> getQueryPlanList(@Param("userDept") String userDept, @Param("teamNo") String teamNo, @Param("prjStatus") String prjStatus, @Param("matBala") String matBala,
                                 @Param("balaFlag") String balaFlag, @Param("prjNo") String prjNo, @Param("itemNo") String itemNo, @Param("reserveNo") String reserveNo,
                                 @Param("mon1") String mon1, @Param("mon2") String mon2, @Param("year") String year, @Param("planStep") String planStep, @Param("matName") String matName,
                                 @Param("matCode") String matCode, @Param("reseFlag") String reseFlag);

    List<MPlan> getMatHis(@Param("userDept") String userDept, @Param("year") String year, @Param("matNo") String matNo);

    int updPlan1(MPlan mPlan);

    int updPlan2(String planNo);

    int updPlan3(@Param("usableAmount") BigDecimal usableAmount, @Param("planNo") String planNo);

    List<MPlan> getDrawApply(@Param("teamNo") String teamNo, @Param("applyType") String applyType, @Param("inDate") String inDate, @Param("storeNo") String storeNo,
                             @Param("matCodeQry") String matCodeQry, @Param("matNameQry") String matNameQry, @Param("currentDate") String currentDate);

    List<MPlan> getRikuQryList(@Param("monthQry") String monthQry, @Param("teamQry") String teamQry, @Param("stepQry") String stepQry, @Param("matQry") String matQry);

    List<HashMap<String, Object>> getMatAnalyMatList(@Param("nowYear") String nowYear, @Param("befOne") String befOne, @Param("befTwo") String befTwo, @Param("matQry") String matQry);

    List<MPlan> getMatAnalyPlanList(@Param("matNos") String matNos, @Param("nowYear") String nowYear, @Param("befOne") String befOne, @Param("befTwo") String befTwo);

    List<MPlan> getMatAnalyInList(@Param("matNos") String matNos, @Param("nowYear") String nowYear, @Param("befOne") String befOne, @Param("befTwo") String befTwo);

    List<MPlan> getMatAnalyOutList(@Param("matNos") String matNos, @Param("nowYear") String nowYear, @Param("befOne") String befOne, @Param("befTwo") String befTwo);
}
