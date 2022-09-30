package cn.ewsd.material.mapper;
import cn.ewsd.material.model.MTypeMat;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-21 16:55:52
 */
public interface MTypeMatMapper extends tk.mybatis.mapper.common.Mapper<MTypeMat> {
    List<MTypeMat> getTypeMatByMatNo(String matNo);

    List<MTypeMat> getTypeMatList(@Param("matCodeQry") String matCodeQry, @Param("matNameQry") String matNameQry);

    List<MTypeMat> getComPlanList(@Param("planMonth") String planMonth, @Param("matQry") String matQry);

    int deleteTypeMat(@Param("typeCode") String typeCode, @Param("matNo") String matNo);

    int insertTypeMat(MTypeMat mTypeMat);

    int updateTypeMat(MTypeMat mTypeMat);
}
