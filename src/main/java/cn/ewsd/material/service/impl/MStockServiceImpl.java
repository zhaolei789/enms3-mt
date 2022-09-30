package cn.ewsd.material.service.impl;

import cn.ewsd.base.utils.Data;
import cn.ewsd.base.utils.DataCantainer;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.mapper.MMaterialMapper;
import cn.ewsd.material.mapper.MStockMapper;
import cn.ewsd.material.model.MMaterial;
import cn.ewsd.material.model.MStock;
import cn.ewsd.material.service.MStockService;
import cn.ewsd.repository.mapper.MInMapper;
import cn.ewsd.repository.mapper.MOutMapper;
import cn.ewsd.repository.mapper.MStoreMapper;
import cn.ewsd.repository.mapper.MTeamMatMapper;
import cn.ewsd.repository.model.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("mStockServiceImpl")
public class MStockServiceImpl extends MaterialBaseServiceImpl<MStock, String> implements MStockService {
	@Autowired
	MStockMapper mStockMapper;
	@Autowired
	MTeamMatMapper mTeamMatMapper;
	@Autowired
	MMaterialMapper mMaterialMapper;
	@Autowired
	MInMapper mInMapper;
	@Autowired
	MOutMapper mOutMapper;
	@Autowired
	MStoreMapper mStoreMapper;

	@Override
	public int getCountByMatNo(String matNo){
		return mStockMapper.getCountByMatNo(matNo);
	}

	@Override
	public List<MStock> getTgAmount(){
		return mStockMapper.getTgAmount();
	}

	@Override
	public void checkStockAmount(String matNo, String storeNo) throws Exception{
		MStock mStock = mStockMapper.getStock(storeNo, matNo);
		double inAmount = mStock == null ? 0 : mStock.getInAmount().doubleValue();
		double outAmount = mStock == null ? 0 : mStock.getOutAmount().doubleValue();
		double stockAmount = mStock == null ? 0 : mStock.getStockAmount().doubleValue();
		if(inAmount < 0){
			throw new XException(XException.ERR_DEFAULT, matNo+" "+storeNo+"数量小于零：入库数量");
		}
		if(outAmount < 0){
			throw new XException(XException.ERR_DEFAULT, matNo+" "+storeNo+"数量小于零：出库数量");
		}
		if(stockAmount < 0){
			throw new XException(XException.ERR_DEFAULT, matNo+" "+storeNo+"数量小于零：库存数量");
		}
		if(inAmount != new BigDecimal(outAmount+"").add(new BigDecimal(stockAmount+"")).doubleValue()){
			throw new XException(XException.ERR_DEFAULT, matNo+" "+storeNo+"数量不符：入库数量、出库数量、库存数量");
		}
	}

	@Override
	public MStock getStock(String matNo, String storeNo){
		return mStockMapper.getStock(storeNo, matNo);
	}

