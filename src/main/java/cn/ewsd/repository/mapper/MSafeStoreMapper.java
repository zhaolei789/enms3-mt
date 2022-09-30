package cn.ewsd.repository.mapper;
import cn.ewsd.repository.model.MSafeStore;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
public interface MSafeStoreMapper extends tk.mybatis.mapper.common.Mapper<MSafeStore> {
    List<MSafeStore> getPageSet(@Param("storeNo") String storeNo, @Param("matQry") String matQry);

    int save(@Param("storeNo") String storeNo, @Param("matNo") String matNo, @Param("d") Double[] d);

    int deleteData(@Param("matNo") String matNo, @Param("storeNo") String storeNo);

    int excuteUpdate(MSafeStore mSafeStore);

    void calcSafeStock(String storeNo);
}
