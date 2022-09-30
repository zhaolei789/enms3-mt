package cn.ewsd.system.mapper;
import cn.ewsd.system.model.TopicDocument;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 主题内容
 * 
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-06-08 16:06:00
 */

public interface TopicDocumentMapper extends tk.mybatis.mapper.common.Mapper<TopicDocument> {

    //获取分页集
    List<TopicDocument> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(TopicDocument var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<TopicDocument> var1);

    int executeUpdate(TopicDocument var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    TopicDocument queryObject(Object var1);

    List<TopicDocument> queryList(Map<String, Object> var1);

    List<TopicDocument> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

    TopicDocument getDetailBytopicUuid(String uuid);

    int updateTopicDocumentContent(String content, String uuid);


}
