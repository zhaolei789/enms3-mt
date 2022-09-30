package cn.ewsd.material.service;

import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.material.model.MBudget;

import java.util.HashMap;
import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface ClassBudgetService extends MaterialBaseService<MBudget, String> {
    List<MBudget> getClassBudgetList(String useYear, String useMonth, String teamNo);

    List<MBudget> getClassForm(String teamNo, String occMonth, String itemNo);

    int saveData(MBudget mBudget, UserInfo userInfo) throws Exception;

}
