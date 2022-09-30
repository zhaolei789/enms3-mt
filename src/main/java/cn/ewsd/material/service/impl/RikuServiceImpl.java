package cn.ewsd.material.service.impl;

import cn.ewsd.base.utils.DataCantainer;
import cn.ewsd.base.utils.Snow;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.material.mapper.*;
import cn.ewsd.material.model.*;
import cn.ewsd.material.service.*;
import cn.ewsd.mdata.service.UtilService;
import cn.ewsd.system.mapper.TCheckMapper;
import cn.ewsd.system.model.TCheck;
import cn.ewsd.system.service.ConfigService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("rikuServiceImpl")
public class RikuServiceImpl extends MaterialBaseServiceImpl<MPlan, String> implements RikuService {
    @Autowired
    private RikuMapper rikuMapper;
    @Autowired
    private MStockMapper mStockMapper;
    @Autowired
    private MPlanMapper mPlanMapper;
    @Autowired
    private TCheckMapper tCheckMapper;
    @Autowired
    private ConfigService configService;
    @Autowired
    private TCostCenterService tCostCenterService;
    @Autowired
    private MStockService mStockService;
    @Autowired
    private MMaterialMapper materialMapper;
    @Autowired
    private UtilService utilService;
    @Autowired
    private MItemService mItemService;
    @Autowired
    private MPurchasePlanMapper mPurchasePlanMapper;

    @Override
    public PageSet<Riku> getMatList(int page, int rows, String matQry, String abcTypeQry, String startDateQry, String endDateQry, String planTypeQry, String costCenterQry, String monthQry, String ifAqfyQry, String wbsQry) {
        PageHelper.startPage(page, rows);
        List<Riku> list = rikuMapper.getMatList(matQry, abcTypeQry, startDateQry, endDateQry, planTypeQry, costCenterQry, monthQry, ifAqfyQry, wbsQry);
        PageInfo<Riku> pageInfo = new PageInfo<>(list);
        PageSet<Riku> pageSet = PageUtils.getPageSet(pageInfo);

        DataCantainer<Riku> rikudc = new DataCantainer<>((ArrayList<Riku>)list);
        DataCantainer<MStock> tgdc = new DataCantainer<>((ArrayList<MStock>)mStockMapper.getTgAmount());
        DataCantainer<MStock> sfdc = new DataCantainer<>((ArrayList<MStock>)mStockMapper.getSfAmount());

        List<Riku> newList = new ArrayList<>();
        ArrayList<DataCantainer<Riku>> rikuList = rikudc.getGroup("mat_no");
        for(int i=0; i<rikuList.size(); i++){
            DataCantainer<Riku> rdc = rikuList.get(i);
            Riku riku = rdc.getRow(0);
            String matNo = riku.getMatNo();
            double tgAmount = tgdc.findDataCantainer("mat_no", matNo).getSum("stock_amount");
            double sfAmount = sfdc.findDataCantainer("mat_no", matNo).getSum("stock_amount");
            double planAmount = rdc.getSum("mat_amount");
            double calcAmount = planAmount + sfAmount - tgAmount;

            for(int j=0; j<rdc.getRowCount(); j++){
                Riku r = rdc.getRow(j);
                r.setTgAmount(new BigDecimal(tgAmount+""));
                r.setSfAmount(new BigDecimal(sfAmount+""));
                r.setPlanAmount(new BigDecimal(planAmount+""));
                r.setCalcAmount(new BigDecimal(calcAmount+""));
                newList.add(r);
            }
        }
        pageSet.setRows(newList);

        return pageSet;
    }

