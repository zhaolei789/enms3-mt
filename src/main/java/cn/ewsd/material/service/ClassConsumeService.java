package cn.ewsd.material.service;

import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.ConsumeStock;
import cn.ewsd.material.model.MItem;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ClassConsumeService{
    PageSet<ConsumeStock> getPageSet(PageParam pageParam, String filterSort, String userTeam, String occDate1Qry, String occDate2Qry, String deptNoQry);

    PageSet<ConsumeStock> getPageSetXzcl(PageParam pageParam, String filterSort, String teamNo, String storeNoQry, String statusQry, String deptNoQry, String occDate2Qry, String prjNoQry, String matCodeQry, String matNameQry);

    void saveMat(HttpServletRequest request, UserInfo userInfo) throws Exception;

    void delete(HttpServletRequest request, UserInfo userInfo) throws Exception;

    List<MItem> getItem(String teamNo, String month);
}
