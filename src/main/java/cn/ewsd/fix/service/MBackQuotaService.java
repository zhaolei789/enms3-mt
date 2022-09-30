package cn.ewsd.fix.service;

import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.fix.model.MBackQuota;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-05-12 14:04:07
 */
public interface MBackQuotaService extends FixBaseService<MBackQuota, String> {
    PageSet<MBackQuota> getPageSet(PageParam pageParam, String filterSort, String monthQry, String prjQry, String userTeam);

    List<MBackQuota> getBackQuotaList(String mothQry, String prjQry, String userTeam);

    List<MBackQuota> getBackPlanList(String monthQry, String prjQry, String userTeam, String teamNoQry);

    void insertBackQuota(HttpServletRequest request, UserInfo userInfo) throws Exception;

    void deleteBackQuota(HttpServletRequest request) throws Exception;
}
