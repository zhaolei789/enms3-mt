package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.SysUserDept;
import cn.ewsd.system.model.SysUserQryOrg;

/**
 * 
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-06-09 17:58:25
 */
public interface SysUserDeptService extends SystemBaseService<SysUserDept, String> {
    PageSet<SysUserDept> getPageSet(PageParam pageParam, String filterSort);

    int executeDeleteBatch(String[] uuids);
}
