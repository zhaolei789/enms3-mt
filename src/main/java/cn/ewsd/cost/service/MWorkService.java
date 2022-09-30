package cn.ewsd.cost.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.cost.model.MWork;
import cn.ewsd.cost.service.CostBaseService;

import java.util.List;
import java.util.Map;

/**
 * 生产
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-13 18:44:03
 */
public interface MWorkService extends CostBaseService<MWork, String> {

    PageSet<MWork> getPageSet(PageParam pageParam, String filterSort);

	MWork queryObject(String uuid);
	
	List<MWork> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(MWork mWork);
	
	int executeUpdate(MWork mWork);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);

	List<MWork> getRationAnalWorkList();
}
