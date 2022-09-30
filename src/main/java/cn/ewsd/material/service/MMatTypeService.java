package cn.ewsd.material.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MMatType;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-21 11:04:46
 */
public interface MMatTypeService extends MaterialBaseService<MMatType, String> {
	List<MMatType> getMatTypeForSelect();

	PageSet<MMatType> getPageSet(PageParam pageParam, String filterSort);

	String checkTypeCode(String typeCode, String uuid);

	int executeSave(MMatType mMatType);

	MMatType queryObject(String uuid);

	int executeUpdate(MMatType mMatType);
	
	int executeDeleteBatch(String[] uuids);

	int getMatCountByMatType(String typeCode);

	boolean checkDeleteBatch(String[] uuid);

	List<MMatType> getMatTypeByFS(String filterSort);

	MMatType getMatTypeByCode(String typeCode);
}
