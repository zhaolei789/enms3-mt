package cn.ewsd.system.service.impl;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.mdata.mapper.OrganizationMapper;
import cn.ewsd.mdata.model.Organization;
import cn.ewsd.system.mapper.SysUserQryOrgMapper;
import cn.ewsd.system.mapper.SysUserStoreMapper;
import cn.ewsd.system.model.SysUserQryOrg;
import cn.ewsd.system.model.SysUserStore;
import cn.ewsd.system.service.SysUserQryOrgService;
import cn.ewsd.system.service.SysUserStoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sysUserQryOrgServiceImpl")
public class SysUserQryOrgServiceImpl extends SystemBaseServiceImpl<SysUserQryOrg, String> implements SysUserQryOrgService {
	@Autowired
	private SysUserQryOrgMapper sysUserQryOrgMapper;
	@Autowired
    private OrganizationMapper organizationMapper;

    @Override
    public PageSet<SysUserQryOrg> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<SysUserQryOrg> list = sysUserQryOrgMapper.getPageSet(filterSort);
        PageInfo<SysUserQryOrg> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public int executeDeleteBatch(String[] uuids){
        return sysUserQryOrgMapper.executeDeleteBatch(uuids);
    }

    @Override
    public String getUserDeptId(String userId){
        String ret = "";

        try{
            List<SysUserQryOrg> list = sysUserQryOrgMapper.getQryOrgByUserId(userId);
            for(int i=0; i<list.size(); i++){
                SysUserQryOrg sysUserQryOrg = list.get(i);
                int orgId = sysUserQryOrg.getOrgId();

                Map map = new HashMap();
                map.put("p1", "sys_organization");
                map.put("p2", orgId);
                map.put("p3", "idStr");
                organizationMapper.getChildIds(map);
                String ids = map.get("p3").toString();

                ret += i==0 ? ids : (","+ids);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return ret;
    }

    @Override
    public List<Organization> getDeptSet(String userId, String deptLevel, boolean justVirtual){
        return sysUserQryOrgMapper.getDeptSet(getUserDeptId(userId), deptLevel, justVirtual);
    }
}
