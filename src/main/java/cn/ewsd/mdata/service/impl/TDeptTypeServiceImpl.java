package cn.ewsd.mdata.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.ewsd.mdata.mapper.TDeptTypeMapper;
import cn.ewsd.mdata.model.TdeptType;
import cn.ewsd.mdata.service.TDeptTypeService;

@Service("tDeptTypeServiceImpl")
public class TDeptTypeServiceImpl extends MdataBaseServiceImpl<TdeptType, String> implements TDeptTypeService {
	@Autowired
	private TDeptTypeMapper tDeptTypeMapper;

    @Override
    public PageSet<TdeptType> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<TdeptType> list = tDeptTypeMapper.getPageSet(filterSort);
        PageInfo<TdeptType> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
	public List<TdeptType> getListByDeptId(String deptId){
    	return tDeptTypeMapper.getListByDeptId(deptId);
	}

	@Override
	public TdeptType queryObject(String uuid){
		return tDeptTypeMapper.queryObject(uuid);
	}

	@Override
	public List<TdeptType> queryList(Map<String, Object> map){
		return tDeptTypeMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return tDeptTypeMapper.queryTotal(map);
	}

	@Override
	public int executeSave(TdeptType tDeptType){
		return tDeptTypeMapper.executeSave(tDeptType);
	}

	@Override
	public int executeUpdate(TdeptType tDeptType){
		return tDeptTypeMapper.executeUpdate(tDeptType);
	}

	@Override
	public int executeDelete(String uuid){
		return tDeptTypeMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return tDeptTypeMapper.executeDeleteBatch(uuids);
	}

	@Override
	public int queryByDeptIdAndDictKey(String deptId, String dictKey){
    	return tDeptTypeMapper.queryByDeptIdAndDictKey(deptId, dictKey);
	}
}
