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

import cn.ewsd.cost.mapper.FItemMapper;
import cn.ewsd.cost.model.FItem;
import cn.ewsd.cost.service.FItemService;
import cn.ewsd.cost.service.impl.CostBaseServiceImpl;

@Service("fItemServiceImpl")
public class FItemServiceImpl extends CostBaseServiceImpl<FItem, String> implements FItemService {
	@Autowired
	private FItemMapper fItemMapper;

    @Override
    public PageSet<FItem> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<FItem> list = fItemMapper.getPageSet(filterSort);
        PageInfo<FItem> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public FItem queryObject(String uuid){
		return fItemMapper.queryObject(uuid);
	}

	@Override
	public List<FItem> queryList(Map<String, Object> map){
		return fItemMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return fItemMapper.queryTotal(map);
	}

	@Override
	public int executeSave(FItem fItem){
		return fItemMapper.executeSave(fItem);
	}

	@Override
	public int executeUpdate(FItem fItem){
		return fItemMapper.executeUpdate(fItem);
	}

	@Override
	public int executeDelete(String uuid){
		return fItemMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return fItemMapper.executeDeleteBatch(uuids);
	}

}
