package cn.ewsd.system.mapper;


import cn.ewsd.base.bean.CommonMapper;
import cn.ewsd.system.model.Menu;
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

public interface MenuMapper extends CommonMapper<Menu> {



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
//
//    List<SysFdField> getFieldsByStateAndPuuid(@Param("filterSort") String filterSort);
//
//    List<SysFdField> getFieldsByPuuid(String puuid);
//
//    int getExistenceByPuuidAndFieldName(String puuid, String fieldName);
//
//    int getExistenceByPuuidAndFieldNameAndUuid(String puuid, String fieldName, String uuid);
//
//    List<SysFdField> getSysFdFieldByPuuid(String puuid);

    List<Menu> getListByPidAndLevelId(String pid, int level_id);

    List<Menu> getListByCodeSetIdAndLevelId(String codeSetId, String levelId);

    List<Menu> getListById(@Param("list") List<String> list);

    Integer getMaxById();

    void updatePid(Integer pid);

    int updateByUuid(String codeSetId, Integer levelId, Integer pid, String resourceType, String text, String url, String state, Integer status, String iconCls, Integer sort, String remark, String uuid);

    List<Menu> getPageSet(@Param("filterSort") String filterSort);

    String getChildIds(Map map);

    List<Menu> getListByLevelId(String level_id);

    Integer getCountById(Integer id);

    Menu getDetailById(Integer id);

    List<Menu> getListByParentid(Integer parentid);

    String getFatherId(Map map);

    Integer cascadeDeleteByUuid(@Param("uuid")String uuid);

    int getLevelIdById(@Param("id")int id);
}
