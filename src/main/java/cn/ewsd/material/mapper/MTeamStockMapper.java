package cn.ewsd.material.mapper;
import cn.ewsd.material.model.MTeamStock;
import com.sun.xml.internal.ws.encoding.MtomCodec;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface MTeamStockMapper extends tk.mybatis.mapper.common.Mapper<MTeamStock> {
    int getCountByMatNo(String matNo);

    int updateTeamStock(MTeamStock mTeamStock);

    int insertTeamStock(MTeamStock mTeamStock);

    MTeamStock getTeamStock(@Param("teamNo") String teamNo, @Param("storeNo") String storeNo, @Param("matNo") String matNo, @Param("matStatus") String matStatus);

    List<MTeamStock> getTeamNew(String matNo);

    MTeamStock getTeamOld(@Param("matNo") String matNo, @Param("teamNo") String teamNo);

    List<MTeamStock> getTeamStockList(String matNo);

    List<MTeamStock> getTeamStockQryList(@Param("userDeptIds") String userDeptIds, @Param("teamNoQry") String teamNoQry, @Param("storeNoQry") String storeNoQry, @Param("matCodeQry") String matCodeQry, @Param("matNameQry") String matNameQry);

    List<MTeamStock> getTeamStockQryMatList(@Param("matNo") String matNo, @Param("userDeptIds") String userDeptIds, @Param("storeNo") String storeNo, @Param("teamNo") String teamNo);
}
