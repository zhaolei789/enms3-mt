package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.TopicDocument;

import java.util.List;
import java.util.Map;

/**
 * 主题内容
 * 
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-06-08 16:06:00
 */
public interface TopicDocumentService extends SystemBaseService<TopicDocument, String> {

    PageSet<TopicDocument> getPageSet(PageParam pageParam, String filterSort);

	TopicDocument queryObject(String uuid);
	
	List<TopicDocument> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

    int executeSave(TopicDocument oaTopicDocument);
	
	int executeUpdate(TopicDocument oaTopicDocument);
	
	int executeDelete(String uuid);
	
	int executeDeleteBatch(String[] uuids);

    TopicDocument getDetailBytopicUuid(String uuid);

	int updateTopicDocumentContent(String content, String uuid);



}
