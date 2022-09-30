package cn.ewsd.cost.mapper;
import cn.ewsd.common.mapper.BaseMapper;
import cn.ewsd.cost.model.FContract;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 合同
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-03 08:39:34
 */
public interface FContractMapper extends tk.mybatis.mapper.common.Mapper<FContract> {

    //获取分页集
    List<FContract> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(FContract var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<FContract> var1);

    int executeUpdate(FContract var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    FContract queryObject(Object var1);

    List<FContract> queryList(Map<String, Object> var1);

    List<FContract> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

}
