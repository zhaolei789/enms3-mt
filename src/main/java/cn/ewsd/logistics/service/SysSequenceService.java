package cn.ewsd.logistics.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.logistics.model.SysSequence;
import cn.ewsd.logistics.service.LogisticsBaseService;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-07-27 09:52:49
 */
public interface SysSequenceService extends LogisticsBaseService<SysSequence, String> {


    PageSet<SysSequence> getPageSet(PageParam pageParam, String filterSort);

	SysSequence queryObject(String uuid);
	
	List<SysSequence> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(SysSequence sysSequence);
	
	int executeUpdate(SysSequence sysSequence);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);

	SysSequence findSequence(String sqSystem,String sqModule,Integer sqYear,Integer sqMonth);

	int sequenceAddOne(String uuid);


}
