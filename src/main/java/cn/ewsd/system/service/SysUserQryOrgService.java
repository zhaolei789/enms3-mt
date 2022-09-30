package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.mdata.model.Organization;
import cn.ewsd.system.model.SysUserQryOrg;
import cn.ewsd.system.model.SysUserStore;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-06-09 17:58:25
 */
public interface SysUserQryOrgService extends SystemBaseService<SysUserQryOrg, String> {
    PageSet<SysUserQryOrg> getPageSet(PageParam pageParam, String filterSort);

    int executeDeleteBatch(String[] uuids);

    String getUserDeptId(String userId);

    public List<Organization> getDeptSet(String userId, String deptLevel, boolean justVirtual);
}
