package cn.ewsd.repository.service.impl;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.repository.mapper.MSafeStoreMapper;
import cn.ewsd.repository.model.MSafeStore;
import cn.ewsd.repository.service.MSafeStoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Service("mSafeStoreServiceImpl")
public class MSafeStoreServiceImpl extends RepositoryBaseServiceImpl<MSafeStore, String> implements MSafeStoreService {
	@Autowired
	private MSafeStoreMapper mSafeStoreMapper;

    @Override
    public PageSet<MSafeStore> getPageSet(PageParam pageParam, String storeNo, String matQry) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<MSafeStore> list = mSafeStoreMapper.getPageSet(storeNo, matQry);
        PageInfo<MSafeStore> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Transactional
    @Override
    public int saveData(HashMap<String, Double[]> map){
         int count = 0;

        Iterator<String> it = map.keySet().iterator();
        while(it.hasNext()){
            String key = it.next();
            String matNo = key.split("_")[0];
            String storeNo = key.split("_")[1];
            Double[] d = map.get(key);

            mSafeStoreMapper.deleteData(matNo, storeNo);
            mSafeStoreMapper.save(storeNo, matNo, d);
            count++;
        }

         return count;
    }

    @Transactional
    @Override
    public void calcSafeStore(String storeNo){
        mSafeStoreMapper.calcSafeStock(storeNo);
    }
}
