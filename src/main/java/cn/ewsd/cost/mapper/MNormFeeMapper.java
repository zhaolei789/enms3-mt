package cn.ewsd.cost.mapper;
import cn.ewsd.cost.model.MNormFee;
import cn.ewsd.cost.model.MOutAssess;
import cn.ewsd.mdata.model.Organization;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * 矿长
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-08-31 17:13:54
 */
public interface MNormFeeMapper extends tk.mybatis.mapper.common.Mapper<MNormFee> {
    List<MNormFee> getMatAssessList(@Param("occMonth") String occMonth, @Param("teamNo") String teamNo);

    int deleteNormFee(@Param("occMonth") String occMonth, @Param("itemNo") String itemNo, @Param("teamNo") String teamNo, @Param("prjNo") String prjNo, @Param("assType") String assType);

    int insertNormFee(MNormFee mNormFee);

    int updateNormFee(MNormFee mNormFee);

    List<MNormFee> getMatAssessMulti(@Param("occMonth") String occMonth, @Param("assType") String assType, @Param("teamNo") String teamNo);

    List<MNormFee> getMatAssTyList(@Param("occMonth") String occMonth, @Param("assType") String assType, @Param("ifEnter") String ifEnter);

    List<MNormFee> getMatAssZhList(@Param("occMonth") String occMonth, @Param("assType") String assType);

    List<MNormFee> getMatAssDcList(@Param("occMonth") String occMonth, @Param("assType") String assType);

    List<MNormFee> getMatAssItemList(@Param("occMonth") String occMonth, @Param("beginDate") String beginDate, @Param("endDate") String endDate);
}
