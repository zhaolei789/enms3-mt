package cn.ewsd.repository.service.impl;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.repository.mapper.*;
import cn.ewsd.repository.model.*;
import cn.ewsd.repository.service.MTeamBillService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("mTeamBillServiceImpl")
public class MTeamBillServiceImpl extends RepositoryBaseServiceImpl<MTeamBill, String> implements MTeamBillService {
	@Autowired
	private MTeamBillMapper mTeamBillMapper;

	@Override
	public PageSet<MTeamBill> getClassMatQryPageSet(PageParam pageParam, String filterSort, String date1Qry, String date2Qry, String teamNoQry, String deptNoQry, String typeQry, String prjNameQry, String matNameQry) {
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MTeamBill> list = mTeamBillMapper.getClassMatQryList(date1Qry, date2Qry, teamNoQry, deptNoQry, typeQry, prjNameQry, matNameQry);
		PageInfo<MTeamBill> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public List<MTeamBill> getClassMatQryList(String date1Qry, String date2Qry, String teamNoQry, String deptNoQry, String typeQry, String prjNameQry, String matNameQry){
		return mTeamBillMapper.getClassMatQryList(date1Qry, date2Qry, teamNoQry, deptNoQry, typeQry, prjNameQry, matNameQry);
	}
}
