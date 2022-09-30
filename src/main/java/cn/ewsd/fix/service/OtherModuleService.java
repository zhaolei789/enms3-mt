package cn.ewsd.fix.service;

import cn.ewsd.fix.model.MBackPlan;

import java.util.List;

/**
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-05-12 14:32:59
 */
public interface OtherModuleService extends FixBaseService<MBackPlan, String> {


	MBackPlan queryObject(String uuid);
	
	List<MBackPlan> queryList(String var1);

}
