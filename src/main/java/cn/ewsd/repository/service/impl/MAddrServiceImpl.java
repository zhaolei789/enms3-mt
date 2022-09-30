package cn.ewsd.repository.service.impl;

import cn.ewsd.repository.mapper.*;
import cn.ewsd.repository.model.*;
import cn.ewsd.repository.service.MAddrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("mAddrServiceImpl")
public class MAddrServiceImpl extends RepositoryBaseServiceImpl<MAddr, String> implements MAddrService {
	@Autowired
	private MAddrMapper mAddrMapper;

	@Override
	public List<MAddr> getAddr(String teamNo){
    	return mAddrMapper.getAddr(teamNo);
	}
}
