package cn.ewsd.repository.service;

import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.repository.model.MCheck;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
public interface MCheckService extends RepositoryBaseService<MCheck, String> {
    PageSet<MCheck> getPageSet(PageParam pageParam, String filterSort, String yearQry, String userId);

    int insertCheck(MCheck mCheck);

    int deleteCheck(String checkNo);

    MCheck getCheckByNo(String checkNo);

    List<MCheck> getCheckSet(String checkStep);

    int deleteBulk(String listId, String checkNo);

    void finishCheck(HttpServletRequest request, UserInfo userInfo) throws Exception;
}
