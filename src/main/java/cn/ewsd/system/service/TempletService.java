package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.Templet;


public interface TempletService extends SystemBaseService<Templet, String> {

    public PageSet<Templet> getPageSet(PageParam pageParam, String filterStr);

}