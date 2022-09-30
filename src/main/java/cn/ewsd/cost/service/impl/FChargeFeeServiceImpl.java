package cn.ewsd.cost.service.impl;

import cn.ewsd.base.utils.Data;
import cn.ewsd.base.utils.DataCantainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.ewsd.cost.mapper.FChargeFeeMapper;
import cn.ewsd.cost.model.FChargeFee;
import cn.ewsd.cost.service.FChargeFeeService;
import cn.ewsd.cost.service.impl.CostBaseServiceImpl;

@Service("fChargeFeeServiceImpl")
public class FChargeFeeServiceImpl extends CostBaseServiceImpl<FChargeFee, String> implements FChargeFeeService {
	@Autowired
	private FChargeFeeMapper fChargeFeeMapper;

    @Override
    public PageSet<FChargeFee> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<FChargeFee> list = fChargeFeeMapper.getPageSet(filterSort);
        PageInfo<FChargeFee> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public PageSet<FChargeFee> getPageSetSbCw(PageParam pageParam, String filterSort,String queryMonth) {
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<FChargeFee> list = fChargeFeeMapper.getPageSetSbCw(filterSort,queryMonth);
		PageInfo<FChargeFee> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public PageSet<FChargeFee> getPageSetSbFs(PageParam pageParam, String filterSort,String queryMonth) {
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<FChargeFee> list = fChargeFeeMapper.getPageSetSbFs(filterSort,queryMonth);
		PageInfo<FChargeFee> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public PageSet<FChargeFee> getPageSetSettle(PageParam pageParam, String filterSort,String fMonth) {
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<FChargeFee> list = fChargeFeeMapper.getPageSetSettle(filterSort,fMonth);
		PageInfo<FChargeFee> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public List<FChargeFee> getSettleList(String filterSort, String fMonth){
    	return fChargeFeeMapper.getPageSetSettle(filterSort, fMonth);
	}

	@Override
	public List<FChargeFee> getSettleSum(String year){
    	return fChargeFeeMapper.getSettleSum(year);
	}

	@Override
	public FChargeFee queryObject(String uuid){
		return fChargeFeeMapper.queryObject(uuid);
	}

	@Override
	public List<FChargeFee> queryList(Map<String, Object> map){
		return fChargeFeeMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return fChargeFeeMapper.queryTotal(map);
	}

	@Override
	public int executeSave(FChargeFee fChargeFee){
		return fChargeFeeMapper.executeSave(fChargeFee);
	}

	@Override
	public int executeUpdate(FChargeFee fChargeFee){
		return fChargeFeeMapper.executeUpdate(fChargeFee);
	}

	@Override
	public int executeDelete(String uuid){
		return fChargeFeeMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return fChargeFeeMapper.executeDeleteBatch(uuids);
	}

	@Override
	public List<FChargeFee> getLeaderSumList(String userId, String yearQry, String month1, String month2){
    	List<FChargeFee> yearList = fChargeFeeMapper.getLeaderSumYearBudList(yearQry, userId);
    	DataCantainer<FChargeFee> yearDc = new DataCantainer<>((ArrayList)yearList);
    	List<FChargeFee> monthList = fChargeFeeMapper.getLeaderSumMonthBudList(month1, month2);
		DataCantainer<FChargeFee> monthDc = new DataCantainer<>((ArrayList)monthList);
    	List<FChargeFee> ljfsList = fChargeFeeMapper.getLeaderSumLJFS(month1, month2, yearQry);
    	DataCantainer<FChargeFee> ljfsDc = new DataCantainer<>((ArrayList)ljfsList);

    	for(int i=0; i<yearList.size(); i++){
    		FChargeFee fChargeFee = yearList.get(i);
    		String leader = fChargeFee.getLeader();
    		double yearBud = fChargeFee.getYearBud().doubleValue();
    		double monthBala = monthDc.findDataCantainer("leader", leader).getSum("month_bud");
    		double assessBala = monthDc.findDataCantainer("leader", leader).getSum("assess_bala");
    		double diffBala = monthBala - assessBala;
			double diffScale = (monthBala==0) ? 0 : (diffBala / monthBala)*100;
			double sumOcc = ljfsDc.findDataCantainer("leader", leader).getSum("bala");
			double leftBala = yearBud - sumOcc;
			double leftScale = (yearBud==0) ? 0 : (leftBala / yearBud)*100;
    		fChargeFee.setYearBudText(Data.normalToFinal(Data.trimDoubleNo0(yearBud, 2)));
			fChargeFee.setMonthBalaText(Data.normalToFinal(Data.trimDoubleNo0(monthBala, 2)));
			fChargeFee.setAssessBalaText(Data.normalToFinal(Data.trimDoubleNo0(assessBala, 2)));
			fChargeFee.setDiffBalaText(Data.normalToFinal(Data.trimDoubleNo0(diffBala, 2)));
			fChargeFee.setDiffScaleText(Data.trimDoubleNo0(diffScale, 0) + "%");
			fChargeFee.setSumOccText(Data.normalToFinal(Data.trimDoubleNo0(sumOcc, 2)));
			fChargeFee.setLeftBalaText(Data.normalToFinal(Data.trimDoubleNo0(leftBala, 2)));
			fChargeFee.setLeftScaleText(Data.trimDoubleNo0(leftScale, 0) + "%");
		}

		double sumYearBud = yearDc.getSum("year_bud");
		double sumMonthBala = monthDc.getSum("month_bud");
		double sumAssessBala = monthDc.getSum("assess_bala");
		double sumDiffBala = sumMonthBala - sumAssessBala;
		double sumDiffScale = (sumMonthBala==0) ? 0 : (sumDiffBala / sumMonthBala)*100;
		double sumHjOcc = ljfsDc.getSum("bala");
		double sumLeftBala = sumYearBud - sumHjOcc;
		double sumLeftScale = (sumYearBud==0) ? 0 : (sumLeftBala / sumYearBud)*100;
    	FChargeFee fChargeFee = new FChargeFee();
    	fChargeFee.setLeaderName("合计");
		fChargeFee.setYearBudText(Data.normalToFinal(Data.trimDoubleNo0(sumYearBud, 2)));
		fChargeFee.setMonthBalaText(Data.normalToFinal(Data.trimDoubleNo0(sumMonthBala, 2)));
		fChargeFee.setAssessBalaText(Data.normalToFinal(Data.trimDoubleNo0(sumAssessBala, 2)));
		fChargeFee.setDiffBalaText(Data.normalToFinal(Data.trimDoubleNo0(sumDiffBala, 2)));
		fChargeFee.setDiffScaleText(Data.trimDoubleNo0(sumDiffScale, 0) + "%");
		fChargeFee.setSumOccText(Data.normalToFinal(Data.trimDoubleNo0(sumHjOcc, 2)));
		fChargeFee.setLeftBalaText(Data.normalToFinal(Data.trimDoubleNo0(sumLeftBala, 2)));
		fChargeFee.setLeftScaleText(Data.trimDoubleNo0(sumLeftScale, 0) + "%");
		yearList.add(fChargeFee);

		return yearList;
	}

	@Override
	public List<FChargeFee> getLeaderSumDetailList(String yearQry, String month1, String month2, String leader){
    	List<FChargeFee> yearList = fChargeFeeMapper.getLeaderSumDetailYearList(yearQry, leader);
		DataCantainer<FChargeFee> yearDc = new DataCantainer<>((ArrayList)yearList);
    	List<FChargeFee> monthList = fChargeFeeMapper.getLeaderSumDetailMonthList(month1, month2, leader);
		DataCantainer<FChargeFee> monthDc = new DataCantainer<>((ArrayList)monthList);
    	List<FChargeFee> ljfsList = fChargeFeeMapper.getLeaderSumDetailMonthList(yearQry+"01", month2, leader);
    	DataCantainer<FChargeFee> ljfsDc = new DataCantainer<>((ArrayList)ljfsList);

    	for(int i=0; i<yearList.size(); i++){
    		FChargeFee fChargeFee = yearList.get(i);
    		String teamNo = fChargeFee.getTeamNo();
    		double yearBud = fChargeFee.getYearBud().doubleValue();
			double sumOcc = ljfsDc.findDataCantainer( "team_no", teamNo).getSum("month_bala");
			double leftBala = yearBud - sumOcc;
			double sumFinance = ljfsDc.findDataCantainer("team_no", teamNo).getSum("finance_bala");
			double monthBala = monthDc.findDataCantainer("team_no", teamNo).getSum("month_bala");
			double financeBala = monthDc.findDataCantainer("team_no", teamNo).getSum("finance_bala");
			fChargeFee.setYearBudText(Data.normalToFinal(Data.trimDoubleNo0(yearBud, 2)));
			fChargeFee.setSumOccText(Data.normalToFinal(Data.trimDoubleNo0(sumOcc, 2)));
			fChargeFee.setLeftBalaText(Data.normalToFinal(Data.trimDoubleNo0(leftBala, 2)));
			fChargeFee.setSumFinance(Data.normalToFinal(Data.trimDoubleNo0(sumFinance, 2)));
			fChargeFee.setMonthBalaText(Data.normalToFinal(Data.trimDoubleNo0(monthBala, 2)));
			fChargeFee.setFinanceBalaText(Data.normalToFinal(Data.trimDoubleNo0(financeBala, 2)));
			fChargeFee.setLeader(leader);
		}

    	FChargeFee fChargeFee = new FChargeFee();
    	fChargeFee.setTeamName("合计");
    	double sumYearBud = yearDc.getSum("year_bud");
    	double sumMonthBala = ljfsDc.getSum("month_bala");
		fChargeFee.setYearBudText(Data.normalToFinal(Data.trimDoubleNo0(sumYearBud, 2)));
		fChargeFee.setSumOccText(Data.normalToFinal(Data.trimDoubleNo0(sumMonthBala, 2)));
		fChargeFee.setLeftBalaText(Data.normalToFinal(Data.trimDoubleNo0(sumYearBud-sumMonthBala, 2)));
		fChargeFee.setSumFinance(Data.normalToFinal(Data.trimDoubleNo0(ljfsDc.getSum("finance_bala"), 2)));
		fChargeFee.setMonthBalaText(Data.normalToFinal(Data.trimDoubleNo0(monthDc.getSum("month_bala"), 2)));
		fChargeFee.setFinanceBalaText(Data.normalToFinal(Data.trimDoubleNo0(monthDc.getSum("finance_bala"), 2)));
		yearList.add(fChargeFee);

		return yearList;
	}

	@Override
	public List<FChargeFee> getLeaderSumDeptList(String yearQry, String month1, String month2, String leader, String teamNo){
		List<FChargeFee> yearList = fChargeFeeMapper.getLeaderSumDeptYearList(yearQry, leader, teamNo);
		DataCantainer<FChargeFee> yearDc = new DataCantainer<>((ArrayList)yearList);
		List<FChargeFee> monthList = fChargeFeeMapper.getLeaderSumDeptMonthList(month1, month2, leader, teamNo);
		DataCantainer<FChargeFee> monthDc = new DataCantainer<>((ArrayList)monthList);
		List<FChargeFee> ljfsList = fChargeFeeMapper.getLeaderSumDeptMonthList(yearQry+"01", month2, leader, teamNo);
		DataCantainer<FChargeFee> ljfsDc = new DataCantainer<>((ArrayList)ljfsList);

		for(int i=0; i<yearList.size(); i++){
			FChargeFee fChargeFee = yearList.get(i);
			String itemId = fChargeFee.getItemId();
			double yearBud = fChargeFee.getYearBud().doubleValue();
			double sumOcc = ljfsDc.findDataCantainer( "item_id", itemId).getSum("month_bala");
			double leftBala = yearBud - sumOcc;
			double sumFinance = ljfsDc.findDataCantainer("item_id", itemId).getSum("finance_bala");
			double monthBala = monthDc.findDataCantainer("item_id", itemId).getSum("month_bala");
			double financeBala = monthDc.findDataCantainer("item_id", itemId).getSum("finance_bala");
			fChargeFee.setYearBudText(Data.normalToFinal(Data.trimDoubleNo0(yearBud, 2)));
			fChargeFee.setSumOccText(Data.normalToFinal(Data.trimDoubleNo0(sumOcc, 2)));
			fChargeFee.setLeftBalaText(Data.normalToFinal(Data.trimDoubleNo0(leftBala, 2)));
			fChargeFee.setSumFinance(Data.normalToFinal(Data.trimDoubleNo0(sumFinance, 2)));
			fChargeFee.setMonthBalaText(Data.normalToFinal(Data.trimDoubleNo0(monthBala, 2)));
			fChargeFee.setFinanceBalaText(Data.normalToFinal(Data.trimDoubleNo0(financeBala, 2)));
		}

		FChargeFee fChargeFee = new FChargeFee();
		fChargeFee.setItemName("合计");
		fChargeFee.setTeamName("");
		fChargeFee.setCalcWayName("");
		double sumYearBud = yearDc.getSum("year_bud");
		double sumMonthBala = ljfsDc.getSum("month_bala");
		fChargeFee.setYearBudText(Data.normalToFinal(Data.trimDoubleNo0(sumYearBud, 2)));
		fChargeFee.setSumOccText(Data.normalToFinal(Data.trimDoubleNo0(sumMonthBala, 2)));
		fChargeFee.setLeftBalaText(Data.normalToFinal(Data.trimDoubleNo0(sumYearBud-sumMonthBala, 2)));
		fChargeFee.setSumFinance(Data.normalToFinal(Data.trimDoubleNo0(ljfsDc.getSum("finance_bala"), 2)));
		fChargeFee.setMonthBalaText(Data.normalToFinal(Data.trimDoubleNo0(monthDc.getSum("month_bala"), 2)));
		fChargeFee.setFinanceBalaText(Data.normalToFinal(Data.trimDoubleNo0(monthDc.getSum("finance_bala"), 2)));
		yearList.add(fChargeFee);

		return yearList;
	}

	@Override
	public List<FChargeFee> getDeptSumList(String userDeptIds, String yearQry, String month1, String month2){
		List<FChargeFee> yearList = fChargeFeeMapper.getDeptSumYearList(yearQry, userDeptIds);
		DataCantainer<FChargeFee> yearDc = new DataCantainer<>((ArrayList)yearList);
		List<FChargeFee> monthList = fChargeFeeMapper.getDeptSumMonthList(yearQry, month1, month2, userDeptIds);
		DataCantainer<FChargeFee> monthDc = new DataCantainer<>((ArrayList)monthList);
		List<FChargeFee> ljfsList = fChargeFeeMapper.getDeptSumLJFSList(yearQry, month2, userDeptIds);
		DataCantainer<FChargeFee> ljfsDc = new DataCantainer<>((ArrayList)ljfsList);

		for(int i=0; i<yearList.size(); i++){
			FChargeFee fChargeFee = yearList.get(i);
			String teamNo = fChargeFee.getTeamNo();
			double yearBud = fChargeFee.getYearBud().doubleValue();
			double monthBala = monthDc.findDataCantainer("team_no", teamNo).getSum("month_bud");
			double assessBala = monthDc.findDataCantainer("team_no", teamNo).getSum("assess_bala");
			double diffBala = monthBala - assessBala;
			double diffScale = (monthBala==0) ? 0 : (diffBala / monthBala)*100;
			double sumOcc = ljfsDc.findDataCantainer("team_no", teamNo).getSum("bala");
			double leftBala = yearBud - sumOcc;
			double leftScale = (yearBud==0) ? 0 : (leftBala / yearBud)*100;
			fChargeFee.setYearBudText(Data.normalToFinal(Data.trimDoubleNo0(yearBud, 2)));
			fChargeFee.setMonthBalaText(Data.normalToFinal(Data.trimDoubleNo0(monthBala, 2)));
			fChargeFee.setAssessBalaText(Data.normalToFinal(Data.trimDoubleNo0(assessBala, 2)));
			fChargeFee.setDiffBalaText(Data.normalToFinal(Data.trimDoubleNo0(diffBala, 2)));
			fChargeFee.setDiffScaleText(Data.trimDoubleNo0(diffScale, 0) + "%");
			fChargeFee.setSumOccText(Data.normalToFinal(Data.trimDoubleNo0(sumOcc, 2)));
			fChargeFee.setLeftBalaText(Data.normalToFinal(Data.trimDoubleNo0(leftBala, 2)));
			fChargeFee.setLeftScaleText(Data.trimDoubleNo0(leftScale, 0) + "%");
		}

		double sumYearBud = yearDc.getSum("year_bud");
		double sumMonthBala = monthDc.getSum("month_bud");
		double sumAssessBala = monthDc.getSum("assess_bala");
		double sumDiffBala = sumMonthBala - sumAssessBala;
		double sumDiffScale = (sumMonthBala==0) ? 0 : (sumDiffBala / sumMonthBala)*100;
		double sumHjOcc = ljfsDc.getSum("bala");
		double sumLeftBala = sumYearBud - sumHjOcc;
		double sumLeftScale = (sumYearBud==0) ? 0 : (sumLeftBala / sumYearBud)*100;
		FChargeFee fChargeFee = new FChargeFee();
		fChargeFee.setTeamName("合计");
		fChargeFee.setYearBudText(Data.normalToFinal(Data.trimDoubleNo0(sumYearBud, 2)));
		fChargeFee.setMonthBalaText(Data.normalToFinal(Data.trimDoubleNo0(sumMonthBala, 2)));
		fChargeFee.setAssessBalaText(Data.normalToFinal(Data.trimDoubleNo0(sumAssessBala, 2)));
		fChargeFee.setDiffBalaText(Data.normalToFinal(Data.trimDoubleNo0(sumDiffBala, 2)));
		fChargeFee.setDiffScaleText(Data.trimDoubleNo0(sumDiffScale, 0) + "%");
		fChargeFee.setSumOccText(Data.normalToFinal(Data.trimDoubleNo0(sumHjOcc, 2)));
		fChargeFee.setLeftBalaText(Data.normalToFinal(Data.trimDoubleNo0(sumLeftBala, 2)));
		fChargeFee.setLeftScaleText(Data.trimDoubleNo0(sumLeftScale, 0) + "%");
		yearList.add(fChargeFee);

		return yearList;
	}

	@Override
	public List<FChargeFee> getDeptSumDetailList(String yearQry, String month1, String month2, String teamNo){
		List<FChargeFee> yearList = fChargeFeeMapper.getDeptSumDetailYearList(yearQry, teamNo);
		DataCantainer<FChargeFee> yearDc = new DataCantainer<>((ArrayList)yearList);
		List<FChargeFee> monthList = fChargeFeeMapper.getDeptSumDetailMonthList(month1, month2, teamNo);
		DataCantainer<FChargeFee> monthDc = new DataCantainer<>((ArrayList)monthList);
		List<FChargeFee> ljfsList = fChargeFeeMapper.getDeptSumDetailMonthList(yearQry+"01", month2, teamNo);
		DataCantainer<FChargeFee> ljfsDc = new DataCantainer<>((ArrayList)ljfsList);

		for(int i=0; i<yearList.size(); i++){
			FChargeFee fChargeFee = yearList.get(i);
			String leader = fChargeFee.getLeader();
			String itemId = fChargeFee.getItemId();
			double yearBud = fChargeFee.getYearBud().doubleValue();
			double sumOcc = ljfsDc.findDataCantainer( "leader", leader, "item_id", itemId).getSum("month_bala");
			double leftBala = yearBud - sumOcc;
			double sumFinance = ljfsDc.findDataCantainer("leader", leader, "item_id", itemId).getSum("finance_bala");
			double monthBala = monthDc.findDataCantainer("leader", leader, "item_id", itemId).getSum("month_bala");
			double financeBala = monthDc.findDataCantainer("leader", leader, "item_id", itemId).getSum("finance_bala");
			fChargeFee.setYearBudText(Data.normalToFinal(Data.trimDoubleNo0(yearBud, 2)));
			fChargeFee.setSumOccText(Data.normalToFinal(Data.trimDoubleNo0(sumOcc, 2)));
			fChargeFee.setLeftBalaText(Data.normalToFinal(Data.trimDoubleNo0(leftBala, 2)));
			fChargeFee.setSumFinance(Data.normalToFinal(Data.trimDoubleNo0(sumFinance, 2)));
			fChargeFee.setMonthBalaText(Data.normalToFinal(Data.trimDoubleNo0(monthBala, 2)));
			fChargeFee.setFinanceBalaText(Data.normalToFinal(Data.trimDoubleNo0(financeBala, 2)));
		}

		FChargeFee fChargeFee = new FChargeFee();
		fChargeFee.setItemName("合计");
		fChargeFee.setTeamName("");
		fChargeFee.setCalcWayName("");
		double sumYearBud = yearDc.getSum("year_bud");
		double sumMonthBala = ljfsDc.getSum("month_bala");
		fChargeFee.setYearBudText(Data.normalToFinal(Data.trimDoubleNo0(sumYearBud, 2)));
		fChargeFee.setSumOccText(Data.normalToFinal(Data.trimDoubleNo0(sumMonthBala, 2)));
		fChargeFee.setLeftBalaText(Data.normalToFinal(Data.trimDoubleNo0(sumYearBud-sumMonthBala, 2)));
		fChargeFee.setSumFinance(Data.normalToFinal(Data.trimDoubleNo0(ljfsDc.getSum("finance_bala"), 2)));
		fChargeFee.setMonthBalaText(Data.normalToFinal(Data.trimDoubleNo0(monthDc.getSum("month_bala"), 2)));
		fChargeFee.setFinanceBalaText(Data.normalToFinal(Data.trimDoubleNo0(monthDc.getSum("finance_bala"), 2)));
		yearList.add(fChargeFee);

		return yearList;
	}

	@Override
	public List<FChargeFee> getCostAnalysisList(String userDeptIds, String yearQry, String month1, String month2){
		List<FChargeFee> yearList = fChargeFeeMapper.getCostAnalysisYearList(yearQry, userDeptIds);
		DataCantainer<FChargeFee> yearDc = new DataCantainer<>((ArrayList)yearList);
		List<FChargeFee> monthList = fChargeFeeMapper.getCostAnalysisMonthList(yearQry, month1, month2, userDeptIds);
		DataCantainer<FChargeFee> monthDc = new DataCantainer<>((ArrayList)monthList);
		List<FChargeFee> ljfsList = fChargeFeeMapper.getDeptSumLJFSList(yearQry, month2, userDeptIds);
		DataCantainer<FChargeFee> ljfsDc = new DataCantainer<>((ArrayList)ljfsList);

		for(int i=0; i<yearList.size(); i++){
			FChargeFee fChargeFee = yearList.get(i);
			String teamNo = fChargeFee.getTeamNo();
			double yearBud = fChargeFee.getYearBud().doubleValue();
			double monthBala = monthDc.findDataCantainer("team_no", teamNo).getSum("month_bud");
			double assessBala = monthDc.findDataCantainer("team_no", teamNo).getSum("assess_bala");
			double diffBala = monthBala - assessBala;
			double diffScale = (monthBala==0) ? 0 : (diffBala / monthBala)*100;
			double sumOcc = ljfsDc.findDataCantainer("team_no", teamNo).getSum("bala");
			double leftBala = yearBud - sumOcc;
			double leftScale = (yearBud==0) ? 0 : (leftBala / yearBud)*100;
			fChargeFee.setYearBudText(Data.normalToFinal(Data.trimDoubleNo0(yearBud, 2)));
			fChargeFee.setMonthBalaText(Data.normalToFinal(Data.trimDoubleNo0(monthBala, 2)));
			fChargeFee.setAssessBalaText(Data.normalToFinal(Data.trimDoubleNo0(assessBala, 2)));
			fChargeFee.setDiffBalaText(Data.normalToFinal(Data.trimDoubleNo0(diffBala, 2)));
			fChargeFee.setDiffScaleText(Data.trimDoubleNo0(diffScale, 0) + "%");
			fChargeFee.setSumOccText(Data.normalToFinal(Data.trimDoubleNo0(sumOcc, 2)));
			fChargeFee.setLeftBalaText(Data.normalToFinal(Data.trimDoubleNo0(leftBala, 2)));
			fChargeFee.setLeftScaleText(Data.trimDoubleNo0(leftScale, 0) + "%");
		}

		double sumYearBud = yearDc.getSum("year_bud");
		double sumMonthBala = monthDc.getSum("month_bud");
		double sumAssessBala = monthDc.getSum("assess_bala");
		double sumDiffBala = sumMonthBala - sumAssessBala;
		double sumDiffScale = (sumMonthBala==0) ? 0 : (sumDiffBala / sumMonthBala)*100;
		double sumHjOcc = ljfsDc.getSum("bala");
		double sumLeftBala = sumYearBud - sumHjOcc;
		double sumLeftScale = (sumYearBud==0) ? 0 : (sumLeftBala / sumYearBud)*100;
		FChargeFee fChargeFee = new FChargeFee();
		fChargeFee.setTeamName("合计");
		fChargeFee.setYearBudText(Data.normalToFinal(Data.trimDoubleNo0(sumYearBud, 2)));
		fChargeFee.setMonthBalaText(Data.normalToFinal(Data.trimDoubleNo0(sumMonthBala, 2)));
		fChargeFee.setAssessBalaText(Data.normalToFinal(Data.trimDoubleNo0(sumAssessBala, 2)));
		fChargeFee.setDiffBalaText(Data.normalToFinal(Data.trimDoubleNo0(sumDiffBala, 2)));
		fChargeFee.setDiffScaleText(Data.trimDoubleNo0(sumDiffScale, 0) + "%");
		fChargeFee.setSumOccText(Data.normalToFinal(Data.trimDoubleNo0(sumHjOcc, 2)));
		fChargeFee.setLeftBalaText(Data.normalToFinal(Data.trimDoubleNo0(sumLeftBala, 2)));
		fChargeFee.setLeftScaleText(Data.trimDoubleNo0(sumLeftScale, 0) + "%");
		yearList.add(fChargeFee);

		return yearList;
	}

	@Override
	public List<FChargeFee> getCostAnalysisDetailList(String yearQry, String month1, String month2, String teamNo){
		List<FChargeFee> yearList = fChargeFeeMapper.getCostAnalysisDetailYearList(yearQry, teamNo);
		DataCantainer<FChargeFee> yearDc = new DataCantainer<>((ArrayList)yearList);
		List<FChargeFee> monthList = fChargeFeeMapper.getCostAnalysisDetailMonthList(month1, month2, teamNo);
		DataCantainer<FChargeFee> monthDc = new DataCantainer<>((ArrayList)monthList);
		List<FChargeFee> ljfsList = fChargeFeeMapper.getCostAnalysisDetailMonthList(yearQry+"01", month2, teamNo);
		DataCantainer<FChargeFee> ljfsDc = new DataCantainer<>((ArrayList)ljfsList);

		for(int i=0; i<yearList.size(); i++){
			FChargeFee fChargeFee = yearList.get(i);
			String leader = fChargeFee.getLeader();
			String itemId = fChargeFee.getItemId();
			double yearBud = fChargeFee.getYearBud().doubleValue();
			double sumOcc = ljfsDc.findDataCantainer( "leader", leader, "item_id", itemId).getSum("month_bala");
			double leftBala = yearBud - sumOcc;
			double sumFinance = ljfsDc.findDataCantainer("leader", leader, "item_id", itemId).getSum("finance_bala");
			double monthBala = monthDc.findDataCantainer("leader", leader, "item_id", itemId).getSum("month_bala");
			double financeBala = monthDc.findDataCantainer("leader", leader, "item_id", itemId).getSum("finance_bala");
			fChargeFee.setYearBudText(Data.normalToFinal(Data.trimDoubleNo0(yearBud, 2)));
			fChargeFee.setSumOccText(Data.normalToFinal(Data.trimDoubleNo0(sumOcc, 2)));
			fChargeFee.setLeftBalaText(Data.normalToFinal(Data.trimDoubleNo0(leftBala, 2)));
			fChargeFee.setSumFinance(Data.normalToFinal(Data.trimDoubleNo0(sumFinance, 2)));
			fChargeFee.setMonthBalaText(Data.normalToFinal(Data.trimDoubleNo0(monthBala, 2)));
			fChargeFee.setFinanceBalaText(Data.normalToFinal(Data.trimDoubleNo0(financeBala, 2)));
		}

		FChargeFee fChargeFee = new FChargeFee();
		fChargeFee.setItemName("合计");
		fChargeFee.setTeamName("");
		fChargeFee.setCalcWayName("");
		double sumYearBud = yearDc.getSum("year_bud");
		double sumMonthBala = ljfsDc.getSum("month_bala");
		fChargeFee.setYearBudText(Data.normalToFinal(Data.trimDoubleNo0(sumYearBud, 2)));
		fChargeFee.setSumOccText(Data.normalToFinal(Data.trimDoubleNo0(sumMonthBala, 2)));
		fChargeFee.setLeftBalaText(Data.normalToFinal(Data.trimDoubleNo0(sumYearBud-sumMonthBala, 2)));
		fChargeFee.setSumFinance(Data.normalToFinal(Data.trimDoubleNo0(ljfsDc.getSum("finance_bala"), 2)));
		fChargeFee.setMonthBalaText(Data.normalToFinal(Data.trimDoubleNo0(monthDc.getSum("month_bala"), 2)));
		fChargeFee.setFinanceBalaText(Data.normalToFinal(Data.trimDoubleNo0(monthDc.getSum("finance_bala"), 2)));
		yearList.add(fChargeFee);

		return yearList;
	}
}
