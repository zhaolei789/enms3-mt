package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.SysAuthdataFilter;

import java.util.List;
import java.util.Map;

/**
 * 数据权限过滤器
 *
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-12-26 09:58:54
 */
public interface SysAuthdataFilterService extends SystemBaseService<SysAuthdataFilter, String> {

    PageSet<SysAuthdataFilter> getPageSet(PageParam pageParam, String filterSort);

    SysAuthdataFilter queryObject(String uuid);

    List<SysAuthdataFilter> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    int executeSave(SysAuthdataFilter sysAuthdataFilter);

    int executeUpdate(SysAuthdataFilter sysAuthdataFilter);

    int executeDelete(String uuid);

    int executeDeleteBatch(String[] uuids);

    List<SysAuthdataFilter> getListByApiUuid(String apiUuid);
}
