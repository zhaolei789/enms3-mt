package cn.ewsd.material.mapper;
import cn.ewsd.material.model.MMakeMatDept;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-21 16:55:52
 */
public interface MMakeMatDeptMapper extends tk.mybatis.mapper.common.Mapper<MMakeMatDept> {
    int deleteByMatNo(String matNo);

    MMakeMatDept findByMatNo(String matNo);

    int updateByMatNo(@Param("makeDept") String makeDept, @Param("makePrice") BigDecimal makePrice, @Param("matNo") String matNo);

    int insertData(MMakeMatDept mMakeMatDept);
}
