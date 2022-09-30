package cn.ewsd.material.service.impl;

import cn.ewsd.base.utils.Snow;
import cn.ewsd.material.mapper.MPrjItemMapper;
import cn.ewsd.material.mapper.MYearBudgetMapper;
import cn.ewsd.material.model.MBudget;
import cn.ewsd.material.service.MMonthBudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Service("mMonthBudgetServiceImpl")
public class MMonthBudgetServiceImpl extends MaterialBaseServiceImpl<MBudget, String> implements MMonthBudgetService {
	@Autowired
	private MYearBudgetMapper mYearBudgetMapper;
    @Autowired
    private MPrjItemMapper mPrjItemMapper;

	@Override
    public List<MBudget> getMonthBudgetList(String itemNo, String useYear, String useMonth, String sysNo){
        return mYearBudgetMapper.getMonthBudgetList(itemNo, useYear, useMonth, sysNo, useYear+useMonth);
    }

    @Transactional
    @Override
    public int saveData(String itemNo, String occMonth, HashMap<String, Double[]> map){
        int count = 0;

        try{
            Iterator<String> it = map.keySet().iterator();
            while(it.hasNext()){
                String key = it.next();
                Double[] d = map.get(key);
                Double iniBala = d[0] * 10000;
                Double addBala = d[1] * 10000;
                Double overBala = d[2] * 10000;
                Double budBala = iniBala + addBala + overBala;

                int c = mYearBudgetMapper.updateData(key, itemNo, occMonth, iniBala, addBala, overBala, budBala);
                if(c<1) {
                    mYearBudgetMapper.saveData(String.valueOf(Snow.getUUID()), key, itemNo, occMonth, iniBala, addBala, overBala, budBala);
                }

                count++;
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }

        return count;
    }
}
