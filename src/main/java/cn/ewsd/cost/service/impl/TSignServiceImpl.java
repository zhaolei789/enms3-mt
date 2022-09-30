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

import cn.ewsd.cost.mapper.TSignMapper;
import cn.ewsd.cost.model.TSign;
import cn.ewsd.cost.service.TSignService;
import cn.ewsd.cost.service.impl.CostBaseServiceImpl;

@Service("tSignServiceImpl")
public class TSignServiceImpl extends CostBaseServiceImpl<TSign, String> implements TSignService {
	@Autowired
	private TSignMapper tSignMapper;

    @Override
    public PageSet<TSign> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<TSign> list = tSignMapper.getPageSet(filterSort);
        PageInfo<TSign> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public TSign queryObject(String uuid){
		return tSignMapper.queryObject(uuid);
	}

	@Override
	public List<TSign> queryList(Map<String, Object> map){
		return tSignMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return tSignMapper.queryTotal(map);
	}

	@Override
	public int executeSave(TSign tSign){
		return tSignMapper.executeSave(tSign);
	}

	@Override
	public int executeUpdate(TSign tSign){
		return tSignMapper.executeUpdate(tSign);
	}

	@Override
	public int executeDelete(String uuid){
		return tSignMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return tSignMapper.executeDeleteBatch(uuids);
	}

}
