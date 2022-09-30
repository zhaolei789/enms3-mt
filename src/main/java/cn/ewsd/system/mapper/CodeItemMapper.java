package cn.ewsd.system.mapper;


import cn.ewsd.base.bean.CommonMapper;
import cn.ewsd.system.model.CodeItem;
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

public interface CodeItemMapper extends CommonMapper<CodeItem> {



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


    List<CodeItem> getPageSet(@Param("filterSort") String filterSort);


    List<CodeItem> getListByLevelId(@Param("filterSort") String filterSort);

    List<CodeItem> getListByCodeSetIdAndLevelId(String codeSetId, String levelId);

    List<CodeItem> getListByPid(String pid);

    List<CodeItem> getListByCodeSetIdAndPid(String codeSetId, String pid);

    List<CodeItem> getListByCodeSetId(String codeSetId);

    List<CodeItem> getListByPidAndLevelId(String pid, int levelId);

    String getFatherIdsVarchar(Map map);

    CodeItem getListById(String id);

    CodeItem getDetailByCodesetIdAndId(String codesetId, String id);

    CodeItem getDetailByCodesetIdAndCode(String codesetId, String code);

    CodeItem getDetailByCodesetIdAndText(String codesetId, String text);

    Integer getCountByCodeSetIdAndId(String codeSetId, String id, String levelId);

    CodeItem getDetailByHqlCodeSetIdAndId(String codeSetId, String id, String levelId);

    Integer cascadeDeleteByUuid(@Param("uuid") String uuid);

    CodeItem getCodeItemById(@Param("id")String id);
}
