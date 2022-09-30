package cn.ewsd.material.mapper;
import cn.ewsd.common.mapper.BaseMapper;
import cn.ewsd.material.model.MSite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 货位
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2022-07-06 17:28:02
 */
public interface MSiteMapper extends tk.mybatis.mapper.common.Mapper<MSite> {

    //获取分页集
    List<MSite> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(MSite var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<MSite> var1);

    int executeUpdate(MSite var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    MSite queryObject(Object var1);

    List<MSite> queryList(Map<String, Object> var1);

    List<MSite> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

    List<MSite> queryListByIds(Object[] var1);

}
