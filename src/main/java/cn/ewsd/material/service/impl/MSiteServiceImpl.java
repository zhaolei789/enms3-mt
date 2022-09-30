package cn.ewsd.material.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.ewsd.material.mapper.MSiteMapper;
import cn.ewsd.material.model.MSite;
import cn.ewsd.material.service.MSiteService;
import cn.ewsd.material.service.impl.MaterialBaseServiceImpl;

@Service("mSiteServiceImpl")
public class MSiteServiceImpl extends MaterialBaseServiceImpl<MSite, String> implements MSiteService {
	@Autowired
	private MSiteMapper mSiteMapper;

    @Override
    public PageSet<MSite> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<MSite> list = mSiteMapper.getPageSet(filterSort);
        PageInfo<MSite> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public MSite queryObject(String uuid){
		return mSiteMapper.queryObject(uuid);
	}

	@Override
	public List<MSite> queryList(Map<String, Object> map){
		return mSiteMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return mSiteMapper.queryTotal(map);
	}

	@Override
	public int executeSave(MSite mSite){
		return mSiteMapper.executeSave(mSite);
	}

	@Override
	public int executeUpdate(MSite mSite){
		return mSiteMapper.executeUpdate(mSite);
	}

	@Override
	public int executeDelete(String uuid){
		return mSiteMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return mSiteMapper.executeDeleteBatch(uuids);
	}

	@Override
	public List<MSite> queryListByFS(String filterSort){
		return mSiteMapper.getPageSet(filterSort);
	}

	@Override
	public List<MSite> queryListByIds(String[] uuids){
		return mSiteMapper.queryListByIds(uuids);
	}

	@Override
	public List<MSite> getAllList() {
    	String filterSort = "is_del = 0 order by store_no asc,site_code asc";
		return mSiteMapper.getPageSet(filterSort);
	}

	@Override
	public List<MSite> getListByStoreNo(String storeNo) {
		String filterSort = "store_no = " +storeNo+
				" and is_del = 0 order by store_no asc,site_code asc";
		return mSiteMapper.getPageSet(filterSort);
	}
}
