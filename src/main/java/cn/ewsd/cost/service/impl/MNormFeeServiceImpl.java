package cn.ewsd.cost.service.impl;

import cn.ewsd.base.utils.*;
import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.cost.mapper.*;
import cn.ewsd.cost.model.*;
import cn.ewsd.cost.service.MNormFeeService;
import cn.ewsd.cost.util.BigDecimalUtils;
import cn.ewsd.material.mapper.MAccountMapper;
import cn.ewsd.material.model.MAccount;
import cn.ewsd.material.model.MMaterial;
import cn.ewsd.mdata.model.Organization;
import cn.ewsd.repository.model.MOut;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("mNormFeeServiceImpl")
public class MNormFeeServiceImpl extends CostBaseServiceImpl<MNormFee, String> implements MNormFeeService {
	@Autowired
	private MNormFeeMapper mNormFeeMapper;
	@Autowired
	private MAssessMapper mAssessMapper;
	@Autowired
	private MAccountMapper mAccountMapper;
	@Autowired
	private MFeeItemMapper mFeeItemMapper;
	@Autowired
	private MWorkMapper mWorkMapper;
	@Autowired
	private MOutAssessMapper mOutAssessMapper;
	@Autowired
	private MAssessNormMapper mAssessNormMapper;

    @Override
	public List<MNormFee> getMatAssessList(String monthQry, String teamNoQry){
    	List<MNormFee> list = mNormFeeMapper.getMatAssessList(monthQry, teamNoQry);
    	List<MAssess> assList = mAssessMapper.getMatAssessAssList(monthQry, teamNoQry);
		DataCantainer<MAssess> assDc = new DataCantainer<>((ArrayList)assList);

    	for(int i=0; i<list.size(); i++){
    		MNormFee mNormFee = list.get(i);
    		String resultId = mNormFee.getResultId();

    		mNormFee.setAssPriceText(Data.normalToFinal(Data.trimDoubleNo0(mNormFee.getAssPrice().doubleValue(), 4)));
    		mNormFee.setNormAmountText(Data.normalToFinal(Data.trimDoubleNo0(mNormFee.getNormAmount().doubleValue(), 4)));
    		mNormFee.setNormBalaText(Data.normalToFinal(Data.trimDoubleNo0(mNormFee.getNormBala().doubleValue(), 2)));
    		mNormFee.setNormBalaAssText(Data.normalToFinal(Data.trimDoubleNo0(assDc.findDataCantainer("assess_id", resultId).getSum("norm_bala"), 2)));
    		mNormFee.setLastStockText(Data.trimDoubleNo0(assDc.findDataCantainer("assess_id", resultId).getSum("last_stock"), 2));
    		mNormFee.setInBalaText(Data.trimDoubleNo0(assDc.findDataCantainer("assess_id", resultId).getSum("in_bala"), 2));
    		mNormFee.setStockBalaText(Data.trimDoubleNo0(assDc.findDataCantainer("assess_id", resultId).getSum("stock_bala"), 2));
    		mNormFee.setAddBalaText(Data.trimDoubleNo0(assDc.findDataCantainer("assess_id", resultId).getSum("add_bala"), 2));
    		mNormFee.setOccBalaText(Data.normalToFinal(Data.trimDoubleNo0(assDc.findDataCantainer("assess_id", resultId).getSum("occ_bala"), 2)));
    		mNormFee.setDiffBalaText(Data.normalToFinal(Data.trimDoubleNo0(assDc.findDataCantainer("assess_id", resultId).getSum("diff_bala"), 2)));
    		mNormFee.setRemark(assDc.findDataCantainer("assess_id", resultId).getRow(0).getRemark());
    		mNormFee.setCnt(assDc.findDataCantainer("assess_id", resultId).getRow(0).getCnt());
		}

    	MNormFee mNormFee = new MNormFee();
    	mNormFee.setPrjName("合计");
		mNormFee.setAssPriceText("");
		mNormFee.setNormAmountText("");
		mNormFee.setNormBalaText("");
		mNormFee.setNormBalaAssText(Data.normalToFinal(Data.trimDoubleNo0(assDc.getSum("norm_bala"), 2)));
		mNormFee.setLastStockText("");
		mNormFee.setInBalaText("");
		mNormFee.setStockBalaText("");
		mNormFee.setAddBalaText("");
		mNormFee.setOccBalaText(Data.normalToFinal(Data.trimDoubleNo0(assDc.getSum("occ_bala"), 2)));
		mNormFee.setDiffBalaText(Data.normalToFinal(Data.trimDoubleNo0(assDc.getSum("diff_bala"), 2)));
		mNormFee.setRemark("");
		list.add(mNormFee);

    	return list;
	}

