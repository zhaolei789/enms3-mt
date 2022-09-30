package cn.ewsd.fix.service.impl;

import cn.ewsd.fix.mapper.OtherModuleMapper;
import cn.ewsd.fix.model.MBackPlan;
import cn.ewsd.fix.service.OtherModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("otherModuleServiceImpl")
public class OtherModuleServiceImpl extends FixBaseServiceImpl<MBackPlan, String> implements OtherModuleService {
	@Autowired
	private OtherModuleMapper otherModuleMapper;

	@Override
	public MBackPlan queryObject(String uuid){
		return otherModuleMapper.queryObject(uuid);
	}

	@Override
	public List<MBackPlan> queryList(String var1){
		return otherModuleMapper.queryList(var1);
	}


}
