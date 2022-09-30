package cn.ewsd.material.service.impl;

import cn.ewsd.base.utils.*;
import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.material.mapper.*;
import cn.ewsd.material.model.*;
import cn.ewsd.material.service.MItemService;
import cn.ewsd.material.service.MPlanService;
import cn.ewsd.mdata.mapper.OrganizationMapper;
import cn.ewsd.mdata.model.Organization;
import cn.ewsd.mdata.service.UtilService;
import cn.ewsd.repository.mapper.MOutMapper;
import cn.ewsd.repository.model.MOut;
import cn.ewsd.system.mapper.TCheckMapper;
import cn.ewsd.system.model.AuditMessage;
import cn.ewsd.system.model.DicItem;
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
import java.util.*;

@Service("mPlanServiceImpl")
public class MPlanServiceImpl extends MaterialBaseServiceImpl<MPlan, String> implements MPlanService {
	@Autowired
	MPlanMapper mPlanMapper;
	@Autowired
	MPrjItemMapper mPrjItemMapper;
	@Autowired
	MYearBudgetMapper mYearBudgetMapper;
	@Autowired
	MMaterialMapper mMaterialMapper;
	@Autowired
	UtilService utilService;
	@Autowired
	MItemService mItemService;
	@Autowired
	MPrjMapper mPrjMapper;
	@Autowired
	private ConfigService configService;
	@Autowired
	private MMakeMatDeptMapper mMakeMatDeptMapper;
	@Autowired
	private MAskCodeMapper mAskCodeMapper;
	@Autowired
	private TCheckMapper tCheckMapper;
	@Autowired
	private MOutMapper mOutMapper;
	@Autowired
	private MStockMapper mStockMapper;
	@Autowired
	private OrganizationMapper organizationMapper;
	@Autowired
	private TCostCenterMapper tCostCenterMapper;
	@Autowired
	private MErpMaterialMapper mErpMaterialMapper;
	@Autowired
	private PlanCheckMapper planCheckMapper;

	@Override
	public int getCountByMatNo(String matNo){
		return mPlanMapper.getCountByMatNo(matNo);
	}

	@Override
	public int getCountByPrjNo(String prjNo){
		return mPlanMapper.getCountByPrjNo(prjNo);
	}

	@Override
	public int checkPrjItemHasPlaned(String prjNo, String[] itemNos){
		return mPlanMapper.checkPrjItemHasPlaned(prjNo, itemNos);
	}

