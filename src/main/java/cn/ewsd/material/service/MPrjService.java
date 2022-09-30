package cn.ewsd.material.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MPrj;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface MPrjService extends MaterialBaseService<MPrj, String> {
    PageSet<MPrj> getPageSet(PageParam pageParam, String filterSort);

    int executeSave(MPrj mPrj);

    MPrj queryObject(String uuid);

    int executeUpdate(MPrj offer);

    int executeDelete(String uuid);

    List<MPrj> getUpNameAll();

    List<MPrj> getPrjByStatusAndTeamNoAndItemNo(String status, String teamNo, String itemNo);

    MPrj getPrjByPrjNo(String prjNo);

    List<MPrj> getPrjList(String teamNo, String prjStatus);

    List<MPrj> getPrjSet(String teamNo, String prjStatus);

    List<MPrj> getPrjFromBackQuota();
}
