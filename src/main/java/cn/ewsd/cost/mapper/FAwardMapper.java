package cn.ewsd.cost.mapper;
import cn.ewsd.common.mapper.BaseMapper;
import cn.ewsd.cost.model.FAward;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 奖罚考核
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-08-26 18:03:27
 */
public interface FAwardMapper extends tk.mybatis.mapper.common.Mapper<FAward> {

    //获取分页集
    List<FAward> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(FAward var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<FAward> var1);

    int executeUpdate(FAward var1);

    int executeUpdate2(FAward var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    FAward queryObject(Object var1);

    FAward queryObject2(Object var1);

    FAward queryObjectSum(Object var1);

    List<FAward> queryList(Map<String, Object> var1);

    List<FAward> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

    List<FAward> getPageSetJoinAssign(@Param("filterSort") String filterSort);

}
