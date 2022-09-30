package cn.ewsd.material.service.impl;

import cn.ewsd.material.mapper.MPrjItemMapper;
import cn.ewsd.material.model.MPrjItem;
import cn.ewsd.material.service.MPrjItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("mPrjItemServiceImpl")
public class MPrjItemServiceImpl extends MaterialBaseServiceImpl<MPrjItem, String> implements MPrjItemService {
	@Autowired
	private MPrjItemMapper mPrjItemMapper;

    @Override
    public int deleteByPrjNo(String prjNo){
        return mPrjItemMapper.deleteByPrjNo(prjNo);
    }

    @Override
    public List<MPrjItem> getPrjItemListByPrjNo(String prjNo, String filterSort){
        return mPrjItemMapper.getPrjItemListByPrjNo(prjNo, filterSort);
    }

    @Transactional
    @Override
    public int saveData(ArrayList<MPrjItem> list, String prjNo){
        mPrjItemMapper.deleteByPrjNo(prjNo);

        for(int i=0; i<list.size(); i++){
            MPrjItem mPrjItem = list.get(i);
            mPrjItemMapper.saveData(mPrjItem);
        }
        return 1;
    }

    @Override
    public String[] getItemNosByPrjNo(String prjNo){
        return mPrjItemMapper.getItemNosByPrjNo(prjNo);
    }
}
