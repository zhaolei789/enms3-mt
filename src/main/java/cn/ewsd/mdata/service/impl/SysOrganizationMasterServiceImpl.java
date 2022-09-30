package cn.ewsd.mdata.service.impl;

import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.cost.mapper.SysUserBarMasterMapper;
import cn.ewsd.cost.model.SysUserBarMaster;
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

import cn.ewsd.mdata.mapper.SysOrganizationMasterMapper;
import cn.ewsd.mdata.model.SysOrganizationMaster;
import cn.ewsd.mdata.service.SysOrganizationMasterService;
import cn.ewsd.mdata.service.impl.MdataBaseServiceImpl;

@Service("sysOrganizationMasterServiceImpl")
public class SysOrganizationMasterServiceImpl extends MdataBaseServiceImpl<SysOrganizationMaster, String> implements SysOrganizationMasterService {
	@Autowired
	private SysOrganizationMasterMapper sysOrganizationMasterMapper;
	@Autowired
	private SysUserBarMasterMapper sysUserBarMasterMapper;

    @Override
    public PageSet<SysOrganizationMaster> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<SysOrganizationMaster> list = sysOrganizationMasterMapper.getPageSet(filterSort);
        PageInfo<SysOrganizationMaster> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public SysOrganizationMaster queryObject(String uuid){
		return sysOrganizationMasterMapper.queryObject(uuid);
	}

	@Override
	public List<SysOrganizationMaster> queryList(Map<String, Object> map){
		return sysOrganizationMasterMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return sysOrganizationMasterMapper.queryTotal(map);
	}

	@Override
	public int executeSave(SysOrganizationMaster sysOrganizationMaster){
		return sysOrganizationMasterMapper.executeSave(sysOrganizationMaster);
	}

	@Override
	public int executeUpdate(SysOrganizationMaster sysOrganizationMaster){
		return sysOrganizationMasterMapper.executeUpdate(sysOrganizationMaster);
	}

	@Override
	public int executeDelete(String uuid){
		return sysOrganizationMasterMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return sysOrganizationMasterMapper.executeDeleteBatch(uuids);
	}

	@Override
	public List<SysOrganizationMaster> queryListByIds(String[] uuids) {
		return sysOrganizationMasterMapper.queryListByIds(uuids);
	}

	@Override
	public int batchInsert(List<SysOrganizationMaster> list) {
		return sysOrganizationMasterMapper.batchInsert(list);
	}

	@Override
	public int executeDeleteByOrgBatch(String[] orgIds,String masterUuid) {
		return sysOrganizationMasterMapper.executeDeleteByOrgBatch(orgIds,masterUuid);
	}

	@Override
	public String getFgldAuditOrgs() {
    	String resStr = "";
		List<SysUserBarMaster> thisUserMasterList = sysUserBarMasterMapper.getPageSet(" user_id = '"+ LoginInfo.getUuid()+"' ");
		String masterArray [] = new String[thisUserMasterList.size()];
		if(thisUserMasterList==null||thisUserMasterList.size()<1){
			return "";
		}
		for (int i = 0; i < thisUserMasterList.size(); i++) {
			masterArray[i] = thisUserMasterList.get(i).getMasterId();
		}
		List<SysOrganizationMaster> sysOrganizationMasterList = sysOrganizationMasterMapper.queryListByMasterIds(masterArray);
		if(sysOrganizationMasterList==null||sysOrganizationMasterList.size()<1){
			return "";
		}
		for (int i = 0; i < sysOrganizationMasterList.size(); i++) {
			if(resStr.equals("")){
				resStr = sysOrganizationMasterList.get(i).getOrgId()+"";
			}else {
				resStr += ","+sysOrganizationMasterList.get(i).getOrgId();
			}
		}
		return resStr;
	}

	public String getFgldAuditOrgs(UserInfo userInfo) {
		String resStr = "";
		List<SysUserBarMaster> thisUserMasterList = sysUserBarMasterMapper.getPageSet(" user_id = '"+ userInfo.getUuid()+"' ");
		String masterArray [] = new String[thisUserMasterList.size()];
		if(thisUserMasterList==null||thisUserMasterList.size()<1){
			return "";
		}
		for (int i = 0; i < thisUserMasterList.size(); i++) {
			masterArray[i] = thisUserMasterList.get(i).getMasterId();
		}
		List<SysOrganizationMaster> sysOrganizationMasterList = sysOrganizationMasterMapper.queryListByMasterIds(masterArray);
		if(sysOrganizationMasterList==null||sysOrganizationMasterList.size()<1){
			return "";
		}
		for (int i = 0; i < sysOrganizationMasterList.size(); i++) {
			if(resStr.equals("")){
				resStr = sysOrganizationMasterList.get(i).getOrgId()+"";
			}else {
				resStr += ","+sysOrganizationMasterList.get(i).getOrgId();
			}
		}
		return resStr;
	}

	@Override
	public List<SysOrganizationMaster> queryListByMasterIds(String[] masterIds) {
		return sysOrganizationMasterMapper.queryListByMasterIds(masterIds);
	}

	@Override
	public List<SysOrganizationMaster> queryListByFS(String filterSort) {
		return sysOrganizationMasterMapper.getPageSet(filterSort);
	}

}
