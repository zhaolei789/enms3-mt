package cn.ewsd.system.service.impl;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.system.mapper.RuleMapper;
import cn.ewsd.system.model.Rule;
import cn.ewsd.system.service.RuleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author С��һ�(service@ewsd.cn)
 * @date 2015-12-02
 */

@Service("ruleServiceImpl")
public class RuleServiceImpl extends SystemBaseServiceImpl<Rule, String> implements RuleService {
        @Resource
    private RuleMapper ruleMapper;

    @Override
    public PageSet<Rule> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<Rule> list = ruleMapper.getPageSet(filterSort);
        PageInfo<Rule> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }



}