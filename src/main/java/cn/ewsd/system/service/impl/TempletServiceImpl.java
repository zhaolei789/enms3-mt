package cn.ewsd.system.service.impl;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.system.mapper.TempletMapper;
import cn.ewsd.system.model.Templet;
import cn.ewsd.system.service.TempletService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("templetService")
public class TempletServiceImpl extends SystemBaseServiceImpl<Templet, String> implements TempletService {

    @Resource
    private TempletMapper templetMapper;

    @Override
    public PageSet<Templet> getPageSet(PageParam pageParam, String filterStr) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<Templet> list = templetMapper.getPageSet(filterStr);
        PageInfo<Templet> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

}