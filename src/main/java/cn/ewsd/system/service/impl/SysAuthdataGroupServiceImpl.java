package cn.ewsd.system.service.impl;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.system.mapper.SysAuthdataGroupMapper;
import cn.ewsd.system.model.SysAuthdataGroup;
import cn.ewsd.system.service.SysAuthdataGroupService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("sysAuthdataGroupServiceImpl")
public class SysAuthdataGroupServiceImpl extends SystemBaseServiceImpl<SysAuthdataGroup, String> implements SysAuthdataGroupService {
    @Autowired
    private SysAuthdataGroupMapper sysAuthdataGroupMapper;

    @Override
    public PageSet<SysAuthdataGroup> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<SysAuthdataGroup> list = sysAuthdataGroupMapper.getPageSet(filterSort);
        PageInfo<SysAuthdataGroup> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public SysAuthdataGroup queryObject(String uuid) {
        return sysAuthdataGroupMapper.queryObject(uuid);
    }

    @Override
    public List<SysAuthdataGroup> queryList(Map<String, Object> map) {
        return sysAuthdataGroupMapper.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysAuthdataGroupMapper.queryTotal(map);
    }

    @Override
    public int executeSave(SysAuthdataGroup sysAuthdataGroup) {
        return sysAuthdataGroupMapper.executeSave(sysAuthdataGroup);
    }

    @Override
    public int executeUpdate(SysAuthdataGroup sysAuthdataGroup) {
        return sysAuthdataGroupMapper.executeUpdate(sysAuthdataGroup);
    }

    @Override
    public int executeDelete(String uuid) {
        return sysAuthdataGroupMapper.executeDelete(uuid);
    }

    @Override
    public int executeDeleteBatch(String[] uuids) {
        return sysAuthdataGroupMapper.executeDeleteBatch(uuids);
    }

    @Override
    public String getGroupUuidsByIds(String ids) {
        List<SysAuthdataGroup> sysAuthdataGroups = sysAuthdataGroupMapper.getGroupUuidsByIds(ids);
        String groupUuids = "";
        for (int i = 0; i < sysAuthdataGroups.size(); i++) {
            groupUuids += sysAuthdataGroups.get(i).getUuid() + (i == sysAuthdataGroups.size() - 1 ? "" : ",");
        }
        return groupUuids;
    }

    @Override
    public Integer getMaxById() {
        Integer id = sysAuthdataGroupMapper.getMaxById();
        return id == null ? 0 : id;
    }

}
