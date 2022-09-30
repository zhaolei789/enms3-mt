package cn.ewsd.mdata.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.mdata.model.SysUserSign;
import cn.ewsd.mdata.service.MdataBaseService;

import java.util.List;
import java.util.Map;

/**
 * 用户签名
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2022-05-06 15:43:29
 */
public interface SysUserSignService extends MdataBaseService<SysUserSign, String> {

    PageSet<SysUserSign> getPageSet(PageParam pageParam, String filterSort);

	SysUserSign queryObject(String uuid);
	
	List<SysUserSign> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(SysUserSign sysUserSign);
	
	int executeUpdate(SysUserSign sysUserSign);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);

	int setAllStatus0(String userId);

	SysUserSign getUserSign(String userId);
}
