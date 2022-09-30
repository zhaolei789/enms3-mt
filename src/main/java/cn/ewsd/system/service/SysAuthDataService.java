package cn.ewsd.system.service;

import cn.ewsd.common.service.IMybatisBaseService;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.SysAuthData;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-07-02 08:49:48
 */
public interface SysAuthDataService extends IMybatisBaseService<SysAuthData, String> {

    PageSet<SysAuthData> getPageSet(PageParam pageParam, String filterSort);

	SysAuthData queryObject(String uuid);
	
	List<SysAuthData> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(SysAuthData sysAuthData);
	
	int executeUpdate(SysAuthData sysAuthData);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);

	List<SysAuthData> getListByLevelId(String levelId);

	List<SysAuthData> getListByPid(String pid);

	int getIncreasementId();

	List<SysAuthData> getLisById(String filter);

    SysAuthData getListById(String dataAuth);

}
