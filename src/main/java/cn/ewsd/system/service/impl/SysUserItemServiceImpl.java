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

import cn.ewsd.system.mapper.SysUserItemMapper;
import cn.ewsd.system.model.SysUserItem;
import cn.ewsd.system.service.SysUserItemService;
import cn.ewsd.system.service.impl.SystemBaseServiceImpl;

@Service("sysUserItemServiceImpl")
public class SysUserItemServiceImpl extends SystemBaseServiceImpl<SysUserItem, String> implements SysUserItemService {
	@Autowired
	private SysUserItemMapper sysUserItemMapper;

    @Override
    public PageSet<SysUserItem> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<SysUserItem> list = sysUserItemMapper.getPageSet(filterSort);
        PageInfo<SysUserItem> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public SysUserItem queryObject(String uuid){
		return sysUserItemMapper.queryObject(uuid);
	}

	@Override
	public List<SysUserItem> queryList(Map<String, Object> map){
		return sysUserItemMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return sysUserItemMapper.queryTotal(map);
	}

	@Override
	public int executeSave(SysUserItem sysUserItem){
		return sysUserItemMapper.executeSave(sysUserItem);
	}

	@Override
	public int executeUpdate(SysUserItem sysUserItem){
		return sysUserItemMapper.executeUpdate(sysUserItem);
	}

	@Override
	public int executeDelete(String uuid){
		return sysUserItemMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return sysUserItemMapper.executeDeleteBatch(uuids);
	}

}
