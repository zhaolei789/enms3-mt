package cn.ewsd.repository.service.impl;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.repository.mapper.MStoreMapper;
import cn.ewsd.repository.model.MStore;
import cn.ewsd.repository.service.MStoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("mStoreServiceImpl")
public class MStoreServiceImpl extends RepositoryBaseServiceImpl<MStore, String> implements MStoreService {
	@Autowired
	private MStoreMapper mStoreMapper;

    @Override
    public PageSet<MStore> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<MStore> list = mStoreMapper.getPageSet(filterSort);
        PageInfo<MStore> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public int executeSave(MStore mStore) {
        return mStoreMapper.executeSave(mStore);
    }

    @Override
    public MStore queryObject(String uuid){
        return mStoreMapper.queryObject(uuid);
    }

    @Override
    public int executeUpdate(MStore mStore) {
        return mStoreMapper.executeUpdate(mStore);
    }

    @Override
    public int executeDelete(String uuid){
        return mStoreMapper.executeDelete(uuid);
    }

    @Override
    public List<MStore> getStoreList(String userId, String upDown, String mngTeam, String ifSafe, String ifStock, String bigType, String storeType, String storeLevel){
        return mStoreMapper.getStoreList(userId, upDown, mngTeam, ifSafe, ifStock, bigType, storeType, storeLevel);
    }

    @Override
    public MStore getStoreByNo(String storeNo){
        return mStoreMapper.getStoreByNo(storeNo);
    }

    @Override
    public MStore getStoreByName(String storeName) {
        return mStoreMapper.getStoreByName(storeName);
    }

    @Override
    public List<MStore> getStoreList1(){
        return mStoreMapper.getStoreList1();
    }

    @Override
    public List<MStore> getOutStoreList(String startDate, String endDate){
        return mStoreMapper.getOutStoreList(startDate, endDate);
    }

    @Override
    public List<MStore> getPowerStore(boolean isPower, String userId){
        return mStoreMapper.getPowerStore(isPower, userId);
    }

    @Override
    public List<MStore> getAllStockQryStore(String levels, boolean ifPower){
        return mStoreMapper.getAllStockQryStore(levels, ifPower);
    }
}
