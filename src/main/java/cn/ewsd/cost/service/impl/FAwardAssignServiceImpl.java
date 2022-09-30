package cn.ewsd.cost.service.impl;

import cn.ewsd.cost.model.FAwardAssignSelect_A;
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

import cn.ewsd.cost.mapper.FAwardAssignMapper;
import cn.ewsd.cost.model.FAwardAssign;
import cn.ewsd.cost.service.FAwardAssignService;
import cn.ewsd.cost.service.impl.CostBaseServiceImpl;

@Service("fAwardAssignServiceImpl")
public class FAwardAssignServiceImpl extends CostBaseServiceImpl<FAwardAssign, String> implements FAwardAssignService {
	@Autowired
	private FAwardAssignMapper fAwardAssignMapper;

    @Override
    public PageSet<FAwardAssign> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<FAwardAssign> list = fAwardAssignMapper.getPageSet(filterSort);
        PageInfo<FAwardAssign> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public FAwardAssign queryObject(String uuid){
		return fAwardAssignMapper.queryObject(uuid);
	}

	@Override
	public List<FAwardAssign> queryList(Map<String, Object> map){
		return fAwardAssignMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return fAwardAssignMapper.queryTotal(map);
	}

	@Override
	public int executeSave(FAwardAssign fAwardAssign){
		return fAwardAssignMapper.executeSave(fAwardAssign);
	}

	@Override
	public int executeUpdate(FAwardAssign fAwardAssign){
		return fAwardAssignMapper.executeUpdate(fAwardAssign);
	}

	@Override
	public int executeDelete(String uuid){
		return fAwardAssignMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuid){
		return fAwardAssignMapper.executeDeleteBatch(uuid);
	}

	@Override
	public List<FAwardAssign> queryListByFilterSort(String filterSort) {
		return fAwardAssignMapper.getPageSet(filterSort);
	}

	@Override
	public List<FAwardAssignSelect_A> queryListByLeadExcel(String filterSort) {
		return fAwardAssignMapper.queryListByLeadExcel(filterSort);
	}

}
