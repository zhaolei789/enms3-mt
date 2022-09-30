package cn.ewsd.material.mapper;
import cn.ewsd.material.model.MItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-21 16:55:52
 */
public interface MItemMapper extends tk.mybatis.mapper.common.Mapper<MItem> {

    //获取分页集
    List<MItem> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(MItem var1);

    int executeUpdate(MItem var1);

    int executeDeleteBatch(Object[] uuid);

    MItem queryObject(@Param("itemNo") Object itemNo);

    MItem getItemByItemNoAndNotByUuid(@Param("itemNo")String itemNo, @Param("uuid")String uuid);

    List<MItem> getMatItemSet(@Param("isMyItem") boolean isMyItem, @Param("userNo") String userNo);

    List<MItem> getItemSet(String prjNo);

    List<MItem> getItemList(@Param("prjNo") String prjNo, @Param("planMonth") String planMonth, @Param("planType") String planType, @Param("planStep") String planStep);

    List<MItem> getItemList1(@Param("teamNo") String teamNo, @Param("fMonth") String fMonth);
}
