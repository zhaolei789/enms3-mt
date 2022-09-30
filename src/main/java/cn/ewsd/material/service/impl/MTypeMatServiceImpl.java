package cn.ewsd.material.service.impl;

import cn.ewsd.base.utils.Snow;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.material.mapper.*;
import cn.ewsd.material.model.*;
import cn.ewsd.material.service.MTypeMatService;
import cn.ewsd.repository.mapper.MOutMapper;
import cn.ewsd.repository.model.MOut;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Service("mTypeMatServiceImpl")
public class MTypeMatServiceImpl extends MaterialBaseServiceImpl<MTypeMat, String> implements MTypeMatService {
	@Autowired
	private MTypeMatMapper mTypeMatMapper;
	@Autowired
	private MMaterialMapper mMaterialMapper;
	@Autowired
	private MOutMapper mOutMapper;
	@Autowired
	private MHandInMapper mHandInMapper;

	@Override
	public PageSet<MTypeMat> getPageSet(PageParam pageParam, String filterSort, String matCodeQry, String matNameQry){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MTypeMat> list = mTypeMatMapper.getTypeMatList(matCodeQry, matNameQry);
		PageInfo<MTypeMat> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public List<MTypeMat> getMTypeMatList(String matCodeQry, String matNameQry){
		return mTypeMatMapper.getTypeMatList(matCodeQry, matNameQry);
	}

	@Override
	public PageSet<MTypeMat> getComPlanPageSet(PageParam pageParam, String filterSort, String planMonth, String matQry){
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MTypeMat> list = mTypeMatMapper.getComPlanList(planMonth, matQry);
		PageInfo<MTypeMat> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	@Transactional
	public void comIns(HttpServletRequest request, UserInfo userInfo) throws Exception{
		try{
			String[] matNos = request.getParameterValues("matNo");
			for(int i=0; i<matNos.length; i++){
				String matNo = matNos[i];
				double oldRate = 0;
				try{
					oldRate = Double.parseDouble(request.getParameter("rate1_"+matNo));
				}catch (Exception e){
					throw new XException(XException.ERR_DEFAULT, "交旧比率必须是数字！");
				}

				mTypeMatMapper.deleteTypeMat("m.TypeCode.10", matNo);

				MTypeMat mTypeMat = new MTypeMat();
				mTypeMat.setTypeCode("m.TypeCode.10");
				mTypeMat.setMatNo(matNo);
				mTypeMat.setOldRate(new BigDecimal(oldRate+""));
				mTypeMat.setOldLimit(0);
				mTypeMatMapper.insertTypeMat(mTypeMat);
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	@Transactional
	public void insertTypeMat(MTypeMat mTypeMat) throws Exception{
		try{
			String matCode = mTypeMat.getMatCode();
			BigDecimal backRate = mTypeMat.getOldRate();

			if("".equals(matCode) || matCode==null){
				throw new XException(XException.ERR_DEFAULT, "材料编码不能为空！");
			}
			if(matCode.length()>9){
				throw new XException(XException.ERR_DEFAULT, "编码应为8位数字！");
			}

			MMaterial mMaterial = mMaterialMapper.getMatByCode("X"+matCode);
			if(mMaterial==null){
				throw new XException(XException.ERR_DEFAULT, "找不到对应的物料信息，请重新输入！");
			}
			String matNo = mMaterial.getMatNo();

			mTypeMatMapper.deleteTypeMat("m.TypeCode.10", matNo);

			MTypeMat mTypeMat1 = new MTypeMat();
			mTypeMat1.setMatNo(matNo);
			mTypeMat1.setTypeCode("m.TypeCode.10");
			mTypeMat1.setOldRate(backRate);
			mTypeMatMapper.insertTypeMat(mTypeMat1);
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	@Transactional
	public void saveTypeMat(HttpServletRequest request, UserInfo userInfo) throws Exception{
		try{
			String[] matNos = request.getParameterValues("matNo");
			for(int i=0; i<matNos.length; i++){
				String matNo = matNos[i];
				double oldRate = 0;
				try{
					oldRate = Double.parseDouble(request.getParameter("rate_"+matNo));
				}catch (Exception e){
					throw new XException(XException.ERR_DEFAULT, "交旧比率必须是数字！");
				}

				MTypeMat mTypeMat = new MTypeMat();
				mTypeMat.setTypeCode("m.TypeCode.10");
				mTypeMat.setMatNo(matNo);
				mTypeMat.setOldRate(new BigDecimal(oldRate+""));
				mTypeMat.setOldLimit(0);
				mTypeMatMapper.updateTypeMat(mTypeMat);
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	@Transactional
	public void deleteTypeMat(HttpServletRequest request, UserInfo userInfo) throws Exception{
		String[] matNos = request.getParameterValues("matNo");
		for(int i=0; i<matNos.length; i++){
			String matNo = matNos[i];

			mTypeMatMapper.deleteTypeMat("m.TypeCode.10", matNo);
		}
	}

	@Override
	@Transactional
	public void dealTypeMat(HttpServletRequest request, UserInfo userInfo) throws Exception{
		try{
			List<MOut> outList = mOutMapper.getOldMatEditOut();

			String remark = "由系统自动生成";
			String occDate = XDate.getDate();
			String userId = userInfo.getUuid();

			for(int i=0; i<outList.size(); i++){
				MOut mOut = outList.get(i);

				MHandIn mHandIn = new MHandIn();
				mHandIn.setBillNo(Snow.getUUID()+"");
				mHandIn.setMatNo(mOut.getMatNo());
				mHandIn.setTeamNo(mOut.getTeamNo());
				mHandIn.setStoreNo("");
				mHandIn.setMatAmount(mOut.getAmount());
				mHandIn.setOldRate(mOut.getOldRate());
				mHandIn.setOccDate(mOut.getOutDate());
				mHandIn.setPlanGive("");
				mHandIn.setLinkNo(mOut.getDrawNo());
				mHandIn.setRemark(remark);
				mHandIn.setModiEmp(userId);
				mHandIn.setModiDate(occDate);
				mHandIn.setCheckStep("7202F");
				mHandIn.setPauseMonth("");
				mHandInMapper.insertHandIn(mHandIn);
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}
}
