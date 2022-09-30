package cn.ewsd.repository.mapper;
import cn.ewsd.repository.model.MTeamBill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
public interface MTeamBillMapper extends tk.mybatis.mapper.common.Mapper<MTeamBill> {
    int saveTeamBill(MTeamBill mTeamBill);

    List<MTeamBill> getConsumeList(@Param("deptNos") String deptNos, @Param("newMatNo") String newMatNo, @Param("oldMatNo") String oldMatNo);

    MTeamBill getTeamBill(String billNo);

    int deleteTeamBill(String billNo);

    List<MTeamBill> getTeamStockOutDetail(@Param("matNo") String matNo, @Param("bDateQry") String bDateQry, @Param("eDateQry") String eDateQry, @Param("teamNoQry") String teamNoQry);

    List<MTeamBill> getClassMatQryList(@Param("date1Qry") String date1Qry, @Param("date2Qry") String date2Qry, @Param("teamNoQry") String teamNoQry, @Param("deptNoQry") String deptNoQry,
                                       @Param("typeQry") String typeQry, @Param("prjNameQry") String prjNameQry, @Param("matNameQry") String matNameQry);
}
