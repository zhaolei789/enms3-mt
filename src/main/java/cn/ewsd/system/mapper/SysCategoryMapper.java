package cn.ewsd.system.mapper;


import cn.ewsd.base.bean.CommonMapper;
import cn.ewsd.system.model.SysCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhaoxiace
 * @email zhaoxiace@ewsd.cn
 * @date 2018-06-09 09:20:29
 */

public interface SysCategoryMapper extends CommonMapper<SysCategory> {

    //获取分页集
    List<SysCategory> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(SysCategory var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<SysCategory> var1);

    int executeUpdate(SysCategory var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    SysCategory queryObject(Object var1);

    List<SysCategory> queryList(Map<String, Object> var1);

    List<SysCategory> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

    List<SysCategory> getCategorysByTypeAndLevelId(String levelId, String type);

    List<SysCategory> selectListByPid(String pid, String type);

    int getIncreasementId();

    String getChildIds(Map map);

    String getFatherId(Map map);

    List<SysCategory> getCategorysByPortalDisplayAndTypeAndLevelId(String levelId, String type);

}
