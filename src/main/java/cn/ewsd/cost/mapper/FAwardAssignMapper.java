package cn.ewsd.cost.mapper;
import cn.ewsd.common.mapper.BaseMapper;
import cn.ewsd.cost.model.FAwardAssign;
import cn.ewsd.cost.model.FAwardAssignSelect_A;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 罚款分配
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-08-26 18:03:29
 */
public interface FAwardAssignMapper extends tk.mybatis.mapper.common.Mapper<FAwardAssign> {

    //获取分页集
    List<FAwardAssign> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(FAwardAssign var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<FAwardAssign> var1);

    int executeUpdate(FAwardAssign var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    FAwardAssign queryObject(Object var1);

    List<FAwardAssign> queryList(Map<String, Object> var1);

    List<FAwardAssign> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

    List<FAwardAssignSelect_A> queryListByLeadExcel(@Param("filterSort") String filterSort);

}
