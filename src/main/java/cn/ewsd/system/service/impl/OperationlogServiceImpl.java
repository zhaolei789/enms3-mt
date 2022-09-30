package cn.ewsd.system.service.impl;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.system.mapper.OperationlogMapper;
import cn.ewsd.system.model.Operationlog;
import cn.ewsd.system.service.OperationlogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("operationlogServiceImpl")
public class OperationlogServiceImpl extends SystemBaseServiceImpl<Operationlog, String> implements OperationlogService {

	@Resource
	private OperationlogMapper operationlogMapper;

	@Override
	public PageSet<Operationlog> getPageSet(PageParam pageParam, String filterSort) {
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<Operationlog> list = operationlogMapper.getPageSet(filterSort);
		PageInfo<Operationlog> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}
}
