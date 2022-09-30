package cn.ewsd.mdata.service.impl;

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

import cn.ewsd.mdata.mapper.SysUserSignMapper;
import cn.ewsd.mdata.model.SysUserSign;
import cn.ewsd.mdata.service.SysUserSignService;
import cn.ewsd.mdata.service.impl.MdataBaseServiceImpl;

@Service("sysUserSignServiceImpl")
public class SysUserSignServiceImpl extends MdataBaseServiceImpl<SysUserSign, String> implements SysUserSignService {
	@Autowired
	private SysUserSignMapper sysUserSignMapper;

    @Override
    public PageSet<SysUserSign> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<SysUserSign> list = sysUserSignMapper.getPageSet(filterSort);
        PageInfo<SysUserSign> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public SysUserSign queryObject(String uuid){
		return sysUserSignMapper.queryObject(uuid);
	}

	@Override
	public List<SysUserSign> queryList(Map<String, Object> map){
		return sysUserSignMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return sysUserSignMapper.queryTotal(map);
	}

	@Override
	public int executeSave(SysUserSign sysUserSign){
		return sysUserSignMapper.executeSave(sysUserSign);
	}

	@Override
	public int executeUpdate(SysUserSign sysUserSign){
		return sysUserSignMapper.executeUpdate(sysUserSign);
	}

	@Override
	public int executeDelete(String uuid){
		return sysUserSignMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return sysUserSignMapper.executeDeleteBatch(uuids);
	}

	@Override
	public int setAllStatus0(String userId) {
		return sysUserSignMapper.setAllStatus0(userId);
	}

	@Override
	public SysUserSign getUserSign(String userId) {
		return sysUserSignMapper.getUserSign(userId);
	}

}
