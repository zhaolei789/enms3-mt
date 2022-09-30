package cn.ewsd.system.mapper;


import cn.ewsd.base.bean.CommonMapper;
import cn.ewsd.system.model.Remind;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-06-26 10:50:51
 */

public interface RemindMapper extends CommonMapper<Remind> {



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


    List<Remind> getPageSet(@Param("filterSort") String filterSort);



}
