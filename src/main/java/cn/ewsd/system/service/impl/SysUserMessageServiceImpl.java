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

import cn.ewsd.system.mapper.SysUserMessageMapper;
import cn.ewsd.system.model.SysUserMessage;
import cn.ewsd.system.service.SysUserMessageService;
import cn.ewsd.system.service.impl.SystemBaseServiceImpl;

@Service("sysUserMessageServiceImpl")
public class SysUserMessageServiceImpl extends SystemBaseServiceImpl<SysUserMessage, String> implements SysUserMessageService {
	@Autowired
	private SysUserMessageMapper sysUserMessageMapper;

    @Override
    public PageSet<SysUserMessage> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<SysUserMessage> list = sysUserMessageMapper.getPageSet(filterSort);
        PageInfo<SysUserMessage> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public SysUserMessage queryObject(String uuid){
		return sysUserMessageMapper.queryObject(uuid);
	}

	@Override
	public List<SysUserMessage> queryList(Map<String, Object> map){
		return sysUserMessageMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return sysUserMessageMapper.queryTotal(map);
	}

	@Override
	public int executeSave(SysUserMessage sysUserMessage){
		return sysUserMessageMapper.executeSave(sysUserMessage);
	}

	@Override
	public int executeUpdate(SysUserMessage sysUserMessage){
		return sysUserMessageMapper.executeUpdate(sysUserMessage);
	}

	@Override
	public int executeDelete(String uuid){
		return sysUserMessageMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return sysUserMessageMapper.executeDeleteBatch(uuids);
	}

	@Override
	public int queryNotReadNum(String user_uuid) {
		return sysUserMessageMapper.queryNotReadNum(user_uuid);
	}

	@Override
	public int executeReadBatch(String[] uuids) {
		return sysUserMessageMapper.executeReadBatch(uuids);
	}

	@Override
	public List<SysUserMessage> getListByFilterSort(String filterSort) {
		return sysUserMessageMapper.getPageSet(filterSort);
	}

}
