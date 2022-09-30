package cn.ewsd.system.service.impl;

import cn.ewsd.common.service.impl.MybatisBaseServiceImpl;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.system.mapper.SysCategoryMapper;
import cn.ewsd.system.model.SysCategory;
import cn.ewsd.system.service.SysCategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("sysCategoryServiceImpl")
public class SysCategoryServiceImpl extends MybatisBaseServiceImpl<SysCategory, String> implements SysCategoryService {
	@Autowired
	private SysCategoryMapper sysCategoryMapper;

    @Override
    public PageSet<SysCategory> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<SysCategory> list = sysCategoryMapper.getPageSet(filterSort);
        PageInfo<SysCategory> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public SysCategory queryObject(String uuid){
		return sysCategoryMapper.queryObject(uuid);
	}

	@Override
	public List<SysCategory> queryList(Map<String, Object> map){
		return sysCategoryMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return sysCategoryMapper.queryTotal(map);
	}

	@Override
	public int executeSave(SysCategory sysCategory){
		return sysCategoryMapper.executeSave(sysCategory);
	}

	@Override
	public int executeUpdate(SysCategory sysCategory){
		return sysCategoryMapper.executeUpdate(sysCategory);
	}

	@Override
	public int executeDelete(String uuid){
		return sysCategoryMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return sysCategoryMapper.executeDeleteBatch(uuids);
	}

	@Override
	public List<SysCategory> getCategorysByTypeAndLevelId(String levelId, String type) {
		return sysCategoryMapper.getCategorysByTypeAndLevelId(levelId,type);
	}

	@Override
	public List<SysCategory> selectListByPid(String pid, String type) {
		return sysCategoryMapper.selectListByPid(pid,type);
	}

	@Override
	public int getIncreasementId() {
		return sysCategoryMapper.getIncreasementId();
	}

	@Override
	public String getChildIds(Map map) {
		return sysCategoryMapper.getChildIds(map);
	}

	@Override
	public String getFatherId(Map map) {
		return sysCategoryMapper.getFatherId(map);
	}

	@Override
	public List<SysCategory> getCategorysByPortalDisplayAndTypeAndLevelId(String levelId, String type) {
		return sysCategoryMapper.getCategorysByPortalDisplayAndTypeAndLevelId(levelId,type);
	}

}
