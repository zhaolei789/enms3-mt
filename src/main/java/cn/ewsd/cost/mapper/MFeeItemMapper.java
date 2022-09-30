package cn.ewsd.cost.mapper;
import cn.ewsd.cost.model.FAccount;
import cn.ewsd.cost.model.MFeeItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 工程结算
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-03 08:39:36
 */
public interface MFeeItemMapper extends tk.mybatis.mapper.common.Mapper<MFeeItem> {
    List<MFeeItem> getItemForSelect(@Param("itemType") String itemType, @Param("ifUse") String ifUse, @Param("teamNo") String teamNo);

    List<MFeeItem> getMatAssessItemList(String teamNo);

    List<MFeeItem> getFeeItemList(@Param("itemType") String itemType, @Param("ifUse") String ifUse, @Param("mngDept") String mngDept, @Param("teamNo") String teamNo);

    int insertFeeItem(MFeeItem mFeeItem);

    MFeeItem getFeeItemByItemNo(String itemNo);

    int updateFeeItem(MFeeItem mFeeItem);

    int deleteFeeItem(String itemNo);

    List<MFeeItem> getTeamItemQuotaItem(@Param("teamNo") String teamNo, @Param("mngTeam") String mngTeam);
}
