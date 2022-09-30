package cn.ewsd.logistics.mapper;
import cn.ewsd.common.mapper.BaseMapper;
import cn.ewsd.logistics.model.HqUserItems;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户的物品
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-08-02 15:11:19
 */
public interface HqUserItemsMapper extends tk.mybatis.mapper.common.Mapper<HqUserItems> {

    //获取分页集
    List<HqUserItems> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(HqUserItems var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<HqUserItems> var1);

    int executeUpdate(HqUserItems var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    HqUserItems queryObject(Object var1);

    List<HqUserItems> queryList(Map<String, Object> var1);

    List<HqUserItems> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

    int queryTotalByUserArray(Object[] var1);

    HqUserItems queryByUserUuid(Object var1);

    List<HqUserItems> getPageSetItems(@Param("filterSort") String filterSort);

    int executeUpdateItem(HqUserItems var1);
}
