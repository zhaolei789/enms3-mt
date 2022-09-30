package cn.ewsd.material.mapper;
import cn.ewsd.material.model.MMatType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-21 11:04:46
 */
public interface MMatTypeMapper extends tk.mybatis.mapper.common.Mapper<MMatType> {
    List<MMatType> getMatTypeForSelect();

    List<MMatType> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(MMatType var1);

    MMatType queryObject(Object var1);

    int executeUpdate(MMatType var1);

    int executeDeleteBatch(Object[] var1);

    int getMatCountByMatType(@Param("typeCode")String typeCode);

    MMatType getMatTypeByCode(String typeCode);
}
