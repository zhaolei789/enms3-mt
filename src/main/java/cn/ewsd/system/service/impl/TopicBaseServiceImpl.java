package cn.ewsd.system.service.impl;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.system.mapper.TopicBaseMapper;
import cn.ewsd.system.model.TopicBase;
import cn.ewsd.system.service.TopicBaseService;
import cn.ewsd.system.model.TopicBase;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("oaTopicBaseServiceImpl")
public class TopicBaseServiceImpl extends SystemBaseServiceImpl<TopicBase, String> implements TopicBaseService {
	@Autowired
	private TopicBaseMapper topicBaseMapper;

    @Override
    public PageSet<TopicBase> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<TopicBase> list = topicBaseMapper.getPageSet(filterSort);
        PageInfo<TopicBase> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public TopicBase queryObject(String uuid){
		return topicBaseMapper.queryObject(uuid);
	}

	@Override
	public List<TopicBase> queryList(Map<String, Object> map){
		return topicBaseMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return topicBaseMapper.queryTotal(map);
	}

	@Override
	public int executeSave(TopicBase oaTopicBase){
		return topicBaseMapper.executeSave(oaTopicBase);
	}

	@Override
	public int executeUpdate(TopicBase oaTopicBase){
		return topicBaseMapper.executeUpdate(oaTopicBase);
	}

	@Override
	public int executeDelete(String uuid){
		return topicBaseMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return topicBaseMapper.executeDeleteBatch(uuids);
	}

	@Override
	public HashMap<String, Object> getDetails(String uuid) {
		return topicBaseMapper.getDetails(uuid);
	}

	@Override
	public String getChildIds(Map map) {
		return topicBaseMapper.getChildIds(map);
	}

	@Override
	public List<TopicBase> getLatestOaTopicByCategoryId(String categoryId, Integer limit) {
		return topicBaseMapper.getLatestOaTopicByCategoryId(categoryId,limit);
	}

	@Override
	public List<TopicBase> getoaTopicStatus(String status) {
		return topicBaseMapper.getoaTopicStatus(status);
	}

//	@Override
//	public List<OaCategory> getoaTopics() {
//		return topicBaseMapper.getoaTopics();
//	}

}
