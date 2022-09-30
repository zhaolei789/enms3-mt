package cn.ewsd.material.mapper;
import cn.ewsd.material.model.MPlan;
import cn.ewsd.material.model.PlanCheckIndex;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface PlanCheckMapper extends tk.mybatis.mapper.common.Mapper<PlanCheckIndex> {
    List<PlanCheckIndex> getIndexData(@Param("filterSort") String filterSort, @Param("teamNo") String teamNo, @Param("userId") String userId, @Param("userItemCnt") int userItemCnt);

    List<MPlan> getPlanList(@Param("prjNo") String prjNo, @Param("planMonth") String planMonth, @Param("planType") String planType, @Param("planStep") String planStep,
                            @Param("teamNo") String teamNo, @Param("matCodeQry") String matCodeQry, @Param("matNameQry") String matNameQry, @Param("itemQry") String itemQry);

    Integer getUserItemCnt(@Param("userId") String userId);

    List<PlanCheckIndex> getPLanPendingRecords(@Param("teamNo") String teamNo, @Param("userId") String userId, @Param("userItemCnt") int userItemCnt);
}
