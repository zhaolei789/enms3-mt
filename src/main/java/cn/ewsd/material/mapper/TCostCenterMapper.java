package cn.ewsd.material.mapper;
import cn.ewsd.material.model.TCostCenter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface TCostCenterMapper extends tk.mybatis.mapper.common.Mapper<TCostCenter> {
    List<TCostCenter> getPageSet(@Param("filterSort") String filterSort);

    int checkCostCenterExistByNoOrName(@Param("centerNo") String centerNo, @Param("centerName") String centerName);

    int executeSave(TCostCenter tCostCenter);

    int saveCenterTeam(@Param("centerNo") String centerNo, @Param("deptNo") String deptNo);

    TCostCenter queryObject(String centerNo);

    int executeUpdate(TCostCenter tCostCenter);

    int deleteCenterTeam(@Param("centerNo") String centerNo, @Param("deptNo") String deptNo);

    int executeDelete(String centerNo);

    List<TCostCenter> getTeamCenter(String teamNo);

    String getMoveType(String centerNo);

    List<TCostCenter> getCenterSet(String userDeptIds);

    TCostCenter getCenterTeam(String centerNo);

    List<TCostCenter> getCenterList();
}
