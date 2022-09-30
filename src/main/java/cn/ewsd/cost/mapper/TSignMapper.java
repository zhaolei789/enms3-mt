package cn.ewsd.cost.mapper;
import cn.ewsd.common.mapper.BaseMapper;
import cn.ewsd.cost.model.TSign;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-17 10:27:06
 */
public interface TSignMapper extends tk.mybatis.mapper.common.Mapper<TSign> {

    //获取分页集
    List<TSign> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(TSign var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<TSign> var1);

    int executeUpdate(TSign var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    TSign queryObject(Object var1);

    List<TSign> queryList(Map<String, Object> var1);

    List<TSign> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

}