    @Transactional
    @Override
    public int deletePlan(String[] planNos, String userId, String userName, String teamNo){
        int ret = 0;

        for(int i=0; i<planNos.length; i++){
            String planNo = planNos[i];

            MPlan mPlan = new MPlan();
            mPlan.setPlanNo(planNo);
            mPlan.setPlanStep("7105X");
            mPlanMapper.updatePlan(mPlan);

            mPlan = mPlanMapper.getPlanByPlanNo(planNo);
            String checkNo = mPlan.getCheckNo();
            String stepCode = mPlan.getPlanStep();
            BigDecimal matPrice = mPlan.getMatPrice();
            BigDecimal matAmount = mPlan.getMatAmount();

            TCheck tCheck = new TCheck();
            tCheck.setCheckNo(checkNo);
            tCheck.setStepKey("7105");
            tCheck.setStepCode(stepCode);
            tCheck.setCheckType("sys.checkType.0");
            tCheck.setDirect("sys.checkDirect.1");
            tCheck.setIdea("利库 删除");
            tCheck.setUserId(userId);
            tCheck.setUserName(userName);
            tCheck.setTeamNo(Integer.parseInt(teamNo));
            tCheck.setOccDate(XDate.getDate());
            tCheck.setOccTime(XDate.getTime());
            tCheck.setLogInfo("价格："+matPrice+"；数量："+matAmount);
            tCheck.setBefAmount(matAmount);
            tCheck.setBefPrice(matPrice);
            tCheck.setAftAmount(matAmount);
            tCheck.setAftPrice(matPrice);
            tCheck.setEmpId(0L);
            tCheckMapper.executeSave(tCheck);

            ret++;
        }

        return ret;
    }

    @Transactional
    @Override
    public int savePlan(ArrayList<MPlan> list, String userId, String userName, String teamNo){
        int ret = 0;

        for(int i=0; i<list.size(); i++){
            MPlan mPlan = list.get(i);
            String planNo = mPlan.getPlanNo();
            String abcType = mPlan.getAbcType();
            BigDecimal matAmount = mPlan.getMatAmount();

            MPlan oldPlan = mPlanMapper.getPlanByPlanNo(planNo);
            String checkNo = oldPlan.getCheckNo();
            String oldAbc = oldPlan.getAbcType();
            BigDecimal matPrice = mPlan.getMatPrice();
            BigDecimal oldAmount = mPlan.getMatAmount();

            if(!oldAbc.equals(abcType)){
                mPlan.setPlanStep("7105F");
                mPlan.setUsableAmount(matAmount);
                mPlan.setPlanSrc("m.planSrc.I4");
            }

            mPlanMapper.updatePlan(mPlan);

            TCheck tCheck = new TCheck();
            tCheck.setCheckNo(checkNo);
            tCheck.setStepKey("7105");
            tCheck.setStepCode("7105M");
            tCheck.setCheckType("sys.checkType.0");
            tCheck.setDirect("");
            tCheck.setIdea("记录");
            tCheck.setUserId(userId);
            tCheck.setUserName(userName);
            tCheck.setTeamNo(Integer.parseInt(teamNo));
            tCheck.setOccDate(XDate.getDate());
            tCheck.setOccTime(XDate.getTime());
            tCheck.setLogInfo("价格："+matPrice+"；数量："+matAmount);
            tCheck.setBefAmount(oldAmount);
            tCheck.setBefPrice(matPrice);
            tCheck.setAftAmount(matAmount);
            tCheck.setAftPrice(matPrice);
            tCheck.setEmpId(0L);
            tCheckMapper.executeSave(tCheck);

            ret++;
        }

        return ret;
    }

