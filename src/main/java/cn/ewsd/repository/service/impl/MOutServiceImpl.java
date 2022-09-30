package cn.ewsd.repository.service.impl;

import cn.ewsd.base.utils.*;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.material.mapper.*;
import cn.ewsd.material.model.*;
import cn.ewsd.material.service.MItemService;
import cn.ewsd.material.service.MStockService;
import cn.ewsd.mdata.mapper.OrganizationMapper;
import cn.ewsd.mdata.model.Organization;
import cn.ewsd.mdata.service.UtilService;
import cn.ewsd.repository.mapper.*;
import cn.ewsd.repository.model.*;
import cn.ewsd.system.mapper.DicItemMapper;
import cn.ewsd.system.mapper.TCheckMapper;
import cn.ewsd.system.model.DicItem;
import cn.ewsd.system.model.SysAuditProcess;
import cn.ewsd.system.model.TCheck;
import cn.ewsd.system.service.ConfigService;
import cn.ewsd.system.service.SysAuditProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.ewsd.repository.service.MOutService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;

@Service("mOutServiceImpl")
public class MOutServiceImpl extends RepositoryBaseServiceImpl<MOut, String> implements MOutService {
	@Autowired
	private MOutMapper mOutMapper;
	@Autowired
	private ConfigService configService;
	@Autowired
	private UtilService utilService;
	@Autowired
	private MAddrMapper mAddrMapper;
	@Autowired
	private MPlanMapper mPlanMapper;
	@Autowired
	private MStockMapper mStockMapper;
	@Autowired
	private TCheckMapper tCheckMapper;
	@Autowired
	private MPrjMapper mPrjMapper;
	@Autowired
	private OrganizationMapper organizationMapper;
	@Autowired
	private MStoreMapper mStoreMapper;
	@Autowired
	private MItemService mItemService;
	@Autowired
	private MTeamStockMapper mTeamStockMapper;
	@Autowired
	private MTeamStockLogMapper mTeamStockLogMapper;
	@Autowired
	private MStockService mStockService;
	@Autowired
	private MTypeMatMapper mTypeMatMapper;
	@Autowired
	private MHandInMapper mHandInMapper;
	@Autowired
	private MInMapper mInMapper;
	@Autowired
	private MFifoMapper mFifoMapper;
	@Autowired
	private MMaterialMapper mMaterialMapper;
	@Autowired
	private MMakeMatDeptMapper mMakeMatDeptMapper;
	@Autowired
	private MTeamBillMapper mTeamBillMapper;
	@Autowired
	private SysAuditProcessService sysAuditProcessService;
	@Autowired
	private MOutBillMapper mOutBillMapper;
	@Autowired
	private MInBackMapper mInBackMapper;
	@Autowired
	private DicItemMapper dicItemMapper;

	@Override
	public int getCountByMatNo(String matNo){
		return mOutMapper.getCountByMatNo(matNo);
	}

	@Override
	public List<MOut> getApplySet(String planNos){
		return mOutMapper.getApplySet(planNos);
	}

	@Override
	public MOut getZaiTuByPlanNo(String planNo){
		return mOutMapper.getZaiTuByPlanNo(planNo);
	}