	@Override
	public HashMap<String, Object> getMatAssessFormData(String month, String teamQry){
    	String lastMonth = XDate.addMonth(month, -1);

		List<MAccount> accList = mAccountMapper.getAccountList(teamQry, month, lastMonth);
		DataCantainer<MAccount> accDc = new DataCantainer<>((ArrayList)accList);
		DataCantainer<MAccount> nowDc = accDc.findDataCantainer("occ_month", month);
		DataCantainer<MAccount> lastDc = accDc.findDataCantainer("occ_month", lastMonth);

		HashMap<String, Object> map = new HashMap<>();
		map.put("statusCode", 200);

		BigDecimal awardBala = nowDc.getRowCount() > 0 ? nowDc.getRow(0).getAwardBala() : new BigDecimal("0");
		awardBala = awardBala == null ? new BigDecimal("0") : awardBala;
		BigDecimal keepBala = nowDc.getRowCount() > 0 ? nowDc.getRow(0).getKeepBala() : new BigDecimal("0");
		keepBala = keepBala == null ? new BigDecimal("0") : keepBala;
		BigDecimal delayBala = nowDc.getRowCount() > 0 ? nowDc.getRow(0).getDelayBala() : new BigDecimal("0");
		delayBala = delayBala == null ? new BigDecimal("0") : delayBala;
		BigDecimal monthBala = nowDc.getRowCount() > 0 ? nowDc.getRow(0).getMonthBala() : new BigDecimal("0");
		monthBala = monthBala == null ? new BigDecimal("0") : monthBala;
		BigDecimal lastKeepBala = lastDc.getRowCount() > 0 ? lastDc.getRow(0).getKeepBala() : new BigDecimal("0");
		lastKeepBala = lastKeepBala == null ? new BigDecimal("0") : lastKeepBala;
		BigDecimal lastDelayBala = lastDc.getRowCount() > 0 ? lastDc.getRow(0).getDelayBala() : new BigDecimal("0");
		lastDelayBala = lastDelayBala == null ? new BigDecimal("0") : lastDelayBala;
		map.put("awardBala", Data.trimDoubleNo0(awardBala.doubleValue(), 2));
		map.put("keepBala", Data.trimDoubleNo0(keepBala.doubleValue(), 2));
		map.put("delayBala", Data.trimDoubleNo0(delayBala.doubleValue(), 2));
		map.put("monthBala", Data.trimDoubleNo0(monthBala.doubleValue(), 2));
		map.put("remark", nowDc.getRowCount() > 0 ? nowDc.getRow(0).getRemark() : "");
		map.put("lastKeepBala", Data.trimDoubleNo0(lastKeepBala.doubleValue(), 2));
		map.put("lastDelayBala", Data.trimDoubleNo0(lastDelayBala.doubleValue(), 2));

    	return map;
	}

