package cn.ewsd.fix.service.impl;

import cn.ewsd.base.utils.Snow;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.material.mapper.MMaterialMapper;
import cn.ewsd.material.mapper.MPrjMapper;
import cn.ewsd.mdata.service.UtilService;
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

import cn.ewsd.fix.mapper.MBackQuotaMapper;
import cn.ewsd.fix.model.MBackQuota;
import cn.ewsd.fix.service.MBackQuotaService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;

@Service("mBackQuotaServiceImpl")
public class MBackQuotaServiceImpl extends FixBaseServiceImpl<MBackQuota, String> implements MBackQuotaService {
	@Autowired
	private MBackQuotaMapper mBackQuotaMapper;
	@Autowired
	private MPrjMapper mPrjMapper;
	@Autowired
	private MMaterialMapper mMaterialMapper;
	@Autowired
	private UtilService utilService;

    @Override
    public PageSet<MBackQuota> getPageSet(PageParam pageParam, String filterSort, String monthQry, String prjQry, String userTeam) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<MBackQuota> list = mBackQuotaMapper.getBackQuotaList(filterSort, monthQry, prjQry, userTeam);
        PageInfo<MBackQuota> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
	public List<MBackQuota> getBackQuotaList(String montQry, String prjQry, String userTeam){
    	return mBackQuotaMapper.getBackQuotaList("", montQry, prjQry, userTeam);
	}

	@Override
	public List<MBackQuota> getBackPlanList(String monthQry, String prjQry, String userTeam, String teamNoQry){
    	return mBackQuotaMapper.getBackPlanList(monthQry, prjQry, userTeam, teamNoQry);
	}

    @Override
	public void insertBackQuota(HttpServletRequest request, UserInfo userInfo) throws Exception{
		try{
			String month = request.getParameter("month");
			String prjNo = request.getParameter("prjNo");
			String matNo = request.getParameter("matNo");
			double measure = 0;
			try{
				measure = Double.parseDouble(request.getParameter("measure"));
			}catch (Exception e){
				throw new XException(XException.ERR_DEFAULT, "推进计量必须是数字！");
			}
			if(measure<=0){
				throw new XException(XException.ERR_DEFAULT, "推进计量必须是正数！");
			}
			double amount = 0;
			try {
				amount = Double.parseDouble(request.getParameter("amount"));
			}catch (Exception e){
				throw new XException(XException.ERR_DEFAULT, "回收量必须是数字！");
			}
			if(amount<=0){
				throw new XException(XException.ERR_DEFAULT, "回收量必须是正数！");
			}
			String prjName = mPrjMapper.getPrjByPrjNo(prjNo).getPrjName();
			BigDecimal matPrice = mMaterialMapper.getMatByNo(matNo).getMatPrice();

			ArrayList<String[]> list = new ArrayList<>();
			list.add(new String[]{"begin_month", "=", month});
			list.add(new String[]{"prj_no", "=", prjNo});
			list.add(new String[]{"mat_no", "=", matNo});
			if(utilService.checkColumnDataExist("m_back_quota", list)){
				MBackQuota mBackQuota = new MBackQuota();
				mBackQuota.setMeasure(measure);
				mBackQuota.setAmount(amount);
				mBackQuota.setMatPrice(matPrice.doubleValue());
				mBackQuota.setBeginMonth(month);
				mBackQuota.setPrjNo(prjNo);
				mBackQuota.setMatNo(matNo);
				mBackQuotaMapper.updateBackQuota(mBackQuota);
			}else{
				MBackQuota mBackQuota = new MBackQuota();
				mBackQuota.setQuotaId(Snow.getUUID()+"");
				mBackQuota.setBeginMonth(month);
				mBackQuota.setPrjNo(prjNo);
				mBackQuota.setPrjName(prjName);
				mBackQuota.setMatNo(matNo);
				mBackQuota.setMeasure(measure);
				mBackQuota.setAmount(amount);
				mBackQuota.setMatPrice(matPrice.doubleValue());
				mBackQuota.setMngTeam(Integer.parseInt(userInfo.getOrgId()));
				mBackQuota.setModiDate(XDate.getDate());
				mBackQuota.setModiUser(userInfo.getUuid());
				mBackQuota.setUuid(Snow.getUUID()+"");
				mBackQuotaMapper.insertBackQuota(mBackQuota);
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	@Transactional
	public void deleteBackQuota(HttpServletRequest request) throws Exception{
		try{
			String[] quotaIds = request.getParameterValues("quotaId");
			if(quotaIds==null || quotaIds.length<1){
				throw new XException(XException.ERR_DEFAULT, "请先选择要删除的定额");
			}
			for(int i=0; i<quotaIds.length; i++){
				String quotaId = quotaIds[i];
				mBackQuotaMapper.deleteBackQuota(quotaId);
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}
}
