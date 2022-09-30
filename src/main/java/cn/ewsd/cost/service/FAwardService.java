package cn.ewsd.cost.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.cost.model.FAward;
import cn.ewsd.cost.service.CostBaseService;

import java.util.List;
import java.util.Map;

/**
 * 奖罚考核
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-08-26 18:03:27
 */
public interface FAwardService extends CostBaseService<FAward, String> {

    PageSet<FAward> getPageSet(PageParam pageParam, String filterSort);

	PageSet<FAward> getPageSetJoinAssign(PageParam pageParam, String filterSort);

	FAward queryObject(String uuid);

	FAward queryObject2(String uuid);

	FAward queryObjectSum(String uuid);
	
	List<FAward> queryList(Map<String, Object> map);

	List<FAward> queryList(String filterSort);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(FAward fAward);
	
	int executeUpdate(FAward fAward);

	int executeUpdate2(FAward fAward);

	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuid);

	List<FAward> queryListJoinAssign(String filterSort);
}
