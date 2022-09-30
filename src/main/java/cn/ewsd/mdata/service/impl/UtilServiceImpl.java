package cn.ewsd.mdata.service.impl;

import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.material.mapper.MPrjItemMapper;
import cn.ewsd.material.mapper.MYearBudgetMapper;
import cn.ewsd.mdata.mapper.UtilMapper;
import cn.ewsd.mdata.model.TdeptType;
import cn.ewsd.mdata.service.UtilService;
import cn.ewsd.repository.mapper.MOutMapper;
import cn.ewsd.system.mapper.AuthAccessMapper;
import cn.ewsd.system.service.ConfigService;
import org.redisson.misc.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service("utilServiceImpl")
public class UtilServiceImpl extends MdataBaseServiceImpl<TdeptType, String> implements UtilService {
	@Autowired
	private UtilMapper utilMapper;
	@Autowired
	private ConfigService configService;
	@Autowired
	private MPrjItemMapper mPrjItemMapper;
	@Autowired
	private MYearBudgetMapper mYearBudgetMapper;
	@Autowired
	private MOutMapper mOutMapper;

	@Override
	public boolean checkColumnDataExist(String tableName, ArrayList<String[]> conditionList) {
		return utilMapper.getRowCountByCondition(tableName, conditionList)>0;
	}

	@Override
	public int getColumnNumber(String tableName, ArrayList<String[]> conditionList){
		return utilMapper.getRowCountByCondition(tableName, conditionList);
	}

	@Override
	public boolean checkRight(String roleIds, int id){
		return utilMapper.getAuthAccessByRoleIdsAndId(roleIds, id)>0;
	}

	@Override
	public String getFMonth() throws Exception{
		String endDay = configService.getConfigByCode("ETC_END_DAY_OF_MATERIAL");
		int iEndDay = -1;
		try{
			iEndDay = Integer.parseInt(endDay);
		}catch (Exception e){
			throw new XException(XException.ERR_DEFAULT, "材料截止日期错误！");
		}
		if(iEndDay>31 || iEndDay<18){
			throw new XException(XException.ERR_DEFAULT, "材料截止日期错误！");
		}
		int iNowDay = Integer.parseInt(XDate.getDate().substring(6, 8));

		if(iNowDay > iEndDay){
			return XDate.getNextMonth();
		}else{
			return XDate.getMonth();
		}
	}

	@Override
	public double getBudgetBala(String prjNo, String payTeam, String planMonth, String itemNo, String prjType){
		double bala = 0;

		if("2".equals(prjType)){
			bala = mPrjItemMapper.getSumMatBala(prjNo);
		}else{
			bala = mYearBudgetMapper.getSumBudBala(payTeam, planMonth, itemNo);
		}

		return bala;
	}

	@Override
	public double getOccurBala(String payTeam, String prjNo, String itemNo, String fMonth){
		if("20".equals(payTeam) || "22".equals(payTeam)){
			return mOutMapper.getOccurBala(payTeam, prjNo, null, null);
		}else{
			return mOutMapper.getOccurBala(payTeam, null, itemNo, fMonth);
		}
	}

	@Override
	public String getBillCode(String tableName, String columnName, String prefix, int length, ArrayList<String[]> conditionList){
		String maxBillCode = utilMapper.getMaxBillCode(tableName, columnName, prefix, conditionList);
		if("".equals(maxBillCode)){
			return prefix + String.format("%0"+length+"d", 1);
		}else{
			Integer max = Integer.parseInt(maxBillCode.substring(maxBillCode.length() - length));
			return prefix + String.format("%0"+length+"d", max+1);
		}
	}

	@Override
	public boolean hasCheckRight(String processNo, String userId){
		return utilMapper.getCheckRight(processNo, userId) > 0;
	}
}
