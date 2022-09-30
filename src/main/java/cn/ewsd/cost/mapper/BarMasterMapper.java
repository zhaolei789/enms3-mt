package cn.ewsd.cost.mapper;
import cn.ewsd.common.mapper.BaseMapper;
import cn.ewsd.cost.model.BarMaster;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 矿长
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-08-31 17:13:54
 */
public interface BarMasterMapper extends tk.mybatis.mapper.common.Mapper<BarMaster> {

    //获取分页集
    List<BarMaster> getPageSet(@Param("filterSort") String filterSort);

    List<BarMaster> getPageSetYearFee(@Param("filterSort") String filterSort,@Param("fMonth") String fMonth);

    List<BarMaster> getPageSetSbCw(@Param("filterSort") String filterSort,@Param("queryYear") String queryYear,@Param("queryMonth") String queryMonth);

    List<BarMaster> getPageSetSbFs(@Param("filterSort") String filterSort,@Param("queryYear") String queryYear,@Param("queryMonth") String queryMonth);

    List<BarMaster> getPageSetSettle(@Param("filterSort") String filterSort,@Param("queryYear") String queryYear,@Param("queryMonth") String queryMonth);

    int executeSave(BarMaster var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<BarMaster> var1);

    int executeUpdate(BarMaster var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    BarMaster queryObject(Object var1);

    List<BarMaster> queryList(Map<String, Object> var1);

    List<BarMaster> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

}
