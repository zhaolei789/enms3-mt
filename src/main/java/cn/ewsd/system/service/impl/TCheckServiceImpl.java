package cn.ewsd.system.service.impl;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.mdata.mapper.OrganizationMapper;
import cn.ewsd.mdata.model.Organization;
import cn.ewsd.system.mapper.SysUserQryOrgMapper;
import cn.ewsd.system.mapper.TCheckMapper;
import cn.ewsd.system.model.SysUserQryOrg;
import cn.ewsd.system.model.TCheck;
import cn.ewsd.system.service.SysUserQryOrgService;
import cn.ewsd.system.service.TCheckService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("tCheckServiceImpl")
public class TCheckServiceImpl extends SystemBaseServiceImpl<TCheck, String> implements TCheckService {
	@Autowired
	private TCheckMapper tCheckMapper;

    @Override
    public List<TCheck> getTCheckByNo(String checkNo){
        return tCheckMapper.getTCheckByNo(checkNo);
    }

    @Override
    public List<TCheck> getTCheckList(String checkNos){
        return tCheckMapper.getTCheckList(checkNos);
    }
}