	@Override
	public PageSet<MPlan> getPageSet(PageParam pageParam, String filterSort, String prjNo, String planMonth, String teamNo, String itemNo, String planStep){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MPlan> list = mPlanMapper.getPageSet(filterSort, prjNo, planMonth, teamNo, itemNo, planStep);
		PageInfo<MPlan> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public double getSumBala(String prjNo, String planMonth, String teamNo, String itemNo, String planStep){
		return mPlanMapper.getSumBala(prjNo, planMonth, teamNo, itemNo, planStep);
	}

	@Override
	public double getBudgetBala(String prjNo, String payTeam, String planMonth, String itemNo, String prjType){
		if("2".equals(prjType)){
			return mPrjItemMapper.getSumMatBala(prjNo);
		}else{
			return mYearBudgetMapper.getSumBudBala(payTeam, planMonth, itemNo);
		}
	}

	@Override
	public double getPlanBala(String prjNo, String payTeam, String planMonth, String itemNo, String prjType){
		if("2".equals(prjType)){
			return mPlanMapper.getGCSumBala(prjNo);
		}else{
			return mPlanMapper.getSCSumBala(payTeam, planMonth, itemNo);
		}
	}

	@Override
	public List<DicItem> getFactory(){
		return mPlanMapper.getFactory();
	}

	@Transactional
	@Override
	public void addPlan(String prjNo, String[] matNos, String clModel, String planType, String planMonth, String itemNo, String teamNo, int period, String factoryNo, String costCenter, String wbsElement, String itemType, String needComp, String needFactory, String purOrg, String purGroup, String creatorId, String creator, String[] planAmounts, String[] matAddrs) throws Exception{
		int count = 0;

		try{
			for(int i=0; i<matNos.length; i++){
				String matNo = matNos[i];
				double planAmount = 0;
				try{
					planAmount = Double.parseDouble(planAmounts[i]);
				}catch (Exception e){
					throw new XException(XException.ERR_DEFAULT, "计划数必须是数字");
				}
				if(planAmount == 0){
					continue;
				}
				String matAddr = matAddrs[i];

				String planSrc = mPlanMapper.getPlanSrc(prjNo, matNo, clModel);
				if("".equals(planSrc) || planSrc==null){
					planSrc = "m.planSrc.I4";
				}
				String ifMake = "m.planSrc.I2".equals(planSrc) ? "m.ifMake.2" : "m.ifMake.1";
				String planNo = Snow.getUUID()+"";

				MMaterial mMaterial = mMaterialMapper.getMatByNo(matNo);
				BigDecimal matPrice = mMaterial.getMatPrice();
				matAddr = "".equals(matAddr) || matAddr==null ? "0" : matAddr;

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
				String endMonth = XDate.addMonth(planMonth, period);
				String checkNo = Snow.getUUID()+"";

				MPlan mPlan = new MPlan();
				mPlan.setPlanNo(planNo);
				mPlan.setTeamNo(Integer.parseInt(teamNo));
				mPlan.setPlanMonth(planMonth);
				mPlan.setPlanType(planType);
				mPlan.setItemNo(itemNo);
				mPlan.setPrjNo(prjNo);
				mPlan.setMatNo(matNo);
				mPlan.setAbcType("");
				mPlan.setMatPrice(matPrice);
				mPlan.setMatAmount(new BigDecimal(planAmount+""));
				mPlan.setUsableAmount(new BigDecimal("0"));
				mPlan.setUseDate("");
				mPlan.setRemark("");
				mPlan.setPlanStep("71050");
				mPlan.setMatAddr(matAddr);
				mPlan.setFactoryNo(factoryNo);
				mPlan.setCostCenter(costCenter);
				mPlan.setTraxNo("");
				mPlan.setWbsElement(wbsElement);
				mPlan.setMoveType(itemType);
				mPlan.setListNo("");
				mPlan.setItemType(itemType);
				mPlan.setPurGroup(purGroup);
				mPlan.setTrackingNo("");
				mPlan.setIfMake(ifMake);
				mPlan.setPlanSrc(planSrc);
				mPlan.setMatUse("");
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
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Transactional
	@Override
    public int update(ArrayList<MPlan> list){
	    int count = 0;

        for(int i=0; i<list.size(); i++){
            mPlanMapper.updatePlan(list.get(i));
            count++;
        }

	    return count;
    }

    @Transactional
    @Override
    public int deleteBatch(String[] planNos){
        int count = 0;

        for(int i=0; i<planNos.length; i++){
            mPlanMapper.deletePlan(planNos[i]);
            count++;
        }

        return count;
    }

    @Override
	public MPlan getPlanByPlanNo(String planNo){
		return mPlanMapper.getPlanByPlanNo(planNo);
	}

	@Transactional
	@Override
	public int submitPlan(HttpServletRequest request, HashMap<String, String> paramMap, String[] planNos, String planStep, String userId, String userName) throws Exception{
		int count =0;

		try{
			for(int i=0; i<planNos.length; i++){
				String planNo = planNos[i];
				MPlan mPlan = mPlanMapper.getPlanByPlanNo(planNo);
				String prevStepCode = mPlan.getPlanStep();
				String prjNo = mPlan.getPrjNo();
				String ifUrgent = mPlan.getIfUrgent();
				String offerNo = mPlan.getOfferNo();
				String remark = mPlan.getRemark();
				String matUse = mPlan.getMatUse();
				BigDecimal oldAmount = mPlan.getMatAmount();
				String idea = "";
				String matNo = mPlan.getMatNo();
				String matCode = mPlan.getMatCode();
				String itemNo = mPlan.getItemNo();
				String planSrc = mPlan.getPlanSrc();
				String teamNo = mPlan.getTeamNo()+"";
				String reserveNo = mPlan.getReserveNo();
				String planDate = mPlan.getPlanDate();
				int payTeam = mPlan.getPayTeam();
				String checkNo = mPlan.getCheckNo();
				String matAddr = mPlan.getMatAddr();
				String ifMake = mPlan.getIfMake();
				String planMonth = mPlan.getPlanMonth();
				String costCenter = mPlan.getCostCenter();
				String itemName = mPlan.getItemName();

				if(!prevStepCode.equals(planStep)){
					throw new XException(XException.ERR_DEFAULT, "步骤不对应，可能计划已被提交！");
				}

				if(!"71050".equals(prevStepCode) && !"7105X".equals(prevStepCode) && !"7105F".equals(prevStepCode) && !utilService.hasCheckRight(prevStepCode, userId)){
					throw new XException(XException.ERR_DEFAULT, "没有对应的权限！");
				}

				MPrj mPrj = mPrjMapper.getPrjByPrjNo(prjNo);
				String prjStatus = mPrj.getPrjStatus();
				String prjName = mPrj.getPrjName();
				String prjType1 = mPrj.getPrjType1();

				if(!"m.prjStatus.0".equals(prjStatus)){
					throw new XException(XException.ERR_DEFAULT, "工程被暂停或结束："+prjName);
				}

				double matAmount = 0;
				try{
					matAmount = Double.parseDouble(request.getParameter("amount_"+planNo));
				}catch (Exception e){
					throw new XException(XException.ERR_DEFAULT, "计划数必须是大于0的数字！");
				}

				String useDate = request.getParameter("date_"+planNo);
				offerNo = "1".equals(ifUrgent) ? offerNo : request.getParameter("offer_"+planNo);

				if("71050".equals(planStep)){
					if(matAmount<=0){
						throw new XException(XException.ERR_DEFAULT, "计划数必须是大于0的数字！");
					}
					remark = request.getParameter("remark_"+planNo);
					matUse = request.getParameter("use_"+planNo);
					if("".equals(matUse) && !"N".equals(paramMap.get("ifMatUse"))){
						throw new XException(XException.ERR_DEFAULT, "材料用途必须填写！");
					}
					if("1".equals(ifUrgent) && oldAmount.compareTo(new BigDecimal(matAmount+""))<0){
						throw new XException(XException.ERR_DEFAULT, "应急计划涉及库存，计划数量不能调整！");
					}
				}else{
					if(matAmount <= 0 || new BigDecimal(matAmount+"").subtract(oldAmount).compareTo(new BigDecimal("0.0001"))>0){
						throw new XException(XException.ERR_DEFAULT, "计划数必须大于0且不能增加！");
					}
					idea = request.getParameter("reason_"+planNo);
				}

				MMaterial mMaterial = mMaterialMapper.getMatByNo(matNo);
				BigDecimal matPrice = mMaterial.getMatPrice();
				String abcType = paramMap.get("clLbItem").equals(itemNo) || prjName.indexOf("补货计划")>=0 ? "m.abcType.C" : mMaterial.getAbcType();
				String matType = mMaterial.getMatType();
				String matName = mMaterial.getMatName();
				String matUnit = mMaterial.getMatUnit();

				if("71050".equals(prevStepCode)){
					if("Y".equals(paramMap.get("ifCtlPrice")) && (matPrice.doubleValue() == 1 || matPrice.doubleValue() <= 0)){
						throw new XException(XException.ERR_DEFAULT, "价格必须大于0或者不小于1："+matCode);
					}
					if("".equals(itemNo)){
						throw new XException(XException.ERR_DEFAULT, "材料费用科目不能为空！请删除后重新编制！");
					}
					MPlan mPlan1 = new MPlan();
					mPlan1.setAbcType(abcType);
					mPlan1.setPlanDate(XDate.getDate());
					mPlan1.setPlanTime(XDate.getTime());
					mPlan1.setMatUse(matUse);
					mPlan1.setPlanSrc(planSrc);
					mPlan1.setOfferNo(offerNo);
					mPlan1.setPlanNo(planNo);
					mPlanMapper.updatePlan(mPlan1);
				}

				String nextStepCode = getNextStep(prevStepCode, itemNo, matPrice.doubleValue(), matAmount, teamNo, planSrc, reserveNo);

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
						mAskCode.setAskAmount(oldAmount);
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
				mPlan1.setOfferNo(offerNo);
				mPlan1.setUsableAmount(new BigDecimal(usableAmount+""));
				mPlan1.setMatAddr(matAddr);
				mPlan1.setUseDate(useDate);
				mPlan1.setRemark(remark);
				mPlan1.setPlanStep(nextStepCode);
				mPlan1.setPlanSrc(planSrc);
				mPlan1.setIfMake(ifMake);
				mPlan1.setPlanNo(planNo);
				mPlanMapper.updatePlan(mPlan1);

				TCheck tCheck = new TCheck();
				tCheck.setCheckNo(checkNo);
				tCheck.setStepKey("7105");
				tCheck.setStepCode(prevStepCode);
				tCheck.setCheckType("sys.checkType.0");
				tCheck.setDirect("sys.checkDirect.0");
				tCheck.setIdea(idea);
				tCheck.setUserId(userId);
				tCheck.setUserName(userName);
				tCheck.setTeamNo(Integer.parseInt(teamNo));
				tCheck.setOccDate(XDate.getDate());
				tCheck.setOccTime(XDate.getTime());
				tCheck.setLogInfo("价格："+matPrice+"；数量："+matAmount);
				tCheck.setBefAmount(oldAmount);
				tCheck.setBefPrice(matPrice);
				tCheck.setAftAmount(new BigDecimal(matAmount+""));
				tCheck.setAftPrice(matPrice);
				tCheck.setEmpId(0L);
				tCheckMapper.executeSave(tCheck);

				double budgetBala = getBudgetBala(prjNo, payTeam+"", planMonth, itemNo, prjType1);
				double planBala = getPlanBala(prjNo, payTeam+"", planMonth, itemNo, prjType1);

				if("71050".equals(planStep) && !"1".equals(ifUrgent)){
					if(!"C1202L0000".equals(costCenter) && planBala>budgetBala && !itemName.matches("(.*)安全费用(.*)")){
						throw new XException(XException.ERR_DEFAULT,
								"【超过预算】<p>" +
										"【"+mPlan.getTeamName()+"】" +
										"【"+mPlan.getItemName()+"】" +
										"【"+XDate.getCDate8(planMonth)+"】<p>" +
										"【预算 "+budgetBala+"】" +
										"【已用 "+planBala+ "】");
					}
				}
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}

		return count;
	}

	@Override
	public String getNextStep(String prevStepCode, String itemNo, double matPrice, double matAmount, String teamNo, String planSrc, String reserveNo){
		String nextStepCode = "";

		if("71050".equals(prevStepCode)){
			nextStepCode = "71051";
		}else if("71051".equals(prevStepCode)){
			nextStepCode = "m.planSrc.IZ".equals(planSrc) ? "71057" : "71052";
		}else if("71052".equals(prevStepCode)){
			nextStepCode = "7105A";
		}else if("71057".equals(prevStepCode)){
			nextStepCode = "71059";
		}else if("7105A".equals(prevStepCode)){
			nextStepCode = "71054";
//		}else if("7105B".equals(prevStepCode)){
//			nextStepCode = "71054";
		}else if("71054".equals(prevStepCode)){
			nextStepCode = "71055";
		}else if("71055".equals(prevStepCode)){
			nextStepCode = "71056";
		}else if("71056".equals(prevStepCode)){
			nextStepCode = "7105M";
		}else if("7105M".equals(prevStepCode) || "7105S".equals(prevStepCode) || "71059".equals(prevStepCode)){
			nextStepCode = "7105F";
		}

		if("71057".equals(nextStepCode)){
			if("0".equals(reserveNo) || "".equals(reserveNo)){
				nextStepCode = "71059";
			}
		}

		if("71052".equals(nextStepCode)){
			MItem mItem = mItemService.queryObject(itemNo);
			Integer znks = 	mItem.getPayTeam();
			if(znks == null){
				nextStepCode = "7105A";
			}
		}

		if("71056".equals(nextStepCode)){
			double bigBala = Double.parseDouble(configService.getConfigByCode("CL_BIG_BALA"));
			if(matPrice < bigBala){
				nextStepCode = "I".equals(planSrc.substring(planSrc.lastIndexOf(".")+1, planSrc.lastIndexOf(".")+2)) ? "7105F" : "7105M";
			}
		}

		if("7105M".equals(nextStepCode) && "I".equals(planSrc.substring(planSrc.lastIndexOf(".")+1, planSrc.lastIndexOf(".")+2))){
			nextStepCode = "7105F";
		}

		return nextStepCode;
	}

	public int updatePlan(MPlan mPlan){
		return mPlanMapper.updatePlan(mPlan);
	}

	@Override
	public List<MPlan> getPlanStepData(String userDept, String beginMonth, String endMonth, String itemNoQry, boolean ifRole, String userTeam){
		return mPlanMapper.getPlanStepData(userDept, beginMonth, endMonth, itemNoQry, ifRole, userTeam);
	}

	@Override
	public List<MPlan> getPlanStepTeamList(String userDept, String beginMonth, String endMonth, String itemNoQry, boolean ifRole, String userTeam){
		return mPlanMapper.getPlanStepTeamList(userDept, beginMonth, endMonth, itemNoQry, ifRole, userTeam);
	}

	@Override
	public PageSet<MPlan> getDetailPageSet(PageParam pageParam, String filterSort, String beginMonth, String endMonth, String teamNo, String planStep, String itemNo, String planSrc, String balaFlag, String matBala, String matQry, boolean ifRole, String userTeam){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MPlan> list = mPlanMapper.getDetailPageSet(beginMonth, endMonth, teamNo, planStep, itemNo, planSrc, balaFlag, matBala, matQry, ifRole, userTeam);
		PageInfo<MPlan> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public PageSet<MPlan> getQueryPlanList(PageParam pageParam, String filterSort, String userDept, String teamNo, String prjStatus, String matBala, String balaFlag, String prjNo, String itemNo, String reserveNo, String mon1, String mon2, String year, String planStep, String matName, String matCode, String reseFlag){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MPlan> list = mPlanMapper.getQueryPlanList(userDept, teamNo, prjStatus, matBala, balaFlag, prjNo, itemNo, reserveNo, mon1, mon2, year, planStep, matName, matCode, reseFlag);
		PageInfo<MPlan> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public List<MPlan> getQueryPlanDownLoad(String userDept, String teamNo, String prjStatus, String matBala, String balaFlag, String prjNo, String itemNo, String reserveNo, String mon1, String mon2, String year, String planStep, String matName, String matCode, String reseFlag){
		return mPlanMapper.getQueryPlanList(userDept, teamNo, prjStatus, matBala, balaFlag, prjNo, itemNo, reserveNo, mon1, mon2, year, planStep, matName, matCode, reseFlag);
	}

	@Override
	public PageSet<MPlan> getMatHis(PageParam pageParam, String filterSort, String userDept, String year, String matNo){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MPlan> list = mPlanMapper.getMatHis(userDept, year, matNo);
		PageInfo<MPlan> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Transactional
	@Override
	public void adjustPlan(String isDelete, String planNo, String purchaseNo, String reserveNo, String planStep, String remark, double minusAmount, String checkNo, UserInfo userInfo) throws Exception{
		try{
			if(isDelete==null){
				MPlan mPlan = new MPlan();
				mPlan.setMatAmount(new BigDecimal(minusAmount+""));
				mPlan.setUsableAmount(new BigDecimal(minusAmount+""));
				mPlan.setRemark(remark);
				mPlan.setPlanStep(planStep);
				mPlan.setReserveNo(reserveNo);
				mPlan.setPurchaseNo(purchaseNo);
				mPlan.setPlanNo(planNo);
				mPlanMapper.updPlan1(mPlan);
				if("7105Q".equals(planStep)){
					mPlanMapper.updPlan2(planNo);
				}
			}else{
				if(mOutMapper.getYiLingByPlanNo(planNo).getOutAmount().doubleValue() > 0){
					throw new XException(XException.ERR_DEFAULT, "已发生领用不能删除计划！") ;
				}else{
					mOutMapper.deleteByPlanNo(planNo);
				}

				MPlan mPlan = new MPlan();
				mPlan.setPlanStep("7105X");
				mPlan.setRemark(remark);
				mPlan.setPlanNo(planNo);
				mPlanMapper.updatePlan(mPlan);

				TCheck tCheck = new TCheck();
				tCheck.setCheckNo(checkNo);
				tCheck.setStepKey("7105");
				tCheck.setStepCode("7105X");
				tCheck.setCheckType("sys.checkType.0");
				tCheck.setDirect("sys.checkDirect.1");
				tCheck.setIdea(remark);
				tCheck.setUserId(userInfo.getUuid());
				tCheck.setUserName(userInfo.getUserName());
				tCheck.setTeamNo(Integer.parseInt(userInfo.getOrgId()));
				tCheck.setOccDate(XDate.getDate());
				tCheck.setOccTime(XDate.getTime());
				tCheck.setLogInfo("删除定稿计划");
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
	public PageSet<MPlan> getDrawApply(PageParam pageParam, String filterSort, String teamNo, String applyType, String inDate, String storeNo, String matCodeQry, String matNameQry){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MPlan> list = mPlanMapper.getDrawApply(teamNo, applyType, inDate, storeNo, matCodeQry, matNameQry, XDate.getDate());
		PageInfo<MPlan> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	@Transactional
	public void submitUrgentPlan(HttpServletRequest request, UserInfo userInfo) throws Exception{
		try{
			String planMonth = XDate.getMonth();
			String planType = "m.planType.2";
			String prjNo = request.getParameter("prjNo");
			if("".equals(prjNo)){
				throw new XException(XException.ERR_DEFAULT, "请选择工程！");
			}
			String itemNo = "100101";
			String teamNo = userInfo.getOrgId();
			Organization o1 = organizationMapper.getListById(teamNo);
			if(o1 == null || o1.getDeptCode()==null || "".equals(o1.getDeptCode())){
				throw new XException(XException.ERR_DEFAULT, "本单位没有对应的成本中心！");
			}
			String costCenter = o1.getDeptCode();
			String planSrc = "m.planSrc.IZ";
			String ifUrgent = "1";
			String planStep = "71051";

			String[] keys = request.getParameterValues("key");
			for(int i=0; i<keys.length; i++){
				String key = keys[i];
				String reserveNo = key.split("_")[0];
				String storeNo = key.split("_")[1];
				String matNo = key.split("_")[2];

				double amount = 0;
				String amt = request.getParameter("amount_"+key);
				if("".equals(amt)){
					continue;
				}
				try{
					amount = Double.parseDouble(amt);
				}catch (Exception e){
					throw new XException(XException.ERR_DEFAULT, "计划数量必须是数字！");
				}
				if(amount<=0){
					continue;
				}
				String matUse = request.getParameter("use_"+key);
				String remark = request.getParameter("remark_"+key);

				double stock=0;
				String offerTeam = "";
				if("0".equals(reserveNo)){
					MStock stock1 = mStockMapper.getStock(storeNo, matNo);
					stock = stock1!=null ? stock1.getStockAmount().doubleValue() : 0;
				}else{
					stock = mStockMapper.getPlanIosStockAmount(reserveNo, storeNo, matNo);
					Organization o = organizationMapper.getOrgEpPlan(reserveNo);
					offerTeam = o!=null ? o.getId()+"" : "";
				}

				if("".equals(offerTeam) || "18".equals(offerTeam)){
					offerTeam = configService.getConfigByCode("XT_TEAM_MATERIAL");
				}

				if(amount - stock > 0.0001){
					throw new XException(XException.ERR_DEFAULT, "计划数量，大于库存数量！");
				}

				MMaterial mMaterial = mMaterialMapper.getMatByNo(matNo);
				BigDecimal matPrice = mMaterial.getMatPrice();
				String payDept = mItemService.getPayTeam(prjNo, itemNo, teamNo);
				String endMonth = XDate.addMonth(planMonth, Integer.parseInt(configService.getConfigByCode("CL_PLAN_END_PERIOD")));
				String checkNo = Snow.getUUID()+"";

				MPlan mPlan = new MPlan();
				mPlan.setPlanNo(Snow.getUUID()+"");
				mPlan.setTeamNo(Integer.parseInt(teamNo));
				mPlan.setPlanMonth(planMonth);
				mPlan.setPlanType(planType);
				mPlan.setItemNo(itemNo);
				mPlan.setPrjNo(prjNo);
				mPlan.setMatNo(matNo);
				mPlan.setAbcType("");
				mPlan.setMatPrice(matPrice);
				mPlan.setMatAmount(new BigDecimal(amount+""));
				mPlan.setUsableAmount(new BigDecimal("0"));
				mPlan.setUseDate("");
				mPlan.setRemark(remark);
				mPlan.setPlanStep(planStep);
				mPlan.setMatAddr(storeNo);
				mPlan.setFactoryNo("");
				mPlan.setCostCenter(costCenter);
				mPlan.setTraxNo("");
				mPlan.setWbsElement("");
				mPlan.setMoveType("");
				mPlan.setListNo("");
				mPlan.setItemType("");
				mPlan.setPurGroup("");
				mPlan.setTrackingNo("");
				mPlan.setIfMake("");
				mPlan.setPlanSrc(planSrc);
				mPlan.setMatUse(matUse);
				mPlan.setNeedComp("");
				mPlan.setNeedFactory("");
				mPlan.setPurOrg("");
				mPlan.setPmNumber("");
				mPlan.setIfUrgent(ifUrgent);
				mPlan.setOfferNo(offerTeam);
				mPlan.setPayTeam(Integer.parseInt(payDept));
				mPlan.setPlanDate(XDate.getDate());
				mPlan.setPlanTime(XDate.getTime());
				mPlan.setCheckNo(checkNo);
				mPlan.setOrderDir("m.orderDir.1");
				mPlan.setEndMonth(endMonth);
				mPlan.setReserveNo(!"0".equals(reserveNo) ? reserveNo : "");
				mPlan.setPurchaseNo("");
				mPlan.setUuid(BaseUtils.UUIDGenerator());
				mPlan.setCreateTime(new Date());
				mPlan.setCreator(userInfo.getUserName());
				mPlan.setCreatorId(userInfo.getUuid());
				mPlanMapper.savePlan(mPlan);

//				double budgetBala = getUrgBudBala(teamNo, planMonth);
//				double planBala = getUrgPlanBala(teamNo, planMonth);
//
//				if (planBala>budgetBala) {
//					Organization o = organizationMapper.getOrganizationById(Integer.parseInt(teamNo));
//					throw new XException(XException.ERR_DEFAULT,
//							"【超过应急预算】<p>" +
//									"【"+o.getText()+"】" +
//									"【"+XDate.getCDate8(planMonth)+"】<p>" +
//									"【预算 "+budgetBala+"】" +
//									"【已用 "+planBala + "】"
//					);
//				}
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	public double getUrgBudBala(String teamNo, String planMonth){
		return mYearBudgetMapper.getUrgBudBala(planMonth, teamNo);
	}

	@Override
	public double getUrgPlanBala(String teamNo, String planMonth){
		return mYearBudgetMapper.getUrgPlanBala(teamNo, planMonth);
	}

	@Override
	public PageSet<MPlan> getRikuQry(PageParam pageParam, String filterSort, String monthQry, String teamQry, String stepQry, String matQry){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MPlan> list = mPlanMapper.getRikuQryList(monthQry, teamQry, stepQry, matQry);
		PageInfo<MPlan> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public List<MPlan> getRikuQryList(String monthQry, String teamQry, String stepQry, String matQry){
		return mPlanMapper.getRikuQryList(monthQry, teamQry, stepQry, matQry);
	}

	@Override
	@Transactional
	public void importPlan(List<Map<String, String>> varList, UserInfo userInfo, String teamNo, String itemNo, String planMonth, String planType, String prjNo) throws Exception{
		try{
			String needComp = configService.getConfigByCode("ETC_NEED_COMP");
			String needFactory = configService.getConfigByCode("ETC_NEED_FACTORY");
			String purOrg = configService.getConfigByCode("ETC_PURCHASE_ORG");
			String purGroup = configService.getConfigByCode("ETC_PURCHASE_GROUP");

			for(int i=0; i<varList.size(); i++){
				HashMap<String, String> map = (HashMap)varList.get(i);
				String costCenter = map.get("成本中心编号");
				String matCode = map.get("物料编码(8位)");
				String mCode = matCode.length()==8 ? "X"+matCode : matCode;
				String amount = map.get("计划数量");
				amount = amount==null ? "" : amount;
				double matAmount = "".equals(amount) ? 0 : Double.parseDouble(amount);
				String matUse = map.get("用途");
				matUse = matUse==null ? "" : matUse;
				String remark = map.get("备注");
				remark = remark==null ? "" : remark;
				String wbs = map.get("WBS元素号");
				wbs = wbs==null ? "" : wbs;
				String offerNo = map.get("供应商号");
				offerNo = offerNo==null ? "" : offerNo;

				String itemType = tCostCenterMapper.getMoveType(costCenter);
				if(!"".equals(wbs)){
					itemType = "m.erpItem.3";
				}
				if("".equals(itemType) || itemType==null){
					throw new XException(XException.ERR_DEFAULT, "移动类型不正确！");
				}

				MMaterial mMaterial = mMaterialMapper.getMatByCode(mCode);
				String matNo = mMaterial.getMatNo();
				BigDecimal matPrice = mMaterial.getMatPrice();
				String factoryNo = "";
				String matAddr = "";

				List<MErpMaterial> emList = mErpMaterialMapper.getErpMaterialByMatNo(matNo);
				if(emList.size() > 0){
					factoryNo = emList.get(0).getFactoryNo();
					matAddr = emList.get(0).getMatAddr();
				}

				List<MMaterial> psList = mMaterialMapper.getPlanSrc(matNo);
				String planSrc = "".equals(factoryNo) ? "m.planSrc.I4" : (psList.size()>0 ? psList.get(0).getPlanNo() : "m.planSrc.I4");
				String ifMake = "m.planSrc.I2".equals(planSrc) ? "m.ifMake.2" : "m.ifMake.1";
				String planNo = Snow.getUUID()+"";

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

				MPlan mPlan = new MPlan();
				mPlan.setPlanNo(planNo);
				mPlan.setTeamNo(Integer.parseInt(teamNo));
				mPlan.setPlanMonth(planMonth);
				mPlan.setPlanType(planType);
				mPlan.setItemNo(itemNo);
				mPlan.setPrjNo(prjNo);
				mPlan.setMatNo(matNo);
				mPlan.setAbcType("");
				mPlan.setMatPrice(matPrice);
				mPlan.setMatAmount(new BigDecimal(matAmount+""));
				mPlan.setUsableAmount(new BigDecimal("0"));
				mPlan.setUseDate("");
				mPlan.setRemark(remark);
				mPlan.setPlanStep("71050");
				mPlan.setMatAddr(matAddr);
				mPlan.setFactoryNo(factoryNo);
				mPlan.setCostCenter(costCenter);
				mPlan.setTraxNo("");
				mPlan.setWbsElement(wbs);
				mPlan.setMoveType(itemType);
				mPlan.setListNo("");
				mPlan.setItemType(itemType);
				mPlan.setPurGroup(purGroup);
				mPlan.setTrackingNo("");
				mPlan.setIfMake(ifMake);
				mPlan.setPlanSrc(planSrc);
				mPlan.setMatUse(matUse);
				mPlan.setNeedComp(needComp);
				mPlan.setNeedFactory(needFactory);
				mPlan.setPurOrg(purOrg);
				mPlan.setPmNumber("");
				mPlan.setIfUrgent("");
				mPlan.setOfferNo(offerNo);
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
				mPlan.setCreator(userInfo.getUserName());
				mPlan.setCreatorId(userInfo.getUuid());

				mPlanMapper.savePlan(mPlan);
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	public PageSet<HashMap<String, Object>> getMatAnalyPageSet(PageParam pageParam, String filterSort, String matQry){
		try {
			String nowYear = XDate.getYear();
			String befOne = String.valueOf(Integer.parseInt(nowYear) - 1);
			String befTwo = String.valueOf(Integer.parseInt(nowYear) - 2);

			PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
			List<HashMap<String, Object>> matList = mPlanMapper.getMatAnalyMatList(nowYear, befOne, befTwo, matQry);

			String mats = "-1";
			for (int i = 0; i < matList.size(); i++) {
				mats += "," + matList.get(i).get("mat_no");
			}
			List<MPlan> planList = mPlanMapper.getMatAnalyPlanList(mats, nowYear, befOne, befTwo);
			DataCantainer<MPlan> planDc = new DataCantainer<>((ArrayList)planList);
			List<MPlan> inList = mPlanMapper.getMatAnalyInList(mats, nowYear, befOne, befTwo);
			DataCantainer<MPlan> inDc = new DataCantainer<>((ArrayList)inList);
			List<MPlan> outList = mPlanMapper.getMatAnalyOutList(mats, nowYear, befOne, befTwo);
			DataCantainer<MPlan> outDc = new DataCantainer<>((ArrayList)outList);

			for(int i=0; i<matList.size(); i++){
				HashMap<String, Object> map = matList.get(i);
				String matNo = String.valueOf(map.get("mat_no"));
				String matCode = String.valueOf(map.get("mat_code"));
				map.put("nowAmount", Data.normalToFinal(planDc.findDataCantainer("occ_year", nowYear, "mat_code", matCode).getSum("mat_amount")));
				map.put("nowIn", Data.normalToFinal(inDc.findDataCantainer("occ_year", nowYear, "mat_no", matNo).getSum("mat_amount")));
				map.put("nowOut", Data.normalToFinal(outDc.findDataCantainer("occ_year", nowYear, "mat_no", matNo).getSum("mat_amount")));
				map.put("oneAmount", Data.normalToFinal(planDc.findDataCantainer("occ_year", befOne, "mat_code", matCode).getSum("mat_amount")));
				map.put("oneIn", Data.normalToFinal(inDc.findDataCantainer("occ_year", befOne, "mat_no", matNo).getSum("mat_amount")));
				map.put("oneOut", Data.normalToFinal(outDc.findDataCantainer("occ_year", befOne, "mat_no", matNo).getSum("mat_amount")));
				map.put("twoAmount", Data.normalToFinal(planDc.findDataCantainer("occ_year", befTwo, "mat_code", matCode).getSum("mat_amount")));
				map.put("twoIn", Data.normalToFinal(inDc.findDataCantainer("occ_year", befTwo, "mat_no", matNo).getSum("mat_amount")));
				map.put("towOut", Data.normalToFinal(outDc.findDataCantainer("occ_year", befTwo, "mat_no", matNo).getSum("mat_amount")));
			}

			PageInfo<HashMap<String, Object>> pageInfo = new PageInfo<>(matList);
			return PageUtils.getPageSet(pageInfo);
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<AuditMessage> getPendingRecords(UserInfo userInfo) throws Exception{
		List<AuditMessage> list = new ArrayList<>();
		int order = 0;

		//需求计划
		List<PlanCheckIndex> planList = planCheckMapper.getPLanPendingRecords(userInfo.getOrgId(), userInfo.getUuid(), planCheckMapper.getUserItemCnt(userInfo.getUuid()));
		for(int i=0; i<planList.size(); i++){
			PlanCheckIndex planCheckIndex = planList.get(i);

			AuditMessage auditMessage = new AuditMessage();
			auditMessage.setMessageId(BaseUtils.UUIDGenerator());
			auditMessage.setMessageName("待审计划");
			auditMessage.setMessageType(2);
			auditMessage.setMessageNum(planCheckIndex.getCnt());
			auditMessage.setMessageSort(order++);
			auditMessage.setJumpPageUrl("/material/planCheck/index");
			auditMessage.setJumpPageName("审批需求计划");
			auditMessage.setBusinessId("");

			list.add(auditMessage);
		}

		//申请领用
		List<MOut> drawList = mOutMapper.getDrawPendingRecords(userInfo.getUuid(), userInfo.getOrgId());
		for(int i=0; i<drawList.size(); i++){
			MOut mOut = drawList.get(i);

			AuditMessage auditMessage = new AuditMessage();
			auditMessage.setMessageId(BaseUtils.UUIDGenerator());
			auditMessage.setMessageName("待审领用");
			auditMessage.setMessageType(2);
			auditMessage.setMessageNum(mOut.getCnt());
			auditMessage.setMessageSort(order++);
			auditMessage.setJumpPageUrl("/repository/drawCheck/index");
			auditMessage.setJumpPageName("区队领料审批");
			auditMessage.setBusinessId("");

			list.add(auditMessage);
		}

		//自制加工
		List<MAskCode> askList = mAskCodeMapper.getCheckPendingRecords(userInfo.getUuid(), userInfo.getOrgId());
		for(int i=0; i<askList.size(); i++){
			MAskCode mAskCode = askList.get(i);

			AuditMessage auditMessage = new AuditMessage();
			auditMessage.setMessageId(BaseUtils.UUIDGenerator());
			auditMessage.setMessageName("待审加工");
			auditMessage.setMessageType(2);
			auditMessage.setMessageNum(mAskCode.getAskCnt());
			auditMessage.setMessageSort(order++);
			auditMessage.setJumpPageUrl("/material/matAskCheck/index");
			auditMessage.setJumpPageName("加工审批");
			auditMessage.setBusinessId("");

			list.add(auditMessage);
		}

		//物资过期
		int cnt = 0;
		cnt = mOutMapper.getPeriodWarnRecords(XDate.addDate(XDate.getDate(), 30));
		if(cnt > 0){
			AuditMessage auditMessage = new AuditMessage();
			auditMessage.setMessageId(BaseUtils.UUIDGenerator());
			auditMessage.setMessageName("到期物资");
			auditMessage.setMessageType(2);
			auditMessage.setMessageNum(cnt);
			auditMessage.setMessageSort(order++);
			auditMessage.setJumpPageUrl("/repository/bcDrawQry/index?periodQry=30&d1Qry=2020-01-01&d2Qry="+XDate.dateTo10(XDate.getDate()));
			auditMessage.setJumpPageName("入库明细查询");
			auditMessage.setBusinessId("");

			list.add(auditMessage);
		}

		//库存预警
		cnt = mOutMapper.getStockWarnRecords();
		if(cnt > 0){
			AuditMessage auditMessage = new AuditMessage();
			auditMessage.setMessageId(BaseUtils.UUIDGenerator());
			auditMessage.setMessageName("库存预警");
			auditMessage.setMessageType(2);
			auditMessage.setMessageNum(cnt);
			auditMessage.setMessageSort(order++);
			auditMessage.setJumpPageUrl("/repository/stockQry/index?warnFlag=y&flagQry=y");
			auditMessage.setJumpPageName("库存实时查询");
			auditMessage.setBusinessId("");

			list.add(auditMessage);
		}

		return list;
	}
}
