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

import cn.ewsd.cost.mapper.FAccountMapper;
import cn.ewsd.cost.model.FAccount;
import cn.ewsd.cost.service.FAccountService;
import cn.ewsd.cost.service.impl.CostBaseServiceImpl;

@Service("fAccountServiceImpl")
public class FAccountServiceImpl extends CostBaseServiceImpl<FAccount, String> implements FAccountService {
	@Autowired
	private FAccountMapper fAccountMapper;

    @Override
    public PageSet<FAccount> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<FAccount> list = fAccountMapper.getPageSet(filterSort);
        PageInfo<FAccount> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public FAccount queryObject(String uuid){
		return fAccountMapper.queryObject(uuid);
	}

	@Override
	public List<FAccount> queryList(Map<String, Object> map){
		return fAccountMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return fAccountMapper.queryTotal(map);
	}

	@Override
	public int executeSave(FAccount fAccount){
		return fAccountMapper.executeSave(fAccount);
	}

	@Override
	public int executeUpdate(FAccount fAccount){
		return fAccountMapper.executeUpdate(fAccount);
	}

	@Override
	public int executeDelete(String uuid){
		return fAccountMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return fAccountMapper.executeDeleteBatch(uuids);
	}

}
