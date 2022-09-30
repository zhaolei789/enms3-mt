package cn.ewsd.material.mapper;
import cn.ewsd.material.model.MPrjItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface MPrjItemMapper extends tk.mybatis.mapper.common.Mapper<MPrjItem> {
    int deleteByPrjNo(String prjNo);

    List<MPrjItem> getPrjItemListByPrjNo(@Param("prjNo") String prjNo, @Param("filterSort") String filterSort);

    int saveData(MPrjItem mPrjItem);

    String[] getItemNosByPrjNo(String prjNo);

    double getSumMatBala(String prjNo);
}
