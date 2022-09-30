package cn.ewsd.mdata.service.impl;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.mdata.mapper.SysUserPostMapper;
import cn.ewsd.mdata.model.Organization;
import cn.ewsd.mdata.model.SysUserPost;
import cn.ewsd.mdata.service.SysUserPostService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("sysUserPostServiceImpl")
public class SysUserPostServiceImpl extends MdataBaseServiceImpl<SysUserPost, String> implements SysUserPostService {
	@Autowired
	private SysUserPostMapper sysUserPostMapper;

    @Override
    public PageSet<SysUserPost> getPageSet(PageParam pageParam, String filterSort,String userNameId) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<SysUserPost> list = sysUserPostMapper.getPageSet(filterSort,userNameId);
        PageInfo<SysUserPost> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public SysUserPost queryObject(String uuid){
		return sysUserPostMapper.queryObject(uuid);
	}

	@Override
	public List<SysUserPost> queryList(Map<String, Object> map){
		return sysUserPostMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return sysUserPostMapper.queryTotal(map);
	}

	@Override
	public int executeSave(SysUserPost sysUserPost){
		return sysUserPostMapper.executeSave(sysUserPost);
	}

	@Override
	public int executeUpdate(SysUserPost sysUserPost){
		return sysUserPostMapper.executeUpdate(sysUserPost);
	}

	@Override
	public int executeDelete(String uuid){
		return sysUserPostMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return sysUserPostMapper.executeDeleteBatch(uuids);
	}

	@Override
	public List<Organization> getGroupConcat(String[] ids) {
		return sysUserPostMapper.getGroupConcat(ids);
	}

    @Override
    public boolean isExistence(String userNameId, String post, String orgId) {
        return sysUserPostMapper.isExistence(userNameId,post,orgId)>0?true:false;

    }

	@Override
	public String getPostText(String post) {
		return sysUserPostMapper.getPostText(post);
	}

}
