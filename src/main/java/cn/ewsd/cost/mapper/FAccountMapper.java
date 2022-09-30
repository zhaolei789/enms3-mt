package cn.ewsd.cost.mapper;
import cn.ewsd.common.mapper.BaseMapper;
import cn.ewsd.cost.model.FAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 工程结算
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-03 08:39:36
 */
public interface FAccountMapper extends tk.mybatis.mapper.common.Mapper<FAccount> {

    //获取分页集
    List<FAccount> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(FAccount var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<FAccount> var1);

    int executeUpdate(FAccount var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    FAccount queryObject(Object var1);

    List<FAccount> queryList(Map<String, Object> var1);

    List<FAccount> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

}
