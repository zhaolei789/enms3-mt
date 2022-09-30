package cn.ewsd.material.mapper;
import cn.ewsd.material.model.ConsumeStock;
import cn.ewsd.material.model.MItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ClassConsumeMapper {
    List<ConsumeStock> getPageSet(@Param("userTeam") String userTeam, @Param("occDate1Qry") String occDate1Qry, @Param("occDate2Qry") String occDate2Qry, @Param("deptNoQry") String deptNoQry);

    List<ConsumeStock> getPageSetXzcl(@Param("teamNo") String teamNo, @Param("storeNoQry") String storeNoQry, @Param("statusQry") String statusQry, @Param("deptNoQry") String deptNoQry,
                                      @Param("occDate2Qry") String occDate2Qry, @Param("prjNoQry") String prjNoQry, @Param("matCodeQry") String matCodeQry, @Param("matNameQry") String matNameQry);

    double getPrice(@Param("teamNo") String teamNo, @Param("matNo") String matNo);

    List<MItem> getItem(@Param("teamNo") String teamNo, @Param("month") String month);
}
