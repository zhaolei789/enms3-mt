package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.Operationlog;

public interface OperationlogService extends SystemBaseService<Operationlog, String> {

    PageSet<Operationlog> getPageSet(PageParam pageParam, String filterSort);
}