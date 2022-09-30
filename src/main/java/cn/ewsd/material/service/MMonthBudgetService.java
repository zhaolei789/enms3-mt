package cn.ewsd.material.service;

import cn.ewsd.material.model.MBudget;

import java.util.HashMap;
import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface MMonthBudgetService extends MaterialBaseService<MBudget, String> {
    List<MBudget> getMonthBudgetList(String itemNo, String useYear, String useMonth, String teamNo);

    int saveData(String itemNo, String occMonth, HashMap<String, Double[]> map);

}
