package cn.ewsd.material.service.impl;

import cn.ewsd.material.mapper.MOfferMatMapper;
import cn.ewsd.material.model.MOffer;
import cn.ewsd.material.model.MOfferMat;
import cn.ewsd.material.service.MOfferMatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("mOfferMatServiceImpl")
public class MOfferMatServiceImpl extends MaterialBaseServiceImpl<MOfferMat, String> implements MOfferMatService {
	@Autowired
	private MOfferMatMapper mOfferMatMapper;

    @Override
    public List<MOfferMat> getOfferMatByOfferNo(String offerNo) {
        return mOfferMatMapper.getOfferMatByOfferNo(offerNo);
    }

    @Override
    public List<MOffer> getOfferMatByMatNo(String matNo){
        return mOfferMatMapper.getOfferMatByMatNo(matNo);
    }
}
