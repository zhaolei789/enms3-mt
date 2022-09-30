package cn.ewsd.logistics.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.ewsd.logistics.mapper.SysSequenceMapper;
import cn.ewsd.logistics.model.SysSequence;
import cn.ewsd.logistics.service.SysSequenceService;
import cn.ewsd.logistics.service.impl.LogisticsBaseServiceImpl;

@Service("sysSequenceServiceImpl")
public class SysSequenceServiceImpl extends LogisticsBaseServiceImpl<SysSequence, String> implements SysSequenceService {
	@Autowired
	private SysSequenceMapper sysSequenceMapper;

	//SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//SimpleDateFormat sdfD=new SimpleDateFormat("yyyyMMdd");

    @Override
    public PageSet<SysSequence> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<SysSequence> list = sysSequenceMapper.getPageSet(filterSort);
        PageInfo<SysSequence> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public SysSequence queryObject(String uuid){
		return sysSequenceMapper.queryObject(uuid);
	}

	@Override
	public List<SysSequence> queryList(Map<String, Object> map){
		return sysSequenceMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return sysSequenceMapper.queryTotal(map);
	}

	@Override
	public int executeSave(SysSequence sysSequence){
		return sysSequenceMapper.executeSave(sysSequence);
	}

	@Override
	public int executeUpdate(SysSequence sysSequence){
		return sysSequenceMapper.executeUpdate(sysSequence);
	}

	@Override
	public int executeDelete(String uuid){
		return sysSequenceMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return sysSequenceMapper.executeDeleteBatch(uuids);
	}

	@Override
	public SysSequence findSequence(String sqSystem, String sqModule, Integer sqYear, Integer sqMonth) {
		return sysSequenceMapper.findSequence(sqSystem,sqModule,sqYear,sqMonth);
	}

	@Override
	public int sequenceAddOne(String uuid) {
		return sysSequenceMapper.sequenceAddOne(uuid);
	}




}
