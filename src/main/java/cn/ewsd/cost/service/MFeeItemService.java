package cn.ewsd.cost.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.cost.model.FAward;
import cn.ewsd.cost.model.MFeeItem;

import java.util.List;
import java.util.Map;

/**
 * 奖罚考核
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-08-26 18:03:27
 */
public interface MFeeItemService extends CostBaseService<MFeeItem, String> {

    List<MFeeItem> getItemForSelect(String itemType, String ifUse, String teamNo);

    PageSet<MFeeItem> getFeeItemPageSet(PageParam pageParam, String filterSort, String itemType, String ifUse, String mngDept, String teamNo);

    void insertFeeItem(MFeeItem mFeeItem) throws Exception;

    MFeeItem getFeeItemByItemNo(String itemNo);

    void updateFeeItem(MFeeItem mFeeItem) throws Exception;

    void deleteFeeItem(String[] itemNos) throws Exception;

    List<MFeeItem> getTeamItemQuotaItem(String teamNo, String mngTeam);
}
