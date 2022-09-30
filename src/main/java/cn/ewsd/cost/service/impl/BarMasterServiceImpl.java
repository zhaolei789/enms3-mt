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

import cn.ewsd.cost.mapper.BarMasterMapper;
import cn.ewsd.cost.model.BarMaster;
import cn.ewsd.cost.service.BarMasterService;
import cn.ewsd.cost.service.impl.CostBaseServiceImpl;

@Service("barMasterServiceImpl")
public class BarMasterServiceImpl extends CostBaseServiceImpl<BarMaster, String> implements BarMasterService {
	@Autowired
	private BarMasterMapper barMasterMapper;

    @Override
    public PageSet<BarMaster> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<BarMaster> list = barMasterMapper.getPageSet(filterSort);
        PageInfo<BarMaster> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public PageSet<BarMaster> getPageSetYearFee(PageParam pageParam, String filterSort,String fMonth) {
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<BarMaster> list = barMasterMapper.getPageSetYearFee(filterSort,fMonth);
		PageInfo<BarMaster> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public PageSet<BarMaster> getPageSetSbCw(PageParam pageParam, String filterSort,String queryYear,String queryMonth) {
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<BarMaster> list = barMasterMapper.getPageSetSbCw(filterSort,queryYear,queryMonth);
		PageInfo<BarMaster> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public PageSet<BarMaster> getPageSetSbFs(PageParam pageParam, String filterSort,String queryYear,String queryMonth) {
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<BarMaster> list = barMasterMapper.getPageSetSbFs(filterSort,queryYear,queryMonth);
		PageInfo<BarMaster> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public PageSet<BarMaster> getPageSetSettle(PageParam pageParam, String filterSort,String queryYear,String queryMonth) {
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<BarMaster> list = barMasterMapper.getPageSetSettle(filterSort,queryYear,queryMonth);
		PageInfo<BarMaster> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public BarMaster queryObject(String uuid){
		return barMasterMapper.queryObject(uuid);
	}

	@Override
	public List<BarMaster> queryList(Map<String, Object> map){
		return barMasterMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return barMasterMapper.queryTotal(map);
	}

	@Override
	public int executeSave(BarMaster barMaster){
		return barMasterMapper.executeSave(barMaster);
	}

	@Override
	public int executeUpdate(BarMaster barMaster){
		return barMasterMapper.executeUpdate(barMaster);
	}

	@Override
	public int executeDelete(String uuid){
		return barMasterMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return barMasterMapper.executeDeleteBatch(uuids);
	}

}
