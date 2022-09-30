package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.TopicBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主题表
 * 
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-06-08 16:05:31
 */
public interface TopicBaseService extends SystemBaseService<TopicBase, String> {

    PageSet<TopicBase> getPageSet(PageParam pageParam, String filterSort);

	TopicBase queryObject(String uuid);
	
	List<TopicBase> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(TopicBase oaTopicBase);
	
	int executeUpdate(TopicBase oaTopicBase);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);

    HashMap<String,Object> getDetails(String uuid);

    String getChildIds(Map map);

    List<TopicBase> getLatestOaTopicByCategoryId(String categoryId, Integer limit);

    List<TopicBase> getoaTopicStatus(String status);

//    List<OaCategory> getoaTopics();

}
