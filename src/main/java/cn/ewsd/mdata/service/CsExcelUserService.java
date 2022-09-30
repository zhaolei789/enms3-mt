package cn.ewsd.mdata.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.mdata.model.CsExcelUser;

import java.util.List;
import java.util.Map;

/**
 * 用户导入导出测试
 * 
 * @Author fengkai
 * @Email fengkai@ewsd.cn
 * @Date 2019-08-29 14:01:55
 */
public interface CsExcelUserService extends MdataBaseService<CsExcelUser, String> {

    PageSet<CsExcelUser> getPageSet(PageParam pageParam, String filterSort);

	CsExcelUser queryObject(String uuid);
	
	List<CsExcelUser> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(CsExcelUser csExcelUser);
	
	int executeUpdate(CsExcelUser csExcelUser);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);

	/**
	 * @MethodName getDataByTablesAndField 根据字段和表查询数据
	 * @Description TODO
	 * @Param tables  表
	 * @Param field  字段
	 * @Return java.util.List<cn.ewsd.mdata.model.User>
	 * @Author 朱永敬<zhuyongjing@zuoyour.com>
	 * @Date 2019-08-29 11:55
	 */
	List<CsExcelUser> getDataByTablesAndField(String tables, String field);

	/**
	 * @MethodName batchInsert 批量添加
	 * @Description TODO
	 * @Param config
	 * @Return java.util.List<cn.ewsd.system.model.Config>
	 * @Author 朱永敬<zhuyongjing@zuoyour.com>
	 * @Date 2019-08-27 18:46
	 */
	int batchInsert(List<CsExcelUser> list);
}
