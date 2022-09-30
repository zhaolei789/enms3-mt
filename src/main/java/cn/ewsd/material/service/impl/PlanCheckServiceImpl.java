package cn.ewsd.material.service.impl;

import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.material.mapper.MPlanMapper;
import cn.ewsd.material.mapper.PlanCheckMapper;
import cn.ewsd.material.model.MPlan;
import cn.ewsd.material.model.PlanCheckIndex;
import cn.ewsd.material.service.PlanCheckService;
import cn.ewsd.system.mapper.TCheckMapper;
import cn.ewsd.system.model.TCheck;
import cn.ewsd.system.service.ConfigService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Service("planCheckServiceImpl")
public class PlanCheckServiceImpl extends MaterialBaseServiceImpl<PlanCheckIndex, String> implements PlanCheckService {
	@Autowired
	private PlanCheckMapper planCheckMapper;
	@Autowired
    private ConfigService configService;
	@Autowired
    private MPlanMapper mPlanMapper;
	@Autowired
    private TCheckMapper tCheckMapper;

    @Override
    public PageSet<PlanCheckIndex> getIndexData(PageParam pageParam, String filterSort, String teamNo, String userId, int userItemCnt) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<PlanCheckIndex> list = planCheckMapper.getIndexData(filterSort, teamNo, userId, userItemCnt);
        PageInfo<PlanCheckIndex> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public PageSet<MPlan> getPlanList(PageParam pageParam, String filterSort, String prjNo, String planMonth, String planType, String planStep, String teamNo, String matCodeQry, String matNameQry, String itemQry){
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<MPlan> list = planCheckMapper.getPlanList(prjNo, planMonth, planType, planStep, teamNo, matCodeQry, matNameQry, itemQry);
        PageInfo<MPlan> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    @Transactional
    public void backPlan(HttpServletRequest request, UserInfo userInfo) throws Exception {
        try{
            String clModel = configService.getConfigByCode("CL_MODEL");
            String planStep = request.getParameter("planStep");
            String[] planNos = request.getParameterValues("planNo");

            if(planNos == null || planNos.length<1){
                throw new XException(XException.ERR_DEFAULT, "请选择要退回的计划！");
            }

            for(int i=0; i<planNos.length; i++){
                String planNo = planNos[i];
                String remark = request.getParameter("reason_"+planNo);

                MPlan mPlan = mPlanMapper.getPlanByPlanNo(planNo);
                String prvStep = mPlan.getPlanStep();
                String checkNo = mPlan.getCheckNo();

                if(!planStep.equals(prvStep)){
                    throw new XException(XException.ERR_DEFAULT, "审批步骤不一致，请刷新页面！");
                }
                if("7105F".equals(prvStep)){
                    throw new XException(XException.ERR_DEFAULT, "定稿计划不能退稿！");
                }

                MPlan plan = new MPlan();
                plan.setPlanNo(planNo);
                plan.setPlanStep("1".equals(mPlan.getIfUrgent()) ? "7105X" : "71050");
                mPlanMapper.updatePlan(plan);

                TCheck tCheck = new TCheck();
                tCheck.setCheckNo(checkNo);
                tCheck.setStepKey("7105");
                tCheck.setStepCode(prvStep);
                tCheck.setCheckType("sys.checkType.0");
                tCheck.setDirect("sys.checkDirect.1");
                tCheck.setIdea(remark);
                tCheck.setUserId(userInfo.getUuid());
                tCheck.setUserName(userInfo.getUserName());
                tCheck.setTeamNo(Integer.parseInt(userInfo.getOrgId()));
                tCheck.setOccDate(XDate.getDate());
                tCheck.setOccTime(XDate.getTime());
                tCheck.setLogInfo("退稿");
                tCheck.setBefAmount(new BigDecimal("0"));
                tCheck.setBefPrice(new BigDecimal("0"));
                tCheck.setAftAmount(new BigDecimal("0"));
                tCheck.setAftPrice(new BigDecimal("0"));
                tCheck.setEmpId(0L);
                tCheckMapper.executeSave(tCheck);
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    @Override
    public Integer getUserItemCnt(String userId){
        return planCheckMapper.getUserItemCnt(userId);
    }
}
