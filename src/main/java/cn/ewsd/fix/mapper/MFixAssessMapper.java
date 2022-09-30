package cn.ewsd.fix.mapper;
import cn.ewsd.fix.model.MFixAssess;
import cn.ewsd.mdata.model.Organization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-05-13 15:11:59
 */
public interface MFixAssessMapper extends tk.mybatis.mapper.common.Mapper<MFixAssess> {
    int updateFixAssess(MFixAssess mFixAssess);

    int insertFixAssess(MFixAssess mFixAssess);

    MFixAssess getFixAssess(@Param("assType") String assType, @Param("assMonth") String assMonth, @Param("mngTeam") String mngTeam, @Param("teamNo") String teamNo,
                            @Param("prjNo") String prjNo);

    List<Organization> getFixAssessTeam(@Param("assMonth") String assMonth, @Param("userTeam") String userTeam);

    List<Organization> getMngTeam();

    MFixAssess getFixAssess1(@Param("assType") String assType, @Param("assMonth") String assMonth, @Param("mngTeam") String mngTeam, @Param("assStatus") String assStatus,
                             @Param("prjNo") String prjNo);
}