	@Override
	public PageSet<MOut> getOutApplyPageSet(PageParam pageParam, String filterSort, String date1Qry, String date2Qry, String teamNo, String storeNoQry, String addrQry, String appTypeQry, String matCodeQry, String matNameQry, String planStepQry){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MOut> list = mOutMapper.getOutApplyList(date1Qry, date2Qry, teamNo, storeNoQry, addrQry, appTypeQry, matCodeQry, matNameQry, planStepQry);
		PageInfo<MOut> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	@Transactional
	public void insertDrawApply(String prjNo, String item1, String deptNo, String ifSend, String sendAddr, String sendTime, String getEmp, UserInfo userInfo, HttpServletRequest request) throws Exception{
		try{
			String applyType = "r.outStockType.1";
			String dataSrc = "m.dataSrc.3";
			String userNo = userInfo.getUuid();
			String occDate = XDate.getDate();
			String teamNo = userInfo.getOrgId();
			String zhCtlTeam = configService.getConfigByCode("CL_ZH_CTL_TEAM");
			String pickDate = "";
			String pickTime = "";
			if(!"".equals(sendTime)){
				pickDate = XDate.dateTo8(sendTime.substring(0, 10));
				pickTime = XDate.timeTo6(sendTime.substring(11))+"00";
			}

			/*if(!"".equals(sendAddr)){
				ArrayList<String[]> list = new ArrayList<>();
				list.add(new String[]{"mat_addr", "=", sendAddr});
				boolean hasAddr = utilService.checkColumnDataExist("m_addr", list);
				if(!hasAddr){
					MAddr mAddr = new MAddr();
					mAddr.setMatAddr(sendAddr);
					mAddr.setTeamNo(Integer.parseInt(teamNo));
					mAddr.setUpDown("");
					mAddr.setIfUsing("1");
					mAddrMapper.insertAddr(mAddr);
				}
			}*/

			String[] keys = request.getParameterValues("key");

			for(int i=0; i<keys.length; i++){
				String key = keys[i];
				System.out.println("key--->"+key);
				if("".equals(key)){
					continue;
				}
				String planNo = Snow.getUUID()+"";
						//String planNo = key.split("_")[0];
				String matNo = key.split("_")[1];
				String storeNo = key.split("_")[2];
				double amount = 0;
				try {
					amount = Double.parseDouble(request.getParameter("amount_"+key));
				}catch (Exception e){
					throw new XException(XException.ERR_DEFAULT, "申请数必须是大于0的数字!");
				}
				if(amount<=0){
					throw new XException(XException.ERR_DEFAULT, "申请数必须是大于0的数字！");
				}
				if("0".equals(storeNo)){
					throw new XException(XException.ERR_DEFAULT, "必须选择仓库！");
				}
				String applyInfo = request.getParameter("remark_"+key);

				MMaterial material = mMaterialMapper.getMatByNo(matNo);

				//MPlan mPlan = mPlanMapper.getPlanByPlanNo(planNo);
				BigDecimal usableAmount = new BigDecimal(amount);
				BigDecimal matPrice = material.getMatPrice();
				BigDecimal planPrice = material.getMatPrice();
				String itemNo = "";
				String matType = material.getMatType();
				String planMonth = "";
				BigDecimal applyAmount = new BigDecimal(amount);

//				MPlan mPlan = mPlanMapper.getPlanByPlanNo(planNo);
//				BigDecimal usableAmount = mPlan.getUsableAmount();
//				BigDecimal matPrice = mPlan.getmMatPrice();
//				BigDecimal planPrice = mPlan.getMatPrice();
//				String itemNo = "".equals(item) ? mPlan.getItemNo() : item;
//				String matType = mPlan.getMatType();
//				String planMonth = mPlan.getPlanMonth();
//				BigDecimal applyAmount = new BigDecimal(mOutMapper.getChkAmountByPlanNo(planNo)+"");

//				if(new BigDecimal(amount+"").compareTo(usableAmount.subtract(applyAmount)) == 1){
//					throw new XException(XException.ERR_DEFAULT, "申请数量不能大于实际可领数量！");
//				}
				BigDecimal stockAmount = mStockMapper.getStock(storeNo, matNo).getStockAmount();
				if(!"".equals(storeNo) && new BigDecimal(amount+"").compareTo(stockAmount) == 1){
					throw new XException(XException.ERR_DEFAULT, "申请数量不能大于库存数！");
				}
				if(matPrice.doubleValue() == 1){
					throw new XException(XException.ERR_DEFAULT, "物料价格为1，不能发生领用！");
				}
//				if (zhCtlTeam.matches("(.*)"+teamNo+"(.*)") && "07".equals(matType) && !XDate.getMonth().equals(planMonth)) {
//					throw new XException(XException.ERR_DEFAULT, "本单位，支护材料计划，当月有效！");
//				}

				String drawStep = getNextDrawStep("72020", 0, matNo);
				String checkNo = Snow.getUUID()+"";
				String drawNo = Snow.getUUID()+"";

				MOut mOut = new MOut();
				mOut.setDrawNo(drawNo);
				mOut.setOutType(applyType);
				if(!"".equals(planNo)){
					mOut.setPlanNo(planNo);
				}
				mOut.setPlanTeam(teamNo);
				mOut.setMatNo(matNo);
				mOut.setApplyAmount(new BigDecimal(amount+""));
				mOut.setApplyDate(occDate);
				mOut.setApplyTime(XDate.getTime());
				mOut.setApplyEmp(userNo);
				mOut.setUrgentLevel("");
				mOut.setStoreAddr("");
				mOut.setIfSend(ifSend);
				mOut.setUseAddr(sendAddr);
				mOut.setApplyInfo(applyInfo);
				mOut.setChkAmount(new BigDecimal(amount+""));
				mOut.setAgreeEmp("");
				mOut.setAbcType("");
				mOut.setDrawStep(drawStep);
				mOut.setCheckNo(checkNo);
				mOut.setOutAmount(new BigDecimal("0"));
				mOut.setDrawEmp("");
				mOut.setStoreNo(storeNo);
				mOut.setTeamNo(Integer.parseInt(teamNo));
				mOut.setOutDate("");
				mOut.setOutTime("");
				mOut.setItemNo(itemNo);
				mOut.setPlanPrice(planPrice);
				mOut.setMatPrice(matPrice);
				mOut.setPrjNo(prjNo);
				mOut.setPrice1(new BigDecimal("0"));
				mOut.setPrice2(new BigDecimal("0"));
				mOut.setDataSrc(dataSrc);
				mOut.setLinkNo(planNo);
				mOut.setOfferTeam("".equals(deptNo) ? null : Integer.parseInt(deptNo));
				mOut.setPickDate(pickDate);
				mOut.setPickTime(pickTime);
				mOut.setReserve1(getEmp);
				mOut.setUuid(BaseUtils.UUIDGenerator());
				mOut.setCreator(userInfo.getUserName());
				mOut.setCreatorId(userNo);
				mOut.setCreatorOrgId(Integer.parseInt(teamNo));
				mOut.setReserve2(utilService.getBillCode("m_out", "reserve2", XDate.getMonth().substring(2, 6)+"L", 5, null));
				//mOut.setReserve3(mPlan.getWbsElement());
				mOut.setReserve3("");
				mOutMapper.insertOut(mOut);

				TCheck tCheck = new TCheck();
				tCheck.setCheckNo(checkNo);
				tCheck.setStepKey("7202");
				tCheck.setStepCode("72020");
				tCheck.setCheckType("sys.checkType.0");
				tCheck.setDirect("sys.checkDirect.0");
				tCheck.setIdea("");
				tCheck.setUserId(userNo);
				tCheck.setUserName(userInfo.getUserName());
				tCheck.setTeamNo(Integer.parseInt(teamNo));
				tCheck.setOccDate(XDate.getDate());
				tCheck.setOccTime(XDate.getTime());
				tCheck.setLogInfo("审前数量："+amount);
				tCheck.setBefAmount(new BigDecimal(amount+""));
				tCheck.setBefPrice(new BigDecimal("0"));
				tCheck.setAftAmount(new BigDecimal(amount+""));
				tCheck.setAftPrice(new BigDecimal("0"));
				tCheck.setEmpId(0L);
				tCheckMapper.executeSave(tCheck);
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	private String getNextDrawStep(String prevDrawStep, double price, String matCode) throws Exception{
		String nextDrawStep = "";

		if("72020".equals(prevDrawStep)){
			nextDrawStep = "72021";
		}else if("72021".equals(prevDrawStep)){
			nextDrawStep = "7202T";
		}else if("72023".equals(prevDrawStep)){
			nextDrawStep = "72022";
		}else if("72022".equals(prevDrawStep)){
			double matPrice = 0;
			try{
				matPrice = Double.parseDouble(configService.getConfigByCode("CL_JYKZ_MAT_PRICE"));
			}catch (Exception e){
				throw new XException(XException.ERR_DEFAULT, "经营科长审批材料单价未设置或非数字！");
			}

			List<DicItem> dicList = dicItemMapper.getDictItemByCode("r.escapeMat");
			boolean flag = true;
			for(int i=0; i<dicList.size(); i++){
				if(matCode.equals(dicList.get(i).getValue())){
					flag = false;
					break;
				}
			}

			if(price >= matPrice && flag){
				nextDrawStep = "72024";
			}else{
				nextDrawStep = "7202T";
			}
		}else if("72024".equals(prevDrawStep)){
			nextDrawStep = "7202T";
		}else if("7202T".equals(prevDrawStep)){
			nextDrawStep = "7202F";
		}
		if("".equals(nextDrawStep)){
			throw new XException(XException.ERR_DEFAULT, "未知的领料审批流向！");
		}

		return nextDrawStep;
	}

	@Override
	public List<MOut> getDrawCheckIndex(String userId, String userTeam){
		return mOutMapper.getDrawCheckIndex(userId, userTeam);
	}

	@Override
	public PageSet<MOut> getDrawCheckList(PageParam pageParam, String filterSort, String teamNo, String drawStep, String applyDate, String date1Qry, String date2Qry, String drawNoQry, String userNo, String storeNoQry, String matQry, String typeQry, String userTeam){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MOut> list = mOutMapper.getDrawCheckList(teamNo, drawStep, applyDate, date1Qry, date2Qry, drawNoQry, userNo, storeNoQry, matQry, typeQry, userTeam);
		PageInfo<MOut> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public double getRoadAmount(String matNo, String storeNo){
		return mOutMapper.getRoadAmount(matNo, storeNo);
	}

	@Override
	@Transactional
	public void submitOutCheck(UserInfo userInfo, HttpServletRequest request) throws Exception{
		try{
			String[] drawNos = request.getParameterValues("drawNo");

			for(int i=0; i<drawNos.length; i++){
				String drawNo = drawNos[i];
				double amount = 0;
				try{
					amount = Double.parseDouble(request.getParameter("amount_"+drawNo));
				}catch (Exception e){
					throw new XException(XException.ERR_DEFAULT, "审批数必须是数字！");
				}
				String idea = request.getParameter("idea_"+drawNo);
				String storeNo = request.getParameter("store_"+drawNo);
				String drawStep = request.getParameter("step_"+drawNo);

				MOut mOut = mOutMapper.getOutByDrawNo(drawNo);
				String prjNo = mOut.getPrjNo();
				String planNo = mOut.getPlanNo();
				String matNo = mOut.getMatNo();
				String teamNo = mOut.getPlanTeam();
				Integer stockTeam = mOut.getTeamNo();
				String prevStep = mOut.getDrawStep();
				BigDecimal prevAmount = mOut.getApplyAmount();
				BigDecimal matPrice = mOut.getMatPrice();
				String itemNo = mOut.getItemNo();
				String checkNo = mOut.getCheckNo();
				String applyType = mOut.getOutType();
				String applyInfo = mOut.getApplyInfo();
				String oldStore = mOut.getStoreNo();
				String payTeam = teamNo;

				if("7202F".equals(prevStep) || "7202X".equals(prevStep)){
					throw new XException(XException.ERR_DEFAULT, "审批流程已结束");
				}
				if(!prevStep.equals(drawStep)){
					throw new XException(XException.ERR_DEFAULT, "步骤不对应，可能领料已被审批！");
				}
				if(new BigDecimal(amount+"").compareTo(prevAmount) == 1){
					throw new XException(XException.ERR_DEFAULT, "只能减少数量！");
				}

				if(!"72020".equals(prevStep) && !"7202X".equals(prevStep) && !"7202F".equals(prevStep) && !utilService.hasCheckRight(prevStep, userInfo.getUuid())){
					throw new XException(XException.ERR_DEFAULT, "没有对应的权限！");
				}

				String reserveNo = "";
//				MPlan mPlan = mPlanMapper.getPlanByPlanNo(planNo);
//				if(mPlan==null){
//					throw new XException(XException.ERR_DEFAULT, "没有对应的计划！");
//				}
//				String reserveNo = "";
//				if(!"0".equals(planNo)){
//					payTeam = mPlan.getPayTeam()+"";
//					BigDecimal usableAmount = mPlan.getUsableAmount();
//					reserveNo = mPlan.getReserveNo();
//
//					if(usableAmount.compareTo(new BigDecimal(amount+"")) == -1){
//						throw new XException(XException.ERR_DEFAULT, "计划可领数不足["+ drawNo +"]");
//					}
//				}


//				MPrj mPrj = mPrjMapper.getPrjByPrjNo(prjNo);
//				String prjStatus = mPrj.getPrjStatus();
//				if(!"m.prjStatus.0".equals(prjStatus)){
//					Organization organization = organizationMapper.getListById(mPrj.getTeamNo()+"");
//					throw new XException(XException.ERR_DEFAULT, organization.getText()+" "+ mPrj.getPrjName()+ " 工程已结束或暂停！");
//				}

				if(!storeNo.equals(oldStore)){
					MStore mStore = mStoreMapper.getStoreByNo(storeNo);
					String ifCtl = mStore.getIfControl();
					if("1".equals(ifCtl)){
						MStock mStock = mStockMapper.getStock(storeNo, matNo);
						BigDecimal stock = mStock.getStockAmount();
						if(new BigDecimal(amount+"").subtract(stock).compareTo(new BigDecimal("0")) == 1){
							throw new XException(XException.ERR_DEFAULT, "库存不足");
						}
					}
				}

				MMaterial mMaterial = mMaterialMapper.getMatByNo(matNo);
				String matCode = mMaterial.getMatCode();

				String nextStep = getNextDrawStep(prevStep, matPrice.doubleValue(), matCode);

				MOut mOut1 = new MOut();
				mOut1.setDrawStep(nextStep);
				mOut1.setChkAmount(new BigDecimal(amount+""));
				mOut1.setTeamNo(stockTeam);
				mOut1.setStoreNo(storeNo);
				mOut1.setAgreeEmp(userInfo.getUuid());
				mOut1.setAgreeDate(XDate.getDate());
				mOut1.setAgreeTime(XDate.getTime());
				mOut1.setReserveNo(reserveNo);
				mOut1.setDrawNo(drawNo);
				mOutMapper.updateOut(mOut1);

				TCheck tCheck = new TCheck();
				tCheck.setCheckNo(checkNo);
				tCheck.setStepKey("7202");
				tCheck.setStepCode(prevStep);
				tCheck.setCheckType("sys.checkType.0");
				tCheck.setDirect("sys.checkDirect.0");
				tCheck.setIdea(idea);
				tCheck.setUserId(userInfo.getUuid());
				tCheck.setUserName(userInfo.getUserName());
				tCheck.setTeamNo(Integer.parseInt(teamNo));
				tCheck.setOccDate(XDate.getDate());
				tCheck.setOccTime(XDate.getTime());
				tCheck.setLogInfo("");
				tCheck.setBefAmount(prevAmount);
				tCheck.setBefPrice(matPrice);
				tCheck.setAftAmount(new BigDecimal(amount+""));
				tCheck.setAftPrice(matPrice);
				tCheck.setEmpId(0L);
				tCheckMapper.executeSave(tCheck);

				if("7202F".equals(nextStep)){
					MOut mOut2 = new MOut();
					mOut2.setOutAmount(new BigDecimal(amount+""));
					mOut2.setDrawEmp(userInfo.getUuid());
					mOut2.setOutDate(XDate.getDate());
					mOut2.setOutTime(XDate.getTime());
					mOut2.setfMonth(utilService.getFMonth());
					mOut2.setPayTeam(mItemService.getPayTeam(prjNo, itemNo, teamNo));
					mOut2.setDrawNo(drawNo);
					mOutMapper.updateOut(mOut2);

//					MPlan mPlan1 = new MPlan();
//					mPlan1.setUsableAmount(mPlan.getUsableAmount().subtract(new BigDecimal(amount+"")));
//					mPlan1.setPlanNo(planNo);

					String dzk = "";
//					List<MStore> storeList = mStoreMapper.getDZK("r.storeType2.71", stockTeam+"");
//					if(storeList.size()<1){
//						Organization organization = organizationMapper.getListById(stockTeam+"");
//						throw new XException(XException.ERR_DEFAULT, organization.getText()+"，没有设置队主库！");
//					}
//					dzk = storeList.get(0).getStoreNo();

					MTeamStock mTeamStock = new MTeamStock();
					mTeamStock.setMatAmount(new BigDecimal(amount+""));
					mTeamStock.setUseAmount(new BigDecimal("0"));
					mTeamStock.setTeamNo(stockTeam);
					mTeamStock.setStoreNo(dzk);
					mTeamStock.setMatNo(matNo);
					mTeamStock.setMatStatus("r.stockStatus.1");
					mTeamStock.setUuid(BaseUtils.UUIDGenerator());
					int c =mTeamStockMapper.updateTeamStock(mTeamStock);

					if(c < 1){
						mTeamStock.setSiteCode("");
						mTeamStockMapper.insertTeamStock(mTeamStock);
					}

					MTeamStock mTeamStock1 = mTeamStockMapper.getTeamStock(stockTeam+"", dzk, matNo, "r.stockStatus.1");
					BigDecimal mAmt = mTeamStock1.getMatAmount();
					if(mAmt.compareTo(new BigDecimal("0")) == -1){
						throw new XException(XException.ERR_DEFAULT, "总数不能小于0");
					}
					BigDecimal occBala = new BigDecimal(amount+"").multiply(matPrice);

					MTeamStockLog mTeamStockLog = new MTeamStockLog();
					mTeamStockLog.setLogId(Snow.getUUID()+"");
					mTeamStockLog.setStoreNo(dzk);
					mTeamStockLog.setTeamNo(stockTeam);
					mTeamStockLog.setMatNo(matNo);
					mTeamStockLog.setOccDate(XDate.getDate());
					mTeamStockLog.setOccAmount(new BigDecimal(amount+""));
					mTeamStockLog.setOccBala(occBala);
					mTeamStockLog.setInoutFlag("r.inOutFlag.1");
					mTeamStockLog.setDataSrc("m.dataSrc.9");
					mTeamStockLog.setBillNo(drawNo);
					mTeamStockLog.setMatStatus("r.stockStatus.1");
					mTeamStockLogMapper.insertTeamStockLog(mTeamStockLog);

					//未实现自动消耗

					MStock mStock = new MStock();
					mStock.setOutAmount(new BigDecimal(amount+""));
					mStock.setStockAmount(new BigDecimal(amount+"").multiply(new BigDecimal("-1")));
					mStock.setBulkAmount(new BigDecimal(amount+"").multiply(new BigDecimal("-1")));
					mStock.setStoreNo(storeNo);
					mStock.setMatNo(matNo);
					mStockMapper.updateStock(mStock);

					mStockService.checkStockAmount(matNo, storeNo);

					MOutBill mOutBill = new MOutBill();
					mOutBill.setBillNo(Snow.getUUID()+"");
					mOutBill.setOutType(applyType);
					mOutBill.setLinkNo(drawNo);
					mOutBill.setDrawNo(drawNo);
					mOutBill.setPackNo("0");
					mOutBill.setInNo("0");
					mOutBill.setMatNo(matNo);
					mOutBill.setMatAmount(new BigDecimal(amount+""));
					mOutBill.setMatPrice(matPrice);
					mOutBill.setMatBala(new BigDecimal(amount+"").multiply(matPrice));
					mOutBill.setOccDate(XDate.getDate());
					mOutBill.setDrawEmp(userInfo.getUuid());
					mOutBill.setStoreNo(storeNo);
					mOutBill.setTeamNo(Integer.parseInt(teamNo));
					mOutBill.setPrjNo(prjNo);
					mOutBill.setfMonth(utilService.getFMonth());
					mOutBill.setItemNo(itemNo);
					mOutBill.setPayTeam(Integer.parseInt(payTeam));
					mOutBill.setRemark(applyInfo);
					mOutBill.setPrintCnt(0);
					mOutBillMapper.insertOutBill(mOutBill);

//					List<MTypeMat> mTypeMatList = mTypeMatMapper.getTypeMatByMatNo(matNo);
//					if(mTypeMatList.size()>0){
//						MTypeMat mTypeMat = mTypeMatList.get(0);
//						BigDecimal oldRate = mTypeMat.getOldRate();
//						Integer limit = mTypeMat.getOldLimit();
//						if(limit==null){
//							limit = 0;
//						}
//
//						MHandIn mHandIn = new MHandIn();
//						mHandIn.setBillNo(Snow.getUUID()+"");
//						mHandIn.setMatNo(matNo);
//						mHandIn.setTeamNo(Integer.parseInt(teamNo));
//						mHandIn.setStoreNo("");
//						mHandIn.setMatAmount(new BigDecimal(amount+"").multiply(oldRate).divide(new BigDecimal("100")));
//						mHandIn.setOldRate(oldRate.divide(new BigDecimal("100")));
//						mHandIn.setOccDate(XDate.getDate());
//						mHandIn.setPlanGive(XDate.addDate(XDate.getDate(), limit));
//						mHandIn.setLinkNo(drawNo);
//						mHandIn.setRemark("直接出库，由系统自动产生");
//						mHandIn.setModiEmp(userInfo.getUuid());
//						mHandIn.setModiDate(XDate.getDate());
//						mHandIn.setCheckStep("7500F");
//						mHandIn.setPauseMonth("");
//						mHandInMapper.insertHandIn(mHandIn);
//
//						
//					}
					BigDecimal drawAmountSum = new BigDecimal("0");
					if(drawAmountSum.compareTo(new BigDecimal(amount+"")) == -1){
						List<MIn> mInList = mInMapper.getInList(storeNo, matNo);
						if(mInList.size() < 1){
							throw new XException(XException.ERR_DEFAULT, "入库记录与库存数不符，请校验！");
						}
						for(int j=0; j<mInList.size() && drawAmountSum.compareTo(new BigDecimal(amount+"")) == -1; j++){
							MIn mIn = mInList.get(j);
							String inNo = mIn.getBillNo();
							BigDecimal inPrice = mIn.getSetPrice();
							BigDecimal stockAmount = mIn.getBillAmount();

							String drawBill = drawNo;
							BigDecimal dAmount = new BigDecimal("0");
							if(new BigDecimal(amount+"").subtract(drawAmountSum).compareTo(stockAmount) == 1){
								dAmount = stockAmount;
								drawAmountSum = drawAmountSum.add(dAmount);
								drawBill = Snow.getUUID()+"";
							}else{
								dAmount = new BigDecimal(amount+"").subtract(drawAmountSum);
								drawAmountSum = drawAmountSum.add(dAmount);
							}

							BigDecimal matBala = dAmount.multiply(inPrice);

							MFifo mFifo = new MFifo();
							mFifo.setBillNo(drawBill);
							mFifo.setDrawNo(drawNo);
							mFifo.setInNo(inNo);
							mFifo.setStoreNo(storeNo);
							mFifo.setMatNo(matNo);
							mFifo.setMatAmount(dAmount);
							mFifo.setMatPrice(inPrice);
							mFifo.setMatBala(matBala);
							mFifo.setOccDate(XDate.getDate());
							mFifo.setTeamNo(Integer.parseInt(teamNo));
							mFifoMapper.insertFifo(mFifo);

							mInMapper.updateMIn1(dAmount, inNo);
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
	public void backOutCheck(UserInfo userInfo, HttpServletRequest request) throws Exception{
		try{
			String[] drawNos = request.getParameterValues("drawNo");

			for(int i=0; i<drawNos.length; i++){
				String drawNo = drawNos[i];

				double amount = 0;
				try{
					amount = Double.parseDouble(request.getParameter("amount_"+drawNo));
				}catch (Exception e){
					throw new XException(XException.ERR_DEFAULT, "审批数必须是数字！");
				}
				String idea = request.getParameter("idea_"+drawNo);
				String drawStep = request.getParameter("step_"+drawNo);

				MOut mOut = mOutMapper.getOutByDrawNo(drawNo);
				String dbStep = mOut.getDrawStep();
				if(!dbStep.equals(drawStep)){
					throw new XException(XException.ERR_DEFAULT, "审批流程不符，请刷新！");
				}
				String checkNo = mOut.getCheckNo();

				MOut mOut1 = new MOut();
				mOut1.setDrawStep("7202X");
				mOut1.setDrawNo(drawNo);
				mOutMapper.updateOut(mOut1);

				TCheck tCheck = new TCheck();
				tCheck.setCheckNo(checkNo);
				tCheck.setStepKey("7202");
				tCheck.setStepCode(drawStep);
				tCheck.setCheckType("sys.checkType.0");
				tCheck.setDirect("sys.checkDirect.1");
				tCheck.setIdea(idea);
				tCheck.setUserId(userInfo.getUuid());
				tCheck.setUserName(userInfo.getUserName());
				tCheck.setTeamNo(Integer.parseInt(userInfo.getOrgId()));
				tCheck.setOccDate(XDate.getDate());
				tCheck.setOccTime(XDate.getTime());
				tCheck.setLogInfo("审前数量："+amount);
				tCheck.setBefAmount(new BigDecimal(amount+""));
				tCheck.setBefPrice(new BigDecimal("0"));
				tCheck.setAftAmount(new BigDecimal(amount+""));
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
	public List<HashMap<String, String>> getHzLog(String drawNo) throws Exception{
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		String fMonth = utilService.getFMonth();

		MOut mOut = mOutMapper.getOutByDrawNo(drawNo);
		String matNo = mOut.getMatNo();
		String prjNo = mOut.getPrjNo();
		String teamNo = mOut.getTeamNo()+"";
		String itemNo = mOut.getItemNo();
		String prjType = mOut.getPrjType1();

		MMaterial mMaterial = mMaterialMapper.getMatByNo(matNo);
		String newOld = mMaterial.getNewOld();
		String matCode = mMaterial.getMatCode();

		String newMatNo = "";
		String oldMatNo = "";
		if("J".equals(newOld)){
			oldMatNo = matNo;
			newMatNo = mMaterialMapper.getNewMatNo(matCode);
		}else{
			newMatNo = matNo;
			oldMatNo = mMaterialMapper.getOldMatNo(matCode);
		}

		HashMap<String, String> matMap = new HashMap<>();
		matMap.put("item", "代码名称");
		matMap.put("content", matCode+"  "+mMaterial.getMatName());
		list.add(matMap);

		double budgetBala = utilService.getBudgetBala(prjNo, teamNo, fMonth, itemNo, prjType);
		double occurBala = utilService.getOccurBala(teamNo, prjNo, itemNo, fMonth);

		HashMap<String, String> budgetMap = new HashMap<>();
		budgetMap.put("item", "预算执行");
		budgetMap.put("content", "预算："+budgetBala+"  已领："+occurBala+"  剩余："+(budgetBala-occurBala));
		list.add(budgetMap);

		List<MStock> mineList = mStockMapper.getKKXL(matNo);
		BigDecimal xlAmount = new BigDecimal("0");
		String kkDetail = "";
		for(int i=0; i<mineList.size(); i++){
			MStock mStock = mineList.get(i);
			xlAmount = xlAmount.add(mStock.getStockAmount());
			kkDetail += mStock.getSiteCode() + ":" + mStock.getStockAmount() + "；";
		}

		double oldMineAmount = mStockMapper.getStockAmount(oldMatNo);

		HashMap<String, String> kkcMap = new HashMap<>();
		kkcMap.put("item", "矿井库存");
		kkcMap.put("content", "新料："+xlAmount.doubleValue()+"  旧料："+oldMineAmount);
		list.add(kkcMap);

		HashMap<String, String> kkcDetailMap = new HashMap<>();
		kkcDetailMap.put("item", "矿井库存");
		kkcDetailMap.put("content", kkDetail);
		list.add(kkcDetailMap);

		MTeamStock mTeamStock1 = mTeamStockMapper.getTeamOld(oldMatNo, teamNo);
		double oldDeptAmount = mTeamStock1==null ? 0 : mTeamStock1.getMatAmount().doubleValue();

		HashMap<String, String> dkcMap = new HashMap<>();
		dkcMap.put("item", "区队库存");
		dkcMap.put("content", "新料：0  旧料："+oldDeptAmount);
		list.add(dkcMap);

		List<MTeamStock> teamList = mTeamStockMapper.getTeamNew(newMatNo);
		String dkDetail = "";
		for(int i=0; i<teamList.size(); i++){
			MTeamStock mTeamStock = teamList.get(i);
			dkDetail += mTeamStock.getSiteCode()+":"+mTeamStock.getMatAmount();
		}

		HashMap<String, String> dkcDetailMap = new HashMap<>();
		dkcDetailMap.put("item", "区队库存");
		dkcDetailMap.put("content", dkDetail);
		list.add(dkcDetailMap);

		HashMap<String, String> planMap = new HashMap<>();
		planMap.put("item", "计划信息");
		planMap.put("content", "使用日期："+mOut.getApplyDate()+" 备注："+ mOut.getApplyInfo());
		list.add(planMap);

		HashMap<String, String> applyMap = new HashMap<>();
		applyMap.put("item", "申请信息");
		applyMap.put("content", mOut.getApplyInfo());
		list.add(applyMap);

		MMakeMatDept mMakeMatDept = mMakeMatDeptMapper.findByMatNo(matNo);

		HashMap<String, String> makeMap = new HashMap<>();
		makeMap.put("item", "加工单位");
		makeMap.put("content", mMakeMatDept==null?"":mMakeMatDept.getMakeDeptName());
		list.add(makeMap);

		return list;
	}

	public List<MOut> getDrawList(String drawNo){
		MOut mOut = mOutMapper.getOutByDrawNo(drawNo);
		String teamNo = mOut.getTeamNo()+"";
		String matNo = mOut.getMatNo();

		MMaterial mMaterial = mMaterialMapper.getMatByNo(matNo);
		String newOld = mMaterial.getNewOld();
		String matCode = mMaterial.getMatCode();

		String newMatNo = "";
		String oldMatNo = "";
		if("J".equals(newOld)){
			oldMatNo = matNo;
			newMatNo = mMaterialMapper.getNewMatNo(matCode);
		}else{
			newMatNo = matNo;
			oldMatNo = mMaterialMapper.getOldMatNo(matCode);
		}

		return mOutMapper.getDrawList(teamNo, newMatNo, oldMatNo);
	}

	@Override
	public List<MTeamBill> getConsumeList(String drawNo){
		MOut mOut = mOutMapper.getOutByDrawNo(drawNo);
		String teamNo = mOut.getTeamNo()+"";
		String matNo = mOut.getMatNo();

		MMaterial mMaterial = mMaterialMapper.getMatByNo(matNo);
		String newOld = mMaterial.getNewOld();
		String matCode = mMaterial.getMatCode();

		String newMatNo = "";
		String oldMatNo = "";
		if("J".equals(newOld)){
			oldMatNo = matNo;
			newMatNo = mMaterialMapper.getNewMatNo(matCode);
		}else{
			newMatNo = matNo;
			oldMatNo = mMaterialMapper.getOldMatNo(matCode);
		}

		Map map = new HashMap();
		map.put("p1", "sys_organization");
		map.put("p2", teamNo);
		map.put("p3", "idStr");
		organizationMapper.getChildIds(map);
		String deptNos = map.get("p3").toString();

		return mTeamBillMapper.getConsumeList(deptNos, newMatNo, oldMatNo);
	}

	@Override
	public List<MTeamStock> getKcList(String drawNo){
		MOut mOut = mOutMapper.getOutByDrawNo(drawNo);
		String matNo = mOut.getMatNo();

		MMaterial mMaterial = mMaterialMapper.getMatByNo(matNo);
		String newOld = mMaterial.getNewOld();
		String matCode = mMaterial.getMatCode();

		String newMatNo = "";
		if("J".equals(newOld)){
			newMatNo = mMaterialMapper.getNewMatNo(matCode);
		}else{
			newMatNo = matNo;
		}

		List<MTeamStock> stockList = mTeamStockMapper.getTeamStockList(newMatNo);
		MMaterial m = mMaterialMapper.getMatByNo(newMatNo);
		String matUnitName = m==null ? "" : m.getMatUnitName();
		for(int i=0; i<stockList.size(); i++){
			stockList.get(i).setMatUnitName(matUnitName);
		}

		return stockList;
	}

	@Override
	public List<HashMap<String, String>> getApplyStepList(String date1Qry, String date2Qry, String deptIds){
		ArrayList<HashMap<String, String>> list = new ArrayList<>();

		List<MOut> outList = mOutMapper.getApplyStepList(date1Qry, date2Qry, deptIds);
		List<MOut> teamList = mOutMapper.getApplyStepTeam(date1Qry, date2Qry, deptIds);
		List<SysAuditProcess> stepList = sysAuditProcessService.queryListByFuuid("D0180DF7E4324DD091306C26F2207145");

		DataCantainer<MOut> dataDc = new DataCantainer<MOut>((ArrayList)outList);

		HashMap<String, String> tMap = new HashMap<>();
		tMap.put("teamNo", "");
		tMap.put("teamName", "合计");
		tMap.put("total", dataDc.getSum("count")+"");
		for(int i=0; i <stepList.size(); i++){
			SysAuditProcess sysAuditProcess = stepList.get(i);
			String processNo = sysAuditProcess.getProcessNo();
			tMap.put(processNo, processNo+","+dataDc.findDataCantainer("draw_step", processNo).getSum("count"));
		}
		list.add(tMap);

		for(int i=0; i<teamList.size(); i++){
			MOut mOut = teamList.get(i);
			String teamNo = mOut.getPlanTeam()+"";
			String teamName = mOut.getPlanTeamName();
			DataCantainer<MOut> d = dataDc.findDataCantainer("plan_team", teamNo);

			HashMap<String, String> map = new HashMap<>();
			map.put("teamNo", teamNo);
			map.put("teamName", teamName);
			map.put("total", d.getSum("count")+"");
			for(int j=0; j<stepList.size(); j++){
				SysAuditProcess sysAuditProcess = stepList.get(j);
				String processNo = sysAuditProcess.getProcessNo();
				map.put(processNo, processNo+","+d.findDataCantainer("draw_step", processNo).getSum("count"));
			}
			list.add(map);
		}

		return list;
	}

	@Override
	public PageSet<MOut> getApplyStepDetail(PageParam pageParam, String filterSort, String teamNo, String date1Qry, String date2Qry, String drawStep, String itemNo, String planTypeQry, String matQry, String userTeam, boolean ifRole){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MOut> list = mOutMapper.getApplyStepDetail(teamNo, date1Qry, date2Qry, drawStep, itemNo, planTypeQry, matQry, userTeam, ifRole);
		PageInfo<MOut> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public PageSet<MOut> getOutDrawPageSet(PageParam pageParam, String filterSort, String date1Qry, String date2Qry, String teamNoQry, String stepQry, String itemQry, String urgentQry, String drawNoQry, String storeNoQry, String erpTypeQry, String matCodeQry, String matNameQry, String deptIds, String userId, String newOldQry){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MOut> list = mOutMapper.getOutDrawPageSet(date1Qry, date2Qry, teamNoQry, stepQry, itemQry, urgentQry, drawNoQry, storeNoQry, erpTypeQry, matCodeQry, matNameQry, deptIds, userId, newOldQry);
		PageInfo<MOut> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public List<MOut> getOutDrawList(String date1Qry, String date2Qry, String teamNoQry, String stepQry, String itemQry, String urgentQry, String drawNoQry, String storeNoQry, String erpTypeQry, String matCodeQry, String matNameQry, String deptIds, String userId, String newOldQry){
		return mOutMapper.getOutDrawPageSet(date1Qry, date2Qry, teamNoQry, stepQry, itemQry, urgentQry, drawNoQry, storeNoQry, erpTypeQry, matCodeQry, matNameQry, deptIds, userId, newOldQry);
	}

	@Transactional
	@Override
	public void doCancel(String drawNo, UserInfo userInfo) throws Exception{
		try{
			MOut mOut = mOutMapper.getOutByDrawNo(drawNo);
			String drawStep = mOut.getDrawStep();
			String storeNo = mOut.getStoreNo();
			String teamNo = mOut.getTeamNo()+"";
			String userNo = userInfo.getUuid();
			String occDate = XDate.getDate();
			String planNo = mOut.getPlanNo();
			BigDecimal amount = mOut.getOutAmount();
			String matNo = mOut.getMatNo();

			if(!"7202F".equals(drawStep)){
				throw new XException(XException.ERR_DEFAULT, "非定稿，不允许冲单！");
			}

			mPlanMapper.updPlan3(amount, planNo);

			MStock mStock = new MStock();
			mStock.setInAmount(new BigDecimal("0"));
			mStock.setOutAmount(amount.multiply(new BigDecimal("-1")));
			mStock.setStockAmount(amount);
			mStock.setPackAmount(new BigDecimal("0"));
			mStock.setLockAmount(new BigDecimal("0"));
			mStock.setBulkAmount(amount);
			mStock.setStoreNo(storeNo);
			mStock.setMatNo(matNo);
			int c = mStockMapper.updateStock(mStock);

			if(c<1){
				mStock.setSiteCode("");
				mStockMapper.insertStock(mStock);
			}

			mStockService.checkStockAmount(matNo, storeNo);

			MOut m = new MOut();
			String drawNo1 = Snow.getUUID()+"";
			m.setDrawNo(drawNo1);
			m.setOutType("r.outBillType.5");
			m.setPlanNo(planNo);
			m.setPlanTeam(teamNo);
			m.setMatNo(matNo);
			m.setApplyAmount(amount.multiply(new BigDecimal("-1")));
			m.setApplyDate(occDate);
			m.setApplyTime(XDate.getTime());
			m.setApplyEmp(userNo);
			m.setUrgentLevel(mOut.getUrgentLevel());
			m.setStoreAddr(storeNo);
			m.setIfSend(mOut.getIfSend());
			m.setUseAddr(mOut.getUseAddr());
			m.setApplyInfo("冲单："+drawNo);
			m.setChkAmount(amount.multiply(new BigDecimal("-1")));
			m.setAgreeEmp("");
			m.setAbcType("");
			m.setDrawStep(drawStep);
			m.setCheckNo("0");
			m.setOutAmount(amount.multiply(new BigDecimal("-1")));
			m.setDrawEmp("");
			m.setStoreNo(storeNo);
			m.setTeamNo(Integer.parseInt(teamNo));
			m.setOutDate(occDate);
			m.setOutTime(XDate.getTime());
			m.setItemNo(mOut.getItemNo());
			m.setPlanPrice(mOut.getPlanPrice());
			m.setMatPrice(mOut.getMatPrice());
			m.setPrjNo(mOut.getPrjNo());
			m.setPrice1(new BigDecimal("0"));
			m.setPrice2(new BigDecimal("0"));
			m.setDataSrc(mOut.getDataSrc());
			m.setLinkNo(drawNo);
			m.setOfferTeam(mOut.getOfferTeam());
			m.setUuid(BaseUtils.UUIDGenerator());
			mOutMapper.insertOut(m);

			MInBack mInBack = new MInBack();
			mInBack.setBackId(drawNo1);
			mInBack.setBackStep("7201F");
			mInBack.setStoreNo(storeNo);
			mInBack.setMatNo(matNo);
			mInBack.setBackAmount(new BigDecimal(amount + ""));
			mInBack.setBackDate(XDate.getDate());
			mInBack.setBackEmp(LoginInfo.getUuid());
			mInBack.setBackInfo("");
			mInBack.setCheckNo("0");
			mInBack.setOfferNo("");
			mInBack.setInitBill(drawNo);
			mInBack.setRedBill("0");
			mInBackMapper.insertInBack(mInBack);
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	public List<KeyAnaly> getKeyAnalyList(String date1Qry, String date2Qry){
		List<MMaterial> balaList = mOutMapper.getKeyAnalyBalaTop20(date1Qry, date2Qry);
		List<MMaterial> priceList = mOutMapper.getKeyAnalyPriceTop20(date1Qry, date2Qry);
		List<MMaterial> amountList = mOutMapper.getKeyAnalyAmountTop20(date1Qry, date2Qry);

		List<KeyAnaly> list = new ArrayList<>();
		for(int i=0; i<20; i++){
			KeyAnaly keyAnaly = new KeyAnaly();

			MMaterial balaMaterial = i+1 > balaList.size() ? null : balaList.get(i);
			MMaterial priceMaterial = i+1 > priceList.size() ? null : priceList.get(i);
			MMaterial amountMaterial = i+1 > amountList.size() ? null : amountList.get(i);

			keyAnaly.setBalaMatCode(balaMaterial == null ? "" : balaMaterial.getMatCode());
			keyAnaly.setBalaMatName(balaMaterial == null ? "" : balaMaterial.getMatName());
			keyAnaly.setBalaMatUnit(balaMaterial == null ? "" : balaMaterial.getMatUnit());
			keyAnaly.setBalaTypeName(balaMaterial == null ? "" : balaMaterial.getTypeName());
			keyAnaly.setBalaBala(balaMaterial == null ? "" : Data.normalToFinal(Data.trimDoubleNo0(balaMaterial.getBala().doubleValue(), 2)));

			keyAnaly.setPriceMatCode(priceMaterial == null ? "" : priceMaterial.getMatCode());
			keyAnaly.setPriceMatName(priceMaterial == null ? "" : priceMaterial.getMatName());
			keyAnaly.setPriceMatUnit(priceMaterial == null ? "" : priceMaterial.getMatUnit());
			keyAnaly.setPriceTypeName(priceMaterial == null ? "" : priceMaterial.getTypeName());
			keyAnaly.setPriceBala(priceMaterial == null ? "" : Data.normalToFinal(Data.trimDoubleNo0(priceMaterial.getBala().doubleValue(), 2)));

			keyAnaly.setAmountMatCode(amountMaterial == null ? "" : amountMaterial.getMatCode());
			keyAnaly.setAmountMatName(amountMaterial == null ? "" : amountMaterial.getMatName());
			keyAnaly.setAmountMatUnit(amountMaterial == null ? "" : amountMaterial.getMatUnit());
			keyAnaly.setAmountTypeName(amountMaterial == null ? "" : amountMaterial.getTypeName());
			keyAnaly.setAmountBala(amountMaterial == null ? "" : Data.normalToFinal(Data.trimDoubleNo0(amountMaterial.getBala().doubleValue(), 2)));

			list.add(keyAnaly);
		}

		return list;
	}

	@Override
	@Transactional
	public void submitOldApply(HttpServletRequest request, UserInfo userInfo) throws Exception{
		try{
			String teamNo = request.getParameter("teamNo");
			String ifSend = request.getParameter("ifSend");
			String useAddr = request.getParameter("useAddr");
			String sendTime = request.getParameter("sendTime");
			String getEmp = request.getParameter("getEmp");
			String occDate = XDate.dateTo8(request.getParameter("occDate"));

			if("1".equals(ifSend) && ("".equals(useAddr) || "".equals(sendTime) || "".equals(getEmp))){
				throw new XException(XException.ERR_DEFAULT, "如需配送，必须指定配送地点及配送时间");
			}

			List<MPrj> prjList = mPrjMapper.getPrjList1(teamNo, "1");
			if(prjList.size()<1){
				throw new XException(XException.ERR_DEFAULT, "区队没有可用工程！");
			}
			String prjNo = prjList.get(0).getPrjNo();
			String prevDrawStep = "72020";
			String nextStep = "72021";
			String userId = userInfo.getUuid();
			String outType = "r.outBillType.2";
			String dataSrc = "m.dataSrc.P";
			String pickDate = "";
			String pickTime = "";
			if(!"".equals(sendTime)){
				pickDate = XDate.dateTo8(sendTime.substring(0, 10));
				pickTime = sendTime.substring(11, 13)+"0000";
			}

			if(!"".equals(useAddr)){
				ArrayList<String[]> condList = new ArrayList<>();
				condList.add(new String[]{"mat_addr", "=", useAddr});
				boolean flag = utilService.checkColumnDataExist("m_addr", condList);
				if(!flag){
					MAddr mAddr = new MAddr();
					mAddr.setMatAddr(useAddr);
					mAddr.setTeamNo(Integer.parseInt(teamNo));
					mAddr.setUpDown("");
					mAddr.setIfUsing("1");
					mAddrMapper.insertAddr(mAddr);
				}
			}

			String[] keys = request.getParameterValues("uuid");
			if(keys==null || keys.length<1){
				throw new XException(XException.ERR_DEFAULT, "请先选择要提交的记录！");
			}
			for(int i=0; i<keys.length; i++){
				String key = keys[i];
				String storeNo = key.split("_")[0];
				String matNo = key.split("_")[1];
				double outAmount = 0;
				try {
					outAmount = Double.parseDouble(request.getParameter("amount_"+key));
				}catch (Exception e){
					throw new XException(XException.ERR_DEFAULT, "申请数必须是数字！");
				}
				if(outAmount <= 0){
					throw new XException(XException.ERR_DEFAULT, "申请数必须大于0");
				}

				MStock stock = mStockMapper.getStock(storeNo, matNo);
				BigDecimal stockAmount = new BigDecimal("0");
				if(stock != null){
					stockAmount = stock.getStockAmount();
				}
				if(stockAmount.compareTo(new BigDecimal(outAmount+"")) == -1){
					throw new XException(XException.ERR_DEFAULT, "库存不足！");
				}
				String applyInfo = request.getParameter("remark_"+key);

				MMaterial material = mMaterialMapper.getMatByNo(matNo);
				BigDecimal matPrice = material.getMatPrice();
				BigDecimal fixPrice = material.getFixPrice();
				String outTeam = material.getFixContent();
				if("".equals(outTeam) || outTeam==null){
					throw new XException(XException.ERR_DEFAULT, "请维护责任科室！");
				}

				String checkNo = Snow.getUUID()+"";
				String drawNo = Snow.getUUID()+"";

				MOut mOut = new MOut();
				mOut.setDrawNo(drawNo);
				mOut.setOutType(outType);
				mOut.setPlanTeam(teamNo);
				mOut.setMatNo(matNo);
				mOut.setApplyAmount(new BigDecimal(outAmount+""));
				mOut.setApplyDate(occDate);
				mOut.setApplyTime(XDate.getTime());
				mOut.setApplyEmp(userId);
				mOut.setUrgentLevel("");
				mOut.setStoreAddr("");
				mOut.setIfSend(ifSend);
				mOut.setUseAddr(useAddr);
				mOut.setApplyInfo(applyInfo);
				mOut.setChkAmount(new BigDecimal(outAmount+""));
				mOut.setAgreeEmp("");
				mOut.setAbcType("");
				mOut.setDrawStep(nextStep);
				mOut.setCheckNo(checkNo);
				mOut.setOutAmount(new BigDecimal("0"));
				mOut.setDrawEmp("");
				mOut.setStoreNo(storeNo);
				mOut.setTeamNo(Integer.parseInt(teamNo));
				mOut.setOutDate("");
				mOut.setOutTime("");
				mOut.setItemNo("");
				mOut.setPlanPrice(matPrice);
				mOut.setMatPrice(matPrice);
				mOut.setPrjNo(prjNo);
				mOut.setPrice1(fixPrice);
				mOut.setPrice2(new BigDecimal("0"));
				mOut.setDataSrc(dataSrc);
				mOut.setLinkNo("");
				mOut.setOfferTeam(Integer.parseInt(outTeam));
				mOut.setPickDate(pickDate);
				mOut.setPickTime(pickTime);
				mOut.setReserve1(getEmp);
				mOut.setUuid(Snow.getUUID()+"");
				mOut.setReserve2(utilService.getBillCode("m_out", "reserve2", XDate.getMonth().substring(2, 6)+"F", 5, null));
				mOutMapper.insertOut(mOut);

				TCheck tCheck = new TCheck();
				tCheck.setCheckNo(checkNo);
				tCheck.setStepKey("7202");
				tCheck.setStepCode(prevDrawStep);
				tCheck.setCheckType("sys.checkType.0");
				tCheck.setDirect("sys.checkDirect.0");
				tCheck.setIdea("");
				tCheck.setUserId(userId);
				tCheck.setUserName(userInfo.getUserName());
				tCheck.setTeamNo(Integer.parseInt(userInfo.getOrgId()));
				tCheck.setOccDate(XDate.getDate());
				tCheck.setOccTime(XDate.getTime());
				tCheck.setLogInfo("");
				tCheck.setBefAmount(new BigDecimal(outAmount+""));
				tCheck.setBefPrice(matPrice);
				tCheck.setAftAmount(new BigDecimal(outAmount+""));
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
	public PageSet<MOut> getOldApplyDetail(PageParam pageParam, String filterSort, String date1Qry, String date2Qry, String userTeam, String matQry){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MOut> list = mOutMapper.getOldApplyDetail(date1Qry, date2Qry, userTeam, matQry);
		PageInfo<MOut> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public PageSet<MOut> getOldOutChkList(PageParam pageParam, String filterSort, String userId, String userTeam, String teamNoQry){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MOut> list = mOutMapper.getOldOutChkList(userId, userTeam, teamNoQry);
		PageInfo<MOut> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	@Transactional
	public void submitOldOutChk(HttpServletRequest request, UserInfo userInfo) throws Exception{
		try{
			String occDate = XDate.getDate();

			String[] drawNos = request.getParameterValues("drawNo");
			if(drawNos==null || drawNos.length<1){
				throw new XException(XException.ERR_DEFAULT, "请先选择要审批的申请！");
			}
			for(int i=0; i<drawNos.length; i++){
				String drawNo = drawNos[i];
				String nowStep = request.getParameter("step_"+drawNo);
				double amount = 0;
				try {
					amount = Double.parseDouble(request.getParameter("amount_"+drawNo));
				}catch (Exception e){
					throw new XException(XException.ERR_DEFAULT, "申请数量必须是数字！");
				}
				if(amount <= 0){
					throw new XException(XException.ERR_DEFAULT, "申请数量必须大于零！");
				}

				MOut mOut = mOutMapper.getOutByDrawNo(drawNo);
				String prevStep = mOut.getDrawStep();
				String storeNo = mOut.getStoreNo();
				String matNo = mOut.getMatNo();
				String checkNo = mOut.getCheckNo();
				BigDecimal matAmount = mOut.getChkAmount();
				BigDecimal matPrice = mOut.getMatPrice();

				if(!nowStep.equals(prevStep)){
					throw new XException(XException.ERR_DEFAULT, "审批步骤不对！");
				}
				if(!"72020".equals(prevStep) && !"7202X".equals(prevStep) && !"7202F".equals(prevStep) && !utilService.hasCheckRight(nowStep, userInfo.getUuid())){
					throw new XException(XException.ERR_DEFAULT, "没有对应的权限！");
				}
				if(new BigDecimal(amount+"").compareTo(matAmount) == 1){
					throw new XException(XException.ERR_DEFAULT, "审批数量不能增加！");
				}

				String nextStep	= "";
				if("72021".equals(nowStep)){
					nextStep = "72023";
				}else if("72023".equals(nowStep)){
					nextStep = "72022";
				}else if("72022".equals(nowStep)){
					nextStep = "7202T";
				}else if("7202T".equals(nowStep)){
					nextStep = "7202F";
				}

				if("".equals(nextStep)){
					throw new XException(XException.ERR_DEFAULT, "未知的审核步骤！");
				}

				MOut m = new MOut();
				m.setDrawNo(drawNo);
				m.setDrawStep(nextStep);
				m.setChkAmount(new BigDecimal(amount+""));
				if("72022".equals(nowStep)){
					m.setAgreeEmp(userInfo.getUuid());
					m.setAgreeDate(occDate);
					m.setAgreeTime(XDate.getTime());
				}
				if("7202F".equals(nextStep)){
					m.setOutAmount(new BigDecimal(amount+""));
					m.setDrawEmp(userInfo.getUuid());
					m.setOutDate(occDate);
					m.setOutTime(XDate.getTime());
					m.setfMonth(occDate.substring(0, 6));
				}
				mOutMapper.updateOut(m);

				TCheck tCheck = new TCheck();
				tCheck.setCheckNo(checkNo);
				tCheck.setStepKey("7202");
				tCheck.setStepCode(nowStep);
				tCheck.setCheckType("sys.checkType.0");
				tCheck.setDirect("sys.checkDirect.0");
				tCheck.setIdea("");
				tCheck.setUserId(userInfo.getUuid());
				tCheck.setUserName(userInfo.getUserName());
				tCheck.setTeamNo(Integer.parseInt(userInfo.getOrgId()));
				tCheck.setOccDate(XDate.getDate());
				tCheck.setOccTime(XDate.getTime());
				tCheck.setLogInfo("");
				tCheck.setBefAmount(matAmount);
				tCheck.setBefPrice(matPrice);
				tCheck.setAftAmount(new BigDecimal(amount+""));
				tCheck.setAftPrice(matPrice);
				tCheck.setEmpId(0L);
				tCheckMapper.executeSave(tCheck);

				if("7202F".equals(nextStep)){
					MStock mStock = new MStock();
					mStock.setStoreNo(storeNo);
					mStock.setMatNo(matNo);
					mStock.setInAmount(new BigDecimal("0"));
					mStock.setOutAmount(new BigDecimal(amount+""));
					mStock.setStockAmount(new BigDecimal(amount+"").multiply(new BigDecimal("-1")));
					mStock.setPackAmount(new BigDecimal("0"));
					mStock.setLockAmount(new BigDecimal("0"));
					mStock.setBulkAmount(new BigDecimal(amount+"").multiply(new BigDecimal("-1")));
					int c = mStockMapper.updateStock(mStock);
					if(c<1){
						mStock.setSiteCode("");
						mStockMapper.insertStock(mStock);
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
	public void backOldOutChk(HttpServletRequest request, UserInfo userInfo) throws Exception{
		try{
			String[] drawNos = request.getParameterValues("drawNo");
			if(drawNos==null || drawNos.length<1){
				throw new XException(XException.ERR_DEFAULT, "请先选择要退回的申请！");
			}

			for(int i=0; i<drawNos.length; i++){
				String drawNo = drawNos[i];
				String nowStep = request.getParameter("step_"+drawNo);

				MOut m = mOutMapper.getOutByDrawNo(drawNo);
				String prevStep = m.getDrawStep();
				if("7202F".equals(prevStep)){
					throw new XException(XException.ERR_DEFAULT, "审批步骤已定稿！");
				}
				if(!nowStep.equals(prevStep)){
					throw new XException(XException.ERR_DEFAULT, "审批步骤不对！");
				}

				MOut mOut = new MOut();
				mOut.setDrawNo(drawNo);
				mOut.setDrawStep("7202X");
				mOutMapper.updateOut(mOut);
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	public List<MOut> getDrawApplyPrint(String drawNos){
		return mOutMapper.getDrawApplyPrint(drawNos);
	}

	@Override
	public List<MOut> getOutStatQryItem(String beginDateQry, String endDateQry, String matCodeQry, String matNameQry){
		return mOutMapper.getOutStatQryItem(beginDateQry, endDateQry, matCodeQry, matNameQry);
	}

	@Override
	public List<HashMap<String, Object>> getOutStatQryList(String beginDate, String endDateQry, String matCodeQry, String matNameQry){
		List<MOut> dataList = mOutMapper.getOutStatQryList(beginDate, endDateQry, matCodeQry, matNameQry);
		DataCantainer<MOut> dataDc = new DataCantainer<MOut>((ArrayList) dataList);
		List<MOut> storeList = mOutMapper.getOutStatQryItem(beginDate, endDateQry, matCodeQry, matNameQry);

		List<HashMap<String, Object>> retList = new ArrayList<>();

		HashMap<String, Object> totalMap = new HashMap<>();
		totalMap.put("team", "合计");
		totalMap.put("teamNo", "");
		totalMap.put("total", Data.trimDoubleNo0(dataDc.getSum("bala"), 2));
		for(int i=0; i<storeList.size(); i++){
			MOut store = storeList.get(i);
			String storeNo = store.getStoreNo();
			totalMap.put(storeNo, Data.trimDoubleNo0(dataDc.findDataCantainer("store_no", storeNo).getSum("bala"), 2));
		}
		retList.add(totalMap);

		List<MOut> teamList = mOutMapper.getOutStatQryTeam(beginDate, endDateQry, matCodeQry, matNameQry);
		for(int k=0; k<teamList.size(); k++){
			MOut d = teamList.get(k);
			String teamNo = d.getTeamNo()+"";
			HashMap<String, Object> teamMap = new HashMap<>();
			teamMap.put("team", d.getTeamName());
			teamMap.put("teamNo", teamNo);
			teamMap.put("total", Data.trimDoubleNo0(dataDc.findDataCantainer("team_no", teamNo).getSum("bala"), 2));
			for(int j=0; j<storeList.size(); j++){
				MOut store = storeList.get(j);
				String storeNo = store.getStoreNo();
				teamMap.put(storeNo, Data.trimDoubleNo0(dataDc.findDataCantainer("store_no", storeNo, "team_no", teamNo).getSum("bala"), 2));
			}
			retList.add(teamMap);
		}

		return retList;
	}

	@Override
	public List<MOut> getOutStatQryDetail(String teamNo, String date1Qry, String date2Qry, String matCodeQry, String matNameQry){
		return mOutMapper.getOutStatQryDetail(teamNo, date1Qry, date2Qry, matCodeQry, matNameQry);
	}

	@Override
	public PageSet<MOut> getOldOutQryPageSet(PageParam pageParam, String filterSort, String userDeptIds, String userTeam, String teamNoQry, String typeQry, String stepQry, String outTeamQry, String date1Qry, String date2Qry, String storeQry, String matQry){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MOut> list = mOutMapper.getOldOutQryList(userDeptIds, userTeam, teamNoQry, typeQry, stepQry, outTeamQry, date1Qry, date2Qry, storeQry, matQry);
		PageInfo<MOut> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public List<MOut> getOldOutQryList(String userDeptIds, String userTeam, String teamNoQry, String typeQry, String stepQry, String outTeamQry, String date1Qry, String date2Qry, String storeQry, String matQry){
		return mOutMapper.getOldOutQryList(userDeptIds, userTeam, teamNoQry, typeQry, stepQry, outTeamQry, date1Qry, date2Qry, storeQry, matQry);
	}
}
