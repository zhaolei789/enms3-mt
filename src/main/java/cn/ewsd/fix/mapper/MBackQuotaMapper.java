package cn.ewsd.fix.mapper;
import cn.ewsd.fix.model.MBackQuota;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-05-12 14:04:07
 */
public interface MBackQuotaMapper extends tk.mybatis.mapper.common.Mapper<MBackQuota> {
    List<MBackQuota> getBackQuotaList(@Param("filterSort") String filterSort, @Param("monthQry") String monthQry, @Param("prjQry") String prjQry, @Param("userTeam") String userTeam);

    List<MBackQuota> getBackPlanList(@Param("monthQry") String monthQry, @Param("prjQry") String prjQry, @Param("userTeam") String userTeam, @Param("teamNoQry") String teamNoQry);

    int insertBackQuota(MBackQuota mBackQuota);

    int updateBackQuota(MBackQuota mBackQuota);

    int deleteBackQuota(String quotaId);

    List<MBackQuota> getBackQuotaList1(@Param("prjNo") String prjNo, @Param("mngTeam") String mngTeam);
}
