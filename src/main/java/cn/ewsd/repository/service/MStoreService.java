package cn.ewsd.repository.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.repository.model.MStore;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
public interface MStoreService extends RepositoryBaseService<MStore, String> {
    PageSet<MStore> getPageSet(PageParam pageParam, String filterSort);

    int executeSave(MStore mStore);

    MStore queryObject(String uuid);

    int executeUpdate(MStore mStore);

    int executeDelete(String uuid);

    List<MStore> getStoreList(String userId, String upDown, String mngTeam, String ifSafe, String ifStock, String bigType, String storeType, String storeLevel);

    MStore getStoreByNo(String storeNo);

    MStore getStoreByName(String storeName);

    List<MStore> getStoreList1();

    List<MStore> getOutStoreList(String startDate, String endDate);

    List<MStore> getPowerStore(boolean isPower, String userId);

    List<MStore> getAllStockQryStore(String levels, boolean ifPower);
}
