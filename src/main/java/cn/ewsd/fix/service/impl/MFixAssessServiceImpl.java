package cn.ewsd.fix.service.impl;

import cn.ewsd.mdata.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import cn.ewsd.fix.mapper.MFixAssessMapper;
import cn.ewsd.fix.model.MFixAssess;
import cn.ewsd.fix.service.MFixAssessService;

@Service("mFixAssessServiceImpl")
public class MFixAssessServiceImpl extends FixBaseServiceImpl<MFixAssess, String> implements MFixAssessService {
	@Autowired
	private MFixAssessMapper mFixAssessMapper;

	@Override
	public MFixAssess getFixAssess(String assType, String assMonth, String mngTeam, String teamNo, String prjNo){
		return mFixAssessMapper.getFixAssess(assType, assMonth, mngTeam, teamNo, prjNo);
	}

	@Override
	public List<Organization> getFixAssessTeam(String assMonth, String userTeam){
		return mFixAssessMapper.getFixAssessTeam(assMonth, userTeam);
	}
	@Override
	public List<Organization> getMngTeam(){
		return mFixAssessMapper.getMngTeam();
	}

}
