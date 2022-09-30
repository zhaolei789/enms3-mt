package cn.ewsd.material.service;

import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.SendMatPlan;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface SendMatPlanService extends MaterialBaseService<SendMatPlan, String> {
    List<SendMatPlan> getPlanMonth();

    PageSet<SendMatPlan> getPageSet(PageParam pageParam, String filterSort, String planMonth, String planType, String prjNo, String matCode, String matName, String teamNo, String itemNo, String centerNo);

    String back(String[] planNos, UserInfo userInfo);

    int submit(String[] planNos, UserInfo userInfo) throws Exception;

    List<SendMatPlan> getList(String planMonth, String planType, String prjNo, String matCode, String matName, String teamNo, String itemNo, String centerNo);
}
