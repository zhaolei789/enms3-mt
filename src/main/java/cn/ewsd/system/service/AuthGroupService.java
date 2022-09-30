package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.AuthGroup;

import java.util.List;

public interface AuthGroupService extends SystemBaseService<AuthGroup, String> {

    AuthGroup getListByGroupStr(String groupStr);

    List<AuthGroup> getListByUuid(String uuid);

	PageSet<AuthGroup> getPageSet(PageParam pageParam, String filterSort);

	Integer getMaxById();

	List<AuthGroup> getListBySort();

	List<AuthGroup> getListByLevelId(String levelId);

	List<AuthGroup> getListByCodeSetIdLevelId(String codeSetId, String levelId);

	List<AuthGroup> getListByPid(String pid);
}