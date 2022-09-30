package cn.ewsd.fix.service.impl;

import cn.ewsd.base.utils.BaseUtils;
import cn.ewsd.base.utils.Snow;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.fix.mapper.MBackQuotaMapper;
import cn.ewsd.fix.mapper.MFixAssessMapper;
import cn.ewsd.fix.model.MBackQuota;
import cn.ewsd.fix.model.MFixAssess;
import cn.ewsd.material.mapper.MMaterialMapper;
import cn.ewsd.material.mapper.MPrjMapper;
import cn.ewsd.material.mapper.MStockMapper;
import cn.ewsd.material.model.MMaterial;
import cn.ewsd.material.model.MPrj;
import cn.ewsd.material.model.MStock;
import cn.ewsd.material.service.MStockService;
import cn.ewsd.mdata.model.Organization;
import cn.ewsd.mdata.service.UtilService;
import cn.ewsd.repository.mapper.MInMapper;
import cn.ewsd.repository.mapper.MOutMapper;
import cn.ewsd.repository.model.MOut;
import cn.ewsd.system.mapper.TCheckMapper;
import cn.ewsd.system.model.TCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.ewsd.fix.mapper.MBackPlanMapper;
import cn.ewsd.fix.model.MBackPlan;
import cn.ewsd.fix.service.MBackPlanService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;

@Service("mBackPlanServiceImpl")
public class MBackPlanServiceImpl extends FixBaseServiceImpl<MBackPlan, String> implements MBackPlanService {
	@Autowired
	private MBackPlanMapper mBackPlanMapper;
	@Autowired
	private MPrjMapper mPrjMapper;
	@Autowired
	private MFixAssessMapper fixAssessMapper;
	@Autowired
	private MBackQuotaMapper mBackQuotaMapper;
	@Autowired
	private UtilService utilService;
	@Autowired
	private MMaterialMapper mMaterialMapper;
	@Autowired
	private MInMapper mInMapper;
	@Autowired
	private TCheckMapper tCheckMapper;
	@Autowired
	private MStockMapper mStockMapper;
	@Autowired
	private MStockService mStockService;
	@Autowired
	private MOutMapper mOutMapper;

