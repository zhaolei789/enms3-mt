package cn.ewsd.material.service.impl;

import cn.ewsd.base.utils.Snow;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.material.mapper.MHandInMapper;
import cn.ewsd.material.mapper.MMaterialMapper;
import cn.ewsd.material.mapper.MStockMapper;
import cn.ewsd.material.model.MHandIn;
import cn.ewsd.material.model.MMaterial;
import cn.ewsd.material.model.MStock;
import cn.ewsd.material.service.MHandInService;
import cn.ewsd.mdata.model.Organization;
import cn.ewsd.repository.mapper.MStoreMapper;
import cn.ewsd.repository.model.MStore;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Service("mHandInServiceImpl")
public class MHandInServiceImpl extends MaterialBaseServiceImpl<MHandIn, String> implements MHandInService {
	@Autowired
	private MHandInMapper mHandInMapper;
	@Autowired
	private MMaterialMapper mMaterialMapper;
	@Autowired
	private MStoreMapper mStoreMapper;
	@Autowired
	private MStockMapper mStockMapper;

    @Override
    public PageSet<MHandIn> getPageSet(PageParam pageParam, String filterSort, String date1Qry, String date2Qry, String teamNoQry, String matCodeQry, String matNameQry) {
		System.out.println(date1Qry+"---"+date2Qry+"---"+teamNoQry+"---"+matCodeQry+"---"+matNameQry);
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<MHandIn> list = mHandInMapper.getOldMatRegList(date1Qry, date2Qry, teamNoQry, matCodeQry, matNameQry);
        PageInfo<MHandIn> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
	public List<Organization> getTeamSet(String date1Qry, String date2Qry, String teamNoQry, String matCodeQry, String matNameQry){
    	return mHandInMapper.getTeamSet(date1Qry, date2Qry, teamNoQry, matCodeQry, matNameQry);
	}

	@Override
	public List<MHandIn> getList(String date1Qry, String date2Qry, String teamNoQry, String matCodeQry, String matNameQry){
    	return mHandInMapper.getOldMatRegList(date1Qry, date2Qry, teamNoQry, matCodeQry, matNameQry);
	}

	@Override
	public List<MMaterial> getMatList(String q, String userTeam){
    	return mHandInMapper.getMatList(q, userTeam);
	}

	@Override
	@Transactional
	public void saveOldMatReg(HttpServletRequest request, UserInfo userInfo) throws Exception{
		try{
			String matNo = request.getParameter("matNo");
			String teamNo = request.getParameter("teamNo");
			String storeNo = "";
			double matAmount = 0;
			try {
				matAmount = Double.parseDouble("matAmount");
			}catch (Exception e){
				throw new XException(XException.ERR_DEFAULT, "应交数量必须是数字！");
			}
			String occDate = XDate.dateTo8(request.getParameter("occDate"));
			String linkNo = "0";
			String remark = request.getParameter("remark");
			String modiEmp = userInfo.getUuid();
			String modiDate = XDate.getDate();
			String checkStep = "7202F";

			MMaterial material = mMaterialMapper.getMatByNo(matNo);
			BigDecimal oldRate = new BigDecimal("0");
			BigDecimal price = material.getMatPrice();

			String billNo = Snow.getUUID()+"";
			MHandIn mHandIn = new MHandIn();
			mHandIn.setBillNo(billNo);
			mHandIn.setMatNo(matNo);
			mHandIn.setTeamNo(Integer.parseInt(teamNo));
			mHandIn.setStoreNo(storeNo);
			mHandIn.setMatAmount(new BigDecimal(matAmount+""));
			mHandIn.setOldRate(oldRate.divide(new BigDecimal("100")));
			mHandIn.setOccDate(occDate);
			mHandIn.setPlanGive("");
			mHandIn.setLinkNo(linkNo);
			mHandIn.setRemark(remark);
			mHandIn.setModiEmp(modiEmp);
			mHandIn.setModiDate(modiDate);
			mHandIn.setCheckStep(checkStep);
			mHandIn.setPauseMonth("交旧");
			mHandInMapper.insertHandIn(mHandIn);

			List<MStore> storeList = mStoreMapper.getStoreList(null, null, userInfo.getOrgId(), null, null, null, "", "");
			String inStore = "";
			if(storeList.size() > 0){
				inStore = storeList.get(0).getStoreNo();
			}
			String backBill = Snow.getUUID()+"";
			MHandIn mHandIn1 = new MHandIn();
			mHandIn1.setBillNo(backBill);
			mHandIn1.setMatNo(matNo);
			mHandIn1.setTeamNo(Integer.parseInt(teamNo));
			mHandIn1.setStoreNo(inStore);
			mHandIn1.setMatAmount(new BigDecimal(matAmount+""));
			mHandIn1.setOldRate(oldRate.divide(new BigDecimal("100")));
			mHandIn1.setOccDate(occDate);
			mHandIn1.setPlanGive("");
			mHandIn1.setLinkNo(billNo);
			mHandIn1.setRemark("手动添加待交记录");
			mHandIn1.setModiEmp(modiEmp);
			mHandIn1.setModiDate(modiDate);
			mHandIn1.setCheckStep(checkStep);
			mHandIn1.setPauseMonth("");
			mHandInMapper.insertHandIn(mHandIn);

			MStock mStock = new MStock();
			mStock.setStoreNo(inStore);
			mStock.setMatNo(matNo);
			mStock.setInAmount(new BigDecimal(matAmount+""));
			mStock.setOutAmount(new BigDecimal("0"));
			mStock.setStockAmount(new BigDecimal(matAmount+""));
			mStock.setPackAmount(new BigDecimal("0"));
			mStock.setLockAmount(new BigDecimal("0"));
			mStock.setBulkAmount(new BigDecimal(matAmount+""));
			int c = mStockMapper.updateStock(mStock);
			if(c < 1){
				mStock.setSiteCode("");
				mStockMapper.insertStock(mStock);
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	@Transactional
	public void delOldMatReg(HttpServletRequest request, UserInfo userInfo) throws Exception{
		try{
			String billNo = request.getParameter("billNo");

			if("".equals(billNo)){
				throw new XException(XException.ERR_DEFAULT, "单据编号不正确!");
			}

			MHandIn mHandIn = mHandInMapper.getMHandInByBillNo(billNo);
			BigDecimal matAmount = mHandIn.getMatAmount().multiply(new BigDecimal("-1"));

			mHandInMapper.delMHandInByLinkNo(billNo);
			mHandInMapper.delMHandInByBillNo(billNo);

			MStock mStock = new MStock();
			mStock.setStoreNo(mHandIn.getStoreNo());
			mStock.setMatNo(mHandIn.getMatNo());
			mStock.setInAmount(matAmount);
			mStock.setOutAmount(new BigDecimal("0"));
			mStock.setStockAmount(matAmount);
			mStock.setPackAmount(new BigDecimal("0"));
			mStock.setLockAmount(new BigDecimal("0"));
			mStock.setBulkAmount(matAmount);
			int c = mStockMapper.updateStock(mStock);
			if(c < 1){
				mStock.setSiteCode("");
				mStockMapper.insertStock(mStock);
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	@Transactional
	public void updateOldMatReg(HttpServletRequest request, UserInfo userInfo) throws Exception{
		try{
			String storeNoQry = request.getParameter("storeNoQry");
			if("".equals(storeNoQry)){
				throw new XException(XException.ERR_DEFAULT, "回收仓库不能为空！");
			}

			String[] billNos = request.getParameterValues("billNo");
			for(int i=0; i<billNos.length; i++){
				String billNo = billNos[i];
				double matAmount = 0;
				try{
					matAmount = Double.parseDouble(request.getParameter("amount_"+billNo));
				}catch (Exception e){
					throw new XException(XException.ERR_DEFAULT, "本次交旧数必须是数字！");
				}
				String remark = request.getParameter("remark_"+billNo);

				MHandIn h = mHandInMapper.getMHandInByBillNo(billNo);

				MHandIn mHandIn = new MHandIn();
				mHandIn.setBillNo(Snow.getUUID()+"");
				mHandIn.setMatNo(h.getMatNo());
				mHandIn.setTeamNo(h.getTeamNo());
				mHandIn.setStoreNo(storeNoQry);
				mHandIn.setMatAmount(new BigDecimal(matAmount+""));
				mHandIn.setOldRate(h.getOldRate());
				mHandIn.setOccDate(XDate.getDate());
				mHandIn.setPlanGive("");
				mHandIn.setLinkNo(billNo);
				mHandIn.setRemark(remark);
				mHandIn.setModiEmp(userInfo.getUuid());
				mHandIn.setModiDate(XDate.getDate());
				mHandIn.setCheckStep("7202F");
				mHandIn.setPauseMonth("");
				mHandInMapper.insertHandIn(mHandIn);

				MStock mStock = new MStock();
				mStock.setStoreNo(mHandIn.getStoreNo());
				mStock.setMatNo(mHandIn.getMatNo());
				mStock.setInAmount(new BigDecimal(matAmount+""));
				mStock.setOutAmount(new BigDecimal("0"));
				mStock.setStockAmount(new BigDecimal(matAmount+""));
				mStock.setPackAmount(new BigDecimal("0"));
				mStock.setLockAmount(new BigDecimal("0"));
				mStock.setBulkAmount(new BigDecimal(matAmount+""));
				int c = mStockMapper.updateStock(mStock);
				if(c < 1){
					mStock.setSiteCode("");
					mStockMapper.insertStock(mStock);
				}
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	public PageSet<MHandIn> getOldMatRegDetailPageSet(PageParam pageParam, String filterSort, String monthQry, String userDeptIds, String teamNoQry, String matCodeQry, String matNameQry) {
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MHandIn> list = mHandInMapper.getOldMatRegDetailList(monthQry, userDeptIds, teamNoQry, matNameQry, matNameQry);
		PageInfo<MHandIn> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public PageSet<MHandIn> getRetDetailPageSet(PageParam pageParam, String filterSort, String flagQry, String userDeptIds, String date1Qry, String date2Qry, String teamNoQry, String typeQry, String matQry) {
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MHandIn> list = "".equals(flagQry) ? mHandInMapper.getRetDetailList1(userDeptIds, date1Qry, date2Qry, teamNoQry, typeQry, matQry) : mHandInMapper.getRetDetailList2(userDeptIds, date1Qry, date2Qry, teamNoQry, typeQry, matQry);
		PageInfo<MHandIn> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public List<MHandIn> getRetDetailList(String flagQry, String userDeptIds, String date1Qry, String date2Qry, String teamNoQry, String typeQry, String matQry){
    	return "".equals(flagQry) ? mHandInMapper.getRetDetailList1(userDeptIds, date1Qry, date2Qry, teamNoQry, typeQry, matQry) : mHandInMapper.getRetDetailList2(userDeptIds, date1Qry, date2Qry, teamNoQry, typeQry, matQry);
	}
}
