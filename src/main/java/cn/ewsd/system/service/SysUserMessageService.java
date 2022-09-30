package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.SysUserMessage;


import java.util.List;
import java.util.Map;

/**
 * 消息
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-10-21 13:58:38
 */
public interface SysUserMessageService extends SystemBaseService<SysUserMessage, String> {

    PageSet<SysUserMessage> getPageSet(PageParam pageParam, String filterSort);

	SysUserMessage queryObject(String uuid);
	
	List<SysUserMessage> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(SysUserMessage sysUserMessage);
	
	int executeUpdate(SysUserMessage sysUserMessage);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);

	int queryNotReadNum(String user_uuid);

	int executeReadBatch(String[] uuids);

	List<SysUserMessage> getListByFilterSort(String filterSort);
}
