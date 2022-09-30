package cn.ewsd.material.service.impl;

import cn.ewsd.base.utils.DataCantainer;
import cn.ewsd.base.utils.Snow;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.material.mapper.*;
import cn.ewsd.material.model.*;
import cn.ewsd.material.service.MItemService;
import cn.ewsd.material.service.MPrjService;
import cn.ewsd.material.service.WzscPlanService;
import cn.ewsd.mdata.mapper.OrganizationMapper;
import cn.ewsd.mdata.model.Organization;
import cn.ewsd.mdata.service.UtilService;
import cn.ewsd.system.service.ConfigService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.*;

@Service("wzscPlanServiceImpl")
public class WzscPlanServiceImpl extends MaterialBaseServiceImpl<WzscPlan, String> implements WzscPlanService {
	@Autowired
	private WzscPlanMapper wzscPlanMapper;
	@Autowired
    private MInAccountMapper mInAccountMapper;
	@Autowired
    private MMaterialMapper mMaterialMapper;
    @Autowired
    private MErpMaterialMapper mErpMaterialMapper;
    @Autowired
    private MPlanMapper mPlanMapper;
    @Autowired
    private ConfigService configService;
    @Autowired
    private MPrjService mPrjService;
    @Autowired
    private MPrjItemMapper mPrjItemMapper;
    @Autowired
    private UtilService utilService;
    @Autowired
    private MItemService mItemService;
    @Autowired
    private OrganizationMapper organizationMapper;

    @Override
    public PageSet<WzscPlan> getPageSet(PageParam pageParam, String filterSort, String monthQry, String userDeptIds, String codeQry, String typeQry, String centerQry, String purcQry, String matTypeQry, String nameQry, String statusQry) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<WzscPlan> list = wzscPlanMapper.getPageSet(filterSort, monthQry, userDeptIds, codeQry, typeQry, centerQry, purcQry, matTypeQry, nameQry, statusQry);

        List<MInAccount> mInAccountList = mInAccountMapper.getSumSetBalaGroupByOrderNo();
        DataCantainer<MInAccount> dc = new DataCantainer<MInAccount>((ArrayList)mInAccountList);
        for(int i=0; i<list.size(); i++){
            WzscPlan wzscPlan = list.get(i);
            String followNo = wzscPlan.getFollowNo();
            double bala = dc.findDataCantainer("order_no", followNo).getSum("set_bala");
            wzscPlan.setSumBala(new BigDecimal(bala+""));
        }

        PageInfo<WzscPlan> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public List<WzscPlan> getList(String filterSort, String monthQry, String userDeptIds, String codeQry, String typeQry, String centerQry, String purcQry, String matTypeQry, String nameQry, String statusQry) {
        return wzscPlanMapper.getPageSet(filterSort, monthQry, userDeptIds, codeQry, typeQry, centerQry, purcQry, matTypeQry, nameQry, statusQry);
    }

