package cn.ewsd.mdata.mapper;

import cn.ewsd.system.model.AuthAccess;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-20 11:40:20
 */
public interface UtilMapper {
    int getRowCountByCondition(@Param("tableName") String tableName, @Param("conditionList") ArrayList<String[]> conditionList);

    int getAuthAccessByRoleIdsAndId(@Param("roleIds") String roleIds, @Param("id") int id);

    String getMaxBillCode(@Param("tableName") String tableName, @Param("columnName") String columnName, @Param("prefix") String prefix,
                          @Param("conditionList") ArrayList<String[]> conditionList);

    int getCheckRight(@Param("processNo") String processNo, @Param("userId") String userId);
}
