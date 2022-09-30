//package cn.ewsd.logistics.mapper;
//import cn.ewsd.common.mapper.BaseMapper;
//import cn.ewsd.logistics.model.AuditLog;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Param;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * 审批日志
// *
// * @Author zxrmine
// * @Email zxrmine@163.cn
// * @Date 2021-03-25 14:10:22
// */
//public interface AuditLogMapper extends tk.mybatis.mapper.common.Mapper<AuditLog> {
//
//    //获取分页集
//    List<AuditLog> getPageSet(@Param("filterSort") String filterSort);
//
//    int executeSave(AuditLog var1);
//
//    int executeSave(Map<String, Object> var1);
//
//    //void executeSaveBatch(List<AuditLog> var1);
//
//    int executeUpdate(AuditLog var1);
//
//    int executeUpdate(Map<String, Object> var1);
//
//    int executeDelete(Object var1);
//
//    int executeDelete(Map<String, Object> var1);
//
//    int executeDeleteBatch(Object[] var1);
//
//    AuditLog queryObject(Object var1);
//
//    List<AuditLog> queryList(Map<String, Object> var1);
//
//    List<AuditLog> queryList(Object var1);
//
//    int queryTotal(Map<String, Object> var1);
//
//    int queryTotal();
//
//    List<AuditLog> queryListAll();
//
//    List<AuditLog> queryListByBusiness(@Param("businessType") String businessType,@Param("businessId") String businessId);
//
//}
