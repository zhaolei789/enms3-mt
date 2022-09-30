package cn.ewsd.cost.mapper;
import cn.ewsd.cost.model.MAssessNorm;
import cn.ewsd.cost.model.MFeeItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工程结算
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-03 08:39:36
 */
public interface MAssessNormMapper extends tk.mybatis.mapper.common.Mapper<MAssessNorm> {
    List<MAssessNorm> getTeamItemQuotaList(@Param("assTeam") String assTeam, @Param("teamNoQry") String teamNoQry, @Param("prjQry") String prjQry);

    int updateAssessNorm(MAssessNorm mAssessNorm);

    int insertAssessNorm(MAssessNorm assessNorm);

    int deleteAssessNorm(String normId);

    List<MAssessNorm> getMatAssDcNormList(String teamNos);
}
