package cn.ewsd.mdata.mapper;
import cn.ewsd.mdata.model.Organization;
import cn.ewsd.mdata.model.SysUserPost;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Author zhaoxiace
 * @Email zhaoxiace@ewsd.cn
 * @Date 2019-05-06 14:04:50
 */
public interface SysUserPostMapper extends tk.mybatis.mapper.common.Mapper<SysUserPost> {

    //获取分页集
    List<SysUserPost> getPageSet(@Param("filterSort") String filterSort, @Param("userNameId") String userNameId);

    int executeSave(SysUserPost var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<SysUserPost> var1);

    int executeUpdate(SysUserPost var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    SysUserPost queryObject(Object var1);

    List<SysUserPost> queryList(Map<String, Object> var1);

    List<SysUserPost> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

    List<Organization> getGroupConcat(@Param("ids") String[] ids);

    int isExistence(@Param("userNameId") String userNameId, @Param("post") String post, @Param("orgId") String orgId);

    String getPostText(@Param("post") String post);
}
