package cn.ewsd.material.service.impl;

import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.material.mapper.*;
import cn.ewsd.material.model.*;
import cn.ewsd.material.service.MPlanService;
import cn.ewsd.material.service.SendMatPlanService;
import cn.ewsd.system.mapper.TCheckMapper;
import cn.ewsd.system.model.TCheck;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.List;

@Service("sendMatPlanServiceImpl")
public class SendMatPlanServiceImpl extends MaterialBaseServiceImpl<SendMatPlan, String> implements SendMatPlanService {
	@Autowired
	private SendMatPlanMapper sendMatPlanMapper;
	@Autowired
    private MPlanMapper mPlanMapper;
	@Autowired
    private TCheckMapper tCheckMapper;
	@Autowired
    private MPrjMapper mPrjMapper;
	@Autowired
    private MPlanService mPlanService;
    @Autowired
    private MMaterialMapper mMaterialMapper;
    @Autowired
    private MMakeMatDeptMapper mMakeMatDeptMapper;
    @Autowired
    private MAskCodeMapper mAskCodeMapper;

    @Override
    public List<SendMatPlan> getPlanMonth(){
        return sendMatPlanMapper.getPlanMonth();
    }

    @Override
    public PageSet<SendMatPlan> getPageSet(PageParam pageParam, String filterSort, String planMonth, String planType, String prjNo, String matCode, String matName, String teamNo, String itemNo, String centerNo){
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<SendMatPlan> list = sendMatPlanMapper.getPageSet(filterSort, planMonth, planType, prjNo, matCode, matName, teamNo, itemNo, centerNo);
        PageInfo<SendMatPlan> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public List<SendMatPlan> getList(String planMonth, String planType, String prjNo, String matCode, String matName, String teamNo, String itemNo, String centerNo){
        return sendMatPlanMapper.getPageSet("", planMonth, planType, prjNo, matCode, matName, teamNo, itemNo, centerNo);
    }

    @Transactional
    @Override
    public String back(String[] planNos, UserInfo userInfo){
        for(int i=0; i<planNos.length; i++){
            String planNo = planNos[i];
            MPlan mPlan = mPlanMapper.getPlanByPlanNo(planNo);
            String prvStep = mPlan.getPlanStep();
            String checkNo = mPlan.getCheckNo();
            if(!"7105S".equals(prvStep)){
                return "审批步骤不一致，请刷新页面！";
            }
            if("7105F".equals(prvStep)){
                return "定稿计划不能退稿！";
            }

            MPlan mPlan1 = new MPlan();
            mPlan1.setPlanStep("71050");
            mPlan1.setPlanNo(planNo);
            mPlanMapper.updatePlan(mPlan1);

            TCheck tCheck = new TCheck();
            tCheck.setCheckNo(checkNo);
            tCheck.setStepKey("7105");
            tCheck.setStepCode(prvStep);
            tCheck.setCheckType("sys.checkType.0");
            tCheck.setDirect("sys.checkDirect.1");
            tCheck.setIdea("");
            tCheck.setUserId(userInfo.getUserNameId());
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

        return "";
    }

    @Transactional
    @Override
    public int submit(String[] planNos, UserInfo userInfo) throws Exception{
        try{
            int ret = 0;
            String planStep = "7105S";
//        String drawApply = Config.getInstance().getValue(Param.ETC_DRAW_APPLY);
//        String agreeEmp  = Param.getString(Param.CL_AUTO_CLSP_EMP);
//        String ifCtlPrice= Param.getString(Param.CL_CONTROL_1_PRICE);
//        String clModel   = Param.getString(Param.CL_MODEL);
//        submitOne(stepCode, planNo, drawApply, agreeEmp, ifCtlPrice, clModel);

            for(int i=0; i<planNos.length; i++){
                String planNo = planNos[i];
                MPlan mPlan = mPlanMapper.getPlanByPlanNo(planNo);

                String prvStep = mPlan.getPlanStep();
                String prjNo = mPlan.getPrjNo();
                String itemNo = mPlan.getItemNo();
                String matNo = mPlan.getMatNo();
                double matAmount = mPlan.getMatAmount().doubleValue();
                String teamNo = mPlan.getTeamNo()+"";
                String planSrc = mPlan.getPlanSrc();
                String reserveNo = mPlan.getReserveNo();
                String planDate = mPlan.getPlanDate();
                int payTeam = mPlan.getPayTeam();
                String checkNo = mPlan.getCheckNo();
                String remark = mPlan.getRemark();
                String ifUrgent = mPlan.getIfUrgent();

                if(!planStep.equals(prvStep)){
                    throw new XException(XException.ERR_DEFAULT, "步骤不对应，可能计划已被提交");
                }

                MPrj mPrj = mPrjMapper.getPrjByPrjNo(prjNo);
                String prjStatus = mPrj.getPrjStatus();
                String prjName = mPrj.getPrjName();
                String prjType1 = mPrj.getPrjType1();

                if(!"m.prjStatus.0".equals(prjStatus)){
                    throw new XException(XException.ERR_DEFAULT, "工程被暂停或结束："+prjName);
                }

                MMaterial mMaterial = mMaterialMapper.getMatByNo(matNo);
                BigDecimal matPrice = mMaterial.getMatPrice();
                String matType = mMaterial.getMatType();
                String matName = mMaterial.getMatName();
                String matUnit = mMaterial.getMatUnit();

                String nextStepCode = mPlanService.getNextStep(prvStep, itemNo, matPrice.doubleValue(), matAmount, teamNo, planSrc, reserveNo);

                double usableAmount = 0;
                if("7105F".equals(nextStepCode)){
                    usableAmount = matAmount;
                    if("m.matType.FB".equals(matType)){
                        MMakeMatDept mMakeMatDept = mMakeMatDeptMapper.findByMatNo(matNo);
                        if(mMakeMatDept==null){
                            throw new XException(XException.ERR_DEFAULT, "没有对应的加工单位！");
                        }
                        int makeTeam = mMakeMatDept.getMakeDept();
                        BigDecimal payFee = mMaterial.getPayFee();
                        BigDecimal otherFee = mMaterial.getOtherFee();
                        MAskCode mAskCode = new MAskCode();
                        mAskCode.setAskNo(planNo);
                        mAskCode.setAskName(matName);
                        mAskCode.setAskModel("");
                        mAskCode.setMatWeight(new BigDecimal("0"));
                        mAskCode.setAskUnit(matUnit);
                        mAskCode.setAskDate(planDate);
                        mAskCode.setAskTeam(Integer.parseInt(teamNo));
                        mAskCode.setAskAmount(new BigDecimal(matAmount+""));
                        mAskCode.setPayTeam(payTeam);
                        mAskCode.setMakeTeam(makeTeam);
                        mAskCode.setCheckTeam(0);
                        mAskCode.setPrjNo(prjNo);
                        mAskCode.setItemNo(itemNo);
                        mAskCode.setMatFee(matPrice);
                        mAskCode.setPayFee(payFee);
                        mAskCode.setOtherFee(otherFee);
                        mAskCode.setCheckAmount(new BigDecimal(matAmount+""));
                        mAskCode.setDoDate(planDate);
                        mAskCode.setMatNo(matNo);
                        mAskCode.setIfSelf("1");
                        mAskCode.setAskStep("7300F");
                        mAskCode.setCheckNo(checkNo);
                        mAskCode.setNeedDate(XDate.addDate(planDate,15));
                        mAskCode.setUseAddr("");
                        mAskCode.setRemark(remark);
                        mAskCodeMapper.executeSave(mAskCode);
                    }
                    if("1".equals(ifUrgent) && !"".equals(reserveNo)){
                        mPlanMapper.updPlan(reserveNo, new BigDecimal(matAmount+""), new BigDecimal(matAmount+""));
                    }
                }

                MPlan mPlan1 = new MPlan();
                mPlan1.setMatPrice(matPrice);
                mPlan1.setMatAmount(new BigDecimal(matAmount+""));
                mPlan1.setUsableAmount(new BigDecimal(usableAmount+""));
                mPlan1.setRemark(remark);
                mPlan1.setPlanStep(nextStepCode);
                mPlan1.setPlanSrc(planSrc);
                mPlan1.setPlanNo(planNo);
                mPlanMapper.updatePlan(mPlan1);

                TCheck tCheck = new TCheck();
                tCheck.setCheckNo(checkNo);
                tCheck.setStepKey("7105");
                tCheck.setStepCode(prvStep);
                tCheck.setCheckType("sys.checkType.0");
                tCheck.setDirect("sys.checkDirect.0");
                tCheck.setIdea("");
                tCheck.setUserId(userInfo.getUserNameId());
                tCheck.setUserName(userInfo.getUserName());
                tCheck.setTeamNo(Integer.parseInt(teamNo));
                tCheck.setOccDate(XDate.getDate());
                tCheck.setOccTime(XDate.getTime());
                tCheck.setLogInfo("价格："+matPrice+"；数量："+matAmount);
                tCheck.setBefAmount(new BigDecimal(matAmount+""));
                tCheck.setBefPrice(matPrice);
                tCheck.setAftAmount(new BigDecimal(matAmount+""));
                tCheck.setAftPrice(matPrice);
                tCheck.setEmpId(0L);
                tCheckMapper.executeSave(tCheck);
            }
            return ret;
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }
}
