package cn.ewsd.system.service.impl;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.system.mapper.RemindMapper;
import cn.ewsd.system.model.Remind;
import cn.ewsd.system.service.RemindService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("remindServiceImpl")
public class RemindServiceImpl extends SystemBaseServiceImpl<Remind, String> implements RemindService {

	@Resource
	private RemindMapper remindMapper;
	

	@Override
	public PageSet<Remind> getPageSet(PageParam pageParam, String filterSort) {
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<Remind> list = remindMapper.getPageSet(filterSort);
		PageInfo<Remind> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}
}
