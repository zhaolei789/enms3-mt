package cn.ewsd.system.service.impl;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.system.mapper.DicItemMapper;
import cn.ewsd.system.model.DicItem;
import cn.ewsd.system.service.DicItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unchecked"})
@Service("dicItemService")
public class DicItemServiceImpl extends SystemBaseServiceImpl<DicItem, String> implements DicItemService {


    @Resource
    private DicItemMapper dicItemMapper;

//    @Resource
//    public void setSystemBaseDao(ISystemBaseDao<DicItem, String> dicItemDao) {
//        super.setBaseDao(dicItemDao);
//    }

    @Override
    public DicItem getDicItem(String puuid, String value) {
//        return dicItemDao.getDetailByHql(String.format("FROM DicItem WHERE puuid = '%s' AND value = '%s'", puuid, value));
   return null;
    }

    @Override
    public PageSet<DicItem> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<DicItem> list = dicItemMapper.getPageSet(filterSort);
        PageInfo<DicItem> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public List<Map<String, Object>> getListByPuuid(String uuid) {
        return dicItemMapper.getListByPuuid(uuid);
    }

    @Override
    public List<DicItem> getListByUuid(String uuid) {
        return dicItemMapper.getListByUuid(uuid);
    }

    @Override
    public DicItem getDicItemByValue(String value) {
        return dicItemMapper.getDicItemByValue(value);
    }
}
