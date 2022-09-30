package cn.ewsd.system.mapper;


import cn.ewsd.base.bean.CommonMapper;
import cn.ewsd.system.model.SysGenerator;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-12-24 16:27:06
 */

public interface SysGeneratorMapper extends CommonMapper<SysGenerator> {

    //获取分页集
    List<SysGenerator> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(SysGenerator var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<SysGenerator> var1);

    int executeUpdate(SysGenerator var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    SysGenerator queryObject(Object var1);

    List<SysGenerator> queryList(Map<String, Object> var1);

    List<SysGenerator> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

}
