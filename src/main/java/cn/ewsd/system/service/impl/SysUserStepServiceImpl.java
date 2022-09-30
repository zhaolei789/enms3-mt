package cn.ewsd.system.service.impl;

import cn.ewsd.mdata.mapper.OrganizationMapper;
import cn.ewsd.system.mapper.SysAuditProcessMapper;
import cn.ewsd.system.model.SysAuditProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.ewsd.system.mapper.SysUserStepMapper;
import cn.ewsd.system.model.SysUserStep;
import cn.ewsd.system.service.SysUserStepService;
import cn.ewsd.system.service.impl.SystemBaseServiceImpl;

@Service("sysUserStepServiceImpl")
public class SysUserStepServiceImpl extends SystemBaseServiceImpl<SysUserStep, String> implements SysUserStepService {
	@Autowired
	private SysUserStepMapper sysUserStepMapper;
	@Autowired
	private SysAuditProcessMapper sysAuditProcessMapper;
	@Autowired
	private OrganizationMapper organizationMapper;

    @Override
    public PageSet<SysUserStep> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<SysUserStep> list = sysUserStepMapper.getPageSet(filterSort);
        PageInfo<SysUserStep> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public SysUserStep queryObject(String uuid){
		return sysUserStepMapper.queryObject(uuid);
	}

	@Override
	public List<SysUserStep> queryList(Map<String, Object> map){
		return sysUserStepMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return sysUserStepMapper.queryTotal(map);
	}

	@Override
	public int executeSave(SysUserStep sysUserStep){
		return sysUserStepMapper.executeSave(sysUserStep);
	}

	@Override
	public int executeUpdate(SysUserStep sysUserStep){
		return sysUserStepMapper.executeUpdate(sysUserStep);
	}

	@Override
	public int executeDelete(String uuid){
		return sysUserStepMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return sysUserStepMapper.executeDeleteBatch(uuids);
	}


	/**
	 * 查找用户审批步骤权限(返回是否)
	 * @param stepId 步骤UUID
	 * @param userId 用户UUID
	 * @param orgId 部门ID,可不传,不传时不校验部门
	 * @return
	 */
	@Override
	public boolean checkUserHavePms(String stepId,String userId,String orgId){
    	boolean flag = false;
    	if(orgId==null||orgId.equals("")||orgId.equals("null")||orgId.equals("undefined")){
			orgId = null;
		}
    	int checkNum = sysUserStepMapper.checkUserHavePms(stepId,userId,orgId);
    	if(checkNum>0){flag = true;}
		return flag;
	}

	/**
	 * 查找用户审批步骤权限部门(返回部门id数组)
	 * @param stepId 步骤UUID
	 * @param userId 用户UUID
	 * @return
	 */
	@Override
	public String selectUserPmsOrg(String stepId,String userId){
		List<SysUserStep> userStepList = sysUserStepMapper.queryListByUserAndStep(stepId,userId);
		LinkedHashSet<String> orgHashSet = new LinkedHashSet<>();
		for (int i = 0; i < userStepList.size(); i++) {
			if(userStepList.get(i).getAuditOrg()!=null&&userStepList.get(i).getAuditOrg()!=99999) {
				orgHashSet.add(userStepList.get(i).getAuditOrg().toString());
				//加入子集
				Map map = new HashMap();
				map.put("p1", "sys_organization");
				map.put("p2", userStepList.get(i).getAuditOrg());
				map.put("p3", 1);
				map.put("p4", "idStr");
				organizationMapper.getChildIds(map);
				String ChildIds = map.get("p3").toString();
				if(ChildIds!=null&&!"".equals(ChildIds)){
					String childsArray [] = ChildIds.split(",");
					for (int j = 0; j < childsArray.length; j++) {
						orgHashSet.add(childsArray[j]+"");
					}
				}
			}
		}
		return orgHashSet.toString().replace(" ","").replace("[","").replace("]","");
	}

	@Override
	public String selectUserStepSqlByProcessNo(String processNo,String userId){
		SysAuditProcess sysAuditProcess = sysAuditProcessMapper.queryObjectByProcessNo(processNo);
		List<SysUserStep> userStepList = sysUserStepMapper.queryListByUserAndProcessUuid(sysAuditProcess.getUuid(),userId);
		LinkedHashSet<String> stepHashSet = new LinkedHashSet<>();
		for (int i = 0; i < userStepList.size(); i++) {
			stepHashSet.add("'"+userStepList.get(i).getStepUuid()+"'");
		}
		return stepHashSet.toString().replace(" ","").replace("[","(").replace("]",")");
	}


	public List<String> selectUserStepListByProcessNo(String processNo,String userId){
		SysAuditProcess sysAuditProcess = sysAuditProcessMapper.queryObjectByProcessNo(processNo);
		List<SysUserStep> userStepList = sysUserStepMapper.queryListByUserAndProcessUuid(sysAuditProcess.getUuid(),userId);
		List<String> resList = new ArrayList<>();
		for (int i = 0; i < userStepList.size(); i++) {
			if(!resList.contains(userStepList.get(i).getStepUuid())){
				resList.add(userStepList.get(i).getStepUuid());
			}
		}
		return resList;
	}

	@Override
	public List<SysUserStep> queryListByFS(String filterSort) {
		return sysUserStepMapper.getPageSet(filterSort);
	}

	@Override
	public List<SysUserStep> queryListByStepGroupUser(String stepId) {
		return sysUserStepMapper.queryListByStepGroupUser(stepId);
	}

	@Override
	public List<SysUserStep> queryListByStepAndOrgGroupUser(String stepUuid, Integer auditOrg) {
		return sysUserStepMapper.queryListByStepAndOrgGroupUser(stepUuid, auditOrg);
	}
}
