package cn.ewsd.cost.mapper;
import cn.ewsd.common.mapper.BaseMapper;
import cn.ewsd.cost.model.FChargeFee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 分管费用
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-07 09:22:24
 */
public interface FChargeFeeMapper extends tk.mybatis.mapper.common.Mapper<FChargeFee> {

    //获取分页集
    List<FChargeFee> getPageSet(@Param("filterSort") String filterSort);

    List<FChargeFee> getPageSetSbCw(@Param("filterSort") String filterSort,@Param("queryMonth") String queryMonth);

    List<FChargeFee> getPageSetSbFs(@Param("filterSort") String filterSort,@Param("queryMonth") String queryMonth);

    List<FChargeFee> getPageSetSettle(@Param("filterSort") String filterSort,@Param("fMonth") String fMonth);

    List<FChargeFee> getSettleSum(String year);

    int executeSave(FChargeFee var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<FChargeFee> var1);

    int executeUpdate(FChargeFee var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    FChargeFee queryObject(Object var1);

    List<FChargeFee> queryList(Map<String, Object> var1);

    List<FChargeFee> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

    List<FChargeFee> getLeaderSumYearBudList(@Param("yearQry") String yearQry, @Param("userId") String userId);

    List<FChargeFee> getLeaderSumMonthBudList(@Param("month1") String month1, @Param("month2") String month2);

    List<FChargeFee> getLeaderSumLJFS(@Param("month1") String month1, @Param("month2") String month2, @Param("yearQry") String yearQry);

    List<FChargeFee> getLeaderSumDetailYearList(@Param("yearQry") String yearQry, @Param("leader") String leader);

    List<FChargeFee> getLeaderSumDetailMonthList(@Param("month1") String month1, @Param("month2") String month2, @Param("leader") String leader);

    List<FChargeFee> getLeaderSumDeptYearList(@Param("yearQry") String yearQry, @Param("leader") String leader, @Param("teamNo") String teamNo);

    List<FChargeFee> getLeaderSumDeptMonthList(@Param("month1") String month1, @Param("month2") String month2, @Param("leader") String leader, @Param("teamNo") String teamNo);

    List<FChargeFee> getDeptSumYearList(@Param("yearQry") String yearQry, @Param("userDeptIds") String userDeptIds);

    List<FChargeFee> getDeptSumMonthList(@Param("yearQry") String yearQry, @Param("month1") String month1, @Param("month2") String month2, @Param("userDeptIds") String userDeptIds);

    List<FChargeFee> getDeptSumLJFSList(@Param("yearQry") String yearQry, @Param("month2") String month2, @Param("userDeptIds") String userDeptIds);

    List<FChargeFee> getDeptSumDetailYearList(@Param("yearQry") String yearQry, @Param("teamNo") String teamNo);

    List<FChargeFee> getDeptSumDetailMonthList(@Param("month1") String month1, @Param("month2") String month2, @Param("teamNo") String teamNo);

    List<FChargeFee> getCostAnalysisYearList(@Param("yearQry") String yearQry, @Param("userDeptIds") String userDeptIds);

    List<FChargeFee> getCostAnalysisMonthList(@Param("yearQry") String yearQry, @Param("month1") String month1, @Param("month2") String month2, @Param("userDeptIds") String userDeptIds);

    List<FChargeFee> getCostAnalysisLJFSList(@Param("yearQry") String yearQry, @Param("month2") String month2, @Param("userDeptIds") String userDeptIds);

    List<FChargeFee> getCostAnalysisDetailYearList(@Param("yearQry") String yearQry, @Param("teamNo") String teamNo);

    List<FChargeFee> getCostAnalysisDetailMonthList(@Param("month1") String month1, @Param("month2") String month2, @Param("teamNo") String teamNo);
}
