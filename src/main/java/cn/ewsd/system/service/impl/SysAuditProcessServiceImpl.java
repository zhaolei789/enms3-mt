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

import cn.ewsd.system.mapper.SysAuditProcessMapper;
import cn.ewsd.system.model.SysAuditProcess;
import cn.ewsd.system.service.SysAuditProcessService;
import cn.ewsd.system.service.impl.SystemBaseServiceImpl;

@Service("sysAuditProcessServiceImpl")
public class SysAuditProcessServiceImpl extends SystemBaseServiceImpl<SysAuditProcess, String> implements SysAuditProcessService {
	@Autowired
	private SysAuditProcessMapper sysAuditProcessMapper;

    @Override
    public PageSet<SysAuditProcess> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<SysAuditProcess> list = sysAuditProcessMapper.getPageSet(filterSort);
        PageInfo<SysAuditProcess> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public SysAuditProcess queryObject(String uuid){
		return sysAuditProcessMapper.queryObject(uuid);
	}

	@Override
	public List<SysAuditProcess> queryList(Map<String, Object> map){
		return sysAuditProcessMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return sysAuditProcessMapper.queryTotal(map);
	}

	@Override
	public int executeSave(SysAuditProcess sysAuditProcess){
    	return sysAuditProcessMapper.executeSave(sysAuditProcess);
	}

	@Override
	public int executeUpdate(SysAuditProcess sysAuditProcess){
		return sysAuditProcessMapper.executeUpdate(sysAuditProcess);
	}

	@Override
	public int executeDelete(String uuid){
		return sysAuditProcessMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		SysAuditProcess sysAuditProcess =  sysAuditProcessMapper.queryObject(uuids[0]);
		if(sysAuditProcess.getLevel()==2){
			sysAuditProcessMapper.executeUpdateInitState("1",sysAuditProcess.getFuuid());
		}
    	return sysAuditProcessMapper.executeDeleteBatch(uuids);
	}

	//====================================

	@Override
	public int executeResetInitStateBatch(String[] uuids){
		return sysAuditProcessMapper.executeResetInitStateBatch(uuids);
	}

	@Override
	public int executeUpdateInitState(String initState,String uuid){
		return sysAuditProcessMapper.executeUpdateInitState(initState,uuid);
	}

	@Override
	public int executeUpdateStep(String lastStep,String nextStep,String uuid){
		return sysAuditProcessMapper.executeUpdateStep(lastStep,nextStep,uuid);
	}

	@Override
	public List<SysAuditProcess> queryListByFuuid(String fuuid){
		return sysAuditProcessMapper.queryListByFuuid(fuuid);
	}

	@Override
	public List<SysAuditProcess> queryListByUuids(String[] uuids){
		return sysAuditProcessMapper.queryListByUuids(uuids);
	}

	@Override
	public SysAuditProcess queryObjectByProcessNo(String processNo){
		return sysAuditProcessMapper.queryObjectByProcessNo(processNo);
	}

	@Override
	public String getFirstStepNoByProcessNo(String processNo){
		SysAuditProcess process = sysAuditProcessMapper.queryObjectByProcessNo(processNo);
		if(process==null){return "null";}
		SysAuditProcess stepObj = sysAuditProcessMapper.queryFirstObjectByfuuid(process.getUuid());
		return stepObj.getUuid();
	}

	@Override
	public SysAuditProcess getFirstStepByProcessNo(String processNo) {
		SysAuditProcess process = sysAuditProcessMapper.queryObjectByProcessNo(processNo);
		if(process==null){return null;}
		SysAuditProcess stepObj = sysAuditProcessMapper.queryFirstObjectByfuuid(process.getUuid());
		return stepObj;
	}
}
