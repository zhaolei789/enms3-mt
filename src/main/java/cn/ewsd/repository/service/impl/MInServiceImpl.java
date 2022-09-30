package cn.ewsd.repository.service.impl;

import cn.ewsd.base.utils.*;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.fix.mapper.MBackPlanMapper;
import cn.ewsd.fix.model.MBackPlan;
import cn.ewsd.material.mapper.*;
import cn.ewsd.material.model.*;
import cn.ewsd.material.service.MOfferService;
import cn.ewsd.material.service.MStockService;
import cn.ewsd.mdata.mapper.OrganizationMapper;
import cn.ewsd.mdata.model.Organization;
import cn.ewsd.repository.mapper.*;
import cn.ewsd.repository.model.*;
import cn.ewsd.repository.service.MInService;
import cn.ewsd.system.mapper.ConfigMapper;
import cn.ewsd.system.mapper.TCheckMapper;
import cn.ewsd.system.model.SysUserQryOrg;
import cn.ewsd.system.model.TCheck;
import cn.ewsd.system.service.SysUserQryOrgService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Service("mInServiceImpl")
public class MInServiceImpl extends RepositoryBaseServiceImpl<MIn, String> implements MInService {
	@Autowired
	private MInMapper mInMapper;
	@Autowired
	private MOfferMapper mOfferMapper;
	@Autowired
	private MOfferService mOfferService;
	@Autowired
	private WzscPlanMapper wzscPlanMapper;
	@Autowired
	private MPlanMapper mPlanMapper;
	@Autowired
	private OrganizationMapper organizationMapper;
	@Autowired
	private TCheckMapper tCheckMapper;
	@Autowired
	private MMaterialMapper mMaterialMapper;
	@Autowired
	private MStoreMapper mStoreMapper;
	@Autowired
	private MStockMapper mStockMapper;
	@Autowired
	private MTeamBillMapper mTeamBillMapper;
	@Autowired
	private MTeamStockMapper mTeamStockMapper;
	@Autowired
	private MStockService mStockService;
	@Autowired
	private MTeamStockLogMapper mTeamStockLogMapper;
	@Autowired
	private MInBackMapper mInBackMapper;
	@Autowired
	private MBackPlanMapper mBackPlanMapper;
	@Autowired
	private MOutMapper mOutMapper;
	@Autowired
	private MPrjMapper mPrjMapper;
	@Resource
	private ConfigMapper configMapper;

	@Override
	public int getCountByMatNo(Long matNo){
		return mInMapper.getCountByMatNo(matNo);
	}

	@Override
	public int getCountByStoreNo(String storeNo){
		return mInMapper.getCountByStoreNo(storeNo);
	}

