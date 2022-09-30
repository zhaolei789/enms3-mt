package cn.ewsd.material.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MItem;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-21 16:55:52
 */
public interface MItemService extends MaterialBaseService<MItem, String> {

    PageSet<MItem> getPageSet(PageParam pageParam, String filterSort);

	MItem queryObject(String itemNo);

    int executeSave(MItem mItem);

	int executeUpdate(MItem mItem);

	int executeDeleteBatch(String[] uuid);

	MItem getItemByItemNoAndNotByUuid(String itemNo, String uuid);

	List<MItem> getMatItemSet(boolean isMyItem, String userNo);

	List<MItem> getItemSet(String prjNo);

	String getPayTeam(String prjNo, String itemNo, String teamNo);

	List<MItem> getItemList(String prjNo, String planMonth, String planType, String planStep);

	List<MItem> getItemList1(String teamNo, String fMonth);
}
