package cn.ewsd.material.service.impl;

import cn.ewsd.base.utils.XDate;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.material.mapper.MTeamStockMapper;
import cn.ewsd.material.model.*;
import cn.ewsd.material.service.MTeamStockService;
import cn.ewsd.repository.mapper.MInMapper;
import cn.ewsd.repository.mapper.MOutMapper;
import cn.ewsd.repository.mapper.MTeamBillMapper;
import cn.ewsd.repository.model.MIn;
import cn.ewsd.repository.model.MOut;
import cn.ewsd.repository.model.MTeamBill;
import cn.ewsd.repository.model.StockQryDetail;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("mTeamStockServiceImpl")
public class MTeamStockServiceImpl extends MaterialBaseServiceImpl<MTeamStock, String> implements MTeamStockService {
	@Autowired
	private MTeamStockMapper mTeamStockMapper;
	@Autowired
	private MInMapper mInMapper;
	@Autowired
	private MTeamBillMapper mTeamBillMapper;

	@Override
	public int getCountByMatNo(String matNo){
		return mTeamStockMapper.getCountByMatNo(matNo);
	}

	@Override
	public PageSet<MTeamStock> getTeamStockQryPageSet(PageParam pageParam, String filterSort, String userDeptIds, String teamNoQry, String storeNoQry, String matCodeQry, String matNameQry) {
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MTeamStock> list = mTeamStockMapper.getTeamStockQryList(userDeptIds, teamNoQry, storeNoQry, matCodeQry, matNameQry);
		PageInfo<MTeamStock> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public List<MTeamStock> getTeamStockQryList(String userDeptIds, String teamNoQry, String storeNoQry, String matCodeQry, String matNameQry){
		return mTeamStockMapper.getTeamStockQryList(userDeptIds, teamNoQry, storeNoQry, matCodeQry, matNameQry);
	}

	@Override
	public List<StockQryDetail> getTeamStockQryDetail(String matNo, String storeNo, String bDateQry, String eDateQry, String teamNoQry){
		List<StockQryDetail> list = new ArrayList<>();

		List<MIn> inList = mInMapper.getTeamStockInDetail(matNo, storeNo, bDateQry, eDateQry, teamNoQry);
		List<MTeamBill> outList = mTeamBillMapper.getTeamStockOutDetail(matNo, bDateQry, eDateQry, teamNoQry);

		int inCount = inList.size();
		int outCount = outList.size();
		int count = inCount>outCount ? inCount : outCount;

		for(int i=0; i<count; i++){
			StockQryDetail stockQryDetail = new StockQryDetail();
			if(i>=inCount){
				stockQryDetail.setStoreName("");
				stockQryDetail.setInType("");
				stockQryDetail.setInDate("");
				stockQryDetail.setAmount(new BigDecimal("0"));
				stockQryDetail.setPrice(new BigDecimal("0"));
				stockQryDetail.setInBala(new BigDecimal("0"));
			}else{
				MIn mIn = inList.get(i);
				stockQryDetail.setStoreName(mIn.getStoreName());
				stockQryDetail.setInType(mIn.getInType());
				stockQryDetail.setInDate(XDate.dateTo10(mIn.getInDate()));
				stockQryDetail.setAmount(mIn.getAmount());
				stockQryDetail.setPrice(mIn.getPrice());
				stockQryDetail.setInBala(mIn.getAmount().multiply(mIn.getPrice()));
			}
			if(i>=outCount){
				stockQryDetail.setTeamName("");
				stockQryDetail.setPrjName("");
				stockQryDetail.setOccDate("");
				stockQryDetail.setOccAmount(new BigDecimal(0));
				stockQryDetail.setMatPrice(new BigDecimal(0));
				stockQryDetail.setOutBala(new BigDecimal("0"));
			}else{
				MTeamBill mTeamBill = outList.get(i);
				stockQryDetail.setTeamName(mTeamBill.getDeptName());
				stockQryDetail.setPrjName(mTeamBill.getPrjName());
				stockQryDetail.setOccDate(XDate.dateTo10(mTeamBill.getOccDate()));
				stockQryDetail.setOccAmount(mTeamBill.getOccAmount());
				stockQryDetail.setMatPrice(mTeamBill.getMatPrice());
				stockQryDetail.setOutBala(mTeamBill.getOccAmount().multiply(mTeamBill.getMatPrice()));
			}
			list.add(stockQryDetail);
		}

		return list;
	}

	@Override
	public List<MTeamStock> getTeamStockQryMatList(String matNo, String userDeptIds, String storeNo, String teamNo){
		return mTeamStockMapper.getTeamStockQryMatList(matNo, userDeptIds, storeNo, teamNo);
	}
}
