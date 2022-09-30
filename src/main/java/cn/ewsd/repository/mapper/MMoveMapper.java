package cn.ewsd.repository.mapper;
import cn.ewsd.material.model.MMaterial;
import cn.ewsd.material.model.WzscPlan;
import cn.ewsd.repository.model.MIn;
import cn.ewsd.repository.model.MMove;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
public interface MMoveMapper extends tk.mybatis.mapper.common.Mapper<MMove> {
    List<MMove> getMoveOrderList(@Param("monthQry") String monthQry);

    int insertMove(MMove mMove);

    MMove getMove(@Param("transNo") String transNo);

    double getPickAmount(@Param("transNo") String transNo);

    int deleteMove(@Param("transNo") String transNo);

    int deleteMoveList(@Param("transNo") String transNo);

    List<MMaterial> getMoveMat(@Param("transNo") String transNo, @Param("outStore") String outStore, @Param("matCodeQry") String matCode, @Param("matNameQry") String matNameQry);

    int saveList(MMove move);

    List<MMove> getMoveList(@Param("transNo") String transNo);

    int deleteList(@Param("listNo") String listNo);

    int updateList(@Param("listNo") String listNo, @Param("matAmount") String matAmount);

    int updateList1(@Param("getAmount") String getAmount, @Param("getEmp") String getEmp, @Param("getDate") String getDate, @Param("listNo") String listNo);

    int updateMove(@Param("moveStep") String moveStep, @Param("userId") String userId, @Param("takeDate") String takeDate, @Param("takeTime") String takeTime, @Param("transNo") String transNo);

    int updateMove1(@Param("signDate") String signDate, @Param("signTime") String signTime, @Param("moveStep") String moveStep, @Param("transNo") String transNo);

    List<MMove> getMoveCheckList(@Param("userId") String userId);

    List<MMove> getCheckList(@Param("transNo") String transNo);

    MMove getMl(@Param("listNo") String listNo);

    int getCnt(@Param("transNo") String transNo);

    int getCnt1(@Param("transNo") String transNo);
}
