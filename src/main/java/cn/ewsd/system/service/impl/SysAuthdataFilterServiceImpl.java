package cn.ewsd.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.ewsd.system.mapper.SysAuthdataFilterMapper;
import cn.ewsd.system.model.SysAuthdataFilter;
import cn.ewsd.system.service.SysAuthdataFilterService;
import cn.ewsd.system.service.impl.SystemBaseServiceImpl;

@Service("sysAuthdataFilterServiceImpl")
public class SysAuthdataFilterServiceImpl extends SystemBaseServiceImpl<SysAuthdataFilter, String> implements SysAuthdataFilterService {
    @Autowired
    private SysAuthdataFilterMapper sysAuthdataFilterMapper;

    @Override
    public PageSet<SysAuthdataFilter> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<SysAuthdataFilter> list = sysAuthdataFilterMapper.getPageSet(filterSort);
        PageInfo<SysAuthdataFilter> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public SysAuthdataFilter queryObject(String uuid) {
        return sysAuthdataFilterMapper.queryObject(uuid);
    }

    @Override
    public List<SysAuthdataFilter> queryList(Map<String, Object> map) {
        return sysAuthdataFilterMapper.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysAuthdataFilterMapper.queryTotal(map);
    }

    @Override
    public int executeSave(SysAuthdataFilter sysAuthdataFilter) {
        return sysAuthdataFilterMapper.executeSave(sysAuthdataFilter);
    }

    @Override
    public int executeUpdate(SysAuthdataFilter sysAuthdataFilter) {
        return sysAuthdataFilterMapper.executeUpdate(sysAuthdataFilter);
    }

    @Override
    public int executeDelete(String uuid) {
        return sysAuthdataFilterMapper.executeDelete(uuid);
    }

    @Override
    public int executeDeleteBatch(String[] uuids) {
        return sysAuthdataFilterMapper.executeDeleteBatch(uuids);
    }

    @Override
    public List<SysAuthdataFilter> getListByApiUuid(String apiUuid) {
        return sysAuthdataFilterMapper.getListByApiUuid(apiUuid);
    }

}
