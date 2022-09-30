package cn.ewsd.material.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.*;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface MOfferService extends MaterialBaseService<MOffer, String> {
    PageSet<MOffer> getPageSet(PageParam pageParam, String filterSort);

    String getNewOfferNo();

    int executeSave(MOffer offer);

    MOffer queryObject(String uuid);

    int executeUpdate(MOffer offer);

    int executeDelete(String uuid);

    List<MOffer> getOfferList(String offerName);
}
