package cn.ewsd.mdata.mapper;
import cn.ewsd.mdata.model.TdeptType;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-20 11:40:20
 */
public interface TDeptTypeMapper extends tk.mybatis.mapper.common.Mapper<TdeptType> {

    //获取分页集
    List<TdeptType> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(TdeptType var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<TDeptType> var1);

    int executeUpdate(TdeptType var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    TdeptType queryObject(Object var1);

    List<TdeptType> queryList(Map<String, Object> var1);

    List<TdeptType> queryList(Object var1);

    List<TdeptType> getListByDeptId(@Param("deptId")String deptId);

    int queryByDeptIdAndDictKey(@Param("deptId")String deptId, @Param("dictKey")String dictKey);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

}
