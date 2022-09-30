package cn.ewsd.repository.mapper;
import cn.ewsd.repository.model.MTeamMat;
import org.apache.ibatis.annotations.Param;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
public interface MTeamMatMapper extends tk.mybatis.mapper.common.Mapper<MTeamMat> {
    int insertTeamMat(MTeamMat mTeamMat);

    int updateTeamMat(@Param("teamNo") String teamNo, @Param("matNo") String matNo);
}
