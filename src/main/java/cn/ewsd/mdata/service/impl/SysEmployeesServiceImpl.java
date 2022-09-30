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

import cn.ewsd.mdata.mapper.SysEmployeesMapper;
import cn.ewsd.mdata.model.SysEmployees;
import cn.ewsd.mdata.service.SysEmployeesService;
import cn.ewsd.mdata.service.impl.MdataBaseServiceImpl;

@Service("sysEmployeesServiceImpl")
public class SysEmployeesServiceImpl extends MdataBaseServiceImpl<SysEmployees, String> implements SysEmployeesService {
	@Autowired
	private SysEmployeesMapper sysEmployeesMapper;

    @Override
    public PageSet<SysEmployees> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<SysEmployees> list = sysEmployeesMapper.getPageSet(filterSort);
        PageInfo<SysEmployees> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public SysEmployees queryObject(String uuid){
		return sysEmployeesMapper.queryObject(uuid);
	}

	@Override
	public List<SysEmployees> queryList(Map<String, Object> map){
		return sysEmployeesMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return sysEmployeesMapper.queryTotal(map);
	}

	@Override
	public int executeSave(SysEmployees sysEmployees){
		return sysEmployeesMapper.executeSave(sysEmployees);
	}

	@Override
	public int executeUpdate(SysEmployees sysEmployees){
		return sysEmployeesMapper.executeUpdate(sysEmployees);
	}

	@Override
	public int executeDelete(String uuid){
		return sysEmployeesMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return sysEmployeesMapper.executeDeleteBatch(uuids);
	}

	@Override
	public SysEmployees getByEmpNo(String empNo){
		return sysEmployeesMapper.getByEmpNo(empNo);
	}

	@Override
	public List<SysEmployees> selectAllList() {
		return sysEmployeesMapper.selectAllList();
	}

	@Override
	public List<SysEmployees> getLimitedListByQ(String q) {
		return sysEmployeesMapper.getLimitedListByQ(q);
	}
}
