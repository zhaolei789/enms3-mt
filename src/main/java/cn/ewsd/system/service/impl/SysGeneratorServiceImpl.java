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

import cn.ewsd.system.mapper.SysGeneratorMapper;
import cn.ewsd.system.model.SysGenerator;
import cn.ewsd.system.service.SysGeneratorService;
import cn.ewsd.system.service.impl.SystemBaseServiceImpl;

@Service("sysGeneratorServiceImpl")
public class SysGeneratorServiceImpl extends SystemBaseServiceImpl<SysGenerator, String> implements SysGeneratorService {
	@Autowired
	private SysGeneratorMapper sysGeneratorMapper;

    @Override
    public PageSet<SysGenerator> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<SysGenerator> list = sysGeneratorMapper.getPageSet(filterSort);
        PageInfo<SysGenerator> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public SysGenerator queryObject(String uuid){
		return sysGeneratorMapper.queryObject(uuid);
	}

	@Override
	public List<SysGenerator> queryList(Map<String, Object> map){
		return sysGeneratorMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return sysGeneratorMapper.queryTotal(map);
	}

	@Override
	public int executeSave(SysGenerator sysGenerator){
		return sysGeneratorMapper.executeSave(sysGenerator);
	}

	@Override
	public int executeUpdate(SysGenerator sysGenerator){
		return sysGeneratorMapper.executeUpdate(sysGenerator);
	}

	@Override
	public int executeDelete(String uuid){
		return sysGeneratorMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return sysGeneratorMapper.executeDeleteBatch(uuids);
	}

}
