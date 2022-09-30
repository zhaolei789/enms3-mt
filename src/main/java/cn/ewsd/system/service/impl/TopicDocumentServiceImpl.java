package cn.ewsd.system.service.impl;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.system.mapper.TopicDocumentMapper;
import cn.ewsd.system.model.TopicDocument;
import cn.ewsd.system.service.TopicDocumentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.bouncycastle.math.ec.ScaleYPointMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("TopicDocumentServiceImpl")
public class TopicDocumentServiceImpl extends SystemBaseServiceImpl<TopicDocument, String> implements TopicDocumentService {
	@Autowired
	private TopicDocumentMapper topicDocumentMapper;

    @Override
    public PageSet<TopicDocument> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<TopicDocument> list = topicDocumentMapper.getPageSet(filterSort);
        PageInfo<TopicDocument> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public TopicDocument queryObject(String uuid){
		return topicDocumentMapper.queryObject(uuid);
	}

	@Override
	public List<TopicDocument> queryList(Map<String, Object> map){
		return topicDocumentMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return topicDocumentMapper.queryTotal(map);
	}

	@Override
	public int executeSave(TopicDocument oaTopicDocument){
		return topicDocumentMapper.executeSave(oaTopicDocument);
	}

	@Override
	public int executeUpdate(TopicDocument oaTopicDocument){
		return topicDocumentMapper.executeUpdate(oaTopicDocument);
	}

	@Override
	public int executeDelete(String uuid){
		return topicDocumentMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return topicDocumentMapper.executeDeleteBatch(uuids);
	}

	@Override
	public TopicDocument getDetailBytopicUuid(String uuid) {
		return topicDocumentMapper.getDetailBytopicUuid(uuid);
	}

	@Override
	public int updateTopicDocumentContent(String content, String uuid) {
		return topicDocumentMapper.updateTopicDocumentContent(content,uuid);
	}



}
