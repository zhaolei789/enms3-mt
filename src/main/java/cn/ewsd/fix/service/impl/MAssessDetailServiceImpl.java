package cn.ewsd.fix.service.impl;

import cn.ewsd.base.utils.*;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.model.MCoreBase;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.DateUtils;
import cn.ewsd.common.utils.StringUtils;
import cn.ewsd.cost.util.BigDecimalUtils;
import cn.ewsd.fix.mapper.MBackPlanMapper;
import cn.ewsd.fix.mapper.MBackQuotaMapper;
import cn.ewsd.fix.mapper.MFixAssessMapper;
import cn.ewsd.fix.model.MBackPlan;
import cn.ewsd.fix.model.MBackQuota;
import cn.ewsd.fix.model.MFixAssess;
import cn.ewsd.material.mapper.MMaterialMapper;
import cn.ewsd.material.mapper.MPrjMapper;
import cn.ewsd.material.model.MMaterial;
import cn.ewsd.material.model.MPrj;
import cn.ewsd.mdata.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.ewsd.fix.mapper.MAssessDetailMapper;
import cn.ewsd.fix.model.MAssessDetail;
import cn.ewsd.fix.service.MAssessDetailService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;

@Service("mAssessDetailServiceImpl")
public class MAssessDetailServiceImpl extends FixBaseServiceImpl<MAssessDetail, String> implements MAssessDetailService {
	@Autowired
	private MAssessDetailMapper mAssessDetailMapper;
	@Autowired
	private UtilService utilService;
	@Autowired
	private MBackPlanMapper mBackPlanMapper;
	@Autowired
	private MPrjMapper mPrjMapper;
	@Autowired
	private MFixAssessMapper mFixAssessMapper;
	@Autowired
	private MBackQuotaMapper mBackQuotaMapper;
	@Autowired
	private MMaterialMapper mMaterialMapper;

    @Override
	public List<MAssessDetail> getBackAssessList(String assMonth, String userTeam, String teamQry, String prjQry, String mngTeamQry){
    	List<MAssessDetail> list =  mAssessDetailMapper.getBackAssessList(assMonth, userTeam, teamQry, prjQry, mngTeamQry);
    	DataCantainer<MAssessDetail> dc = new DataCantainer<>((ArrayList)list);
    	ArrayList<DataCantainer<MAssessDetail>> aList = dc.getGroup("prj_name");

    	List<MAssessDetail> retList = new ArrayList<>();
    	for(int i=0; i<aList.size(); i++){
			DataCantainer<MAssessDetail> adc = aList.get(i);
			MAssessDetail mAssessDetail = new MAssessDetail();
			mAssessDetail.setPrjName(adc.getRow(0).getPrjName());
			mAssessDetail.setPrjRow("1");
			retList.add(mAssessDetail);
			for(int j=0; j<adc.getRowCount(); j++){
				retList.add(adc.getRow(j));
			}
		}

    	return retList;
	}

