package cn.ewsd.cost.service;

import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.cost.model.MNormFee;
import cn.ewsd.cost.model.MOutAssess;
import cn.ewsd.mdata.model.Organization;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * 矿长
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-08-31 17:13:54
 */
public interface MNormFeeService extends CostBaseService<MNormFee, String> {
    List<MNormFee> getMatAssessList(String monthQry, String teamNoQry);

    HashMap<String, Object> getMatAssessFormData(String month, String teamQry);

    void calcMatAssess(HttpServletRequest request, UserInfo userInfo) throws Exception;

    void saveMatAssess(HttpServletRequest request, UserInfo userInfo) throws Exception;

    void deleteMatAssess(HttpServletRequest request, UserInfo userInfo) throws Exception;

    List<MNormFee> getMatAssTyList(String occMonth, String assType, String ifEnter);

    List<MNormFee> getMatAssZhList(String occMonth, String assType);

    List<MNormFee> getMatAssDcList(String occMonth, String assType);

    List<MNormFee> getMatAssItemList(String occMonth, String beginDate, String endDate);
}
