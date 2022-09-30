package cn.ewsd.system.service.impl;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.system.mapper.SysCsBaseMapper;
import cn.ewsd.system.model.SysCsBase;
import cn.ewsd.system.service.SysCsBaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("sysCsBaseServiceImpl")
public class SysCsBaseServiceImpl extends SystemBaseServiceImpl<SysCsBase, String> implements SysCsBaseService {
	@Autowired
	private SysCsBaseMapper sysCsBaseMapper;

    @Override
    public PageSet<SysCsBase> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<SysCsBase> list = sysCsBaseMapper.getPageSet(filterSort);
        PageInfo<SysCsBase> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public SysCsBase queryObject(String uuid){
		return sysCsBaseMapper.queryObject(uuid);
	}

	@Override
	public List<SysCsBase> queryList(Map<String, Object> map){
		return sysCsBaseMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return sysCsBaseMapper.queryTotal(map);
	}

	@Override
	public int executeSave(SysCsBase sysCsBase){
		return sysCsBaseMapper.executeSave(sysCsBase);
	}

	@Override
	public int executeUpdate(SysCsBase sysCsBase){
		return sysCsBaseMapper.executeUpdate(sysCsBase);
	}

	@Override
	public int executeDelete(String uuid){
		return sysCsBaseMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return sysCsBaseMapper.executeDeleteBatch(uuids);
	}

}
