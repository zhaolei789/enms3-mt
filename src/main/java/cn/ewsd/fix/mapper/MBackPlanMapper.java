package cn.ewsd.fix.mapper;
import cn.ewsd.fix.model.MBackPlan;
import cn.ewsd.material.model.MPrj;
import cn.ewsd.mdata.model.Organization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-05-12 14:32:59
 */
public interface MBackPlanMapper extends tk.mybatis.mapper.common.Mapper<MBackPlan> {
    int deleteBackPlan(@Param("planMonth") String planMonth, @Param("teamNo") String teamNo, @Param("planTeam") String planTeam, @Param("prjNo") String prjNo);

    int insertBackPlan(MBackPlan mBackPlan);

    int updateBackPlan(@Param("planAmount") String planAmount, @Param("remark") String remark, @Param("planMonth") String planMonth, @Param("teamNo") String teamNo,
                       @Param("planTeam") String planTeam, @Param("matNo") String matNo, @Param("fixType") String fixType, @Param("prjNo") String prjNo);

    int delBackPlan(String planNo);

    int updBackPlan(@Param("planStep") String planStep, @Param("planDate") String planDate, @Param("planAmount") String planAmount, @Param("remark") String remark,
                    @Param("checkNo") String checkNo, @Param("planNo") String planNo);

    MBackPlan getBackPlan(String planNo);

    List<MBackPlan> getPageSet(@Param("monthQry") String monthQry, @Param("teamNoQry") String teamNoQry, @Param("matQry") String matQry, @Param("ksTeamQry") String ksTeamQry,
                               @Param("ifZnks") boolean ifZnks, @Param("userTeam") String userTeam, @Param("userDeptIds") String userDeptIds);

    List<Organization> getBackPlanTeam();

    List<MBackPlan> getTurnRegList(@Param("monthQry") String monthQry, @Param("userTeam") String userTeam, @Param("addrQry") String addrQry, @Param("teamQry") String teamQry,
                                   @Param("matQry") String matQry);

    int delBackPlan1(String planNo);

    List<MBackPlan> getFixTaskList(@Param("userTeam") String userTeam, @Param("matQry") String matQry, @Param("monthQry") String monthQry);

    List<MBackPlan> getFixPlanList(@Param("monthQry") String monthQry, @Param("teamNoQry") String teamNoQry, @Param("mngTeamQry") String mngTeamQry, @Param("ifZnks") boolean ifZnks,
                                   @Param("userTeam") String userTeam, @Param("userDeptIds") String userDeptIds, @Param("stepQry") String stepQry, @Param("fixTypeQry") String fixTypeQry,
                                   @Param("matQry") String matQry);

    List<Organization> getBackPlanMngTeam(String monthQry);

    List<MBackPlan> getRepInStk(@Param("monthQry") String monthQry, @Param("userTeam") String userTeam);

    List<MPrj> getBackAssPrj(@Param("planMonth") String planMonth, @Param("teamNo") String teamNo);

    List<MBackPlan> getBackAssPlan(@Param("monthQry") String monthQry, @Param("userTeam") String userTeam);
}
