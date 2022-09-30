package cn.ewsd.material.mapper;
import cn.ewsd.material.model.MErpMat;

import java.util.HashMap;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface MErpMatMapper extends tk.mybatis.mapper.common.Mapper<MErpMat> {
    HashMap<String, Object> getRealTimePurchaseCircle(String matCode);
}
