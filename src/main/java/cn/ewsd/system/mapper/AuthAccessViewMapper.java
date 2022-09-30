package cn.ewsd.system.mapper;

import cn.ewsd.base.bean.CommonMapper;
import cn.ewsd.system.model.AuthAccess;
import cn.ewsd.system.model.AuthAccessView;
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


public interface AuthAccessViewMapper extends CommonMapper<AuthAccessView> {



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


    List<AuthAccessView> getPageSet(@Param("filterSort") String filterSort);

    List<Map<String,Object>> getListByJdbc(@Param("list") List<String> list, @Param("codeSetId") String codeSetId, @Param("levelId") String levelId, @Param("menuId") String menuId);

    List<Map<String,Object>> getListByRoleIdAndCodeSetIdAndMenuId2(@Param("list") List<String> list, @Param("codeSetId") String codeSetId, @Param("menuId") String menuId);

    List<AuthAccessView> getListByRoleId(@Param("list") List<String> list);

    List<Map<String,Object>> getListByRoleIdAndPid(@Param("list") List<String> list, @Param("pid") String pid);

    List<AuthAccessView> getListByRoleIdAndCodeSetIdAndLevelId3(@Param("list") List<String> list, @Param("codeSetId") String codeSetId, @Param("levelId") String levelId);

    List<AuthAccess> getListByRoleIdAllMenu(@Param("roleId") String roleId);

}
