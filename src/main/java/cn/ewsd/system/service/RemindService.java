package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.Remind;

public interface RemindService extends SystemBaseService<Remind, String> {

    PageSet<Remind> getPageSet(PageParam pageParam, String filterSort);
}