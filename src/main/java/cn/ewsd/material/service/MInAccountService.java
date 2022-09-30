package cn.ewsd.material.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MInAccount;

import java.util.List;
import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface MInAccountService extends MaterialBaseService<MInAccount, String> {
    PageSet<MInAccount> getPageSet(PageParam pageParam, String filterSort, String yearQry, String monQry, String numberQry, String orderQry);

    int importInAccount(List<Map<String, String>> dataList, String creatorId, String creator, String creatorOrgId);

    List<MInAccount> getList(String yearQry, String monQry, String numberQry, String orderQry);

    int deleteBatch(String[] uuids);
}
