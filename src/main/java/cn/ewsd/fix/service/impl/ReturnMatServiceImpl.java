package cn.ewsd.fix.service.impl;

import cn.ewsd.base.utils.XDate;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.fix.model.MBackPlan;
import cn.ewsd.fix.service.ReturnMatService;
import cn.ewsd.material.mapper.MMaterialMapper;
import cn.ewsd.material.model.MMaterial;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("returnMatServiceImpl")
public class ReturnMatServiceImpl extends FixBaseServiceImpl<MBackPlan, String> implements ReturnMatService {
	@Autowired
	private MMaterialMapper mMaterialMapper;

	@Override
	public PageSet<MMaterial> getPageSet(PageParam pageParam, String filterSort, String month, String teamQry, String matQry) {
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<MMaterial> list = mMaterialMapper.getReturnMatList(month, teamQry, matQry, XDate.getMonth());
		PageInfo<MMaterial> pageInfo = new PageInfo<>(list);
		return PageUtils.getPageSet(pageInfo);
	}
}
