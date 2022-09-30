package cn.ewsd.system.mapper;

import cn.ewsd.system.model.TopicBase;
import org.apache.ibatis.annotations.Param;

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

public interface TopicBaseMapper extends tk.mybatis.mapper.common.Mapper<TopicBase> {

    //获取分页集
    List<TopicBase> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(TopicBase var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<TopicBase> var1);

    int executeUpdate(TopicBase var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    TopicBase queryObject(Object var1);

    List<TopicBase> queryList(Map<String, Object> var1);

    List<TopicBase> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

    HashMap<String,Object> getDetails(@Param("uuid") String uuid);

    String getChildIds(Map map);

    List<TopicBase> getLatestOaTopicByCategoryId(String categoryId, Integer limit);

    List<TopicBase> getoaTopicStatus(String status);

//    List<OaCategory> getoaTopics();

}
