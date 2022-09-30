package cn.ewsd.system.mapper;

import cn.ewsd.base.bean.CommonMapper;
import cn.ewsd.system.model.AuthAccess;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-06-26 10:50:51
 */

public interface AuthAccessMapper extends CommonMapper<AuthAccess> {



//    //获取分页集
//    List<Config> getPageSet(@Param("filterSort") String filterSort);
//
//    int executeSave(SysFdField var1);
//
//    int executeSave(Map<String, Object> var1);
//
//    //void executeSaveBatch(List<SysFdField> var1);
//
//    int executeUpdate(SysFdField var1);
//
//    int executeUpdate(Map<String, Object> var1);
//
//    int executeDelete(Object var1);
//
//    int executeDelete(Map<String, Object> var1);
//
//    int executeDeleteBatch(Object[] var1);
//
//    SysFdField queryObject(Object var1);
//
//    List<SysFdField> queryList(Map<String, Object> var1);
//
//    List<SysFdField> queryList(Object var1);
//
//    int queryTotal(Map<String, Object> var1);
//
//    int queryTotal();


    List<AuthAccess> getPageSet(@Param("filterSort") String filterSort);

    List<AuthAccess> getListByRoleIdAndPid1(@Param("list") List<String> list, @Param("pid") String pid);

    Integer getListByRoleIdAndUuid(Integer roleId, String uuid);

    Integer updateAccessAuth(Integer roleId, String uuid);

    Integer getListByRoleIdAndUuids(String roleId, String singleUuid);

    Integer updateAccessAuths(String roleId, String singleUuid);

    Integer getByRoleIdAndId(Integer id, Integer rootId);

    List<Map<String,Object>> getListByResourceType();

    void updateByPids(@Param("list") List<String> list);

    Integer getCountByCurrentUserNameIdAUrl(@Param("list") List<String> list, @Param("url") String url);

    AuthAccess getDetailByRoleIdByUserNameIdAndUrl(@Param("list") List<String> list, @Param("refererUri") String refererUri);

    List<Map<String,Object>> getListByRoleIdByUserNameIdAndPid(@Param("list") List<String> list, @Param("pid") String pid);

    List<Map<String,Object>> getListByRoleIdByUserNameIdAndText(@Param("list") List<String> list, @Param("text") String text);

    List<AuthAccess> selectUserNameIdAndPid(String roleIdByUserNameId, String pid);

    List<AuthAccess> selectCodeLeveMenu(@Param("list") List<String> list, @Param("codeSetId") String codeSetId, @Param("levelId") String levelId, @Param("menuId") String menuId);

    List<AuthAccess> getListByRoleId(String roleId);

    AuthAccess getDetailByRoleIdAndUrl(@Param("list") List<String> list, @Param("url") String url);

    Integer getCountByRoleIdAndMenuId(Integer roleId, Integer id);

    void executeByStateRoleIdMid(String state, Integer roleId, Integer id);

    List<AuthAccess> getListByRoleIdAndPid(Integer roleId, Integer id);

    List<AuthAccess> getLimitedListByRoldIdsAndUrl(@Param("list") List<String> list, @Param("url") String url);

    List<Map<String,Object>> getListByJdbc(@Param("filterSort") String filterStr);

    String getChildIds(Map map);

    List<AuthAccess> getListBypId(@Param("filterSort") String filterStr);

    List<AuthAccess> getListInUuid(@Param("list") List<String> list);

    int deleteByUuid(String allUuids);

    int getListByRoleIdInt(Integer id);

    int deleteByRoleId(@Param("roleIdList") List<Integer> roleIdList);

    void insertMultiple(Map map);

    void setMenuStateByRecursion(Map map);
}