	@Override
	@Transactional
	public void calcMatAssess(HttpServletRequest request, UserInfo userInfo) throws Exception{
    	try {
			String yearQry = request.getParameter("yearQry");
			String monQry = request.getParameter("monQry");
			String month = yearQry + monQry;
			String teamQry = request.getParameter("teamQry");

			if("".equals(teamQry) || teamQry==null){
				throw new XException(XException.ERR_DEFAULT, "没有考核的部门，请选择！");
			}

			String assTeam = userInfo.getOrgId();
			String occDate = XDate.getDate();
			String userId = userInfo.getUuid();
			String endDate = month + "25";
			String beginDate = XDate.addMonth(month, -1) + "26";
			String assType = "m.assType.4";

			List<MFeeItem> itemList = mFeeItemMapper.getMatAssessItemList(teamQry);
			for(int i=0; i<itemList.size(); i++){
				MFeeItem mFeeItem = itemList.get(i);
				String itemNo = mFeeItem.getItemNo();
				String itemName = mFeeItem.getItemName();
				String prjNo = mFeeItem.getPrjNo();
				String prjName = mFeeItem.getPrjName();
				String upItem = mFeeItem.getUpItem();
				BigDecimal normPrice = mFeeItem.getNormPrice();
				if(normPrice==null){
					throw new XException(XException.ERR_DEFAULT, itemName + ":未设置定额");
				}
				BigDecimal normAmount = mFeeItem.getNormAmount();
				BigDecimal assRatio = mFeeItem.getAssRatio();
				BigDecimal awardRatio = mFeeItem.getAwardRatio();
				BigDecimal punishRatio = mFeeItem.getPunishRatio();
				BigDecimal itemNorm = normPrice.multiply(normAmount);
				String itemType = mFeeItem.getItemType();
				String outType = mFeeItem.getOutType();
				String assCycle = mFeeItem.getAssCycle();
				String ifEnter = mFeeItem.getIfEnter();

				BigDecimal totalPlan = new BigDecimal("0");
				BigDecimal totalOcc = new BigDecimal("0");
				BigDecimal totalFact = new BigDecimal("0");
				BigDecimal awardBala = new BigDecimal("0");
				List<MWork> outList = null;
				List<MOutAssess> occList = null;
				if("m.assCycle.1".equals(assCycle)){
					outList = mWorkMapper.getMatAssessOutList(month, month, teamQry);
					occList = mOutAssessMapper.getMatAssessOccList(month, month, teamQry);
				}else if("m.assCycle.2".equals(assCycle)){
					int mon = Integer.parseInt(monQry);
					if (!(3==mon||6==mon||9==mon||12==mon)) {
						continue;
					}
					String beginMonth = XDate.addMonth(month, -2);
					outList = mWorkMapper.getMatAssessOutList(beginMonth, month, teamQry);
					occList = mOutAssessMapper.getMatAssessOccList(beginMonth, month, teamQry);
				}else if("m.assCycle.3".equals(assCycle)){
					int mon = Integer.parseInt(monQry);
					if (!(6==mon||12==mon)) {
						continue;
					}
					String beginMonth = XDate.addMonth(month, -5);
					outList = mWorkMapper.getMatAssessOutList(beginMonth, month, teamQry);
					occList = mOutAssessMapper.getMatAssessOccList(beginMonth, month, teamQry);
				}else if("m.assCycle.4".equals(assCycle)){
					int mon = Integer.parseInt(monQry);
					if (!(12==mon)) {
						continue;
					}
					String beginMonth = XDate.addMonth(month, -11);
					outList = mWorkMapper.getMatAssessOutList(beginMonth, month, teamQry);
					occList = mOutAssessMapper.getMatAssessOccList(beginMonth, month, teamQry);
				}else{
					throw new XException(XException.ERR_DEFAULT, "请确定指标["+ itemName +"]的考核周期！");
				}
				DataCantainer<MWork> outDc = new DataCantainer<>((ArrayList)outList);
				DataCantainer<MOutAssess> occDc = new DataCantainer<>((ArrayList)occList);

				mNormFeeMapper.deleteNormFee(month, itemNo, teamQry, prjNo, assType);
				mAssessMapper.deleteAssess(month, itemNo, teamQry, prjNo, assType);

				if("m.itemType.42".equals(itemType)){
					totalFact = "1".equals(ifEnter) ? totalFact : new BigDecimal(occDc.findDataCantainer("item_no", itemNo, "prj_no", prjNo).getSum("bala"));
					totalOcc = totalFact.multiply(assRatio);

					if("m.outType.1".equals(outType)){
						BigDecimal out = new BigDecimal(outDc.findDataCantainer("prj_no", prjNo).getSum("occ_rawout"));
						totalPlan = itemNorm.multiply(out);
					}
					if("m.outType.3".equals(outType)){
						BigDecimal out = new BigDecimal(outDc.findDataCantainer("prj_no", prjNo).getSum("occ_saleout"));
						totalPlan = itemNorm.multiply(out);
					}
					if("m.outType.2".equals(outType)){
						BigDecimal out = new BigDecimal(outDc.findDataCantainer("prj_no", prjNo).getSum("occ_dig"));
						totalPlan = itemNorm.multiply(out);
					}
					if("".equals(outType)){
						totalPlan = itemNorm.multiply(normPrice);
					}
					BigDecimal award = totalPlan.subtract(totalOcc);
					awardBala = award.compareTo(new BigDecimal("0")) == 1 ? award.multiply(awardRatio) : award.multiply(punishRatio);
				}

				if(totalPlan.compareTo(new BigDecimal("0")) != 1){
					continue;
				}

				String assessId = Snow.getUUID()+"";
				MNormFee mNormFee = new MNormFee();
				mNormFee.setAssessId(assessId);
				mNormFee.setOccMonth(month);
				mNormFee.setAssType(assType);
				mNormFee.setTeamNo(Integer.parseInt(teamQry));
				mNormFee.setItemNo(itemNo);
				mNormFee.setPrjNo(prjNo);
				mNormFee.setPrjName(prjName);
				mNormFee.setAssPrice(normPrice);
				mNormFee.setNormAmount(normAmount);
				mNormFee.setAssRate(assRatio);
				mNormFee.setNormBala(totalPlan);
				mNormFee.setUpItem(upItem);
				mNormFeeMapper.insertNormFee(mNormFee);

				if("1".equals(ifEnter)){
					if("".equals(upItem)){
						List<MAssess> assList = mAssessMapper.getAssessList(XDate.addMonth(month, -1), assType, teamQry, itemNo);
						BigDecimal lastStock = new BigDecimal("0");
						if(assList.size()>0){
							lastStock = assList.get(0).getStockBala();
						}
						BigDecimal stockBala = new BigDecimal("0");

						MAssess mAssess = new MAssess();
						mAssess.setAssessId(assessId);
						mAssess.setOccMonth(month);
						mAssess.setAssType(assType);
						mAssess.setTeamNo(Integer.parseInt(teamQry));
						mAssess.setItemNo(itemNo);
						mAssess.setPrjNo(prjNo);
						mAssess.setPrjName(prjName);
						mAssess.setNormBala(totalPlan);
						mAssess.setLastStock(lastStock);
						mAssess.setInBala(totalOcc);
						mAssess.setStockBala(stockBala);
						mAssess.setOccBala(BigDecimalUtils.sub(BigDecimalUtils.add(lastStock, totalOcc), stockBala));
						mAssess.setAddBala(new BigDecimal("0"));
						mAssess.setAwardRatio(awardRatio);
						mAssess.setPunishRatio(punishRatio);
						mAssess.setDiffBala(BigDecimalUtils.sub(totalPlan, BigDecimalUtils.sub(BigDecimalUtils.add(lastStock, totalOcc), stockBala)));
						mAssess.setDiffScale(new BigDecimal("1"));
						mAssess.setAwardBala(BigDecimalUtils.sub(totalPlan, BigDecimalUtils.sub(BigDecimalUtils.add(lastStock, totalOcc), stockBala)));
						mAssess.setAssTeam(Integer.parseInt(assTeam));
						mAssess.setModiDate(occDate);
						mAssess.setModiEmp(userId);
						mAssess.setRemark("");
						mAssess.setUpItem(upItem);
						mAssessMapper.insertAssess(mAssess);

						MNormFee mNormFee1 = new MNormFee();
						mNormFee1.setResultId(assessId);
						mNormFee1.setAssessId(assessId);
						mNormFeeMapper.updateNormFee(mNormFee1);
					}
				}else{
					MAssess mAssess = new MAssess();
					mAssess.setAssessId(assessId);
					mAssess.setOccMonth(month);
					mAssess.setAssType("m.assType.4");
					mAssess.setTeamNo(Integer.parseInt(teamQry));
					mAssess.setItemNo(itemNo);
					mAssess.setPrjNo(prjNo);
					mAssess.setPrjName(prjName);
					mAssess.setNormBala(totalPlan);
					mAssess.setLastStock(new BigDecimal("0"));
					mAssess.setInBala(new BigDecimal("0"));
					mAssess.setStockBala(new BigDecimal("0"));
					mAssess.setOccBala(totalOcc);
					mAssess.setAddBala(new BigDecimal("0"));
					mAssess.setAwardRatio(awardRatio);
					mAssess.setPunishRatio(punishRatio);
					mAssess.setDiffBala(awardBala);
					mAssess.setDiffScale(new BigDecimal("1"));
					mAssess.setAwardBala(awardBala);
					mAssess.setAssTeam(Integer.parseInt(assTeam));
					mAssess.setModiDate(occDate);
					mAssess.setModiEmp(userId);
					mAssess.setRemark("");
					mAssess.setUpItem(upItem);
					mAssessMapper.insertAssess(mAssess);

					MNormFee mNormFee1 = new MNormFee();
					mNormFee1.setResultId(assessId);
					mNormFee1.setAssessId(assessId);
					mNormFeeMapper.updateNormFee(mNormFee1);
				}
			}

			List<MNormFee> nnList = mNormFeeMapper.getMatAssessMulti(month, assType, teamQry);
			for(int i=0; i<nnList.size(); i++){
				MNormFee mNormFee = nnList.get(i);
				String upItem = mNormFee.getUpItem();
				BigDecimal sumNorm = mNormFee.getNormBala();

				mAssessMapper.deleteAssess(month, upItem, teamQry, null, assType);
				List<MOutAssess> occList = mOutAssessMapper.getMatAssessOccList(month, month, teamQry);
				DataCantainer<MOutAssess> occDc = new DataCantainer<>((ArrayList)occList);
				BigDecimal totalOcc = new BigDecimal(occDc.findDataCantainer("up_item", upItem).getSum("bala"));

				List<MAssess> assList = mAssessMapper.getAssessList(XDate.addMonth(month, -1), assType, teamQry, upItem);
				BigDecimal lastStock = new BigDecimal("0");
				if(assList.size()>0){
					lastStock = assList.get(0).getStockBala();
				}
				BigDecimal stockBala = new BigDecimal("0");

				String resultId = Snow.getUUID()+"";
				MAssess mAssess = new MAssess();
				mAssess.setAssessId(resultId);
				mAssess.setOccMonth(month);
				mAssess.setAssType(assType);
				mAssess.setTeamNo(Integer.parseInt(teamQry));
				mAssess.setItemNo(upItem);
				mAssess.setPrjNo("0");
				mAssess.setPrjName("");
				mAssess.setNormBala(sumNorm);
				mAssess.setLastStock(lastStock);
				mAssess.setInBala(totalOcc);
				mAssess.setStockBala(stockBala);
				mAssess.setOccBala(BigDecimalUtils.sub(BigDecimalUtils.add(lastStock, totalOcc), stockBala));
				mAssess.setAddBala(new BigDecimal("0"));
				mAssess.setAwardRatio(new BigDecimal("1"));
				mAssess.setPunishRatio(new BigDecimal("1"));
				mAssess.setDiffBala(BigDecimalUtils.sub(sumNorm, BigDecimalUtils.sub(BigDecimalUtils.add(lastStock, totalOcc), stockBala)));
				mAssess.setDiffScale(new BigDecimal("1"));
				mAssess.setAwardBala(BigDecimalUtils.sub(sumNorm, BigDecimalUtils.sub(BigDecimalUtils.add(lastStock, totalOcc), stockBala)));
				mAssess.setAssTeam(Integer.parseInt(assTeam));
				mAssess.setModiDate(occDate);
				mAssess.setModiEmp(userId);
				mAssess.setRemark("");
				mAssess.setUpItem(upItem);
				mAssessMapper.insertAssess(mAssess);

				MNormFee mNormFee1 = new MNormFee();
				mNormFee1.setResultId(resultId);
				mNormFee1.setOccMonth(month);
				mNormFee1.setAssType(assType);
				mNormFee1.setTeamNo(Integer.parseInt(teamQry));
				mNormFee1.setUpItem(upItem);
				mNormFeeMapper.updateNormFee(mNormFee1);
			}

			BigDecimal awardBala = new BigDecimal(mAssessMapper.getAwardBala(month, teamQry));

			MAccount mAccount = new MAccount();
			mAccount.setAwardBala(awardBala);
			mAccount.setOccMonth(month);
			mAccount.setTeamNo(teamQry);
			int cnt = mAccountMapper.updateAccount(mAccount);
			if(cnt < 1){
				mAccount.setRemark("");
				mAccountMapper.insertAccount(mAccount);
			}

		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	@Transactional
	public void saveMatAssess(HttpServletRequest request, UserInfo userInfo) throws Exception{
    	try {
			String yearQry = request.getParameter("yearQry");
			String monQry = request.getParameter("monQry");
			String month = yearQry + monQry;
			String teamQry = request.getParameter("teamQry");
			double awardBala = 0;
			double keepBala = 0;
			double delayBala = 0;
			double monthBala = 0;
			try {
				String aBala = request.getParameter("awardBala");
				aBala = "".equals(aBala) ? "0" : aBala;
				awardBala = Double.parseDouble(aBala);
			}catch (Exception e){
				throw new XException(XException.ERR_DEFAULT, "考核奖罚金额必须是数字！");
			}
			try {
				String kBala = request.getParameter("keepBala");
				kBala = "".equals(kBala) ? "0" : kBala;
				keepBala = Double.parseDouble(kBala);
			}catch (Exception e){
				throw new XException(XException.ERR_DEFAULT, "当月留存金额必须是数字！");
			}
			try {
				String dBala = request.getParameter("delayBala");
				dBala = "".equals(dBala) ? "0" : dBala;
				delayBala = Double.parseDouble(dBala);
			}catch (Exception e){
				throw new XException(XException.ERR_DEFAULT, "当月延期金额必须是数字！");
			}
			try {
				String mBala = request.getParameter("monthBala");
				mBala = "".equals(mBala) ? "0" : mBala;
				monthBala = Double.parseDouble(mBala);
			}catch (Exception e){
				throw new XException(XException.ERR_DEFAULT, "实际奖罚金额必须是数字！");
			}
			String assRemark = request.getParameter("remark");

			String[] resultIds = request.getParameterValues("key");
			for(int i=0; resultIds!=null && i<resultIds.length; i++){
				String resultId = resultIds[i];
				double lastStock = 0;
				double inBala = 0;
				double stockBala = 0;
				double addBala = 0;
				try {
					String mBala = request.getParameter("last_"+resultId);
					mBala = "".equals(mBala) || mBala==null ? "0" : mBala;
					lastStock = Double.parseDouble(mBala);
				}catch (Exception e){
					throw new XException(XException.ERR_DEFAULT, "上月结存必须是数字！");
				}
				try {
					String mBala = request.getParameter("in_"+resultId);
					mBala = "".equals(mBala) || mBala==null ? "0" : mBala;
					inBala = Double.parseDouble(mBala);
				}catch (Exception e){
					throw new XException(XException.ERR_DEFAULT, "本月领用必须是数字！");
				}
				try {
					String mBala = request.getParameter("stock_"+resultId);
					mBala = "".equals(mBala) || mBala==null ? "0" : mBala;
					stockBala = Double.parseDouble(mBala);
				}catch (Exception e){
					throw new XException(XException.ERR_DEFAULT, "月末结存必须是数字！");
				}
				try {
					String mBala = request.getParameter("add_"+resultId);
					mBala = "".equals(mBala) || mBala==null ? "0" : mBala;
					addBala = Double.parseDouble(mBala);
				}catch (Exception e){
					throw new XException(XException.ERR_DEFAULT, "其他使用必须是数字！");
				}
				double occBala = lastStock + inBala - stockBala - addBala;
				String remark = request.getParameter("remark_"+resultId);
				if(remark==null){
					remark = "";
				}

				MAssess mAssess = mAssessMapper.getAssess(resultId);
				String ifEnter = mAssess==null ? "" : mAssess.getIfEnter();
				if("".equals(ifEnter) || "1".equals(ifEnter) || ifEnter==null){
					MAssess mAssess1 = new MAssess();
					mAssess1.setLastStock(new BigDecimal(lastStock));
					mAssess1.setInBala(new BigDecimal(inBala));
					mAssess1.setStockBala(new BigDecimal(stockBala));
					mAssess1.setAddBala(new BigDecimal(addBala));
					mAssess1.setOccBala(new BigDecimal(occBala));
					mAssess1.setRemark(remark);
					mAssess1.setAssessId(resultId);
					mAssessMapper.updateAssess(mAssess1);
				}else{
					MAssess mAssess1 = new MAssess();
					mAssess1.setRemark(remark);
					mAssess1.setAssessId(resultId);
					mAssessMapper.updateAssess(mAssess1);
				}
			}

			double awardFact = mAssessMapper.getAwardBala(month, teamQry);
			MAccount mAccount = new MAccount();
			mAccount.setAwardBala(new BigDecimal(awardFact));
			mAccount.setKeepBala(new BigDecimal(keepBala));
			mAccount.setDelayBala(new BigDecimal(delayBala));
			mAccount.setMonthBala(new BigDecimal(monthBala));
			mAccount.setRemark(assRemark);
			mAccount.setOccMonth(month);
			mAccount.setTeamNo(teamQry);
			mAccountMapper.updateAccount(mAccount);
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	@Transactional
	public void deleteMatAssess(HttpServletRequest request, UserInfo userInfo) throws Exception{
		try {
			String yearQry = request.getParameter("yearQry");
			String monQry = request.getParameter("monQry");
			String month = yearQry + monQry;
			String teamQry = request.getParameter("teamQry");

			mAssessMapper.deleteAssess(month, null, teamQry, null, null);
			mNormFeeMapper.deleteNormFee(month, null, teamQry, null, null);
			mAccountMapper.deleteAccount(month, teamQry);
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	public List<MNormFee> getMatAssTyList(String occMonth, String assType, String ifEnter){
		List<MNormFee> retList = new ArrayList<>();

		try{
			List<MNormFee> list = mNormFeeMapper.getMatAssTyList(occMonth, assType, ifEnter);
			List<MAccount> resultList = mAccountMapper.getMatAssTyResultList(occMonth);
			DataCantainer<MAccount> resultDc = new DataCantainer<>((ArrayList)resultList);
			List<MWork> workList = mWorkMapper.getMatAssTyWorkList(occMonth);
			DataCantainer<MWork> workDc = new DataCantainer<>((ArrayList)workList);

			DataCantainer<MNormFee> dc = new DataCantainer<>((ArrayList)list);
			ArrayList<DataCantainer<MNormFee>> nList = dc.getGroup("team_no");
			for(int i=0; i<nList.size(); i++){
				DataCantainer<MNormFee> ndc = nList.get(i);
				String teamNo = ndc.getRow(0).getTeamNo()+"";
				for(int j=0; j<ndc.getRowCount(); j++){
					MNormFee mNormFee = ndc.getRow(j);
					String outType = mNormFee.getOutType();
					String prjNo = mNormFee.getPrjNo();
					double workValue = "m.outType.1".equals(outType) ? workDc.findDataCantainer("team_no", teamNo, "prj_no", prjNo).getSum("occ_rawout") : ("m.outType.3".equals(outType) ? workDc.findDataCantainer("team_no", teamNo, "prj_no", prjNo).getSum("occ_saleout") : workDc.findDataCantainer("team_no", teamNo, "prj_no", prjNo).getSum("occ_dig"));
					double normBala = mNormFee.getNormBala().doubleValue();
					double occBala = mNormFee.getOccBala().doubleValue();
					double diffBala = mNormFee.getDiffBala().doubleValue();
					mNormFee.setOrderNo(j==0 ? i+1 : 0);
					mNormFee.setRowSpan(j==0 ? ndc.getRowCount() : 0);
					mNormFee.setNormAmountText(Data.normalToFinal(Data.trimDoubleNo0(workValue, 2)));
					mNormFee.setAssPriceText(Data.trimDoubleNo0(mNormFee.getAssPrice().doubleValue(), 4));
					mNormFee.setNormBalaText(Data.normalToFinal(Data.trimDoubleNo0(normBala, 2)));
					mNormFee.setOccBalaText(Data.normalToFinal(Data.trimDoubleNo0(occBala, 2)));
					mNormFee.setDiffBalaText(Data.normalToFinal(Data.trimDoubleNo0(diffBala, 2)));
					mNormFee.setNormBalaAssText((normBala > 0 ? Data.trimDoubleNo0(diffBala*100/normBala, 2) : "") + "%");
					if(j==0){
						DataCantainer<MAccount> adc = resultDc.findDataCantainer("team_no", teamNo);
						mNormFee.setLastStockText(Data.normalToFinal(Data.trimDoubleNo0(adc.getSum("keep_bala"), 2)));
						mNormFee.setInBalaText(Data.normalToFinal(Data.trimDoubleNo0(adc.getSum("delay_bala"), 2)));
						mNormFee.setStockBalaText(Data.normalToFinal(Data.trimDoubleNo0(adc.getSum("month_bala"), 2)));
						mNormFee.setAddBalaText(adc.getRowCount()>0 ? adc.getRow(0).getRemark() : "");
					}
					retList.add(mNormFee);
				}

				double normBala = ndc.findDataCantainer("team_no", teamNo).getSum("norm_bala");
				double occBala = ndc.findDataCantainer("team_no", teamNo).getSum("occ_bala");
				double diffBala = ndc.findDataCantainer("team_no", teamNo).getSum("diff_bala");
				MNormFee mNormFee = new MNormFee();
				mNormFee.setOrderNo(0);
				mNormFee.setRowSpan(0);
				mNormFee.setTeamName("小计");
				mNormFee.setNormBalaText(Data.normalToFinal(Data.trimDoubleNo0(normBala, 2)));
				mNormFee.setOccBalaText(Data.normalToFinal(Data.trimDoubleNo0(occBala, 2)));
				mNormFee.setDiffBalaText(Data.normalToFinal(Data.trimDoubleNo0(diffBala, 2)));
				mNormFee.setNormBalaAssText((normBala > 0 ? Data.trimDoubleNo0(diffBala*100/normBala, 2) : "") + "%");
				retList.add(mNormFee);
			}

			double normBala = dc.getSum("norm_bala");
			double occBala = dc.getSum("occ_bala");
			double diffBala = dc.getSum("diff_bala");
			MNormFee mNormFee = new MNormFee();
			mNormFee.setOrderNo(0);
			mNormFee.setRowSpan(0);
			mNormFee.setTeamName("合计");
			mNormFee.setNormBalaText(Data.normalToFinal(Data.trimDoubleNo0(normBala, 2)));
			mNormFee.setOccBalaText(Data.normalToFinal(Data.trimDoubleNo0(occBala, 2)));
			mNormFee.setDiffBalaText(Data.normalToFinal(Data.trimDoubleNo0(diffBala, 2)));
			mNormFee.setNormBalaAssText((normBala > 0 ? Data.trimDoubleNo0(diffBala*100/normBala, 2) : "") + "%");
			retList.add(mNormFee);
		}catch (Exception e){
			e.printStackTrace();
		}

		return retList;
	}

	@Override
	public List<MNormFee> getMatAssZhList(String occMonth, String assType){
		List<MNormFee> list = mNormFeeMapper.getMatAssZhList(occMonth, assType);
		String prjs = "";
		String results = "";
		for(int i=0; i<list.size(); i++){
			MNormFee mNormFee = list.get(i);
			prjs += (i==0 ? "" : ",") + mNormFee.getPrjNo();
			results += (i==0 ? "" : ",") + mNormFee.getResultId();
		}
		List<MWork> workList = mWorkMapper.getMatAssZhWorkList(occMonth, prjs);
		DataCantainer<MWork> workDc = new DataCantainer<>((ArrayList)workList);
		List<MAssess> assList = mAssessMapper.getMatAssZhAssList(results);
		DataCantainer<MAssess> assDc = new DataCantainer<>((ArrayList)assList);

		DataCantainer<MNormFee> dc = new DataCantainer<>((ArrayList)list);
		ArrayList<DataCantainer<MNormFee>> nList = dc.getGroup("team_no");
		for(int i=0; i<nList.size(); i++){
			DataCantainer<MNormFee> ndc = nList.get(i);
			for(int j=0; j<ndc.getRowCount(); j++){
				MNormFee mNormFee = ndc.getRow(j);
				String resultId = mNormFee.getResultId();
				mNormFee.setRowSpan(j==0 ? ndc.getRowCount() : 0);
				mNormFee.setOrderNo(j==0 ? i+1 : 0);
				mNormFee.setOccDigText(Data.normalToFinal(Data.trimDoubleNo0(workDc.findDataCantainer("prj_no", mNormFee.getPrjNo()).getSum("occ_dig"), 2)));
				mNormFee.setAssPriceText(Data.trimDoubleNo0(mNormFee.getAssPrice().doubleValue(), 4));
				mNormFee.setNormBalaText(Data.normalToFinal(Data.trimDoubleNo0(mNormFee.getNormBala().doubleValue(), 2)));
				if(mNormFee.getFrequ()==1){
					mNormFee.setCnt(assDc.findDataCantainer("assess_id", resultId).getRow(0).getCnt());
					mNormFee.setAddBalaText(Data.normalToFinal(Data.trimDoubleNo0(assDc.findDataCantainer("assess_id", resultId).getSum("norm_bala"), 2)));
					mNormFee.setLastStockText(Data.normalToFinal(Data.trimDoubleNo0(assDc.findDataCantainer("assess_id", resultId).getSum("last_stock"), 2)));
					mNormFee.setInBalaText(Data.normalToFinal(Data.trimDoubleNo0(assDc.findDataCantainer("assess_id", resultId).getSum("in_bala"), 2)));
					mNormFee.setOccBalaText(Data.normalToFinal(Data.trimDoubleNo0(assDc.findDataCantainer("assess_id", resultId).getSum("occ_bala"), 2)));
					mNormFee.setStockBalaText(Data.normalToFinal(Data.trimDoubleNo0(assDc.findDataCantainer("assess_id", resultId).getSum("stock_bala"), 2)));
					mNormFee.setDiffBalaText(Data.normalToFinal(Data.trimDoubleNo0(assDc.findDataCantainer("assess_id", resultId).getSum("diff_bala"), 2)));
				}
			}
		}

		MNormFee mNormFee = new MNormFee();
		mNormFee.setTeamName("合计");
		mNormFee.setRowSpan(0);
		mNormFee.setOrderNo(0);
		mNormFee.setFrequ(0);
		mNormFee.setAddBalaText(Data.normalToFinal(Data.trimDoubleNo0(assDc.getSum("norm_bala"), 2)));
		mNormFee.setLastStockText(Data.normalToFinal(Data.trimDoubleNo0(assDc.getSum("last_stock"), 2)));
		mNormFee.setInBalaText(Data.normalToFinal(Data.trimDoubleNo0(assDc.getSum("in_bala"), 2)));
		mNormFee.setOccBalaText(Data.normalToFinal(Data.trimDoubleNo0(assDc.getSum("occ_bala"), 2)));
		mNormFee.setStockBalaText(Data.normalToFinal(Data.trimDoubleNo0(assDc.getSum("stock_bala"), 2)));
		mNormFee.setDiffBalaText(Data.normalToFinal(Data.trimDoubleNo0(assDc.getSum("diff_bala"), 2)));
		list.add(mNormFee);

		return list;
	}

	@Override
	public List<MNormFee> getMatAssDcList(String occMonth, String assType){
    	List<MNormFee> list = mNormFeeMapper.getMatAssDcList(occMonth, assType);
    	List<MNormFee> retList = new ArrayList<>();

		String teamNos = "";
		String results = "";
		for(int i=0; i<list.size(); i++){
			MNormFee mNormFee = list.get(i);
			teamNos += (i==0 ? "" : ",") + mNormFee.getTeamNo();
			results += (i==0 ? "" : ",") + mNormFee.getResultId();
		}

		List<MWork> workList = mWorkMapper.getMatAssDcWorkList(occMonth, teamNos);
		DataCantainer<MWork> workDc = new DataCantainer<>((ArrayList)workList);
		List<MAssessNorm> normList = mAssessNormMapper.getMatAssDcNormList(teamNos);
		DataCantainer<MAssessNorm> normDc = new DataCantainer<>((ArrayList)normList);
		List<MAssess> assList = mAssessMapper.getMatAssDcAssList(results);
		DataCantainer<MAssess> assDc = new DataCantainer<>((ArrayList)assList);

		DataCantainer<MNormFee> dc = new DataCantainer<>((ArrayList)list);
		ArrayList<DataCantainer<MNormFee>> nList = dc.getGroup("team_no");
		for(int i=0; i<nList.size(); i++){
			DataCantainer<MNormFee> ndc = nList.get(i);
			String teamNo = ndc.getRow(0).getTeamNo()+"";
			DataCantainer<MWork> nwdc = workDc.findDataCantainer("team_no", teamNo);
			DataCantainer<MAssessNorm> nndc = normDc.findDataCantainer("team_no", teamNo);
			for(int j=0; j<ndc.getRowCount(); j++){
				MNormFee mNormFee = ndc.getRow(j);
				String itemName = mNormFee.getItemName();
				String resultId = mNormFee.getResultId();
				mNormFee.setRowSpan(j==0 ? ndc.getRowCount()+1 : 0);
				mNormFee.setAssPriceText(Data.trimDoubleNo0(mNormFee.getAssPrice().doubleValue(), 4));
				mNormFee.setCnt(j==0 ? ndc.getRowCount() : 0);
				String occDigText = "";
				for(int k=0; k<nwdc.getRowCount(); k++){
					MWork mWork = nwdc.getRow(k);
					occDigText += (k==0 ? "" : "@|$|@") + mWork.getWorkPrj()+"："+Data.trimDouble(mWork.getOccDig().doubleValue(), 3);
				}
				mNormFee.setOccDigText(occDigText);
				mNormFee.setAddBalaText(Data.trimDoubleNo0(nndc.findDataCantainer("item_name", itemName, "prj_name", "地坪").getSum("norm_amount"), 4));
				mNormFee.setNormBalaAssText(Data.trimDoubleNo0(nndc.findDataCantainer("item_name", itemName, "prj_name", "喷浆").getSum("norm_amount"), 4));
				mNormFee.setNormBalaText(Data.normalToFinal(Data.trimDoubleNo0(assDc.findDataCantainer("assess_id", resultId).getSum("norm_bala"), 2)));
				mNormFee.setLastStockText(Data.normalToFinal(Data.trimDoubleNo0(assDc.findDataCantainer("assess_id", resultId).getSum("last_stock"), 2)));
				mNormFee.setInBalaText(Data.normalToFinal(Data.trimDoubleNo0(assDc.findDataCantainer("assess_id", resultId).getSum("in_bala"), 2)));
				mNormFee.setOccBalaText(Data.normalToFinal(Data.trimDoubleNo0(assDc.findDataCantainer("assess_id", resultId).getSum("occ_bala"), 2)));
				mNormFee.setStockBalaText(Data.normalToFinal(Data.trimDoubleNo0(assDc.findDataCantainer("assess_id", resultId).getSum("stock_bala"), 2)));
				mNormFee.setDiffBalaText(Data.normalToFinal(Data.trimDoubleNo0(assDc.findDataCantainer("assess_id", resultId).getSum("diff_bala"), 2)));
				retList.add(mNormFee);
			}
			MNormFee mNormFee = new MNormFee();
			mNormFee.setRowSpan(0);
			mNormFee.setCnt(0);
			mNormFee.setItemName("小计");
			mNormFee.setNormBalaText(Data.normalToFinal(Data.trimDoubleNo0(assDc.findDataCantainer("team_no", teamNo).getSum("norm_bala"), 2)));
			mNormFee.setLastStockText(Data.normalToFinal(Data.trimDoubleNo0(assDc.findDataCantainer("team_no", teamNo).getSum("last_stock"), 2)));
			mNormFee.setInBalaText(Data.normalToFinal(Data.trimDoubleNo0(assDc.findDataCantainer("team_no", teamNo).getSum("in_bala"), 2)));
			mNormFee.setOccBalaText(Data.normalToFinal(Data.trimDoubleNo0(assDc.findDataCantainer("team_no", teamNo).getSum("occ_bala"), 2)));
			mNormFee.setStockBalaText(Data.normalToFinal(Data.trimDoubleNo0(assDc.findDataCantainer("team_no", teamNo).getSum("stock_bala"), 2)));
			mNormFee.setDiffBalaText(Data.normalToFinal(Data.trimDoubleNo0(assDc.findDataCantainer("team_no", teamNo).getSum("diff_bala"), 2)));
			retList.add(mNormFee);
		}

		MNormFee mNormFee = new MNormFee();
		mNormFee.setRowSpan(0);
		mNormFee.setCnt(0);
		mNormFee.setTeamName("合计");
		mNormFee.setNormBalaText(Data.normalToFinal(Data.trimDoubleNo0(assDc.getSum("norm_bala"), 2)));
		mNormFee.setLastStockText(Data.normalToFinal(Data.trimDoubleNo0(assDc.getSum("last_stock"), 2)));
		mNormFee.setInBalaText(Data.normalToFinal(Data.trimDoubleNo0(assDc.getSum("in_bala"), 2)));
		mNormFee.setOccBalaText(Data.normalToFinal(Data.trimDoubleNo0(assDc.getSum("occ_bala"), 2)));
		mNormFee.setStockBalaText(Data.normalToFinal(Data.trimDoubleNo0(assDc.getSum("stock_bala"), 2)));
		mNormFee.setDiffBalaText(Data.normalToFinal(Data.trimDoubleNo0(assDc.getSum("diff_bala"), 2)));
		retList.add(mNormFee);

		return retList;
	}

	@Override
	public List<MNormFee> getMatAssItemList(String occMonth, String beginDate, String endDate){
    	List<MNormFee> list = mNormFeeMapper.getMatAssItemList(occMonth, beginDate, endDate);
    	List<MNormFee> retList = new ArrayList<>();

    	DataCantainer<MNormFee> dc = new DataCantainer<>((ArrayList)list);
    	ArrayList<DataCantainer<MNormFee>> nList = dc.getGroup("team_no");
    	for(int i=0; i<nList.size(); i++){
    		DataCantainer<MNormFee> ndc = nList.get(i);
    		String teamNo = ndc.getRow(0).getTeamNo()+"";
    		for(int j=0; j<ndc.getRowCount(); j++){
    			MNormFee mNormFee = ndc.getRow(j);
    			double budBala = mNormFee.getNormBala().doubleValue();
    			double occBala = mNormFee.getNormAmount().doubleValue();
    			mNormFee.setRowSpan(j==0 ? ndc.getRowCount()+1 : 0);
				mNormFee.setNormBalaText(Data.normalToFinal(Data.trimDoubleNo0(budBala, 2)));
				mNormFee.setNormAmountText(Data.normalToFinal(Data.trimDoubleNo0(occBala, 2)));
				mNormFee.setAddBalaText(Data.normalToFinal(Data.trimDoubleNo0(budBala - occBala, 2)));
				mNormFee.setOccBalaText(budBala==0 ? "" : Data.trimDoubleNo0((budBala - occBala) * 100 / budBala, 2));
    			retList.add(mNormFee);
			}

    		double budBala = ndc.getSum("norm_bala");
    		double occBala = ndc.getSum("norm_amount");
    		MNormFee mNormFee = new MNormFee();
    		mNormFee.setRowSpan(0);
    		mNormFee.setItemName("小计");
			mNormFee.setNormBalaText(Data.normalToFinal(Data.trimDoubleNo0(budBala, 2)));
			mNormFee.setNormAmountText(Data.normalToFinal(Data.trimDoubleNo0(occBala, 2)));
			mNormFee.setAddBalaText(Data.normalToFinal(Data.trimDoubleNo0(budBala - occBala, 2)));
			mNormFee.setOccBalaText(budBala==0 ? "" : Data.trimDoubleNo0((budBala - occBala) * 100 / budBala, 2));
    		retList.add(mNormFee);
		}

		double budBala = dc.getSum("norm_bala");
		double occBala = dc.getSum("norm_amount");
    	MNormFee mNormFee = new MNormFee();
    	mNormFee.setRowSpan(0);
    	mNormFee.setTeamName("合计");
		mNormFee.setNormBalaText(Data.normalToFinal(Data.trimDoubleNo0(budBala, 2)));
		mNormFee.setNormAmountText(Data.normalToFinal(Data.trimDoubleNo0(occBala, 2)));
		mNormFee.setAddBalaText(Data.normalToFinal(Data.trimDoubleNo0(budBala - occBala, 2)));
		mNormFee.setOccBalaText(budBala==0 ? "" : Data.trimDoubleNo0((budBala - occBala) * 100 / budBala, 2));
    	retList.add(mNormFee);

    	return retList;
	}
}
