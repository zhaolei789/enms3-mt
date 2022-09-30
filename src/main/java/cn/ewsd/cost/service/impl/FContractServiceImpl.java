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

import cn.ewsd.cost.mapper.FContractMapper;
import cn.ewsd.cost.model.FContract;
import cn.ewsd.cost.service.FContractService;
import cn.ewsd.cost.service.impl.CostBaseServiceImpl;

@Service("fContractServiceImpl")
public class FContractServiceImpl extends CostBaseServiceImpl<FContract, String> implements FContractService {
	@Autowired
	private FContractMapper fContractMapper;

    @Override
    public PageSet<FContract> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<FContract> list = fContractMapper.getPageSet(filterSort);
        PageInfo<FContract> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public FContract queryObject(String uuid){
		return fContractMapper.queryObject(uuid);
	}

	@Override
	public List<FContract> queryList(Map<String, Object> map){
		return fContractMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return fContractMapper.queryTotal(map);
	}

	@Override
	public int executeSave(FContract fContract){
		return fContractMapper.executeSave(fContract);
	}

	@Override
	public int executeUpdate(FContract fContract){
		return fContractMapper.executeUpdate(fContract);
	}

	@Override
	public int executeDelete(String uuid){
		return fContractMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return fContractMapper.executeDeleteBatch(uuids);
	}

}
