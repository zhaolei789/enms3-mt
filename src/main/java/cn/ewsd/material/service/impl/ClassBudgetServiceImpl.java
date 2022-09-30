package cn.ewsd.material.service.impl;

import cn.ewsd.base.utils.Snow;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.material.mapper.MPrjItemMapper;
import cn.ewsd.material.mapper.MYearBudgetMapper;
import cn.ewsd.material.model.MBudget;
import cn.ewsd.material.service.ClassBudgetService;
import cn.ewsd.material.service.MMonthBudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Service("classBudgetServiceImpl")
public class ClassBudgetServiceImpl extends MaterialBaseServiceImpl<MBudget, String> implements ClassBudgetService {
	@Autowired
	private MYearBudgetMapper mYearBudgetMapper;

	@Override
    public List<MBudget> getClassBudgetList(String useYear, String useMonth, String teamNo){
        return mYearBudgetMapper.getClassBudgetList(useYear, useMonth, teamNo);
    }

    @Override
    public List<MBudget> getClassForm(String teamNo, String occMonth, String itemNo){
        return mYearBudgetMapper.getClassForm(teamNo, occMonth, itemNo);
    }

    @Transactional
    @Override
    public int saveData(MBudget mBudget, UserInfo userInfo) throws Exception{
        int count = 0;

        String teamNo = userInfo.getOrgId();
        String itemNo = mBudget.getItemNo();
        String occMonth = mBudget.getOccMonth();

        List<MBudget> list = mYearBudgetMapper.getClassCheck(teamNo, occMonth, itemNo);
        double teamBala = 0;
        double classBala = 0;
        for(int i=0; i<list.size(); i++){
            MBudget mBudget1 = list.get(i);
            if(teamNo.equals(mBudget1.getTeamNo()+"")){
                classBala = mBudget1.getBudBala().doubleValue();
            }else{
                teamBala = mBudget1.getBudBala().doubleValue();
            }
        }

        if(classBala+mBudget.getBudBala().doubleValue() > teamBala){
            throw new XException(XException.ERR_DEFAULT, "班组预算综合不能超过区队预算！");
        }

        try{
            int c = mYearBudgetMapper.updateData(mBudget.getTeamNo()+"", mBudget.getItemNo(), mBudget.getOccMonth(), 0.0, 0.0, 0.0, mBudget.getBudBala().doubleValue());
            if(c<1) {
                mYearBudgetMapper.saveData(String.valueOf(Snow.getUUID()), mBudget.getTeamNo()+"", mBudget.getItemNo(), mBudget.getOccMonth(), 0.0, 0.0, 0.0, mBudget.getBudBala().doubleValue());
            }
            return 1;
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }
}
