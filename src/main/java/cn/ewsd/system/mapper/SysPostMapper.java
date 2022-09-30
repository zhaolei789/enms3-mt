package cn.ewsd.system.mapper;
import cn.ewsd.common.mapper.BaseMapper;
import cn.ewsd.system.model.SysPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2022-03-07 15:11:32
 */
public interface SysPostMapper extends tk.mybatis.mapper.common.Mapper<SysPost> {

    //获取分页集
    List<SysPost> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(SysPost var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<SysPost> var1);

    int executeUpdate(SysPost var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    SysPost queryObject(Object var1);

    List<SysPost> queryList(Map<String, Object> var1);

    List<SysPost> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

    int queryTotalByFS(@Param("filterSort") String filterSort);

}
