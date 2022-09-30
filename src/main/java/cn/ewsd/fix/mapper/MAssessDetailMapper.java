package cn.ewsd.fix.mapper;
import cn.ewsd.fix.model.MAssessDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-05-13 15:11:55
 */
public interface MAssessDetailMapper extends tk.mybatis.mapper.common.Mapper<MAssessDetail> {
    List<MAssessDetail> getBackAssessList(@Param("assMonth") String assMonth, @Param("userTeam") String userTeam, @Param("teamQry") String teamQry, @Param("prjQry") String prjQry, @Param("mngTeamQry") String mngTeamQry);

    int deleteAssessDetail(@Param("monthQry") String monthQry, @Param("userTeam") String userTeam);

    int insertAssessDetail(MAssessDetail mAssessDetail);

    int updateAssessDetail(MAssessDetail mAssessDetail);

    MAssessDetail getAssessDetailByDetailId(String detailiD);
}
