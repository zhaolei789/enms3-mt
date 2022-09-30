//package cn.ewsd.logistics.service;
//
//import cn.ewsd.common.utils.easyui.PageParam;
//import cn.ewsd.common.utils.easyui.PageSet;
//import cn.ewsd.logistics.model.AuditLog;
//import cn.ewsd.logistics.service.LogisticsBaseService;
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
//public interface AuditLogService extends LogisticsBaseService<AuditLog, String> {
//
//    PageSet<AuditLog> getPageSet(PageParam pageParam, String filterSort);
//
//	AuditLog queryObject(String uuid);
//
//	List<AuditLog> queryList(Map<String, Object> map);
//
//	List<AuditLog> queryListAll();
//
//	int queryTotal(Map<String, Object> map);
//
//    int executeSave(AuditLog auditLog);
//
//	int executeUpdate(AuditLog auditLog);
//
//	int executeDelete(String uuid);
//
//	int executeDeleteBatch(String[] uuids);
//
//	List<AuditLog> queryListByBusiness(String businessType,String businessId);
//
//	List<AuditLog> queryListByFS(String filterSort);
//}
