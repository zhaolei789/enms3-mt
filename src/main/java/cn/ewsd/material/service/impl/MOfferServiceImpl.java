package cn.ewsd.material.service.impl;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.material.mapper.MOfferMapper;
import cn.ewsd.material.model.*;
import cn.ewsd.material.service.MOfferService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("mOfferServiceImpl")
public class MOfferServiceImpl extends MaterialBaseServiceImpl<MOffer, String> implements MOfferService {
	@Autowired
	private MOfferMapper mOfferMapper;

    @Override
    public PageSet<MOffer> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<MOffer> list = mOfferMapper.getPageSet(filterSort);
        PageInfo<MOffer> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public String getNewOfferNo(){
        String newOfferNo = mOfferMapper.getMaxOfferNo();
        if(newOfferNo == null){
            newOfferNo = "100001";
        }else{
            newOfferNo = String.valueOf(Long.parseLong(newOfferNo) + 1);
        }
        return  newOfferNo;
    }

    @Override
    public int executeSave(MOffer mOffer) {
        return mOfferMapper.executeSave(mOffer);
    }

    @Override
    public MOffer queryObject(String uuid){
        return mOfferMapper.queryObject(uuid);
    }

    @Override
    public int executeUpdate(MOffer mOffer) {
        return mOfferMapper.executeUpdate(mOffer);
    }

    @Override
    public int executeDelete(String uuid){
        return mOfferMapper.executeDelete(uuid);
    }

    @Override
    public List<MOffer> getOfferList(String offerName){
        return mOfferMapper.getOfferList(offerName);
    }
}
