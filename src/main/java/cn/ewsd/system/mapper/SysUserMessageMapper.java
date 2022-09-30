package cn.ewsd.system.mapper;
import cn.ewsd.common.mapper.BaseMapper;
import cn.ewsd.system.model.SysUserMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 消息
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-10-21 13:58:38
 */
public interface SysUserMessageMapper extends tk.mybatis.mapper.common.Mapper<SysUserMessage> {

    //获取分页集
    List<SysUserMessage> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(SysUserMessage var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<SysUserMessage> var1);

    int executeUpdate(SysUserMessage var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    SysUserMessage queryObject(Object var1);

    List<SysUserMessage> queryList(Map<String, Object> var1);

    List<SysUserMessage> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

    int queryNotReadNum(@Param("userId") String user_uuid);

    int executeReadBatch(Object[] var1);

}
