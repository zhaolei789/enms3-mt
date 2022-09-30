package cn.ewsd.material.mapper;
import cn.ewsd.material.model.MOffer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface MOfferMapper extends tk.mybatis.mapper.common.Mapper<MOffer> {
    List<MOffer> getPageSet(@Param("filterSort") String filterSort);

    String getMaxOfferNo();

    int executeSave(MOffer offer);

    MOffer queryObject(String uuid);

    int executeUpdate(MOffer offer);

    int executeDelete(String uuid);

    List<MOffer> getOfferList(String offerName);

    MOffer getOfferByName(String offerName);
}
