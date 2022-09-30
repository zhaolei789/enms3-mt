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

import cn.ewsd.cost.mapper.MWorkMapper;
import cn.ewsd.cost.model.MWork;
import cn.ewsd.cost.service.MWorkService;
import cn.ewsd.cost.service.impl.CostBaseServiceImpl;

@Service("mWorkServiceImpl")
public class MWorkServiceImpl extends CostBaseServiceImpl<MWork, String> implements MWorkService {
	@Autowired
	private MWorkMapper mWorkMapper;

    @Override
    public PageSet<MWork> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<MWork> list = mWorkMapper.getPageSet(filterSort);
        PageInfo<MWork> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public MWork queryObject(String uuid){
		return mWorkMapper.queryObject(uuid);
	}

	@Override
	public List<MWork> queryList(Map<String, Object> map){
		return mWorkMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return mWorkMapper.queryTotal(map);
	}

	@Override
	public int executeSave(MWork mWork){
		return mWorkMapper.executeSave(mWork);
	}

	@Override
	public int executeUpdate(MWork mWork){
		return mWorkMapper.executeUpdate(mWork);
	}

	@Override
	public int executeDelete(String uuid){
		return mWorkMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return mWorkMapper.executeDeleteBatch(uuids);
	}

	@Override
	public List<MWork> getRationAnalWorkList(){
    	return mWorkMapper.getRationAnalWorkList();
	}
}
