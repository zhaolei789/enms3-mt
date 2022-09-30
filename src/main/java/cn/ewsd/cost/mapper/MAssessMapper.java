package cn.ewsd.cost.mapper;
import cn.ewsd.cost.model.MAssess;
import cn.ewsd.cost.model.MNormFee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 矿长
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-08-31 17:13:54
 */
public interface MAssessMapper extends tk.mybatis.mapper.common.Mapper<MAssess> {
    List<MAssess> getMatAssessAssList(@Param("occMonth") String occMonth, @Param("teamNo") String teamNo);

    int deleteAssess(@Param("occMonth") String occMonth, @Param("itemNo") String itemNo, @Param("teamNo") String teamNo, @Param("prjNo") String prjNo, @Param("assType") String assType);

    List<MAssess> getAssessList(@Param("occMonth") String occMonth, @Param("assType") String assType, @Param("teamNo") String teamNo, @Param("itemNo") String itemNo);

    int insertAssess(MAssess mAssess);

    double getAwardBala(@Param("occMonth") String occMonth, @Param("teamNo") String teamNo);

    MAssess getAssess(String assessId);

    int updateAssess(MAssess mAssess);

    List<MAssess> getMatAssZhAssList(@Param("results") String results);

    List<MAssess> getMatAssDcAssList(@Param("results") String results);
}
