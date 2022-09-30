package cn.ewsd.material.mapper;
import cn.ewsd.material.model.SendMatPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface SendMatPlanMapper extends tk.mybatis.mapper.common.Mapper<SendMatPlan> {
    List<SendMatPlan> getPlanMonth();

    List<SendMatPlan> getPageSet(@Param("filterSort") String filterSort, @Param("planMonth") String planMonth, @Param("planType") String planType, @Param("prjNo") String prjNo,
                                 @Param("matCode") String matCode, @Param("matName") String matName, @Param("teamNo") String teamNo, @Param("itemNo") String itemNo,
                                 @Param("centerNo") String centerNo);
}