	@Override
	public PageSet<MMaterial> getStockPackMat(PageParam pageParam, String filterSort, String userId, String storeQry, String flagQry, String matQry){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MMaterial> list = mStockMapper.getStockPackMat(userId, storeQry, flagQry, matQry);
		PageInfo<MMaterial> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public List<MStock> getStockPackStore(String matNo){
		return mStockMapper.getStockPackStore(matNo);
	}

	@Transactional
	@Override
	public void saveStore(HttpServletRequest request) throws Exception{
		try{
			String matNo = request.getParameter("matNo");
			String[] storeNos = request.getParameterValues("storeNo");
			String teamNo = request.getParameter("teamNo");

//			if(storeNos==null || storeNos.length<1){
////				throw new XException(XException.ERR_DEFAULT, "请先选择仓库！");
////			}

			if(!"".equals(teamNo)){
				int c = mTeamMatMapper.updateTeamMat(teamNo, matNo);
				if(c < 1){
					MTeamMat mTeamMat = new MTeamMat();
					mTeamMat.setMatCode("");
					mTeamMat.setMatNo(matNo);
					mTeamMat.setTeamNo(Integer.parseInt(teamNo));
					mTeamMatMapper.insertTeamMat(mTeamMat);
				}
			}

			for(int i=0; storeNos!=null && i<storeNos.length; i++){
				String storeNo = storeNos[i];
				String siteCode = request.getParameter("site_"+storeNo);

				MStock mStock = new MStock();
				mStock.setSiteCode(siteCode);
				mStock.setStoreNo(storeNo);
				mStock.setMatNo(matNo);
				mStockMapper.updateStock(mStock);
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	public PageSet<MStock> getDxStockPageSet(PageParam pageParam, String filterSort, String storeNoQry, String flagQry, String matCodeQry, String matNameQry){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MStock> list = mStockMapper.getDxStockList(storeNoQry, flagQry, matCodeQry, matNameQry);
		PageInfo<MStock> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public List<MStock> getDxStockList(String storeNoQry, String flagQry, String matCodeQry, String matNameQry){
		return mStockMapper.getDxStockList(storeNoQry, flagQry, matCodeQry, matNameQry);
	}

	@Override
	public PageSet<MStock> getStockQryPageSet(PageParam pageParam, String filterSort, String storeNoQry, String abcTypeQry, String flagQry, boolean ifPower, String matCodeQry, String erpTypeQry, String matNameQry, String warnFlag) {
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MStock> list = mStockMapper.getStockQryList(storeNoQry, abcTypeQry, flagQry, ifPower, matCodeQry, erpTypeQry, matNameQry, warnFlag);

		List<MMaterial> oldList = mMaterialMapper.getOldMatList();
		DataCantainer<MMaterial> oldDc = new DataCantainer<>((ArrayList)oldList);
		for(int i=0; i<list.size(); i++){
			MStock mStock = list.get(i);
			String matCode = mStock.getMatCode();
			if("X".equals(matCode.substring(0, 1))){
				matCode = matCode.substring(1);
			}
			DataCantainer<MMaterial> d = oldDc.findDataCantainer("erp_code", matCode);
			if(d.getRowCount()>0){
				mStock.setOldCode(d.getRow(0).getMatCode());
			}else{
				mStock.setOldCode("");
			}
		}

		PageInfo<MStock> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public List<MStock> getStockQryList(String storeNoQry, String abcTypeQry, String flagQry, boolean ifPower, String matCodeQry, String erpTypeQry, String matNameQry, String warnFlag){
		return mStockMapper.getStockQryList(storeNoQry, abcTypeQry, flagQry, ifPower, matCodeQry, erpTypeQry, matNameQry, warnFlag);
	}

	@Override
	public List<StockQryDetail> getStockQryDetail(String matNo, String date1Qry, String date2Qry, String storeNoQry, boolean ifPower){
		List<StockQryDetail> list = new ArrayList<>();

		List<MIn> inList = mInMapper.getStockInDetail(matNo, date1Qry, date2Qry, storeNoQry, ifPower);
		List<MOut> outList = mOutMapper.getStockOutDetail(matNo, date1Qry, date2Qry, storeNoQry, ifPower);

		int inCount = inList.size();
		int outCount = outList.size();
		int count = inCount>outCount ? inCount : outCount;

		for(int i=0; i<count; i++){
			StockQryDetail stockQryDetail = new StockQryDetail();
			if(i>=inCount){
				stockQryDetail.setStoreName("");
				stockQryDetail.setBillDate("");
				stockQryDetail.setInEmpName("");
				stockQryDetail.setBillAmount(new BigDecimal("0"));
				stockQryDetail.setSetPrice(new BigDecimal("0"));
				stockQryDetail.setBala(new BigDecimal("0"));
			}else{
				MIn mIn = inList.get(i);
				stockQryDetail.setStoreName(mIn.getStoreName());
				stockQryDetail.setBillDate(XDate.dateTo10(mIn.getBillDate()));
				stockQryDetail.setInEmpName(mIn.getInEmpName());
				stockQryDetail.setBillAmount(mIn.getBillAmount());
				stockQryDetail.setSetPrice(mIn.getSetPrice());
				stockQryDetail.setBala(mIn.getBala());
			}
			if(i>=outCount){
				stockQryDetail.setTeamName("");
				stockQryDetail.setStoreName1("");
				stockQryDetail.setOutDate("");
				stockQryDetail.setEmpName("");
				stockQryDetail.setDrawEmpName("");
				stockQryDetail.setOutAmount(new BigDecimal("0"));
				stockQryDetail.setMatPrice(new BigDecimal("0"));
				stockQryDetail.setOutBala(new BigDecimal("0"));
			}else{
				MOut mOut = outList.get(i);
				stockQryDetail.setTeamName(mOut.getTeamName());
				stockQryDetail.setStoreName1(mOut.getStoreName());
				stockQryDetail.setOutDate(XDate.dateTo10(mOut.getOutDate()));
				stockQryDetail.setEmpName(mOut.getEmpName());
				stockQryDetail.setDrawEmpName(mOut.getDrawEmpName());
				stockQryDetail.setOutAmount(mOut.getOutAmount());
				stockQryDetail.setMatPrice(mOut.getMatPrice());
				stockQryDetail.setOutBala(mOut.getBala());
			}
			list.add(stockQryDetail);
		}

		return list;
	}

	@Override
	public PageSet<MStock> getOldApplyList(PageParam pageParam, String filterSort, String storeQry, String matQry) {
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MStock> list = mStockMapper.getOldApplyList(storeQry, matQry);
		PageInfo<MStock> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public PageSet<MMaterial> getUrgentPlanStock(PageParam pageParam, String filterSort, String teamNo, String planMonth, String storeQry, String reserveQry, String matQry) {
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MMaterial> list = mStockMapper.getUrgentPlanStock(teamNo, planMonth, storeQry, reserveQry, matQry);
		PageInfo<MMaterial> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public PageSet<MStock> getWarnStockPageSet(PageParam pageParam, String filterSort, String storeQry, String flagQry, String codeQry, String nameQry){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MStock> list = mStockMapper.getWarnStock("yellow".equals(flagQry) ? "max_amount" : "red_warn", storeQry, flagQry, codeQry, nameQry);
		PageInfo<MStock> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public List<MStock> getWarnStockList(String storeQry, String flagQry, String codeQry, String nameQry){
		return mStockMapper.getWarnStock("yellow".equals(flagQry) ? "max_amount" : "red_warn", storeQry, flagQry, codeQry, nameQry);
	}

	@Override
	public PageSet<MStock> getInOutStockPageSet(PageParam pageParam, String filterSort, String userId, String storeQry, boolean ifPower, String beginDateQry, String endDateQry, String matCodeQry, String matNameQry){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MStock> list = mStockMapper.getInOutStock(userId, storeQry, ifPower, beginDateQry, endDateQry, matCodeQry, matNameQry);

		for(int i=0; i<list.size(); i++){
			MStock mStock = list.get(i);
			mStock.setQqAmountText(Data.trimDoubleNo0(mStock.getQqAmount().doubleValue() ,4));
			mStock.setQqBalaText(Data.normalToFinal(Data.trimDoubleNo0(mStock.getQqBala().doubleValue() ,2)));
			mStock.setBqrAmountText(Data.trimDoubleNo0(mStock.getBqrAmount().doubleValue() ,4));
			mStock.setBqrBalaText(Data.normalToFinal(Data.trimDoubleNo0(mStock.getBqrBala().doubleValue() ,2)));
			mStock.setBqcAmountText(Data.trimDoubleNo0(mStock.getBqcAmount().doubleValue() ,4));
			mStock.setBqcBalaText(Data.normalToFinal(Data.trimDoubleNo0(mStock.getBqcBala().doubleValue() ,2)));
			mStock.setJyAmountText(Data.trimDoubleNo0(mStock.getJyAmount().doubleValue() ,4));
			mStock.setJyBalaText(Data.normalToFinal(Data.trimDoubleNo0(mStock.getJyBala().doubleValue() ,2)));
		}

		PageInfo<MStock> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public List<MStock> getInOutStockList(String userId, String storeQry, boolean ifPower, String beginDateQry, String endDateQry, String matCodeQry, String matNameQry){
		List<MStock> list = mStockMapper.getInOutStock(userId, storeQry, ifPower, beginDateQry, endDateQry, matCodeQry, matNameQry);

		for(int i=0; i<list.size(); i++){
			MStock mStock = list.get(i);
			mStock.setQqAmountText(Data.trimDoubleNo0(mStock.getQqAmount().doubleValue() ,4));
			mStock.setQqBalaText(Data.normalToFinal(Data.trimDoubleNo0(mStock.getQqBala().doubleValue() ,2)));
			mStock.setBqrAmountText(Data.trimDoubleNo0(mStock.getBqrAmount().doubleValue() ,4));
			mStock.setBqrBalaText(Data.normalToFinal(Data.trimDoubleNo0(mStock.getBqrBala().doubleValue() ,2)));
			mStock.setBqcAmountText(Data.trimDoubleNo0(mStock.getBqcAmount().doubleValue() ,4));
			mStock.setBqcBalaText(Data.normalToFinal(Data.trimDoubleNo0(mStock.getBqcBala().doubleValue() ,2)));
			mStock.setJyAmountText(Data.trimDoubleNo0(mStock.getJyAmount().doubleValue() ,4));
			mStock.setJyBalaText(Data.normalToFinal(Data.trimDoubleNo0(mStock.getJyBala().doubleValue() ,2)));
		}

		return list;
	}

	@Override
	public PageSet<MStock> getAllStockQryPageSet(PageParam pageParam, String filterSort, String storeNoQry, String stLevelQry, boolean ifPower, String matCodeQry, String erpTypeQry, String matNameQry){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MStock> list = mStockMapper.getAllStockQryList(storeNoQry, stLevelQry, ifPower, matCodeQry, erpTypeQry, matNameQry);
		PageInfo<MStock> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public List<MStock> getAllStockQryList(String storeNoQry, String stLevelQry, boolean ifPower, String matCodeQry, String erpTypeQry, String matNameQry){
		return mStockMapper.getAllStockQryList(storeNoQry, stLevelQry, ifPower, matCodeQry, erpTypeQry, matNameQry);
	}

	@Override
	public PageSet<HashMap<String, Object>> getStockSumPageSet(PageParam pageParam, String filterSort, String matCodeQry, String matNameQry, String xjFlagQry, boolean ifPower){
		try{
			List<MStock> list = mStockMapper.getStockSumList(matCodeQry, matNameQry, xjFlagQry, ifPower);
			DataCantainer<MStock> dc = new DataCantainer<>((ArrayList)list);
			List<MStore> storeList = mStoreMapper.getAllStockQryStore("r.storeLevel.1", ifPower);

			PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
			List<HashMap<String, Object>> matList = mStockMapper.getStockSumMat(matCodeQry, matNameQry, xjFlagQry, ifPower);

			for(int i=0; i<matList.size(); i++){
				HashMap<String, Object> map = matList.get(i);
				String matNo = String.valueOf(map.get("mat_no"));
				map.put("sumAmount", dc.findDataCantainer("mat_no", matNo).getSum("stock_amount"));
				map.put("sumBala", Data.normalToFinal(dc.findDataCantainer("mat_no", matNo).getSum("mat_bala")));
				for(int j=0; j<storeList.size(); j++){
					String storeNo = storeList.get(j).getStoreNo();
					map.put(storeNo+"Amt", dc.findDataCantainer("store_no", storeNo, "mat_no", matNo).getSum("stock_amount"));
					map.put(storeNo+"Bala", Data.normalToFinal(dc.findDataCantainer("store_no", storeNo, "mat_no", matNo).getSum("mat_bala")));
				}
			}

			HashMap<String, Object> sumRow = new HashMap<>();
			sumRow.put("mat_code", "合计");
			sumRow.put("sumBala", Data.normalToFinal(dc.getSum("mat_bala")));
			for(int i=0; i<storeList.size(); i++){
				String storeNo = storeList.get(i).getStoreNo();
				sumRow.put(storeNo+"Bala", Data.normalToFinal(dc.findDataCantainer("store_no", storeNo).getSum("mat_bala")));
			}
			matList.add(sumRow);

			PageInfo<HashMap<String, Object>> pageInfo = new PageInfo<>(matList);
			return PageUtils.getPageSet(pageInfo);
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getStockSumList(String matCodeQry, String matNameQry, String xjFlagQry, boolean ifPower){
		try{
			List<HashMap<String, Object>> matList = mStockMapper.getStockSumMat(matCodeQry, matNameQry, xjFlagQry, ifPower);
			List<MStock> list = mStockMapper.getStockSumList(matCodeQry, matNameQry, xjFlagQry, ifPower);
			DataCantainer<MStock> dc = new DataCantainer<>((ArrayList)list);
			List<MStore> storeList = mStoreMapper.getAllStockQryStore("r.storeLevel.1", ifPower);

			for(int i=0; i<matList.size(); i++){
				HashMap<String, Object> map = matList.get(i);

				String matNo = String.valueOf(map.get("mat_no"));
				map.put("sumAmount", dc.findDataCantainer("mat_no", matNo).getSum("stock_amount"));
				map.put("sumBala", Data.normalToFinal(dc.findDataCantainer("mat_no", matNo).getSum("mat_bala")));
				for(int j=0; j<storeList.size(); j++){
					String storeNo = storeList.get(j).getStoreNo();
					map.put(storeNo+"Amt", dc.findDataCantainer("store_no", storeNo, "mat_no", matNo).getSum("stock_amount"));
					map.put(storeNo+"Bala", Data.normalToFinal(dc.findDataCantainer("store_no", storeNo, "mat_no", matNo).getSum("mat_bala")));
				}
			}

			HashMap<String, Object> sumRow = new HashMap<>();
			sumRow.put("mat_code", "合计");
			sumRow.put("sumBala", Data.normalToFinal(dc.getSum("mat_bala")));
			for(int i=0; i<storeList.size(); i++){
				String storeNo = storeList.get(i).getStoreNo();
				sumRow.put(storeNo+"Bala", Data.normalToFinal(dc.findDataCantainer("store_no", storeNo).getSum("mat_bala")));
			}
			matList.add(sumRow);

			return matList;
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public PageSet<MStock> getStockSumDetailPageSet(PageParam pageParam, String filterSort, String matCodeQry, String matNameQry, String xjFlagQry, String storeNoQry){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MStock> list = mStockMapper.getStockSumDetailList(matCodeQry, matNameQry, xjFlagQry, storeNoQry);
		PageInfo<MStock> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public PageSet<MStock> getExpirationAlertPageSet(PageParam pageParam, String filterSort, String matQry){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MStock> list = mStockMapper.getExpirationAlertList(matQry, XDate.addDate(XDate.getDate(), 30));
		PageInfo<MStock> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}
}
