package cn.ewsd.material.service.impl;

import cn.ewsd.mdata.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.ewsd.material.mapper.MMatTypeMapper;
import cn.ewsd.material.model.MMatType;
import cn.ewsd.material.service.MMatTypeService;
import org.springframework.transaction.annotation.Transactional;

@Service("mMatTypeServiceImpl")
public class MMatTypeServiceImpl extends MaterialBaseServiceImpl<MMatType, String> implements MMatTypeService {
	@Autowired
	private MMatTypeMapper mMatTypeMapper;
	@Autowired
	private UtilService utilService;

	@Override
	public PageSet<MMatType> getPageSet(PageParam pageParam, String filterSort) {
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MMatType> list = mMatTypeMapper.getPageSet(filterSort);
		PageInfo<MMatType> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public String checkTypeCode(String typeCode, String uuid){
		int typeCodeLength = typeCode.length();

		ArrayList<String[]> list = new ArrayList<String[]>();
		list.add(new String[]{"type_code", "=", typeCode});
		if(uuid != null){
			list.add(new String[]{"uuid", "!=", uuid});
		}

		boolean selfExist = utilService.checkColumnDataExist("m_mat_type", list);
		if(selfExist){
			return "分类编码重复！";
		}

		if(typeCode.length()>2) {
			list.set(0, new String[]{"type_code", "=", typeCodeLength == 4 ? typeCode.substring(0, 2) : typeCode.substring(0, 4)});
			boolean parentExist = typeCodeLength == 2 ? true : utilService.checkColumnDataExist("m_mat_type", list);
			if (!parentExist) {
				return "未找到父分类！";
			}
		}

		return "";
	}

	@Override
	public int executeSave(MMatType mMatType){
		return mMatTypeMapper.executeSave(mMatType);
	}

	@Override
	public MMatType queryObject(String uuid){
		return mMatTypeMapper.queryObject(uuid);
	}

	@Override
	public List<MMatType> getMatTypeForSelect(){
		return mMatTypeMapper.getMatTypeForSelect();
	}

	@Override
	public int executeUpdate(MMatType mMatType){
		return mMatTypeMapper.executeUpdate(mMatType);
	}

	@Override
	public int getMatCountByMatType(String typeCode){
		return mMatTypeMapper.getMatCountByMatType(typeCode);
	}

	@Override
	@Transactional
	public boolean checkDeleteBatch(String[] uuids){
		boolean flag = true;
		for (int i = 0; i < uuids.length; i++) {
			int count = getMatCountByMatType(queryObject(uuids[i]).getTypeCode());
			if(count>0){
				flag=false;
				break;
			}
		}
		return flag;
	}

	@Override
	public List<MMatType> getMatTypeByFS(String filterSort) {
		return mMatTypeMapper.getPageSet(filterSort);
	}

	@Override
	public MMatType getMatTypeByCode(String typeCode) {
		return mMatTypeMapper.getMatTypeByCode(typeCode);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return mMatTypeMapper.executeDeleteBatch(uuids);
	}
}
