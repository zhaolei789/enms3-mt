package cn.ewsd.fix.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.fix.model.MBackPlan;
import cn.ewsd.material.model.MMaterial;

/**
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-05-12 14:32:59
 */
public interface ReturnMatService extends FixBaseService<MBackPlan, String> {
	PageSet<MMaterial> getPageSet(PageParam pageParam, String filterSort, String month, String teamQry, String matQry);
}
