package cn.ewsd.cost.mapper;
import cn.ewsd.common.mapper.BaseMapper;
import cn.ewsd.cost.model.FFeeReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 费用上报
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-09 10:31:35
 */
public interface FFeeReportMapper extends tk.mybatis.mapper.common.Mapper<FFeeReport> {

    //获取分页集
    List<FFeeReport> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(FFeeReport var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<FFeeReport> var1);

    int executeUpdate(FFeeReport var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    FFeeReport queryObject(Object var1);

    List<FFeeReport> queryList(Map<String, Object> var1);

    List<FFeeReport> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

}
