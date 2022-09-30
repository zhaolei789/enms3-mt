package cn.ewsd.material.service.impl;

import cn.ewsd.base.utils.Snow;
import cn.ewsd.material.mapper.MYearBudgetMapper;
import cn.ewsd.material.model.MBudget;
import cn.ewsd.material.service.MYearBudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Service("mYearBudgetServiceImpl")
public class MYearBudgetServiceImpl extends MaterialBaseServiceImpl<MBudget, String> implements MYearBudgetService {
	@Autowired
	private MYearBudgetMapper mYearBudgetMapper;

	@Override
    public List<MBudget> getYearBudgetList(String itemNo, String useYear, String sysNo){
        return mYearBudgetMapper.getYearBudgetList(itemNo, useYear, sysNo);
    }

    @Transactional
    @Override
    public int saveData(String itemNo, String useYear, HashMap<String, Double[]> map){
        int count = 0;

        Iterator<String> it = map.keySet().iterator();
        while(it.hasNext()){
            String key = it.next();
            Double[] d = map.get(key);
            Double iniBala = d[0] * 10000;
            Double addBala = d[1] * 10000;
            Double budBala = iniBala + addBala;
            if(budBala==0){
                mYearBudgetMapper.deleteData(key, itemNo, useYear);
            }else{
                int c = mYearBudgetMapper.updateData(key, itemNo, useYear, iniBala, addBala, 0.0, budBala);
                if(c<1) {
                    mYearBudgetMapper.saveData(String.valueOf(Snow.getUUID()), key, itemNo, useYear, iniBala, addBala, 0.0, budBala);
                }
            }
            count++;
        }

        return count;
    }

    @Override
    public List<MBudget> getBudOccAnalItem(String userDeptIds, String monthQry, String itemNoQry, String teamNoQry){
	    return mYearBudgetMapper.getBudOccAnalItem(userDeptIds, monthQry, itemNoQry, teamNoQry);
    }

    @Override
    public List<HashMap<String, Object>> getBudOccAnalList(String userDeptIds, String monthQry, String itemNoQry, String teamNoQry){
        List<HashMap<String, Object>> list = new ArrayList<>();

        List<MBudget> itemList = mYearBudgetMapper.getBudOccAnalItem(userDeptIds, monthQry, itemNoQry, teamNoQry);
        List<MBudget> teamList = mYearBudgetMapper.getBudOccAnalTeam(userDeptIds, monthQry, itemNoQry, teamNoQry);

        return list;
    }

    @Override
    public Double getDeptYearBudget(String year, String teamNo){
        return mYearBudgetMapper.getDeptYearBudget(year, teamNo);
    }

    @Override
    public Double getDeptYearOccBudget(String year, String month, String teamNo){
        return mYearBudgetMapper.getDeptYearOccBudget(year, month, teamNo);
    }

    @Override
    public Double getDeptYearOutBudget(String year, String month, String teamNo){
        return mYearBudgetMapper.getDeptYearOutBudget(year, month, teamNo);
    }

    @Override
    public Double getDeptMonthBudget(String year, String month1, String month2, String teamNo){
        return mYearBudgetMapper.getDeptMonthBudget(year, month1, month2, teamNo);
    }

    @Override
    public Double getDeptMonthOccBudget(String month1, String month2, String teamNo){
        return mYearBudgetMapper.getDeptMonthOccBudget(month1, month2, teamNo);
    }

    @Override
    public Double getDeptPlanSum(String month1, String month2, String teamNo){
        return mYearBudgetMapper.getDeptPlanSum(month1, month2, teamNo);
    }

    @Override
    public Double getDeptAddPlan(String month1, String month2, String teamNo){
        return mYearBudgetMapper.getDeptAddPlan(month1, month2, teamNo);
    }

    @Override
    public Double getDeptNoOk(String month1, String month2, String teamNo){
        return mYearBudgetMapper.getDeptNoOk(month1, month2, teamNo);
    }
}
