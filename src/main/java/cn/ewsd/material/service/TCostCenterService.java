package cn.ewsd.material.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.TCostCenter;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface TCostCenterService extends MaterialBaseService<TCostCenter, String> {
    PageSet<TCostCenter> getPageSet(PageParam pageParam, String filterSort);

    int checkCostCenterExistByNoOrName(String centerNo, String centerName);

    int executeSave(TCostCenter tCostCenter, String[] deptNos);

    TCostCenter queryObject(String centerNo);

    int executeUpdate(TCostCenter tCostCenter, String []deptNos);

    int executeDelete(String centerNo, String deptNo);

    List<TCostCenter> getTeamCenter(String teamNo);

    String getMoveType(String centerNo, String wbsElement);

    List<TCostCenter> getCenterSet(String userDeptIds);

    List<TCostCenter> getCenterList();
}
