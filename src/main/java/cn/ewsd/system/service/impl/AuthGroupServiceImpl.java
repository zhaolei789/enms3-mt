package cn.ewsd.system.service.impl;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.system.mapper.AuthGroupMapper;
import cn.ewsd.system.model.AuthGroup;
import cn.ewsd.system.service.AuthGroupService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked"})
@Service("authGroupServiceImpl")
public class AuthGroupServiceImpl extends SystemBaseServiceImpl<AuthGroup, String> implements AuthGroupService {

	@Autowired
	private AuthGroupMapper authGroupMapper;

//	@Resource
//	private SystemHBaseDao<AuthGroup, String> groupDao;
	
//	@Resource
//	public void setSystemBaseDao(ISystemBaseDao<AuthGroup, String> groupDao) {
//		super.setBaseDao(groupDao);
//	}
	
	public AuthGroup group;


	@Override
	public AuthGroup getListByGroupStr(String groupStr) {
		List<String> list = new ArrayList<String>();
		String d[] = groupStr.split(",");
		for (int i = 0; i < d.length; i++) {
			list.add(d[i]);
		}
		return authGroupMapper.getListByGroupStr(list);
	}

	@Override
	public List<AuthGroup> getListByUuid(String uuid) {
		List<String> list = new ArrayList<String>();
		String d[] = uuid.split(",");
		for (int i = 0; i < d.length; i++) {
			list.add(d[i]);
		}
		return authGroupMapper.getListByUuid(list);
	}

	@Override
	public PageSet<AuthGroup> getPageSet(PageParam pageParam, String filterSort) {
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<AuthGroup> list = authGroupMapper.getPageSet(filterSort);
		PageInfo<AuthGroup> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}

	@Override
	public Integer getMaxById() {
		return authGroupMapper.getMaxById();
	}

	@Override
	public List<AuthGroup> getListBySort() {
		return authGroupMapper.getListBySort();
	}

	@Override
	public List<AuthGroup> getListByLevelId(String levelId) {
		return authGroupMapper.getListByLevelId(levelId);
	}

	@Override
	public List<AuthGroup> getListByCodeSetIdLevelId(String codeSetId, String levelId) {
		return authGroupMapper.getListByCodeSetIdLevelId(codeSetId,levelId);
	}

	@Override
	public List<AuthGroup> getListByPid(String pid) {
		return authGroupMapper.getListByPid(pid);
	}

}
