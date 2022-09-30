package cn.ewsd.system.service.impl;

import cn.ewsd.common.service.impl.MybatisBaseServiceImpl;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.system.mapper.CategoryMapper;
import cn.ewsd.system.model.Category;
import cn.ewsd.system.service.CategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("CategoryServiceImpl")
public class CategoryServiceImpl extends MybatisBaseServiceImpl<Category, String> implements CategoryService {
	@Autowired
	private CategoryMapper categoryMapper;

    @Override
    public PageSet<Category> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<Category> list = categoryMapper.getPageSet(filterSort);
        PageInfo<Category> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

	@Override
	public Category queryObject(String uuid){
		return categoryMapper.queryObject(uuid);
	}

	@Override
	public List<Category> queryList(Map<String, Object> map){
		return categoryMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return categoryMapper.queryTotal(map);
	}

	@Override
	public int executeSave(Category sysCategory){
		return categoryMapper.executeSave(sysCategory);
	}

	@Override
	public int executeUpdate(Category sysCategory){
		return categoryMapper.executeUpdate(sysCategory);
	}

	@Override
	public int executeDelete(String uuid){
		return categoryMapper.executeDelete(uuid);
	}

	@Override
	public int executeDeleteBatch(String[] uuids){
		return categoryMapper.executeDeleteBatch(uuids);
	}

	@Override
	public List<Category> getCategorysByTypeAndLevelId(String levelId, String type) {
		return categoryMapper.getCategorysByTypeAndLevelId(levelId,type);
	}

	@Override
	public List<Category> selectListByPid(String pid, String type) {
		return categoryMapper.selectListByPid(pid,type);
	}

	@Override
	public int getIncreasementId() {
		return categoryMapper.getIncreasementId();
	}

	@Override
	public String getChildIds(Map map) {
		return categoryMapper.getChildIds(map);
	}

	@Override
	public String getFatherId(Map map) {
		return categoryMapper.getFatherId(map);
	}

	@Override
	public List<Category> getCategorysByPortalDisplayAndTypeAndLevelId(String levelId, String type) {
		return categoryMapper.getCategorysByPortalDisplayAndTypeAndLevelId(levelId,type);
	}

}
