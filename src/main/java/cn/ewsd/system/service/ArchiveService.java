package cn.ewsd.system.service;


import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.Archive;

import java.util.List;
import java.util.Map;

public interface ArchiveService extends SystemBaseService<Archive, String> {


    List<Archive> getListByCreateTime();

    String getChildIds(Map map);

    PageSet<Archive> getPageSet(PageParam pageParam, String filterSort);

    List<Archive> getListByFilterSort(String filterSort);
}