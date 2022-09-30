package cn.ewsd.repository.mapper;
import cn.ewsd.material.model.WzscPlan;
import cn.ewsd.repository.model.MIn;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
public interface MInMapper extends tk.mybatis.mapper.common.Mapper<MIn> {
    int getCountByMatNo(Long matNo);

    int getCountByStoreNo(String storeNo);

    List<WzscPlan> getEpPlanPageSet(@Param("filterSort") String filterSort, @Param("beginDate") String beginDate, @Param("occDate") String occDate,
                                    @Param("monthQry") String monthQry, @Param("codeQry") String codeQry, @Param("typeQry") String typeQry, @Param("purcQry") String purcQry,
                                    @Param("nameQry") String nameQry, @Param("statusQry") String statusQry);

    int saveIn(MIn mIn);

    List<MIn> getDetail();

    List<MIn> getDirectInPageSet(@Param("userId") String userId, @Param("startDateQry") String startDateQry, @Param("endDateQry") String endDateQry,
                                 @Param("matQry") String matQry, @Param("storeNoQry") String storeNoQry);

    List<MIn> getCheckIndexPageSet(String userId);

    List<MIn> getInCheckPageSet(@Param("applyDate") String applyDate, @Param("inType") String inType, @Param("inStep") String inStep,
                                @Param("storeNo") String storeNo, @Param("matQry") String matQry);

    MIn getMInByBillNo(String billNo);

    int updateMIn(MIn mIn);

    List<MIn> getBcDrawQry(@Param("date1Qry") String date1Qry, @Param("date2Qry") String date2Qry, @Param("userId") String userId, @Param("ifAllQry") boolean ifAllQry,
                           @Param("matQry") String matQry, @Param("storeNoQry") String storeNoQry, @Param("inTypeQry") String inTypeQry, @Param("wbsQry") String wbsQry,
                           @Param("inStepQry") String inStepQry, @Param("accountQry") String accountQry, @Param("centerQry") String centerQry,
                           @Param("periodQry") String periodQry, @Param("periodDay") String periodDay, @Param("userDeptIds") String userDeptIds);

    List<MIn> getInList(@Param("storeNo") String storeNo, @Param("matNo") String matNo);

    int updateMIn1(@Param("outAmount") BigDecimal outAmount, @Param("billNo") String billNo);

    double getBillAmount(String planNo);

    List<MIn> getTurnRegList(@Param("date1Qry") String date1Qry, @Param("date2Qry") String date2Qry, @Param("matQry") String matQry, @Param("stepQry") String stepQry,
                             @Param("userTeam") String userTeam);

    int deleteMIn(String billNo);

    List<MIn> getTurnCheckList(@Param("date1Qry") String date1Qry, @Param("date2Qry") String date2Qry, @Param("userId") String userId, @Param("userTeam") String userTeam,
                               @Param("matQry") String matQry);

    List<MIn> getTurnConfList(@Param("date1Qry") String date1Qry, @Param("date2Qry") String date2Qry, @Param("matQry") String matQry, @Param("userTeam") String userTeam);

    int updMIn(@Param("inStep") String inStep, @Param("billDate") String billDate, @Param("billEmp") String billEmp, @Param("checkAddr") String checkAddr, @Param("billNo") String billNo);

    List<MIn> getReturnMatList(@Param("userTeam") String userTeam, @Param("date1Qry") String date1Qry, @Param("date2Qry") String date2Qry, @Param("matQry") String matQry);

    List<MIn> getReturnChkList(@Param("userNo") String userNo, @Param("userTeam") String userTeam, @Param("matQry") String matQry, @Param("storeQry") String storeQry);

    List<MIn> getReturnQryList(@Param("date1Qry") String date1Qry, @Param("date2Qry") String date2Qry, @Param("userDeptIds") String userDeptIds,
                               @Param("typeQry") String typeQry, @Param("matQry") String matQry);

    List<MIn> getInStkChkList(@Param("date1Qry") String date1Qry, @Param("date2Qry") String date2Qry, @Param("userNo") String userNo, @Param("userTeam") String userTeam);

    List<MIn> getStockInDetail(@Param("matNo") String matNo, @Param("date1Qry") String date1Qry, @Param("date2Qry") String date2Qry,
                               @Param("storeNoQry") String storeNoQry, @Param("ifPower") boolean ifPower);

    List<MIn> getTeamStockInDetail(@Param("matNo") String matNo, @Param("storeNo") String storeNo, @Param("bDateQry") String bDateQry, @Param("eDateQry") String eDateQry, @Param("teamNoQry") String teamNoQry);

    List<MIn> getInstockQryList(@Param("userDeptIds") String userDeptIds, @Param("teamNoQry") String teamNoQry, @Param("statusQry") String statusQry, @Param("date1Qry") String date1Qry, @Param("date2Qry") String date2Qry, @Param("matQry") String matQry);
}
