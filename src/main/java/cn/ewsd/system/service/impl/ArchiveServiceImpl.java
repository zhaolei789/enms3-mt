package cn.ewsd.system.service.impl;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.system.mapper.ArchiveMapper;
import cn.ewsd.system.model.Archive;
import cn.ewsd.system.service.ArchiveService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("archiveServiceImpl")
public class ArchiveServiceImpl extends SystemBaseServiceImpl<Archive, String> implements ArchiveService {

//    @Resource
//    private ISystemBaseDao<Archive, String> archiveDao;

    @Autowired
    private ArchiveMapper archiveMapper;

//    @Override
//    public void updateVisitByUuid(String uuid) {
//        try {
//            Archive archive = archiveMapper.selectByPrimaryKey(uuid);
//            archive.setViewCount(archive.getViewCount() + 1);
//            archiveMapper.updateByPrimaryKeySelective(archive);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//    @Override
//    public List<Archive> getHotArchives(Integer channelId, Integer limit) {
//        String idStr = archiveDao.callProAndReturn("{call p_get_child_ids(?,?,?)}", "sys_channel", channelId, "@idStr");
//        List<Archive> archives = archiveDao.getLimitedListByHql("FROM Archive WHERE categoryId IN (" + idStr + ") ORDER BY viewNums DESC", 0, limit);
//        return archives;
//    }

    @Override
    public List<Archive> getListByCreateTime() {
        return archiveMapper.getListByCreateTime();
    }

    @Override
    public String getChildIds(Map map) {
        return archiveMapper.getChildIds(map);
    }

    @Override
    public PageSet<Archive> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<Archive> list = archiveMapper.getPageSet(filterSort);
        PageInfo<Archive> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public List<Archive> getListByFilterSort(String filterSort) {
        return archiveMapper.getListByFilterSort(filterSort);
    }
}