    @Override
	@Transactional
    public void insertBackPlan(HttpServletRequest request, UserInfo userInfo) throws Exception{
		try{
			String yearQry = request.getParameter("yearQry");
			String monQry = request.getParameter("monQry");
			String month = yearQry+monQry;
			String prjQry = request.getParameter("prjQry");
			String teamNoQry = request.getParameter("teamNoQry");
			double planValue = 0;
			try {
				planValue = Double.parseDouble(request.getParameter("planValue"));
			}catch (Exception e){
				throw new XException(XException.ERR_DEFAULT, "计划生产必须是数字！");
			}
			if(planValue<=0){
				throw new XException(XException.ERR_DEFAULT, "计划生产必须是正数！");
			}
			String fixType = "m.fixType.1";
			if("".equals(prjQry)){
				throw new XException(XException.ERR_DEFAULT, "请选择回收地点");
			}

			String prjName = mPrjMapper.getPrjByPrjNo(prjQry).getPrjName();
			String userTeam = userInfo.getOrgId();
			String userId = userInfo.getUuid();
			String endDate = month+"26";

			MFixAssess mFixAssess = new MFixAssess();
			mFixAssess.setPlanValue(new BigDecimal(planValue+""));
			mFixAssess.setAssType(fixType);
			mFixAssess.setAssMonth(month);
			mFixAssess.setMngTeam(Integer.parseInt(userTeam));
			mFixAssess.setTeamNo(Integer.parseInt(teamNoQry));
			mFixAssess.setPrjNo(prjQry);
			if(fixAssessMapper.updateFixAssess(mFixAssess)<1){
				mFixAssess.setAssessId(Snow.getUUID()+"");
				mFixAssess.setPrjName(prjName);
				mFixAssess.setWorkValue(new BigDecimal("0"));
				mFixAssess.setRemark("");
				mFixAssess.setAssStatus("0");
				mFixAssess.setApplyUser(userId);
				mFixAssess.setApplyDate(XDate.getDate());
				mFixAssess.setAgreeUser("");
				mFixAssess.setAgreeDate("");
				fixAssessMapper.insertFixAssess(mFixAssess);
			}

			mBackPlanMapper.deleteBackPlan(month, teamNoQry, userTeam, prjQry);

			List<MBackQuota> list = mBackQuotaMapper.getBackQuotaList("", month, prjQry, userTeam);
			for(int i=0; i<list.size(); i++){
				MBackQuota mBackQuota = list.get(i);
				String matNo = mBackQuota.getMatNo();

				ArrayList<String[]> condList = new ArrayList<>();
				condList.add(new String[]{"plan_month", "=", month});
				condList.add(new String[]{"team_no", "=", teamNoQry});
				condList.add(new String[]{"plan_team", "=", userTeam});
				condList.add(new String[]{"prj_no", "=", prjQry});
				condList.add(new String[]{"mat_no", "=", matNo});
				if(utilService.checkColumnDataExist("m_back_plan", condList)){
					continue;
				}

				BigDecimal measure = new BigDecimal(mBackQuota.getMeasure()+"");
				BigDecimal norm = new BigDecimal(mBackQuota.getAmount()+"");
				BigDecimal planAmount = new BigDecimal(planValue+"").divide(measure).multiply(norm);

				MMaterial mMaterial = mMaterialMapper.getMatByNo(matNo);
				BigDecimal price = mMaterial.getMatPrice();
				BigDecimal fixPrice = mMaterial.getFixPrice();

				MBackPlan mBackPlan = new MBackPlan();
				mBackPlan.setPlanNo(Snow.getUUID()+"");
				mBackPlan.setPlanMonth(month);
				mBackPlan.setTeamNo(Integer.parseInt(teamNoQry));
				mBackPlan.setMatNo(matNo);
				mBackPlan.setPlanAmount(planAmount);
				mBackPlan.setMatPrice(price);
				mBackPlan.setAwardBala(fixPrice);
				mBackPlan.setPlanStep("77000");
				mBackPlan.setBackNorm(norm);
				mBackPlan.setPrjNo(prjQry);
				mBackPlan.setPrjName(prjName);
				mBackPlan.setPlanDate(XDate.getDate());
				mBackPlan.setPlanTeam(Integer.parseInt(userTeam));
				mBackPlan.setRemark("");
				mBackPlan.setEndDate(endDate);
				mBackPlan.setFixType(fixType);
				mBackPlanMapper.insertBackPlan(mBackPlan);
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
    }

	@Override
	@Transactional
	public void saveBackPlan(HttpServletRequest request, UserInfo userInfo) throws Exception {
		try{
			String month = request.getParameter("month");
			String teamNo = request.getParameter("teamNo");
			String prjNo = request.getParameter("prjNo");
			String fixType = "m.fixType.1";
			if("".equals(prjNo)){
				throw new XException(XException.ERR_DEFAULT, "请选择回收地点！");
			}
			String prjName = mPrjMapper.getPrjByPrjNo(prjNo).getPrjName();
			String userTeam = userInfo.getOrgId();
			String endDate = month+"26";

			String[] matNos = request.getParameterValues("matNo");
			if(matNos==null || matNos.length<1){
				throw new XException(XException.ERR_DEFAULT, "请先选择要保存的计划");
			}
			for(int i=0; i<matNos.length; i++){
				String matNo = matNos[i];
				double amount = 0;
				try{
					amount = Double.parseDouble(request.getParameter("amount_"+matNo));
				}catch (Exception e){
					throw new XException(XException.ERR_DEFAULT, "计划回收必须是数字！");
				}
				if(amount <0){
					throw new XException(XException.ERR_DEFAULT, "计划回收必须是正数！");
				}
				String remark= request.getParameter("remark_"+matNo);

				MMaterial mMaterial = mMaterialMapper.getMatByNo(matNo);
				BigDecimal matPrice = mMaterial.getMatPrice();
				BigDecimal fixPrice = mMaterial.getFixPrice();

				int c = mBackPlanMapper.updateBackPlan(amount+"", remark, month, teamNo, userTeam, matNo, fixType, prjNo);
				if(c<=0){
					MBackPlan mBackPlan = new MBackPlan();
					mBackPlan.setPlanNo(Snow.getUUID()+"");
					mBackPlan.setPlanMonth(month);
					mBackPlan.setTeamNo(Integer.parseInt(teamNo));
					mBackPlan.setMatNo(matNo);
					mBackPlan.setPlanAmount(new BigDecimal(amount+""));
					mBackPlan.setMatPrice(matPrice);
					mBackPlan.setAwardBala(fixPrice);
					mBackPlan.setPlanStep("77000");
					mBackPlan.setPrjNo(prjNo);
					mBackPlan.setPrjName(prjName);
					mBackPlan.setPlanDate(XDate.getDate());
					mBackPlan.setPlanTeam(Integer.parseInt(userTeam));
					mBackPlan.setRemark(remark);
					mBackPlan.setEndDate(endDate);
					mBackPlan.setFixType(fixType);
					mBackPlanMapper.insertBackPlan(mBackPlan);
				}
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	public void deleteBackPlan(String planNo) throws Exception{
		int c = mBackPlanMapper.delBackPlan(planNo);
		if(c<1){
			throw new XException(XException.ERR_DEFAULT, "非草稿，不能删除！");
		}
	}

	@Override
	public void submitBackPlan(HttpServletRequest request) throws Exception{
		String[] planNos = request.getParameterValues("planNo");
		if(planNos==null || planNos.length<1){
			throw new XException(XException.ERR_DEFAULT, "没有需要提交的计划！");
		}

		try{
			for(int i=0; i<planNos.length; i++){
				String planNo = planNos[i];

				MBackPlan mBackPlan = mBackPlanMapper.getBackPlan(planNo);
				if(!"77000".equals(mBackPlan.getPlanStep())){
					throw new XException(XException.ERR_DEFAULT, "非草稿，不能提交！");
				}
				String matNo = mBackPlan.getMatNo();

				double amount = 0;
				try{
					amount = Double.parseDouble(request.getParameter("amount_"+matNo));
				}catch (Exception e){
					throw new XException(XException.ERR_DEFAULT, "计划回收必须是数字！");
				}
				if(amount < 0){
					throw new XException(XException.ERR_DEFAULT, "计划回收必须是正数！");
				}
				String remark= request.getParameter("remark_"+matNo);

				mBackPlanMapper.updBackPlan("7700F", XDate.getDate(), amount+"", remark, null, planNo);
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	public PageSet<MBackPlan> getPageSet(PageParam pageParam, String filterSort, String monthQry, String teamNoQry, String matQry, String ksTeamQry, boolean ifZnks, String userTeam, String userDeptIds) {
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MBackPlan> list = mBackPlanMapper.getPageSet(monthQry, teamNoQry, matQry, ksTeamQry, ifZnks, userTeam, userDeptIds);
		PageInfo<MBackPlan> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public List<MBackPlan> getBackPlanList(String monthQry, String teamNoQry, String matQry, String ksTeamQry, boolean ifZnks, String userTeam, String userDeptIds) {
		return mBackPlanMapper.getPageSet(monthQry, teamNoQry, matQry, ksTeamQry, ifZnks, userTeam, userDeptIds);
	}

	@Override
	public List<Organization> getBackPlanTeam(){
    	return mBackPlanMapper.getBackPlanTeam();
	}

	@Override
	public PageSet<MBackPlan> getTurnRegPageSet(PageParam pageParam, String filterSort, String monthQry, String userTeam, String addrQry, String teamQry, String matQry) {
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MBackPlan> list = mBackPlanMapper.getTurnRegList(monthQry, userTeam, addrQry, teamQry, matQry);
		PageInfo<MBackPlan> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public MBackPlan getBackPlan(String planNo){
    	return mBackPlanMapper.getBackPlan(planNo);
	}

	@Override
	@Transactional
	public void updateBackPlan(HttpServletRequest request, UserInfo userInfo) throws Exception{
		try{
			String planNo = request.getParameter("planNo");
			String remark = request.getParameter("remark");
			double modiAmount = 0;
			try {
				modiAmount = Double.parseDouble(request.getParameter("amount"));
			}catch (Exception e){
				throw new XException(XException.ERR_DEFAULT, "调整数量必须是数字！");
			}
			if(modiAmount<=0){
				throw new XException(XException.ERR_DEFAULT, "调整数量必须是正数！");
			}

			MBackPlan mBackPlan = mBackPlanMapper.getBackPlan(planNo);
			BigDecimal planAmount = mBackPlan.getPlanAmount();
			BigDecimal price = mBackPlan.getMatPrice();
			String checkNo = Snow.getUUID()+"";

			BigDecimal inAmount = new BigDecimal(mInMapper.getBillAmount(planNo)+"");

			if(new BigDecimal(modiAmount+"").compareTo(planAmount.subtract(inAmount)) == 1){
				throw new XException(XException.ERR_DEFAULT, "调整数量不能大于计划数量减去已入库数量！");
			}
			String userId = userInfo.getUuid();
			String userName = userInfo.getUserName();
			String occDate = XDate.getDate();
			String userTeam = userInfo.getOrgId();

			BigDecimal afterAmount = planAmount.subtract(new BigDecimal(modiAmount+""));
			if(afterAmount.compareTo(new BigDecimal("0")) == 1){
				mBackPlanMapper.updBackPlan(null, null, afterAmount.doubleValue()+"", null, checkNo, planNo);
			}else{
				mBackPlanMapper.delBackPlan1(planNo);
			}

			TCheck tCheck = new TCheck();
			tCheck.setCheckNo(checkNo);
			tCheck.setStepKey("7700");
			tCheck.setStepCode("7700F");
			tCheck.setCheckType("sys.checkType.0");
			tCheck.setDirect("sys.checkDirect.0");
			tCheck.setIdea("");
			tCheck.setUserId(userId);
			tCheck.setUserName(userName);
			tCheck.setTeamNo(Integer.parseInt(userTeam));
			tCheck.setOccDate(occDate);
			tCheck.setOccTime(XDate.getTime());
			tCheck.setLogInfo("");
			tCheck.setBefAmount(planAmount);
			tCheck.setBefPrice(price);
			tCheck.setAftAmount(afterAmount);
			tCheck.setAftPrice(price);
			tCheck.setEmpId(0L);
			tCheckMapper.executeSave(tCheck);
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	public PageSet<MBackPlan> getFixTaskList(PageParam pageParam, String filterSort, String userTeam, String matQry, String monthQry) {
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MBackPlan> list = mBackPlanMapper.getFixTaskList(userTeam, matQry, monthQry);
		PageInfo<MBackPlan> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	@Transactional
	public void submitFixTask(HttpServletRequest request, UserInfo userInfo, String mngTeam) throws Exception{
		try{
			String year = request.getParameter("year");
			String mon = request.getParameter("mon");
			String fixTeam = request.getParameter("fixTeam");
			if("".equals(fixTeam)){
				throw new XException(XException.ERR_DEFAULT, "请选择修复单位");
			}
			String teamNo = userInfo.getOrgId();
			String userId = userInfo.getUuid();
			String occDate = XDate.getDate();
			String outType = "r.outBillType.1";
			String dataSrc = "m.dataSrc.T";

			String[] keys = request.getParameterValues("storeNo");
			for(int i=0; i<keys.length; i++){
				String key = keys[i];
				String storeNo = key.split("_")[0];
				String matNo = key.split("_")[1];
				double amount = 0;
				try{
					amount = Double.parseDouble(request.getParameter("amount_"+key));
				}catch (Exception e){
					throw new XException(XException.ERR_DEFAULT, "计划数必须是数字！");
				}
				if(amount<=0){
					throw new XException(XException.ERR_DEFAULT, "计划数必须大于0");
				}
				String remark = request.getParameter("remark_"+key);

				MMaterial mMaterial = mMaterialMapper.getMatByNo(matNo);
				BigDecimal price = mMaterial.getMatPrice();
				BigDecimal fixPrice = mMaterial.getFixPrice();

				String planNo = Snow.getUUID()+"";
				MBackPlan mBackPlan = new MBackPlan();
				mBackPlan.setPlanNo(planNo);
				mBackPlan.setPlanMonth(year+mon);
				mBackPlan.setTeamNo(Integer.parseInt(fixTeam));
				mBackPlan.setMatNo(matNo);
				mBackPlan.setPlanAmount(new BigDecimal(amount+""));
				mBackPlan.setMatPrice(price);
				mBackPlan.setAwardBala(fixPrice);
				mBackPlan.setPlanStep("7701F");
				mBackPlan.setPrjName("0".equals(storeNo) ? "" : storeNo);
				mBackPlan.setPlanDate(occDate);
				mBackPlan.setPlanTeam(Integer.parseInt(teamNo));
				mBackPlan.setFixAddr(remark);
				mBackPlan.setEndDate("");
				mBackPlan.setFixType("m.fixType.2");
				mBackPlanMapper.insertBackPlan(mBackPlan);

				if(!"0".equals(storeNo)){
					MStock mStock = mStockMapper.getStock(storeNo, matNo);
					BigDecimal stock = mStock.getStockAmount();
					BigDecimal plan = stock.compareTo(new BigDecimal(amount+"")) >= 0 ? new BigDecimal(amount+"") : stock;

					if(plan.compareTo(new BigDecimal("0")) == 1){
						MStock mStock1 = new MStock();
						mStock1.setStoreNo(storeNo);
						mStock1.setMatNo(matNo);
						mStock1.setInAmount(new BigDecimal("0"));
						mStock1.setOutAmount(plan);
						mStock1.setStockAmount(plan.multiply(new BigDecimal("-1")));
						mStock1.setPackAmount(new BigDecimal("0"));
						mStock1.setLockAmount(new BigDecimal("0"));
						mStock1.setBulkAmount(plan.multiply(new BigDecimal("-1")));
						int c = mStockMapper.updateStock(mStock1);
						if(c<1){
							mStock1.setSiteCode("");
							mStockMapper.insertStock(mStock1);
						}
						mStockService.checkStockAmount(matNo, storeNo);

						MOut mOut = new MOut();
						mOut.setDrawNo(Snow.getUUID()+"");
						mOut.setOutType(outType);
						mOut.setPlanNo(planNo);
						mOut.setPlanTeam(teamNo);
						mOut.setMatNo(matNo);
						mOut.setApplyAmount(plan);
						mOut.setApplyDate(occDate);
						mOut.setApplyTime(XDate.getTime());
						mOut.setApplyEmp(userId);
						mOut.setUrgentLevel("");
						mOut.setStoreAddr("");
						mOut.setIfSend("");
						mOut.setUseAddr("下达修复任务，核减");
						mOut.setApplyInfo("");
						mOut.setChkAmount(plan);
						mOut.setAgreeEmp(userId);
						mOut.setAbcType("");
						mOut.setDrawStep("7202F");
						mOut.setCheckNo("0");
						mOut.setOutAmount(plan);
						mOut.setDrawEmp(userId);
						mOut.setStoreNo(storeNo);
						mOut.setTeamNo(Integer.parseInt(fixTeam));
						mOut.setOutDate(occDate);
						mOut.setOutTime(XDate.getTime());
						mOut.setItemNo("");
						mOut.setPlanPrice(price);
						mOut.setMatPrice(price);
						mOut.setPrjNo("0");
						mOut.setPrice1(fixPrice);
						mOut.setPrice2(new BigDecimal("0"));
						mOut.setDataSrc(dataSrc);
						mOut.setLinkNo("");
						mOut.setOfferTeam(Integer.parseInt(teamNo));
						mOut.setUuid(BaseUtils.UUIDGenerator());
						mOutMapper.insertOut(mOut);
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
	public int deleteFixTask(String planNo){
		try{
			System.out.println(planNo+"-----");

			MBackPlan mBackPlan = mBackPlanMapper.getBackPlan(planNo);
			String storeNo = mBackPlan.getPrjName();
			String matNo = mBackPlan.getMatNo();
			BigDecimal amount = mBackPlan.getPlanAmount();

			if(!"".equals(storeNo)){
				MStock mStock1 = new MStock();
				mStock1.setStoreNo(storeNo);
				mStock1.setMatNo(matNo);
				mStock1.setInAmount(new BigDecimal("0"));
				mStock1.setOutAmount(amount.multiply(new BigDecimal("-1")));
				mStock1.setStockAmount(amount);
				mStock1.setPackAmount(new BigDecimal("0"));
				mStock1.setLockAmount(new BigDecimal("0"));
				mStock1.setBulkAmount(amount);
				int c = mStockMapper.updateStock(mStock1);
				if(c<1){
					mStock1.setSiteCode("");
					mStockMapper.insertStock(mStock1);
				}
			}

			return mBackPlanMapper.delBackPlan1(planNo);
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	public PageSet<MBackPlan> getFixPlanPageSet(PageParam pageParam, String filterSort, String monthQry, String teamNoQry, String mngTeamQry, boolean ifZnks, String userTeam, String userDeptIds, String stepQry, String fixTypeQry, String matQry){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MBackPlan> list = mBackPlanMapper.getFixPlanList(monthQry, teamNoQry, mngTeamQry, ifZnks, userTeam, userDeptIds, stepQry, fixTypeQry, matQry);
		PageInfo<MBackPlan> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public List<MBackPlan> getFixPlanList(String monthQry, String teamNoQry, String mngTeamQry, boolean ifZnks, String userTeam, String userDeptIds, String stepQry, String fixTypeQry, String matQry){
		return mBackPlanMapper.getFixPlanList(monthQry, teamNoQry, mngTeamQry, ifZnks, userTeam, userDeptIds, stepQry, fixTypeQry, matQry);
	}

	@Override
	public List<Organization> getBackPlanMngTeam(String monthQry){
    	return mBackPlanMapper.getBackPlanMngTeam(monthQry);
	}

	@Override
	public List<MBackPlan> getRepInStk(String monthQry, String userTeam){
    	return mBackPlanMapper.getRepInStk(monthQry, userTeam);
	}

	@Override
	public List<MPrj> getBackAssPrj(String planMonth, String teamNo){
    	return mBackPlanMapper.getBackAssPrj(planMonth, teamNo);
	}
}
