package cn.ewsd.mdata.mapper;
import cn.ewsd.mdata.model.CsExcelUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户导入导出测试
 * 
 * @Author fengkai
 * @Email fengkai@ewsd.cn
 * @Date 2019-08-29 14:01:55
 */
public interface CsExcelUserMapper extends tk.mybatis.mapper.common.Mapper<CsExcelUser> {

    //获取分页集
    List<CsExcelUser> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(CsExcelUser var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<CsExcelUser> var1);

    int executeUpdate(CsExcelUser var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    CsExcelUser queryObject(Object var1);

    List<CsExcelUser> queryList(Map<String, Object> var1);

    List<CsExcelUser> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

    List<CsExcelUser> getDataByTablesAndField(@Param("tables") String tables, @Param("field") String field);

    int batchInsert(@Param("list") List<CsExcelUser> list);

}
