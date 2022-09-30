package cn.ewsd.mdata.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.mdata.model.Organization;
import cn.ewsd.mdata.model.SysUserPost;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Author zhaoxiace
 * @Email zhaoxiace@ewsd.cn
 * @Date 2019-05-06 14:04:50
 */
public interface SysUserPostService extends MdataBaseService<SysUserPost, String> {

    PageSet<SysUserPost> getPageSet(PageParam pageParam, String filterSort, String userNameId);

	SysUserPost queryObject(String uuid);
	
	List<SysUserPost> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(SysUserPost sysUserPost);
	
	int executeUpdate(SysUserPost sysUserPost);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);

    List<Organization> getGroupConcat(String[] ids);

	boolean isExistence(String userNameId, String post, String orgId);

	String getPostText(String post);
}
