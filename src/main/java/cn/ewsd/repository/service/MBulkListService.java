package cn.ewsd.repository.service;

import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.repository.model.MBulkList;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
public interface MBulkListService extends RepositoryBaseService<MBulkList, String> {
    PageSet<MBulkList> getPageSet(PageParam pageParam, String filterSort, String checkNo, String siteNoQry, String matNameQry);

    void updateBulk(HttpServletRequest request, UserInfo userInfo) throws Exception;

    void submitBulk(HttpServletRequest request) throws Exception;

    List<MBulkList> getBulkList(String checkNo, String siteNoQry, String matNameQry);

    PageSet<MBulkList> getChkPageSet(PageParam pageParam, String filterSort, String checkNoQry);

    PageSet<MBulkList> getInventQryPageSet(PageParam pageParam, String filterSort, String checkNoQry);
}
