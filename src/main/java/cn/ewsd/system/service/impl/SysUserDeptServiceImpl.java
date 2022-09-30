package cn.ewsd.system.service.impl;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.system.mapper.SysUserDeptMapper;
import cn.ewsd.system.mapper.SysUserQryOrgMapper;
import cn.ewsd.system.model.SysUserDept;
import cn.ewsd.system.model.SysUserQryOrg;
import cn.ewsd.system.service.SysUserDeptService;
import cn.ewsd.system.service.SysUserQryOrgService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sysUserDeptServiceImpl")
public class SysUserDeptServiceImpl extends SystemBaseServiceImpl<SysUserDept, String> implements SysUserDeptService {
	@Autowired
	private SysUserDeptMapper sysUserDeptMapper;

    @Override
    public PageSet<SysUserDept> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<SysUserDept> list = sysUserDeptMapper.getPageSet(filterSort);
        PageInfo<SysUserDept> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public int executeDeleteBatch(String[] uuids){
        return sysUserDeptMapper.executeDeleteBatch(uuids);
    }
}