    @Transactional
    @Override
    public void importWzscPlan(List<Map<String, String>> dataList, String creatorId, String creator, String creatorOrgId) throws Exception{
        try{
            String mineFlag = configService.getConfigByCode("MINE_FLAG");

            for(int i = 0; i < dataList.size(); i++){
                HashMap<String, String> map = (HashMap)dataList.get(i);
                String epId = map.get("需求计划编号");
                String pStatus = map.get("计划状态");
                String matCode = map.get("物料编码");
                String matName = map.get("物料名称");
                String matUnit = map.get("计量单位");
                String itemType = "m.erpType."+map.get("科目类型编码");
                String erpType = map.get("物料组");
                String typeName = map.get("物料组名称");
                double matAmount = "".equals(map.get("需求数量")) ? 0 : Double.parseDouble(map.get("需求数量"));
                double uPrice = "".equals(map.get("参考价格")) ? 0 : Double.parseDouble(map.get("参考价格"));
                double unitAmount = "".equals(map.get("价格单位")) ? 0 : Double.parseDouble(map.get("价格单位"));
                double matPrice = unitAmount==0 ? 0 : uPrice/unitAmount;
                String factAddr = map.get("供应仓库编码");
                String[] attr1 = factAddr.split("-");
                String factory = "m.factoryNo."+attr1[0];
                String matAddr = "m.matAddr."+attr1[1];
                String repDate = map.get("提报日期");
                String requestDate = "".equals(repDate) ? "" : XDate.dateTo8(repDate);
                String crtDate = map.get("同步时间");
                String createDate = "".equals(crtDate) ? "" : XDate.dateTo8(crtDate.substring(0, 10));
                String costCenter = map.get("成本中心编码");
                String wbs = map.get("WBS");
                String purchaseNo = map.get("交货单");
                String purchaseList = map.get("交货单行号");
                String remark = map.get("备注");

                String mCode = "X"+matCode;
                MMaterial mMaterial = mMaterialMapper.getMatByCode(mCode);
                String matNo = "";
                if(mMaterial==null || mMaterial.getMatCode()==null){
                    matNo = Snow.getUUID()+"";

                    MMaterial mMaterial1 = new MMaterial();
                    mMaterial1.setUuid(BaseUtils.UUIDGenerator());
                    mMaterial1.setCreator(creator);
                    mMaterial1.setCreatorId(creatorId);
                    mMaterial1.setCreatorOrgId(Integer.parseInt(creatorOrgId));
                    mMaterial1.setCreateTime(new Date());
                    mMaterial1.setMatNo(matNo);
                    mMaterial1.setMatCode(mCode);
                    mMaterial1.setMatName(matName);
                    mMaterial1.setMatUnit(matUnit);
                    mMaterial1.setMatPrice(new BigDecimal(matPrice+""));
                    mMaterial1.setOldRebate(new BigDecimal("0"));
                    mMaterial1.setRealFlag("m.realFlag.0");
                    mMaterial1.setReFlag("m.reFlag.0");
                    mMaterial1.setOffenFlag("m.offenFlag.1");
                    mMaterial1.setUseInfo("");
                    mMaterial1.setInsDate(XDate.getDate());
                    mMaterial1.setErpCode("");
                    mMaterial1.setAbcType("m.abcType.C");
                    mMaterial1.setIfSend("");
                    mMaterial1.setPackScale(new BigDecimal("0"));
                    mMaterial1.setGoodsType("m.goodsType.M");
                    mMaterial1.setPayFee(new BigDecimal("0"));
                    mMaterial1.setOtherFee(new BigDecimal("0"));
                    mMaterial1.setErpType(erpType);
                    mMaterial1.setTypeName(typeName);
                    mMaterial1.setMatType(erpType.substring(0, 2));
                    mMaterial1.setNewOld("X");
                    mMaterialMapper.executeSave(mMaterial1);

                    MErpMaterial mErpMaterial = new MErpMaterial();
                    mErpMaterial.setUuid(BaseUtils.UUIDGenerator());
                    mErpMaterial.setCreator(creator);
                    mErpMaterial.setCreatorId(creatorId);
                    mErpMaterial.setCreatorOrgId(Integer.parseInt(creatorOrgId));
                    mErpMaterial.setCreateTime(new Date());
                    mErpMaterial.setFactoryNo(factory);
                    mErpMaterial.setMatAddr(matAddr);
                    mErpMaterial.setMatNo(matNo);
                    mErpMaterial.setMatUnit(matUnit);
                    mErpMaterialMapper.executeSave(mErpMaterial);
                }else{
                    matNo = mMaterial.getMatNo();
                    String uuid = mMaterial.getUuid();
                    MMaterial mMaterial1 = new MMaterial();
                    mMaterial1.setUuid(uuid);
                    mMaterial1.setMatPrice(new BigDecimal(matPrice+""));
                    mMaterialMapper.executeUpdate(mMaterial1);

                    mErpMaterialMapper.deleteByMatNo(matNo);

                    MErpMaterial mErpMaterial = new MErpMaterial();
                    mErpMaterial.setUuid(BaseUtils.UUIDGenerator());
                    mErpMaterial.setCreator(creator);
                    mErpMaterial.setCreatorId(creatorId);
                    mErpMaterial.setCreatorOrgId(Integer.parseInt(creatorOrgId));
                    mErpMaterial.setCreateTime(new Date());
                    mErpMaterial.setFactoryNo(factory);
                    mErpMaterial.setMatAddr(matAddr);
                    mErpMaterial.setMatNo(matNo);
                    mErpMaterial.setMatUnit(matUnit);
                    mErpMaterialMapper.executeSave(mErpMaterial);
                }

                wzscPlanMapper.deleteByEpId(epId);

                WzscPlan wzscPlan = new WzscPlan();
                wzscPlan.setUuid(BaseUtils.UUIDGenerator());
                wzscPlan.setCreatorId(creatorId);
                wzscPlan.setCreatorOrgId(Integer.parseInt(creatorOrgId));
                wzscPlan.setCreateTime(new Date());
                wzscPlan.setEpId(epId);
                wzscPlan.setMatCode(matCode);
                wzscPlan.setMatName(matName);
                wzscPlan.setMatUnit(matUnit);
                wzscPlan.setErpType(erpType);
                wzscPlan.setTypeName(typeName);
                wzscPlan.setMatAmount(new BigDecimal(matAmount+""));
                wzscPlan.setMatPrice(new BigDecimal(matPrice+""));
                wzscPlan.setFactoryNo(factory);
                wzscPlan.setCenterNo(costCenter);
                wzscPlan.setMatAddr(matAddr);
                wzscPlan.setItemType(itemType);
                wzscPlan.setWbs(wbs);
                wzscPlan.setPlanType("");
                wzscPlan.setRequest("");
                wzscPlan.setPurchaseNo(purchaseNo);
                wzscPlan.setPurchaseList(purchaseList);
                wzscPlan.setRemark(remark);
                wzscPlan.setCreateDate(createDate);
                wzscPlan.setRequestDate(requestDate);
                wzscPlan.setCreator(map.get("提报者名称"));
                wzscPlan.setStatus(map.get("计划状态编码"));
                wzscPlan.setSupplier(map.get("供应商编码"));
                wzscPlan.setSupplierName(map.get("供应商名称"));
                wzscPlan.setFollowNo(map.get("后续单据号"));
                wzscPlan.setRemark("");
                wzscPlanMapper.excuteSave(wzscPlan);

                String planStep = "";
                if(!"".equals(epId) && !"".equals(purchaseNo) && !"已关闭".equals(pStatus)){
                    planStep = "7105O";
                }
                if("已关闭".equals(pStatus)){
                    planStep = "7105P";
                }
                if(!"".equals(epId) && "".equals(purchaseNo)){
                    planStep = "7105Q";
                }

                if(!"".equals(remark) && remark!=null){
                    if("ENZLW".equals(mineFlag) && remark.indexOf(mineFlag) >= 0){
                        String planNo = remark.substring(remark.indexOf(":")+1, remark.indexOf("]"));
                        MPlan mPlan = new MPlan();
                        mPlan.setMatNo(matNo);
                        mPlan.setMatAmount(new BigDecimal(matAmount+""));
                        mPlan.setMatPrice(new BigDecimal(matPrice+""));
                        mPlan.setReserveNo(epId);
                        mPlan.setPurchaseNo(purchaseNo);
                        mPlan.setPurchaseList(purchaseList);
                        mPlan.setItemType(itemType);
                        mPlan.setMoveType(itemType);
                        mPlan.setCostCenter(costCenter);
                        mPlan.setMatAddr(matAddr);
                        mPlan.setPlanStep(planStep);
                        mPlan.setPlanNo(planNo);
                        mPlanMapper.updatePlan(mPlan);

                        MPlan mPlan1 = mPlanMapper.getPlanByPlanNo(planNo);
                        WzscPlan wzscPlan1 = new WzscPlan();
                        wzscPlan1.setRequest(mPlan1.getTeamNo()+"");
                        wzscPlan1.setEpId(epId);
                        wzscPlanMapper.updateEpPlan(wzscPlan1);
                    }else{
                        List<MPlan> planList = mPlanMapper.getPlanByReserveNo(epId);
                        if(planList.size() > 0){
                            MPlan mPlan1 = new MPlan();
                            mPlan1.setMatNo(matNo);
                            //mPlan1.setMatAmount(new BigDecimal(matAmount+""));
                            mPlan1.setMatPrice(new BigDecimal(matPrice+""));
                            mPlan1.setPurchaseNo(purchaseNo);
                            mPlan1.setPurchaseList(purchaseList);
                            mPlan1.setItemType(itemType);
                            mPlan1.setMoveType(itemType);
                            mPlan1.setCostCenter(costCenter);
                            mPlan1.setMatAddr(matAddr);
                            mPlan1.setPlanStep(planStep);
                            mPlan1.setReserveNo(epId);
                            mPlanMapper.updatePlanByReserveNo(mPlan1);
                        }else{
                            String newPlan = Snow.getUUID()+"";
                            Organization o = organizationMapper.getOrgByCenterNo(costCenter);
                            String teamNo = "";
                            if(o==null){
                                teamNo = configService.getConfigByCode("XT_TEAM_MATERIAL");
                            }else{
                                teamNo = o.getId()+"";
                            }
                            String planMonth = "20"+epId.substring(1, 5);
                            String planType = "m.planType.2";
                            String prjNo = mPrjService.getPrjSet(teamNo, "m.prjStatus.0").get(0).getPrjNo();
                            String itemNo = mPrjItemMapper.getItemNosByPrjNo(prjNo)[0];
                            String abcType = "";
                            if(matPrice==0){
                                MMaterial mMaterial1 = mMaterialMapper.getMatByNo(matNo);
                                matPrice = mMaterial1.getMatPrice().doubleValue();
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
                                    continue;
                                }
                            }
                            String payDept = mItemService.getPayTeam(prjNo, itemNo, teamNo);
                            int period = Integer.parseInt(configService.getConfigByCode("CL_PLAN_END_PERIOD"));
                            String endMonth = XDate.addMonth(planMonth, period);
                            String checkNo = Snow.getUUID()+"";

                            MPlan mPlan1 = new MPlan();
                            mPlan1.setPlanNo(newPlan);
                            mPlan1.setTeamNo(Integer.parseInt(teamNo));
                            mPlan1.setPlanMonth(planMonth);
                            mPlan1.setPlanType(planType);
                            mPlan1.setItemNo(itemNo);
                            mPlan1.setPrjNo(prjNo);
                            mPlan1.setMatNo(matNo);
                            mPlan1.setAbcType("");
                            mPlan1.setMatPrice(new BigDecimal(matPrice+""));
                            mPlan1.setMatAmount(new BigDecimal(matAmount+""));
                            mPlan1.setUsableAmount(new BigDecimal(matAmount+""));
                            mPlan1.setUseDate("");
                            mPlan1.setRemark(remark);
                            mPlan1.setPlanStep("7105O");
                            mPlan1.setMatAddr(matAddr);
                            mPlan1.setFactoryNo("");
                            mPlan1.setCostCenter(costCenter);
                            mPlan1.setTraxNo("");
                            mPlan1.setWbsElement(wbs);
                            mPlan1.setMoveType(itemType);
                            mPlan1.setListNo("");
                            mPlan1.setItemType(itemType);
                            mPlan1.setPurGroup("");
                            mPlan1.setTrackingNo("");
                            mPlan1.setIfMake("");
                            mPlan1.setPlanSrc("m.planSrc.O1");
                            mPlan1.setMatUse(remark);
                            mPlan1.setNeedComp("");
                            mPlan1.setNeedFactory("");
                            mPlan1.setPurOrg("");
                            mPlan1.setPmNumber("");
                            mPlan1.setIfUrgent("");
                            mPlan1.setOfferNo("");
                            mPlan1.setPayTeam(Integer.parseInt(payDept));
                            mPlan1.setPlanDate(XDate.getDate());
                            mPlan1.setPlanTime(XDate.getTime());
                            mPlan1.setCheckNo(checkNo);
                            mPlan1.setOrderDir("m.orderDir.1");
                            mPlan1.setEndMonth(endMonth);
                            mPlan1.setReserveNo("");
                            mPlan1.setPurchaseNo("");
                            mPlan1.setUuid(BaseUtils.UUIDGenerator());
                            mPlan1.setCreateTime(new Date());
                            mPlan1.setCreator(creator);
                            mPlan1.setCreatorId(creatorId);
                            mPlanMapper.savePlan(mPlan1);
                        }
                    }
                }else{
                    List<MPlan> planList = mPlanMapper.getPlanByReserveNo(epId);
                    if(planList.size() > 0){
                        MPlan mPlan1 = new MPlan();
                        mPlan1.setMatNo(matNo);
                        //mPlan1.setMatAmount(new BigDecimal(matAmount+""));
                        mPlan1.setMatPrice(new BigDecimal(matPrice+""));
                        mPlan1.setPurchaseNo(purchaseNo);
                        mPlan1.setPurchaseList(purchaseList);
                        mPlan1.setItemType(itemType);
                        mPlan1.setMoveType(itemType);
                        mPlan1.setCostCenter(costCenter);
                        mPlan1.setMatAddr(matAddr);
                        mPlan1.setPlanStep(planStep);
                        mPlan1.setReserveNo(epId);
                        mPlanMapper.updatePlanByReserveNo(mPlan1);
                    }else{
                        String newPlan = Snow.getUUID()+"";
                        Organization o = organizationMapper.getOrgByCenterNo(costCenter);
                        String teamNo = "";
                        if(o==null){
                            teamNo = configService.getConfigByCode("XT_TEAM_MATERIAL");
                        }else{
                            teamNo = o.getId()+"";
                        }
                        String planMonth = "20"+epId.substring(1, 5);
                        String planType = "m.planType.2";
                        List<MPrj> prjList =  mPrjService.getPrjSet(teamNo, "m.prjStatus.0");
                        if(prjList.size()<1){
                            throw new XException(XException.ERR_DEFAULT, "第"+(i+2)+"行：区队没有正在施工的工程！");
                        }
                        String prjNo = prjList.get(0).getPrjNo();
                        String[] items = mPrjItemMapper.getItemNosByPrjNo(prjNo);
                        if(items==null || items.length<1){
                            throw new XException(XException.ERR_DEFAULT, "第"+(i+2)+"行：工程没有对应的科目！");
                        }
                        String itemNo = items[0];
                        String abcType = "";
                        if(matPrice==0){
                            MMaterial mMaterial1 = mMaterialMapper.getMatByNo(matNo);
                            matPrice = mMaterial1.getMatPrice().doubleValue();
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
                                continue;
                            }
                        }
                        String payDept = mItemService.getPayTeam(prjNo, itemNo, teamNo);
                        int period = Integer.parseInt(configService.getConfigByCode("CL_PLAN_END_PERIOD"));
                        String endMonth = XDate.addMonth(planMonth, period);
                        String checkNo = Snow.getUUID()+"";

                        MPlan mPlan1 = new MPlan();
                        mPlan1.setPlanNo(newPlan);
                        mPlan1.setTeamNo(Integer.parseInt(teamNo));
                        mPlan1.setPlanMonth(planMonth);
                        mPlan1.setPlanType(planType);
                        mPlan1.setItemNo(itemNo);
                        mPlan1.setPrjNo(prjNo);
                        mPlan1.setMatNo(matNo);
                        mPlan1.setAbcType("");
                        mPlan1.setMatPrice(new BigDecimal(matPrice+""));
                        mPlan1.setMatAmount(new BigDecimal(matAmount+""));
                        mPlan1.setUsableAmount(new BigDecimal(matAmount+""));
                        mPlan1.setUseDate("");
                        mPlan1.setRemark(remark);
                        mPlan1.setPlanStep("7105O");
                        mPlan1.setMatAddr(matAddr);
                        mPlan1.setFactoryNo("");
                        mPlan1.setCostCenter(costCenter);
                        mPlan1.setTraxNo("");
                        mPlan1.setWbsElement(wbs);
                        mPlan1.setMoveType(itemType);
                        mPlan1.setListNo("");
                        mPlan1.setItemType(itemType);
                        mPlan1.setPurGroup("");
                        mPlan1.setTrackingNo("");
                        mPlan1.setIfMake("");
                        mPlan1.setPlanSrc("m.planSrc.O1");
                        mPlan1.setMatUse(remark);
                        mPlan1.setNeedComp("");
                        mPlan1.setNeedFactory("");
                        mPlan1.setPurOrg("");
                        mPlan1.setPmNumber("");
                        mPlan1.setIfUrgent("");
                        mPlan1.setOfferNo("");
                        mPlan1.setPayTeam(Integer.parseInt(payDept));
                        mPlan1.setPlanDate(XDate.getDate());
                        mPlan1.setPlanTime(XDate.getTime());
                        mPlan1.setCheckNo(checkNo);
                        mPlan1.setOrderDir("m.orderDir.1");
                        mPlan1.setEndMonth(endMonth);
                        mPlan1.setReserveNo("");
                        mPlan1.setPurchaseNo("");
                        mPlan1.setUuid(BaseUtils.UUIDGenerator());
                        mPlan1.setCreateTime(new Date());
                        mPlan1.setCreator(creator);
                        mPlan1.setCreatorId(creatorId);
                        mPlanMapper.savePlan(mPlan1);
                    }
                }
            }

