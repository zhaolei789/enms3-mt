package cn.ewsd.system.mapper;

import cn.ewsd.system.model.Category;
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

public interface CategoryMapper extends tk.mybatis.mapper.common.Mapper<Category> {

    //获取分页集
    List<Category> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(Category var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<Category> var1);

    int executeUpdate(Category var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    Category queryObject(Object var1);

    List<Category> queryList(Map<String, Object> var1);

    List<Category> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

    List<Category> getCategorysByTypeAndLevelId(String levelId, String type);

    List<Category> selectListByPid(String pid, String type);

    int getIncreasementId();

    String getChildIds(Map map);

    String getFatherId(Map map);

    List<Category> getCategorysByPortalDisplayAndTypeAndLevelId(String levelId, String type);

}
