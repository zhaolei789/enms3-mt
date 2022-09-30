package cn.ewsd.logistics.mapper;
import cn.ewsd.common.mapper.BaseMapper;
import cn.ewsd.logistics.model.SysSequence;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-07-27 09:52:49
 */
public interface SysSequenceMapper extends tk.mybatis.mapper.common.Mapper<SysSequence> {

    //获取分页集
    List<SysSequence> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(SysSequence var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<SysSequence> var1);

    int executeUpdate(SysSequence var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    SysSequence queryObject(Object var1);

    List<SysSequence> queryList(Map<String, Object> var1);

    List<SysSequence> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

    SysSequence findSequence(@Param("sqSystem") String sqSystem,
                             @Param("sqModule") String sqModule,
                             @Param("sqYear") Integer sqYear,
                             @Param("sqMonth") Integer sqMonth);

    int sequenceAddOne(Object var1);
}
