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

import cn.ewsd.system.mapper.SysAuthdataApiMapper;
import cn.ewsd.system.model.SysAuthdataApi;
import cn.ewsd.system.service.SysAuthdataApiService;
import cn.ewsd.system.service.impl.SystemBaseServiceImpl;

@Service("sysAuthdataApiServiceImpl")
public class SysAuthdataApiServiceImpl extends SystemBaseServiceImpl<SysAuthdataApi, String> implements SysAuthdataApiService {
	@Autowired
	private SysAuthdataApiMapper sysAuthdataApiMapper;

    @Override
    public PageSet<SysAuthdataApi> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<SysAuthdataApi> list = sysAuthdataApiMapper.getPageSet(filterSort);
        PageInfo<SysAuthdataApi> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public SysAuthdataApi queryObject(String uuid){
		return sysAuthdataApiMapper.queryObject(uuid);
	}

	@Override
	public List<SysAuthdataApi> queryList(Map<String, Object> map){
		return sysAuthdataApiMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return sysAuthdataApiMapper.queryTotal(map);
	}

	@Override
	public int executeSave(SysAuthdataApi sysAuthdataApi){
		return sysAuthdataApiMapper.executeSave(sysAuthdataApi);
	}

	@Override
	public int executeUpdate(SysAuthdataApi sysAuthdataApi){
		return sysAuthdataApiMapper.executeUpdate(sysAuthdataApi);
	}

	@Override
	public int executeDelete(String uuid){
		return sysAuthdataApiMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return sysAuthdataApiMapper.executeDeleteBatch(uuids);
	}

    @Override
    public List<SysAuthdataApi> getListByGroupUuidsAndUrl(String[] groupUuids, String url) {
        return sysAuthdataApiMapper.getListByGroupUuidsAndUrl(groupUuids, url);
    }

}
