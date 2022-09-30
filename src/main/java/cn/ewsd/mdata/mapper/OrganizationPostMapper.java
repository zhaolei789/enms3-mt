package cn.ewsd.mdata.mapper;

import cn.ewsd.mdata.model.OrganizationPost;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrganizationPostMapper extends tk.mybatis.mapper.common.Mapper<OrganizationPost> {

   //获取分页集
    List<OrganizationPost> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(OrganizationPost var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<DevDeviceBase> var1);

    int executeUpdate(OrganizationPost var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

//    Organization queryObject(Object var1);
//
//    List<Organization> queryList(Map<String, Object> var1);
//
//    List<Organization> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

    OrganizationPost queryObject(String uuid);

    List<OrganizationPost> queryList(Map<String, Object> map);

   OrganizationPost getOrgPostByOrgUuidAndPost(String orgUuid, String postValue);
}
