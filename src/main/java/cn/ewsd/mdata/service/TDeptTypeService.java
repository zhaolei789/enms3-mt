package cn.ewsd.mdata.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.mdata.model.TdeptType;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-20 11:40:20
 */
public interface TDeptTypeService extends MdataBaseService<TdeptType, String> {

    PageSet<TdeptType> getPageSet(PageParam pageParam, String filterSort);

	TdeptType queryObject(String uuid);
	
	List<TdeptType> queryList(Map<String, Object> map);

	List<TdeptType> getListByDeptId(String deptId);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(TdeptType tDeptType);
	
	int executeUpdate(TdeptType tDeptType);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);

	int queryByDeptIdAndDictKey(String deptId, String dictKey);
}
