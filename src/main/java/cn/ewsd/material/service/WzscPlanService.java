package cn.ewsd.material.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.WzscPlan;

import java.util.List;
import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface WzscPlanService extends MaterialBaseService<WzscPlan, String> {
    PageSet<WzscPlan> getPageSet(PageParam pageParam, String filterSort, String monthQry, String userDeptIds, String codeQry, String typeQry, String centerQry, String purcQry, String matTypeQry, String nameQry, String statusQry);

    List<WzscPlan> getList(String filterSort, String monthQry, String userDeptIds, String codeQry, String typeQry, String centerQry, String purcQry, String matTypeQry, String nameQry, String statusQry);

    void importWzscPlan(List<Map<String, String>> dataList, String creatorId, String creator, String creatorOrgId) throws Exception;

    int deleteBatch(String[] uuids);

    PageSet<WzscPlan> getArriveQryPageSet(PageParam pageParam, String filterSort, String reserveQry, String monthQry, String userDeptIds, String centerQry, String matCodeQry, String over3Qry, String matNameQry, String over1Qry, String nowDate);

    List<WzscPlan> getArriveQryList(String reserveQry, String monthQry, String userDeptIds, String centerQry, String matCodeQry, String over3Qry, String matNameQry, String over1Qry, String nowDate);
}
