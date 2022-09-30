package cn.ewsd.cost.service.impl;

import cn.ewsd.base.utils.Snow;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.cost.mapper.MOutAssessMapper;
import cn.ewsd.cost.model.MOutAssess;
import cn.ewsd.cost.service.MOutAssessService;
import cn.ewsd.material.mapper.MMaterialMapper;
import cn.ewsd.material.mapper.MPrjMapper;
import cn.ewsd.material.model.MMaterial;
import cn.ewsd.mdata.mapper.OrganizationMapper;
import cn.ewsd.mdata.model.Organization;
import cn.ewsd.system.service.ConfigService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Service("mOutAssessServiceImpl")
public class MOutAssessServiceImpl extends CostBaseServiceImpl<MOutAssess, String> implements MOutAssessService {
	@Autowired
	private MOutAssessMapper mOutAssessMapper;
	@Autowired
	private MMaterialMapper mMaterialMapper;
	@Autowired
	private OrganizationMapper organizationMapper;
	@Autowired
	private MPrjMapper mPrjMapper;
	@Autowired
	private ConfigService configService;

    @Override
    public PageSet<MOutAssess> getDetailUpDataPageSet(PageParam pageParam, String filterSort, String monthQry, String teamQry, String storeQry, String addrQry, String purchaseQry, String reserveNoQry, String drawSrcQry, String itemNameQry, String matQry, boolean ifMngDept, String matMngTeam) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<MOutAssess> list = mOutAssessMapper.getDetailUpDataList(filterSort, monthQry, teamQry, storeQry, addrQry, purchaseQry, reserveNoQry, drawSrcQry, itemNameQry, matQry, ifMngDept, matMngTeam);
        PageInfo<MOutAssess> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
	public List<MOutAssess> getItemList(String monthQry){
    	return mOutAssessMapper.getItemList(monthQry);
	}

	@Override
	public List<MOutAssess> getItemForSelect(String teamNo, boolean ifMngDept){
    	return mOutAssessMapper.getItemForSelect(teamNo, ifMngDept);
	}

	@Override
	public List<MOutAssess> getPrjForSelect(String teamNo){
    	return mOutAssessMapper.getPrjForSelect(teamNo);
	}

	@Override
	public List<MOutAssess> getDetailUpDataList(String monthQry, String teamQry, String storeQry, String addrQry, String purchaseQry, String reserveNoQry, String drawSrcQry, String itemNameQry, String matQry, boolean ifMngDept, String matMngTeam) {
		return mOutAssessMapper.getDetailUpDataList("", monthQry, teamQry, storeQry, addrQry, purchaseQry, reserveNoQry, drawSrcQry, itemNameQry, matQry, ifMngDept, matMngTeam);
	}

