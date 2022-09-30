package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.Sample;

/**
 * @author 佐佑科技(service@ewsd.cn)
 * @date 2018-01-01
 */
public interface SampleService extends SystemBaseService<Sample, String> {

    PageSet<Sample> getPageSet(PageParam pageParam, String filterSort);

}