            mPlanMapper.updPlanSc("7105O");
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    @Override
    @Transactional
    public int deleteBatch(String[] uuids){
        int ret=0;
        for(int i=0; i<uuids.length; i++){
            String uuid = uuids[i];

            WzscPlan wzscPlan = wzscPlanMapper.getByUuid(uuid);
            wzscPlanMapper.deleteByUuid(uuid);
            MPlan mPlan = new MPlan();
            mPlan.setItemNo("");
            mPlan.setPurchaseNo("");
            mPlan.setPurchaseList("");
            mPlan.setReserveNo(wzscPlan.getEpId());
            mPlanMapper.updatePlanByReserveNo(mPlan);

            ret++;
        }
        return ret;
    }

    @Override
    public PageSet<WzscPlan> getArriveQryPageSet(PageParam pageParam, String filterSort, String reserveQry, String monthQry, String userDeptIds, String centerQry, String matCodeQry, String over3Qry, String matNameQry, String over1Qry, String nowDate){
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<WzscPlan> list = wzscPlanMapper.getArriveQryPageSet(filterSort, reserveQry, monthQry, userDeptIds, centerQry, matCodeQry, over3Qry, matNameQry, over1Qry, nowDate);

//        List<MInAccount> mInAccountList = mInAccountMapper.getSumSetBalaGroupByOrderNo();
//        DataCantainer<MInAccount> dc = new DataCantainer<MInAccount>((ArrayList)mInAccountList);
//        for(int i=0; i<list.size(); i++){
//            WzscPlan wzscPlan = list.get(i);
//            String followNo = wzscPlan.getFollowNo();
//            double bala = dc.findDataCantainer("order_no", followNo).getSum("set_bala");
//            wzscPlan.setSumBala(new BigDecimal(bala));
//        }

        PageInfo<WzscPlan> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public List<WzscPlan> getArriveQryList(String reserveQry, String monthQry, String userDeptIds, String centerQry, String matCodeQry, String over3Qry, String matNameQry, String over1Qry, String nowDate){
        return wzscPlanMapper.getArriveQryPageSet("", reserveQry, monthQry, userDeptIds, centerQry, matCodeQry, over3Qry, matNameQry, over1Qry, nowDate);
    }
}
