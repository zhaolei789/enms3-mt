package cn.ewsd.repository.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.repository.model.MSafeStore;

import java.util.HashMap;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
public interface MSafeStoreService extends RepositoryBaseService<MSafeStore, String> {
    PageSet<MSafeStore> getPageSet(PageParam pageParam, String storeNo, String matQry);

    int saveData(HashMap<String, Double[]> map);

    void calcSafeStore(String storeNo);
}
