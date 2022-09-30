package cn.ewsd.repository.mapper;
import cn.ewsd.repository.model.MStore;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:12
 */
public interface MStoreMapper extends tk.mybatis.mapper.common.Mapper<MStore> {
    List<MStore> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(MStore var1);

    MStore queryObject(String uuid);

    int executeUpdate(MStore oStore);

    int executeDelete(String uuid);

    List<MStore> getStoreList(@Param("userId") String userId, @Param("upDown") String upDown, @Param("mngTeam") String mngTeam, @Param("ifSafe") String ifSafe,
                              @Param("ifStock") String ifStock, @Param("bigType") String bigType, @Param("storeType") String storeType, @Param("storeLevel") String storeLevel);

    MStore getStoreByNo(String storeNo);

    MStore getStoreByName(String storeName);

    List<MStore> getStoreList1();

    List<MStore> getOutStoreList(@Param("startDate") String startDate, @Param("endDate") String endDate);

    List<MStore> getDZK(@Param("storeType") String storeType, @Param("mngTeam") String mngTeam);

    List<MStore> getStoreByType(String storeType);

    List<MStore> getPowerStore(@Param("isPower") boolean isPower, @Param("userId") String userId);

    List<MStore> getAllStockQryStore(@Param("levels") String levels, @Param("ifPower") boolean ifPower);
}
