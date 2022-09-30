package cn.ewsd.cost.mapper;
import cn.ewsd.common.mapper.BaseMapper;
import cn.ewsd.cost.model.FItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 费用项目
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-07 09:22:21
 */
public interface FItemMapper extends tk.mybatis.mapper.common.Mapper<FItem> {

    //获取分页集
    List<FItem> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(FItem var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<FItem> var1);

    int executeUpdate(FItem var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    FItem queryObject(Object var1);

    List<FItem> queryList(Map<String, Object> var1);

    List<FItem> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

}
