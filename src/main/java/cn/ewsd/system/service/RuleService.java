package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.Rule;

/**
 * @author С��һ�(service@ewsd.cn)
 * @date 2015-12-02
 */

public interface RuleService extends SystemBaseService<Rule, String> {

    PageSet<Rule> getPageSet(PageParam pageParam, String filterSort);
}