package cn.ewsd.system.mapper;


import cn.ewsd.base.bean.CommonMapper;
import cn.ewsd.system.model.SysAuthData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-07-02 08:49:48
 */

public interface SysAuthDataMapper extends CommonMapper<SysAuthData> {

    //获取分页集
    List<SysAuthData> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(SysAuthData var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<SysAuthData> var1);

    int executeUpdate(SysAuthData var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    SysAuthData queryObject(Object var1);

    List<SysAuthData> queryList(Map<String, Object> var1);

    List<SysAuthData> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

    List<SysAuthData> getListByLevelId(String levelId);

    List<SysAuthData> getListByPid(String pid);

    int getIncreasementId();

    List<SysAuthData> getLisById(String filter);

    SysAuthData getListById(String dataAuth);

}
