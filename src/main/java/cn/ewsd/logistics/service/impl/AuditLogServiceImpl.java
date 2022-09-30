//package cn.ewsd.logistics.service.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//
//import cn.ewsd.common.utils.BaseUtils;
//import cn.ewsd.common.utils.easyui.PageParam;
//import cn.ewsd.common.utils.easyui.PageSet;
//import cn.ewsd.common.utils.easyui.PageUtils;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//
//import cn.ewsd.logistics.mapper.AuditLogMapper;
//import cn.ewsd.logistics.model.AuditLog;
//import cn.ewsd.logistics.service.AuditLogService;
//import cn.ewsd.logistics.service.impl.LogisticsBaseServiceImpl;
//
//@Service("auditLogServiceImpl")
//public class AuditLogServiceImpl extends LogisticsBaseServiceImpl<AuditLog, String> implements AuditLogService {
//	@Autowired
//	private AuditLogMapper auditLogMapper;
//
//    @Override
//    public PageSet<AuditLog> getPageSet(PageParam pageParam, String filterSort) {
//        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
//        List<AuditLog> list = auditLogMapper.getPageSet(filterSort);
//        PageInfo<AuditLog> pageInfo = new PageInfo<>(list);
//        return PageUtils.getPageSet(pageInfo);
//    }
//
//	@Override
//	public AuditLog queryObject(String uuid){
//		return auditLogMapper.queryObject(uuid);
//	}
//
//	@Override
//	public List<AuditLog> queryList(Map<String, Object> map){
//		return auditLogMapper.queryList(map);
//	}
//
//	@Override
//	public int queryTotal(Map<String, Object> map){
//		return auditLogMapper.queryTotal(map);
//	}
//
//	@Override
//	public int executeSave(AuditLog auditLog){
//		return auditLogMapper.executeSave(auditLog);
//	}
//
//	@Override
//	public int executeUpdate(AuditLog auditLog){
//		return auditLogMapper.executeUpdate(auditLog);
//	}
//
//	@Override
//	public int executeDelete(String uuid){
//		return auditLogMapper.executeDelete(uuid);
//	}
//
//	@Override
//	public int executeDeleteBatch(String[] uuids){
//		return auditLogMapper.executeDeleteBatch(uuids);
//	}
//
//	@Override
//	public List<AuditLog> queryListAll(){
//		return auditLogMapper.queryListAll();
//	}
//
//	@Override
//	public List<AuditLog> queryListByBusiness(String businessType,String businessId){
//		return auditLogMapper.queryListByBusiness(businessType,businessId);
//	}
//
//	@Override
//	public List<AuditLog> queryListByFS(String filterSort) {
//		return auditLogMapper.getPageSet(filterSort);
//	}
//
//}
