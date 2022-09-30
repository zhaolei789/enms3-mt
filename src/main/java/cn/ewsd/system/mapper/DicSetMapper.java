package cn.ewsd.system.mapper;


import cn.ewsd.base.bean.CommonMapper;
import cn.ewsd.system.model.DicItem;
import cn.ewsd.system.model.DicSet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 *
 */

public interface DicSetMapper extends CommonMapper<DicSet> {



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


    List<DicSet> getPageSet(@Param("filterSort") String filterSort);

    DicSet getListByCode(String code);

    Integer updateIsDel(int i, String s);

    List<DicSet> getListByCodes(String code);

    DicSet getDicSetByCode(String code);

    DicItem getListByValue(@Param("value") String value);

    Integer getListByValueAndUuid(@Param("value")String value, @Param("uuid")String uuid);

    List<DicSet> getAllDicSet();
}
