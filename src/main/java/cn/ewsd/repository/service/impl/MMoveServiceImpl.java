package cn.ewsd.repository.service.impl;

import cn.ewsd.base.utils.BaseUtils;
import cn.ewsd.base.utils.Snow;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
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
import cn.ewsd.repository.service.MMoveService;
import cn.ewsd.system.mapper.TCheckMapper;
import cn.ewsd.system.model.TCheck;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Service("mMoveServiceImpl")
public class MMoveServiceImpl extends RepositoryBaseServiceImpl<MMove, String> implements MMoveService {
	@Autowired
	private MMoveMapper mMoveMapper;
	@Autowired
	private MMaterialMapper mMaterialMapper;
	@Autowired
	private TCheckMapper tCheckMapper;
	@Autowired
	private MStockMapper mStockMapper;
	@Autowired
	private MInMapper mInMapper;
	@Autowired
	private  MStockService mStockService;

	@Override
	public PageSet<MMove> getMoveOrderList(PageParam pageParam, String monthQry){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MMove> list = mMoveMapper.getMoveOrderList(monthQry);
		PageInfo<MMove> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public int insertMove(String inStore, String outStore){
		MMove mMove = new MMove();
		mMove.setTransNo(Snow.getUUID()+"");
		mMove.setMoveStep("72100");
		mMove.setOutStore(outStore);
		mMove.setInStore(inStore);
		mMove.setCheckNo(Snow.getUUID()+"");
		mMove.setOccDate(XDate.getDate());
		mMove.setOccTime(XDate.getTime());

		return mMoveMapper.insertMove(mMove);
	}

	@Transactional
	@Override
	public int deleteMove(String transNo) throws Exception{
		MMove mMove = mMoveMapper.getMove(transNo);

		String moveStep = mMove.getMoveStep();
		if(!"72100".equals(moveStep) && !"72101".equals(moveStep)){
			throw new XException(XException.ERR_DEFAULT, "非草稿或拣货状态，不能删除！");
		}

		double pickAmount = mMoveMapper.getPickAmount(transNo);
		if(pickAmount > 0){
			throw new XException(XException.ERR_DEFAULT, "该移库指令中已有记录进行了拣货操作，不能删除！");
		}

		try {
			mMoveMapper.deleteMoveList(transNo);
			return mMoveMapper.deleteMove(transNo);
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	public PageSet<MMaterial> getMoveMatList(PageParam pageParam, String transNo, String outStore, String matCodeQry, String matNameQry){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MMaterial> list = mMoveMapper.getMoveMat(transNo, outStore, matCodeQry, matNameQry);
		PageInfo<MMaterial> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public MMove getMove(String transNo){
		return mMoveMapper.getMove(transNo);
	}

	@Override
	@Transactional
	public void saveMat(String transNo, String[] matNos) throws Exception{
		try {
			for(int i=0; i<matNos.length; i++){
				String matNo = matNos[i];
				MMaterial mMaterial = mMaterialMapper.getMatByNo(matNo);
				if(mMaterial==null){
					throw new XException(XException.ERR_DEFAULT, "物料不存在！");
				}
				BigDecimal matPrice = mMaterial.getMatPrice();
				String listNo = Snow.getUUID()+"";

				MMove listBean = new MMove();
				listBean.setListNo(listNo);
				listBean.setTransNo(transNo);
				listBean.setMatNo(matNo);
				listBean.setMatAmount(0.0);
				listBean.setPickAmount(0.0);
				listBean.setPickEmp("0");
				listBean.setGetAmount(0.0);
				listBean.setGetEmp("0");
				listBean.setGetDate("");
				listBean.setMatPrice(matPrice.doubleValue());
				mMoveMapper.saveList(listBean);
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	public PageSet<MMove> getMoveList(PageParam pageParam, String transNo){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MMove> list = mMoveMapper.getMoveList(transNo);
		PageInfo<MMove> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	@Transactional
	public void deleteMat(String transNo, String[] listNos) throws Exception{
		MMove mMove = mMoveMapper.getMove(transNo);
		if(mMove==null){
			throw new XException(XException.ERR_DEFAULT, "移库指令不存在！");
		}
		if(!"72100".equals(mMove.getMoveStep())){
			throw new XException(XException.ERR_DEFAULT, "非草稿，不能删除！");
		}

		try {
			for(int i=0; i<listNos.length; i++){
				String listNo = listNos[i];

				mMoveMapper.deleteList(listNo);
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	@Transactional
	public void saveAmount(String transNo, String[] listNos, HttpServletRequest request) throws Exception{
		MMove mMove = mMoveMapper.getMove(transNo);
		if(mMove==null){
			throw new XException(XException.ERR_DEFAULT, "移库指令不存在！");
		}
		if(!"72100".equals(mMove.getMoveStep())){
			throw new XException(XException.ERR_DEFAULT, "非草稿，不能保存！");
		}

		try {
			for(int i=0; i<listNos.length; i++){
				String listNo = listNos[i];
				String matAmount = request.getParameter("amount_"+listNo);
				try{
					double amount = Double.parseDouble(matAmount);
					if(amount<0){
						throw new Exception();
					}
				}catch (Exception e){
					throw new XException(XException.ERR_DEFAULT, "数量必须是大于0的数字！");
				}

				mMoveMapper.updateList(listNo, matAmount);
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	@Transactional
	public void submit(String transNo) throws Exception{
		MMove mMove = mMoveMapper.getMove(transNo);
		if(mMove==null){
			throw new XException(XException.ERR_DEFAULT, "移库指令不存在！");
		}
		if(!"72100".equals(mMove.getMoveStep())){
			throw new XException(XException.ERR_DEFAULT, "非草稿，不能提交！");
		}

		List<MMove> list = mMoveMapper.getMoveList(transNo);
		if(list.size()<1){
			throw new XException(XException.ERR_DEFAULT, "指令下必须有要移库的物资！");
		}
		for(int i=0; i<list.size(); i++){
			if(list.get(i).getMatAmount()<=0){
				throw new XException(XException.ERR_DEFAULT, "要移库的物资数量必须大于0");
			}
		}

		try {
			mMoveMapper.updateMove("72104", LoginInfo.getUuid(), XDate.getDate(), XDate.getTime(), transNo);

			TCheck tCheck = new TCheck();
			tCheck.setCheckNo(mMove.getCheckNo());
			tCheck.setStepKey("7210");
			tCheck.setStepCode("72100");
			tCheck.setCheckType("sys.checkType.0");
			tCheck.setDirect("sys.checkDirect.0");
			tCheck.setIdea("");
			tCheck.setUserId(LoginInfo.getUuid());
			tCheck.setUserName(LoginInfo.getUserName());
			tCheck.setTeamNo(Integer.parseInt(LoginInfo.getOrgId()));
			tCheck.setOccDate(XDate.getDate());
			tCheck.setOccTime(XDate.getTime());
			tCheck.setLogInfo("");
			tCheck.setBefAmount(new BigDecimal("0"));
			tCheck.setBefPrice(new BigDecimal("0"));
			tCheck.setAftAmount(new BigDecimal("0"));
			tCheck.setAftPrice(new BigDecimal("0"));
			tCheck.setEmpId(0L);
			tCheckMapper.executeSave(tCheck);

			for(int i=0; i<list.size(); i++){
				double amount = list.get(i).getMatAmount();
				MStock mStock = new MStock();
				mStock.setStoreNo(mMove.getOutStore());
				mStock.setMatNo(list.get(i).getMatNo());
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

				mStockService.checkStockAmount(list.get(i).getMatNo(), mMove.getOutStore());
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	public PageSet<MMove> getMoveCheckList(PageParam pageParam, String userId){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MMove> list = mMoveMapper.getMoveCheckList(userId);
		PageInfo<MMove> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public PageSet<MMove> getCheckList(PageParam pageParam, String transNo){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MMove> list = mMoveMapper.getCheckList(transNo);
		PageInfo<MMove> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	@Transactional
	public void dealMove(String transNo, String[] listNos) throws Exception{
		MMove mMove = mMoveMapper.getMove(transNo);
		if(mMove==null){
			throw new XException(XException.ERR_DEFAULT, "移库指令不存在！");
		}
		if(!"72104".equals(mMove.getMoveStep())){
			throw new XException(XException.ERR_DEFAULT, "指令非收货状态，不能收货！");
		}

		try {
			for(int i=0; i<listNos.length; i++){
				String listNo = listNos[i];
				MMove mMove1 = mMoveMapper.getMl(listNo);
				String matNo = mMove1.getMatNo();
				double amount = mMove1.getMatAmount();
				double matPrice = mMove1.getMatPrice();

				MStock mStock = new MStock();
				mStock.setStoreNo(mMove.getInStore());
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

				mStockService.checkStockAmount(matNo, mMove.getInStore());

				MIn mIn = new MIn();
				mIn.setBillNo(Snow.getUUID()+"");
				mIn.setInType("r.inBillType.8");
				mIn.setPlanSrc("");
				mIn.setPlanNo("");
				mIn.setTeamNo(Integer.parseInt(LoginInfo.getOrgId()));
				mIn.setOfferTeam(0);
				mIn.setStoreNo(mMove.getInStore());
				mIn.setReserveNo("");
				mIn.setMatNo(matNo);
				mIn.setApplyAmount(new BigDecimal(amount+""));
				mIn.setApplyDate(XDate.getDate());
				mIn.setApplyEmp(LoginInfo.getUuid());
				mIn.setInAmount(new BigDecimal(amount+""));
				mIn.setInDate(XDate.getDate());
				mIn.setInEmp(LoginInfo.getUuid());
				mIn.setBillAmount(new BigDecimal(amount+""));
				mIn.setSetPrice(new BigDecimal(matPrice+""));
				mIn.setBillDate(XDate.getDate());
				mIn.setBillEmp(LoginInfo.getUuid());
				mIn.setOfferNo("");
				mIn.setInStep("7201F");
				mIn.setCheckNo("0");
				mIn.setRemark("YK，IN");
				mIn.setDataSrc("m.dataSrc.U");
				mIn.setPrice1(new BigDecimal("0"));
				mIn.setPrice2(new BigDecimal("0"));
				mIn.setPrice3(new BigDecimal("0"));
				mIn.setLinkNo(listNo);
				mIn.setNormNo("");
				mIn.setReserve1("");
				mIn.setReserve2("");
				mIn.setReserve3("");
				mIn.setReserve4("");
				mIn.setErpBill("");
				mInMapper.saveIn(mIn);

				mMoveMapper.updateList1(amount+"", LoginInfo.getUuid(), XDate.getDate(), listNo);
			}

			if (mMoveMapper.getCnt(transNo) <= 0) {
				mMoveMapper.updateMove1(XDate.getDate(), XDate.getTime(), "72105", transNo);
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	@Transactional
	public void backMove(String transNo, String[] listNos) throws Exception{
		MMove mMove = mMoveMapper.getMove(transNo);
		if(mMove==null){
			throw new XException(XException.ERR_DEFAULT, "移库指令不存在！");
		}
		if(!"72104".equals(mMove.getMoveStep())){
			throw new XException(XException.ERR_DEFAULT, "指令非收货状态，不能退回！");
		}

		try {
			for(int i=0; i<listNos.length; i++){
				String listNo = listNos[i];
				MMove mMove1 = mMoveMapper.getMl(listNo);
				String matNo = mMove1.getMatNo();
				double amount = mMove1.getMatAmount();
				double matPrice = mMove1.getMatPrice();

				MStock mStock = new MStock();
				mStock.setStoreNo(mMove.getOutStore());
				mStock.setMatNo(matNo);
				mStock.setInAmount(new BigDecimal("0"));
				mStock.setOutAmount(new BigDecimal(amount+"").multiply(new BigDecimal("-1")));
				mStock.setStockAmount(new BigDecimal(amount+""));
				mStock.setPackAmount(new BigDecimal("0"));
				mStock.setLockAmount(new BigDecimal("0"));
				mStock.setBulkAmount(new BigDecimal(amount+""));
				int c = mStockMapper.updateStock(mStock);
				if(c<1){
					mStock.setSiteCode("");
					mStockMapper.insertStock(mStock);
				}

				mMoveMapper.deleteList(listNo);
			}

			if (mMoveMapper.getCnt(transNo) <= 0) {
				mMoveMapper.updateMove1(XDate.getDate(), XDate.getTime(), "72105", transNo);
			}

			if (mMoveMapper.getCnt1(transNo) <= 0) {
				mMoveMapper.deleteMove(transNo);
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}
}