	@Override
	public PageSet<WzscPlan> getEpPlanPageSet(PageParam pageParam, String filterSort, String beginDate, String occDate, String monthQry, String codeQry, String typeQry, String purcQry, String nameQry, String statusQry) {
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<WzscPlan> list = mInMapper.getEpPlanPageSet(filterSort, beginDate, occDate, monthQry, codeQry, typeQry, purcQry, nameQry, statusQry);
		PageInfo<WzscPlan> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public PageSet<MIn> getDetailPageSet(PageParam pageParam, String filterSort) {
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MIn> list = mInMapper.getDetail();
		PageInfo<MIn> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Transactional
	@Override
	public void submitApply(String storeQry, String ifStockQry, double amount, String offer, String remark, String epId, UserInfo userInfo) throws Exception{
		try{
			MOffer mOffer = mOfferMapper.getOfferByName(offer);
			String offerNo = offer;
			if(mOffer == null){
				offerNo = mOfferService.getNewOfferNo();
				MOffer mOffer1 = new MOffer();
				mOffer1.setOfferNo(offerNo);
				mOffer1.setOfferName(offer);
				mOffer1.setShortName(offer);
				mOfferMapper.executeSave(mOffer1);
			}

			WzscPlan wzscPlan = wzscPlanMapper.getEpPlan(epId);
			String matNo = wzscPlan.getMatNo();
			String centerNo = wzscPlan.getCenterNo();
			double matPrice = wzscPlan.getMatPrice().doubleValue();

			List<MPlan> planList = mPlanMapper.getPlanByReserveNo(epId);
			String planTeam = "";
			if(planList.size()>0){
				planTeam = planList.get(0).getTeamNo()+"";
			}else {
				planTeam = configMapper.getConfigByCode("XT_TEAM_MATERIAL");
			}

			Organization organization = organizationMapper.getOrgByCenterNo(centerNo);
			String team = organization!=null ? organization.getId()+"" : "0";
			String teamNo = "".equals(planTeam) ? team : planTeam;
			if("0".equals(teamNo) || "".equals(teamNo) || teamNo==null){
				throw new XException(XException.ERR_DEFAULT, "成本中心不存在！");
			}
			if(matNo == null){
				throw new XException(XException.ERR_DEFAULT, "系统中不存在该编码【"+ wzscPlan.getMatCode() +"】");
			}

			String billNo = Snow.getUUID()+"";
			String checkNo = Snow.getUUID()+"";
			String inType = "r.inBillType.1";
			String planSrc = "r.inPlanSrc.1";
			String applyDate = XDate.getDate();

			MIn mIn = new MIn();
			mIn.setBillNo(billNo);
			mIn.setInType(inType);
			mIn.setPlanSrc(planSrc);
			mIn.setPlanNo(epId);
			mIn.setTeamNo(Integer.parseInt(teamNo));
			mIn.setOfferTeam(Integer.parseInt(teamNo));
			mIn.setStoreNo(storeQry);
			mIn.setReserveNo(epId);
			mIn.setMatNo(matNo);
			mIn.setApplyAmount(new BigDecimal(amount+""));
			mIn.setApplyDate(applyDate);
			mIn.setApplyEmp(userInfo.getUuid());
			mIn.setInAmount(new BigDecimal(amount+""));
			mIn.setInDate(applyDate);
			mIn.setInEmp(userInfo.getUuid());
			mIn.setBillAmount(new BigDecimal("0"));
			mIn.setSetPrice(new BigDecimal(matPrice+""));
			mIn.setBillDate("");
			mIn.setBillEmp("");
			mIn.setOfferNo(offerNo);
			mIn.setInStep("72011");
			mIn.setCheckNo(checkNo);
			mIn.setRemark("");
			mIn.setDataSrc("m.dataSrc.1");
			mIn.setPrice1(new BigDecimal("0"));
			mIn.setPrice2(new BigDecimal("0"));
			mIn.setPrice3(new BigDecimal("0"));
			mIn.setLinkNo("");
			mIn.setNormNo("");
			mIn.setReserve1("");
			mIn.setReserve2("");
			mIn.setReserve3(ifStockQry);
			mIn.setReserve4("");
			mIn.setErpBill("");
			mInMapper.saveIn(mIn);

			TCheck tCheck = new TCheck();
			tCheck.setCheckNo(checkNo);
			tCheck.setStepKey("7201");
			tCheck.setStepCode("72010");
			tCheck.setCheckType("sys.checkType.0");
			tCheck.setDirect("sys.checkDirect.0");
			tCheck.setIdea("");
			tCheck.setUserId(userInfo.getUuid());
			tCheck.setUserName(userInfo.getUserName());
			tCheck.setTeamNo(Integer.parseInt(userInfo.getOrgId()));
			tCheck.setOccDate(XDate.getDate());
			tCheck.setOccTime(XDate.getTime());
			tCheck.setLogInfo("");
			tCheck.setBefAmount(new BigDecimal(amount+""));
			tCheck.setBefPrice(new BigDecimal(matPrice+""));
			tCheck.setAftAmount(new BigDecimal(amount+""));
			tCheck.setAftPrice(new BigDecimal(matPrice+""));
			tCheck.setEmpId(0L);
			tCheckMapper.executeSave(tCheck);
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	public PageSet<MIn> getDirectInPageSet(PageParam pageParam, String filterSort, String userId, String startDateQry, String endDateQry, String matQry, String storeNoQry){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MIn> list = mInMapper.getDirectInPageSet(userId, startDateQry, endDateQry, matQry, storeNoQry);
		PageInfo<MIn> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Transactional
	@Override
	public int saveDirectIn(UserInfo userInfo, String site, String matNo, String storeNo, double amount, String remark, String ifAccount){
		try {
//			MOffer mOffer = mOfferMapper.getOfferByName(offer);
//			String offerNo = offer;
//			if(mOffer == null){
//				offerNo = mOfferService.getNewOfferNo();
//				MOffer mOffer1 = new MOffer();
//				mOffer1.setOfferNo(offerNo);
//				mOffer1.setOfferName(offer);
//				mOffer1.setShortName(offer);
//				mOfferMapper.executeSave(mOffer1);
//			}

			String billNo = Snow.getUUID()+"";
			String checkNo = Snow.getUUID()+"";
			BigDecimal matPrice = mMaterialMapper.getMatByNo(matNo).getMatPrice();

			MIn mIn = new MIn();
			mIn.setBillNo(billNo);
			mIn.setInType("r.inBillType.2");
			mIn.setPlanSrc("");
			mIn.setPlanNo("");
			mIn.setTeamNo(Integer.parseInt(userInfo.getOrgId()));
			mIn.setOfferTeam(0);
			mIn.setStoreNo(storeNo);
			mIn.setReserveNo("");
			mIn.setMatNo(matNo);
			mIn.setApplyAmount(new BigDecimal(amount+""));
			mIn.setApplyDate(XDate.getDate());
			mIn.setApplyEmp(userInfo.getUuid());
			mIn.setInAmount(new BigDecimal(amount+""));
			mIn.setInDate(XDate.getDate());
			mIn.setInEmp(userInfo.getUuid());
			mIn.setBillAmount(new BigDecimal("0"));
			mIn.setSetPrice(matPrice);
			mIn.setBillDate("");
			mIn.setBillEmp("");
			mIn.setOfferNo("");
			mIn.setInStep("72011");
			mIn.setCheckNo(checkNo);
			mIn.setRemark(remark);
			mIn.setDataSrc("m.dataSrc.1");
			mIn.setPrice1(new BigDecimal("0"));
			mIn.setPrice2(new BigDecimal("0"));
			mIn.setPrice3(new BigDecimal("0"));
			mIn.setLinkNo("");
			mIn.setNormNo("");
			mIn.setReserve1(ifAccount);
			mIn.setReserve2(site==null?"":site);
			mIn.setReserve3("1");
			mIn.setReserve4("");
			mIn.setErpBill("");
			mInMapper.saveIn(mIn);

			TCheck tCheck = new TCheck();
			tCheck.setCheckNo(checkNo);
			tCheck.setStepKey("7201");
			tCheck.setStepCode("72010");
			tCheck.setCheckType("sys.checkType.0");
			tCheck.setDirect("sys.checkDirect.0");
			tCheck.setIdea("");
			tCheck.setUserId(userInfo.getUuid());
			tCheck.setUserName(userInfo.getUserName());
			tCheck.setTeamNo(Integer.parseInt(userInfo.getOrgId()));
			tCheck.setOccDate(XDate.getDate());
			tCheck.setOccTime(XDate.getTime());
			tCheck.setLogInfo("");
			tCheck.setBefAmount(new BigDecimal(amount+""));
			tCheck.setBefPrice(matPrice);
			tCheck.setAftAmount(new BigDecimal(amount+""));
			tCheck.setAftPrice(matPrice);
			tCheck.setEmpId(0L);
			tCheckMapper.executeSave(tCheck);
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}

		return 1;
	}

	@Transactional
	@Override
	public int saveDirectIn2(UserInfo userInfo, String site, String matNo, String storeNo, double amount, String remark, String ifAccount,BigDecimal matPrice){
		try {
			String billNo = Snow.getUUID()+"";
			String checkNo = Snow.getUUID()+"";
			//BigDecimal matPrice = material.getMatPrice();
			MIn mIn = new MIn();
			mIn.setBillNo(billNo);
			mIn.setInType("r.inBillType.2");
			mIn.setPlanSrc("");
			mIn.setPlanNo("");
			mIn.setTeamNo(Integer.parseInt(userInfo.getOrgId()));
			mIn.setOfferTeam(0);
			mIn.setStoreNo(storeNo);
			mIn.setReserveNo("");
			mIn.setMatNo(matNo);
			mIn.setApplyAmount(new BigDecimal(amount+""));
			mIn.setApplyDate(XDate.getDate());
			mIn.setApplyEmp(userInfo.getUuid());
			mIn.setInAmount(new BigDecimal(amount+""));
			mIn.setInDate(XDate.getDate());
			mIn.setInEmp(userInfo.getUuid());
			mIn.setBillAmount(new BigDecimal("0"));
			mIn.setSetPrice(matPrice);
			mIn.setBillDate("");
			mIn.setBillEmp("");
			mIn.setOfferNo("");
			mIn.setInStep("72011");
			mIn.setCheckNo(checkNo);
			mIn.setRemark(remark);
			mIn.setDataSrc("m.dataSrc.1");
			mIn.setPrice1(new BigDecimal("0"));
			mIn.setPrice2(new BigDecimal("0"));
			mIn.setPrice3(new BigDecimal("0"));
			mIn.setLinkNo("");
			mIn.setNormNo("");
			mIn.setReserve1(ifAccount);
			mIn.setReserve2(site);
			mIn.setReserve3("1");
			mIn.setReserve4("");
			mIn.setErpBill("");
			mInMapper.saveIn(mIn);

			TCheck tCheck = new TCheck();
			tCheck.setCheckNo(checkNo);
			tCheck.setStepKey("7201");
			tCheck.setStepCode("72010");
			tCheck.setCheckType("sys.checkType.0");
			tCheck.setDirect("sys.checkDirect.0");
			tCheck.setIdea("");
			tCheck.setUserId(userInfo.getUuid());
			tCheck.setUserName(userInfo.getUserName());
			tCheck.setTeamNo(Integer.parseInt(userInfo.getOrgId()));
			tCheck.setOccDate(XDate.getDate());
			tCheck.setOccTime(XDate.getTime());
			tCheck.setLogInfo("");
			tCheck.setBefAmount(new BigDecimal(amount+""));
			tCheck.setBefPrice(matPrice);
			tCheck.setAftAmount(new BigDecimal(amount+""));
			tCheck.setAftPrice(matPrice);
			tCheck.setEmpId(0L);
			tCheckMapper.executeSave(tCheck);
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}

		return 1;
	}

	/*@Transactional
	@Override
	public int saveDirectIn(UserInfo userInfo, String offer, String matNo, String storeNo, double amount, String remark, String ifAccount){
		try {
			MOffer mOffer = mOfferMapper.getOfferByName(offer);
			String offerNo = offer;
			if(mOffer == null){
				offerNo = mOfferService.getNewOfferNo();
				MOffer mOffer1 = new MOffer();
				mOffer1.setOfferNo(offerNo);
				mOffer1.setOfferName(offer);
				mOffer1.setShortName(offer);
				mOfferMapper.executeSave(mOffer1);
			}

			String billNo = Snow.getUUID()+"";
			String checkNo = Snow.getUUID()+"";
			BigDecimal matPrice = mMaterialMapper.getMatByNo(matNo).getMatPrice();

			MIn mIn = new MIn();
			mIn.setBillNo(billNo);
			mIn.setInType("r.inBillType.2");
			mIn.setPlanSrc("");
			mIn.setPlanNo("");
			mIn.setTeamNo(Integer.parseInt(userInfo.getOrgId()));
			mIn.setOfferTeam(0);
			mIn.setStoreNo(storeNo);
			mIn.setReserveNo("");
			mIn.setMatNo(matNo);
			mIn.setApplyAmount(new BigDecimal(amount+""));
			mIn.setApplyDate(XDate.getDate());
			mIn.setApplyEmp(userInfo.getUuid());
			mIn.setInAmount(new BigDecimal(amount+""));
			mIn.setInDate(XDate.getDate());
			mIn.setInEmp(userInfo.getUuid());
			mIn.setBillAmount(new BigDecimal("0"));
			mIn.setSetPrice(matPrice);
			mIn.setBillDate("");
			mIn.setBillEmp("");
			mIn.setOfferNo(offerNo);
			mIn.setInStep("72011");
			mIn.setCheckNo(checkNo);
			mIn.setRemark(remark);
			mIn.setDataSrc("m.dataSrc.1");
			mIn.setPrice1(new BigDecimal("0"));
			mIn.setPrice2(new BigDecimal("0"));
			mIn.setPrice3(new BigDecimal("0"));
			mIn.setLinkNo("");
			mIn.setNormNo("");
			mIn.setReserve1(ifAccount);
			mIn.setReserve2("");
			mIn.setReserve3("1");
			mIn.setReserve4("");
			mIn.setErpBill("");
			mInMapper.saveIn(mIn);

			TCheck tCheck = new TCheck();
			tCheck.setCheckNo(checkNo);
			tCheck.setStepKey("7201");
			tCheck.setStepCode("72010");
			tCheck.setCheckType("sys.checkType.0");
			tCheck.setDirect("sys.checkDirect.0");
			tCheck.setIdea("");
			tCheck.setUserId(userInfo.getUuid());
			tCheck.setUserName(userInfo.getUserName());
			tCheck.setTeamNo(Integer.parseInt(userInfo.getOrgId()));
			tCheck.setOccDate(XDate.getDate());
			tCheck.setOccTime(XDate.getTime());
			tCheck.setLogInfo("");
			tCheck.setBefAmount(new BigDecimal(amount+""));
			tCheck.setBefPrice(matPrice);
			tCheck.setAftAmount(new BigDecimal(amount+""));
			tCheck.setAftPrice(matPrice);
			tCheck.setEmpId(0L);
			tCheckMapper.executeSave(tCheck);
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}

		return 1;
	}
	*/
	@Override
	public PageSet<MIn> getCheckIndexPageSet(PageParam pageParam, String filterSort, String userId){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MIn> list = mInMapper.getCheckIndexPageSet(userId);
		PageInfo<MIn> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public PageSet<MIn> getInCheckPageSet(PageParam pageParam, String filterSort, String applyDate, String inType, String inStep, String storeNo, String matQry){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MIn> list = mInMapper.getInCheckPageSet(applyDate, inType, inStep, storeNo, matQry);
		PageInfo<MIn> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public MIn getMInByBillNo(String billNo){
		return mInMapper.getMInByBillNo(billNo);
	}

	@Override
	@Transactional
	public void submitMIn(HttpServletRequest request, UserInfo userInfo) throws Exception{
		try{
			String[] billNos = request.getParameterValues("billNo");

			if(billNos==null || billNos.length<1){
				throw new XException(XException.ERR_DEFAULT, "\"请至少选择一条入库记录！\"") ;
			}

			for(int i=0; i<billNos.length; i++){
				String billNo = billNos[i];

				MIn mIn = mInMapper.getMInByBillNo(billNo);
				String inStep = mIn.getInStep();
				double inAmount = mIn.getApplyAmount().doubleValue();
				int qaPeriod = mIn.getQaPeriod()==null ? 0 : mIn.getQaPeriod();
				String ifStock = mIn.getReserve3();
				String matNo = mIn.getMatNo();
				Integer teamNo = mIn.getTeamNo();
				BigDecimal matPrice = mIn.getSetPrice();
				String ifAccount = mIn.getReserve1();
				String checkNo = mIn.getCheckNo();

				if(!"72011".equals(inStep)){
					throw new XException(XException.ERR_DEFAULT, "入库步骤不对应，请刷新！");
				}
				double price = 0;
				try {
					price = Double.parseDouble(request.getParameter("price_"+billNo));
				}catch (Exception e){
					throw new XException(XException.ERR_DEFAULT, "价格必须是数字！") ;
				}
				if(price<0){
					throw new XException(XException.ERR_DEFAULT, "价格不能小于0！") ;
				}
				double amount = 0;
				try {
					amount = Double.parseDouble(request.getParameter("amount_"+billNo));
				}catch (Exception e){
					throw new XException(XException.ERR_DEFAULT, "账入数量必须是数字！") ;
				}
				if(amount - inAmount*1.1 > 0.0001){
					throw new XException(XException.ERR_DEFAULT, "账入数量不能大于物入数量的110%！");
				}
				String storeNo = request.getParameter("store_"+billNo);
				String site = request.getParameter("site_"+billNo);
				String prod = request.getParameter("prod_"+billNo);

				if(qaPeriod!=0 && ("".equals(prod) || !XDate.validDate8(prod))){
					throw new XException(XException.ERR_DEFAULT, "物料存在质保期限，请输入正确的生产日期，例如20220101！");
				}

				MStore mStore = mStoreMapper.getStoreByNo(mIn.getStoreNo());
				String storeLevel = mStore.getStoreLevel();
				if("1".equals(ifStock)){
					if("r.storeLevel.1".equals(storeLevel)){
						MStock mStock = new MStock();
						mStock.setStoreNo(storeNo);
						mStock.setMatNo(matNo);
						mStock.setSiteCode(site);
						mStock.setInAmount(new BigDecimal(amount+""));
						mStock.setOutAmount(new BigDecimal("0"));
						mStock.setStockAmount(new BigDecimal(amount+""));
						mStock.setPackAmount(new BigDecimal("0"));
						mStock.setLockAmount(new BigDecimal("0"));
						mStock.setBulkAmount(new BigDecimal(amount+""));
						if(mStockMapper.updateStock(mStock)<1){
							mStockMapper.insertStock(mStock);
						}
					}else if("r.storeLevel.2".equals(storeLevel)){
						MTeamBill mTeamBill = new MTeamBill();
						mTeamBill.setBillNo(Snow.getUUID()+"");
						mTeamBill.setStoreNo(storeNo);
						mTeamBill.setTeamNo(teamNo);
						mTeamBill.setMatNo(matNo);
						mTeamBill.setOccAmount(new BigDecimal(amount+""));
						mTeamBill.setMatPrice(matPrice);
						mTeamBill.setOccDate(XDate.getDate());
						mTeamBill.setRemark("ERP领料，入库");
						mTeamBill.setModiEmp(userInfo.getUuid());
						mTeamBill.setModiDate(XDate.getDate());
						mTeamBill.setModiTime(XDate.getTime());
						mTeamBill.setDataSrc("m.dataSrc.1");
						mTeamBill.setMatStatus("r.stockStatus.1");
						mTeamBillMapper.saveTeamBill(mTeamBill);

						MTeamStock mTeamStock = new MTeamStock();
						mTeamStock.setMatAmount(new BigDecimal(amount+""));
						mTeamStock.setUseAmount(new BigDecimal("0"));
						mTeamStock.setTeamNo(teamNo);
						mTeamStock.setStoreNo(storeNo);
						mTeamStock.setMatNo(matNo);
						mTeamStock.setMatStatus("r.stockStatus.1");
						if(mTeamStockMapper.updateTeamStock(mTeamStock)<1){
							mTeamStock.setSiteCode(site);
							mTeamStockMapper.insertTeamStock(mTeamStock);
						}
						mTeamStock = mTeamStockMapper.getTeamStock(teamNo+"", storeNo, matNo, "r.stockStatus.1");
						if(mTeamStock.getMatAmount().doubleValue()<0){
							throw new XException(XException.ERR_DEFAULT, "总数不能小于0");
						}
						double occBala = amount * matPrice.doubleValue();

						MTeamStockLog mTeamStockLog = new MTeamStockLog();
						mTeamStockLog.setLogId(Snow.getUUID()+"");
						mTeamStockLog.setStoreNo(storeNo);
						mTeamStockLog.setTeamNo(teamNo);
						mTeamStockLog.setMatNo(matNo);
						mTeamStockLog.setOccDate(XDate.getDate());
						mTeamStockLog.setOccAmount(new BigDecimal(amount+""));
						mTeamStockLog.setOccBala(new BigDecimal(occBala+""));
						mTeamStockLog.setInoutFlag("r.inOutFlag.1");
						mTeamStockLog.setDataSrc("m.dataSrc.1");
						mTeamStockLog.setBillNo(billNo);
						mTeamStockLog.setMatStatus("r.stockStatus.1");
					}else{
						throw new XException(XException.ERR_DEFAULT, "仓库信息不正确！");
					}
				}

				MIn mIn1 = new MIn();
				mIn1.setBillNo(billNo);
				mIn1.setStoreNo(storeNo);
				mIn1.setSetPrice(new BigDecimal(price+""));
				mIn1.setInAmount(new BigDecimal(amount+""));
				mIn1.setInDate(XDate.getDate());
				mIn1.setInEmp(userInfo.getUuid());
				mIn1.setBillAmount(new BigDecimal(amount+""));
				mIn1.setBillDate(XDate.getDate());
				mIn1.setBillEmp(userInfo.getUuid());
				mIn1.setInStep("7201F");
				mIn1.setReserve4(prod);
				if("1".equals(ifAccount)){
					mIn1.setOutAmount(new BigDecimal(amount+""));
				}
				mInMapper.updateMIn(mIn1);

				TCheck tCheck = new TCheck();
				tCheck.setCheckNo(checkNo);
				tCheck.setStepKey("7201");
				tCheck.setStepCode("72011");
				tCheck.setCheckType("sys.checkType.0");
				tCheck.setDirect("sys.checkDirect.0");
				tCheck.setIdea("");
				tCheck.setUserId(userInfo.getUuid());
				tCheck.setUserName(userInfo.getUserName());
				tCheck.setTeamNo(Integer.parseInt(userInfo.getOrgId()));
				tCheck.setOccDate(XDate.getDate());
				tCheck.setOccTime(XDate.getTime());
				tCheck.setLogInfo("");
				tCheck.setBefAmount(new BigDecimal(inAmount+""));
				tCheck.setBefPrice(matPrice);
				tCheck.setAftAmount(new BigDecimal(amount+""));
				tCheck.setAftPrice(new BigDecimal(price+""));
				tCheck.setEmpId(0L);
				tCheckMapper.executeSave(tCheck);
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	@Transactional
	public void deletePlanIn(String billNo) throws Exception{
		try{
			MIn mIn = mInMapper.getMInByBillNo(billNo);
			String inStep = mIn.getInStep();
			if("7201F".equals(inStep) || "7201X".equals(inStep) || "7201Z".equals(inStep)){
				throw new XException(XException.ERR_DEFAULT, "非未定稿入库！");
			}
			if(!"r.inBillType.1".equals(mIn.getInType())){
				throw new XException(XException.ERR_DEFAULT, "非计划入库！");
			}

			mInMapper.deleteMIn(billNo);

		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	public PageSet<MIn> getBcDrawQryPageSet(PageParam pageParam, String filterSort, String date1Qry, String date2Qry, String userId, boolean ifAllQry, String matQry, String storeNoQry, String inTypeQry, String wbsQry, String inStepQry, String accountQry, String centerQry, String periodQry, String periodDay, String userDeptIds){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MIn> list = mInMapper.getBcDrawQry(date1Qry, date2Qry, userId, ifAllQry, matQry, storeNoQry, inTypeQry, wbsQry, inStepQry, accountQry, centerQry, periodQry, periodDay, userDeptIds);
		PageInfo<MIn> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public List<MIn> getBcDrawQryList(String date1Qry, String date2Qry, String userId, boolean ifAllQry, String matQry, String storeNoQry, String inTypeQry, String wbsQry, String inStepQry, String accountQry, String centerQry, String periodQry, String periodDay, String userDeptIds){
		return mInMapper.getBcDrawQry(date1Qry, date2Qry, userId, ifAllQry, matQry, storeNoQry, inTypeQry, wbsQry, inStepQry, accountQry, centerQry, periodQry, periodDay, userDeptIds);
	}

	@Override
	@Transactional
	public int backDrawIn(String billNo, UserInfo userInfo) throws Exception{
		try {
			MIn mIn = mInMapper.getMInByBillNo(billNo);
			double amount = mIn.getBillAmount().doubleValue();
			String matNo = mIn.getMatNo();
			Integer teamNo = mIn.getTeamNo();
			String storeNo = mIn.getStoreNo();
			BigDecimal price = mIn.getSetPrice();
			String planSrc = mIn.getPlanSrc();
			String planNo = mIn.getPlanNo();

			MStore mStore = mStoreMapper.getStoreByNo(storeNo);
			String storeLevel = mStore.getStoreLevel();
			Integer mngTeam = mStore.getMngTeam();
			if (!userInfo.getOrgId().equals(String.valueOf(mngTeam))) {
				throw new XException(XException.ERR_DEFAULT, "非仓库管理部门");
			}
			String nowDate = XDate.getDate();
			String empNo = userInfo.getUuid();

			if ("r.storeLevel.1".equals(storeLevel)) {
				MStock mStock = new MStock();
				mStock.setStoreNo(storeNo);
				mStock.setMatNo(matNo);
				mStock.setInAmount(new BigDecimal(-amount + ""));
				mStock.setStockAmount(new BigDecimal(-amount + ""));
				mStock.setBulkAmount(new BigDecimal(-amount + ""));
				mStockMapper.updateStock(mStock);

				mStockService.checkStockAmount(matNo, storeNo);
			} else if ("r.storeLevel.2".equals(storeLevel)) {
				String dkBill = Snow.getUUID() + "";
				String remark = "ERP领料，入库，冲单";

				MTeamBill mTeamBill = new MTeamBill();
				mTeamBill.setBillNo(dkBill);
				mTeamBill.setStoreNo(storeNo);
				mTeamBill.setTeamNo(teamNo);
				mTeamBill.setMatNo(matNo);
				mTeamBill.setOccAmount(new BigDecimal(-amount + ""));
				mTeamBill.setMatPrice(price);
				mTeamBill.setOccDate(nowDate);
				mTeamBill.setPrjNo("");
				mTeamBill.setDrawEmp("");
				mTeamBill.setUseAddr("");
				mTeamBill.setNormNo("");
				mTeamBill.setIfAssess("");
				mTeamBill.setIfStock("");
				mTeamBill.setRemark(remark);
				mTeamBill.setModiEmp(empNo);
				mTeamBill.setModiDate(nowDate);
				mTeamBill.setModiTime(XDate.getTime());
				mTeamBill.setDataSrc("m.dataSrc.1");
				mTeamBill.setAccountType("");
				mTeamBill.setMatStatus("r.stockStatus.1");
				mTeamBillMapper.saveTeamBill(mTeamBill);

				MTeamStock mTeamStock = new MTeamStock();
				mTeamStock.setTeamNo(teamNo);
				mTeamStock.setStoreNo(storeNo);
				mTeamStock.setMatNo(matNo);
				mTeamStock.setMatStatus("r.stockStatus.1");
				mTeamStock.setMatAmount(new BigDecimal(-amount + ""));
				mTeamStock.setUseAmount(new BigDecimal("0"));
				int c = mTeamStockMapper.updateTeamStock(mTeamStock);

				if (c < 1) {
					mTeamStock.setSiteCode("");
					mTeamStockMapper.insertTeamStock(mTeamStock);
				}

				mTeamStock = mTeamStockMapper.getTeamStock(teamNo + "", storeNo, matNo, "r.stockStatus.1");
				if (mTeamStock.getMatAmount().doubleValue() < 0) {
					throw new XException(XException.ERR_DEFAULT, "总数不能小于零");
				}

				BigDecimal occBala = price.multiply(new BigDecimal(-amount + ""));
				MTeamStockLog mTeamStockLog = new MTeamStockLog();
				mTeamStockLog.setLogId(Snow.getUUID() + "");
				mTeamStockLog.setTeamNo(teamNo);
				mTeamStockLog.setStoreNo(storeNo);
				mTeamStockLog.setMatNo(matNo);
				mTeamStockLog.setOccDate(nowDate);
				mTeamStockLog.setOccAmount(new BigDecimal(-amount + ""));
				mTeamStockLog.setOccBala(occBala);
				mTeamStockLog.setInoutFlag("r.inOutFlag.1");
				mTeamStockLog.setDataSrc("m.dataSrc.1");
				mTeamStockLog.setBillNo(dkBill);
				mTeamStockLog.setMatStatus("r.stockStatus.1");
				mTeamStockLogMapper.insertTeamStockLog(mTeamStockLog);
			} else {
				throw new XException(XException.ERR_DEFAULT, "仓库信息不正确！");
			}

			String backNo = Snow.getUUID() + "";
			MInBack mInBack = new MInBack();
			mInBack.setBackId(backNo);
			mInBack.setBackStep("7201F");
			mInBack.setStoreNo(storeNo);
			mInBack.setMatNo(matNo);
			mInBack.setBackAmount(new BigDecimal(amount + ""));
			mInBack.setBackDate(nowDate);
			mInBack.setBackEmp(empNo);
			mInBack.setBackInfo("");
			mInBack.setCheckNo("0");
			mInBack.setOfferNo("");
			mInBack.setInitBill(billNo);
			mInBack.setRedBill("0");
			mInBackMapper.insertInBack(mInBack);

			MIn mIn1 = new MIn();
			mIn1.setBillNo(backNo);
			mIn1.setInType("r.inBillType.3");
			mIn1.setPlanSrc(planSrc);
			mIn1.setPlanNo(planNo);
			mIn1.setStoreNo(storeNo);
			mIn1.setReserveNo(mIn.getReserveNo());
			mIn1.setMatNo(matNo);
			mIn1.setApplyAmount(new BigDecimal("0"));
			mIn1.setApplyDate(mIn.getApplyDate());
			mIn1.setApplyEmp(mIn.getApplyEmp());
			mIn1.setInAmount(new BigDecimal("0"));
			mIn1.setInDate(mIn.getInDate());
			mIn1.setInEmp(mIn.getInEmp());
			mIn1.setBillAmount(new BigDecimal(-amount + ""));
			mIn1.setSetPrice(price);
			mIn1.setBillDate(nowDate);
			mIn1.setBillEmp(mIn.getBillEmp());
			mIn1.setOfferNo("");
			mIn1.setInStep(mIn.getInStep());
			mIn1.setCheckNo("");
			mIn1.setRemark("退货");
			mIn1.setDataSrc(mIn.getDataSrc());
			mIn1.setPrice1(new BigDecimal("0"));
			mIn1.setPrice2(new BigDecimal("0"));
			mIn1.setPrice3(new BigDecimal("0"));
			mIn1.setLinkNo(billNo);
			mIn1.setNormNo("");
			mIn1.setReserve1("");
			mIn1.setReserve2("");
			mIn1.setReserve3("");
			mIn1.setReserve4("");
			mIn1.setErpBill("");
			mInMapper.saveIn(mIn1);
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
		return 1;
	}

	@Override
	public double getBillAmount(String planNo){
		return mInMapper.getBillAmount(planNo);
	}

	@Override
	@Transactional
	public void insertTurnReg(HttpServletRequest request, UserInfo userInfo) throws Exception{
		String occDate = XDate.dateTo8(request.getParameter("occDate"));
		String backAddr = request.getParameter("backAddr");
		String storeAddr = request.getParameter("storeAddr");
		String inType = "r.inBillType.A";
		String userId = userInfo.getUuid();
		String planSrc = "";
		String reserveNo = "";
		String inStep = "77013";
		String checkNo = "";
		String dataSrc = "m.dataSrc.A";

		try{
			String[] planNos = request.getParameterValues("planNo");
			if(planNos==null || planNos.length<1){
				throw new XException(XException.ERR_DEFAULT, "请选择要提交的计划！");
			}
			for(int i=0; i<planNos.length; i++){
				String planNo = planNos[i];
				double amount = 0;
				try{
					amount = Double.parseDouble(request.getParameter("amount_"+planNo));
				}catch (Exception e){
					throw new XException(XException.ERR_DEFAULT, "本次回收数必须是数字！");
				}
				if(amount <= 0){
					throw new XException(XException.ERR_DEFAULT, "本次回收数必须是正数！");
				}
				String remark = request.getParameter("remark_"+planNo);

				MBackPlan mBackPlan = mBackPlanMapper.getBackPlan(planNo);
				Integer teamNo = mBackPlan.getTeamNo();
				Integer mngTeam = mBackPlan.getPlanTeam();
				String matNo = mBackPlan.getMatNo();
				BigDecimal matPrice = mBackPlan.getMatPrice();
				BigDecimal fixPrice = mBackPlan.getAwardBala();
				String prjNo = mBackPlan.getPrjNo();
				String storeNo = "";
				List<MStore> storeList = mStoreMapper.getDZK("r.storeType2.14", mngTeam+"");
				if(storeList.size()<1){
					throw new XException(XException.ERR_DEFAULT, "管理科室没有旧料库！");
				}
				storeNo = storeList.get(0).getStoreNo();

				MIn mIn = new MIn();
				mIn.setBillNo(Snow.getUUID()+"");
				mIn.setInType(inType);
				mIn.setPlanSrc(planSrc);
				mIn.setPlanNo(planNo);
				mIn.setTeamNo(teamNo);
				mIn.setOfferTeam(mngTeam);
				mIn.setStoreNo(storeNo);
				mIn.setReserveNo(reserveNo);
				mIn.setMatNo(matNo);
				mIn.setApplyAmount(new BigDecimal(amount+""));
				mIn.setApplyDate(occDate);
				mIn.setApplyEmp(userId);
				mIn.setInAmount(new BigDecimal(amount+""));
				mIn.setInDate(occDate);
				mIn.setInEmp("");
				mIn.setBillAmount(new BigDecimal("0"));
				mIn.setSetPrice(matPrice);
				mIn.setBillDate("");
				mIn.setBillEmp("");
				mIn.setOfferNo("");
				mIn.setInStep(inStep);
				mIn.setCheckNo(checkNo);
				mIn.setRemark(remark);
				mIn.setDataSrc(dataSrc);
				mIn.setPrice1(fixPrice);
				mIn.setPrice2(new BigDecimal("0"));
				mIn.setPrice3(new BigDecimal("0"));
				mIn.setLinkNo("");
				mIn.setNormNo(prjNo);
				mIn.setReserve1(storeAddr);
				mIn.setReserve2("");
				mIn.setReserve3("");
				mIn.setReserve4("");
				mIn.setErpBill("");
				mInMapper.saveIn(mIn);
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	public PageSet<MIn> getTurnRegPageSet(PageParam pageParam, String filterSort, String date1Qry, String date2Qry, String matQry, String stepQry, String userTeam){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MIn> list = mInMapper.getTurnRegList(date1Qry, date2Qry, matQry, stepQry, userTeam);
		PageInfo<MIn> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public void deleteTurnReg(String billNo) throws Exception{
		MIn mIn = mInMapper.getMInByBillNo(billNo);
		if("7701F".equals(mIn.getInStep()) || "77017".equals(mIn.getInStep())){
			throw new XException(XException.ERR_DEFAULT, "记录已经过科室审核，不能删除！");
		}

		mInMapper.deleteMIn(billNo);
	}

	@Override
	public PageSet<MIn> getTurnChkPageSet(PageParam pageParam, String filterSort, String date1Qry, String date2Qry, String userId, String userTeam, String matQry){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MIn> list = mInMapper.getTurnCheckList(date1Qry, date2Qry, userId, userTeam, matQry);
		PageInfo<MIn> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	@Transactional
	public void submitTurnChk(HttpServletRequest request, UserInfo userInfo) throws Exception{
		String nextStep = "7701F";
		String userNo = userInfo.getUuid();
		String occDate = XDate.getDate();
		String dataSrc = "m.dataSrc.P";
		String[] billNos = request.getParameterValues("billNo");

		try{
			for(int i=0; i<billNos.length; i++){
				String billNo = billNos[i];
				String prvStep = request.getParameter("step_"+billNo);
				double amount = 0;
				try{
					amount = Double.parseDouble(request.getParameter("amount_"+billNo));
				}catch (Exception e){
					throw new XException(XException.ERR_DEFAULT, "审批数量必须是数字！");
				}
				String checkInfo = request.getParameter("info_"+billNo);
				String stockAddr = request.getParameter("addr_"+billNo);
				String getTeam = request.getParameter("team_"+billNo);

				MIn mIn = mInMapper.getMInByBillNo(billNo);
				String nowStep = mIn.getInStep();
				if(!prvStep.equals(nowStep)){
					throw new XException(XException.ERR_DEFAULT, "记录非待审状态！");
				}
				String matNo = mIn.getMatNo();
				String storeNo = mIn.getStoreNo();

				if(!"".equals(getTeam) && "77013".equals(nowStep)){
					nextStep = "77015";
				}
				if("".equals(getTeam) && "77013".equals(nowStep)){
					nextStep = "77017";
				}
				if("77017".equals(nowStep)){
					getTeam = mIn.getOutTeam()+"";
				}
				if("77015".equals(nextStep) || "77017".equals(nextStep)){
					MIn mIn1 = new MIn();
					mIn1.setInAmount(new BigDecimal(amount+""));
					mIn1.setInDate(XDate.getDate());
					mIn1.setInEmp(userNo);
					mIn1.setInStep(nextStep);
					mIn1.setCheckInfo(checkInfo);
					mIn1.setReserve1(stockAddr);
					mIn1.setOutTeam(Integer.parseInt("".equals(getTeam) ? "0" : getTeam));
					mIn1.setBillNo(billNo);
					mInMapper.updateMIn(mIn1);
				}
				if("7701F".equals(nextStep)){
					MIn mIn1 = new MIn();
					mIn1.setBillAmount(new BigDecimal(amount+""));
					mIn1.setBillEmp(userNo);
					mIn1.setBillDate(XDate.getDate());
					mIn1.setInStep(nextStep);
					mIn1.setCheckInfo(checkInfo);
					mIn1.setReserve1(stockAddr);
					mIn1.setOutTeam(Integer.parseInt(getTeam));
					mIn1.setBillNo(billNo);
					mInMapper.updateMIn(mIn1);

					if("".equals(getTeam)){
						MStock mStock = new MStock();
						mStock.setStoreNo(storeNo);
						mStock.setMatNo(matNo);
						mStock.setInAmount(new BigDecimal(amount+""));
						mStock.setOutAmount(new BigDecimal("0"));
						mStock.setStockAmount(new BigDecimal(amount+""));
						mStock.setPackAmount(new BigDecimal("0"));
						mStock.setLockAmount(new BigDecimal("0"));
						mStock.setBulkAmount(new BigDecimal(amount+""));
						int c = mStockMapper.updateStock(mStock);
						if(c<1){
							mStock.setSiteCode("");
							mStockMapper.insertStock(mStock);
						}
					}

					mStockService.checkStockAmount(matNo, storeNo);
				}else{
					String inEmp = mIn.getInEmp();
					Integer offerTeam = mIn.getTeamNo();
					Integer teamNo = mIn.getOutTeam();
					String userAddr = mIn.getCheckAddr();
					BigDecimal matPrice = mIn.getSetPrice();
					BigDecimal price1 = mIn.getPrice1();

					MOut mOut = new MOut();
					mOut.setDrawNo(Snow.getUUID()+"");
					mOut.setOutType("");
					mOut.setPlanNo(billNo);
					mOut.setPlanTeam(teamNo+"");
					mOut.setMatNo(matNo);
					mOut.setApplyAmount(new BigDecimal(amount+""));
					mOut.setApplyDate(occDate);
					mOut.setApplyTime(XDate.getTime());
					mOut.setApplyEmp(userNo);
					mOut.setUrgentLevel("");
					mOut.setStoreAddr("");
					mOut.setIfSend("");
					mOut.setUseAddr(userAddr);
					mOut.setApplyInfo("");
					mOut.setChkAmount(new BigDecimal(amount+""));
					mOut.setAgreeEmp(inEmp);
					mOut.setAbcType("");
					mOut.setDrawStep("7202F");
					mOut.setCheckNo("0");
					mOut.setOutAmount(new BigDecimal(amount+""));
					mOut.setDrawEmp(userNo);
					mOut.setStoreNo(storeNo);
					mOut.setTeamNo(teamNo);
					mOut.setOutDate(occDate);
					mOut.setOutTime(XDate.getTime());
					mOut.setItemNo("");
					mOut.setPlanPrice(matPrice);
					mOut.setMatPrice(matPrice);
					mOut.setPrjNo("0");
					mOut.setPrice1(price1);
					mOut.setPrice2(new BigDecimal("0"));
					mOut.setDataSrc(dataSrc);
					mOut.setLinkNo("");
					mOut.setOfferTeam(offerTeam);
					mOut.setUuid(BaseUtils.UUIDGenerator());
					mOutMapper.insertOut(mOut);
				}
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	@Transactional
	public void backTurnChk(HttpServletRequest request, UserInfo userInfo) throws Exception{
		String[] billNos = request.getParameterValues("billNo");

		try{
			for(int i=0; i<billNos.length; i++){
				String billNo = billNos[i];
				String prvStep = request.getParameter("step_"+billNo);

				MIn mIn = mInMapper.getMInByBillNo(billNo);
				String nowStep = mIn.getInStep();
				if(!prvStep.equals(nowStep)){
					throw new XException(XException.ERR_DEFAULT, "记录非待审状态！");
				}

				MIn m = new MIn();
				m.setInStep("7701X");
				m.setBillDate(XDate.getDate());
				m.setBillEmp(userInfo.getUuid());
				m.setBillNo(billNo);
				mInMapper.updateMIn(m);
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	public PageSet<MIn> getTurnConfPageSet(PageParam pageParam, String filterSort, String date1Qry, String date2Qry, String matQry, String userTeam){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MIn> list = mInMapper.getTurnConfList(date1Qry, date2Qry, matQry, userTeam);
		PageInfo<MIn> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	@Transactional
	public void submitTurnConf(HttpServletRequest request, UserInfo userInfo) throws Exception{
		String nextStep = "77017";
		String userNo = userInfo.getUuid();
		String occDate = XDate.getDate();
		String[] billNos = request.getParameterValues("billNo");

		try{
			for(int i=0; i<billNos.length; i++){
				String billNo = billNos[i];
				String useAddr = request.getParameter("addr_"+billNo);

				MIn mIn = mInMapper.getMInByBillNo(billNo);
				String nowStep = mIn.getInStep();
				if(!"77015".equals(nowStep)){
					throw new XException(XException.ERR_DEFAULT, "记录非确认状态！");
				}

				mInMapper.updMIn(nextStep, occDate, userNo, useAddr, billNo);
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Transactional
	@Override
	public void backTurnConf(HttpServletRequest request, UserInfo userInfo) throws Exception{
		try{
			String[] billNos = request.getParameterValues("billNos");

			for(int i=0; i<billNos.length; i++){
				String billNo = billNos[i];

				MIn mIn = mInMapper.getMInByBillNo(billNo);
				String nowStep = mIn.getInStep();
				if(!"77015".equals(nowStep)){
					throw new XException(XException.ERR_DEFAULT, "记录非确认状态！");
				}

				MIn m = new MIn();
				m.setInStep("77013");
				m.setBillDate(XDate.getDate());
				m.setBillEmp(userInfo.getUuid());
				m.setBillNo(billNo);
				mInMapper.updateMIn(m);
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	public PageSet<MIn> getReturnMatList(PageParam pageParam, String filterSort, String userTeam, String date1Qry, String date2Qry, String matQry){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MIn> list = mInMapper.getReturnMatList(userTeam, date1Qry, date2Qry, matQry);
		PageInfo<MIn> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	@Transactional
	public void insertReturnMat(HttpServletRequest request, UserInfo userInfo) throws Exception{
		try{
			String storeNo = request.getParameter("storeNo");
			String userTeam = userInfo.getOrgId();
			if("".equals(storeNo)){
				throw new XException(XException.ERR_DEFAULT, "必须选择仓库！");
			}
			String occDate = XDate.getDate();
			String userEmp = userInfo.getUuid();
			String inType = "r.inBillType.B";
			String nextStep = "77013";
			String planSrc = "";

			String[] keys = request.getParameterValues("planNo");
			for(int i=0; i<keys.length; i++){
				String key = keys[i];
				String planNo = key.split("_")[1];
				String matNo = key.split("_")[0];
				double whAmount = 0;
				double dxAmount = 0;
				double bfAmount = 0;
				try {
					String wh = request.getParameter("wh_"+key);
					whAmount = Double.parseDouble("".equals(wh) ? "0" : wh);
				}catch (Exception e){
					throw new XException(XException.ERR_DEFAULT, "完好数必须是数字！");
				}
				try {
					String dx = request.getParameter("dx_"+key);
					dxAmount = Double.parseDouble("".equals(dx) ? "0" : dx);
				}catch (Exception e){
					throw new XException(XException.ERR_DEFAULT, "待修数必须是数字！");
				}
				try {
					String bf = request.getParameter("bf_"+key);
					bfAmount = Double.parseDouble("".equals(bf) ? "0" : bf);
				}catch (Exception e){
					throw new XException(XException.ERR_DEFAULT, "报废数必须是数字！");
				}
				if(whAmount<0){
					throw new XException(XException.ERR_DEFAULT, "完好数必须是正数！");
				}
				if(dxAmount<0){
					throw new XException(XException.ERR_DEFAULT, "待修数必须是正数！");
				}
				if(bfAmount<0){
					throw new XException(XException.ERR_DEFAULT, "报废数必须是正数！");
				}

				String remark = request.getParameter("remark_"+key);
				String prjNo = request.getParameter("prj_"+key);
				MPrj mPrj = mPrjMapper.getPrjByPrjNo(prjNo);
				String backAddr = mPrj.getPrjName();

				MMaterial mMaterial = mMaterialMapper.getMatByNo(matNo);
				BigDecimal price = mMaterial.getMatPrice();
				BigDecimal fixPrice = mMaterial.getFixPrice();

				MBackPlan mBackPlan = mBackPlanMapper.getBackPlan(planNo);
				if(mBackPlan==null){
					throw new XException(XException.ERR_DEFAULT, "计划单位，不存在！");
				}
				Integer mngTeam = mBackPlan.getPlanTeam();

				if(whAmount > 0){
					String billNo = Snow.getUUID()+"";
					planSrc = "m.xjlfStatus.1";
					MStore mStore = mStoreMapper.getStoreByNo(storeNo);
					String storeType = mStore.getStoreType();
					if(!"r.storeType2.14".equals(storeType)){
						throw new XException(XException.ERR_DEFAULT, "非旧料仓库！");
					}
					String checkNo = Snow.getUUID()+"";
					MIn mIn = new MIn();
					mIn.setBillNo(billNo);
					mIn.setInType(inType);
					mIn.setPlanSrc(planSrc);
					mIn.setPlanNo(planNo);
					mIn.setTeamNo(Integer.parseInt(userTeam));
					mIn.setOfferTeam(mngTeam);
					mIn.setStoreNo(storeNo);
					mIn.setReserveNo("");
					mIn.setMatNo(matNo);
					mIn.setApplyAmount(new BigDecimal(whAmount+""));
					mIn.setApplyDate(occDate);
					mIn.setApplyEmp(userEmp);
					mIn.setInAmount(new BigDecimal(whAmount+""));
					mIn.setInDate(occDate);
					mIn.setInEmp(userEmp);
					mIn.setBillAmount(new BigDecimal("0"));
					mIn.setSetPrice(price);
					mIn.setBillDate("");
					mIn.setBillEmp("");
					mIn.setOfferNo(userTeam);
					mIn.setInStep(nextStep);
					mIn.setCheckNo(checkNo);
					mIn.setRemark(remark);
					mIn.setDataSrc("m.dataSrc.A");
					mIn.setPrice1(fixPrice);
					mIn.setPrice2(new BigDecimal("0"));
					mIn.setPrice3(new BigDecimal("0"));
					mIn.setLinkNo("");
					mIn.setNormNo(prjNo);
					mIn.setReserve1("回收申请，完好");
					mIn.setReserve2(backAddr);
					mIn.setReserve3("");
					mIn.setReserve4("");
					mIn.setErpBill("");
					mInMapper.saveIn(mIn);
				}
				if(dxAmount > 0){
					String billNo = Snow.getUUID()+"";
					planSrc = "m.xjlfStatus.2";
					String checkNo = Snow.getUUID()+"";

					MIn mIn = new MIn();
					mIn.setBillNo(billNo);
					mIn.setInType(inType);
					mIn.setPlanSrc(planSrc);
					mIn.setPlanNo(planNo);
					mIn.setTeamNo(Integer.parseInt(userTeam));
					mIn.setOfferTeam(mngTeam);
					mIn.setStoreNo(storeNo);
					mIn.setReserveNo("");
					mIn.setMatNo(matNo);
					mIn.setApplyAmount(new BigDecimal(dxAmount+""));
					mIn.setApplyDate(occDate);
					mIn.setApplyEmp(userEmp);
					mIn.setInAmount(new BigDecimal(dxAmount+""));
					mIn.setInDate(occDate);
					mIn.setInEmp(userEmp);
					mIn.setBillAmount(new BigDecimal("0"));
					mIn.setSetPrice(price);
					mIn.setBillDate("");
					mIn.setBillEmp("");
					mIn.setOfferNo(userTeam);
					mIn.setInStep(nextStep);
					mIn.setCheckNo(checkNo);
					mIn.setRemark(remark);
					mIn.setDataSrc("m.dataSrc.A");
					mIn.setPrice1(fixPrice);
					mIn.setPrice2(new BigDecimal("0"));
					mIn.setPrice3(new BigDecimal("0"));
					mIn.setLinkNo("");
					mIn.setNormNo(prjNo);
					mIn.setReserve1("回收申请，待修");
					mIn.setReserve2(backAddr);
					mIn.setReserve3("");
					mIn.setReserve4("");
					mIn.setErpBill("");
					mInMapper.saveIn(mIn);
				}
				if(bfAmount > 0){
					String billNo = Snow.getUUID()+"";
					planSrc = "m.xjlfStatus.3";
					List<MStore> storeList = mStoreMapper.getStoreList(null, null, null, null, null, null, "r.storeType2.61", null);
					if(storeList.size() < 1){
						throw new XException(XException.ERR_DEFAULT, "没有待报废库！");
					}
					String checkNo = Snow.getUUID()+"";
					String bfStore = storeList.get(0).getStoreNo();

					MIn mIn = new MIn();
					mIn.setBillNo(billNo);
					mIn.setInType(inType);
					mIn.setPlanSrc(planSrc);
					mIn.setPlanNo(planNo);
					mIn.setTeamNo(Integer.parseInt(userTeam));
					mIn.setOfferTeam(mngTeam);
					mIn.setStoreNo(bfStore);
					mIn.setReserveNo("");
					mIn.setMatNo(matNo);
					mIn.setApplyAmount(new BigDecimal(bfAmount+""));
					mIn.setApplyDate(occDate);
					mIn.setApplyEmp(userEmp);
					mIn.setInAmount(new BigDecimal(bfAmount+""));
					mIn.setInDate(occDate);
					mIn.setInEmp(userEmp);
					mIn.setBillAmount(new BigDecimal("0"));
					mIn.setSetPrice(price);
					mIn.setBillDate("");
					mIn.setBillEmp("");
					mIn.setOfferNo(userTeam);
					mIn.setInStep(nextStep);
					mIn.setCheckNo(checkNo);
					mIn.setRemark(remark);
					mIn.setDataSrc("m.dataSrc.A");
					mIn.setPrice1(fixPrice);
					mIn.setPrice2(new BigDecimal("0"));
					mIn.setPrice3(new BigDecimal("0"));
					mIn.setLinkNo("");
					mIn.setNormNo(prjNo);
					mIn.setReserve1("回收申请，报废");
					mIn.setReserve2(backAddr);
					mIn.setReserve3("");
					mIn.setReserve4("");
					mIn.setErpBill("");
					mInMapper.saveIn(mIn);
				}
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	public void deleteReturnMat(HttpServletRequest request, UserInfo userInfo) throws Exception{
		String billNo = request.getParameter("billNo");

		MIn m = mInMapper.getMInByBillNo(billNo);
		String inStep = m.getInStep();
		if("7701F".equals(inStep)){
			throw new XException(XException.ERR_DEFAULT, "已完成验收，不能删除！");
		}

		mInMapper.deleteMIn(billNo);
	}

	@Override
	public PageSet<MIn> getReturnChkList(PageParam pageParam, String filterSort, String userNo, String userTeam, String matQry, String storeQry){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MIn> list = mInMapper.getReturnChkList(userNo, userTeam, matQry, storeQry);
		PageInfo<MIn> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	@Transactional
	public void submitReturnChk(HttpServletRequest request, UserInfo userInfo) throws Exception{
		String userId = userInfo.getUuid();
		String occDate = XDate.getDate();
		String userName = userInfo.getUserName();
		String userTeam = userInfo.getOrgId();

		try{
			String[] billNos = request.getParameterValues("billNo");
			for(int i=0; i<billNos.length; i++){
				String billNo = billNos[i];
				String prvStep = request.getParameter("step_"+billNo);

				double amount = 0;
				try {
					amount = Double.parseDouble(request.getParameter("amount_"+billNo));
				}catch (Exception e){
					throw new XException(XException.ERR_DEFAULT, "审核数量必须是数字！");
				}

				MIn mIn = mInMapper.getMInByBillNo(billNo);
				if(!prvStep.equals(mIn.getInStep())){
					throw new XException(XException.ERR_DEFAULT, "步骤不符！");
				}

				BigDecimal oldAmount = mIn.getInAmount();
				BigDecimal oldPrice = mIn.getSetPrice();
				String storeNo = mIn.getStoreNo();
				String matNo = mIn.getMatNo();
				String checkNo = mIn.getCheckNo();
				String nextStep = getNextInStep(prvStep);

				MIn m = new MIn();
				if("7701F".equals(nextStep)){
					mIn.setBillAmount(new BigDecimal(amount+""));
					mIn.setBillDate(occDate);
					mIn.setBillEmp(userId);
				}else{
					mIn.setInAmount(new BigDecimal(amount+""));
					mIn.setInDate(occDate);
					mIn.setInEmp(userId);
				}
				mIn.setInStep(nextStep);
				mIn.setBillNo(billNo);
				mInMapper.updateMIn(mIn);

				if("7701F".equals(nextStep)){
					MStock mStock = new MStock();
					mStock.setInAmount(new BigDecimal(amount+""));
					mStock.setOutAmount(new BigDecimal("0"));
					mStock.setStockAmount(new BigDecimal(amount+""));
					mStock.setPackAmount(new BigDecimal("0"));
					mStock.setLockAmount(new BigDecimal("0"));
					mStock.setBulkAmount(new BigDecimal(amount+""));
					mStock.setMatNo(matNo);
					mStock.setStoreNo(storeNo);
					int c = mStockMapper.updateStock(mStock);
					if(c<1){
						mStock.setSiteCode("");
						mStockMapper.insertStock(mStock);
					}
				}

				TCheck tCheck = new TCheck();
				tCheck.setCheckNo(checkNo);
				tCheck.setStepKey("7701");
				tCheck.setStepCode(prvStep);
				tCheck.setCheckType("sys.checkType.0");
				tCheck.setDirect("sys.checkDirect.0");
				tCheck.setIdea("");
				tCheck.setUserId(userId);
				tCheck.setUserName(userName);
				tCheck.setTeamNo(Integer.parseInt(userTeam));
				tCheck.setOccDate(occDate);
				tCheck.setOccTime(XDate.getTime());
				tCheck.setLogInfo("");
				tCheck.setBefAmount(oldAmount);
				tCheck.setBefPrice(oldPrice);
				tCheck.setAftAmount(new BigDecimal(amount+""));
				tCheck.setAftPrice(oldPrice);
				tCheck.setEmpId(0L);
				tCheckMapper.executeSave(tCheck);
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	@Transactional
	public void backReturnChk(HttpServletRequest request, UserInfo userInfo) throws Exception{
		String userId = userInfo.getUuid();
		String occDate = XDate.getDate();
		String userName = userInfo.getUserName();
		String userTeam = userInfo.getOrgId();

		try{
			String[] billNos = request.getParameterValues("billNo");
			for(int i=0; i<billNos.length; i++){
				String billNo = billNos[i];
				String prevStep = request.getParameter("step_"+billNo);

				MIn mIn = mInMapper.getMInByBillNo(billNo);
				if(!prevStep.equals(mIn.getInStep())){
					throw new XException(XException.ERR_DEFAULT, "步骤不符！");
				}
				String checkNo = mIn.getCheckNo();
				BigDecimal oldAmt = mIn.getInAmount();
				BigDecimal price = mIn.getSetPrice();

				MIn m = new MIn();
				m.setInStep("7701X");
				m.setBillDate(occDate);
				m.setBillEmp(userId);
				m.setBillNo(billNo);
				mInMapper.updateMIn(m);

				TCheck tCheck = new TCheck();
				tCheck.setCheckNo(checkNo);
				tCheck.setStepKey("7701");
				tCheck.setStepCode(prevStep);
				tCheck.setCheckType("sys.checkType.0");
				tCheck.setDirect("sys.checkDirect.1");
				tCheck.setIdea("");
				tCheck.setUserId(userId);
				tCheck.setUserName(userName);
				tCheck.setTeamNo(Integer.parseInt(userTeam));
				tCheck.setOccDate(occDate);
				tCheck.setOccTime(XDate.getTime());
				tCheck.setLogInfo("");
				tCheck.setBefAmount(oldAmt);
				tCheck.setBefPrice(price);
				tCheck.setAftAmount(oldAmt);
				tCheck.setAftPrice(price);
				tCheck.setEmpId(0L);
				tCheckMapper.executeSave(tCheck);
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	private String getNextInStep(String prevStep) throws Exception{
		String nextStepCode = "";

		if("77010".equals(prevStep)){
			nextStepCode = "77013";
		}else if("77013".equals(prevStep)){
			nextStepCode = "77015";
		}else if("77015".equals(prevStep)){
			nextStepCode = "77017";
		}else if("77017".equals(prevStep)){
			nextStepCode = "7701F";
		}

		if("".equals(nextStepCode)){
			throw new XException(XException.ERR_DEFAULT, "不能确定审批流向！");
		}

		return nextStepCode;
	}

	@Override
	public PageSet<MIn> getReturnQryList(PageParam pageParam, String filterSort, String date1Qry, String date2Qry, String userDeptIds, String typeQry, String matQry){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MIn> list = mInMapper.getReturnQryList(date1Qry, date2Qry, userDeptIds, typeQry, matQry);
		PageInfo<MIn> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public List<MIn> getRetQryList(String date1Qry, String date2Qry, String userDeptIds, String typeQry, String matQry){
		return  mInMapper.getReturnQryList(date1Qry, date2Qry, userDeptIds, typeQry, matQry);
	}

	@Override
	@Transactional
	public void submitRepInStk(HttpServletRequest request, UserInfo userInfo) throws Exception{
		String storeNo = request.getParameter("storeNo");
		String[] planNos = request.getParameterValues("planNo");

		try{
			for(int i=0; i<planNos.length; i++){
				String planNo = planNos[i];
				String matNo = request.getParameter("mat_"+planNo);
				double amount = 0;
				try{
					amount = Double.parseDouble(request.getParameter("applyAmount_"+planNo));
				}catch (Exception e){
					throw new XException(XException.ERR_DEFAULT, "申请数量必须是数字！");
				}
				if(amount <= 0){
					throw new XException(XException.ERR_DEFAULT, "申请数量必须大于0！");
				}
				String occDate = XDate.dateTo8(request.getParameter("date_"+planNo));
				String fixAddr = request.getParameter("fixAddr");
				String fixEmp = request.getParameter("fixEmp");

				MMaterial mMaterial = mMaterialMapper.getMatByNo(matNo);
				BigDecimal matPrice = mMaterial.getMatPrice();
				BigDecimal fixPrice = mMaterial.getFixPrice();

				MBackPlan mBackPlan = mBackPlanMapper.getBackPlan(planNo);
				String fixType = mBackPlan.getFixType();
				Integer planTeam = mBackPlan.getPlanTeam();
				String nextStep = "77013";
				String billNo = Snow.getUUID()+"";
				String checkNo = Snow.getUUID()+"";
				String teamNo = userInfo.getOrgId();
				String userId = userInfo.getUuid();

				MIn mIn = new MIn();
				mIn.setBillNo(billNo);
				mIn.setInType(fixType);
				mIn.setPlanSrc("m.fixType.2");
				mIn.setPlanNo(planNo);
				mIn.setTeamNo(planTeam);
				mIn.setOfferTeam(Integer.parseInt(teamNo));
				mIn.setStoreNo(storeNo);
				mIn.setReserveNo("");
				mIn.setMatNo(matNo);
				mIn.setApplyAmount(new BigDecimal(amount+""));
				mIn.setApplyDate(occDate);
				mIn.setApplyEmp(userId);
				mIn.setInAmount(new BigDecimal(amount+""));
				mIn.setInDate("");
				mIn.setInEmp("");
				mIn.setBillAmount(new BigDecimal("0"));
				mIn.setSetPrice(matPrice);
				mIn.setBillDate("");
				mIn.setBillEmp("");
				//mIn.setOfferNo(teamNo);
				mIn.setInStep(nextStep);
				mIn.setCheckNo(checkNo);
				mIn.setRemark("旧料修复验收申请");
				mIn.setDataSrc("m.dataSrc.B");
				mIn.setPrice1(fixPrice);
				mIn.setPrice2(new BigDecimal("0"));
				mIn.setPrice3(new BigDecimal("0"));
				mIn.setLinkNo(planNo);
				mIn.setNormNo("");
				mIn.setReserve1("");
				mIn.setReserve2(fixAddr);
				mIn.setReserve3(fixEmp);
				mIn.setReserve4("");
				mIn.setErpBill("");
				mInMapper.saveIn(mIn);

				TCheck tCheck = new TCheck();
				tCheck.setCheckNo(checkNo);
				tCheck.setStepKey("7701");
				tCheck.setStepCode("77010");
				tCheck.setCheckType("sys.checkType.0");
				tCheck.setDirect("sys.checkDirect.0");
				tCheck.setIdea("修复验收申请，直接");
				tCheck.setUserId(userId);
				tCheck.setUserName(userInfo.getUserName());
				tCheck.setTeamNo(Integer.parseInt(teamNo));
				tCheck.setOccDate(occDate);
				tCheck.setOccTime(XDate.getTime());
				tCheck.setLogInfo("");
				tCheck.setBefAmount(new BigDecimal(amount+""));
				tCheck.setBefPrice(matPrice);
				tCheck.setAftAmount(new BigDecimal(amount+""));
				tCheck.setAftPrice(matPrice);
				tCheck.setEmpId(0L);
				tCheckMapper.executeSave(tCheck);
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	public PageSet<MIn> getInStkChkPageSet(PageParam pageParam, String filterSort, String date1Qry, String date2Qry, String userId){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MIn> list = mInMapper.getInStkChkList(date1Qry, date2Qry, userId, LoginInfo.getOrgId());
		PageInfo<MIn> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	@Transactional
	public void submitInStkChk(HttpServletRequest request, UserInfo userInfo) throws Exception{
		String userNo = userInfo.getUuid();
		String userName = userInfo.getUserName();
		String userTeam = userInfo.getOrgId();

		try{
			String[] billNos = request.getParameterValues("billNo");
			for(int i=0; i<billNos.length; i++){
				String billNo = billNos[i];

				MIn mIn = mInMapper.getMInByBillNo(billNo);
				String storeNo = mIn.getStoreNo();
				Integer offerTeam = mIn.getOfferTeam();
				String matNo = mIn.getMatNo();
				String checkNo = mIn.getCheckNo();
				String prvStep = mIn.getInStep();
				String fixType = mIn.getInType();
				BigDecimal matAmount = mIn.getApplyAmount();
				BigDecimal matPrice = mIn.getSetPrice();  //程序是getMatPrice

				double checkAmount = 0;
				double waitAmount = 0;
				double scrapAmount = 0;
				String 	checkRemark = request.getParameter("checkRemark_"+billNo);
				try{
					checkAmount = Double.parseDouble(request.getParameter("checkAmount_"+billNo));
				}catch (Exception e){
					throw new XException(XException.ERR_DEFAULT, "完好数必须是数字!");
				}
				try{
					//页面没有waitAmount
					waitAmount = Double.parseDouble(request.getParameter("waiAmount_"+billNo));
					scrapAmount = Double.parseDouble(request.getParameter("scrapAmount_"+billNo));
				}catch (Exception e){

				}

				if(checkAmount + waitAmount + scrapAmount > matAmount.doubleValue()){
					throw new XException(XException.ERR_DEFAULT, "完好、待修、报废数量之和不能大于申请入库数！");
				}
				String nextStep = "77013".equals(prvStep) ? "77015" : "77015".equals(prvStep) ? "7701F" : "";
				String inDate = XDate.getDate();

				MIn m = new MIn();
				if("77013".equals(prvStep)){
					m.setInAmount(new BigDecimal(checkAmount+""));
					m.setInDate(inDate);
					m.setInEmp(userNo);
					m.setRemark(checkRemark);
				}
				if("7701F".equals(nextStep)){
					m.setBillAmount(new BigDecimal(checkAmount+""));
					m.setBillDate(inDate);
					m.setBillEmp(userNo);
				}
				m.setInStep(nextStep);
				m.setBillNo(billNo);
				mInMapper.updateMIn(m);

				if(!"".equals(checkNo)){
					TCheck tCheck = new TCheck();
					tCheck.setCheckNo(checkNo);
					tCheck.setStepKey("7701");
					tCheck.setStepCode(prvStep);
					tCheck.setCheckType("sys.checkType.0");
					tCheck.setDirect("sys.checkDirect.0");
					tCheck.setIdea("");
					tCheck.setUserId(userNo);
					tCheck.setUserName(userName);
					tCheck.setTeamNo(Integer.parseInt(userTeam));
					tCheck.setOccDate(inDate);
					tCheck.setOccTime(XDate.getTime());
					tCheck.setLogInfo("验收申请");
					tCheck.setBefAmount(matAmount);
					tCheck.setBefPrice(matPrice);
					tCheck.setAftAmount(matAmount);
					tCheck.setAftPrice(matPrice);
					tCheck.setEmpId(0L);
					tCheckMapper.executeSave(tCheck);
				}

				if("7701F".equals(nextStep)){
					MStock mStock = new MStock();
					mStock.setMatNo(matNo);
					mStock.setStoreNo(storeNo);
					mStock.setInAmount(new BigDecimal(checkAmount+""));
					mStock.setOutAmount(new BigDecimal("0"));
					mStock.setStockAmount(new BigDecimal(checkAmount+""));
					mStock.setPackAmount(new BigDecimal("0"));
					mStock.setLockAmount(new BigDecimal("0"));
					mStock.setBulkAmount(new BigDecimal("0"));
					int c = mStockMapper.updateStock(mStock);
					if(c < 1){
						mStock.setSiteCode("");
						mStockMapper.insertStock(mStock);
					}

					if(waitAmount != 0){
						String dxStore = "";
						List<MStore> storeList = mStoreMapper.getDZK("r.storeType2.21", offerTeam+"");
						if(storeList.size()<1){
							throw new XException(XException.ERR_DEFAULT, "无对应的待修库！");
						}
						dxStore = storeList.get(0).getStoreNo();
						String dxBill = Snow.getUUID()+"";

						MIn mIn1 = new MIn();
						mIn1.setBillNo(dxBill);
						mIn1.setInType(fixType);
						mIn1.setPlanSrc("");
						mIn1.setPlanNo("");
						mIn1.setTeamNo(offerTeam);
						mIn1.setOfferTeam(offerTeam);
						mIn1.setStoreNo(dxStore);
						mIn1.setReserveNo("");
						mIn1.setMatNo(matNo);
						mIn1.setApplyAmount(new BigDecimal(waitAmount+""));
						mIn1.setApplyDate(inDate);
						mIn1.setApplyEmp(userNo);
						mIn1.setInAmount(new BigDecimal(waitAmount+""));
						mIn1.setInDate(inDate);
						mIn1.setInEmp(userNo);
						mIn1.setBillAmount(new BigDecimal("0"));
						mIn1.setSetPrice(matPrice);
						mIn1.setBillDate("");
						mIn1.setBillEmp("");
						mIn1.setOfferNo("");
						mIn1.setInStep("7701F");
						mIn1.setCheckNo(checkNo);
						mIn1.setRemark("旧料修复验收申请");
						mIn1.setDataSrc("m.dataSrc.A");
						mIn1.setPrice1(new BigDecimal("0"));
						mIn1.setPrice2(new BigDecimal("0"));
						mIn1.setPrice3(new BigDecimal("0"));
						mIn1.setLinkNo(billNo);
						mIn1.setNormNo("");
						mIn1.setReserve1("待修记录");
						mIn1.setReserve2("");
						mIn1.setReserve3("");
						mIn1.setReserve4("");
						mIn1.setErpBill("");
						mInMapper.saveIn(mIn);

						MStock mStock1 = new MStock();
						mStock1.setMatNo(matNo);
						mStock1.setStoreNo(dxStore);
						mStock1.setInAmount(new BigDecimal(waitAmount+""));
						mStock1.setOutAmount(new BigDecimal("0"));
						mStock1.setStockAmount(new BigDecimal(waitAmount+""));
						mStock1.setPackAmount(new BigDecimal("0"));
						mStock1.setLockAmount(new BigDecimal("0"));
						mStock1.setBulkAmount(new BigDecimal("0"));
						int c1 = mStockMapper.updateStock(mStock1);
						if(c1 < 1){
							mStock1.setSiteCode("");
							mStockMapper.insertStock(mStock1);
						}
					}

					if(scrapAmount != 0){
						List<MStore> storeList = mStoreMapper.getStoreByType("r.storeType2.61");
						if(storeList.size() < 1){
							throw new XException(XException.ERR_DEFAULT, "无待报废库！");
						}
						String bfStore = storeList.get(0).getStoreNo();
						String bfBill = Snow.getUUID()+"";

						MIn mIn1 = new MIn();
						mIn1.setBillNo(bfBill);
						mIn1.setInType(fixType);
						mIn1.setPlanSrc("");
						mIn1.setPlanNo("");
						mIn1.setTeamNo(offerTeam);
						mIn1.setOfferTeam(offerTeam);
						mIn1.setStoreNo(bfStore);
						mIn1.setReserveNo("");
						mIn1.setMatNo(matNo);
						mIn1.setApplyAmount(new BigDecimal(scrapAmount+""));
						mIn1.setApplyDate(inDate);
						mIn1.setApplyEmp(userNo);
						mIn1.setInAmount(new BigDecimal(scrapAmount+""));
						mIn1.setInDate(inDate);
						mIn1.setInEmp(userNo);
						mIn1.setBillAmount(new BigDecimal("0"));
						mIn1.setSetPrice(matPrice);
						mIn1.setBillDate("");
						mIn1.setBillEmp("");
						mIn1.setOfferNo("");
						mIn1.setInStep("7701F");
						mIn1.setCheckNo(checkNo);
						mIn1.setRemark("旧料修复验收申请");
						mIn1.setDataSrc("m.dataSrc.L");
						mIn1.setPrice1(new BigDecimal("0"));
						mIn1.setPrice2(new BigDecimal("0"));
						mIn1.setPrice3(new BigDecimal("0"));
						mIn1.setLinkNo(billNo);
						mIn1.setNormNo("");
						mIn1.setReserve1("待报废记录");
						mIn1.setReserve2("");
						mIn1.setReserve3("");
						mIn1.setReserve4("");
						mIn1.setErpBill("");
						mInMapper.saveIn(mIn);

						MStock mStock1 = new MStock();
						mStock1.setMatNo(matNo);
						mStock1.setStoreNo(bfStore);
						mStock1.setInAmount(new BigDecimal(scrapAmount+""));
						mStock1.setOutAmount(new BigDecimal("0"));
						mStock1.setStockAmount(new BigDecimal(scrapAmount+""));
						mStock1.setPackAmount(new BigDecimal("0"));
						mStock1.setLockAmount(new BigDecimal("0"));
						mStock1.setBulkAmount(new BigDecimal("0"));
						int c1 = mStockMapper.updateStock(mStock1);
						if(c1 < 1){
							mStock1.setSiteCode("");
							mStockMapper.insertStock(mStock1);
						}
					}
				}
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	@Transactional
	public void backInStkChk(HttpServletRequest request, UserInfo userInfo) throws Exception{
		try{
			String[] billNos = request.getParameterValues("billNos");
			for(int i=0; i<billNos.length; i++){
				String billNo = billNos[i];
				mInMapper.deleteMIn(billNo);
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	@Transactional
	public void backMIn(HttpServletRequest request, UserInfo userInfo) throws Exception{
		try{
			String[] billNos = request.getParameterValues("billNo");
			if(billNos==null || billNos.length<1){
				throw new XException(XException.ERR_DEFAULT, "请选择要退回的申请！");
			}

			for(int i=0; i<billNos.length; i++){
				String billNo = billNos[i];

				MIn m = mInMapper.getMInByBillNo(billNo);
				String inStep = m.getInStep();
				if(!"72011".equals(inStep)){
					throw new XException(XException.ERR_DEFAULT, "入库步骤不对应，请刷新！");
				}
				String checkNo = m.getCheckNo();

				MIn mIn = new MIn();
				mIn.setBillNo(billNo);
				mIn.setBillDate(XDate.getDate());
				mIn.setBillEmp(userInfo.getUuid());
				mIn.setInStep("7201X");
				mInMapper.updateMIn(mIn);

				TCheck tCheck = new TCheck();
				tCheck.setCheckNo(checkNo);
				tCheck.setStepKey("7201");
				tCheck.setStepCode("7201X");
				tCheck.setCheckType("sys.checkType.0");
				tCheck.setDirect("sys.checkDirect.1");
				tCheck.setIdea("");
				tCheck.setUserId(userInfo.getUuid());
				tCheck.setUserName(userInfo.getUserName());
				tCheck.setTeamNo(Integer.parseInt(userInfo.getOrgId()));
				tCheck.setOccDate(XDate.getDate());
				tCheck.setOccTime(XDate.getTime());
				tCheck.setLogInfo("退回");
				tCheck.setBefAmount(new BigDecimal("0"));
				tCheck.setBefPrice(new BigDecimal("0"));
				tCheck.setAftAmount(new BigDecimal("0"));
				tCheck.setAftPrice(new BigDecimal("0"));
				tCheck.setEmpId(0L);
				tCheckMapper.executeSave(tCheck);

				mInMapper.deleteMIn(billNo);
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	public PageSet<MIn> getInstockQryPageSet(PageParam pageParam, String filterSort, String userDeptIds, String teamNoQry, String statusQry, String date1Qry, String date2Qry, String matQry){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MIn> list = mInMapper.getInstockQryList(userDeptIds, teamNoQry, statusQry, date1Qry, date2Qry, matQry);
		PageInfo<MIn> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public List<MIn> getInstockQryList(String userDeptIds, String teamNoQry, String statusQry, String date1Qry, String date2Qry, String matQry){
		return mInMapper.getInstockQryList(userDeptIds, teamNoQry, statusQry, date1Qry, date2Qry, matQry);
	}

	@Override
	@Transactional
	public void backReturn(HttpServletRequest request, UserInfo userInfo) throws Exception{
		String userId = userInfo.getUuid();
		String occDate = XDate.getDate();
		String userName = userInfo.getUserName();
		String userTeam = userInfo.getOrgId();

		try{
			String[] billNos = request.getParameterValues("billNo");
			for(int i=0; i<billNos.length; i++){
				String billNo = billNos[i];

				MIn mIn = mInMapper.getMInByBillNo(billNo);

				if(!"7701F".equals(mIn.getInStep())){
					throw new XException(XException.ERR_DEFAULT, "非定稿状态，不能退回！");
				}

				BigDecimal oldAmount = mIn.getInAmount();
				BigDecimal oldPrice = mIn.getSetPrice();
				String storeNo = mIn.getStoreNo();
				String matNo = mIn.getMatNo();
				String checkNo = mIn.getCheckNo();
				String nextStep = "7701X";

				MIn m = new MIn();
				m.setInStep("7701X");
				m.setBillDate(occDate);
				m.setBillEmp(userId);
				m.setBillNo(billNo);
				mInMapper.updateMIn(m);

				MStock mStock = new MStock();
				mStock.setInAmount(oldAmount.multiply(new BigDecimal("-1")));
				mStock.setOutAmount(new BigDecimal("0"));
				mStock.setStockAmount(oldAmount.multiply(new BigDecimal("-1")));
				mStock.setPackAmount(new BigDecimal("0"));
				mStock.setLockAmount(new BigDecimal("0"));
				mStock.setBulkAmount(oldAmount.multiply(new BigDecimal("-1")));
				mStock.setMatNo(matNo);
				mStock.setStoreNo(storeNo);
				int c = mStockMapper.updateStock(mStock);
				if(c<1){
					mStock.setSiteCode("");
					mStockMapper.insertStock(mStock);
				}

				TCheck tCheck = new TCheck();
				tCheck.setCheckNo(checkNo);
				tCheck.setStepKey("7701");
				tCheck.setStepCode("7701F");
				tCheck.setCheckType("sys.checkType.0");
				tCheck.setDirect("sys.checkDirect.1");
				tCheck.setIdea("");
				tCheck.setUserId(userId);
				tCheck.setUserName(userName);
				tCheck.setTeamNo(Integer.parseInt(userTeam));
				tCheck.setOccDate(occDate);
				tCheck.setOccTime(XDate.getTime());
				tCheck.setLogInfo("");
				tCheck.setBefAmount(oldAmount);
				tCheck.setBefPrice(oldPrice);
				tCheck.setAftAmount(oldAmount);
				tCheck.setAftPrice(oldPrice);
				tCheck.setEmpId(0L);
				tCheckMapper.executeSave(tCheck);
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}
}
