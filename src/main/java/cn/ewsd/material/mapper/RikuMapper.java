package cn.ewsd.material.mapper;
import cn.ewsd.material.model.Riku;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface RikuMapper{
    List<Riku> getMatList(@Param("matQry") String matQry, @Param("abcTypeQry") String abcTypeQry, @Param("startDateQry") String startDateQry,
                          @Param("endDateQry") String endDateQry, @Param("planTypeQry") String planTypeQry, @Param("costCenterQry") String costCenterQry,
                          @Param("monthQry") String monthQry, @Param("ifAqfyQry") String ifAqfyQry, @Param("wbsQry") String wbsQry);
}