    @Transactional
    @Override
    public int finish(HttpServletRequest request, String creatorId, String creator){
        try{
            String prjNo = request.getParameter("prjNo");
            if(prjNo == null){
                prjNo = "";
            }

            String date = XDate.getDate();
            int day = Integer.parseInt(date.substring(6));
            String planMonth = "";
            String planType = "";
            String costCenter = "C1202G0004";
            String mngTeam = configService.getConfigByCode("XT_TEAM_MATERIAL");
            String itemType = tCostCenterService.getMoveType(costCenter, "");
            if(day>=5 && day<=24){
                planMonth = date.substring(0, 6);
                planType = "m.planType.2";
            } else if(day>24){
                planMonth = XDate.addMonth(date.substring(0, 6), 1);
                planType = "m.planType.1";
            } else {
                planMonth = date.substring(0, 6);
                planType = "m.planType.1";
            }
            String needComp = configService.getConfigByCode("ETC_NEED_COMP");
            String needFactory = configService.getConfigByCode("ETC_NEED_FACTORY");
            String purOrg = configService.getConfigByCode("ETC_PURCHASE_ORG");
            String purGroup = configService.getConfigByCode("ETC_PURCHASE_GROUP");
            String planSrc = "m.planSrc.O1";
            String abcTypeQry = request.getParameter("abcTypeQry");
            String itemNo = configService.getConfigByCode("CL_ITEM_NORMAL");
            String useDate = XDate.getLastDayOfMonth(planMonth+"01");
            String period = configService.getConfigByCode("CL_PLAN_END_PERIOD");
            List<MStock> stkSet = mStockService.getTgAmount();
            DataCantainer<MStock> stkddc = new DataCantainer<MStock>((ArrayList)stkSet);

            if("m.abcType.A".equals(abcTypeQry)){
                String[] matNos = request.getParameterValues("matNo");
                for(int i=0; matNos!=null&&i<matNos.length; i++){
                    String matNo = matNos[i];

                    double amount = Double.parseDouble(request.getParameter("amt_"+matNo));

                    MPlan mPlan1 = mPlanMapper.getPlan(matNo, planMonth, "7105F");
                    double price = mPlan1.getMatPrice().doubleValue();
                    String matAddr = mPlan1.getMatAddr();
                    String factoryNo = mPlan1.getFactoryNo();
                    String planNo = Snow.getUUID()+"";

                    if(price ==0){
                        MMaterial mMaterial = materialMapper.getMatByNo(matNo);
                        price = mMaterial.getMatPrice().doubleValue();
                    }

                    if("m.planType.1".equals(planType)){
                        ArrayList<String[]> condArr = new ArrayList<>();
                        condArr.add(new String[]{"prj_no", "=", prjNo});
                        condArr.add(new String[]{"plan_month", "=", planMonth});
                        condArr.add(new String[]{"item_no", "=", itemNo});
                        condArr.add(new String[]{"mat_no", "=", matNo});
                        condArr.add(new String[]{"plan_type", "=", planType});
                        condArr.add(new String[]{"if_urgent", "!=", "1"});
                        condArr.add(new String[]{"plan_step", "!=", "7105X"});
                        if(utilService.checkColumnDataExist("m_plan", condArr)){
                            return 0;
                        }
                    }

                    String payDept = mItemService.getPayTeam(prjNo, itemNo, mngTeam);
                    String endMonth = XDate.addMonth(planMonth, Integer.parseInt(period));
                    String checkNo = Snow.getUUID()+"";

                    MPlan mPlan = new MPlan();
                    mPlan.setPlanNo(planNo);
                    mPlan.setTeamNo(Integer.parseInt(mngTeam));
                    mPlan.setPlanMonth(planMonth);
                    mPlan.setPlanType(planType);
                    mPlan.setItemNo(itemNo);
                    mPlan.setPrjNo(prjNo);
                    mPlan.setMatNo(matNo);
                    mPlan.setAbcType(abcTypeQry);
                    mPlan.setMatPrice(new BigDecimal(price+""));
                    mPlan.setMatAmount(new BigDecimal(amount+""));
                    mPlan.setUsableAmount(new BigDecimal("0"));
                    mPlan.setUseDate(useDate);
                    mPlan.setRemark("");
                    mPlan.setPlanStep("7105S");
                    mPlan.setMatAddr(matAddr);
                    mPlan.setFactoryNo(factoryNo);
                    mPlan.setCostCenter(costCenter);
                    mPlan.setTraxNo("");
                    mPlan.setWbsElement("");
                    mPlan.setMoveType(itemType);
                    mPlan.setListNo("");
                    mPlan.setItemType(itemType);
                    mPlan.setPurGroup(purGroup);
                    mPlan.setTrackingNo("");
                    mPlan.setIfMake("");
                    mPlan.setPlanSrc(planSrc);
                    mPlan.setMatUse(abcTypeQry.substring(abcTypeQry.lastIndexOf(".")+1) + "料补货计划");
                    mPlan.setNeedComp(needComp);
                    mPlan.setNeedFactory(needFactory);
                    mPlan.setPurOrg(purOrg);
                    mPlan.setPmNumber("");
                    mPlan.setIfUrgent("");
                    mPlan.setOfferNo("");
                    mPlan.setPayTeam(Integer.parseInt(payDept));
                    mPlan.setPlanDate(XDate.getDate());
                    mPlan.setPlanTime(XDate.getTime());
                    mPlan.setCheckNo(checkNo);
                    mPlan.setOrderDir("m.orderDir.1");
                    mPlan.setEndMonth(endMonth);
                    mPlan.setReserveNo("");
                    mPlan.setPurchaseNo("");
                    mPlan.setUuid(BaseUtils.UUIDGenerator());
                    mPlan.setCreateTime(new Date());
                    mPlan.setCreator(creator);
                    mPlan.setCreatorId(creatorId);

                    mPlanMapper.savePlan(mPlan);

                    MPlan mPlan2 = new MPlan();
                    mPlan2.setMatNo(matNo);
                    mPlan2.setPlanMonth(planMonth);
                    mPlan2.setPlanStep("7105F");
                    mPlan2.setAbcType("m.abcType.A");
                    List<MPlan> list = mPlanMapper.getPlanList(mPlan2);

                    double needAmount = 0;
                    for(int j=0; j<list.size(); j++){
                        MPlan mPlan3 = list.get(j);
                        double planAmount = mPlan3.getMatAmount().doubleValue();
                        mPurchasePlanMapper.savePurchasePlan(planNo, mPlan3.getPlanNo(), planAmount, amount);
                        needAmount += planAmount;
                    }
                    double stockAmount = stkddc.findDataCantainer("mat_no", matNo).getRow(0).getStockAmount().doubleValue();

                    TCheck tCheck = new TCheck();
                    tCheck.setCheckNo(checkNo);
                    tCheck.setStepKey("7105");
                    tCheck.setStepCode("71050");
                    tCheck.setCheckType("sys.checkType.0");
                    tCheck.setDirect("sys.checkDirect.1");
                    tCheck.setIdea("利库 A类");
                    tCheck.setUserId(creatorId);
                    tCheck.setUserName(creator);
                    tCheck.setTeamNo(Integer.parseInt(mngTeam));
                    tCheck.setOccDate(XDate.getDate());
                    tCheck.setOccTime(XDate.getTime());
                    tCheck.setLogInfo("需求："+needAmount+",库存："+stockAmount+",价格："+price+",数量："+amount);
                    tCheck.setBefAmount(new BigDecimal(amount+""));
                    tCheck.setBefPrice(new BigDecimal(price+""));
                    tCheck.setAftAmount(new BigDecimal(amount+""));
                    tCheck.setAftPrice(new BigDecimal(price+""));
                    tCheck.setEmpId(0L);
                    tCheckMapper.executeSave(tCheck);
                }
            }else{
                String[] planNos = request.getParameterValues("planNo");
                for(int i=0; planNos!=null&&i<planNos.length; i++){
                    String planNo = planNos[i];
                    MPlan mPlan = mPlanMapper.getPlanByPlanNo(planNo);
                    String itemNo1 = mPlan.getItemNo();
                    String checkNo = mPlan.getCheckNo();
                    String nowStep = mPlan.getPlanStep();
                    double amount = mPlan.getMatAmount().doubleValue();
                    double matPrice = mPlan.getMatPrice().doubleValue();

                    MPlan mPlan1 = new MPlan();
                    mPlan1.setPlanStep("7105S");
                    mPlan1.setPlanNo(planNo);
                    mPlanMapper.updatePlan(mPlan1);

                    TCheck tCheck = new TCheck();
                    tCheck.setCheckNo(checkNo);
                    tCheck.setStepKey("7105");
                    tCheck.setStepCode(nowStep);
                    tCheck.setCheckType("sys.checkType.0");
                    tCheck.setDirect("sys.checkDirect.0");
                    tCheck.setIdea("利库 完成");
                    tCheck.setUserId(creatorId);
                    tCheck.setUserName(creator);
                    tCheck.setTeamNo(Integer.parseInt(mngTeam));
                    tCheck.setOccDate(XDate.getDate());
                    tCheck.setOccTime(XDate.getTime());
                    tCheck.setLogInfo("价格："+matPrice+",数量："+amount);
                    tCheck.setBefAmount(new BigDecimal(amount+""));
                    tCheck.setBefPrice(new BigDecimal(matPrice+""));
                    tCheck.setAftAmount(new BigDecimal(amount+""));
                    tCheck.setAftPrice(new BigDecimal(matPrice+""));
                    tCheck.setEmpId(0L);
                    tCheckMapper.executeSave(tCheck);
                }
            }

            return 1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;
    }
}
