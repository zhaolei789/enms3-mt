package cn.ewsd.material.mapper;
import cn.ewsd.material.model.MPrj;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface MPrjMapper extends tk.mybatis.mapper.common.Mapper<MPrj> {
    List<MPrj> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(MPrj offer);

    MPrj queryObject(String uuid);

    int executeUpdate(MPrj mPrj);

    int executeDelete(String uuid);

    List<MPrj> getUpNameAll();

    List<MPrj> getPrjByStatusAndTeamNoAndItemNo(@Param("prjStatus") String prjStatus, @Param("teamNo") String teamNo, @Param("itemNo") String itemNo);

    MPrj getPrjByPrjNo(@Param("prjNo") String prjNo);

    List<MPrj> getPrjList(@Param("teamNo") String teamNo, @Param("prjStatus") String prjStatus);

    List<MPrj> getPrjSet(@Param("teamNos") String teamNos, @Param("prjStatus") String prjStatus);

    List<MPrj> getPrjFromBackQuota();

    List<MPrj> getPrjList1(@Param("teamNo") String teamNo, @Param("prjType1") String prjType1);
}
