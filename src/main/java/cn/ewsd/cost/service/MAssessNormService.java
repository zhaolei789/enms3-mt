package cn.ewsd.cost.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.cost.model.MAssessNorm;
import cn.ewsd.cost.model.MFeeItem;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 奖罚考核
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-08-26 18:03:27
 */
public interface MAssessNormService extends CostBaseService<MAssessNorm, String> {

    PageSet<MAssessNorm> getTeamItemQuotaPageSet(PageParam pageParam, String filterSort, String assTeam, String teamNoQry, String prjQry);

    void saveAssessNorm(HttpServletRequest request) throws Exception;

    void updateAssessNorm(HttpServletRequest request) throws Exception;

    void deleteAssessNorm(HttpServletRequest request) throws Exception;
}
