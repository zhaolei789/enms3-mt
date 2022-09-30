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

import cn.ewsd.system.mapper.SysUserStoreMapper;
import cn.ewsd.system.model.SysUserStore;
import cn.ewsd.system.service.SysUserStoreService;
import cn.ewsd.system.service.impl.SystemBaseServiceImpl;

@Service("sysUserStoreServiceImpl")
public class SysUserStoreServiceImpl extends SystemBaseServiceImpl<SysUserStore, String> implements SysUserStoreService {
	@Autowired
	private SysUserStoreMapper sysUserStoreMapper;

    @Override
    public PageSet<SysUserStore> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<SysUserStore> list = sysUserStoreMapper.getPageSet(filterSort);
        PageInfo<SysUserStore> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public SysUserStore queryObject(String uuid){
		return sysUserStoreMapper.queryObject(uuid);
	}

	@Override
	public List<SysUserStore> queryList(Map<String, Object> map){
		return sysUserStoreMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return sysUserStoreMapper.queryTotal(map);
	}

	@Override
	public int executeSave(SysUserStore sysUserStore){
		return sysUserStoreMapper.executeSave(sysUserStore);
	}

	@Override
	public int executeUpdate(SysUserStore sysUserStore){
		return sysUserStoreMapper.executeUpdate(sysUserStore);
	}

	@Override
	public int executeDelete(String uuid){
		return sysUserStoreMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return sysUserStoreMapper.executeDeleteBatch(uuids);
	}

}
