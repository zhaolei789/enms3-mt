package cn.ewsd.cost.service.impl;

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

import cn.ewsd.cost.mapper.SysUserLikeMenuMapper;
import cn.ewsd.cost.model.SysUserLikeMenu;
import cn.ewsd.cost.service.SysUserLikeMenuService;
import cn.ewsd.cost.service.impl.CostBaseServiceImpl;

@Service("sysUserLikeMenuServiceImpl")
public class SysUserLikeMenuServiceImpl extends CostBaseServiceImpl<SysUserLikeMenu, String> implements SysUserLikeMenuService {
	@Autowired
	private SysUserLikeMenuMapper sysUserLikeMenuMapper;

    @Override
    public PageSet<SysUserLikeMenu> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<SysUserLikeMenu> list = sysUserLikeMenuMapper.getPageSet(filterSort);
        PageInfo<SysUserLikeMenu> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public SysUserLikeMenu queryObject(String uuid){
		return sysUserLikeMenuMapper.queryObject(uuid);
	}

	@Override
	public List<SysUserLikeMenu> queryList(Map<String, Object> map){
		return sysUserLikeMenuMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return sysUserLikeMenuMapper.queryTotal(map);
	}

	@Override
	public int executeSave(SysUserLikeMenu sysUserLikeMenu){
		return sysUserLikeMenuMapper.executeSave(sysUserLikeMenu);
	}

	@Override
	public int executeUpdate(SysUserLikeMenu sysUserLikeMenu){
		return sysUserLikeMenuMapper.executeUpdate(sysUserLikeMenu);
	}

	@Override
	public int executeDelete(String uuid){
		return sysUserLikeMenuMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return sysUserLikeMenuMapper.executeDeleteBatch(uuids);
	}

	@Override
	public SysUserLikeMenu queryByUserIdAndUri(String user_id, String menu_uri) {
		return sysUserLikeMenuMapper.queryByUserIdAndUri(user_id,menu_uri);
	}

	@Override
	public int executeAddClicksNumber(String user_id, String menu_uri) {
		return sysUserLikeMenuMapper.executeAddClicksNumber(user_id,menu_uri);
	}

	@Override
	public int queryTotalByUser(String user_id) {
		return sysUserLikeMenuMapper.queryTotalByUser(user_id);
	}

	@Override
	public List<SysUserLikeMenu> queryListByUser(String user_id) {
		return sysUserLikeMenuMapper.queryListByUser(user_id);
	}


}
