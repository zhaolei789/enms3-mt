package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.DicItem;
import cn.ewsd.system.model.DicSet;

import javax.servlet.ServletContext;
import java.util.List;

public interface DicSetService extends SystemBaseService<DicSet, String> {

    public void init(ServletContext sc);

    Object getDicItem(String key);

    DicSet getDicSetByCode(String code);

    DicSet getListByCode(String code);

    Integer updateIsDel(int i, String s);

    List<DicSet> getListByCodes(String code);

    PageSet<DicSet> getPageSet(PageParam pageParam, String filterSort);

    DicItem getListByValue(String value);

    Boolean getListByValueAndUuid(String value, String uuid);

    List<DicSet> getAllDicSet();
}