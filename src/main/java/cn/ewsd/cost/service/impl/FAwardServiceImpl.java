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

import cn.ewsd.cost.mapper.FAwardMapper;
import cn.ewsd.cost.model.FAward;
import cn.ewsd.cost.service.FAwardService;
import cn.ewsd.cost.service.impl.CostBaseServiceImpl;

@Service("fAwardServiceImpl")
public class FAwardServiceImpl extends CostBaseServiceImpl<FAward, String> implements FAwardService {
	@Autowired
	private FAwardMapper fAwardMapper;

    @Override
    public PageSet<FAward> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<FAward> list = fAwardMapper.getPageSet(filterSort);
        PageInfo<FAward> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public PageSet<FAward> getPageSetJoinAssign(PageParam pageParam, String filterSort) {
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<FAward> list = fAwardMapper.getPageSetJoinAssign(filterSort);
		PageInfo<FAward> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public FAward queryObject(String uuid){
		return fAwardMapper.queryObject(uuid);
	}

	@Override
	public FAward queryObject2(String uuid) {
		return fAwardMapper.queryObject2(uuid);
	}

	@Override
	public FAward queryObjectSum(String uuid) {
		return fAwardMapper.queryObjectSum(uuid);
	}

	@Override
	public List<FAward> queryList(Map<String, Object> map){
		return fAwardMapper.queryList(map);
	}

	@Override
	public List<FAward> queryList(String filterSort){
		return fAwardMapper.getPageSet(filterSort);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return fAwardMapper.queryTotal(map);
	}

	@Override
	public int executeSave(FAward fAward){
		return fAwardMapper.executeSave(fAward);
	}

	@Override
	public int executeUpdate(FAward fAward){
		return fAwardMapper.executeUpdate(fAward);
	}

	@Override
	public int executeUpdate2(FAward fAward) {
		return fAwardMapper.executeUpdate2(fAward);
	}

	@Override
	public int executeDelete(String uuid){
		return fAwardMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuid){
		return fAwardMapper.executeDeleteBatch(uuid);
	}

	@Override
	public List<FAward> queryListJoinAssign(String filterSort) {
		return fAwardMapper.getPageSetJoinAssign(filterSort);
	}

}
