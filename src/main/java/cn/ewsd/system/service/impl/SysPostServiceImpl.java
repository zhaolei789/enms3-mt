package cn.ewsd.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.ewsd.system.mapper.SysPostMapper;
import cn.ewsd.system.model.SysPost;
import cn.ewsd.system.service.SysPostService;
import cn.ewsd.system.service.impl.SystemBaseServiceImpl;

/**
 *
 */
@Service("sysPostServiceImpl")
public class SysPostServiceImpl extends SystemBaseServiceImpl<SysPost, String> implements SysPostService {
	@Autowired
	private SysPostMapper sysPostMapper;

    @Override
    public PageSet<SysPost> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<SysPost> list = sysPostMapper.getPageSet(filterSort);
        PageInfo<SysPost> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public SysPost queryObject(String uuid){
		return sysPostMapper.queryObject(uuid);
	}

	@Override
	public List<SysPost> queryList(Map<String, Object> map){
		return sysPostMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return sysPostMapper.queryTotal(map);
	}

	@Override
	public int executeSave(SysPost sysPost){
		return sysPostMapper.executeSave(sysPost);
	}

	@Override
	public int executeUpdate(SysPost sysPost){
		return sysPostMapper.executeUpdate(sysPost);
	}

	@Override
	public int executeDelete(String uuid){
		return sysPostMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return sysPostMapper.executeDeleteBatch(uuids);
	}

	@Override
	public int queryTotalByFS(String filterSort) {
		return sysPostMapper.queryTotalByFS(filterSort);
	}

	@Override
	public List<SysPost> queryListByFS(String filterSort) {
		return sysPostMapper.getPageSet(filterSort);
	}

}
