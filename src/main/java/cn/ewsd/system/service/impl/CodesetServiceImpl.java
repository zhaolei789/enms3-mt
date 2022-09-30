package cn.ewsd.system.service.impl;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.system.mapper.CodesetMapper;
import cn.ewsd.system.model.Codeset;
import cn.ewsd.system.service.CodesetService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@SuppressWarnings({"unchecked"})
@Service("codesetService")
public class CodesetServiceImpl extends SystemBaseServiceImpl<Codeset, String> implements CodesetService {

	@Resource
	private CodesetMapper codesetMapper;
	


	@Override
	public PageSet<Codeset> getPageSet(PageParam pageParam, String filterSort) {
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<Codeset> list = codesetMapper.getPageSet(filterSort);
		PageInfo<Codeset> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}
}
