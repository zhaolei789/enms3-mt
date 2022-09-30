package cn.ewsd.repository.mapper;
import cn.ewsd.cost.model.MRation;
import cn.ewsd.material.model.MMaterial;
import cn.ewsd.repository.model.MOut;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
public interface MOutMapper extends tk.mybatis.mapper.common.Mapper<MOut> {
    int updateOfferTeam(MOut mOut);

    int getCountByMatNo(String matNo);

    double getPurchaseCircleConsumption(@Param("matNo") String matNo, @Param("storeNo") String storeNo, @Param("beginDate") String beginDate, @Param("endDate") String endDate);

    List<MOut> getApplySet(String planNos);

    MOut getZaiTuByPlanNo(String planNo);

    MOut getYiLingByPlanNo(String planNo);

    int deleteByPlanNo(String planNo);

    List<MOut> getOutApplyList(@Param("date1Qry") String date1Qry, @Param("date2Qry") String date2Qry, @Param("teamNo") String teamNo, @Param("storeNoQry") String storeNoQry,
                               @Param("addrQry") String addrQry, @Param("appTypeQry") String appTypeQry, @Param("matCodeQry") String matCodeQry, @Param("matNameQry") String matNameQry,
                               @Param("planStepQry") String planStepQry);

    double getChkAmountByPlanNo(String planNo);

    int insertOut(MOut mOut);

    List<MOut> getDrawCheckIndex(@Param("userId") String userId, @Param("userTeam") String userTeam);

    List<MOut> getDrawCheckList(@Param("teamNo") String teamNo, @Param("drawStep") String drawStep, @Param("applyDate") String applyDate, @Param("date1Qry") String date1Qry,
                                @Param("date2Qry") String date2Qry, @Param("drawNoQry") String drawNoQry, @Param("userNo") String userNo, @Param("storeNoQry") String storeNoQry,
                                @Param("matQry") String matQry, @Param("typeQry") String typeQry, @Param("userTeam") String userTeam);

    double getRoadAmount(@Param("matNo") String matNo, @Param("storeNo") String storeNo);

    MOut getOutByDrawNo(String drawNo);

    int updateOut(MOut mOut);

    double getOccurBala(@Param("payTeam") String payTeam, @Param("prjNo") String prjNo, @Param("itemNo") String itemNo, @Param("fMonth") String fMonth);

    List<MOut> getDrawList(@Param("teamNo") String teamNo, @Param("newMatNo") String newMatNo, @Param("oldMatNo") String oldMatNo);

    List<MOut> getApplyStepList(@Param("date1Qry") String date1Qry, @Param("date2Qry") String date2Qry, @Param("deptIds") String deptIds);

    List<MOut> getApplyStepTeam(@Param("date1Qry") String date1Qry, @Param("date2Qry") String date2Qry, @Param("deptIds") String deptIds);

    List<MOut> getApplyStepDetail(@Param("teamNo") String teamNo, @Param("date1Qry") String date1Qry, @Param("date2Qry") String date2Qry, @Param("drawStep") String drawStep,
                                  @Param("itemNo") String itemNo, @Param("planTypeQry") String planTypeQry, @Param("matQry") String matQry,
                                  @Param("userTeam") String userTeam, @Param("ifRole") boolean ifRole);

    List<MOut> getOutDrawPageSet(@Param("date1Qry") String date1Qry, @Param("date2Qry") String date2Qry, @Param("teamNoQry") String teamNoQry, @Param("stepQry") String stepQry,
                                 @Param("itemQry") String itemQry, @Param("urgentQry") String urgentQry, @Param("drawNoQry") String drawNoQry, @Param("storeNoQry") String storeNoQry,
                                 @Param("erpTypeQry") String erpTypeQry, @Param("matCodeQry") String matCodeQry, @Param("matNameQry") String matNameQry,
                                 @Param("deptIds") String deptIds, @Param("userId") String userId, @Param("newOldQry") String newOldQry);

    List<MMaterial> getKeyAnalyBalaTop20(@Param("date1Qry") String date1Qry, @Param("date2Qry") String date2Qry);

    List<MMaterial> getKeyAnalyPriceTop20(@Param("date1Qry") String date1Qry, @Param("date2Qry") String date2Qry);

    List<MMaterial> getKeyAnalyAmountTop20(@Param("date1Qry") String date1Qry, @Param("date2Qry") String date2Qry);

    List<MOut> getStockOutDetail(@Param("matNo") String matNo, @Param("date1Qry") String date1Qry, @Param("date2Qry") String date2Qry,
                                 @Param("storeNoQry") String storeNoQry, @Param("ifPower") boolean ifPower);

    List<MOut> getOldApplyDetail(@Param("date1Qry") String date1Qry, @Param("date2Qry") String date2Qry, @Param("userTeam") String userTeam, @Param("matQry") String matQry);

    List<MOut> getOldOutChkList(@Param("userId") String userId, @Param("userTeam") String userTeam, @Param("teamNoQry") String teamNoQry);

    List<MOut> getDrawApplyPrint(@Param("drawNos") String drawNos);

    List<MOut> getOldMatEditOut();

    List<MOut> getOutStatQryList(@Param("beginDateQry") String beginDateQry, @Param("endDateQry") String endDateQry, @Param("matCodeQry") String matCodeQry, @Param("matNameQry") String matNameQry);

    List<MOut> getOutStatQryTeam(@Param("beginDateQry") String beginDateQry, @Param("endDateQry") String endDateQry, @Param("matCodeQry") String matCodeQry, @Param("matNameQry") String matNameQry);

    List<MOut> getOutStatQryItem(@Param("beginDateQry") String beginDateQry, @Param("endDateQry") String endDateQry, @Param("matCodeQry") String matCodeQry, @Param("matNameQry") String matNameQry);

    List<MOut> getOutStatQryDetail(@Param("teamNo") String teamNo, @Param("date1Qry") String date1Qry, @Param("date2Qry") String date2Qry, @Param("matCodeQry") String matCodeQry, @Param("matNameQry") String matNameQry);

    List<MOut> getOldOutQryList(@Param("userDeptIds") String userDeptIds, @Param("userTeam") String userTeam, @Param("teamNoQry") String teamNoQry, @Param("typeQry") String typeQry, @Param("stepQry") String stepQry,
                                @Param("outTeamQry") String outTeamQry, @Param("date1Qry") String date1Qry, @Param("date2Qry") String date2Qry, @Param("storeQry") String storeQry, @Param("matQry") String matQry);

    List<MRation> getRationAnalOutList();

    List<MOut> getDrawPendingRecords(@Param("userId") String userId, @Param("userTeam") String userTeam);

    int getPeriodWarnRecords(String date);

    int getStockWarnRecords();
}
