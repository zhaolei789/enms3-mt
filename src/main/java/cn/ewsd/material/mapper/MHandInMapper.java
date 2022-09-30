package cn.ewsd.material.mapper;
import cn.ewsd.material.model.MHandIn;
import cn.ewsd.material.model.MMaterial;
import cn.ewsd.mdata.model.Organization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-21 16:55:52
 */
public interface MHandInMapper extends tk.mybatis.mapper.common.Mapper<MHandIn> {
    int insertHandIn(MHandIn mHandIn);

    List<MHandIn> getOldMatRegList(@Param("date1Qry") String date1Qry, @Param("date2Qry") String date2Qry, @Param("teamNoQry") String teamNoQry, @Param("matCodeQry") String matCodeQry,
                                   @Param("matNameQry") String matNameQry);

    List<Organization> getTeamSet(@Param("date1Qry") String date1Qry, @Param("date2Qry") String date2Qry, @Param("teamNoQry") String teamNoQry, @Param("matCodeQry") String matCodeQry,
                                  @Param("matNameQry") String matNameQry);

    List<MMaterial> getMatList(@Param("q") String q, @Param("userTeam") String userTeam);

    MHandIn getMHandInByBillNo(String billNo);

    int delMHandInByBillNo(String billNo);

    int delMHandInByLinkNo(String linkNo);

    List<MHandIn> getOldMatRegDetailList(@Param("monthQry") String monthQry, @Param("userDeptIds") String userDeptIds, @Param("teamNoQry") String teamNoQry,
                                         @Param("matCodeQry") String matCodeQry, @Param("matNameQry") String matNameQry);

    List<MHandIn> getRetDetailList1(@Param("userDeptIds") String userDeptIds, @Param("date1Qry") String date1Qry, @Param("date2Qry") String date2Qry,
                                    @Param("teamNoQry") String teamNoQry, @Param("typeQry") String typeQry, @Param("matQry") String matQry);

    List<MHandIn> getRetDetailList2(@Param("userDeptIds") String userDeptIds, @Param("date1Qry") String date1Qry, @Param("date2Qry") String date2Qry,
                                    @Param("teamNoQry") String teamNoQry, @Param("typeQry") String typeQry, @Param("matQry") String matQry);
}
