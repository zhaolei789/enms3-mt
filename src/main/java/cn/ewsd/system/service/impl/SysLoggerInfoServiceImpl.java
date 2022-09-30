package cn.ewsd.system.service.impl;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.system.mapper.SysLoggerInfoMapper;
import cn.ewsd.system.model.SysLoggerInfo;
import cn.ewsd.system.service.SysLoggerInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("sysLoggerInfoServiceImpl")
public class SysLoggerInfoServiceImpl extends SystemBaseServiceImpl<SysLoggerInfo, String> implements SysLoggerInfoService {
	@Autowired
	private SysLoggerInfoMapper sysLoggerInfoMapper;

    @Override
    public PageSet<SysLoggerInfo> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<SysLoggerInfo> list = sysLoggerInfoMapper.getPageSet(filterSort);
        PageInfo<SysLoggerInfo> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public SysLoggerInfo queryObject(String uuid){
		return sysLoggerInfoMapper.queryObject(uuid);
	}

	@Override
	public List<SysLoggerInfo> queryList(Map<String, Object> map){
		return sysLoggerInfoMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return sysLoggerInfoMapper.queryTotal(map);
	}

	@Override
	public int executeSave(SysLoggerInfo sysLoggerInfo){
		return sysLoggerInfoMapper.executeSave(sysLoggerInfo);
	}

	@Override
	public int executeUpdate(SysLoggerInfo sysLoggerInfo){
		return sysLoggerInfoMapper.executeUpdate(sysLoggerInfo);
	}

	@Override
	public int executeDelete(String uuid){
		return sysLoggerInfoMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return sysLoggerInfoMapper.executeDeleteBatch(uuids);
	}

}
