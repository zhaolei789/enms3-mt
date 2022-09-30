package cn.ewsd.material.service.impl;

import cn.ewsd.base.utils.XException;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.material.mapper.*;
import cn.ewsd.material.model.*;
import cn.ewsd.mdata.mapper.UtilMapper;
import cn.ewsd.repository.mapper.MOutMapper;
import cn.ewsd.repository.model.MOut;
import cn.ewsd.system.model.DicItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.ewsd.material.service.MMaterialService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service("mMaterialServiceImpl")
public class MMaterialServiceImpl extends MaterialBaseServiceImpl<MMaterial, String> implements MMaterialService {
	@Autowired
	private MMaterialMapper mMaterialMapper;
	@Autowired
	private MErpMaterialMapper mErpMaterialMapper;
	@Autowired
	private MOutMapper mOutMapper;
	@Autowired
	private MMakeMatDeptMapper mMakeMatDeptMapper;
	@Autowired
	private UtilMapper utilMapper;

	@Transactional
	@Override
	public void executeSave(MMaterial mMaterial, MErpMaterial mErpMaterial, MMakeMatDept mMakeMatDept) throws Exception{
		try{
			//mMaterial.setTypeName(mMatTypeMapper.getMatTypeByCode(mMaterial.getErpType()).getTypeName());
			mMaterialMapper.executeSave(mMaterial);

			String matType = mMaterial.getMatType();
			ArrayList<String[]> condArr = new ArrayList<String[]>();
			condArr.add(new String[]{"value", ">=", "m.matType.01"});
			condArr.add(new String[]{"value", "<=", "m.matType.90"});
			condArr.add(new String[]{"value", "=", "m.matType."+matType});
			boolean ifOut = utilMapper.getRowCountByCondition("sys_dic_item", condArr) > 0;
			if(!"".equals(mErpMaterial.getFactoryNo()) && !"".equals(mErpMaterial.getMatAddr()) && !"J".equals(mMaterial.getNewOld()) && ifOut){
				if(mMaterialMapper.getFactoryAddr1(mErpMaterial.getFactoryNo(), mErpMaterial.getMatAddr()).size()<1){
					throw new XException(XException.ERR_DEFAULT, "请选择正确的工厂库存地！");
				}

				mErpMaterialMapper.executeSave(mErpMaterial);
			}

			String fixContent = mMaterial.getFixContent();
			if(!"".equals(fixContent)){
				MOut mOut = new MOut();
				mOut.setOfferTeam(Integer.parseInt(fixContent));
				mOut.setMatNo(mMaterial.getMatNo());
				mOut.setDataSrc("m.dataSrc.P");
				mOutMapper.updateOfferTeam(mOut);
			}

			Integer makeTeam = mMaterial.getMakeTeam();
			String matNo = mMaterial.getMatNo();
			BigDecimal matPrice = mMaterial.getMatPrice();
			if("FB".equals(matType)){
				if("".equals(makeTeam) || makeTeam==null){
					mMakeMatDeptMapper.deleteByMatNo(matNo);
				}else{
					MMakeMatDept l = mMakeMatDeptMapper.findByMatNo(matNo);
					if(l!=null){
						mMakeMatDeptMapper.updateByMatNo(null, matPrice, matNo);
					}else{
						mMakeMatDept.setMatNo(matNo);
						mMakeMatDept.setMakeDept(makeTeam);
						mMakeMatDept.setMakePrice(matPrice);
						mMakeMatDeptMapper.insertData(mMakeMatDept);
					}
				}
			}
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	public List<MFactoryAddr> getFactoryAddr(){
		return  mMaterialMapper.getFactoryAddr();
	}

	@Override
	public List<DicItem> getOldTeamForSelect(){
		return mMaterialMapper.getOldTeamForSelect();
	}

	@Override
	public List<DicItem> getFixTeamForSelect(){
		return mMaterialMapper.getFixTeamForSelect();
	}

	@Override
	public String getNewSequenceByMatCode(String matCode) {
		List<MMaterial> matList = mMaterialMapper.getMatSequenceByMatCode(matCode);
		String newSequence = "";
		HashMap<String, String> map = new HashMap<String, String>();

		for (int i = 0; i < matList.size(); i++) {
			String code = matList.get(i).getMatCode();
			map.put("1"+code, "");
		}
		for (int i = 10001; i < 19999 ; i++) {
			if(!map.containsKey(i+"")){
				newSequence = (i+"").substring(1);
				break;
			}
		}

		return newSequence;
	}

	@Override
	public MMaterial queryObject(String uuid){
		return mMaterialMapper.queryObject(uuid);
	}

	@Transactional
	@Override
	public void executeUpdate(MMaterial mMaterial, MMakeMatDept mMakeMatDept) throws Exception{
		try{
			String matCode = mMaterial.getMatCode().toUpperCase();
			String newOld = matCode.substring(0, 1);
			String newMatCode = "X" + matCode.substring(1);
			String oldMatCode = "J" + matCode.substring(1);
			String erpType = mMaterial.getErpType();
			String newMatType = erpType.substring(0, 2);

			MMaterial m1 = mMaterialMapper.queryObject(mMaterial.getUuid());
			String prevMatCode = m1.getMatCode();
			String prevMatUnit = m1.getMatUnit();
			BigDecimal matPrice = m1.getMatPrice();
			String prevNewMatCode = "X" + prevMatCode.substring(1);
			String prevOldMatCode = "J" + prevMatCode.substring(1);
			String matName = mMaterial.getMatName();

			if("X".equals(newOld)){
				mMaterialMapper.updateMaterialByMatCode(newMatCode, matName, newMatType, mMaterial.getReFlag(), mMaterial.getAbcType(), mMaterial.getPackScale(), mMaterial.getUseInfo(), prevNewMatCode);
			}else if("J".equals(newOld)){
				mMaterialMapper.updateMaterialByMatCode(oldMatCode, matName, newMatType, mMaterial.getReFlag(), mMaterial.getAbcType(), mMaterial.getPackScale(), mMaterial.getUseInfo(), prevOldMatCode);
			}

			String factoryNo = mMaterial.getFactoryNo();
			String matAddr = mMaterial.getMatAddr();
			String fixContent = mMaterial.getFixContent();
			if("".equals(factoryNo) || "".equals(matAddr)){
				mErpMaterialMapper.deleteByMatNo(m1.getMatNo()+"");
			}
			if(!"".equals(factoryNo) && !"".equals(matAddr)){
				if(mMaterialMapper.getFactoryAddr1(factoryNo, matAddr).size()<1){
					throw new XException(XException.ERR_DEFAULT, "请选择正确的工厂库存地！");
				}
				mErpMaterialMapper.deleteByMatNo(m1.getMatNo()+"");
				MErpMaterial mErpMaterial = new MErpMaterial();
				mErpMaterial.setFactoryNo(factoryNo);
				mErpMaterial.setMatAddr(matAddr);
				mErpMaterial.setMatNo(m1.getMatNo());
				mErpMaterial.setMatUnit(prevMatUnit);
				mErpMaterial.setUuid(BaseUtils.UUIDGenerator());
				mErpMaterialMapper.executeSave(mErpMaterial);
			}
			if(!"".equals(fixContent)){
				MOut mOut = new MOut();
				mOut.setOfferTeam(Integer.parseInt(fixContent));
				mOut.setMatNo(mMaterial.getMatNo());
				mOut.setDataSrc("m.dataSrc.P");
				mOutMapper.updateOfferTeam(mOut);
			}

			if("FB".equals(newMatType)){
				if("".equals(mMaterial.getMakeTeam())){
					mMakeMatDeptMapper.deleteByMatNo(m1.getMatNo());
				}else{
					MMakeMatDept l = mMakeMatDeptMapper.findByMatNo(m1.getMatNo());
					if(l!=null){
						mMakeMatDeptMapper.updateByMatNo(null, matPrice, m1.getMatNo());
					}else{
						mMakeMatDept.setMatNo(m1.getMatNo());
						mMakeMatDept.setMakeDept(m1.getMakeTeam());
						mMakeMatDept.setMakePrice(matPrice);
						mMakeMatDeptMapper.insertData(mMakeMatDept);
					}
				}
			}

			//mMaterial.setTypeName(mMatTypeMapper.getMatTypeByCode(mMaterial.getErpType()).getTypeName());
			mMaterialMapper.executeUpdate(mMaterial);
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return mMaterialMapper.executeDeleteBatch(uuids);
	}

	@Override
	public PageSet<MMaterial> getPageSet(PageParam pageParam, String filterSort) {
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MMaterial> list = mMaterialMapper.getPageSet(filterSort);
		PageInfo<MMaterial> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public PageSet<MMaterial> getMatPageSet(PageParam pageParam, String filterSort, String factoryNo, String itemNo, String planType, String prjNo, String planMonth, String matCodeQry, String matTypeQry, String matNameQry, String lastPlan, String lastMonth){
		ArrayList<String[]> condList = new ArrayList<>();
		condList.add(new String[]{"item_no", "=", itemNo});
		boolean itemFlag = utilMapper.getRowCountByCondition("m_item_mat", condList)>0;

		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MMaterial> list = null;
		if("".equals(factoryNo)){
			list = mMaterialMapper.getMatList2(itemFlag, itemNo, planType, prjNo, planMonth, matCodeQry, matTypeQry, matNameQry, lastPlan, lastMonth);
		}else{
			list = mMaterialMapper.getMatList1(factoryNo, itemFlag, itemNo, planType, prjNo, planMonth, matCodeQry, matTypeQry, matNameQry, lastPlan, lastMonth);
		}

		PageInfo<MMaterial> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public List<MMaterial> getMatListForSel(String q, String newOld){
		return mMaterialMapper.getMatListForSel(q, newOld);
	}

	@Override
	public MMaterial getMatByNo(String matNo){
		return mMaterialMapper.getMatByNo(matNo);
	}

	@Override
	public PageSet<MMaterial> getFixTaskPageSet(PageParam pageParam, String filterSort, String userTeam, String matQry) {
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MMaterial> list = mMaterialMapper.getFixTaskList(userTeam, matQry);
		PageInfo<MMaterial> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public List<MMaterial> getOldMatList(){
		return mMaterialMapper.getOldMatList();
	}

	@Override
	public List<MMaterial> getMaterialName(String matType, String query){
		return mMaterialMapper.getMaterialName(matType, query);
	}

	@Override
	public MMaterial getMatByCode(String matCode){
		return mMaterialMapper.getMatByCode(matCode);
	}
}
