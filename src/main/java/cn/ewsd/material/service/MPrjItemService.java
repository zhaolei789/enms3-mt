package cn.ewsd.material.service;

import cn.ewsd.material.model.MPrjItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface MPrjItemService extends MaterialBaseService<MPrjItem, String> {
    int deleteByPrjNo(String prjNo);

    List<MPrjItem> getPrjItemListByPrjNo(String prjNo, String filterSort);

    int saveData(ArrayList<MPrjItem> list, String prjNo);

    String[] getItemNosByPrjNo(String prjNo);
}
