package cn.ewsd.system.service.impl;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.system.mapper.SampleMapper;
import cn.ewsd.system.model.Sample;
import cn.ewsd.system.service.SampleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 佐佑科技(service@ewsd.cn)
 * @date 2018-01-01
 */
@Service("sampleServiceImpl")
public class SampleServiceImpl extends SystemBaseServiceImpl<Sample, String> implements SampleService {

    @Resource
    private SampleMapper sampleMapper;

    @Override
    public PageSet<Sample> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<Sample> list = sampleMapper.getPageSet(filterSort);
        PageInfo<Sample> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }
}