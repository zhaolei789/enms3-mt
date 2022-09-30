package cn.ewsd.material.mapper;
import cn.ewsd.material.model.MErpMaterial;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-21 16:55:52
 */
public interface MErpMaterialMapper extends tk.mybatis.mapper.common.Mapper<MErpMaterial> {
    int executeSave(MErpMaterial mErpMaterial);

    int deleteByMatNo(String matNo);

    List<MErpMaterial> getErpMaterialByMatNo(String matNo);

    MErpMaterial getErpMaterialByMatNoAndFactoryAndAddr(@Param("matNo") String matNo, @Param("factoryNo") String factoryNo, @Param("matAddr") String matAddr);
}
