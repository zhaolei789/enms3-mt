package cn.ewsd.material.mapper;
import cn.ewsd.material.model.MPurchasePlan;
import org.apache.ibatis.annotations.Param;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface MPurchasePlanMapper extends tk.mybatis.mapper.common.Mapper<MPurchasePlan> {
    int savePurchasePlan(@Param("purNo") String purNo, @Param("planNo") String planNo, @Param("planAmount") double planAmount, @Param("buyAmount") double buyAmount);
}
