package cn.ewsd.cost.service.impl;

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

import cn.ewsd.cost.mapper.SysUserBarMasterMapper;
import cn.ewsd.cost.model.SysUserBarMaster;
import cn.ewsd.cost.service.SysUserBarMasterService;
import cn.ewsd.cost.service.impl.CostBaseServiceImpl;

@Service("sysUserBarMasterServiceImpl")
public class SysUserBarMasterServiceImpl extends CostBaseServiceImpl<SysUserBarMaster, String> implements SysUserBarMasterService {
	@Autowired
	private SysUserBarMasterMapper sysUserBarMasterMapper;

    @Override
    public PageSet<SysUserBarMaster> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<SysUserBarMaster> list = sysUserBarMasterMapper.getPageSet(filterSort);
        PageInfo<SysUserBarMaster> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public SysUserBarMaster queryObject(String uuid){
		return sysUserBarMasterMapper.queryObject(uuid);
	}

	@Override
	public List<SysUserBarMaster> queryList(Map<String, Object> map){
		return sysUserBarMasterMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return sysUserBarMasterMapper.queryTotal(map);
	}

	@Override
	public int executeSave(SysUserBarMaster sysUserBarMaster){
		return sysUserBarMasterMapper.executeSave(sysUserBarMaster);
	}

	@Override
	public int executeUpdate(SysUserBarMaster sysUserBarMaster){
		return sysUserBarMasterMapper.executeUpdate(sysUserBarMaster);
	}

	@Override
	public int executeDelete(String uuid){
		return sysUserBarMasterMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return sysUserBarMasterMapper.executeDeleteBatch(uuids);
	}

	@Override
	public int queryTotalByFS(String filterSort) {
		return sysUserBarMasterMapper.queryTotalByFS(filterSort);
	}

	@Override
	public List<SysUserBarMaster> queryListByFS(String filterSort) {
		return sysUserBarMasterMapper.getPageSet(filterSort);
	}

}
