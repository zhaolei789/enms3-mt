package cn.ewsd.material.mapper;
import cn.ewsd.material.model.WzscPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface WzscPlanMapper extends tk.mybatis.mapper.common.Mapper<WzscPlan> {
    List<WzscPlan> getPageSet(@Param("filterSort") String filterSort, @Param("monthQry") String monthQry, @Param("userDeptIds") String userDeptIds, @Param("codeQry") String codeQry,
                              @Param("typeQry") String typeQry, @Param("centerQry") String centerQry, @Param("purcQry") String purcQry, @Param("matTypeQry") String matTypeQry,
                              @Param("nameQry") String nameQry, @Param("statusQry") String statusQry);

    int deleteByEpId(String epId);

    int excuteSave(WzscPlan wzscPlan);

    int updateEpPlan(WzscPlan wzscPlan);

    int deleteByUuid(String uuid);

    WzscPlan getByUuid(String uuid);

    WzscPlan getEpPlan(String epId);

    List<WzscPlan> getArriveQryPageSet(@Param("filterSort") String filterSort, @Param("reserveQry") String reserveQry, @Param("monthQry") String monthQry,
                                       @Param("userDeptIds") String userDeptIds, @Param("centerQry") String centerQry, @Param("matCodeQry") String matCodeQry,
                                       @Param("over3Qry") String over3Qry, @Param("matNameQry") String matNameQry, @Param("over1Qry") String over1Qry, @Param("nowDate") String nowDate);
}
