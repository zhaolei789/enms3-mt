package cn.ewsd.material.mapper;
import cn.ewsd.material.model.MFactoryAddr;
import cn.ewsd.material.model.MMaterial;
import cn.ewsd.material.model.MSettleItem;
import cn.ewsd.system.model.DicItem;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface DataMapper extends tk.mybatis.mapper.common.Mapper<MSettleItem> {

    List<MSettleItem> getList(@Param("userTeam") String userTeam, @Param("occMonth") String occMonth);

    List<MSettleItem> getItem(String userTeam);

    int insertSettleValue(MSettleItem mSettleItem);

    int updateSettleValue(MSettleItem mSettleItem);

    List<MSettleItem> getDayList(@Param("occMonth") String occMonth, @Param("itemNo") String itemNo);

    int deleteDayData(String occMonth);

    int insertDayData(MSettleItem mSettleItem);

    int updateDayData(MSettleItem mSettleItem);

}
