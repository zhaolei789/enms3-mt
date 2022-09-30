package cn.ewsd.material.service;

import cn.ewsd.material.model.MOffer;
import cn.ewsd.material.model.MOfferMat;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface MOfferMatService extends MaterialBaseService<MOfferMat, String> {
    List<MOfferMat> getOfferMatByOfferNo(String offerNo);

    List<MOffer> getOfferMatByMatNo(String matNo);
}