	@Override
	@Transactional
	public void dealBackAss(HttpServletRequest request, UserInfo userInfo) throws Exception{
		try{
			String userTeam = userInfo.getOrgId();
			String yearQry = request.getParameter("yearQry");
			String monQry = request.getParameter("monQry");
			String monthQry = yearQry+monQry;
			String userId = userInfo.getUuid();

			ArrayList<String[]> condList = new ArrayList<>();
			condList.add(new String[]{"ass_month", "=", monthQry});
			condList.add(new String[]{"ass_type", "=", "m.fixType.1"});
			condList.add(new String[]{"ass_status", "!=", "0"});
			condList.add(new String[]{"mng_team", "=", userTeam});
			boolean flag = utilService.checkColumnDataExist("m_fix_assess", condList);
			if(flag){
				throw new XException(XException.ERR_DEFAULT, "本单位考核，已经开始！");
			}

			List<MBackPlan> planList = mBackPlanMapper.getBackAssPlan(monthQry, userTeam);
			DataCantainer<MBackPlan> planDc = new DataCantainer<>((ArrayList)planList);

			mAssessDetailMapper.deleteAssessDetail(monthQry, userTeam);

			ArrayList<DataCantainer<MBackPlan>> list = planDc.getGroup("prj_no");
			for(int i=0; i<list.size(); i++){
				DataCantainer<MBackPlan> dc = list.get(i);
				MBackPlan mBackPlan = dc.getRow(0);
				String prjNo = mBackPlan.getPrjNo();
				Integer teamNo = mBackPlan.getTeamNo();
				MPrj mPrj = mPrjMapper.getPrjByPrjNo(prjNo);
				String prjName = mPrj.getPrjName();

				MFixAssess mFixAssess = mFixAssessMapper.getFixAssess1("m.fixType.1", monthQry, userTeam, "0", prjNo);
				String assId = mFixAssess==null ? "" : mFixAssess.getAssessId();

				if("".equals(assId)){
					assId = Snow.getUUID()+"";
					MFixAssess mfa = new MFixAssess();
					mfa.setAssessId(assId);
					mfa.setAssType("m.fixType.1");
					mfa.setAssMonth(monthQry);
					mfa.setMngTeam(Integer.parseInt(userTeam));
					mfa.setTeamNo(teamNo);
					mfa.setPrjNo(prjNo);
					mfa.setPrjName(prjName);
					mfa.setWorkValue(new BigDecimal("0"));
					mfa.setRemark("");
					mfa.setAssStatus("0");
					mfa.setApplyUser(userId);
					mfa.setApplyDate(XDate.getDate());
					mfa.setAgreeUser("");
					mfa.setAgreeDate("");
					mFixAssessMapper.insertFixAssess(mfa);
				}

				List<MBackQuota> backQuotaList = mBackQuotaMapper.getBackQuotaList1(prjNo, userTeam);
				DataCantainer<MBackQuota> bqDc = new DataCantainer<>((ArrayList)backQuotaList);

				for(int j=0; j<dc.getRowCount(); j++){
					MBackPlan mBackPlan1 = dc.getRow(j);
					String matNo = mBackPlan1.getMatNo();
					MMaterial mMaterial = mMaterialMapper.getMatByNo(matNo);
					BigDecimal fixPrice = mMaterial.getFixPrice();
					BigDecimal plan = mBackPlan1.getPlanAmount();
					BigDecimal zz = mBackPlan1.getZzAmount();
					BigDecimal hs = mBackPlan1.getHsAmount();
					double norm = bqDc.findDataCantainer("mat_no", matNo).getSum("amount");

					MAssessDetail mAssessDetail = new MAssessDetail();
					mAssessDetail.setDetailId(Snow.getUUID()+"");
					mAssessDetail.setAssessId(assId);
					mAssessDetail.setMatNo(matNo);
					mAssessDetail.setPlanAmount(plan);
					mAssessDetail.setMatPrice(fixPrice);
					mAssessDetail.setBackNorm(new BigDecimal(norm+""));
					mAssessDetail.setBackAmount(new BigDecimal("0"));
					mAssessDetail.setBackRatio(new BigDecimal("0"));
					mAssessDetail.setMatAmount(zz.add(hs));
					BigDecimal OverScale = new BigDecimal(0);//计算
					if(plan.compareTo(new BigDecimal("0"))!=0){
						BigDecimal bd_tmp1 = BigDecimalUtils.add(zz,hs);
						BigDecimal bd_tmp2 = BigDecimalUtils.multiply(bd_tmp1,new BigDecimal("100"));
						OverScale = BigDecimalUtils.divide(bd_tmp2,plan,2);
					}
					//mAssessDetail.setOverScale(plan.compareTo(new BigDecimal("0"))==0 ? new BigDecimal("0") : zz.add(hs).multiply(new BigDecimal("100")).divide(plan));
					mAssessDetail.setOverScale(OverScale);
					mAssessDetail.setAccountScale(new BigDecimal("0"));
					mAssessDetail.setAccountBala(new BigDecimal("0"));
					mAssessDetail.setResult("");
					mAssessDetail.setRemark("");
					mAssessDetail.setUuid(BaseUtils.UUIDGenerator());
					mAssessDetail.setCreatorId(LoginInfo.getUserNameId());
					mAssessDetail.setCreator(LoginInfo.getUserName());
					mAssessDetail.setCreateTime(new Date());
					mAssessDetail.setCreatorOrgId(Integer.parseInt(LoginInfo.getOrgId()));
					mAssessDetail.setModifierId(LoginInfo.getUserNameId());
					mAssessDetail.setModifier(LoginInfo.getUserName());
					mAssessDetail.setModifyTime(DateUtils.getSystemDate());
					mAssessDetailMapper.insertAssessDetail(mAssessDetail);
				}
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	@Transactional
	public void saveBackAss(HttpServletRequest request, UserInfo userInfo) throws Exception{
		try{
			String userTeam = userInfo.getOrgId();
			String yearQry = request.getParameter("yearQry");
			String monQry = request.getParameter("monQry");
			String monthQry = yearQry+monQry;
			String teamQry = request.getParameter("teamQry");
			String prjQry = request.getParameter("prjQry");
			String assRemark = request.getParameter("assRemark");
			double workValue = 0;
			try {
				workValue = Double.parseDouble(request.getParameter("workValue"));
			}catch (Exception e){
				throw new XException(XException.ERR_DEFAULT, "生产情况必须是数字！");
			}
			if("".equals(teamQry) && workValue>0){
				throw new XException(XException.ERR_DEFAULT, "请选择回收单位！");
			}
			if("".equals(prjQry)){
				throw new XException(XException.ERR_DEFAULT, "请选择工程项目！");
			}

			ArrayList<String[]> condList = new ArrayList<>();
			condList.add(new String[]{"ass_month", "=", monthQry});
			condList.add(new String[]{"ass_type", "=", "m.fixType.1"});
			condList.add(new String[]{"ass_status", "!=", "0"});
			condList.add(new String[]{"mng_team", "=", userTeam});
			condList.add(new String[]{"team_no", "=", teamQry});
			boolean flag = utilService.checkColumnDataExist("m_fix_assess", condList);
			if(flag){
				throw new XException(XException.ERR_DEFAULT, "本单位考核，已经开始！");
			}

			MFixAssess mFixAssess = new MFixAssess();
			mFixAssess.setWorkValue(new BigDecimal(workValue+""));
			mFixAssess.setRemark(assRemark);
			mFixAssess.setAssMonth(monthQry);
			mFixAssess.setAssType("m.fixType.1");
			mFixAssess.setMngTeam(Integer.parseInt(userTeam));
			mFixAssess.setTeamNo(Integer.parseInt(teamQry));
			mFixAssessMapper.updateFixAssess(mFixAssess);

			String[] detailIds = request.getParameterValues("detailId");
			for(int i=0; i<detailIds.length; i++){
				String detailId = detailIds[i];
				if("".equals(detailId)){
					continue;
				}
				double norm = 0;
				double assAmount = 0;
				try {
					norm = Double.parseDouble(request.getParameter("norm_"+detailId));
				}catch (Exception e){
					throw new XException(XException.ERR_DEFAULT, "每米回收量必须是数字！");
				}
				try {
					assAmount = Double.parseDouble(request.getParameter("ass_"+detailId));
				}catch (Exception e){
					throw new XException(XException.ERR_DEFAULT, "考核数量必须是数字！");
				}
				double ass = assAmount==0 ? workValue*norm : assAmount;
				String result = request.getParameter("result_"+detailId);
				String remark = request.getParameter("remark_"+detailId);

				MAssessDetail mAssessDetail = mAssessDetailMapper.getAssessDetailByDetailId(detailId);
				BigDecimal matAmount = mAssessDetail.getMatAmount();

				MAssessDetail md = new MAssessDetail();
				md.setBackNorm(new BigDecimal(norm+""));
				md.setBackAmount(new BigDecimal(ass+""));
				md.setRemark(remark);
				md.setResult(result);
				BigDecimal overScale = new BigDecimal("0");
				if(ass!=0){
					BigDecimal bd_tmp1 = BigDecimalUtils.divide(matAmount, new BigDecimal(ass+""));
					overScale = BigDecimalUtils.multiply(bd_tmp1, new BigDecimal("100"),2);
				}
				md.setOverScale(overScale);
				md.setDetailId(detailId);
				mAssessDetailMapper.updateAssessDetail(md);
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	@Transactional
	public void backBackAss(HttpServletRequest request, UserInfo userInfo) throws Exception{
		try{
			String userTeam = userInfo.getOrgId();
			String yearQry = request.getParameter("yearQry");
			String monQry = request.getParameter("monQry");
			String monthQry = yearQry+monQry;
			String teamQry = request.getParameter("teamQry");
			String prjQry = request.getParameter("prjQry");
			String mngTeamQry = request.getParameter("mngTeamQry");

			if("".equals(teamQry)){
				throw new XException(XException.ERR_DEFAULT, "请选择回收单位！");
			}
			if("".equals(prjQry)){
				throw new XException(XException.ERR_DEFAULT, "请选择工程项目！");
			}
			if("".equals(mngTeamQry) || mngTeamQry==null){
				throw new XException(XException.ERR_DEFAULT, "请选择管理单位！");
			}

			ArrayList<String[]> condList = new ArrayList<>();
			condList.add(new String[]{"ass_month", "=", monthQry});
			condList.add(new String[]{"ass_type", "=", "m.fixType.1"});
			condList.add(new String[]{"ass_status", "!=", "F"});
			condList.add(new String[]{"mng_team", "=", userTeam});
			condList.add(new String[]{"team_no", "=", teamQry});
			boolean flag = utilService.checkColumnDataExist("m_fix_assess", condList);
			if(flag){
				throw new XException(XException.ERR_DEFAULT, "本单位考核，尚未开始！");
			}

			MFixAssess mFixAssess = new MFixAssess();
			mFixAssess.setAssStatus("0");
			mFixAssess.setAssMonth(monthQry);
			mFixAssess.setAssType("m.fixType.1");
			mFixAssess.setPrjNo(prjQry);
			mFixAssess.setMngTeam(Integer.parseInt(mngTeamQry));
			mFixAssess.setTeamNo(Integer.parseInt(teamQry));
			mFixAssessMapper.updateFixAssess(mFixAssess);
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	@Transactional
	public void submitBackAss(HttpServletRequest request, UserInfo userInfo) throws Exception{
		try{
			String userTeam = userInfo.getOrgId();
			String yearQry = request.getParameter("yearQry");
			String monQry = request.getParameter("monQry");
			String monthQry = yearQry+monQry;
			String teamQry = request.getParameter("teamQry");

			ArrayList<String[]> condList = new ArrayList<>();
			condList.add(new String[]{"ass_month", "=", monthQry});
			condList.add(new String[]{"ass_type", "=", "m.fixType.1"});
			condList.add(new String[]{"ass_status", "!=", "0"});
			condList.add(new String[]{"mng_team", "=", userTeam});
			if(!"".equals(teamQry)){
				condList.add(new String[]{"team_no", "=", teamQry});
			}
			boolean flag = utilService.checkColumnDataExist("m_fix_assess", condList);
			if(flag){
				throw new XException(XException.ERR_DEFAULT, "本单位考核，已经开始！");
			}

			MFixAssess mFixAssess = new MFixAssess();
			mFixAssess.setAssStatus("F");
			mFixAssess.setAssMonth(monthQry);
			mFixAssess.setAssType("m.fixType.1");
			mFixAssess.setMngTeam(Integer.parseInt(userTeam));
			if(!"".equals(teamQry)){
				mFixAssess.setTeamNo(Integer.parseInt(teamQry));
			}
			mFixAssessMapper.updateFixAssess(mFixAssess);
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}
}
