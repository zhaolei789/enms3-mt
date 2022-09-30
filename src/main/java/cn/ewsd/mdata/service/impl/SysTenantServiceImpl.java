package cn.ewsd.mdata.service.impl;

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

import cn.ewsd.mdata.mapper.SysTenantMapper;
import cn.ewsd.mdata.model.SysTenant;
import cn.ewsd.mdata.service.SysTenantService;
import cn.ewsd.mdata.service.impl.MdataBaseServiceImpl;

@Service("sysTenantServiceImpl")
public class SysTenantServiceImpl extends MdataBaseServiceImpl<SysTenant, String> implements SysTenantService {
	@Autowired
	private SysTenantMapper sysTenantMapper;

    @Override
    public PageSet<SysTenant> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<SysTenant> list = sysTenantMapper.getPageSet(filterSort);
        PageInfo<SysTenant> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public SysTenant queryObject(String uuid){
		return sysTenantMapper.queryObject(uuid);
	}

	@Override
	public List<SysTenant> queryList(Map<String, Object> map){
		return sysTenantMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return sysTenantMapper.queryTotal(map);
	}

	@Override
	public int executeSave(SysTenant sysTenant){
		return sysTenantMapper.executeSave(sysTenant);
	}

	@Override
	public int executeUpdate(SysTenant sysTenant){
		return sysTenantMapper.executeUpdate(sysTenant);
	}

	@Override
	public int executeDelete(String uuid){
		return sysTenantMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return sysTenantMapper.executeDeleteBatch(uuids);
	}

	@Override
	public List<SysTenant> queryListByFS(String filterSort){
		return sysTenantMapper.getPageSet(filterSort);
	}

	@Override
	public List<SysTenant> queryListByIds(String[] uuids){
		return sysTenantMapper.queryListByIds(uuids);
	}

	@Override
	public SysTenant getOneOld() {
		return sysTenantMapper.getOneOld();
	}
}
