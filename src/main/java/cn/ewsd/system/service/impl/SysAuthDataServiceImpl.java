package cn.ewsd.system.service.impl;

import cn.ewsd.common.service.impl.MybatisBaseServiceImpl;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.system.mapper.SysAuthDataMapper;
import cn.ewsd.system.model.SysAuthData;
import cn.ewsd.system.service.SysAuthDataService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("sysAuthDataServiceImpl")
public class SysAuthDataServiceImpl extends MybatisBaseServiceImpl<SysAuthData, String> implements SysAuthDataService {
	@Autowired
	private SysAuthDataMapper sysAuthDataMapper;

    @Override
    public PageSet<SysAuthData> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<SysAuthData> list = sysAuthDataMapper.getPageSet(filterSort);
        PageInfo<SysAuthData> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public SysAuthData queryObject(String uuid){
		return sysAuthDataMapper.queryObject(uuid);
	}

	@Override
	public List<SysAuthData> queryList(Map<String, Object> map){
		return sysAuthDataMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return sysAuthDataMapper.queryTotal(map);
	}

	@Override
	public int executeSave(SysAuthData sysAuthData){
		return sysAuthDataMapper.executeSave(sysAuthData);
	}

	@Override
	public int executeUpdate(SysAuthData sysAuthData){
		return sysAuthDataMapper.executeUpdate(sysAuthData);
	}

	@Override
	public int executeDelete(String uuid){
		return sysAuthDataMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return sysAuthDataMapper.executeDeleteBatch(uuids);
	}

	@Override
	public List<SysAuthData> getListByLevelId(String levelId) {
		return sysAuthDataMapper.getListByLevelId(levelId);
	}

	@Override
	public List<SysAuthData> getListByPid(String pid) {
		return sysAuthDataMapper.getListByPid(pid);
	}

	@Override
	public int getIncreasementId() {
		return sysAuthDataMapper.getIncreasementId();
	}

	@Override
	public List<SysAuthData> getLisById(String filter) {
		return sysAuthDataMapper.getLisById(filter);
	}

	@Override
	public SysAuthData getListById(String dataAuth) {
		return sysAuthDataMapper.getListById(dataAuth);
	}

}