	@Override
	public void insertDetailUpData(HttpServletRequest request, UserInfo userInfo) throws Exception{
		String yearQry = request.getParameter("yearQry");
		String monQry = request.getParameter("monQry");
		String teamNo = request.getParameter("teamNo");
		String itemNo = request.getParameter("itemNo");
		String prjNo = request.getParameter("prjNo");
		String matNo = request.getParameter("matNo");
		String occDate = XDate.dateTo8(request.getParameter("occDate"));
		String assDate = yearQry+monQry+"25";
		String remark = request.getParameter("remark");
		double amount = 0;
		try{
			amount = Double.parseDouble(request.getParameter("amount"));
		}catch (Exception e){
			throw new XException(XException.ERR_DEFAULT, "发生数量必须是数字！");
		}

		if("".equals(teamNo)){
			throw new XException(XException.ERR_DEFAULT, "区队，必须选择！");
		}
		if("".equals(itemNo)){
			throw new XException(XException.ERR_DEFAULT, "考核项目，必须选择！");
		}
		if("".equals(matNo)){
			throw new XException(XException.ERR_DEFAULT, "物料必须选择");
		}

		MMaterial mMaterial = mMaterialMapper.getMatByNo(matNo);
		Organization organization = organizationMapper.getListById(teamNo);
		String prjName = "";
		if(!"".equals(prjNo)){
			prjName = mPrjMapper.getPrjByPrjNo(prjNo).getPrjName();
		}

		MOutAssess mOutAssess = new MOutAssess();
		mOutAssess.setDrawNo(Snow.getUUID()+"");
		mOutAssess.setSplitNo("1");
		mOutAssess.setOccDate(occDate);
		mOutAssess.setAssDate(assDate);
		mOutAssess.setTeamNo(teamNo);
		mOutAssess.setStoreNo("");
		mOutAssess.setCenterNo(organization.getDeptCode());
		mOutAssess.setPrjNo(prjNo);
		mOutAssess.setPrjName(prjName);
		mOutAssess.setPlanItem("");
		mOutAssess.setMatNo(matNo);
		mOutAssess.setMatCode(mMaterial.getMatCode());
		mOutAssess.setMatPrice(mMaterial.getMatPrice());
		mOutAssess.setMatAmount(new BigDecimal(amount));
		mOutAssess.setMatBala(new BigDecimal(amount).multiply(mMaterial.getMatPrice()));
		mOutAssess.setReserveNo("");
		mOutAssess.setDrawSrc("m.drawSrc.FACT");
		mOutAssess.setIfAccess("1");
		mOutAssess.setItemNo(itemNo);
		mOutAssess.setStatus("1");
		mOutAssess.setRemark(remark);
		mOutAssess.setWbs("");
		mOutAssessMapper.insertMOutAssess(mOutAssess);
	}

	@Override
	@Transactional
	public void insDetailUpData(HttpServletRequest request, UserInfo userInfo) throws Exception{
    	try {
			String yearQry = request.getParameter("yearQry");
			String monQry = request.getParameter("monQry");
			String month = yearQry+monQry;
			String beginDate = month+"01";
			String endDate = XDate.getLastDayOfMonth(month+"01");

			mOutAssessMapper.insertDetailUpData(month, beginDate, endDate);

			String jdk = configService.getConfigByCode("XT_TEAM_EQUIP");
			mOutAssessMapper.updateJDK(jdk, month);

			mOutAssessMapper.updateZJCL(month, "综机材料");

			mOutAssessMapper.updateRHY(month, "乳化油");

			mOutAssessMapper.updateZHCL(month);

			mOutAssessMapper.updateDC(month);

			mOutAssessMapper.updateTYCL(month);

			mOutAssessMapper.updatePrj(month);
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	@Transactional
	public void deleteDetailUpData(HttpServletRequest request, UserInfo userInfo) throws Exception{
		try {
			String yearQry = request.getParameter("yearQry");
			String monQry = request.getParameter("monQry");
			String drawSrcQry = request.getParameter("drawSrcQry");
			String month = yearQry+monQry;
			String teamQry = request.getParameter("teamQry");

			mOutAssessMapper.deleteDetailUpDataAll(month, drawSrcQry, teamQry);
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	@Transactional
	public void saveDetailUpData(HttpServletRequest request, UserInfo userInfo) throws Exception{
		try {
			String[] keys = request.getParameterValues("key");
			for(int i=0; keys!=null && i<keys.length; i++){
				String key = keys[i];
				String drawNo = key.split("\\_")[0];
				String splitNo = key.split("\\_")[1];
				String itemNo = request.getParameter("item_"+key);
				String prjNo = request.getParameter("prj_"+key);

				MOutAssess mOutAssess = new MOutAssess();
				mOutAssess.setItemNo(itemNo);
				mOutAssess.setPrjNo(prjNo);
				mOutAssess.setPrjName("0".equals(prjNo) ? "" : mPrjMapper.getPrjByPrjNo(prjNo).getPrjName());
				mOutAssess.setDrawNo(drawNo);
				mOutAssess.setSplitNo(splitNo);
				mOutAssessMapper.updateMOutAssess(mOutAssess);
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	public List<Organization> getOutAssessDept(String month){
    	return mOutAssessMapper.getOutAssessDept(month);
	}
}
