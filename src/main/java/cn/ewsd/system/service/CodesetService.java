package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.Codeset;

public interface CodesetService extends SystemBaseService<Codeset, String> {

    PageSet<Codeset> getPageSet(PageParam pageParam, String filterSort);
}