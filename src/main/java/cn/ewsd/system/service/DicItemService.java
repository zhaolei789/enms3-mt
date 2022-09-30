package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.DicItem;

import java.util.List;
import java.util.Map;

public interface DicItemService extends SystemBaseService<DicItem, String> {

    /**
     *
     * @param puuid
     * @param value
     * @return
     */
    DicItem getDicItem(String puuid, String value);

    PageSet<DicItem> getPageSet(PageParam pageParam, String filterSort);

    List<Map<String,Object>> getListByPuuid(String uuid);

    List<DicItem> getListByUuid(String uuid);

    DicItem getDicItemByValue(String value);
}